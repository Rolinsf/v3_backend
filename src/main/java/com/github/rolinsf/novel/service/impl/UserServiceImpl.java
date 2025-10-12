package com.github.rolinsf.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.rolinsf.novel.core.common.constant.ErrorCodeEnum;
import com.github.rolinsf.novel.core.common.exception.BusinessException;
import com.github.rolinsf.novel.core.common.req.UserRegisterReqDto;
import com.github.rolinsf.novel.core.common.resp.RestResp;
import com.github.rolinsf.novel.core.common.resp.UserRegisterRespDto;
import com.github.rolinsf.novel.core.common.util.JwtUtils;
import com.github.rolinsf.novel.core.constant.DatabaseConsts;
import com.github.rolinsf.novel.core.constant.SystemConfigConsts;
import com.github.rolinsf.novel.dao.entity.UserInfo;
import com.github.rolinsf.novel.dao.mapper.UserBookshelfMapper;
import com.github.rolinsf.novel.dao.mapper.UserInfoMapper;
import com.github.rolinsf.novel.manager.VerifyCodeManager;
import com.github.rolinsf.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserInfoMapper userInfoMapper;

    private final VerifyCodeManager verifyCodeManager;

    private final UserBookshelfMapper userBookshelfMapper;

    private final JwtUtils jwtUtils;
    @Override
    public RestResp<UserRegisterRespDto> register(UserRegisterReqDto dto) {
        // 校验图形验证码是否正确
        if (!verifyCodeManager.imgVerifyCodeOk(dto.getSessionId(), dto.getVelCode())) {
            // 图形验证码校验失败
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }

        // 校验手机号是否已注册
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DatabaseConsts.UserInfoTable.COLUMN_USERNAME, dto.getUsername())
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        if (userInfoMapper.selectCount(queryWrapper) > 0) {
            // 手机号已注册
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }

        // 注册成功，保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes(StandardCharsets.UTF_8)));
        userInfo.setUsername(dto.getUsername());
        userInfo.setNickName(dto.getUsername());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setSalt("0");
        userInfoMapper.insert(userInfo);

        // 删除验证码
        verifyCodeManager.removeImgVerifyCode(dto.getSessionId());

        // 生成JWT 并返回
        return RestResp.ok(
                UserRegisterRespDto.builder()
                        .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                        .uid(userInfo.getId())
                        .build()
        );

    }


}
