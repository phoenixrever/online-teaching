package com.phoenixhell.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.phoenixhell.oss.config.OssPropertiesConfig;
import com.phoenixhell.oss.service.OssService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author phoenixhell
 * @since 2021/3/5 0005-下午 2:10
 */
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private OssPropertiesConfig ossPropertiesConfig;
    @Override
    public String uploadFileToOss(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint =ossPropertiesConfig.getEndPoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId =ossPropertiesConfig.getKeyId();
        String accessKeySecret = ossPropertiesConfig.getKeySecret();
        String bucketName = ossPropertiesConfig.getBucketName();
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
//        getName : 获取表单中文件组件的名字
//        getOriginalFilename : 获取上传文件的原名
        String oldFileName = file.getOriginalFilename();
        //MD5 加密
//      String fileMD5 = DigestUtils.md5Digest(file.getInputStream());
        assert oldFileName != null;
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+oldFileName.substring(oldFileName.lastIndexOf(".")+1);
        String datePath=new DateTime().toString("yyyy/MM/dd");
        String fullFileName=datePath+"/"+fileName;
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
            InputStream inputStream = file.getInputStream();

            ossClient.putObject(bucketName, fullFileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            if(ossClient!=null){
                ossClient.shutdown();
            }
        }
        String fileUrl="https://"+bucketName+"."+endpoint+"/"+fullFileName;
        System.out.println(fileUrl);
        return fileUrl;
    }
}
