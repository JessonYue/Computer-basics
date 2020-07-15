## 									第五章 内核同步

你可以把内核看作是不断对秦秋进行相应的服务器，这些请求可能来自在 CPU 上执行的进程，也可能来自发出中断请求的外部设备。

### 内核抢占

内核抢占的主要特点就是：内核态运行的进程，可能在执行内核函数期间被另一个进程取代。

使内核可抢占的目的是减少用户态进程的分配延迟，即从进程变为可执行状态到它时间开始之间的时间间隔。

字段的编码对应三个不同的计数器，在以下三种情况会大于0:

1、内核正在执行中断服务例程。

2、可延迟函数被禁止。

3、通过把抢占计数器设置为正数而显式地禁用内核抢占。

![image-20200715181339484](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715181339484.png)

![image-20200715181352262](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715181352262.png)

### 同步原语

![image-20200715182820268](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715182820268.png)

#### 每 CPU 变量

每 CPU 变量主要是数据结构的数组，系统的每个 CPU 对应数组的一个元素。

每 CPU 的数组元素在主存中被排列以使每个数据结构存放在硬件高速缓存的不同行，因此对每 CPU 数组的并发访问不会导致高速缓存行饿窃用和失效。

在单处理器和多处理器系统中，内核抢占都可能使每 CPU 变量产生竞争条件。总的原则是内核控制历经应该在禁用抢占的情况下访问每 CPU 变量。

![image-20200715201153582](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715201153582.png)

### 原子操作

![image-20200715202402801](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715202402801.png)

![image-20200715202417327](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715202417327.png)

![image-20200715202427639](/Users/liuchang/Library/Application Support/typora-user-images/image-20200715202427639.png)





