//
// Created by LCX on 2020/6/23.
//
#include <stdio.h>
#include <fcntl.h>  //打开文件
#include <unistd.h>
#include <malloc.h>
#include <string.h>
#include <elf.h>

char* initHeader();
void readHeader(char* pbuff);


char* initHeader()
{
    char *pathname = "../source/libget.so";
    int fd = open(pathname,O_RDONLY);
    if(fd==-1){
        printf("open file faile");
        return "";
    }
    printf("open file success\n");
    //分配文件大小
    /*
    *  SEEK_SET 参数offset 即为新的读写位置.
       SEEK_CUR 以目前的读写位置往后增加offset 个位移量.
       SEEK_END 将读写位置指向文件尾后再增加offset 个位移量. 当whence 值为SEEK_CUR 或
       SEEK_END 时, 参数offet 允许负值的出现.
    *
    * */
    long int end = lseek(fd,0,SEEK_END);
    long int begin = lseek(fd,0,SEEK_SET);
    char* pbuff = malloc(end);
    if(!pbuff)
    {
        printf("分配内存失败\n");
    }
    //初始化分配内存空间
    memset(pbuff,0,end);
    int res = read(fd,pbuff,end);
    if(res == -1)
    {
        printf("读取文件信息失败\n");
        return "";
    }
    return pbuff;
}

void readHeader(char *pbuff)
{
    //printf("Elf Header");
    //Magic
    printf("Magic:   ");
    int i;
    for(i = 0;i<EI_NIDENT;++i)   //e_ident[EI_NIDENT]
    {
        printf("%02X", pbuff[i]);
        putchar(' ');
    }
    printf("\r\n");
    //class
    printf("%-33s:", "Class");
    switch (pbuff[4])
    {
        case 0:
            printf(" Invalid class\r\n");
            break;
        case 1:
            printf("Elf32\r\n");
            break;

        case 2:
            printf("Elf64\r\n");
            break;

        case 3:
            printf("Error\r\n");
            break;
    }
    //Data   大小端
    printf("%-33s:","Data");
    switch(pbuff[5])
    {
        case 0:
            printf("Invalid data encoding\r\n");
            break;
        case 1:
            printf("2's complement, little endian\r\n");
            break;
        case 2:
            printf("2's complement, big endian\r\n");
            break;
        default:
            printf("ERROR\r\n");
            break;
    }
    //Version  默认版本为1
    printf("%-33s:", "Version");
    printf("%d", pbuff[6]);
    printf("%s", " (current)\r\n");
    pbuff += EI_NIDENT;
    //os version
    printf("%-33s: %s\r\n", "OS/ABI", "UNIX - System V");

    //ABI Version
    printf("%-33s: %s\r\n", "ABI Version", "0");

    //Type
    printf("%-33s:", "Type");
    switch(*(uint16_t*)pbuff)
    {
        case 0:
            printf("No file type\r\n");
            break;
        case 1:
            printf("Relocatable file\r\n");
            break;
        case 2:
            printf("Executable file\r\n");
            break;
        case 3:
            printf("Shared object file\r\n");
            break;
        case 4:
            printf("Core file\r\n");
            break;
        default:
            printf("ERROR\r\n");
            break;
    }
    pbuff += sizeof(uint16_t);
    //Machine
    printf("%-33s:", "Machine");
    switch(*(uint16_t*)pbuff)
    {
        case EM_386:
            printf(" Intel 80386\r\n");
            break;
        case EM_ARM:
            printf(" ARM\r\n");
            break;
        case EM_X86_64:
            printf(" AMD X86-64 arrchitecture\r\n");
            break;
        case EM_AARCH64:
            printf(" AARCH64\r\n");
            break;
        default:
            printf(" ERROR\r\n");
            break;
    }
    pbuff += sizeof(uint16_t);
    //Version
    printf("%-33s: %s\r\n", "version", "0X1");
    pbuff += sizeof(uint32_t);
    //入口点位置
    printf("%-33s: 0X%lx\r\n", "Entry point address", *(uint64_t*)pbuff);
    pbuff += sizeof(uint64_t);
    //程序头大小
    printf("%-33s: %lu (bytes into file)\r\n", "Start of program headers", *(uint64_t*)pbuff);
    pbuff += sizeof(uint64_t);
    //区段大小
    printf("%-33s: %lu (bytes into file)\r\n", "Start of section headers", *(uint64_t*)pbuff);
    pbuff += sizeof(uint64_t);
    //Flags
    printf("%-33s: 0X0\r\n", "Flags");
    pbuff += sizeof(Elf32_Word);
    //本节大小
    printf("%-33s: %d (bytes)\r\n", "Size of this header", *(Elf32_Half*)pbuff);
    pbuff += sizeof(Elf32_Half);
    //程序头大小
    printf("%-33s: %d (bytes)\r\n", "Size of program headers", *(Elf32_Half*)pbuff);
    pbuff += sizeof(Elf32_Half);
    //程序头大小
    printf("%-33s: %d\r\n", "Number of program headers", *(Elf32_Half*)pbuff);
    pbuff += sizeof(Elf32_Half);
    //section大小
    printf("%-33s: %d (bytes)\r\n", "Size of section headers", *(Elf32_Half*)pbuff);
    pbuff += sizeof(Elf32_Half);
    //section大小
    printf("%-33s: %d\r\n", "Number of section headers", *(Elf32_Half*)pbuff);
    pbuff += sizeof(Elf32_Half);
    //下标值
    printf("%-33s: %d\r\n", "Section header string table index", *(Elf32_Half*)pbuff);
}

int main()
{
    char* pbuff = initHeader();
    readHeader(pbuff);
    return 0;
}
