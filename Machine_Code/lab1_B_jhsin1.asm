# Joshua Hsin
# jhsin1

.globl main
.text
main:
	la $a0, str
	li $v0, 4
	syscall			#prints value of str

	li $v0, 4
	la $a0, endl
	syscall			#newline
	
	lw $t0, str
	rol $t1, $t0, 16
	sw $t1, str		#Rotates value of str by 16 bits and stores in back in str
	
	la $a0, str
	li $v0, 4
	syscall			#prints value of str after rotated to the left 16 bit
	
	li $v0, 4
	la $a0, endl
	syscall			#newline

	li $v0, 11
	la $t0, str
	lb $t1, ($t0)
	lw $t2, num2
	sub $a0, $t1, $t2	
	syscall			#Prints char E after subtracting 32 from ascii value of e
	
	li $v0, 11
	la $t0, str
	lb $t1, 1($t0)
	lw $t2, num2
	sub $a0, $t1, $t2
	syscall			#Prints char D after subtracting 32 from ascii value of d
	
	li $v0, 11
	la $t0, str
	lb $t1, 2($t0)
	lw $t2, num2
	sub $a0, $t1, $t2
	syscall			#Prints char D after subtracting 32 from ascii value of ds
	
	li $v0, 11
	la $t0, str
	lb $t1, 3($t0)
	lw $t2, num2
	sub $a0, $t1, $t2
	syscall			#Prints char E after subtracting 32 from ascii value of e

	li $v0, 10 		#load exit syscall value
	syscall
	



.data
endl: .asciiz "\n"
.align 2
str: .asciiz "deed"
num: .word 98765
num2: .word 32