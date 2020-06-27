package com.chow;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    private final static String FILE_PATH = "D:\\JessonDataStruct\\ParseSoELF\\src\\com\\chow\\libhello-jni.so";

    public static void main(String[] args) {
	    // write your code here
        byte[] soBytes = getBytesFromFile(FILE_PATH);

        parseHeader(soBytes);
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

    private static void parseHeader(byte[] soBytes) {
        ElfType hdr = new ElfType();
        hdr.e_ident = Util.copyByte(soBytes, 0, 16);
        hdr.e_type = Util.copyByte(soBytes, 16, 2);
        hdr.e_machine = Util.copyByte(soBytes, 18, 2);
        hdr.e_version = Util.copyByte(soBytes, 20, 4);
        hdr.e_entry = Util.copyByte(soBytes, 24, 4);
        hdr.e_phoff = Util.copyByte(soBytes, 28, 4);
        hdr.e_shoff = Util.copyByte(soBytes, 32, 4);
        hdr.e_flags = Util.copyByte(soBytes, 36, 4);
        hdr.e_ehsize = Util.copyByte(soBytes, 40, 2);
        hdr.e_phentsize = Util.copyByte(soBytes, 42, 2);
        hdr.e_phnum = Util.copyByte(soBytes, 44, 2);
        hdr.e_shentsize = Util.copyByte(soBytes, 46, 2);
        hdr.e_shnum = Util.copyByte(soBytes, 48, 2);
        hdr.e_shstrndx = Util.copyByte(soBytes, 50, 2);
        System.out.println(hdr.toString());
    }
}
