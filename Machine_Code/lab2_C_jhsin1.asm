# Joshua Hsin
# jshin1

.text
countC:
    add $v0, $0, $0		#sum
    add $s0, $0, $0		#i
    move $t0, $a0
    move $t1, $a1
    lb $t2, empty
    beq $t0, $t2, Done
Loop:
    #beq $t0, $zero, Done
    add $t3, $t0, $s0
    lb $t4, 0($t3)
    beq $t4, $t2, Done
    beq $t4, $t1, Increment
    addi $s0, $s0, 1
    j Loop
Increment:
    addi $v0, $v0, 1
    addi $s0, $s0, 1
    j Loop
Done:
    jr $ra
    
    li $v0, 10
    syscall
    
    
.data
empty: .asciiz ""
    
