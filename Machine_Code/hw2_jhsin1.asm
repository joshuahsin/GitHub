# Joshua Hsin
# jhsin1

.text

ASCIIType:
    # Outputs integer based on character type
    move $s0, $a0   
case_symbol:
    add $t2, $0, $0
    blt $s0, $t2, case_DNE
    addi $t3, $0, 127
    bgt $s0, $t3, case_DNE		#returns -1 if character is out of ASCII value range

    addi $t0, $0, 32
    addi $t1, $0, 47
    blt $s0, $t0, case_invisible	#checks for invisible case
    bgt $s0, $t1, case_number		#checks for number case
    li $v0, 0
    j End
    	
case_invisible:
    li $v0, 4				#returns 4 for invisible chars
    j End
    	
case_number:
    addi $t0, $0, 57
    bgt $s0, $t0, case_symbol2		#checks for symbol case
    li $v0, 1				#returns 1 for number cases
    j End
    
case_symbol2:
    addi $t0, $0, 64
    bgt $s0, $t0, case_upper		#checks for upper case
    li $v0, 0				#returns 0 for symbol
    j End	
    
case_upper:
    addi $t0, $0, 90
    bgt $s0, $t0, case_symbol3		#checks for symbol case
    li $v0, 2				#returns 2 for upper case
    j End
    
case_symbol3:
    addi $t0, $0, 96			
    bgt $s0, $t0, case_lower		#checks for lower case
    li $v0, 0				#returns 0 for lower case
    j End
    
case_lower:
    addi $t0, $0, 122
    bgt $s0, $t0, case_symbol4		#checks for symbol case
    li $v0, 3				# returns 3 for lower case
    j End
    
case_symbol4:
    addi $t0, $0, 126
    bgt $s0, $t0, case_invisible	#checks for invisible case
    li $v0, 4				#returns 4 for symbol case
    j End
    
case_DNE:
    li $v0, -1				#return -1 for error case
    j End
    	
End:
    jr $ra




reverseString:
    move $s0, $a0
    add $s3, $0, $0
    add $s4, $0, $0
    
find_string_length:
    add $t1, $s0, $s3
    lb $t2, 0($t1)
    beq $t2, $zero, minus_one
    addi $s3, $s3, 1
    j find_string_length		#gets length of string that is being reversed
    
minus_one:
    addi $s3, $s3, -1
    j iterate_string_backwards
    
iterate_string_backwards:
    add $t1, $s0, $s3
    lb $t2, 0($t1)
    beq $t2, $zero, end
    add $t3, $a1, $s4
    sb $t2, 0($t3)
    addi $s3, $s3, -1
    addi $s4, $s4, 1
    j iterate_string_backwards		#iterates original string backwards and stores it in given address
    
end:
    add $t3, $a1, $s4
    sb $zero, 0($t3) 			#ends the string with a newline
    jr $ra
    
    
    
    

findValueGreater:
    move $s0, $a0
    move $s1, $a1
    move $s2, $a2
    add $v0, $0, $0			#index of list
    ble $s1, $0, error			#if n is less than 0, return error case

find_index:
    sll $t0, $v0, 2			
    add $t1, $s0, $t0
    lw $t2, 0($t1)			#Increments through int array
    beq $t2, $zero, error               #if incremented through whole array, return error case
    bgt $t2, $s2, end2			#if current index greater than target, increment return value
    addi $v0, $v0, 1
    j find_index
    
error:
    addi $v0, $0, -1
    j end2
    
end2:
    jr $ra






countCharsInStrings:
    move $s0, $a0
    move $s1, $a1
    blt $s1, $0, charError
    add $v0, $0, $0
    add $t0, $0, $0
    add $t5, $0, $0
    
find_length:
    add $s3, $0, $0
    sll $t3, $t0, 2
    add $t4, $t3, $s0
    lw $t2, 0($t4)
    addi $t0, $t0, 1
    beq $s1, $t5, end3
    addi $t5, $t5, 1
    j count_word			#increments through every word
    
count_word:
    add $s4, $t2, $s3
    lb $s2, 0($s4)
    beq $s2, $zero, find_length
    addi $s3, $s3, 1
    addi $v0, $v0, 1
    j count_word			#counts the num of chars in every word
    
charError:
    li $v0, -1
    j end3				#if n is less than 0, return -1
    
end3:
    jr $ra

