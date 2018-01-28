package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 处理文件的接口
 * Created by Administrator on 2018/1/7 0007.
 */
public interface IFileService {

    //上传文件
    String upload(MultipartFile file, String path);
}
