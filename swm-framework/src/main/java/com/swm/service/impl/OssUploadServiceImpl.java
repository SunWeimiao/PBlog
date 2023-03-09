package com.swm.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.swm.domain.ResponseResult;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.exception.SystemException;
import com.swm.service.UploadService;
import com.swm.utils.PathUtils;
import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
@Data
//@ConfigurationProperties(prefix = "oss")

public class OssUploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img){
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
        String url = uploadOSS(img,filePath);
        return ResponseResult.okResult(url);
    }
//TODO
    private String accessKey="dBDchMdL7krQM0BHHY7qYERnu_YFPQ9X0lllau0U";
    private String secretKey="XvMzf8Dahs1GfKWXdRZqUiOZHWn7hGEVkK7AFcCf";
    private String bucket="swm-blog";

    public String uploadOSS(MultipartFile imgFile,String filePath)  {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return "http://rqwd5z7pt.bkt.clouddn.com/"+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "文件上传失败";
    }
}
