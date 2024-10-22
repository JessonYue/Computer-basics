## 											优化程序性能

重新结合变化能够减少计算中关键路径上的数量，通过更好的利用功能单元的流水线能力得到更好的性能。大多数编译器不糊尝试对浮点运算做重新结合，因为这些运算不保证可以结合的。我们可知循环展开和并行地累积在多个值中，是提高程序性能的更可靠的方法。

优化的一些限制条件：

1、程序中某条数据链上存在延迟等于T的时候，这个程序至少要执行T个周期才能执行完成。

2、功能单元的吞吐量界限也是程序执行时间的一个下界。

3、当并行数超过了可用的寄存器数量，那么编译器会溢出，将某些临界值存放到内存中，通常是运行时堆栈上分配空间。

疑问？那如果出现了溢出的情况，在 JVM 中会有什么影响吗？

4、分支预测逻辑不能正常预测一个分支是否要跳转的时候，条件分支可能会招致很大的预测处罚。

现代处理器有专门的功能单元来执行加载和储存操作，这些单元有内部的缓存区来保存未完成的内存操作请求集合。

要确定一台机器上加载操作的延迟，我们可以建立有一系列加载操作组成的一个计算，一条加载操作的结果决定下一条操作的地址。

优化程序性能的基本策略：

1、使用高级设计：为遇到问题选择适当的算法和数据结构。要特别警觉，要避免使用那些会渐近的产生早的性能的算法或编码技术。

2、基本编码原则：避免限制玉华的因素，这样编译器就能产生高效的代码。

	（1）消除连续的函数调用：在可能时，将计算移动到循环外。
	
	（2）消除不必要的内存引用：引入临时变量来保存中间见过。

3、低级优化：结构化代码以利用硬件功能。

	 （1）展开循环、降低开销，并使之进一步得到优化的可能性。
	
	 （2）通过使用例如过个累积变量和重新结合等技术，找到方法提高指令级并行。
	
	 （3）用功能性的风格重写条件操作，是编译采用条件数据传送。