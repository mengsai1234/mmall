package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018/1/7 0007.
 */
@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    //private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 上传文件
     * 上传文件到ftp服务器
     * 删除文件（保留文件夹）
     * @param file
     * @param path
     * @return
     */
    public String upload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename(); //获取上传文件的原始文件名
        //获取文件的扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        //新上传文件
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path); //构建文件存储目录
        if(!fileDir.exists()){
            fileDir.setWritable(true); //设置文件可写
            fileDir.mkdirs();
        }
        //构建完成上传文件，包括路径+文件名
        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile); //文件上传
            //将targerFile上传到ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完之后，删除upload下的文件
            targetFile.delete(); //保留文件夹
        } catch (IOException e) {
            log.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }



}
