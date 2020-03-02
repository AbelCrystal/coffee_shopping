package com.shopping.service.user;

import com.shopping.unit.DateUtils;
import com.shopping.unit.OSSBootUtil;
import com.shopping.unit.OSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("fileService")
public class FileService {
    @Autowired
    private OSSConfig ossConfig;
    public List<String> upload(MultipartFile[] files) {
        List<String> listPic = new ArrayList<>();
        for (MultipartFile file :
                files) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
            String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 18) + suffix;
            String ossFileUrlBoot = OSSBootUtil.upload(ossConfig, file, "images/pic/" + DateUtils.getFormatDateTime(new Date(), DateUtils.YYYMMDD_NOT) + "/", fileName);
            listPic.add(ossFileUrlBoot);
        }
        return listPic;
    }
}
