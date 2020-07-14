## 										第四章 中断

### 工作队列

可延迟函数和工作队列非常相似，但是它们的区别还是很大的。主要区别在于：可延迟函数运行在中断上下文中，而工作队列中的函数运行在进程上下文中。执行可阻塞函数的唯一方式是在进程上下文中运行。

在中断上下文中不可能发生进程切换。可延迟函数和工作队列中的函数都不能访问进程的用户态地址空间。事实上，可延迟函数被执行时不可能有任何正在运行的进程。另一方面，工作队列中的函数是由内核线程来执行的，因此，根本不存在他要访问的用户态地址空间。

#### 工作队列的数据结构

与工作队列县骨干的主要数据结构名为 workqueue_struct 的描述符，它包括一个 NR_CPUS 个元素的数组 ，NR_CPUS 是系统中 CPU 的最大数量。

![image-20200714232241366](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714232241366.png)

![image-20200714232605984](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714232605984.png)

#### 工作队列函数

queue_work()主要执行步骤：

1、检查要插入的函数是否已在工作队列中（work->pending 字段等于1），如果是就结束。

2、把 work_struct 描述符驾到工作队列链表中，然后把 work->pending 置为1。

3、如果工作者线程在本地 CPU 的 cpu_workqueue_struct 描述符的 more_work 等地啊队列上水面，该函数唤醒这个线程。

queue_delayed_work（）函数多接收一个以系统滴答数来表示时间延迟的参数，它用于确保挂起函数在执行前的等待时间尽可能短。

#### 预定义工作队列

![image-20200714234430610](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714234430610.png)

### 从中断和异常返回

![image-20200714235049033](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714235049033.png)

![image-20200714235101638](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714235101638.png)



