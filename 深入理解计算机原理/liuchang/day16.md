​																网络编程

每个网络应用都是基于客户端-服务器模型。一个应用是有一个服务器进程和一个或多个客户端进程组成。

客户端-服务端模型中的基本操作是事务，一个客户端-服务器事务由以下四步组成：

1、当一个客户端需要服务时，它向服务器发送一个请求，发送一个事务。

2、服务器收到请求后，解释它，并以适当的方式操作他的资源。

3、服务器给客户端发送一个响应，并等待下一个请求。

4、客户端收到相应并处理它。

网络只是有一种I/O设备，是数据源和数据接收方。

一个以太网段包括一些电缆和一个叫做集线器的小盒子。

协议必须提供两种基本能力：

1、命名机制：不同的局域网技术有不同和不兼容的方式来为主机分配地址。

2、传送机制：在电缆上编码位和将这些位封装城帧方面，不同的联网技术有不同的和不兼容的方式。

一个IP地址就是一个32位无符号整数。

客户端和服务器使用 socket 函数来创建一个套接字描述符。

客户端通过调用 connect 函数来建立和服务器的连接。

Web 客户端 和服务器之间的交互用的是一个基于文本的应用级协议，叫做HTTP（超文本传输协议）。HTTP是一个简单的协议。

每条有Web服务器返回的内容都是和他管理的某个文件相关联的。这些文件的每一个都有一个唯一的名字，叫做URL。

