# Joshua Hsin
# jhsin1

.text
sumArray:
	#prologue
	addi $sp, $sp, -40
	sw $a2, 36($sp)
	sw $a1, 32($sp)
	sw $s5, 28($sp)
	sw $s0, 24($sp)
	sw $s1, 20($sp)
	sw $s2, 16($sp)
	sw $s3, 12($sp)
	sw $s4, 8($sp)
	sw $a0, 4($sp)
	sw $ra, 0($sp)

	move $s0, $a0		#array address
	move $s1, $a1		#num_rows
	move $s2, $a2		#num_columns
	add $s3, $0, $0		#i
	addi $s4, $s2, -1	#j
	addi $s5, $0, 4
	add $v0, $0, $0
	
row_loop:
	bge $s3, $s1, end
	mul $t0, $s3, $s2	#i * num_col
	add $t0, $t0, $s4	#i * num_col + j
	mul $t0, $t0, $s5	# + 4
	add $t0, $t0, $s0	# + base_address
	lw $t1, 0($t0)
	add $v0, $v0, $t1
	
	addi $s3, $s3, 1
	j row_loop
	
end:
	lw $a2, 36($sp)
	lw $a1, 32($sp)
	lw $s5, 28($sp)
	lw $s0, 24($sp)
	lw $s1, 20($sp)
	lw $s2, 16($sp)
	lw $s3, 12($sp)
	lw $s4, 8($sp)
	lw $a0, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 40
	
 	jr $ra




#prologue
initArray:
	addi $sp, $sp, -40
	sw $a2, 36($sp)
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
	move $s1, $a1		#rows
	move $s2, $a2		#columns
	add $s3, $0, $0		#i
	addi $s4, $s2, -1	#j
	addi $s5, $0, 4
	
row_loop1:
	bge $s3, $s1, end1
	mul $t0, $s3, $s2	#i * num_col
	add $t0, $t0, $s4	#i * num_col + j
	mul $t0, $t0, $s5	# + 4
	add $t0, $t0, $s0	# + base_address
	sw $0, 0($t0)
	
	addi $s3, $s3, 1
	j row_loop1
	
end1:
	lw $a2, 36($sp)
	lw $a1, 32($sp)
	lw $s5, 28($sp)
	lw $s0, 24($sp)
	lw $s1, 20($sp)
	lw $s2, 16($sp)
	lw $s3, 12($sp)
	lw $s4, 8($sp)
	lw $a0, 4($sp)
	lw $ra, 0($sp)
	addi $sp, $sp, 40
	
	jr $ra
	
#epilogue

.data
nums: .word 10, 39, 28, -3, 100, 133, 1, 99, 1000
