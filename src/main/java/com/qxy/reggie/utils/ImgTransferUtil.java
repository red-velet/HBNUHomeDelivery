package com.qxy.reggie.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: SayHello
 * @Date: 2023/3/26 17:33
 * @Introduction: 文件转存工具类
 */
public class ImgTransferUtil {
    public static void transferToLocal(byte[] bytes, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(fileName));
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}
