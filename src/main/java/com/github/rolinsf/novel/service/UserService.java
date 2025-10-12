package com.github.rolinsf.novel.service;

import com.github.rolinsf.novel.core.common.req.UserRegisterReqDto;
import com.github.rolinsf.novel.core.common.resp.RestResp;
import com.github.rolinsf.novel.core.common.resp.UserRegisterRespDto;

public interface UserService {
    /**
     * 用户注册
     *
     * @param dto 注册参数
     * @return JWT
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto);

}
