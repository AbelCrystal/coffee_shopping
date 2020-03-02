package com.shopping.unit;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * @Description: 阿里云 oss 上传工具类(高依赖版)
 * @Author: junqiang.lu
 * @Date: 2019/5/10
 */
public class OSSBootUtil {

    private OSSBootUtil(){}

    /**
     * oss 工具客户端
     */
    private volatile static OSSClient ossClient = null;

    /**
     * 上传文件至阿里云 OSS
     * 文件上传成功,返回文件完整访问路径
     * 文件上传失败,返回 null
     *
     * @param ossConfig oss 配置信息
     * @param file 待上传文件
     * @param fileDir 文件保存目录
     * @return oss 中的相对文件路径
     */
    public static String upload(OSSConfig ossConfig, MultipartFile file, String fileDir,String fileName){
        initOSS(ossConfig);
        StringBuilder fileUrl = new StringBuilder();
        try {

            if (!fileDir.endsWith("/")) {
                fileDir = fileDir.concat("/");
            }
            fileUrl = fileUrl.append(fileDir + fileName);

            PutObjectResult putResult= ossClient.putObject(ossConfig.getBucketName(), fileUrl.toString(), file.getInputStream());
            String tag=putResult.getETag();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String url="https://"+ossConfig.getBucketName()+"."+ossConfig.getFileUrl();
        fileUrl = fileUrl.insert(0,url);
        return fileUrl.toString();
    }

    /**
     * 初始化 oss 客户端
     * @param ossConfig
     * @return
     */
    private static OSSClient initOSS(OSSConfig ossConfig) {
        if (ossClient == null ) {
            synchronized (OSSBootUtil.class) {
                if (ossClient == null) {
                    ossClient = new OSSClient(ossConfig.getEndpoint(),
                            new DefaultCredentialProvider(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()),
                            new ClientConfiguration());
                }
            }
        }
        return ossClient;
    }
}
