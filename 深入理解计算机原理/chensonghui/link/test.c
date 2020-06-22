#include<stdio.h>
#include <pthread.h>

void test() {
  pthread_t thread;
  pthread_create(&thread, NULL, NULL, NULL);
}