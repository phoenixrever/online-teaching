package com.phoenixhell.serviceUcenter.controller;

import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.phoenixhell.serviceUcenter.entity.UcenterMember;
import com.phoenixhell.serviceUcenter.entity.WxProperties;
import com.phoenixhell.serviceUcenter.service.UcenterMemberService;
import com.phoenixhell.serviceUcenter.utils.HttpClientUtils;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import com.phoenixhell.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author phoenixhell
 * @since 2021/3/25 0025-上午 8:03
 */
@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxController {
    @Autowired
    private WxProperties wxProperties;
    @Autowired
    private UcenterMemberService ucenterMemberService;
    @GetMapping("/qrCode")
    public String getQrCode(){
        // 微信开放平台授权baseUrl
        String url = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        String redirectUrl= wxProperties.getRedirectUrl();
        try {
            redirectUrl= URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new MyException(20001,"url encode failed");
        }
        String wxUrl = String.format(url, wxProperties.getAppId(),
                redirectUrl,
                "phoenixhell");

        return "redirect:" + wxUrl;
    }

    @GetMapping("/callback")
    public String callback(String code, String state){
        String accessTokenInfo = null;
        String userInfo =null;
        UcenterMember ucenterMember=null;
        try {
                //向认证服务器发送请求换取access_token  利用申请的appid appsecrect 得到openid 和accesstoken
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl, wxProperties.getAppId(),
                    wxProperties.getAppSecret(),
                    code);
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl, "utf-8", 10000, 10000);
            Gson gson = new Gson();
            HashMap<String,String> hashMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String openid = hashMap.get("openid");
            ucenterMember = ucenterMemberService.query().eq("openid", openid).one();
            if(ucenterMember==null){
                String accessToken = hashMap.get("access_token");

                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap<String, String> userMap = JSONObject.parseObject(userInfo, new TypeReference<HashMap<String, String>>(){});
                ucenterMember=new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setAvatar(userMap.get("headimgurl"));
                ucenterMember.setNickname(userMap.get("nickname"));
                ucenterMember.setSex(Integer.valueOf(userMap.get("sex")));
                ucenterMember.setEmail(UUID.randomUUID().toString().substring(0,6)+"@163.com");
                System.out.println(ucenterMember);
                boolean save = ucenterMemberService.save(ucenterMember);
                System.out.println(ucenterMember);

                if(!save){
                    throw new MyException(20001,"微信注册失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"微信注册失败");
        }
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return "redirect:"+"http://localhost:3000?token="+token;
    }
}
