package com.qxy.reggie.controller;

import com.qxy.reggie.common.R;
import com.qxy.reggie.utils.QiNiuCloudUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 17:22
 * @Introduction:
 */
@RestController
@RequestMapping("common")
@Api(tags = "文件管理")
@Slf4j
public class CommonController {
    @Value("${constant.basePath}")
    private String basePath;

    @Value("${constant.qiniucloud.URL}")
    private String URL;

    @PostMapping("upload")
    @ApiOperation("图片上传")
    public R<String> upload(@RequestParam("file") MultipartFile imgFile) {
        log.info(imgFile.getOriginalFilename());
        log.info(imgFile.getContentType());
        log.info(imgFile.getSize() + "");
        //TODO 将上传的图片转存到本地 / 七牛云服务器
        //1.给图片起个不重复的名字
        //获取图片原始名称
        String originalFilename = imgFile.getOriginalFilename();
        //获取图片后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //设置新的无重复文件名
        String fileName = UUID.randomUUID().toString() + suffix;
        ////todo 方法1：图片转存到本地
        //File file = new File(basePath);
        //if (!file.exists()) {
        //    file.mkdirs();
        //}
        //try {
        //    imgFile.transferTo(new File(basePath + fileName));
        //
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //todo 方法2：图片转存到七牛云
        try {
            QiNiuCloudUtil.upload2QiNiu(imgFile.getBytes(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("download")
    @ApiOperation("图片下载")
    public void download(@RequestParam("name") String fileName, HttpServletResponse response) {
        //TODO 图片预显示
        //todo 方法1：本地图片显示
        //输入流读取图片
        //输出流输出图片
        //try {
        //    FileInputStream fis = new FileInputStream(new File(basePath + fileName));
        //    response.setContentType("image/jpeg");
        //    ServletOutputStream outputStream = response.getOutputStream();
        //    byte[] buf = new byte[1024];
        //    int len = 0;
        //    while ((len = fis.read(buf)) != -1) {
        //        outputStream.write(buf, 0, len);
        //        outputStream.flush();
        //    }
        //    fis.close();
        //    outputStream.close();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        //todo 方法2：七牛云图片显示
        try {
            response.getWriter().write(URL + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
