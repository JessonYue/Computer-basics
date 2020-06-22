### 第十一章 网络编程

#### 客户端-服务器编程模型

* 客户端-服务器模型中的基本操作是事务。一个客户端-服务器事务由以下四步组成
  * 当一个客户端需要服务时，它就向服务器发送一个请求，发起一个事务
  * 服务器接收到请求后，解释它，并也适当的方式操作它的资源
  * 服务器给客户端发送一个响应，并等待下一个请求。
  * 客户端收到响应处理它

* 客户端-服务端事务不是数据库事务，没有数据库事务的任何特性，事务仅仅是客户端和服务器执行一些列的步骤

#### 网络

* 互联网通过运行在每台主机和路由器上的协议软件解决不兼容的问题，这种协议必须提供两种基本能力
  * 命名机制。不同的局域网技术有不同和不兼容的方式来为主机分配地址。物联网络协议通过定义一种一致的主机地址格式消除了这些差异。每台主机会被分配至少一个这种互联网地址，这个地址唯一的标识了这台主机
  * 传送机制。在电缆上编码和将这些位封装成帧方面，不同的互联网技术有不同的和不兼容的方式。互联网协议通过定义一种把数据位捆扎成不连续的片（称为包）的统一方式，从而消除了这些差异。

#### 全球 IP 因特网

* 每台因特网主机都运行实现 TCP/IP 协议的软件
* TCP/IP 是一个协议簇，其中每一个都提供不同的功能。
  * 例如 IP 协议提供基本的命名方法和递送机制，这种递送机制能够从一台因特网主机往其他主机发送包，也叫做数据报
  * IP 机制从某种意义上来说是不可靠的，因为，如果数据报在网络中丢失或者重复，它不会试图修复
  * UDP 稍微扩展了 IP 协议，提供了进程间可靠的全双工（双向的）连接

##### IP 地址

* 一个 IP 地址就是一个 32 位的无符号整数
* TCP/IP 为任意整数数据项定义了统一的 网络字节顺序（大端字节序），即使主机字节顺序是小端法。

```c
#include <arpa/inet.h> 
// 网络字节序和主机字节序的转换函数

// 按照网络字节序的值
uint32_t htonl(uint32_t hostlang);
uint16_t htons(uint16_t hostshort);
// 按照注解字节序的值
uint32_t ntohl(uint32_t netlong);
uint16_t ntohs(uint16_t netshort);
```

##### 因特网域名

* 域名是一串用局点分隔的单词，代替 IP 地址容易记忆

##### 因特网连接

* 一个套接字是发起连接的一个端点。每个套接字都有相应的套接字地址，是由一个因特网地址和一个 16 位的整数端口组成的，用 地址：端口 来表示
* 客户端发起一个连接请求时，客户端套接字地址中的端口是由内核自动分配的，称为临时端口
* 服务器套接字地址中的端口通常是某个知名端口，是和这个服务相对应的（比如：web 服务器端口 80，email 服务器端口 25）
* 每个具有知名端口的服务都有一个对应的知名服务名（例如：web 服务名字是 http，email名字是 smtp）
* 一个连接是由它两端的套接字地址唯一确定的，这对套接字地址叫做套接字对

#### 套接字接口

<img src="/Users/fenglei/Documents/learning/day2/img/1592717704423.jpg" alt="1592717704423" style="zoom:50%;" />

##### 套接字地址结构

* 从 Linux 内核的角度来看，一个套接字就是通信的一个端点。从 Linux 程序的角度来看，套接字就是一个有相应描述符的打开文件

```c
// 因特网套接字地址结构
struct sockaddr_in {
  uint16_t	sin_family;		// AF_INET
  uint16_t	sin_port;			// 16 位的端口号	 网络字节顺序（大端法存放）
  struct in_addr	sin_addr;	//  32 位的 IP 地址 （大端法存放）
  unsigned char	sin_zero[8];	//  struct sockaddr
}

// 通用套接字地址结构
struct sockaddr {
  uint16_t	sa_famliy;  // AF_INET
  char 			sa_data[14]	// 数据
}
```

##### socket 函数

* 客户端和服务器端使用 socket 函数来创建一个套接字描述符

```c
#include <sys/types.h>
#include <sys/socket.h>
/**
	使用方法 clientfd = socket(AN_INET, SOCK_STREAM, 0);
	AN_INET 表示我们正在使用 32 位 IP 地址
	SOCK_STREAM 表示这个套接字是连接的一个端点
	成功返回 非负描述符，出错返回 -1
*/
int socket(int domain, int type, int protocol);
```

##### connect 函数

* 客户端通过调用 connect 函数来建立和服务器的连接

```c
#include <sys/socket.h>
// connect 函数试图与套接字地址为 addr 的服务器建立连接，addrlen 是 sizeof(sockaddr_in)
// connect 函数会阻塞，一直到连接成功或失败，如果连接成功 clientfd 已经准备好可以读写
// 成功返回 0，失败返回 -1
int connect(int clientfd, const struct sockaddr *addr, socklen_t addrlen);
```

##### bind 函数

* bind 函数告诉内核将 addr 中的服务器套接字地址和套接字描述符 sockfd 联系起来

```c
#include <sys/socket.h>

int bind(int sockfd, const struct sockaddr *addr, socklen_t addrlen);
```

##### listen 函数

* listen 函数将 sockfd 从一个主动套接字转化为监听套接字，该套接字可以接受来自客户端的连接请求

```c
#include <sys/socket.h>
// backlog 要求对 TCP/IP 协议理解，通常会把它设置为一个较大的值，比如 1024
int listen(int sockfd, int backlog);
```

##### accept 函数

* 服务器通过调用 accept 函数来等待来自客户端的请求，accept 函数等待来自客户端的连接请求到达侦听描述符 listenfd，然后在 addr 中填写客户端的套接字地址，并返回一个已连接的描述符，这个描述符可被用来利用 Unix I/O 函数与客户端通信

```c
#include <sys/socket.h>
// 若成功则为非负连接描述符，若出错则为 -1
int accept(int listenfd, struct sockaddr *addr, int *addrlen);
```

##### 主机和服务转换

* getaddrinfo 函数将主机名，主机地址，服务名和端口号的字符串表示转化成套接字地址结构。是个可重入的函数，适用任何协议

```c
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>

/**
	给定 host 和 service (套接字地址的两个组成部分), getaddrinfo 返回 result
	result 指向 addrinfo 结构的链表
	每个结构指向一个对应 host 和 service 的套接字地址结构
	如果成功返回 0，如果错误返回错误代码
*/
int getaddrinfo(const char *host, const char *service, const struct addrinfo *hints, struct addrinfo **result);
// 为了避免内存泄漏，程序最后必须调用 freeaddrinfo 释放链表
void freeaddrinfo(struct addrinfor *result);
// 如果返回错误代码 可以调用 gai_strerror 转成消息字符串
const char *gai_strerror(int errcode):
```

* getnameinfo 函数和 getaddrinfo 是相反的，将一个套接字地址结构转换成相应的主机和服务器名字符串。

```c
#include <sys/socket.h>
#include <netdb.h>
/**
  sa: 指向大小为 salen 的套接字地址结构
  host: 指向大小为 hostlen 字节的缓冲区
  service: 指向大小为 servlen 字节的缓冲区
  如果成功返回 0，如果错误返回错误代码
*/
int getnameinfo(const struct sockaddr *sa, socklen_t salen, char *host, 
                size_t hostlen, char *service, size_t servlen, int flags);
```

##### 套接字接口的辅助函数

* open_clientfd 和 open_listenfd 是 get nameinfo 函数和套接字接口的包装函数

```c
#include "caspp.h"
// 建立服务器连接，若成功返回描述符，若出错返回 -1
int open_clientfd(char *hostname, char *port);
// 创建一个监听描述符，准备好接受连接请求，若成功返回描述符，若出错返回 -1
int open_listenfd(char *port);
```

