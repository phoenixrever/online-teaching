package com.phoenixhell.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author phoenixhell
 * @since 2021/3/5 0005-下午 2:10
 */

public interface OssService {
    String uploadFileToOss(MultipartFile file);
}
