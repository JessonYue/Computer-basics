# ELF 理解
## 1、ELF 文件头部
elf.c 中的定义：

	/*
	 * ELF header.
	 */
	typedef struct {
		...
	} Elf32_Ehdr;

	typedef struct {
		unsigned char	e_ident[EI_NIDENT];	/* File identification. */
		Elf64_Half	e_type;		/* File type. */
		Elf64_Half	e_machine;	/* Machine architecture. */
		Elf64_Word	e_version;	/* ELF format version. */
		Elf64_Addr	e_entry;	/* Entry point. */
		Elf64_Off	e_phoff;	/* Program header file offset. */
		Elf64_Off	e_shoff;	/* Section header file offset. */
		Elf64_Word	e_flags;	/* Architecture-specific flags. */
		Elf64_Half	e_ehsize;	/* Size of ELF header in bytes. */
		Elf64_Half	e_phentsize;	/* Size of program header entry. */
		Elf64_Half	e_phnum;	/* Number of program header entries. */
		Elf64_Half	e_shentsize;	/* Size of section header entry. */
		Elf64_Half	e_shnum;	/* Number of section header entries. */
		Elf64_Half	e_shstrndx;	/* Section name strings section. */
	} Elf64_Ehdr;

Program header 再 elf.h 中的定义

	/*
	 * Program header.
	 */
	typedef struct {
		...
	} Elf32_Phdr;

	typedef struct {
		Elf64_Word	p_type;		/* Entry type. */
		Elf64_Word	p_flags;	/* Access permission flags. */
		Elf64_Off	p_offset;	/* File offset of contents. */
		Elf64_Addr	p_vaddr;	/* Virtual address (not used). */
		Elf64_Addr	p_paddr;	/* Physical address. */
		Elf64_Size	p_filesz;	/* Size of contents in file. */
		Elf64_Size	p_memsz;	/* Size of contents in memory. */
		Elf64_Size	p_align;	/* Alignment in memory and file. */
	} Elf64_Phdr;



