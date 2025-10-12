package com.github.rolinsf.novel.service;

import com.github.rolinsf.novel.core.common.req.UserRegisterReqDto;
import com.github.rolinsf.novel.core.common.resp.RestResp;
import com.github.rolinsf.novel.core.common.resp.UserRegisterRespDto;
import com.github.rolinsf.novel.dto.req.UserLoginReqDto;
import com.github.rolinsf.novel.dto.resp.UserLoginRespDto;

public interface UserService {
    /**
     * 用户注册
     *
     * @param dto 注册参数
     * @return JWT
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);
    /**
     * 用户登录
     *
     * @param dto 登录参数
     * @return JWT + 昵称
     */
    RestResp<UserLoginRespDto> login(UserLoginReqDto dto);

}
