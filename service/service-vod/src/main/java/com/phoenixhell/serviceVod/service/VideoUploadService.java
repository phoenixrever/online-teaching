package com.phoenixhell.serviceVod.service;

import com.aliyuncs.DefaultAcsClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface VideoUploadService {
    public Map<String, Object> videoStreamUpload(MultipartFile file) throws IOException;
    public String deleteByVideoId(String videoIds);
}
