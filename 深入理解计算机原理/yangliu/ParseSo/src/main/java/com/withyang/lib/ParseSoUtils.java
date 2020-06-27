package com.withyang.lib;

/**
 * Created by CYLiu on 2020/6/25
 */
class ParseSoUtils {
    private static ElfType32 type32 = new ElfType32();

    /**
     * 解析头部内容
     * @param soBytes so二进制数据
     */
    public static void parseHeader(byte[] soBytes) {
        ElfType32.elf32_hdr hdr = new ElfType32.elf32_hdr();
        hdr.e_ident = ByteUtils.copyByte(soBytes, 0, 16);
        hdr.e_type = ByteUtils.copyByte(soBytes, 16, 2);
        hdr.e_machine = ByteUtils.copyByte(soBytes, 18, 2);
        hdr.e_version = ByteUtils.copyByte(soBytes, 20, 4);
        hdr.e_entry = ByteUtils.copyByte(soBytes, 24, 4);
        hdr.e_phoff = ByteUtils.copyByte(soBytes, 28, 4);
        hdr.e_shoff = ByteUtils.copyByte(soBytes, 32, 4);
        hdr.e_flags = ByteUtils.copyByte(soBytes, 36, 4);
        hdr.e_ehsize = ByteUtils.copyByte(soBytes, 40, 2);
        hdr.e_phentsize = ByteUtils.copyByte(soBytes, 42, 2);
        hdr.e_phnum = ByteUtils.copyByte(soBytes, 44, 2);
        hdr.e_shentsize = ByteUtils.copyByte(soBytes, 46, 2);
        hdr.e_shnum = ByteUtils.copyByte(soBytes, 48, 2);
        hdr.e_shstrndx = ByteUtils.copyByte(soBytes, 50, 2);
        type32.hdr = hdr;
		System.out.println(hdr.toString());
    }

    /**
     * 读取程序头信息
     * @param soBytes so二进制数据
     */
    public static void parseProgramHeaderList(byte[] soBytes) {
        // 程序头信息偏移地址
        int p_header_offset = ByteUtils.byte2int(type32.hdr.e_phoff);
        // 程序头32个字节
        int header_size = 32;
        // 头部个数
        int header_count = ByteUtils.byte2Short(type32.hdr.e_phnum);
        byte[] des = new byte[header_size];
        for (int i = 0; i < header_count; i++) {
            System.arraycopy(soBytes, i * header_size + p_header_offset, des, 0, header_size);
            type32.phdrList.add(parseProgramHeader(des));
        }
		type32.printPhdrList();
    }

    /**
     * 读取段头信息
     * @param soBytes so二进制数据
     */
    public static void parseSectionHeaderList(byte[] soBytes) {
        // 偏移地址
        int s_header_offset = ByteUtils.byte2int(type32.hdr.e_shoff);
        // 40 个字节
        int header_size = 40;
        // 头部的个数
        int  header_count = ByteUtils.byte2Short(type32.hdr.e_shnum);
        byte[] des = new byte[header_size];
        for (int i = 0; i < header_count; i++) {
            System.arraycopy(soBytes, i * header_size + s_header_offset, des, 0, header_size);
            type32.shdrList.add(parseSectionHeader(des));
        }
		type32.printShdrList();
    }

    /**
     * 读取符号表信息
     * 在Elf表中没有找到SymbolTable的数目，观察Section中的Type=DYNSYM段的信息可以得到，
     * 这个段的大小和偏移地址，而SymbolTable的结构大小是固定的16个字节
     * 数目=大小/结构大小
     * @param soBytes 二进制数据
     */
    public static void parseSymbolTable(byte[] soBytes) {
        // 首先在SectionHeader中查找到dynsym段的信息
        int offset_sym = 0;
        int total_sym = 0;
        for (ElfType32.elf32_shdr shdr : type32.shdrList) {
            if (ByteUtils.byte2int(shdr.sh_type) == ElfType32.SHT_DYNSYM) {
                total_sym = ByteUtils.byte2int(shdr.sh_size);
                offset_sym = ByteUtils.byte2int(shdr.sh_offset);
                break;
            }
        }
        int num_sym = total_sym / 16;
        parseSymbolTableList(soBytes, num_sym, offset_sym);
    }

    /**
     * 在Elf表中没有找到StringTable的数目，观察Section中的Type=STRTAB段的信息，可以得到，这个段的大小和偏移地址，
     * 但是我们这时候我们不知道字符串的大小，所以就获取不到数目了
     * 这里我们可以查看Section结构中的name字段：表示偏移值，那么我们可以通过这个值来获取字符串的大小
     * 可以这么理解：当前段的name值 减去 上一段的name的值 = (上一段的name字符串的长度)
     * @param soBytes 二进制数据
     */
    public static void parseStringTable(byte[] soBytes) {
        int prename_len = 0;
        int[] lens = new int[type32.shdrList.size()];
        int total = 0;
        for (int i = 0; i < type32.shdrList.size(); i++) {
            if (ByteUtils.byte2int(type32.shdrList.get(i).sh_type) == ElfType32.SHT_STRTAB) {
                int curname_offset = ByteUtils.byte2int(type32.shdrList.get(i).sh_name);
                lens[i] = curname_offset - prename_len - 1;
                if (lens[i] < 0) {
                    lens[i] = 0;
                }
                total += lens[i];
                prename_len = curname_offset;
                // 最后一个字符串的长度，需要用总长度减去前面的长度总和来获取到
                if (i == (lens.length - 1)) {
                    lens[i] = ByteUtils.byte2int(type32.shdrList.get(i).sh_size) - total - 1;
                }
            }
        }
        for (int i = 0; i < lens.length; i++) {
			System.out.println("len: " + lens[i]);
        }

    }

    /**
     * 解析程序头
     * @param soBytes
     * @return
     */
    private static ElfType32.elf32_phdr parseProgramHeader(byte[] soBytes) {
        ElfType32.elf32_phdr phdr = new ElfType32.elf32_phdr();
        phdr.p_type = ByteUtils.copyByte(soBytes, 0, 4);
        phdr.p_offset = ByteUtils.copyByte(soBytes, 4, 4);
        phdr.p_vaddr = ByteUtils.copyByte(soBytes, 8, 4);
        phdr.p_paddr = ByteUtils.copyByte(soBytes, 12, 4);
        phdr.p_filesz = ByteUtils.copyByte(soBytes, 16, 4);
        phdr.p_memsz = ByteUtils.copyByte(soBytes, 20, 4);
        phdr.p_flags = ByteUtils.copyByte(soBytes, 24, 4);
        phdr.p_align = ByteUtils.copyByte(soBytes, 28, 4);
        return phdr;
    }

    /**
     * 解析段头信息
     * @param soBytes 二进制数据
     * @return
     */
    private static ElfType32.elf32_shdr parseSectionHeader(byte[] soBytes) {
        ElfType32.elf32_shdr shdr = new ElfType32.elf32_shdr();
        shdr.sh_name = ByteUtils.copyByte(soBytes, 0, 4);
        shdr.sh_type = ByteUtils.copyByte(soBytes, 4, 4);
        shdr.sh_flags = ByteUtils.copyByte(soBytes, 8, 4);
        shdr.sh_addr = ByteUtils.copyByte(soBytes, 12, 4);
        shdr.sh_offset = ByteUtils.copyByte(soBytes, 16, 4);
        shdr.sh_size = ByteUtils.copyByte(soBytes, 20, 4);
        shdr.sh_link = ByteUtils.copyByte(soBytes, 24, 4);
        shdr.sh_info = ByteUtils.copyByte(soBytes, 28, 4);
        shdr.sh_addralign = ByteUtils.copyByte(soBytes, 32, 4);
        shdr.sh_entsize = ByteUtils.copyByte(soBytes, 36, 4);
        return shdr;
    }



    /**
     * 解析Symbol Table 内容
     * @param soBytes 二进制数据
     * @param count 个数 
     * @param offset 偏移地址
     */
    private static void parseSymbolTableList(byte[] soBytes, int count, int offset) {
        // 16个字节
        int header_size = 16;
        byte[] des = new byte[header_size];
        for (int i = 0; i < count; i++) {
            System.arraycopy(soBytes, i * header_size + offset, des, 0, header_size);
            type32.symList.add(parseSymbolTableItem(des));
        }
		type32.printSymList();
    }

    /**
     * 解析Symbol Table
     * @param soBytes 二进制数据
     * @return
     */
    private static ElfType32.elf32_sym parseSymbolTableItem(byte[] soBytes) {
        ElfType32.elf32_sym sym = new ElfType32.elf32_sym();
        sym.st_name = ByteUtils.copyByte(soBytes, 0, 4);
        sym.st_value = ByteUtils.copyByte(soBytes, 4, 4);
        sym.st_size = ByteUtils.copyByte(soBytes, 8, 4);
        sym.st_info = ByteUtils.copyByte(soBytes, 12, 1)[0];
        sym.st_other = ByteUtils.copyByte(soBytes, 13, 1)[0];
        sym.st_shndx = ByteUtils.copyByte(soBytes, 14, 2);
        return sym;
    }

}
