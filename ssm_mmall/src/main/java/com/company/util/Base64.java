package com.company.util;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/11/30.
 */
public class Base64 {

    public static void decoderBase64File(String base64Code, String targetPath) {
        byte[] buffer;
        FileOutputStream out = null;
        try {
            buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out = new FileOutputStream(targetPath);
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
