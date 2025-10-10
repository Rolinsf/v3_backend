package com.github.rolinsf.novel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.rolinsf.novel.core.common.resp.RestResp;
import com.github.rolinsf.novel.dto.resp.ImgVerifyCodeRespDto;
import com.github.rolinsf.novel.manager.VerifyCodeManager;
import com.github.rolinsf.novel.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RequiredArgsConstructor
@Slf4j
@Service

public class ResourceServiceImpl implements ResourceService {
    private final VerifyCodeManager verifyCodeManager;


    @Value("${novel.file.upload.path}")
    private String fileUploadPath;

    @Override
    public RestResp<ImgVerifyCodeRespDto> getImgVerifyCode() throws IOException {
        String sessionId = IdWorker.get32UUID();
        return RestResp.ok(ImgVerifyCodeRespDto.builder()
                .sessionId(sessionId)
                .img(verifyCodeManager.genImgVerifyCode(sessionId))
                .build());
    }

    @Override
    public RestResp<String> uploadImage(MultipartFile file) {
        return null;
    }
}
