package com.phoenixhell.serviceUcenter.service.impl;

import com.phoenixhell.serviceUcenter.entity.UcenterMember;
import com.phoenixhell.serviceUcenter.entity.vo.LoginVO;
import com.phoenixhell.serviceUcenter.entity.vo.RegisterVo;
import com.phoenixhell.serviceUcenter.mapper.UcenterMemberMapper;
import com.phoenixhell.serviceUcenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.JwtUtils;
import com.phoenixhell.utils.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-03-23
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    private final String CODE="12345";
    @Override
    public String login(LoginVO loginVO) {
        String emailOrMobile = loginVO.getEmailOrMobile();
        String password = loginVO.getPassword();
        if(StringUtils.isEmpty(emailOrMobile)){
            throw new MyException(20001,"email或者手机为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new MyException(20001,"密码为空");
        }
        String encryptPassword = MD5.encrypt(password);
        UcenterMember member = this.query().eq("mobile", emailOrMobile).or().eq("nickname", emailOrMobile).eq("password", encryptPassword).one();
        if(member==null){
            throw new MyException(20001,"登陆失败");
        }
        if(member.getIsDisabled()){
            throw new MyException(20001,"此账户被禁用");
        }
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return token;
    }

    public String register(RegisterVo registerVo) {
        String username = registerVo.getNickname();
        String phone = registerVo.getMobile();
        String email = registerVo.getEmail();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(username)){
            throw new MyException(20001,"用户名为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new MyException(20001,"密码为空");
        }
        if(StringUtils.isEmpty(email)){
            throw new MyException(20001,"email为空");
        }
        if(StringUtils.isEmpty(phone)){
            throw new MyException(20001,"phone为空");
        }
        if(StringUtils.isEmpty(code)){
            throw new MyException(20001,"code为空");
        }
        String encryptPassword = MD5.encrypt(password);
        registerVo.setPassword(encryptPassword);
        UcenterMember ucenterMember =new UcenterMember();
        BeanUtils.copyProperties(registerVo,ucenterMember);
        Integer mobiles = this.query().eq("mobile", phone).count();
        Integer emails = this.query().eq("email", phone).count();
        if(mobiles>0||emails>0){
            throw new MyException(20001,"手机或者邮箱已经存在");
        }
        if(CODE.equals(code)){
            boolean save = this.save(ucenterMember);
            if(!save){
                throw new MyException(20001,"注册失败");
            }
        }
        String token = JwtUtils.getJwtToken(ucenterMember.getId(),ucenterMember.getNickname());
        return token;
    }

    @Override
    public UcenterMember getUserInfoByToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = this.getById(id);
        if(member==null){
            throw new MyException(20001,"无此用户");
        }
//        member.setPassword("");
        return  member;
    }

    @Override
    public UcenterMember getUserInfoByTokenId(String userId) {
        UcenterMember member = this.getById(userId);
        if(member==null){
            throw new MyException(20001,"无此用户");
        }
//        member.setPassword("");
        return  member;
    }

}
