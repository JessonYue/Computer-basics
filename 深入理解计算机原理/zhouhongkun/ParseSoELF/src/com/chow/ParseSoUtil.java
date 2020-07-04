package com.chow;


public class ParseSoUtil {
	private static ElfType elfType = new ElfType();
	
	/**
	 * 解析头部内容
	 * @param soBytes so二进制数据
	 */
	public static void parseHeader(byte[] soBytes) {
		ElfType.elf32_hdr hdr = new ElfType.elf32_hdr();
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
		elfType.hdr = hdr;
		System.out.println(hdr.toString());
	}

	/**
	 * 读取程序头信息
	 * @param soBytes so二进制数据
	 */
	public static void parseProgramHeaderList(byte[] soBytes) {
		// 程序头信息偏移地址
		int p_header_offset = Util.byte2int(elfType.hdr.e_phoff);
		// 程序头32个字节
		int header_size = 32;
		// 头部个数
		int header_count = Util.byte2Short(elfType.hdr.e_phnum);
		byte[] des = new byte[header_size];
		for (int i = 0; i < header_count; i++) {
			System.arraycopy(soBytes, i * header_size + p_header_offset, des, 0, header_size);
			elfType.phdrList.add(parseProgramHeader(des));
		}
		elfType.printPhdrList();
	}

	/**
	 * 读取段头信息
	 * @param soBytes so二进制数据
	 */
	public static void parseSectionHeaderList(byte[] soBytes) {
		// 偏移地址
		int s_header_offset = Util.byte2int(elfType.hdr.e_shoff);
		// 40 个字节
		int header_size = 40;
		// 头部的个数
		int  header_count = Util.byte2Short(elfType.hdr.e_shnum);
		byte[] des = new byte[header_size];
		for (int i = 0; i < header_count; i++) {
			System.arraycopy(soBytes, i * header_size + s_header_offset, des, 0, header_size);
			elfType.shdrList.add(parseSectionHeader(des));
		}
		elfType.printShdrList();
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
		for (ElfType.elf32_shdr shdr : elfType.shdrList) {
			if (Util.byte2int(shdr.sh_type) == ElfType.SHT_DYNSYM) {
				total_sym = Util.byte2int(shdr.sh_size);
				offset_sym = Util.byte2int(shdr.sh_offset);
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
		int[] lens = new int[elfType.shdrList.size()];
		int total = 0;
		for (int i = 0; i < elfType.shdrList.size(); i++) {
			if (Util.byte2int(elfType.shdrList.get(i).sh_type) == ElfType.SHT_STRTAB) {
				int curname_offset = Util.byte2int(elfType.shdrList.get(i).sh_name);
				lens[i] = curname_offset - prename_len - 1;
				if (lens[i] < 0) {
					lens[i] = 0;
				}
				total += lens[i];
				prename_len = curname_offset;
				// 最后一个字符串的长度，需要用总长度减去前面的长度总和来获取到
				if (i == (lens.length - 1)) {
					lens[i] = Util.byte2int(elfType.shdrList.get(i).sh_size) - total - 1;
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
	private static ElfType.elf32_phdr parseProgramHeader(byte[] soBytes) {
		ElfType.elf32_phdr phdr = new ElfType.elf32_phdr();
		phdr.p_type = Util.copyByte(soBytes, 0, 4);
		phdr.p_offset = Util.copyByte(soBytes, 4, 4);
		phdr.p_vaddr = Util.copyByte(soBytes, 8, 4);
		phdr.p_paddr = Util.copyByte(soBytes, 12, 4);
		phdr.p_filesz = Util.copyByte(soBytes, 16, 4);
		phdr.p_memsz = Util.copyByte(soBytes, 20, 4);
		phdr.p_flags = Util.copyByte(soBytes, 24, 4);
		phdr.p_align = Util.copyByte(soBytes, 28, 4);
		return phdr;
	}

	/**
	 * 解析段头信息
	 * @param soBytes 二进制数据
	 * @return
	 */
	private static ElfType.elf32_shdr parseSectionHeader(byte[] soBytes) {
		ElfType.elf32_shdr shdr = new ElfType.elf32_shdr();
		shdr.sh_name = Util.copyByte(soBytes, 0, 4);
		shdr.sh_type = Util.copyByte(soBytes, 4, 4);
		shdr.sh_flags = Util.copyByte(soBytes, 8, 4);
		shdr.sh_addr = Util.copyByte(soBytes, 12, 4);
		shdr.sh_offset = Util.copyByte(soBytes, 16, 4);
		shdr.sh_size = Util.copyByte(soBytes, 20, 4);
		shdr.sh_link = Util.copyByte(soBytes, 24, 4);
		shdr.sh_info = Util.copyByte(soBytes, 28, 4);
		shdr.sh_addralign = Util.copyByte(soBytes, 32, 4);
		shdr.sh_entsize = Util.copyByte(soBytes, 36, 4);
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
			elfType.symList.add(parseSymbolTableItem(des));
		}
//		type32.printSymList();
	}

	/**
	 * 解析Symbol Table
	 * @param soBytes 二进制数据
	 * @return
	 */
	private static ElfType.elf32_sym parseSymbolTableItem(byte[] soBytes) {
		ElfType.elf32_sym sym = new ElfType.elf32_sym();
		sym.st_name = Util.copyByte(soBytes, 0, 4);
		sym.st_value = Util.copyByte(soBytes, 4, 4);
		sym.st_size = Util.copyByte(soBytes, 8, 4);
		sym.st_info = Util.copyByte(soBytes, 12, 1)[0];
		sym.st_other = Util.copyByte(soBytes, 13, 1)[0];
		sym.st_shndx = Util.copyByte(soBytes, 14, 2);
		return sym;
	}

	
}






















