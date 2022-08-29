.globl main
.text
main:
	lw $a0, five
	lw $a1, four
	jal sum
	
	move $a0, $v0
	li $v0, 1
	syscall
	
	li $v0, 4
	la $a0, new_line
	syscall
	
	la $a0, randomlengthstr
	jal strlen
	
	move $a0, $v0
	li $v0, 1
	syscall
	
	li $v0, 10
	syscall
	
sum:
	#prologue
	addi $sp, $sp, -20
	sw $a0, 0($sp)
	sw $a1, 4($sp)
	sw $s0, 8($sp)
	sw $s1, 12($sp)
	sw $ra, 16($sp)
	
	add $v0, $0, $0
	move $s0, $a0
	move $s1, $a1
	addi $t0, $0, 1
	bne $t0, $s1, notif
	j if
	
if:
	addi $t0, $s0, 1
	mul $t1, $t0, $s0
	
	addi $t2, $0, 2
	div $t1, $t2
	mflo $t3
	move $v0, $t3
	j end

notif:
	move $a0, $s0
	addi $s1, $s1, -1
	move $a1, $s1
	jal sum
	addi $t0, $v0, 1
	mul $t0, $t0, $v0
	sra $t0, $t0, 1
	move $v0, $t0
	
end:
	#epilogue
	lw $a0, 0($sp)
	lw $a1, 4($sp)
	lw $s0, 8($sp)
	lw $s1, 12($sp)
	lw $ra, 16($sp)
	addi $sp, $sp, 20
	jr $ra
	
	
strlen:
	#prologue
	addi $sp, $sp, -12
	sw $a0, 0($sp)
	sw $s0, 4($sp)
	sw $ra 8($sp)
	
counter: 
	move $s0, $a0
	add $v0, $0, $0

loop:
	lb $t0, 0($s0)
	beq $t0, $0, end2
	addi $s0, $s0, 1
	addi $v0, $v0, 1
	j loop

end2:
	#epilogue
	lw $a0, 0($sp)
	lw $s0, 4($sp)
	lw $ra, 8($sp)
	jr $ra

	
.data
five: .word 5
four: .word 2
randomlengthstr: .asciiz "length"
new_line: .asciiz "\n"

