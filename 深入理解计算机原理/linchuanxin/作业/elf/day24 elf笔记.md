# day24 elf笔记

![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/1.png)


![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/2.png)

c语言程序的编译图
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/3.png)



权限说明
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/4.png)
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/5.png)



程序头
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/6.png)

![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/7.png)
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/8.png)
load才会加载到内存中 所以是02和03段

![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/9.png)
02段中到.ARM.extab结束 就是节头表中的[12]

节头和程序头的关系，根据Flg来划分
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/10.png)





02段都是以A开头的权限
03段都是以W开头的权限 
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/11.png)
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/12.png)
所以看出 两个load是根据权限来划分


![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/13.png)



思考：
![image.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/14.png)



- 
  elf文件结构
- linux文件
- window pe文件

1. 整体结构：
   elf header-->elf文件头的位置是固定的
   Segment Header Table -->elf程序头的描述是段的相关信息    (段头表)
   .init
   .text
   .rodata
   .data
   .symtab   符号表
   .line
   .strtab 字符串表
   Section Header Table--->elf节头表描述的是节区的信息
   动态用段 静态用节

2. 程序真正运行加载得是段  提高执行效率
   静态分析的时候 看的是节

   -a 当前相关信息
   -h 头
   -l 段
   -S 节头表信息
   -s符号表 相关符号信息
   -r -d -V -A -I暂时不管

3. 三大结构    elfheader  描述程序头和节头的相关信息 以及当前文件相关信息
   头
   节
   程序头（段）

   Magic   魔术
   class   elf32
   ...
   Entry point address  0x854c     偏移是0x854c    对于可执行程序是有意义  对静态和动态链接库没啥意义
   Start of program headers   程序头的偏移  0x52
   Start of section headers    节头表的偏移是8556
   Flags   标识 默认是5
   Size of this headers   头的大小52  byte
   Size of program headers   程序头的大小32   一个程序头的大小是32  byte
   Number of program headers   表象  有多少个程序头  程序头的数量  8个程序头
   Size of section headers   节头表大小是 40 byte   一个是40byte
   Number of section headers    表象  有多少个节头表  节头表的数量  有24个节
   Section header string table index   节头的字符串 节的名称的索引  描述字符串表的索引   23

4. 节头起始偏移是8556 加上每一个节头表的大小是40byte
   即
   8556+40 * 23   得到结果地址指向字符串表---》（Section header string table index    23）

   ----------------》
   Section Headers:    //节头表信息
   [0]  - [23]代表有24个节
   .interp
   .dynsym   动态链接的符号表
   .dynstr    动态链接的字符串
   .hash
   .rel.dyn   动态链接的重定位
   .rel.plt    plt过程连接表的重定位
   .plt
   .text          存放代码     重点掌握
   .note.android.ide
   .ARM.exidx
   .rodata     只读的权限   只读操作
   .ARM.extab
   .fini_array
   .init_array
   .preinit_array
   .dynamic    动态链接的节区
   .got   全局的偏移表
   .data   数据段 可以进行读写操作    全局初始化 都会存放在这里data      重点掌握
   .bss    变量的统一的初始化    未被初始化的变量会放在.bss中  进行统一的初始化     重点掌握
   .comment
   .note.gnu.gold-ve
   .ARM.attributes

5. [23].shstrtab     比较特殊    .sh节头表缩写就是sh     .shstrtab包含所有节的信息和所有节的名称 就在此字符串表中


   c语言编译图


   编译过程 把相关代码编译到不同的位置   （文件结构的某个位置）
   编译关系   源文件的代码通过编译后成为elf文件  里面相关数据会放在哪些区域  比如存在哪些节里面都是有规律的


   重点：
   节头和程序头的映射关系

   程序头信息
   PHDR
   INTERP


   LOAD    程序真正运行加载到内存的只有load段
   LOAD
   DYNAMIC
   GNU_STACK
   EXIDX
   GNU_RELRO


   重要
   文件头
   节头表
   程序头


   节头表和程序头存在映射关系
   既可以说


   段是由节组成的
   只是通过内存映射关系
   把权限一样的映射到一个段里面去   根据flg权限的映射   映射成不同的段

   反过来我们从内存中抠出来的so文件（dump so文件）有哪些段呢？
   那就只有两个段 只有两个LOAD段  其他没有出现 因为本身不会加载内存     idea是内存dump，dump的so文件

[23].shstrtab 包含了所有节的名称  所以非常特殊



elf信息:

![图片.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/22.png)



libget.so文件内容

![图片.png](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/24.png)



解析libget.so文件结果

![image-20200624143322601](https://gitee.com/andylinchuanxin/bookimagenew/raw/master/img/image-20200624143322601.png)