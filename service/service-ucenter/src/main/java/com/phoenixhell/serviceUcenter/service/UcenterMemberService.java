package com.phoenixhell.serviceUcenter.service;

import com.phoenixhell.serviceUcenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.serviceUcenter.entity.vo.LoginVO;
import com.phoenixhell.serviceUcenter.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-03-23
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVO loginVO);

    String register(RegisterVo registerVo);

    UcenterMember getUserInfoByToken(HttpServletRequest request);

    UcenterMember getUserInfoByTokenId(String userId);
}
