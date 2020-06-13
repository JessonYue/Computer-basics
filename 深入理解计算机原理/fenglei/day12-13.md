```
编写一个函数，检查输入的链表是否是回文的。

示例 1：
输入： 1->2
输出： false 
示例 2：
输入： 1->2->2->1
输出： true 

进阶：
你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/palindrome-linked-list-lcci
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
```





### C++

```c++
bool isPalindrome(ListNode *head) {
    if (head == NULL) {
        return true;
    }
    // 确定中间点
    ListNode *fastPointer, *slowPointer;
    fastPointer = head;
    slowPointer = head;
    while (fastPointer && fastPointer->next) {
        slowPointer = slowPointer->next;
        fastPointer = fastPointer->next->next;
    }

    // 反转链表
    ListNode *leftPointer, *midPointer, *rightPointer;
    leftPointer = NULL;
    midPointer = slowPointer;
    rightPointer = slowPointer->next;
    while (midPointer) {
        midPointer->next = leftPointer;
        leftPointer = midPointer;
        midPointer = rightPointer;
        rightPointer = rightPointer == NULL ? NULL : rightPointer->next;
    }
    print(leftPointer);
    // 逐值比较
    ListNode *node = head;
    while (leftPointer && node){
        if (node->val != leftPointer->val){
            return false;
        }
        leftPointer = leftPointer->next;
        node = node->next;
    }
    return true;
}

// 测试代码
void print(ListNode *node) {
    printf("print start....\n");
    while (node != NULL) {
        printf("[%p,%d]\n", node, node->val);
        node = node->next;
    }
    printf("print end....\n");
}

int main() {
    ListNode *node1 = new ListNode(1);
    ListNode *node2 = new ListNode(0);
    ListNode *node3 = new ListNode(1);
    node1->next = node2;
    node2->next = node3;
    bool flag = isPalindrome(node1);
    printf("result: %d", flag);
    return 0;
}

// 打印结果
print start....
[0x7fb54e405770,1]
[0x7fb54e405760,0]
print end....
result: 1

```

