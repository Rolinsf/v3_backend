package com.github.rolinsf.novel.controller.front;

import com.github.rolinsf.novel.core.common.constant.ApiRouterConsts;
import com.github.rolinsf.novel.core.common.req.UserRegisterReqDto;
import com.github.rolinsf.novel.core.common.resp.RestResp;
import com.github.rolinsf.novel.core.common.resp.UserRegisterRespDto;
import com.github.rolinsf.novel.dto.req.UserLoginReqDto;
import com.github.rolinsf.novel.dto.resp.UserLoginRespDto;
import com.github.rolinsf.novel.service.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserController", description = "前台门户-会员模块")
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_USER_URL_PREFIX)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    /**
     * 用户注册接口
     */
    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto dto) {
        return userService.register(dto);
    }
    /**
     * 用户登录接口
     */
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto dto) {
        return userService.login(dto);
    }


}
