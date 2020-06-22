

### 8.5信号   

pending位向量

POSIX.1定义了数据类型sigset_t来表示或保存多个信号——信号集（signal set），信号掩码就存放在这些信号集中。POSIX定义了下列5个处理信号集的函数。

int sigemptyset(sigset_t *set) 初始化由set指向的信号集，清除其中所有信号，即设置所有信号掩码的阻塞标志

int sigfillset(sigset_t *set) 初始化由set指向的信号集，使其包括所有信号，即清除所有信号掩码的阻塞标志

int sigaddset(sigset_t *set, int signo) 将一个特定信号signo添加到set指向的信号集中，可用于增加个别信号阻塞

int sigdelset(sigset_t *set, int signo) 从set指向的信号集中删除一个特定信号signo，可用于删除个别信号阻塞

int sigismember(const sigset_t *set, int signo) 检查某个信号是否在set指向的信号集中，可确定特定的信号是否在掩码中被标志为阻塞。

另外，进程也可以调用sigprocmask()来检测和更改其当前的信号掩码，以便告诉内核不允许触发该信号集中的信号。其实现代码在kernel/signal.c中，原型为：

int sigprocmask(int how, const sigset_t *set, sigset_t *oset)

首先，若oset是非空指针，那么进程的当前信号掩码通过oset返回。

其次，若set是一个指向信号集的非空指针，则参数how指示如何修改当前进程的信号掩码