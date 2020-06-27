package com.chow;

import java.util.Arrays;

public class ElfType {

    public byte[] e_ident = new byte[16];
    public byte[] e_type = new byte[2];
    public byte[] e_machine = new byte[2];
    public byte[] e_version = new byte[4];
    public byte[] e_entry = new byte[4];
    public byte[] e_phoff = new byte[4];
    public byte[] e_shoff = new byte[4];
    public byte[] e_flags = new byte[4];
    public byte[] e_ehsize = new byte[2];
    public byte[] e_phentsize = new byte[2];
    public byte[] e_phnum = new byte[2];
    public byte[] e_shentsize = new byte[2];
    public byte[] e_shnum = new byte[2];
    public byte[] e_shstrndx = new byte[2];

    @Override
    public String toString() {
        return "ElfType{" +
                "\ne_ident = " + Util.bytesToHexString(e_ident) +
                "\ne_type = " + Util.bytesToHexString(e_type) +
                "\ne_machine = " + Util.bytesToHexString(e_machine) +
                "\ne_version = " + Util.bytesToHexString(e_version) +
                "\ne_entry = " + Util.bytesToHexString(e_entry) +
                "\ne_phoff = " + Util.bytesToHexString(e_phoff) +
                "\ne_shoff = " + Util.bytesToHexString(e_shoff) +
                "\ne_flags = " + Util.bytesToHexString(e_flags) +
                "\ne_ehsize = " + Util.bytesToHexString(e_ehsize) +
                "\ne_phentsize = " + Util.bytesToHexString(e_phentsize) +
                "\ne_phnum = " + Util.bytesToHexString(e_phnum) +
                "\ne_shentsize = " + Util.bytesToHexString(e_shentsize) +
                "\ne_shnum = " + Util.bytesToHexString(e_shnum) +
                "\ne_shstrndx = " + Util.bytesToHexString(e_shstrndx) +
                '}';
    }
}
