# Joshua Hsin
# jhsin1

.globl main
.text
main:
	add $s0, $0, $0		#sum
	add $t0, $0, $0		#i
	la $s2, nums
	lw $s1, nine
Loop:
	bge $t0, $s1, End
	sll $t1, $t0, 2
	add $t1, $t1, $s2
	li $v0, 1
	lw $t2, 0($t1)
	add $s0, $s0, $t2
	addi $t0, $t0, 1
	j Loop

End:
	li $v0, 1
	move $a0, $s0
	syscall
	
	li $v0, 10
	syscall

.data
nums: .word 10, 39, 28, -3, 100, 133, 1, 99, 1000
nine: .word 9