# Joshua Hsin
# jhsin1

.globl main
.text
main:	
	li $v0, 1	
	lw $a0, num
	syscall			#prints value in num
	
	
	li $v0, 4
	la $a0, endl
	syscall			#newline
	
	
	li $v0, 4
	la $a0, abcd
	syscall			#prints ascii variable abcd
	
	
	li $v0, 4
	la $a0, endl
	syscall			#newline
	
	
	lw $t0, num
	sll $t1, $t0, 8		#shifting value of num 8 bits to left
	move $a0, $t1		
	sw $a0, num		#storing new shifted value into num
	li $v0, 1
	syscall			#prints shifted number
	
	li $v0, 4
	la $a0, endl
	syscall			#newline
	
	li $v0, 4
	la $a0, abcd
	syscall			#prints ascii variable abcd
	
	li $v0, 10 		# load exit syscall value
	syscall



.data
endl: .asciiz "\n"
.align 2
abcd: .ascii "abcd"
num: .word -217
