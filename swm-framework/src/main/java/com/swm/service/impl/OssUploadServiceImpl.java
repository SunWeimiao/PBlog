package com.swm.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.swm.domain.ResponseResult;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.exception.SystemException;
import com.swm.service.UploadService;
import com.swm.utils.PathUtils;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Data
//@ConfigurationProperties(prefix = "oss")

public class OssUploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) throws IOException{
        //判断文件类型和大小
        //获取初始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名判断
        boolean judge = originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg");
        if (!judge){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //通过判断 上传文件至OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadFile(filePath,img.getInputStream());
        return ResponseResult.okResult(url);
    }


    private String uploadFile(String filePath, InputStream inputStream){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "id";
        String accessKeySecret = "secret";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "swm-blog";


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream);

        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient
        ossClient.shutdown();
        return "https://"+bucketName+"."+endpoint+"/"+filePath;
    }
}
