package com.withyang.lib;

import com.withyang.lib.ParseSoUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ParseSo {
    public static void main(String [] args)  {
// 读取二进制数据
        byte[] soBytes = getBytesFromFile("/Users/yangliu/Desktop/study/Android/NDK/SoDemo/ParseSo/libs/libnative-lib.so");

        System.out.println("parse elf header ... ");
        ParseSoUtils.parseHeader(soBytes);
        System.out.println("======================================");

        System.out.println("parse program header ... ");
        ParseSoUtils.parseProgramHeaderList(soBytes);
        System.out.println("======================================");

        System.out.println("parse Section header ... ");
        ParseSoUtils.parseSectionHeaderList(soBytes);
        System.out.println("======================================");

        System.out.println("parse Symbol Table ... ");
        ParseSoUtils.parseSymbolTable(soBytes);
        System.out.println("======================================");

        System.out.println("parse String Table ... ");
        ParseSoUtils.parseStringTable(soBytes);
        System.out.println("======================================");
    }

    private static byte[] getBytesFromFile(String filePath) {
        byte[] soBytes = null;
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(filePath);
            byte[] bytes = new byte[1024];
            int len = 0;
            while((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            soBytes =  baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return soBytes;
    }
}