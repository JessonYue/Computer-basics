## 											第五章 内核同步

### 信号量

Linux 提供两种信号量：

1、内核信号量，由内核控制路径使用

2、System V IPC 信号量，由用户态进程使用

内核信号量是 struct_semaphore 类型的对象，包含字段：

1、count ：存放 atomic_t 类型的一个值。如果值大于0，那么资源表示空闲，也就是说，这个资源可以使用。相反，如果 count 等于 0，那么信号量是忙的，但没有进程等待这个被保护的资源。如果 count 为负数，则资源不可用，至少有一个进程等待资源。

2、wait ：存放等待队列链表地址，当前等待资源的所有睡眠进程都放在这个链表中。

3、sleepers：存放一个标志，表示是否有一个进程在信号量上睡眠。

### 对内核数据结构的同步访问

系统中的并发度又取决于两个主要因素：

1、同时运转的 I/O 设备数

2、进行有效工作的 CPU 数

### 在自旋锁、信号量及中断禁止之间的选择

![image-20200725234541470](https://tva1.sinaimg.cn/large/007S8ZIlgy1gh3ng2e1ucj30wo08wwgo.jpg)

![image-20200725234623884](https://tva1.sinaimg.cn/large/007S8ZIlgy1gh3ngbkiuvj30wo07gacd.jpg)

### ![image-20200726172727613](https://tva1.sinaimg.cn/large/007S8ZIlgy1gh4i4ezp7aj30u00vj13r.jpg)

![image-20200726172859164](https://tva1.sinaimg.cn/large/007S8ZIlgy1gh4i5xpk0pj30wu07ojsq.jpg)

### 避免竞争条件的实例

### 引用计数器

引用计数器广泛地用在内核中以避免由于资源的并发分配和释放而产生的竞争条件。













