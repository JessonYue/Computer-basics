#include <stdio.h>
#include <stdlib.h>
#include "elf.h"
//
// Created by 吴超 on 2020/6/21.
//
int data;
/**
 * 读取文件标识部分
 * 包括魔数、class类型、大小端、版本号
 * @param file
 * @param e_ident
 */
void ReadIdent(FILE * file,unsigned char e_ident[EI_NIDENT]);
char* getClass(unsigned char class);
char* getData(unsigned char class);
char* getType(Elf64_Half type);
char* getAbi(unsigned char abi);
char* getMachine(Elf64_Half abi);
void PrintIdent(unsigned char e_ident[EI_NIDENT]);
void PrintType(Elf64_Half type);
void PrintMachine(Elf64_Half machine);
void PrintVersion(Elf64_Half version);
void PrintEntryPointAddress(Elf64_Addr entry);
void PrintStartOfProgramHeaders(Elf64_Off startOfProgramHeaders);
void PrintStartOfSectionHeaders(Elf64_Off startOfSectionHeaders);
void PrintFlags(Elf64_Word flags);
void PrintSizeOfHeader(Elf64_Half sizeOfHeader);
void PrintSizeOfProgramHeader(Elf64_Half sizeOfProgramHeader);
void PrintNumberOfProgramHeaders(Elf64_Half numberOfProgramHeaders);
void PrintNumberOfSectionHeaders(Elf64_Half numberOfSectionHeaders);
void PrintSizeOfSectionHeaders(Elf64_Half sizeOfSectionHeaders);
void PrintSectionHeaderStringTableIndex(Elf64_Half sectionHeaderStringTableIndex);
void ReadElfHeader(FILE * file){
    Elf64_Ehdr* elf64Ehdr = malloc(sizeof(Elf64_Ehdr));
    ReadIdent(file,elf64Ehdr->e_ident);
    fread(&elf64Ehdr->e_type,sizeof(elf64Ehdr->e_type),1,file);
    fread(&elf64Ehdr->e_machine,sizeof(elf64Ehdr->e_machine),1,file);
    fread(&elf64Ehdr->e_version,sizeof(elf64Ehdr->e_version),1,file);
    fread(&elf64Ehdr->e_entry,sizeof(elf64Ehdr->e_entry),1,file);
    fread(&elf64Ehdr->e_phoff,sizeof(elf64Ehdr->e_phoff),1,file);
    fread(&elf64Ehdr->e_shoff,sizeof(elf64Ehdr->e_shoff),1,file);
    fread(&elf64Ehdr->e_flags,sizeof(elf64Ehdr->e_flags),1,file);
    fread(&elf64Ehdr->e_ehsize,sizeof(elf64Ehdr->e_ehsize),1,file);
    fread(&elf64Ehdr->e_phentsize,sizeof(elf64Ehdr->e_phentsize),1,file);
    fread(&elf64Ehdr->e_phnum,sizeof(elf64Ehdr->e_phnum),1,file);
    fread(&elf64Ehdr->e_shentsize,sizeof(elf64Ehdr->e_shentsize),1,file);
    fread(&elf64Ehdr->e_shnum,sizeof(elf64Ehdr->e_shnum),1,file);
    fread(&elf64Ehdr->e_shstrndx,sizeof(elf64Ehdr->e_shstrndx),1,file);
    PrintIdent(elf64Ehdr->e_ident);
    PrintType(elf64Ehdr->e_type);
    PrintMachine(elf64Ehdr->e_machine);
    PrintVersion(elf64Ehdr->e_version);
    PrintEntryPointAddress(elf64Ehdr->e_entry);
    PrintStartOfProgramHeaders(elf64Ehdr->e_phoff);
    PrintStartOfSectionHeaders(elf64Ehdr->e_shoff);
    PrintFlags(elf64Ehdr->e_flags);
    PrintSizeOfHeader(elf64Ehdr->e_ehsize);
    PrintSizeOfProgramHeader(elf64Ehdr->e_phentsize);
    PrintNumberOfProgramHeaders(elf64Ehdr->e_phnum);
    PrintSizeOfSectionHeaders(elf64Ehdr->e_shentsize);
    PrintNumberOfSectionHeaders(elf64Ehdr->e_shnum);
    PrintSectionHeaderStringTableIndex(elf64Ehdr->e_shstrndx);
}
void ReadIdent(FILE* file,unsigned char e_ident[EI_NIDENT]){
    fread(e_ident,EI_NIDENT,1,file);
}
void PrintIdent(unsigned char e_ident[EI_NIDENT]){
    int i=0;
    printf("magic number:");
    while (i<4){
        printf(" %02x",e_ident[i]);
        i++;
    }
    printf("\n");
    printf("Class:");
    printf("%s",getClass(e_ident[i++]));
    printf("\n");
    printf("Data:");
    data = e_ident[i];
    printf("%s",getData(e_ident[i++]));
    printf("\n");
    printf("Version:%d (current)\n",e_ident[i++]);
    printf("ABI:%s\n",getAbi(e_ident[i++]));
    printf("ABI Version:%d\n",e_ident[i++]);
}

void PrintType(Elf64_Half type){
    printf("Type:");
    printf("%s\n",getType(type));
}

void PrintMachine(Elf64_Half machine){
    printf("Machine:");
    printf("%s\n",getMachine(machine));
}

void PrintVersion(Elf64_Half version){
    printf("Version:");
    printf("%#x\n",version);
}

void PrintEntryPointAddress(Elf64_Addr entry){
    printf("Entry point address:%#llx\n",entry);
}

void PrintStartOfProgramHeaders(Elf64_Off startOfProgramHeaders){
    printf("Start of program headers:%lld(bytes into file)\n",startOfProgramHeaders);
}
void PrintStartOfSectionHeaders(Elf64_Off startOfSectionHeaders){
    printf("Start of section headers:%lld(bytes into file)\n",startOfSectionHeaders);
}

void PrintFlags(Elf64_Word flags){
    printf("Flags:%#x\n",flags);
}

void PrintSizeOfHeader(Elf64_Half sizeOfHeader){
    printf("Size of this header:%d(bytes)\n",sizeOfHeader);
}

void PrintSizeOfProgramHeader(Elf64_Half sizeOfProgramHeader){
    printf("Size of program headers:%d(bytes)\n",sizeOfProgramHeader);
}

void PrintNumberOfProgramHeaders(Elf64_Half numberOfProgramHeaders){
    printf("Number of program headers:%d\n",numberOfProgramHeaders);
}

void PrintSizeOfSectionHeaders(Elf64_Half sizeOfSectionHeaders){
    printf("Size of section headers:%d(bytes)\n",sizeOfSectionHeaders);
}
void PrintNumberOfSectionHeaders(Elf64_Half numberOfSectionHeaders){
    printf("Number of section headers:%d\n",numberOfSectionHeaders);
}

void PrintSectionHeaderStringTableIndex(Elf64_Half sectionHeaderStringTableIndex){
    printf("Section header string table index:%d\n",sectionHeaderStringTableIndex);
}

char* getClass(unsigned char class){
    if(class==ELFCLASSNONE){
        return "invalid class";
    } else if(class==ELFCLASS32){
        return "ELF 32";
    } else if(class==ELFCLASS64){
        return "ELF 64";
    }
    return "invalid class";
}

char* getData(unsigned char data){
    if(data==0){
        return "invalid data";
    } else if(data==1){
        return "little endian";
    } else if(data==2){
        return "big endian";
    }
    return "invalid data";
}

char* getType(Elf64_Half type){
    if(type==ET_NONE){
        return "No file type";
    } else if(type==ET_REL){
        return "Relocatable file";
    } else if(type==ET_EXEC){
        return "Executable file";
    } else if(type==ET_DYN){
        return "Shared object file";
    } else if(type==ET_CORE){
        return "Core file";
    }
    return "No file type";
}

char* getAbi(unsigned char abi){
    if(abi==ELFOSABI_NONE){
        return "UNIX System V ABI";
    } else if(abi==ELFOSABI_HPUX){
        return "HP-UX";
    } else if(abi==ELFOSABI_NETBSD){
        return "NetBSD";
    } else if(abi==ELFOSABI_SOLARIS){
        return "Sun Solaris.";
    } else if(abi==ELFOSABI_AIX){
        return "IBM AIX.";
    } else if(abi==ELFOSABI_IRIX){
        return "SGI Irix.";
    } else if(abi==ELFOSABI_FREEBSD){
        return "FreeBSD.";
    } else if(abi==ELFOSABI_TRU64){
        return "Compaq TRU64 UNIX.";
    } else if(abi==ELFOSABI_MODESTO){
        return "Novell Modesto.";
    } else if(abi==ELFOSABI_OPENBSD){
        return "OpenBSD.";
    } else if(abi==ELFOSABI_ARM_AEABI){
        return "ARM EABI";
    } else if(abi==ELFOSABI_ARM){
        return "ARM";
    } else if(abi==ELFOSABI_STANDALONE){
        return "Standalone (embedded) application";
    }
    return "";
}

char* getMachine(Elf64_Half machine){
    if(machine == EM_AARCH64){
        return "ARM AARCH64";
    }
    return "";
}

