#include "console.h"

static inline void lcr3(unsigned int val)
{   
    asm volatile("movl %0,%%cr3" : : "r" (val));
}

static inline void halt(void)
{
    asm volatile("hlt" : : );
}

int *PTD[1024] __attribute__((aligned(4096)));
int *PT1[1024] __attribute__((aligned(4096)));
int *PT2[1024] __attribute__((aligned(4096)));
//int* pointer;

int main(void)
{
    int i; 
    int sum = 0;

    // Initialize the console
    uartinit(); 

    printk("Hello from C\n");

    PTD[0] = (int*)((int)PT1 | 3);
    PTD[1] = (int*)((int)PT2 | 3);
    
    for(i = 0; i < 1024; i++){
        PT1[i] = (int*)((i * 0x1000) | 3);
    }

    for(i = 0; i < 1024; i++){
        PT2[i] = (int*)((0x400000 + (i * 0x1000)) | 3);
    }

    lcr3((unsigned int)&PTD);

    halt(); 
    return sum; 
}