# Joshua Hsin
# jhsin1

.text
trib:
    
    #prologue
    addi $sp, $sp, -16
    sw $a0, 0($sp)
    sw $v0, 4($sp)
    sw $ra, 8($sp)
    sw $s0, 12($sp)
    
    move $s0, $a0
    
    li $v0, 4
    la $a0, trib2
    syscall
    
    li $v0, 1
    move $a0, $s0
    syscall
    move $s0, $a0
    
    li $v0, 4
    la $a0, new_line		#print input value
    syscall
    
first_case:
    blt $s0, $0, else
    li $t0, 1
    bgt $s0, $t0, else_if	#if n = 0 or 1, return 0
    add $v0, $0, $0
    #lw $t0, 4($sp)
    #add $v0, $v0, $t0
    #sw $v0, 4($sp)
    j end
    
else_if:
    li $t0, 2
    bne $s0, $t0, else		#if n = 2, return 1
    addi $v0, $0, 1
    #lw $t0, 4($sp)
    #add $v0, $v0, $t0
    #sw $v0, 4($sp)
    j end
    
else:
    lw $a0, 0($sp)
    addi $a0, $a0, -1
    jal trib
    
    sw $v0, 4($sp)
    lw $a0, 0($sp)
    #lw $a0, 0($sp)
    addi $a0, $a0, -2
    jal trib
    
    
    lw $t0, 4($sp)
    add $v0, $v0, $t0
    sw $v0, 4($sp)
    lw $a0, 0($sp)
    addi $a0, $a0, -3		#else return trib(n-1) + trib (n-2) + trib(n-3)
    jal trib
    
    #move $t0, $v0
    lw $t1, 4($sp)
    add $v0, $v0, $t1
    sw $v0, 4($sp)
    j end2
    
end:
    lw $a0, 0($sp)
    #lw $v0, 4($sp)
    lw $ra, 8($sp)
    lw $s0, 12($sp)
    addi $sp, $sp, 16		#epilogue for if and else if
    #li $v0, 222
    j finish
    
end2:
    lw $a0, 0($sp)
    lw $v0, 4($sp)
    lw $ra, 8($sp)
    lw $s0, 12($sp)
    addi $sp, $sp, 16		#epilogue for if

finish:
    jr $ra



recursiveFindMajorityElement:
    #prologue
    addi $sp, $sp, -56
    sw $v0, 0($sp)
    sw $a0, 4($sp)
    sw $a1, 8($sp)
    sw $a2, 12($sp)
    sw $a3, 16($sp)
    #sw $s5, 20($sp)
    sw $s6, 24($sp)
    sw $ra, 28($sp)
    sw $s0, 32($sp)
    sw $s1, 36($sp)
    sw $s2, 40($sp)
    sw $s3, 44($sp)
    sw $s4, 48($sp)
    sw $s5, 52($sp)
    
    move $s0, $a0
    move $s1, $a1
    move $s2, $a2
    move $s3, $a3
    
    sub $t0, $s3, $s2		
    addi $s4, $t0, 1		#array_length
    
    li $v0, 4
    la $a0, recursiveFindMajorityElement2
    syscall
   
    li $v0, 1
    move $a0, $s2
    syscall
    move $s2, $a0
    
    li $v0, 4
    la $a0, comma
    syscall
    
    li $v0, 1
    move $a0, $s3
    syscall
    move $s3, $a0
   
    li $v0, 4
    la $a0, comma
    syscall
    
    li $v0, 1
    move $a0, $s4
    syscall
    move $s4, $a0
    
    li $v0, 4
    la $a0, endParen
    syscall
    
    addi $t0, $0, 1		#print startIndex, endIndex, array_length for call
    beq $s4, $t0, if

else2:				#if array_length != 1
    addi $t0, $0, 2
    div $s4, $t0
    mflo $s5
    sw $s5, 20($sp)		#find middle of array
    
    #move $a0, $s0
    #move $a1, $s2
    
    lw $a0, 4($sp)
    lw $a1, 8($sp)
    lw $a2, 12($sp)	#startIndex
    lw $s5, 20($sp)
    add $t0, $a2, $s5
    addi $t1, $t0, -1
    move $a3, $t1
    jal recursiveFindMajorityElement		#recursively find sum of candidate occurances on left side
    sw $v0, 0($sp)
    
    lw $a0, 4($sp)
    lw $a1, 8($sp)
    lw $t0, 12($sp)
    lw $s5, 20($sp)
    add $t0, $t0, $s5
    move $a2, $t0
    lw $a3, 16($sp)
    jal recursiveFindMajorityElement		#recursively find um of candidate occurances on right side
    
    lw $t0, 0($sp)
    add $v0, $v0, $t0
    move $s6, $v0
    
    li $v0, 4
    la $a0, return				#add sums and return
    syscall
    
    li $v0, 1
    move $a0, $s6
    syscall
    move $s6, $a0
    
    li $v0, 4
    la $a0, new_line				#print sum
    syscall
    
    move $v0, $s6
    sw $v0, 0($sp)
    j end4
    

if:					#if array_length == 1
    sll $t0, $s2, 2
    add $t1, $t0, $s0
    lw $t2, 0($t1)
    bne $s1, $t2, nested_else		#if candidate != array[startIndex] break
    li $v0, 4
    la $a0, return1
    syscall
    addi $v0, $0, 1			#return one if array_legnth == 1 and candidate == array[startIndex]
    j end3
    #sw $v0, 0($sp)
    
nested_else:
    li $v0, 4
    la $a0, return0
    syscall
    add $v0, $0, $0			#return 0 if array_length == 1 and candidate != array[startIndex]
    #sw $v0, 0($sp)
    j end3
    
end3:					#epilogue for if
    #lw $v0, 0($sp)
    lw $a0, 4($sp)
    lw $a1, 8($sp)
    lw $a2, 12($sp)
    lw $a3, 16($sp)
    lw $s5, 20($sp)
    lw $s6, 24($sp)
    lw $ra, 28($sp)
    lw $s0, 32($sp)
    lw $s1, 36($sp)
    lw $s2, 40($sp)
    lw $s3, 44($sp)
    lw $s4, 48($sp)
    lw $s5, 52($sp)
    addi $sp, $sp, 56
    j finish2
    
end4:					#epilogue for else
    lw $v0, 0($sp)
    lw $a0, 4($sp)
    lw $a1, 8($sp)
    lw $a2, 12($sp)
    lw $a3, 16($sp)
    #lw $s5, 20($sp)
    lw $s6, 24($sp)
    lw $ra, 28($sp)
    lw $s0, 32($sp)
    lw $s1, 36($sp)
    lw $s2, 40($sp)
    lw $s3, 44($sp)
    lw $s4, 48($sp)
    lw $s5, 52($sp)
    addi $sp, $sp, 56
    j finish2
finish2:
    jr $ra


iterateCandidates:
    #prologue
    addi $sp, $sp, -36
    sw $a0, 4($sp)
    sw $s0, 8($sp)
    sw $s1, 12($sp)
    sw $v0, 16($sp)
    sw $s2, 20($sp)
    sw $s3, 24($sp)
    sw $s4, 28($sp)
    sw $ra, 32($sp)
    
    add $s0, $0, $0	#end_index
    add $s1, $0, $0	#start_index
    move $s3, $a0
    
while:
    addi $t0, $0, -1		#find end_index
    sll $t1, $s0, 2
    add $t2, $t1, $s3
    lw $s4, 0($t2)
    beq $s4, $t0, next
    addi $s0, $s0, 1
    j while
    
next:
    addi $s0, $s0, -1
    add $s2, $0, $0
    
for:				#iterate through index
    bgt $s2, $s0, print2
    
    li $v0, 4
    la $a0, candidate
    syscall
    
    li $v0, 1
    sll $t0, $s2, 2
    add $t1, $s3, $t0
    lw $t2, 0($t1)
    sw $t2, 0($sp)
    move $a0, $t2
    syscall			#print candidate
    
    li $v0, 4
    la $a0, new_line
    syscall
    
    move $a0, $s3
    lw $a1, 0($sp)
    move $a2, $s1
    move $a3, $s0
    jal recursiveFindMajorityElement	#find occurances of candidate
    
    addi $t0, $s0, 1
    li $t2, 2
    div $t0, $t2
    mflo $t1
    bge $v0, $t1, pass
    
    addi $s2, $s2, 1
    j for
    
pass:
    li $v0, 4
    la $a0, majority_element2
    syscall
    
    li $v0, 1
    lw $a0, 0($sp)
    syscall
    move $s4, $a0
    
    li $v0, 4
    la $a0, new_line
    syscall
    
    move $v0, $s4
    sw $v0, 16($sp)			#print majority element and return
    j success    

success:				#epilogue for successful majority element
    lw $a0, 4($sp)
    lw $s0, 8($sp)
    lw $s1, 12($sp)
    lw $v0, 16($sp)
    lw $s2, 20($sp)
    lw $s3, 24($sp)
    lw $s4, 28($sp)
    lw $ra, 32($sp)
    addi $sp, $sp, 36
    j end5
    
print2:
    li $v0, 4
    la $a0, majority_element		#print majority element -1 for end of list, failure
    syscall
    
    addi $v0, $0, -1
    j fail

fail:
    lw $a0, 4($sp)			#epilogue for unsuccessful majority element
    lw $s0, 8($sp)
    lw $s1, 12($sp)
    lw $s2, 20($sp)
    lw $s3, 24($sp)
    lw $s4, 28($sp)
    lw $ra, 32($sp)
    addi $sp, $sp, 36
    j end5
    
end5:
    jr $ra
    
    
calcMem:
    # prologue
    addi $sp, $sp, -12
    sw $a0, 0($sp)
    sw $v0, 4($sp)
    sw $ra, 8($sp)
    
    addi $t0, $0, 1
    lw $a0, 0($sp)
    beq $a0, $t0, one
    sw $a0, 0($sp)
    
else3:				#else return (3 * calcMem(n-1))
    lw $a0, 0($sp)
    addi $a0, $a0, -1
    jal calcMem
    
    li $t0, 3
    mul $v0, $v0, $t0
    sw $v0, 4($sp)
    j finish3
    
one:
    addi $v0, $0, 1		#if n == 1 return 1
    j finish4
    
finish3:			#epilogue for else
    lw $a0, 0($sp)
    lw $v0, 4($sp)
    lw $ra, 8($sp)
    addi $sp, $sp, 12
    j end6

finish4:			#epilogue for if
    lw $a0, 0($sp)
    #lw $v0, 4($sp)
    lw $ra, 8($sp)
    addi $sp, $sp, 12
    j end6
    
end6:
    jr $ra


recursiveMatrixFill:
    # get additional parameters
    lw $t0, 0($sp)	#char c
    lw $t1, 4($sp)	#matrix_dim
    lw $t2, 8($sp)	#matrix
    addi $sp, $sp, 12
    
    #prologue
    addi $sp, $sp, -60
    sw $a0, 0($sp)
    sw $a1, 4($sp)
    sw $a2, 8($sp)
    sw $a3, 12($sp)
    sw $t2, 16($sp)
    sw $t1, 20($sp)
    sw $t0, 24($sp)
    sw $s0, 28($sp)
    sw $s1, 32($sp)
    sw $s2, 36($sp)
    sw $s3, 40($sp)
    sw $s4, 44($sp)
    sw $s5, 48($sp)
    sw $s6, 52($sp)
    sw $ra, 56($sp)
    
    move $s0, $a0	#row
    move $s1, $a1	#col
    move $s2, $a2	#degree
    move $s3, $a3	#dim
    move $s4, $t2	#matrix
    move $s5, $t1	#matrix_dim
    move $s6, $t0	#char c
    
    addi $t0, $0, 1
    beq $s2, $t0, degree1		#if degree == 1, matrix[row][col] = c
    
notdegree1:
    lw $a0, 0($sp)
    lw $a1, 4($sp)
    lw $a2, 8($sp)
    addi $a2, $a2, -1
    
    #lw $a3, 12($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    
    move $a3, $t5
    
    lw $t2, 16($sp)
    lw $t1, 20($sp)
    lw $t0, 24($sp)
    
    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    
    jal recursiveMatrixFill	#Top Left
    
    lw $a0, 0($sp)
    lw $a1, 4($sp)
    addi $t0, $0, 2
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    mul $t1, $t5, $t0
    add $a1, $a1, $t1
    lw $a2, 8($sp)
    addi $a2, $a2, -1
    #lw $a3, 12($sp)
    
    #lw $a3, 12($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    
    move $a3, $t5
    
    lw $t2, 16($sp)
    lw $t1, 20($sp)
    lw $t0, 24($sp)
    
    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    
    jal recursiveMatrixFill	#Top Right
    
    lw $a0, 0($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    add $a0, $a0, $t5
    lw $a1, 4($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    add $a1, $a1, $t5
    lw $a2, 8($sp)
    addi $a2, $a2, -1
    #lw $a3, 12($sp)
    
    #lw $a3, 12($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    
    move $a3, $t5
    
    lw $t2, 16($sp)
    lw $t1, 20($sp)
    lw $t0, 24($sp)
    
    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    
    jal recursiveMatrixFill	#Center
    
    lw $a0, 0($sp)
    addi $t0, $0, 2
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    mul $t1, $t5, $t0
    add $a0, $a0, $t1
    lw $a1, 4($sp)
    lw $a2, 8($sp)
    addi $a2, $a2, -1
    #lw $a3, 12($sp)
    
    #lw $a3, 12($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    move $a3, $t5
    
    lw $t2, 16($sp)
    lw $t1, 20($sp)
    lw $t0, 24($sp)
    
    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    
    jal recursiveMatrixFill	#Bottom Left
    
    lw $a0, 0($sp)
    addi $t0, $0, 2
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    mul $t1, $t5, $t0
    add $a0, $a0, $t1
    lw $a1, 4($sp)
    addi $t0, $0, 2
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    mul $t1, $t5, $t0
    add $a1, $a1, $t1
    
    lw $a2, 8($sp)
    addi $a2, $a2, -1
    
    #lw $a3, 12($sp)
    addi $t3, $0, 3
    div $s3, $t3
    mflo $t5
    move $a3, $t5
    
    lw $t2, 16($sp)
    lw $t1, 20($sp)
    lw $t0, 24($sp)
    
    addi $sp, $sp, -12
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    
    jal recursiveMatrixFill	#Bottom Right
    
    j end7
    
degree1:
    mul $t0, $s0, $s5
    add $t0, $t0, $s1
    add $t1, $t0, $s4		#matrix[row][col] = c
    
    sb $s6, 0($t1)
    j end7
    
end7:				#epilogue
    lw $a0, 0($sp)
    lw $a1, 4($sp)
    lw $a2, 8($sp)
    lw $a3, 12($sp)
    lw $t2, 16($sp)
    lw $t1, 20($sp)
    lw $t0, 24($sp)
    lw $s0, 28($sp)
    lw $s1, 32($sp)
    lw $s2, 36($sp)
    lw $s3, 40($sp)
    lw $s4, 44($sp)
    lw $s5, 48($sp)
    lw $s6, 52($sp)
    lw $ra, 56($sp)
    addi $sp, $sp, 60
    
    jr $ra

boxFractal:
    # prologue
    addi $sp, $sp, -44
    sw $a0, 0($sp)
    sw $a1, 4($sp)
    sw $s0, 8($sp)
    sw $s1, 12($sp)
    sw $s2, 16($sp)
    sw $s3, 20($sp)
    sw $s4, 24($sp)
    sw $s5, 28($sp)
    sw $s6, 32($sp)
    sw $s7, 36($sp)
    sw $ra, 40($sp)
    
    move $s0, $a0
    move $s1, $a1
    
calcDim:
    move $a0, $s0	#calculate dimension using calcMem(var)
    jal calcMem
    move $s0, $a0
    move $s2, $v0	#dim
    
clear_matrix:			#clear the matrix
    la $s3, FRACTAL_BUF
    add $s4, $0, $0	#row
    add $s5, $0, $0	#col

loop:
    beq $s4, $s2, reset
    mul $t0, $s4, $s2
    add $t0, $t0, $s5
    add $t0, $t0, $s3
    
    sb $0, 0($t0)
    
    addi $s4, $s4, 1
    j loop
    
reset:
    addi $s5, $s5, 1
    beq $s5, $s2, next2
    add $s4, $0, $0
    j loop
    
next2:				#recursively fill the matrix with char c from top left based on dimensions
    li $a0, 0
    li $a1, 0
    move $a2, $s0
     
    
    addi $sp, $sp, -12
    sw $s1, 0($sp)
    sw $s2, 4($sp)
    sw $s3, 8($sp)
    
    move $a3, $s2
    jal recursiveMatrixFill
    move $s0, $a2
    move $s2, $a3
    add $s6, $0, $0
    
print:				#print out matrix
    beq $s6, $s2, end8
    add $s7, $0, $0
    j inner_for

inner_for:
    beq $s7, $s2, out
    mul $t0, $s6, $s2
    add $t0, $t0, $s7
    add $t0, $t0, $s3
    lb $t1, 0($t0)
    
    beq $t1, $0, for_if
    
    li $v0, 11
    move $a0, $t1		#if FRACTUL_BUF [i][j] != '\0', print char at [i][j]
    syscall
    
    addi $s7, $s7, 1
    
    j inner_for
    
for_if:
    li $v0, 4
    la $a0, space		#if FRACTUL_BUF[i][j] == '\0', print space
    syscall
    addi $s7, $s7, 1
    
    j inner_for
    
out:
    li $v0, 4
    la $a0, new_line		#print new_line at the end of row
    syscall
    
    addi $s6, $s6, 1
    j print

end8:				#epilogue
    lw $a0, 0($sp)
    lw $a1, 4($sp)
    lw $s0, 8($sp)
    lw $s1, 12($sp)
    lw $s2, 16($sp)
    lw $s3, 20($sp)
    lw $s4, 24($sp)
    lw $s5, 28($sp)
    lw $s6, 32($sp)
    lw $s7, 36($sp)
    lw $ra, 40($sp)
    addi $sp, $sp, 44
    jr $ra
    
    
.data
trib2: .asciiz "trib: "
new_line: .asciiz "\n"

candidate: .asciiz "candidate: "

majority_element: .asciiz "majority element: -1\n"

majority_element2: .asciiz "majority element: "

comma: .asciiz ","

recursiveFindMajorityElement2: .asciiz "recursiveFindMajorityElement( "

endParen: .asciiz " )\n"

return0: .asciiz "return: 0\n"
return1: .asciiz "return: 1\n"
return: .asciiz "return: "

space: .asciiz " "
