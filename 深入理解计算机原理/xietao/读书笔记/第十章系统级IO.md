# 系统级I/O

## 什么是Unix文件

一个 Linux 文件就是一个 m 个字节的序列 Bÿ,,>Bk> ,Bm-1所有的 I/O设备(例如网络、磁盘和终端)都被模型化为文件，而所有的输入和输出都被当 作对相应文件的读和写来执行。

## 怎么操作文件

### 1、打开文件：

一个应用程序通过要求内核打开相应的文件，内核将返回一个非负整数，称为描述符，记录打开文件的所有信息：标准输入（描述符0）、标准输出（描述符1）、标准错误（描述符2）

### 2、改变当前文件位置

内核保持一个文件的位置k，初始为0，表示从文件开始处偏移的字节数。通过seek操作。

### 3、读写文件

读操作就是从文件拷贝n个字节到存储器，如果是从k处开始，就是拷贝k+n为止。文件的大小为m，如果k≥m就会触发（EOF），所有就不需要明确的EOF字符了。写操作就是从存储器拷贝n个字节到文件当前位置k处。

### 4、关闭文件

内核释放打开文件时创建的数据结构，释放所有的存储器资源。

## 用RIO包健壮地读写

一个I/O包，称为RICXRobustI/O, 健壮的 I/O)包它会自动为你处理上文中所述的不足值。在像网络程序这样容易出现不足值的应用中，RIO 包提供了方便、健壮和髙效的 I/O。RIO提供了两类不同的函数:

1、无缓冲的输入输出函数。

2、带缓冲的输入函数。

## 读取文件元数据

文件元数据是指文件本身的一些信息，包含：访问模式、大小和创建时间。

## 共享文件

内核用三个相关的数据结构来表示打开的文件:1、描述附表。2、文件表。3、v-node表。共享文件是指同一个进程的不同表项，通过文件表指向了同一个位置。

## I/O的重定向

Linux shell 提供了 I/O 重定向操作符，允许用户将磁盘文件和标准输人输出联系起 来







