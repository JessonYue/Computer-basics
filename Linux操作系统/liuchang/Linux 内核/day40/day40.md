														## 								第三章 进程

进程是任何多道程序设计的操作系统中的基本概念。

在 Linux 源码中，常把进程称为任务（task）或线程（thread）。

#### 进程、轻量级进程和线程

进程是程序执行时的一个实例。可以把它看做充分描述程序已经执行到何种程度的数据结构的汇集。

从内核观点看，进程的目的就是担当分配系统资源（CPU 时间、内存等）的实体。

尽管父子进程可以共享含有程序代码的页，但是它们各自有独立的数据拷贝（堆和栈），因此子进程堆一个内存单元的修改堆父进程是不可兼得（反之亦然）。

现在，大部分多线程应用程序都是用 pthread（POSIX thread）库的标准库函数集编写的。

Linux 使用轻量级进程对多线程应用程序提供更好的支持。两个轻量级进程基本上可以共享一些资源，如地址空间、打开文件等。

#### 进程修饰符

进程描述符都是 task_struct 类型机构，他的字段包含了与一个进程相关的所有信息。

主要包括描述符所涵盖的特殊资源：

1、thread_info ：描述进程基本信息

2、mm_struct：指向内存区描述符的指针

3、tty_struct：与进程相关的tty

4、fs_struct：当前目录

5、files_struct:指向文件描述符的指针

6、signal_struct:所接收的信号

#### 进程状态

进程描述符中的 stats 字段描述了进程当前所处的状态。它由一组标志组成，其中每个标志描述一种可能的进程状态。

在 Linux 版本中这些状态都是互斥的，因为只能设置一种状态，其他的标志将被清除。

##### 进程状态：

1、可执行状态（TASK_RUNNING):进程要么在 CPU 上执行，要么准备执行。

2、可中断的等待状态（TASK_INTERRUPIBLE) ：进程被挂起（睡眠），知道某个条件变为真，产生一个硬件中断，释放进程正等待的系统资源，或传递一个信号都是可以唤醒进程的条件（把进程的状态放回到 TASK_RUNNING)。

3、不可中断的等待状态（TASK_UNINTERRUPIBLE)：把信号传递到睡眠进程不能改变它的状态。

4、暂停状态（TASK_STOPPED）：进程的执行被暂停。当进程收到 SIGSTOP、SIGSTP、SIGTTIN 或 SIGTTOU 的信号后，进入暂停状态。

5、跟踪状态（TASK_TRACED）：进程的执行已由 debugger 程序暂停。当一个进程被另一个进程监控时，任何信号都可以把这个进程至于 TASK_TRACED 状态。

6、僵死状态（EXIT_ZOMBIE)：进程的执行被终止，但是父进程还没有发布wait4（）或 waitpid（） 系统条用来返回有关死亡进程的信息。

7、僵死撤消状态（EXIT_DEAD）：最终状态：由于父进程刚发出 wait4（） 或 waitpid（）系统调用，因而进程有系统删除。

state 字段的值通常复制：

 	p-> state = TASK_RUNNING;

内核使用 set_task_state宏:  设置指定进程的状态

内核使用 set_current_state 宏：设置当前线程的状态

#### 标识一个进程

进程和进程描述符之间有非常严格的一一对应关系，这使得用32位进程描述符地址标识进程成为一种方便的方式。进程描述符指针指向这些地址，内核对进程的大部分引用是通过进程描述符指针进行的。

由于循环使用 PID 编号， 内核必须通过管理一个 pidmap-array 位图来表示当前已分配的 PID 号和闲置的 PID 号。

#### 进程描述符处理

内核使用 alloc_thread_info 和 free_thread_info 宏分配和释放存储 thread_info结构和内核栈的内存区。

#### 标识当前进程

thread_info 结构与内核态堆栈之间的紧密结合提供的主要好处：

内核很容易从 esp 寄存器的值获得当前在 CPU 上正在运行进程的 thread_info 结构的地址。

#### 双向链表

Linux 内核定义了 list_head 数据结构， 字段next 和 prev 分别表示通用双向链表向前和向后的指针元素。

![image-20200710170253747](/Users/admin/Library/Application Support/typora-user-images/image-20200710170253747.png)

#### 进程链表

进程处理表把所有进程的描述符连接起来，每个 task_struck 结构都包含一个 list_head 类型的 tasks 字段，这个类型的 prev 和next 字段分别指向前面和后面的task_struct 元素。

init_task 的 task.prev 字段指向链表中最后插入的进程描述符的tasks字段。

SET_LINKS 宏 用于从进程链表中插入一个进程描述符

REMOVE_LINKS宏 用于从进程链表删除一个进程描述符

for_each_process 宏 用于扫描整个进程链表

实例：

![image-20200710192705412](/Users/admin/Library/Application Support/typora-user-images/image-20200710192705412.png)

这个宏是循环控制语句，内核开发者利用它提供循环。这个宏从指向 init_task 的指针开始，把指针移到下一个任务，然后继续，直到又到 init_task 为止。













