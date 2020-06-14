
add.so:     file format elf64-x86-64


Disassembly of section .init:

0000000000000460 <_init>:
 460:	48 83 ec 08          	sub    $0x8,%rsp
 464:	48 8b 05 8d 0b 20 00 	mov    0x200b8d(%rip),%rax        # 200ff8 <__gmon_start__>
 46b:	48 85 c0             	test   %rax,%rax
 46e:	74 02                	je     472 <_init+0x12>
 470:	ff d0                	callq  *%rax
 472:	48 83 c4 08          	add    $0x8,%rsp
 476:	c3                   	retq   

Disassembly of section .plt:

0000000000000480 <.plt>:
 480:	ff 35 82 0b 20 00    	pushq  0x200b82(%rip)        # 201008 <_GLOBAL_OFFSET_TABLE_+0x8>
 486:	ff 25 84 0b 20 00    	jmpq   *0x200b84(%rip)        # 201010 <_GLOBAL_OFFSET_TABLE_+0x10>
 48c:	0f 1f 40 00          	nopl   0x0(%rax)

Disassembly of section .plt.got:

0000000000000490 <__cxa_finalize@plt>:
 490:	ff 25 4a 0b 20 00    	jmpq   *0x200b4a(%rip)        # 200fe0 <__cxa_finalize>
 496:	66 90                	xchg   %ax,%ax

Disassembly of section .text:

00000000000004a0 <deregister_tm_clones>:
 4a0:	48 8d 3d 79 0b 20 00 	lea    0x200b79(%rip),%rdi        # 201020 <_edata>
 4a7:	55                   	push   %rbp
 4a8:	48 8d 05 71 0b 20 00 	lea    0x200b71(%rip),%rax        # 201020 <_edata>
 4af:	48 39 f8             	cmp    %rdi,%rax
 4b2:	48 89 e5             	mov    %rsp,%rbp
 4b5:	74 19                	je     4d0 <deregister_tm_clones+0x30>
 4b7:	48 8b 05 32 0b 20 00 	mov    0x200b32(%rip),%rax        # 200ff0 <_ITM_deregisterTMCloneTable>
 4be:	48 85 c0             	test   %rax,%rax
 4c1:	74 0d                	je     4d0 <deregister_tm_clones+0x30>
 4c3:	5d                   	pop    %rbp
 4c4:	ff e0                	jmpq   *%rax
 4c6:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 4cd:	00 00 00 
 4d0:	5d                   	pop    %rbp
 4d1:	c3                   	retq   
 4d2:	0f 1f 40 00          	nopl   0x0(%rax)
 4d6:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 4dd:	00 00 00 

00000000000004e0 <register_tm_clones>:
 4e0:	48 8d 3d 39 0b 20 00 	lea    0x200b39(%rip),%rdi        # 201020 <_edata>
 4e7:	48 8d 35 32 0b 20 00 	lea    0x200b32(%rip),%rsi        # 201020 <_edata>
 4ee:	55                   	push   %rbp
 4ef:	48 29 fe             	sub    %rdi,%rsi
 4f2:	48 89 e5             	mov    %rsp,%rbp
 4f5:	48 c1 fe 03          	sar    $0x3,%rsi
 4f9:	48 89 f0             	mov    %rsi,%rax
 4fc:	48 c1 e8 3f          	shr    $0x3f,%rax
 500:	48 01 c6             	add    %rax,%rsi
 503:	48 d1 fe             	sar    %rsi
 506:	74 18                	je     520 <register_tm_clones+0x40>
 508:	48 8b 05 d9 0a 20 00 	mov    0x200ad9(%rip),%rax        # 200fe8 <_ITM_registerTMCloneTable>
 50f:	48 85 c0             	test   %rax,%rax
 512:	74 0c                	je     520 <register_tm_clones+0x40>
 514:	5d                   	pop    %rbp
 515:	ff e0                	jmpq   *%rax
 517:	66 0f 1f 84 00 00 00 	nopw   0x0(%rax,%rax,1)
 51e:	00 00 
 520:	5d                   	pop    %rbp
 521:	c3                   	retq   
 522:	0f 1f 40 00          	nopl   0x0(%rax)
 526:	66 2e 0f 1f 84 00 00 	nopw   %cs:0x0(%rax,%rax,1)
 52d:	00 00 00 

0000000000000530 <__do_global_dtors_aux>:
 530:	80 3d e9 0a 20 00 00 	cmpb   $0x0,0x200ae9(%rip)        # 201020 <_edata>
 537:	75 2f                	jne    568 <__do_global_dtors_aux+0x38>
 539:	48 83 3d 9f 0a 20 00 	cmpq   $0x0,0x200a9f(%rip)        # 200fe0 <__cxa_finalize>
 540:	00 
 541:	55                   	push   %rbp
 542:	48 89 e5             	mov    %rsp,%rbp
 545:	74 0c                	je     553 <__do_global_dtors_aux+0x23>
 547:	48 8b 3d ca 0a 20 00 	mov    0x200aca(%rip),%rdi        # 201018 <__dso_handle>
 54e:	e8 3d ff ff ff       	callq  490 <__cxa_finalize@plt>
 553:	e8 48 ff ff ff       	callq  4a0 <deregister_tm_clones>
 558:	c6 05 c1 0a 20 00 01 	movb   $0x1,0x200ac1(%rip)        # 201020 <_edata>
 55f:	5d                   	pop    %rbp
 560:	c3                   	retq   
 561:	0f 1f 80 00 00 00 00 	nopl   0x0(%rax)
 568:	f3 c3                	repz retq 
 56a:	66 0f 1f 44 00 00    	nopw   0x0(%rax,%rax,1)

0000000000000570 <frame_dummy>:
 570:	55                   	push   %rbp
 571:	48 89 e5             	mov    %rsp,%rbp
 574:	5d                   	pop    %rbp
 575:	e9 66 ff ff ff       	jmpq   4e0 <register_tm_clones>

000000000000057a <add>:
 57a:	55                   	push   %rbp
 57b:	48 89 e5             	mov    %rsp,%rbp
 57e:	89 7d fc             	mov    %edi,-0x4(%rbp)
 581:	89 75 f8             	mov    %esi,-0x8(%rbp)
 584:	8b 55 fc             	mov    -0x4(%rbp),%edx
 587:	8b 45 f8             	mov    -0x8(%rbp),%eax
 58a:	01 d0                	add    %edx,%eax
 58c:	5d                   	pop    %rbp
 58d:	c3                   	retq   

Disassembly of section .fini:

0000000000000590 <_fini>:
 590:	48 83 ec 08          	sub    $0x8,%rsp
 594:	48 83 c4 08          	add    $0x8,%rsp
 598:	c3                   	retq   
