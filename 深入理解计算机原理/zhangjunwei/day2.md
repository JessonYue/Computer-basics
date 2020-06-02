第三章

- 程序计数器 PC  -->  %rip
  - 将要执行的下一条指令在内存中的地址
- 整数寄存器文件包含16个命名的位置，分别存储64位的值。存储地址（C 语言的指针）、整数数据、记录某些重要程序状态、保存临时数据（过程的参数、局部变量、函数返回值）
- 条件码寄存器  保存最近执行的算术或逻辑指令的状态信息。用来实现控制或数据流中的条件变化。比如用来实现 if和 while 语句。
- 一组向量窥阴器  可以存放一个或多个整数或浮点数值。

```c
//mstore.c
long mult2(long, long );
void multstore(long x,long y, long *dest){
    long t = mult2(x,y);
    *dest = t;
}
```

```c
//main.c
#include <stdio.h>
void multstore(long x, long y, long *dest);

int main() {
    long d;
    multstore(2, 3, &d);
    printf("2 * 3 ---> %ld\n",d);
    return 0;
}
void multstore(long x, long y, long *dest) {
    long t = mult2(x, y);
    *dest = t;
}
long mult2(long a, long b) {
    long s = a * b;
    return s;
}
```

gcc -Og -o prog main.c mstore.c  生成 prog 可执行文件

 objdump -d prog  反汇编

```asm
prog:	file format Mach-O 64-bit x86-64

Disassembly of section __TEXT,__text:
__text:
100000f10:	55 	pushq	%rbp
100000f11:	48 89 e5 	movq	%rsp, %rbp
100000f14:	48 83 ec 10 	subq	$16, %rsp
100000f18:	48 8d 55 f8 	leaq	-8(%rbp), %rdx
100000f1c:	bf 02 00 00 00 	movl	$2, %edi
100000f21:	be 03 00 00 00 	movl	$3, %esi
100000f26:	e8 35 00 00 00 	callq	53 <_multstore>
100000f2b:	48 8b 75 f8 	movq	-8(%rbp), %rsi
100000f2f:	48 8d 3d 64 00 00 00 	leaq	100(%rip), %rdi
100000f36:	31 c0 	xorl	%eax, %eax
100000f38:	e8 3b 00 00 00 	callq	59 <dyld_stub_binder+0x100000f78>
100000f3d:	31 c0 	xorl	%eax, %eax
100000f3f:	48 83 c4 10 	addq	$16, %rsp
100000f43:	5d 	popq	%rbp
100000f44:	c3 	retq
100000f45:	66 2e 0f 1f 84 00 00 00 00 00 	nopw	%cs:(%rax,%rax)
100000f4f:	90 	nop
100000f50:	55 	pushq	%rbp
100000f51:	48 89 e5 	movq	%rsp, %rbp
100000f54:	48 89 f8 	movq	%rdi, %rax
100000f57:	48 0f af c6 	imulq	%rsi, %rax
100000f5b:	5d 	popq	%rbp
100000f5c:	c3 	retq
100000f5d:	90 	nop
100000f5e:	90 	nop
100000f5f:	90 	nop
100000f60:	55 	pushq	%rbp
100000f61:	48 89 e5 	movq	%rsp, %rbp
100000f64:	53 	pushq	%rbx
100000f65:	50 	pushq	%rax
100000f66:	48 89 d3 	movq	%rdx, %rbx
100000f69:	e8 e2 ff ff ff 	callq	-30 <_mult2>
100000f6e:	48 89 03 	movq	%rax, (%rbx)
100000f71:	48 83 c4 08 	addq	$8, %rsp
100000f75:	5b 	popq	%rbx
100000f76:	5d 	popq	%rbp
100000f77:	c3 	retq

_main:
100000f10:	55 	pushq	%rbp
100000f11:	48 89 e5 	movq	%rsp, %rbp
100000f14:	48 83 ec 10 	subq	$16, %rsp
100000f18:	48 8d 55 f8 	leaq	-8(%rbp), %rdx
100000f1c:	bf 02 00 00 00 	movl	$2, %edi
100000f21:	be 03 00 00 00 	movl	$3, %esi
100000f26:	e8 35 00 00 00 	callq	53 <_multstore>
100000f2b:	48 8b 75 f8 	movq	-8(%rbp), %rsi
100000f2f:	48 8d 3d 64 00 00 00 	leaq	100(%rip), %rdi
100000f36:	31 c0 	xorl	%eax, %eax
100000f38:	e8 3b 00 00 00 	callq	59 <dyld_stub_binder+0x100000f78>
100000f3d:	31 c0 	xorl	%eax, %eax
100000f3f:	48 83 c4 10 	addq	$16, %rsp
100000f43:	5d 	popq	%rbp
100000f44:	c3 	retq
100000f45:	66 2e 0f 1f 84 00 00 00 00 00 	nopw	%cs:(%rax,%rax)
100000f4f:	90 	nop

_mult2:
100000f50:	55 	pushq	%rbp
100000f51:	48 89 e5 	movq	%rsp, %rbp
100000f54:	48 89 f8 	movq	%rdi, %rax
100000f57:	48 0f af c6 	imulq	%rsi, %rax
100000f5b:	5d 	popq	%rbp
100000f5c:	c3 	retq
100000f5d:	90 	nop
100000f5e:	90 	nop
100000f5f:	90 	nop

_multstore:
100000f60:	55 	pushq	%rbp
100000f61:	48 89 e5 	movq	%rsp, %rbp
100000f64:	53 	pushq	%rbx
100000f65:	50 	pushq	%rax
100000f66:	48 89 d3 	movq	%rdx, %rbx
100000f69:	e8 e2 ff ff ff 	callq	-30 <_mult2>
100000f6e:	48 89 03 	movq	%rax, (%rbx)
100000f71:	48 83 c4 08 	addq	$8, %rsp
100000f75:	5b 	popq	%rbx
100000f76:	5d 	popq	%rbp
100000f77:	c3 	retq
Disassembly of section __TEXT,__stubs:
__stubs:
100000f78:	ff 25 82 10 00 00 	jmpq	*4226(%rip)
Disassembly of section __TEXT,__stub_helper:
__stub_helper:
100000f80:	4c 8d 1d 81 10 00 00 	leaq	4225(%rip), %r11
100000f87:	41 53 	pushq	%r11
100000f89:	ff 25 71 00 00 00 	jmpq	*113(%rip)
100000f8f:	90 	nop
100000f90:	68 00 00 00 00 	pushq	$0
100000f95:	e9 e6 ff ff ff 	jmp	-26 <__stub_helper>
```

| C 声明 | Intel 数据类型 | 汇编代码后缀 | 大小（字节） |
| :----: | -------------- | :----------: | ------------ |
|  char  | 字节           |      b       | 1            |
| short  | 字             |      w       | 2            |
|  int   | 双字           |      l       | 4            |
|  long  | 四字           |      q       | 8            |
| char*  | 四字           |      q       | 8            |
| float  | 单精度         |      s       | 4            |
| double | 双精度         |      l       | 8            |

CPU 包含16个64位值的通用目的寄存器

每个寄存器 可以分别存放字节（8位）、字（16位）、双字（32位)、四字（64位）  

![](img/%E5%9B%BE3-2.png)

==**3.4.1操作指示符  看不懂怎么计算的？？**==

![](img/%E5%9B%BE3-3.png)

> 这里的知识涉及到汇编，而汇编往往和硬件很有关系，比如X86或者ARM的指令是不一样的。这张图我们需要理解几个概念：  1.汇编中出现的数据有立即数（**$52、$0x11等**）、寄存器里面的数据。【上图是用Ea来表示任意寄存器a,用引用 R[Ea]来表示它的值。】存储器里面的数据（可以理解很大的字节数组）。  2.比如最后1行的存储器 Imm（r,r,s）,我们可以这样解读  Imm 是立即偏移数、Eb 是基址寄存器、Ei 是变址寄存器、s 是比例因子，必须是 1、2、4或8  那么在汇编中 我们可以看到2(%esp,%eax,4)这个操作数的意思是：地址为2+%esp+4*%eax的存储器区域的值。 所以你应该明白这张表的意思 表达的就是在底层最为重要的offset。也就是指针偏移，这张表对应的是X86的汇编格式，针对ARM是不一样的，具体可以查ARM的汇编指令。暂时了解这个概念就可以了，ARM指令我们暂时不做逆向，可以不要花太多精力

数据传送  源数据的位数 <= 目的位置的容量位数

- MOVZ 零扩展  用0来填充
- MOVS 符号扩展  有源数据的最高位进行填充

入栈 出栈

栈的大小是有限的   栈底 --> 栈顶 地址是减小的

入栈%rsp（栈指针值减8  存放一个指针需要8个字节）

出栈%rsp栈指针值加8）

![](img/%E5%9B%BE3-10.png)



leaq  为 movq 的变形

```
比如 %rdx 的值为 x
leaq7(%rdx,%rdx,4), %rax  == 4*x+x+7存储到%rax
```





























