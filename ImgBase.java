package com.example.demo;


import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;


public class ImgBase {

    public static String getImgBase(String fileName){
        String result="";
        try {
            //获取后缀名
            String subfix = fileName.substring(fileName.indexOf(".") + 1);
            //获取输入流对象
            FileInputStream fileInputStream = new FileInputStream(fileName);
            //获取输出流的字节数组
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //每次读取1024字节
            byte[] bytes = new byte[1024];
            int len= 0;
            //循环写入数据
            while ((len = fileInputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes,0,len);
            }
            //将字节数组进行base64编码
            String encode = new BASE64Encoder().encode(byteArrayOutputStream.toByteArray());
            //将编码后的数据拼接返回
            result ="data:image/"+subfix+";base64,"+encode;
            //关闭流对象
            fileInputStream.close();
            byteArrayOutputStream.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }
}
