## 								第四章 中断和异常

### 异常

0-“Divide error”：当一个程序时图执行整数倍0除操作时产生。

1-“Debug”：设置 eflags 的 TF 标志时，一条指令或操作数的地址落在一个活动 debug 寄存器的范围之内

2-未用：为非屏蔽中断保留

3-“Breakpoint”：由 int 3 指令 引起

4-“Overflow”（陷阱）：当 eflags 的 OF 标志被设置时，into （检查溢出）指令被执行

5-“Bounds check”（故障）：对于有效地址范围之外的操作符，bound（检查地址边界）指令被执行

6-“Invalid opcode”（故障）：CPU 执行单元检测到一个无效操作码

7-“Divece not available” （故障）：随着 cr0 的 TS 标志被设置，ESCAPE、MMX 或 XMM 指令被执行

8-“Double fault”（异常中止）：正常情况下，当 CPU 正试图为前一个异常调用处理程序时，同时又检测到一个异常，两个异常能被串行地处理。然而，在少数情况下，处理器不能串行的处理它们，因而产生这种异常

9-“Coprocessor segment overrun”（异常中止）：因外部的数学协处理器引起的错误

10-“Invalid TSS”（故障）：CPU 试图让一个上下文切换到有无效的 TSS 的进程

11-“Segment not present”（故障）：引用一个不存在的内存段

12-“Stack segment fault”（故障）：试图超过栈段界限的指令，或者由 ss 标识的段不在内存

13-“General protection”（故障）：违反了80x86 保护模式下的保护规则之一

14-“Page fault”（故障）：寻址的也不在内存，相应的页表项为空，或者违反了一种分页保护机制

15-“Floating point error”（故障）：集成到 CPU 芯片中的浮点单元用信号通知一个错误情形

16-“Alignment check”（故障）：操作数的地址没有被正确的对齐

17-“Machine check”（异常中止）：机器检查机制检测到一个 CPU 错误或总线错误

18-“SIMD floating point exception”（故障）：集成到 CPU 芯片中的 SSE 或 SSE2 单元对浮点操作用信号同志一个错误情形

![image-20200713174637997](/Users/liuchang/Library/Application Support/typora-user-images/image-20200713174637997.png)

### 中断描述符表

中断描述符表（IDT：Interrupt Descriptor Table）是一个系统表，它与每一个中断或异常向量相关联，每一个向量在表中有相应的中断或异常处理程序的入口地址。

IDT 表中的每一项对应一个中断或异常向量，每个向量由8个字节组成。因此最多需要 256*8=2048字节来存放 IDT。

idtr CPU 寄存器使 IDT 可以位于内存的任何地方，它指定 IDT 的线性基地址及其限制（最大长度）。在允许中断之前，必须用 lidt 汇编质量初始化 idtr。

IDT的三种描述符：

1、任务门：当中断信号发生时，必须取代当前进程的那个进程的 TSS 选择符存放在任务门中。

2、中断门：包含段选择符和中断或异常处理程序的段内偏移量。

3、陷阱门：与中断门相似，只是控制权传递到一个适当的段时处理器不修改 IF 标志。

一个中断处理程序即可以抢占其他的中断处理程序，也可以抢占异常处理程序。相反，异常处理程序从不抢占中断处理程序。

### 初始化中断描述符表

内核启用中断前，必须把 IDT 表的初始化地址撞到 idtr 寄存器，并初始化表中的每一个项。

### 中断门、陷阱门及系统门

中断门：用户态的进程不能访问的一个 Intel 中断门。所有的 Linux 中断处理程序都通过中断门激活码并全部限制在内核态。

系统门：用户态的进程可以访问的一个 Intel 陷阱门。它们的向量是 4、5 及 128。

系统中断门：能够被用户态进程访问的 Intel 中断门。与向量 3 相关的异常处理程序是由系统中断门激活的，用户态可以使用汇编语言指令 int3。

陷阱门：用户态的进程不能访问的一个 Intel 陷阱门。大部分 Linux 异常的处理程序都通过陷阱门来激活。

任务门：不能被用户态进程访问的 Intel 任务门。

![image-20200713184026667](/Users/liuchang/Library/Application Support/typora-user-images/image-20200713184026667.png)

![image-20200713184035138](/Users/liuchang/Library/Application Support/typora-user-images/image-20200713184035138.png)

### 异常处理

CPU 产生的大部分异常都有 Linux 解释为出错条件。

异常处理程序有一个标准的结构，由以下三部分组成：

1、在内核堆栈中保存大多数寄存器的内容。

2、用高级 C 函数处理异常。

3、通过 ret_from_exception（） 函数从异常处理程序退出。

### 进入和离开异常处理程序

为了避免硬盘上的数据崩溃，处理程序调用 die（）函数，该函数在控制台上打印出说有 CPU 寄存器的内容，并调用 do_exit（）来终止当前进程。

### 中断处理

中断处理依赖于中断类型，主要有三类中断类型：

1、I/O中断：某些I/O 设备需要关注，相应的中断处理程序必须查询设备以确定适当的操作过程。

2、时钟中断：某种时钟产生一个中断，这种中断告诉内核一个固定的时间间隔已经过去了。这些中断大部分事作为 I/O 中断来处理的。

3、处理器间中断：多处理器系统中一个 CPU 对另一个 CPU 发出一个中断。

### I/O 中断处理

I/O 中断处理程序必须足够灵活以给多个设备同时提供服务。

中断处理程序的灵活性是以两种不同方式实现：

1、IRQ 共享：中断处理程序多个中断服务例程。每个 ISR 是一个与单独设备相关的函数。因为不可能预先知道哪个特定的设备产生 IRQ，因此，每个ISR 都被执行，以证明它的设备是否需要关注，如果是，当设备产生中断时，就需要执行的所有操作。

2、IRQ 动态分配：一条 IRQ 线在可能的最后时刻才与一个设备驱动程序相关联。

中断处理程序是代表进程执行的，它所代表的进程必须总处于 TASK_RUNNING 状态，否则，就可能出现系统僵死情形。

Linux 把紧随中断要执行的操作:

紧急地（Critical）：如对 PIC 应答中断，对 PIC 或设备控制器重编程，或者修改有设备和处理器同时访问的数据结构。

非紧急的（Noncritical）：如修改那些只有处理器才会访问的数据结构。

非紧急可延迟的（Noncritical deferrable）如把缓冲区的内容拷贝到某个进程的地址空间。

所有的 I/O 中断处理程序都执行四个相同的基本操作：

1、在内核态堆栈中保存 IRQ 的值和寄存器的内容

2、为正在给 IRQ 线服务的 PIC 发送一个应答，浙江允许 PIC 进一步发出中断

3、执行共享这个 IRQ 的所有设备的中断服务例程（ISR）

4、跳到 ret_from_intr（）的地址后终止

![image-20200714105647047](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714105647047.png)

![image-20200714105706763](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714105706763.png)

### IRQ 数据结构

![image-20200714110035338](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714110035338.png)

![image-20200714110056513](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714110056513.png)

![image-20200714114300237](/Users/liuchang/Library/Application Support/typora-user-images/image-20200714114300237.png)

_ _do_IRQ() 函数：__do_IRQ()函数接受IRQ 号（通过 eax 寄存器）和指向 pt_regs 结构的指针作为参数。









