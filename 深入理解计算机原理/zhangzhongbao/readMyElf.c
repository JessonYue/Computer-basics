#include <stdio.h>
#include <elf.h>
/**
 * 读取elf中elf header部分
 */
int main() {
    FILE *fp;

    int i;

    Elf64_Ehdr elfheader;
    fp = fopen("/home/zhangfeifei/hello.o", "r");
    fread(&elfheader, sizeof(Elf64_Ehdr), 1, fp);

    printf("Magic:");
    for (i = 0; i < 16; i++) {
        printf("%x", elfheader.e_ident[i]);
    }
    printf("\n");
    printf("类别:：%hx\n", elfheader.e_type);
    printf("数据：%hx\n", elfheader.e_machine);
    printf("版本：%x\n（通常为1）", elfheader.e_version);
    printf("入口虚地址：%lx\n", elfheader.e_entry);
    printf("程序头起点：%lx\n", elfheader.e_phoff);
    printf("段表偏移：%lx\n", elfheader.e_shoff);
    printf("标记：%x\n", elfheader.e_flags);
    printf("elf header大小：%hx\n", elfheader.e_ehsize);
    printf("%hx\n", elfheader.e_phentsize);
    printf("Start of section headers:%hx\n", elfheader.e_phnum);
    printf("节头大小：%hx\n", elfheader.e_shentsize);
    printf("节头数量：%d\n", elfheader.e_shnum);
    printf("字符串表索引节头：%d\n", elfheader.e_shstrndx);
    return 0;
}

///*
//typedef struct
//{
//    unsigned char	e_ident[EI_NIDENT];	/* Magic number and other info */
//    Elf64_Half	e_type;			/* Object file type */
//    Elf64_Half	e_machine;		/* Architecture */
//    Elf64_Word	e_version;		/* Object file version */
//    Elf64_Addr	e_entry;		/* Entry point virtual address */
//    Elf64_Off	e_phoff;		/* Program header table file offset */
//    Elf64_Off	e_shoff;		/* Section header table file offset */
//    Elf64_Word	e_flags;		/* Processor-specific flags */
//    Elf64_Half	e_ehsize;		/* ELF header size in bytes */
//    Elf64_Half	e_phentsize;		/* Program header table entry size */
//    Elf64_Half	e_phnum;		/* Program header table entry count */
//    Elf64_Half	e_shentsize;		/* Section header table entry size */
//    Elf64_Half	e_shnum;		/* Section header table entry count */
//    Elf64_Half	e_shstrndx;		/* Section header string table index */
//} Elf64_Ehdr;
//*/
