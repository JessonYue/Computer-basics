package com.chow;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    private final static String FILE_PATH = "D:\\ParseSoELF\\src\\com\\chow\\libhello-jni.so";

    public static void main(String[] args) {
	    // write your code here
        byte[] soBytes = getBytesFromFile(FILE_PATH);
        System.out.println("parse elf header... ");
        ParseSoUtil.parseHeader(soBytes);
        System.out.println("======================================");

        System.out.println("parse program header... ");
        ParseSoUtil.parseProgramHeaderList(soBytes);
        System.out.println("======================================");

        System.out.println("parse Section header... ");
        ParseSoUtil.parseSectionHeaderList(soBytes);
        System.out.println("======================================");

        System.out.println("parse Symbol Table... ");
        ParseSoUtil.parseSymbolTable(soBytes);
        System.out.println("======================================");

        System.out.println("parse String Table... ");
        ParseSoUtil.parseStringTable(soBytes);
        System.out.println("======================================");
    }

    /**
     * 读取二进制数据
     * @param filePath 文件路径
     * @return
     */
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
