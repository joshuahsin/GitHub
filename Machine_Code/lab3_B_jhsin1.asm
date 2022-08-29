# Joshua Hsin
# jhsin1

.text

printHist:
	#prologue
	addi $sp, $sp, -44
	sw $a1, 40($sp)
	sw $s7, 36($sp)
	sw $s6, 32($sp)
	sw $s5, 28($sp)
	sw $s0, 24($sp)
	sw $s1, 20($sp)
	sw $s2, 16($sp)
	sw $s3, 12($sp)
	sw $s4, 8($sp)
	sw $a0, 4($sp)
	sw $ra, 0($sp)
	
	move $s1, $a1		#num_rows
	addi $s5, $0, 2		#num_cols
	move $s0, $a0		#base_address
	add $s2, $0, $0		#row_counter
	add $s3, $0, $0		#0
	addi $s4, $0, 1		#1
	add $s6, $0, $0		#i
	addi $s7, $0, 4

row_loop2:
	beq $s2, $s1, end2
	mul $t0, $s5, $s6
	add $t0, $t0, $0
	addi $t1, $t0, 1
	mul $t0, $t0, $s7
	mul $t1, $t1, $s7
	add $t0, $t0, $s0
	add $t1, $t1, $s0
	lb $t0, 0($t0)
	lw $t1, 0($t1)
	move $a0, $t0
	move $a1, $t1
	jal printHistRow
	
	addi $s2, $s2, 1
	addi $s6, $s6, 1
	lw $a0, newline2
	li $v0, 11
	syscall
	add $v0, $0, $0
	add $a0, $0, $0
	j row_loop2
	
end2:
	#epilogue
	lw $a1, 40($sp)
	lw $s7, 36($sp)
	lw $s6, 32($sp)
	lw $s5, 28($sp)
	lw $s0, 24($sp)
	lw $s1, 20($sp)
	lw $s2, 16($sp)
	lw $s3, 12($sp)
	lw $s4, 8($sp)
	lw $a0, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 44
	
	jr $ra
	


countHist:
	#prologue
	addi $sp, $sp, -44
	sw $s7, 40($sp)
	sw $s6, 36($sp)
	sw $a1, 32($sp)
	sw $s5, 28($sp)
	sw $s0, 24($sp)
	sw $s1, 20($sp)
	sw $s2, 16($sp)
	sw $s3, 12($sp)
	sw $s4, 8($sp)
	sw $a0, 4($sp)
	sw $ra, 0($sp)


   	move $s0, $a0		#base_address
   	move $s1, $a1		#num_rows
   	move $s2, $a2		#char array
   	add $s3, $0, $0		#row counter/i
   	addi $s4, $0, 2		#num_cols
   	addi $s5, $0, 4
   	add $s7, $0, $0
   	add $s6, $0, $0
   	
row_loop3:
	beq $s3, $s1, end3
	mul $t0, $s3, $s4
	addi $t0, $t0, 1
	mul $t0, $t0, $s5
	add $s6, $t0, $s0
	lw $s7, 0($s6)
	
	mul $t2, $s3, $s4
	add $t2, $t2, $0
	mul $t2, $t2, $s5
	add $t2, $t2, $s0
	lb $a1, 0($t2)
	
	move $a0, $s2
	
	jal countC
	
	add $t3, $v0, $s7
	sb $t3, 0($s6)
	
	addi $s3, $s3, 1
	
	j row_loop3
   	
end3:
	lw $s7, 40($sp)
	lw $s6, 36($sp)
	lw $a1, 32($sp)
	lw $s5, 28($sp)
	lw $s0, 24($sp)
	lw $s1, 20($sp)
	lw $s2, 16($sp)
	lw $s3, 12($sp)
	lw $s4, 8($sp)
	lw $a0, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 44

	jr $ra
	
.data
newline2: .asciiz "\n"
