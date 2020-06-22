###  完成Android so文件的解析工具的开发。

1. 读取 so文件

2. 根据 ELF 文件格式  对 so文件进行解析

   

#### ELF Header

readelf 工具

``` shell
readelf 工具 在sdk/ndk/21.0.6113669/toolchains/x86_64-4.9/prebuilt/darwin-x86_64/bin 目录下  将其x86_64-linux-android-readelf执行文件拷贝到工程中  方便使用
```

```shell
x86_64-linux-android-readelf -h libnative-lib.so
ELF Header:
  Magic:   7f 45 4c 46 01 01 01 00 00 00 00 00 00 00 00 00 
  Class:                             ELF32
  Data:                              2's complement, little endian
  Version:                           1 (current)
  OS/ABI:                            UNIX - System V
  ABI Version:                       0
  Type:                              DYN (Shared object file)
  Machine:                           Intel 80386
  Version:                           0x1
  Entry point address:               0x0
  Start of program headers:          52 (bytes into file)
  Start of section headers:          1259560 (bytes into file)
  Flags:                             0x0
  Size of this header:               52 (bytes)
  Size of program headers:           32 (bytes)
  Number of program headers:         8
  Size of section headers:           40 (bytes)
  Number of section headers:         38
  Section header string table index: 37  //字符串表段表中的下标
```



#### Section Headers

> 段表就是Elf32_Shdr的数组（各种表的集合）
> Elf32_Shdr 描述了各种表的信息  比如字符串表、重定位表、符号表、程序段、数据段、哈希表等表。

```shell
 查看 ELF 文件的段头
 x86_64-linux-android-readelf -S libnative-lib.so 
There are 38 section headers, starting at offset 0x133828:

Section Headers:
  [Nr] Name              Type            Addr     Off    Size   ES Flg Lk Inf Al
  [ 0]                   NULL            00000000 000000 000000 00      0   0  0
  [ 1] .note.android.ide NOTE            00000134 000134 000098 00   A  0   0  2
  [ 2] .note.gnu.build-i NOTE            000001cc 0001cc 000024 00   A  0   0  4
  [ 3] .dynsym           DYNSYM          000001f0 0001f0 001a20 10   A  4   1  4
  [ 4] .dynstr           STRTAB          00001c10 001c10 002064 00   A  0   0  1
  [ 5] .gnu.hash         GNU_HASH        00003c74 003c74 000c30 04   A  3   0  4
  [ 6] .hash             HASH            000048a4 0048a4 000aac 04   A  3   0  4
  [ 7] .gnu.version      VERSYM          00005350 005350 000344 02   A  3   0  2
  [ 8] .gnu.version_d    VERDEF          00005694 005694 00001c 00   A  4   1  4
  [ 9] .gnu.version_r    VERNEED         000056b0 0056b0 000040 00   A  4   2  4
  [10] .rel.dyn          REL             000056f0 0056f0 002a98 08   A  3   0  4
  [11] .rel.plt          REL             00008188 008188 000328 08  AI  3  12  4
  [12] .plt              PROGBITS        000084b0 0084b0 000660 04  AX  0   0 16
  [13] .text             PROGBITS        00008b10 008b10 01f3b8 00  AX  0   0 16
  [14] .gcc_except_table PROGBITS        00027ec8 027ec8 0003d4 00   A  0   0  4
  [15] .rodata           PROGBITS        0002829c 02829c 002e18 00   A  0   0  4
  [16] .eh_frame         PROGBITS        0002b0b4 02b0b4 004850 00   A  0   0  4
  [17] .eh_frame_hdr     PROGBITS        0002f904 02f904 000f44 00   A  0   0  4
  [18] .data.rel.ro      PROGBITS        000325d0 0315d0 001704 00  WA  0   0  4
  [19] .fini_array       FINI_ARRAY      00033cd4 032cd4 000008 00  WA  0   0  4
  [20] .dynamic          DYNAMIC         00033cdc 032cdc 000110 08  WA  4   0  4
  [21] .got              PROGBITS        00033dec 032dec 000074 00  WA  0   0  4
  [22] .got.plt          PROGBITS        00033e60 032e60 0001a0 00  WA  0   0  4
  [23] .data             PROGBITS        00034000 033000 000018 00  WA  0   0  8
  [24] .bss              NOBITS          00034040 033040 00034c 00  WA  0   0 64
  [25] .comment          PROGBITS        00000000 033018 0000dc 01  MS  0   0  1
  [26] .debug_str        PROGBITS        00000000 0330f4 02dde2 01  MS  0   0  1
  [27] .debug_abbrev     PROGBITS        00000000 060ed6 004266 00      0   0  1
  [28] .debug_info       PROGBITS        00000000 06513c 051855 00      0   0  1
  [29] .debug_ranges     PROGBITS        00000000 0b6991 0072e0 00      0   0  1
  [30] .debug_macinfo    PROGBITS        00000000 0bdc71 000010 00      0   0  1
  [31] .debug_line       PROGBITS        00000000 0bdc81 01fb13 00      0   0  1
  [32] .debug_loc        PROGBITS        00000000 0dd794 02b42f 00      0   0  1
  [33] .debug_aranges    PROGBITS        00000000 108bc3 000040 00      0   0  1
  [34] .note.gnu.gold-ve NOTE            00000000 108c04 00001c 00      0   0  4
  [35] .symtab           SYMTAB          00000000 108c20 01f090 10     36 7528  4
  [36] .strtab           STRTAB          00000000 127cb0 00b9e4 00      0   0  1
  [37] .shstrtab         STRTAB          00000000 133694 000194 00      0   0  1
Key to Flags:
  W (write), A (alloc), X (execute), M (merge), S (strings), I (info),
  L (link order), O (extra OS processing required), G (group), T (TLS),
  C (compressed), x (unknown), o (OS specific), E (exclude),
  p (processor specific)

```

#### Program Headers

```shell
x86_64-linux-android-readelf -l libnative-lib.so 

Elf file type is DYN (Shared object file)
Entry point 0x0
There are 8 program headers, starting at offset 52

Program Headers:
  Type           Offset   VirtAddr   PhysAddr   FileSiz MemSiz  Flg Align
  PHDR           0x000034 0x00000034 0x00000034 0x00100 0x00100 R   0x4
  LOAD           0x000000 0x00000000 0x00000000 0x30848 0x30848 R E 0x1000
  LOAD           0x0315d0 0x000325d0 0x000325d0 0x01a48 0x01dbc RW  0x1000
  DYNAMIC        0x032cdc 0x00033cdc 0x00033cdc 0x00110 0x00110 RW  0x4
  NOTE           0x000134 0x00000134 0x00000134 0x000bc 0x000bc R   0x4
  GNU_EH_FRAME   0x02f904 0x0002f904 0x0002f904 0x00f44 0x00f44 R   0x4
  GNU_STACK      0x000000 0x00000000 0x00000000 0x00000 0x00000 RW  0x10
  GNU_RELRO      0x0315d0 0x000325d0 0x000325d0 0x01a30 0x01a30 RW  0x4

 Section to Segment mapping:
  Segment Sections...
   00     
   01     .note.android.ident .note.gnu.build-id .dynsym .dynstr .gnu.hash .hash .gnu.version .gnu.version_d .gnu.version_r .rel.dyn .rel.plt .plt .text .gcc_except_table .rodata .eh_frame .eh_frame_hdr 
   02     .data.rel.ro .fini_array .dynamic .got .got.plt .data .bss 
   03     .dynamic 
   04     .note.android.ident .note.gnu.build-id 
   05     .eh_frame_hdr 
   06     
   07     .data.rel.ro .fini_array .dynamic .got .got.plt 
```

#### Symbol table

```shell
x86_64-linux-android-readelf -s libnative-lib.so 

Symbol table '.dynsym' contains 418 entries:
   Num:    Value  Size Type    Bind   Vis      Ndx Name
     0: 00000000     0 NOTYPE  LOCAL  DEFAULT  UND 
     1: 00000000     0 FUNC    GLOBAL DEFAULT  UND __cxa_atexit@LIBC (2)
     2: 00000000     0 FUNC    GLOBAL DEFAULT  UND __cxa_finalize@LIBC (2)
     3: 00000000     0 FUNC    GLOBAL DEFAULT  UND __stack_chk_fail@LIBC (2)
     4: 00000000     0 FUNC    GLOBAL DEFAULT  UND memcpy@LIBC (2)
     5: 00000000     0 FUNC    GLOBAL DEFAULT  UND __memcpy_chk@LIBC (2)
     6: 00000000     0 FUNC    GLOBAL DEFAULT  UND strlen@LIBC (2)
     7: 00000000     0 FUNC    GLOBAL DEFAULT  UND memset@LIBC (2)
......
```

