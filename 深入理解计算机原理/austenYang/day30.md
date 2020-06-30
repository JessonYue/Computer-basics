Elf 文件：

Elf32_Ehdr: File Header 结构

Elf32_Shdr: 段表结构 --- 段描述符 

* Section Address Alignment 段地址对齐 细看点 ：sh_addralign







32 为虚拟地址空间：4G 虚拟内存 ，当在32 为机上，运行占用内存空间大一些的程序，超过 4 G。那么是怎么处理的？

窗口映射？具体是什么？没看明白。



0xC000 0000 - 0xFFFF FFFF : System

0x0000 0000 - 0xBFFF FFFF : User 





