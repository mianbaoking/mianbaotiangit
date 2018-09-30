package com.mian.bao.tian;

import java.io.*;
import java.util.Base64;
import java.util.Date;

/**
 * Created by 田中盼 on 2018/9/13.
 */
public class Test {

    public static void main(String[] args)
    {
        String strImg = GetImageStr();
        System.out.println(strImg);
        String path = generateImage(strImg);
        System.out.println("path:"+path);
    }
    //图片转化成base64字符串
    public static String GetImageStr()
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "F://test.jpg";//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码

        return Base64.getEncoder().encodeToString(data);//返回Base64编码过的字节数组字符串
    }

    //base64字符串转化成图片
    public static String generateImage(String imgStr) {
        byte[] base64decodedBytes = Base64.getDecoder().decode(imgStr);
        for(int i=0;i<base64decodedBytes.length;i++) {
            //调整异常数据 
            if (base64decodedBytes[i] < 0) {
                base64decodedBytes[i] += 256;
            }
        }
            StringBuffer path = new StringBuffer();
            String rootPath =Class.class.getClass().getResource("/").getPath();
            System.out.println("项目的绝对路径："+rootPath);
            path.append(rootPath);
            path.append("/faces");
            File file = new File(path.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            String imgName = generateKeyId()+".jpg";
            path.append("/"+imgName);
            String imgPath = path.toString();
            try {
                OutputStream out = new FileOutputStream(imgPath);
                out.write(base64decodedBytes);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return imgPath;

    }
    public static synchronized String generateKeyId() {
        return System.currentTimeMillis() + getRandomString(8);
    }
    public static String getRandomString(int length){
        String string = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt((int) Math.round(Math.random() * (len))));
        }
        return sb.toString();
    }
}
