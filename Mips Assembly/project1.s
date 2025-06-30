#aikaterinhs theodwros
#am: 3210006
.data
	newline: .asciiz "\n"
	number: .asciiz "\nNumber: "
	operatorMessage: .asciiz "Operator: "
	error1: .asciiz "\nError: Invalid Operator. \n"
	error2: .asciiz "\nError: Divide by zero. \n"
	resultMessage: .asciiz "\nThe result is: "
	continue: .asciiz "\nDo you want to continue with a new expression? (y/n) "
	yes: .byte 'y'
	plus: .byte '+'
	minus: .byte '-'
	multiply: .byte '*'
	remaining: .byte '%'
	division: .byte '/'
	equal: .byte '='
	
.text
.globl main

main:
	do:
	addi $t0, $zero, 0   #apotelesma iso me 0
	lb $t1, plus		#apo8hkeush toy + ston t1
	lb $t2, minus		#apo8hkeush tou - ston t2
	lb $t3, multiply	#apo8hkeysh tou * ston t3
	lb $t4, remaining	#apo8hkeysh tou % ston t4
	lb $t5, division	#apo8hkeysh toy / ston t5
	lb $t6, equal		#apo8hkeysh toy = ston t6
	lb $s0, yes		#arxikopoihsh ths syn8hkhs synexeias ws y kai apo8hkeush ston s0
		
		li $v0,4
		la $a0,newline		#ektypwsh newline
		syscall
		
		li $v0, 4
		la $a0, number
		syscall                              #ektypwsh Number:
		
		li $v0, 5
		syscall                              #diabazei ari8mo
		
		add $t0, $t0, $v0                    #apotelesma iso me ari8mo1
		
		do2:
			li $v0, 4
			la $a0, operatorMessage
			syscall                                 #ektypwsh Operator:
			
			li $v0, 12
			syscall					#diabazei operator
			
			move $t7, $v0				#apo8hkeysh tou operator ston t7
			
			bne $t7, $t1, check1			#an operator oxi +
			
			
		cont:	
			bne $t7, $t6, synexeia1			#an o operator einai =
			
		pra3eis:
			beq $t7, $t1, pros8esh			#an operator = + kanei pros8esh
			
			beq $t7, $t2, afairesh			#an -
			
			beq $t7, $t3, polmos			#an *
							
			beq $t7, $t4, ypolipo			#an %
			
			beq $t7, $t5, diairesh			#an /
			
		while2:
			bne $t7, $t6, do2			#oso o operator den einai =
			j exit2
			
		exit2: 
			
	 	li $v0, 4
	 	la $a0, resultMessage		#ektypwsh mhnymatos to apotelesma einai
	 	syscall
	 	
	 	li $v0, 1
	 	move $a0, $t0			#ektypwsh tou apotelesmatos
	 	syscall
	 	
	 	li $v0, 4
	 	la $a0, continue		#ektypwsh tou an 8eloume na synexisoume
	 	syscall
	 	
	 	li $v0, 12			#diabazei ton xarakthra y/n
	 	syscall
	 	
	 	move $t9, $v0			#t9 iso me y/n
	 	
	 while:
	 	beq $t9, $s0, do		#an t9 einai y arxise pali to loop
	 	j exit
	 	
	 exit:
	 	li $v0,10			#exit
	 	syscall
	 	
	
#labels------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
				
wrongOperator:
	li $v0, 4
	la $a0, error1 #an operator akuros ektypwse la8os
	syscall
	
	li $v0, 10	#exit
	syscall
	
divideByZero:
	li $v0, 4
	la $a0, error2	#an diaireis me 0 ektypwse la8os
	syscall
	
	li $v0, 10	#exit
	syscall
	
synexeia1:
	li $v0, 4
	la $a0, number		#emfanizei mynhma ari8mou
	syscall
	
	li $v0, 5		#diabazei ari8mo
	syscall
	
	move $t8, $v0		#apo8hkeuei ton 2o ari8mo ston t8
	
	beq $t8, 0,check1_1		#an ari8mos2 = 0 checakrei an psaxnoume to ypolipo
	
     go:
	j pra3eis			#synexizei to loop 


	
check1_1:
	beq $t7, $t4, divideByZero 	#an psaxnoume upolipo me to 0 paei sto divideByZero
	
	j check1_2  			#checkarei an diairoume me 0
	
check1_2:
	beq $t7, $t5, divideByZero	#an diairoume me 0 paei sto divideByZero
	
	j go				#synexizei sto synexeia1
	
pros8esh: 
	add $t0 , $t0, $t8		#pros8etei to apotelesma kai ton ari8mo2
	j while2			#synexizei sto loop

afairesh:
	sub $t0, $t0, $t8		#afairei to apotelesma kai ton ari8mo2
	j while2			#synexizei sto loop

	
polmos:
	mul $t0, $t0, $t8		#pollaplasiazei to apotelesma kai ton ari8mo2	
	j while2			#synexizei sto loop

	
ypolipo:
	rem $t0, $t0, $t8		#briskei to ypoloipo apo apotelesma kai ari8mo2
	j while2			#synexizei to loop


diairesh:
	div $t0, $t0, $t8		#diairei to apotelesma me ton ari8mo2
	j while2			#synexizei to loop

	
check1:
	bne $t7, $t2, check2  #an operator oxi - paei sto check2
	j cont			#synexizei to loop

	
check2:
	bne $t7, $t3, check3	#an operator oxi * paei sto check3
	j cont			#synexizei to loop

	
check3:
	bne $t7, $t4, check4	#an operator oxi % paei sto check4
	j cont			#synexizei sto loop

	
check4:
	bne $t7, $t5, check5	#an operator oxi / paei sto check5
	j cont			#synexizei sto loop
	
check5:
	bne $t7, $t6, wrongOperator	#an operator oxi = paei sto wrongOperator
	j cont				#synexizei to loop

	
