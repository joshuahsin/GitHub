# Joshua Hsin
# jhsin1

.globl main
.text
main:
	li $v0, 4
	la $a0, Enter_string
	syscall

	li $v0, 5
	syscall
	
	move $s0, $v0
	
case1:
	srl $s1, $s0, 3
	sll $s1, $s1, 3
	bne $s1, $s0, case2
	
	li $v0, 4
	la $a0, Value8_string
	syscall
	
	j End

case2:	
	srl $s1, $s0, 2
	sll $s1, $s1, 2
	bne $s1, $s0, case3
	
	li $v0, 4
	la $a0, Value4_string
	syscall
	
	j End
	
case3:
	li $v0, 4
	la $a0, Not8or4_string
	syscall

End:

	li $v0, 10
	syscall

.data
Enter_string: .asciiz "Enter a number: "
Value8_string: .asciiz "Value is a multiple of 8\n"
Value4_string: .asciiz "Value is a multiple of 4\n"
Not8or4_string: .asciiz "Value is not a multiple of 8 or 4\n"
