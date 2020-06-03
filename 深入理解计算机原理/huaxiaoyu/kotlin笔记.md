
# 常见操作符

## 创建集合

- mapOf
- listOf
  
---

## 集合操作符

### 过滤

- all
- any
- count
- find
- firstOrNull
- filter
  - filter操作符会创建一个中间集合,用来保存满足判断条件的元素

### 分组

- groupBy
  - 会创建map,map的key就是分组的依据
  
---

## map操作符

### 遍历

- mapValues{}
- filterValues{}
- filterKyes
- mapKeys

### 变换

- flatMap
  - 获取其元素,并对每个元素做变换,返回包含所有子元素的集合
- flatten
  - 获取其元素,并对每个元素做变换

***链式函数的每一个都会被存储在一个临时的列表中***

---

## 序列

将链式操作保存为序列,能减少链式函数的中间集合

### 创建序列

- asSequence() 将集合转为序列
- generateSequence(){//创建条件}.takeWhile{//终止条件}

### 末端操作

- toList() 将序列转为集合
- sum()

### 中间操作

- 序列的中间操作是惰性的, 只有在末端操作的时候才会真正执行计算
- 集合操作的每个步骤都会对元素进行操作, 而序列则是按顺序将所有操作应用到元素上
- 先应用filter有助于减少变换的总次数

---

## 函数式接口

- 使用lambda作为函数接口的实现, 如果该函数没有调用闭包环境下的变量, 则这个lambda是会复用的

### lambda

- lambda中没有指向自己的this变量(和匿名内部类不同),lambda中的this指向的是包裹他的外部环境
- 通过SAM构造方法能将lambda转为函数式接口的实现

### kotlin lambda

#### 带接受者的lambda函数

with函数

- 接受两个参数的函数, 参数1 xx; 参数2 lambda; 其中this指向参数1
- 返回的是lambda的执行结果

apply函数

- 返回作为实参传递给它的对象