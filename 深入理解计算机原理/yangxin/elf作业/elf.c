//
// Created by 12 on 2020/6/22.
//

#include <stdio.h>
#include <malloc.h>
#include "elf.h"

int main() {
    char *path = "C:/Users/12/Desktop/libtest.so";

    FILE *fastfp = fopen(path, "r");
    if (fastfp == NULL) {
        printf("打开文件失败");
        return 0;
    }
    int buf_size = sizeof(Elf64_Ehdr);
    Elf64_Ehdr *elf_Head = malloc(buf_size);
    fread(elf_Head, buf_size, 1, fastfp);
    printf("=====ELF Head=====\n");
    printf("e_ident ");
    printf("%x", elf_Head->e_ident[0]);
    printf("%c", elf_Head->e_ident[1]);
    printf("%c", elf_Head->e_ident[2]);
    printf("%c", elf_Head->e_ident[3]);
    for (int i = 4; i < EI_NIDENT; i++) {
        printf("%x", elf_Head->e_ident[i]);
    }
    printf("\n");
    printf("e_type %d\n", elf_Head->e_type);
    printf("e_machine %d\n", elf_Head->e_machine);
    printf("e_version %d\n", elf_Head->e_version);
    printf("e_entry %d\n", elf_Head->e_entry);
    printf("e_phoff %d\n", elf_Head->e_phoff);
    printf("e_shoff %d\n", elf_Head->e_shoff);
    printf("e_flags %d\n", elf_Head->e_flags);
    printf("e_ehsize %d\n", elf_Head->e_ehsize);
    printf("e_phnum %d\n", elf_Head->e_phnum);
    printf("e_shentsize %d\n", elf_Head->e_shentsize);
    printf("e_shnum %d\n", elf_Head->e_shnum);
    printf("e_shstrndx %d\n", elf_Head->e_shstrndx);
//这个so有28个，第一个没内容，第二个有就读取2个内容
    Elf64_Shdr elf_Shdr[elf_Head->e_shnum];
//读取28个节区内容
    fseek(fastfp, elf_Head->e_shoff, SEEK_SET);
    fread(elf_Shdr, elf_Head->e_shentsize, elf_Head->e_shnum, fastfp);
    //简单打印第二节区内容
    printf("\nElf64_Shdr_2 %x\n", elf_Shdr[1].sh_name);
    printf("Elf64_Shdr_2 %x\n", elf_Shdr[1].sh_type);
    fclose(fastfp);
    free(elf_Head);


    return 0;
}