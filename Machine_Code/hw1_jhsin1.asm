# Homework 1
# Name: Joshua Hsin
# Net ID: jhsin1

.data
numargs: .word 0
AddressOfNUMVALUE: .word 0
AddressOfFOURCHARS: .word 0
four: .word 4
fourty_eight: .word 48
one: .word 1
flip_multiplier: .word -1
two_to_the_32: .word 2147483648
two_to_the_8: .word 255
excess_127: .word 127
two_to_the_11: .word 2047
excess_1023: .word 1023
zero: .word -2147483648

err_string: .asciiz "ERROR\n"
err_string2: .asciiz "error\n"
newline: .asciiz "\n"

TwosCompLabel: .asciiz "2's Complement:\t"
OnesCompLabel: .asciiz "1's Complement:\t"
Neg1sCompLabel: .asciiz "Neg 1's Complement:\t"
NegSMCompLabel: .asciiz "Neg Signed Magnitude:\t"
FPSglLabel: .asciiz "IEEE-754 single precision:\t"
FPDblLabel: .asciiz "IEEE-754 double precision:\t"
PosInfinity: .asciiz "+INF"
NegInfinity: .asciiz "-INF"
NotANumber: .asciiz "NaN"
PosZero: .asciiz "+0"
NegZero: .asciiz "-0"
tab: .asciiz "\t"

# Helper macro for accessing command line arguments via Label
.macro load_args
    sw $a0, numargs
    lw $t0, 0($a1)
    sw $t0, AddressOfNUMVALUE
    lw $t0, 4($a1)
    sw $t0, AddressOfFOURCHARS
    lw $t0, 8($a1)
.end_macro

.macro tab
	li $v0, 4
    	la $a0, tab
    	syscall
.end_macro

.macro newline
	li $v0, 4
	la $a0, newline
	syscall
.end_macro

.globl main
.text
main:
   	load_args()     # Only do this once
   	lw $t1, numargs			
    	bne $t1, 2, error		#texts if there are two arguments
    	
    	li $v0, 84			#converts ascii string into int
    	lw $a0, AddressOfNUMVALUE
    	syscall				#load in numvalue word
    	
    	move $t0, $v0			#store in value of ascii string NUMVALUE
	
    	bne $v1, 0, error		#Error if characters cannot be converted to int
    	
    	li $v0, 4
    	la $a0, TwosCompLabel		#Twos Comp
    	syscall
    	
    	tab()
    	
    	li $v0, 4			#Print NUMVALUE
    	lw $a0, AddressOfNUMVALUE
    	syscall
    	
    	tab()
    	
    	li $v0, 34			#Print bit representation of NUMVALUE in two's comp
    	move $a0, $t0
    	syscall
    	
    	tab()
    	
    	li $v0, 35			#Print hex representation of NUMVALUE in two's comp
    	move $a0, $t0
    	syscall
    	
    	tab()
    	
    	li $v0, 1			#Print two's comp value
    	move $a0, $t0
    	syscall
    	
    	newline()
    	
    	li $v0, 4
    	la $a0, OnesCompLabel		#One's comp
    	syscall
    	
    	tab()
    	
    	li $v0, 4
    	lw $a0, AddressOfNUMVALUE	#Print NUMVALUE
    	syscall   	
    	
    	tab()
    	
    	slt $t1, $t0, $0		#tests if t0 is negative
    	bne $t1, $0, Neg		#if negative, jump to Neg
    	
Pos:
	li $v0, 34
    	move $a0, $t0			#if positive, print same hex value as for two's comp
    	syscall
    	
    	tab()
    	
	li $v0, 35
	move $a0, $t0			#if positive, print same binary value as for two's comp
	syscall
	
	tab() 
	
	li $v0, 1
    	move $a0, $t0			#if positive, print same int value as two's comp
    	syscall
    	
	j End    
    
Neg:
	li $v0, 34
    	move $a0, $t0
    	lw $t1, one
    	sub $a0, $a0, $t1
    	syscall				#if negative, subtract one from value, then print hex
    	
    	tab()
    	
    	li $v0, 35
    	move $a0, $t0
    	lw $t1, one
    	sub $a0, $a0, $t1
    	syscall				#if negative, subtract one from value, the print binary
    	
    	tab()
    	
    	li $v0, 1
    	move $a0, $t0
    	lw $t1, one
    	sub $a0, $a0, $t1
    	syscall				#if negative, subtract one from two's comp value then print int
    	
    	j End
    	
End:

	newline()
	
	li $v0, 4
	la $a0, Neg1sCompLabel
	syscall				#Negative one's complement
	
	tab()
	
	li $v0, 1
	lw $t1, flip_multiplier
	mul $a0, $t0, $t1
	syscall				#multiply the two's comp value by -1 and print int
	
	tab()
	
	slt $t1, $t0, $0
    	bne $t1, $0, Neg2		#if the two's comp value is negative, jump to Neg2
    	
    	
Pos2:
	
	tab()
	
	li $v0, 34
	lw $t1, flip_multiplier
	mul $a0, $t0, $t1		#multiply the two's comp value by negative one
	lw $t1, one
	sub $a0, $a0, $t1		#subtract the value by one
	syscall				#print hex value
	
	tab()
	
	li $v0, 35
	nor $a0, $t0, $0		#flip the bits of the two's comp value and print in binary
	syscall
	
	lw $t1, flip_multiplier
	mul $t0, $t0, $t1		#flip the two's comp value
	
	tab()
	
	li $v0, 1			
	move $a0, $t0
	lw $t2, one
	sub $a0, $a0, $t2		#subtract the flipped value by one nad print in int
	syscall
	j End2
	
Neg2:
	tab()
	
	li $v0, 34
	lw $t1, flip_multiplier
	mul $a0, $t0, $t1
	syscall				#negate the two's value and print it in hex
	
	tab()
	
	li $v0, 35
	nor $a0, $t0, $0		#flip the values of the two's comp value
	lw $t1, one
	add $a0, $a0, $t1		#add one the the flipped value
	syscall				#print value in binary
	
	lw $t1, flip_multiplier
	mul $t0, $t0, $t1
	
	tab()
	
	li $v0, 1
	move $a0, $t0			#print the new value in int
	syscall
	j End2
	
End2:
	newline()
	
	li $v0, 4
	la $a0, NegSMCompLabel		#Negative Signed Magnitude
	syscall
	
	tab()
	
	beq $t0, $0, Zero		#if the two's comp value is zero, jump to Zero label
	
Regular:
	li $v0, 1			#print the two's comp value in int
	move $a0, $t0
	syscall
	j Pass
	
Zero:
	li $v0, 4			#print negative zero if the two's comp value is zero
	la $a0, NegZero
	syscall
	j Pass
	
Pass:
	tab()
	
	lw $s1, one
	slt $t1, $t0, $s1
    	bne $t1, $0, Neg3		#if less then 0, jump to Neg3 label
    	
Pos3:
	li $v0, 34
	move $a0, $t0
	syscall				#print two's comp value in hex 
	
	tab()
	
	
	li $v0, 35
	move $a0, $t0
	syscall				#print two's comp value in binary
	
	j End3

Neg3:
	
	li $v0, 34
	move $a0, $t0
	lw $t1, one
	sub $a0, $a0, $t1		#subtract one from two's comp value
	nor $a0, $a0, $0		#flip the bits
	lw $t2, two_to_the_32		#change the sign bit to a 1
	add $a0, $a0, $t2
	syscall				#print hex value
	
	tab()
	
	li $v0, 35
	move $a0, $t0
	lw $t1, one
	sub $a0, $a0, $t1		#subtract one from two's comp value
	nor $a0, $a0, $0		#flip the bits
	lw $t2, two_to_the_32
	add $a0, $a0, $t2		#change the sign bit to a 1
	syscall				#print binary value
	
	j End3
	
End3:
	tab()
	
	beq $t0, $0, Zero2		#if value is zero, jump to Zero2
	
	li $v0, 101
	move $a0, $t0
	syscall				#print value in signed magnitude
	j Pass2

Zero2:
	li $v0, 1
	lw $a0, zero
	syscall				#if value is zero, print zero
	j Pass2

	
Pass2:
	
	newline()
	newline()
	
	li $v0, 4
	la $a0, FPSglLabel		#IEEE-754 single precision
	syscall
	
	lw $t1, flip_multiplier		
	mul $t2, $t0, $t1		#flip the bits of the two's comp value
    	move $a0, $t2

    	rol $t1, $t2, 9			#rotate the bits 9 to the left
    	
    	tab()
    	
    	li $v0, 1
    	lw $t3, two_to_the_8
    	and $a0, $t1, $t3
	syscall				#isolate the right 8 bits and print
	
	tab()
	
	move $t2, $a0
	li $v0, 1
    	lw $t3, two_to_the_8
    	and $a0, $t1, $t3		#isolate the right 8 bits
    	lw $t4, excess_127
    	sub $a0, $a0, $t4		#subtract value by 127
	syscall				#print excess 127
    	
    	newline()
    	
    	li $v0, 4
	la $a0, FPDblLabel		#IEEE-754 double precision
	syscall		
    	
    	tab()
    	
    	li $v0, 84
    	lw $a0, AddressOfNUMVALUE
    	syscall				#Load in NUMVALUE
    	
    	move $t2, $v0			#Transfer the two's comp value
    	
    	rol $t2, $t2, 12		#rotate bits 12 left
    	li $v0, 1
    	lw $t3, two_to_the_11		#isolate right 11 bits
    	and $a0, $t2, $t3		
    	syscall				#print excess 1023
    	
    	tab()
    	
	li $v0, 1
    	lw $t3, two_to_the_11	
    	and $a0, $t2, $t3		#isolate right 11 bits
    	lw $t4, excess_1023
    	sub $a0, $a0, $t4		#subtract by 1023
	syscall				#print excess 1023
    	
    	newline()
    	newline()
    	
	#FOURCHARS
    	
    	lw $t5, AddressOfFOURCHARS	#loud in FOURCHARS variable as word
  
    	lb $s0, 0($t5)			#loud bytes for first character
    	
    	lb $s1, 1($t5)			#loud bytes for second character
    	sll $s1, $s1, 8			#shift bits
    	
    	lb $s2, 2($t5)			#loud bytes for third character
    	sll $s2, $s2, 16		#shift bits
    	
    	lb $s3, 3($t5)			#loud bytes for fourth character
    	sll $s3, $s3, 24

    	add $t0, $s0, $s1
    	add $t0, $t0, $s2
    	add $t0, $t0, $s3		#add all values together
    	
    	li $v0, 4
    	la $a0, TwosCompLabel		#two's complement
    	syscall
    	
    	tab()
    	
    	li $v0, 1
    	move $a0, $t0			#print int value of ascii chars
    	syscall
    	
    	tab()
    	
    	li $v0, 34
    	move $a0, $t0			#print hex value of ascii chars
    	syscall
    	
    	tab()
    	
    	li $v0, 35
    	move $a0, $t0			#print binary value of ascii chars
    	syscall
    	
    	tab()
    	
    	li $v0, 1
    	move $a0, $t0			#print int value of ascii chars
    	syscall
    	
    	newline()
    	
    	li $v0, 4
    	la $a0, OnesCompLabel		#One's Comp
    	syscall
    	
    	tab()
    	
    	li $v0, 1
    	move $a0, $t0			#print int value of ascii chars
    	syscall   	
    	
    	tab()
    	
    	slt $t1, $t0, $0
    	bne $t1, $0, Neg4		#if the original int from ascii value is less than zero, jump to Neg4
    	
Pos4:
	li $v0, 34
    	move $a0, $t0			#if positive, print hex value of int value
    	syscall
    	
    	tab()
    	
	li $v0, 35
	move $a0, $t0			#if positive, print binary value of int value
	syscall
	
	tab() 
	
	li $v0, 1
    	move $a0, $t0			#print int value from ascii chars
    	syscall
    	
	j End4  
    
Neg4:
	li $v0, 34
    	move $a0, $t0
    	lw $t1, one
    	sub $a0, $a0, $t1		#if negative, subtract value by 1
    	syscall				#print in hex
    	tab()
    	
    	li $v0, 35
    	move $a0, $t0
    	lw $t1, one
    	sub $a0, $a0, $t1		#subtract by 1
    	syscall				#print in binary
    	
    	tab()
    	
    	li $v0, 1
    	move $a0, $t0			#print int value from ascii chars
    	syscall
    	
    	j End4
    	
End4:

	newline()
	
	li $v0, 4
	la $a0, Neg1sCompLabel		#negative one's comp
	syscall
	
	tab()
	
	li $v0, 1
	lw $t1, flip_multiplier
	mul $a0, $t0, $t1
	syscall				#negate the int from ascii value and print in int
	
	tab()
	
	li $v0, 34
	lw $t1, flip_multiplier
	mul $a0, $t0, $t1		#negate
	lw $t1, one
	sub $a0, $a0, $t1		#subtract one
	syscall				#print hex
	
	tab()
	
	li $v0, 35
	nor $a0, $t0, $0		#flip bits
	syscall				#print binary
	
	lw $t1, flip_multiplier
	mul $t0, $t0, $t1
	slt $t1, $t0, $0		#if negated value is less than zero, jump to Neg5
    	bne $t1, $0, Neg5
    	
Pos5:
	tab()
	
	li $v0, 1
	move $a0, $t0			#if positive, print int from ascii value
	syscall
	j End5
	
Neg5:
	tab()
	li $v0, 1
	lw $t2, one
	sub $a0, $t0, $t2
	syscall				#if negative, subtract one from value, then print int
	j End5	
	
End5:
	newline()
	
	li $v0, 4	
	la $a0, NegSMCompLabel		#negative signed magnitude 
	syscall
	
	tab()
	
	li $v0, 1
	move $a0, $t0			#print negative SM value in int
	syscall
	
	tab()
	
	slt $t1, $t0, $0
    	bne $t1, $0, Neg6		#if negated int value from ascii chars is less than zero, jump to Neg6
    	
Pos6:
	li $v0, 34
	move $a0, $t0
	syscall				#print hex value from int ascii value
	
	tab()
	
	
	li $v0, 35
	move $a0, $t0
	syscall				#print binary value from int ascii value
	
	j End6

Neg6:
	
	li $v0, 34
	move $a0, $t0
	lw $t1, one
	sub $a0, $a0, $t1		#subtract one from original int value from ascii chars
	nor $a0, $a0, $0		#flip the bits
	lw $t2, two_to_the_32		#change signed bit to 1
	add $a0, $a0, $t2
	syscall				#print hex
	
	tab()
	
	li $v0, 35
	move $a0, $t0
	lw $t1, one
	sub $a0, $a0, $t1		#subtract one from original int value from ascii chars
	nor $a0, $a0, $0		#flip the bits
	lw $t2, two_to_the_32		#change signed bit to 1
	add $a0, $a0, $t2
	syscall				#print binary
	
	j End6
	
End6:
	tab()
	
	li $v0, 101
	move $a0, $t0			#print negative SM value
	syscall
	
	li $v0, 10			#terminate the program
	syscall
	

error:
	li $v0, 4
	la $a0, err_string		#print out Error string
	syscall
	
	li $v0, 10			#terminate the program
	syscall