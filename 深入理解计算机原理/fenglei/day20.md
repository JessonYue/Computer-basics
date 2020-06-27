### 第十章

* 输入 / 输出（I/O）是在主存和外部设备（例如磁盘驱动器，终端和网络）之间复制数据的过程。输入操作是从 I/O 设备复制数据到主存，输出操作是从主存复制数据到 I/O 设备

#### Unix I/O

* 所有 I/O 设备（例如网络，磁盘和终端）都被模型化为文件，而所有输入和输出都被当做对响应文件的读和写来执行。I/O 设备映射为文件的方式，允许 Linux 内核引出一个简单，低级的应用接口，称为 Unix I/O，使得所有输入和输出都能以一种统一一致的方式来执行
  * 打开文件：一个应用程序通过要求内核打开相应的文件，来宣告它想要访问一个 I/O 设备。内核返回一个小的非负整数，叫做**描述符**，扫描符在后续对此文件的所有操作中标识这个文件。内核记录有关这个打开文件的所有信息。应用程序只需记住这个扫描符
  * Linux shell 创建的每个进程开始时都有三个打开文件
    * 标准输入（描述符为 0	STDIN_FILENO）
    * 标准输出（描述符为 1    STDOUT_FILENO）
    * 标准错误（描述符为 2    STDERR_FILENO）
  * 改变当前文件的位置。对于每个打开的文件，内核保持着一个文件的位置 k，初始为 0。这个文件位置是从文件头开始的字节偏移量。应用程序能够通过执行 seek 操作，显式的设置文件当前的位置为 k
  * 读写文件。
    * 读操作：一个读操作就是从文件复制到 n > 0 个字节到内存，从当前文件位置 k 开始，然后将 k 增加到 k + n。给定一个大小为 m 字节的文件，当 k >= m 时执行读操作会触发一个称为 end-of-file（EOF）的条件，应用程序能检测到这个条件
    * 写操作：一个写操作就是从内存复制到 n > 0 个字节到一个文件，从当前文件位置 k 开始，然后更新 k
  * 关闭文件：当应用完成了对文件的访问后，通知内核关闭这个文件。作为响应，内核释放文件打开时创建的数据结构，并将这个描述符恢复到可用的描述池中。无论进程因为何种原因终止，内核都会关闭所有打开的文件并释放它们的内存资源

#### 文件

* 每个 Linux 文件都有一个类型（type）来表明它在系统中的角色
  * 普通文件：包含任意数据。应用程序常常区分文本文件和二进制文件
    * 文本文件是只含有 ASCII 或 Unicode 字符的普通文件
    * 二进制文件是所有其他的文件
    * 对内核而言，文本文件和二进制文件没有区别
  * 目录( dircetory )：是包含一组链接的文件，其中每个链接都将一个文件名映射到一个文件，这个文件可能是另一个目录，每个目录至少含有两个条目
    * “.” 是该目录自身的链接
    * “..” 是到目录层次结构中父目录的链接
  * 套接字：socket 用来与另一个进程进行跨网络通信的文件
  * 其他文件类型包括 命名通道，符号链接，以及字符和块设备

* Linux 内核将所有文件都组织成一个目录层次结构，由名为 / 的根目录确定，系统中的每个文件都是根目录的间接和直接的后代

#### 打开和关闭文件

* 进程通过 open 函数打开一个已存在的文件或者创建一个新文件的
  * open 函数将 filename 转换为一个文件描述符，并返回描述符数字，返回的描述符总是在进程中当前没有打开的最小描述符

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
/**
 flags 指明了进程打算如何访问这个文件
 O_RDONLY: 只读
 O_WRONLY: 只写
 O_RDWR:   可读可写
 O_CREAT:  如果文件不存在，就创建一个空文件
 O_TRUNC:  如果文件存在，就截断它
 O_APPEND: 在每次操作前，设置文件位置到文件的结尾处
*/
int open(char *filename,int flags, mode_t mode)
  
  
#include <unsitd.h>
// 进程通过调用 close 函数关闭一个打开的文件
int close(int fd);
```

#### 读和写文件

```c
#include <unsitd.h>
// 读操作 成功则返回读的字节数，若 EOF 为 0，出错为 -1
ssize_t read(int fd,void *buf, size_t n);

// 写操作 成功则为写的字节数，出错为 -1
ssize_t wirte(int fd,const void *buf,size_t n);
```

#### 用 RIO 包健壮的读写

* RIO 包主要处理不足值，主要提供了两类不同的函数
  * 无缓冲的输入和输出函数：这些函数直接在内存和文件之间传送数据，没有应用级缓冲，它们对二进制数据读写到网络和从网络读写二进制数据尤其有用
  * 带缓冲的输入函数：这些函数允许从文件中读取文本行和二进制数据，这些文件的内容缓存在应用级缓冲区内（比如 printf 提供的缓冲区）

##### RIO 无缓冲的输入输出函数

```c
#include "csapp.h" 	// 成功返回传送的字节数， EOF 返回 0，错误返回 -1

ssize_t rio_readn(int fd, void *usrbuf, size_t n):

ssize_t rio_wirten(int fd, void *usrbuf, size_t n);
```

##### RIO 的带缓冲的输入函数

```c
#inculde "csapp.h" 	// 成功返回传送的字节数， EOF 返回 0，错误返回 -1

void rio_readinitb(rio_t *rp, int fd);

ssize_t rio_readlineb(rio_t *rp, void *usrbuf, size_t maxlen);
ssize_t rio_readnb(rio_t *rp, void *usrbuf, size_t h);
```

##### 读取文件元数据

* 应用程序能够调用 stat 和 fstat 函数，检索到关于文件的信息（有时也称为文件的元数据）

```c
#include <unsitd.h>	// 返回：若成功则为 0，若出错则为 -1
#include <sys/stat.h>

int stat(const char *filename, struct stat *buf);
int fstat(int fd, struct stat *buf);
```

##### 读取文件目录

* 应用程序可以用 readdir 系列函数来读取目录的内容

```c
#include <sys/types.h>	// 若成功，则为行处理指针，若出错则为 NULL
#include <dirent.h>

DIR *opendir(const char *name);

#include <dirent.h>
// 若成功 返回指向下一个目录项的指针，若没有更多的目录项或出错则为 NULL
struct dirent *readdir(DIR *dirp);

#include <dirent.h>
// 关闭流并释放所有资源，成功返回 0 出错返回 -1
int closedir(DIR *dirp);
```

#### 共享文件

* 内核用三个相关的数据结构来表示打开文件
  * 描述符表：每个进程都有它独立的描述符表，它的表项是由进程打开文件描述符来索引的。每个打开的描述符表项指向文件表的一个表项
  * 文件表：打开文件的集合是由一张文件表来表示的，虽有进程共享这张表，每个文件表的表项组成包括当前文件的位置，引用计数（当前指向该表项的描述表项数），以及一个指向 v-code 表对应表项的指针
  * v-code 表：同文件表一样，所欲进程共享这张 v-code 表。每个表项包含 stat 结构中的大多数信息，包括 st_mode 和 st_size

#### I/O 重定向

```c
#include <unsitd.h>
// 若成功则为非负的描述符，若出错则为 -1
int dup2(int oldfd, int newfd);
```

