
main:     file format elf64-x86-64


Disassembly of section .init:

00000000000005f0 <_init>:
 5f0:	48 83 ec 08          	sub    $0x8,%rsp
 5f4:	48 8b 05 ed 09 20 00 	mov    0x2009ed(%rip),%rax        # 200fe8 <__gmon_start__>
 5fb:	48 85 c0             	test   %rax,%rax
 5fe:	74 02                	je     602 <_init+0x12>
 600:	ff d0                	callq  *%rax
 602:	48 83 c4 08          	add    $0x8,%rsp
 606:	c3                   	retq   

Disassembly of section .plt:

0000000000000610 <.plt>:
 610:	ff 35 a2 09 20 00    	pushq  0x2009a2(%rip)        # 200fb8 <_GLOBAL_OFFSET_TABLE_+0x8>
 616:	ff 25 a4 09 20 00    	jmpq   *0x2009a4(%rip)        # 200fc0 <_GLOBAL_OFFSET_TABLE_+0x10>
 61c:	0f 1f 40 00          	nopl   0x0(%rax)

0000000000000620 <add@plt>:
 620:	ff 25 a2 09 20 00    	jmpq   *0x2009a2(%rip)        # 200fc8 <add>
 626:	68 00 00 00 00       	pushq  $0x0
 62b:	e9 e0 ff ff ff       	jmpq   610 <.plt>

0000000000000630 <sub@plt>:
 630:	ff 25 9a 09 20 00    	jmpq   *0x20099a(%rip)        # 200fd0 <sub>
 636:	68 01 00 00 00       	pushq  $0x1
 63b:	e9 d0 ff ff ff       	jmpq   610 <.plt>

Disassembly of section .plt.got:

0000000000000640 <__cxa_finalize@plt>:
 640:	ff 25 b2 09 20 00    	jmpq   *0x2009b2(%rip)        # 200ff8 <__cxa_finalize@GLIBC_2.2.5>
 646:	66 90                	xchg   %ax,%ax

Disassembly of section .text:

0000000000000650 <_start>:
 650:	31 ed                	xor    %ebp,%ebp
 652:	49 89 d1             	mov    %rdx,%r9
 655:	5e                   	pop    %rsi
 656:	48 89 e2             	mov    %rsp,%rdx
 659:	48 83 e4 f0          	and    $0xfffffffffffffff0,%rsp
 65d:	50                   	push   %rax
 65e:	54                   	push   %rsp
 65f:	4c 8d 05 aa 01 00 00 	lea    0x1aa(%rip),%r8        # 810 <__libc_csu_fini>
 666:	48 8d 0d 33 01 00 00 	lea    0x133(%rip),%rcx        # 7a0 <__libc_csu_init>
 66d:	48 8d 3d e6 00 00 00 	lea    0xe6(%rip),%rdi        # 75a <main>
 674:	ff 15 66 09 20 00    	callq  *0x200966(%rip)        # 200fe0 <__libc_start_main@GLIBC_2.2.5>
 67a:	f4                   	hlt    
 67b:	0f 1f 44 00 00       	nopl   0x0(%rax,%rax,1)

0000000000000680 <deregister_tm_clones>:
 680:	48 8d 3d 89 09 20 00 	lea    0x200989(%rip),%rdi        # 201010 <__TMC_END__>
 687:	55                   	push   %rbp
 688:	48 8d 05 81 09 20 00 	lea    0x200981(%rip),%rax        # 201010 <__TMC_END__>
 68f:	48 39 f8             	cmp    %rdi,%rax
 692:	48 89 e5             	mov    %rsp,%rbp
 695:	74 19                	je     6b0 <deregister_tm_clones+0x30>
 697:	48 8b 05 3a 09 20 00 	mov    0x20093a(%rip),%rax        # 200fd8 <_ITM_deregisterTMCloneTable>
 69e:	48 85 c0             	test   %rax,%rax
 6a1:	74 0d                	je     6b0 <deregister_tm_clones+0x30>
 6a3:	5d                   	pop    %rbp
 6a4:	ff e0                	jmpq   *%rax
 6a6:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 6ad:	00 00 00 
 6b0:	5d                   	pop    %rbp
 6b1:	c3                   	retq   
 6b2:	0f 1f 40 00          	nopl   0x0(%rax)
 6b6:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 6bd:	00 00 00 

00000000000006c0 <register_tm_clones>:
 6c0:	48 8d 3d 49 09 20 00 	lea    0x200949(%rip),%rdi        # 201010 <__TMC_END__>
 6c7:	48 8d 35 42 09 20 00 	lea    0x200942(%rip),%rsi        # 201010 <__TMC_END__>
 6ce:	55                   	push   %rbp
 6cf:	48 29 fe             	sub    %rdi,%rsi
 6d2:	48 89 e5             	mov    %rsp,%rbp
 6d5:	48 c1 fe 03          	sar    $0x3,%rsi
 6d9:	48 89 f0             	mov    %rsi,%rax
 6dc:	48 c1 e8 3f          	shr    $0x3f,%rax
 6e0:	48 01 c6             	add    %rax,%rsi
 6e3:	48 d1 fe             	sar    %rsi
 6e6:	74 18                	je     700 <register_tm_clones+0x40>
 6e8:	48 8b 05 01 09 20 00 	mov    0x200901(%rip),%rax        # 200ff0 <_ITM_registerTMCloneTable>
 6ef:	48 85 c0             	test   %rax,%rax
 6f2:	74 0c                	je     700 <register_tm_clones+0x40>
 6f4:	5d                   	pop    %rbp
 6f5:	ff e0                	jmpq   *%rax
 6f7:	66 0f 1f 84 00 00 00 	nopw   0x0(%rax,%rax,1)
 6fe:	00 00 
 700:	5d                   	pop    %rbp
 701:	c3                   	retq   
 702:	0f 1f 40 00          	nopl   0x0(%rax)
 706:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 70d:	00 00 00 

0000000000000710 <__do_global_dtors_aux>:
 710:	80 3d f9 08 20 00 00 	cmpb   $0x0,0x2008f9(%rip)        # 201010 <__TMC_END__>
 717:	75 2f                	jne    748 <__do_global_dtors_aux+0x38>
 719:	48 83 3d d7 08 20 00 	cmpq   $0x0,0x2008d7(%rip)        # 200ff8 <__cxa_finalize@GLIBC_2.2.5>
 720:	00 
 721:	55                   	push   %rbp
 722:	48 89 e5             	mov    %rsp,%rbp
 725:	74 0c                	je     733 <__do_global_dtors_aux+0x23>
 727:	48 8b 3d da 08 20 00 	mov    0x2008da(%rip),%rdi        # 201008 <__dso_handle>
 72e:	e8 0d ff ff ff       	callq  640 <__cxa_finalize@plt>
 733:	e8 48 ff ff ff       	callq  680 <deregister_tm_clones>
 738:	c6 05 d1 08 20 00 01 	movb   $0x1,0x2008d1(%rip)        # 201010 <__TMC_END__>
 73f:	5d                   	pop    %rbp
 740:	c3                   	retq   
 741:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)
 748:	f3 c3                	repz retq 
 74a:	66 0f 1f 44 00 00    	nopw   0x0(%rax,%rax,1)

0000000000000750 <frame_dummy>:
 750:	55                   	push   %rbp
 751:	48 89 e5             	mov    %rsp,%rbp
 754:	5d                   	pop    %rbp
 755:	e9 66 ff ff ff       	jmpq   6c0 <register_tm_clones>

000000000000075a <main>:
 75a:	55                   	push   %rbp
 75b:	48 89 e5             	mov    %rsp,%rbp
 75e:	48 83 ec 10          	sub    $0x10,%rsp
 762:	c7 45 f8 00 00 00 00 	movl   $0x0,-0x8(%rbp)
 769:	c7 45 fc 01 00 00 00 	movl   $0x1,-0x4(%rbp)
 770:	8b 55 fc             	mov    -0x4(%rbp),%edx
 773:	8b 45 f8             	mov    -0x8(%rbp),%eax
 776:	89 d6                	mov    %edx,%esi
 778:	89 c7                	mov    %eax,%edi
 77a:	e8 a1 fe ff ff       	callq  620 <add@plt>
 77f:	8b 55 fc             	mov    -0x4(%rbp),%edx
 782:	8b 45 f8             	mov    -0x8(%rbp),%eax
 785:	89 d6                	mov    %edx,%esi
 787:	89 c7                	mov    %eax,%edi
 789:	e8 a2 fe ff ff       	callq  630 <sub@plt>
 78e:	b8 00 00 00 00       	mov    $0x0,%eax
 793:	c9                   	leaveq 
 794:	c3                   	retq   
 795:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 79c:	00 00 00 
 79f:	90                   	nop

00000000000007a0 <__libc_csu_init>:
 7a0:	41 57                	push   %r15
 7a2:	41 56                	push   %r14
 7a4:	49 89 d7             	mov    %rdx,%r15
 7a7:	41 55                	push   %r13
 7a9:	41 54                	push   %r12
 7ab:	4c 8d 25 de 05 20 00 	lea    0x2005de(%rip),%r12        # 200d90 <__frame_dummy_init_array_entry>
 7b2:	55                   	push   %rbp
 7b3:	48 8d 2d de 05 20 00 	lea    0x2005de(%rip),%rbp        # 200d98 <__init_array_end>
 7ba:	53                   	push   %rbx
 7bb:	41 89 fd             	mov    %edi,%r13d
 7be:	49 89 f6             	mov    %rsi,%r14
 7c1:	4c 29 e5             	sub    %r12,%rbp
 7c4:	48 83 ec 08          	sub    $0x8,%rsp
 7c8:	48 c1 fd 03          	sar    $0x3,%rbp
 7cc:	e8 1f fe ff ff       	callq  5f0 <_init>
 7d1:	48 85 ed             	test   %rbp,%rbp
 7d4:	74 20                	je     7f6 <__libc_csu_init+0x56>
 7d6:	31 db                	xor    %ebx,%ebx
 7d8:	0f 1f 84 00 00 00 00 	nopl   0x0(%rax,%rax,1)
 7df:	00 
 7e0:	4c 89 fa             	mov    %r15,%rdx
 7e3:	4c 89 f6             	mov    %r14,%rsi
 7e6:	44 89 ef             	mov    %r13d,%edi
 7e9:	41 ff 14 dc          	callq  *(%r12,%rbx,8)
 7ed:	48 83 c3 01          	add    $0x1,%rbx
 7f1:	48 39 dd             	cmp    %rbx,%rbp
 7f4:	75 ea                	jne    7e0 <__libc_csu_init+0x40>
 7f6:	48 83 c4 08          	add    $0x8,%rsp
 7fa:	5b                   	pop    %rbx
 7fb:	5d                   	pop    %rbp
 7fc:	41 5c                	pop    %r12
 7fe:	41 5d                	pop    %r13
 800:	41 5e                	pop    %r14
 802:	41 5f                	pop    %r15
 804:	c3                   	retq   
 805:	90                   	nop
 806:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 80d:	00 00 00 

0000000000000810 <__libc_csu_fini>:
 810:	f3 c3                	repz retq 

Disassembly of section .fini:

0000000000000814 <_fini>:
 814:	48 83 ec 08          	sub    $0x8,%rsp
 818:	48 83 c4 08          	add    $0x8,%rsp
 81c:	c3                   	retq   
