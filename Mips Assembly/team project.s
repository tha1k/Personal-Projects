#vasileios avgerakis 
#am: 3210013
#aikaterinis theodoros 
#am: 3210006
.data
	syn: .byte '+'
	meion: .byte '-'
	epi: .byte '*'
	dia: .byte '/'
	ison: .byte '='
	keno: .byte ' '
	userInput: .space 20
	message: .asciiz "Postfix (input): "
	failMessage: .asciiz "Invalid Postfix"
	goodMessage: .asciiz "Postfix Evaluation: "
	newLine: .asciiz "\n"

.text


main:

	lb $s0, keno	#apo8hkeush toy kenou xarakthra ston s0
	lb $s1, syn	#apo8hkeush toy + ston s1
	lb $s2, meion	#apo8hkeush toy - ston s2
	lb $s3, epi	#apo8hkeush toy * ston s3
	lb $s4, dia	#apo8hkeush toy / ston s4
	lb $s5, ison	#apo8hkeush toy = ston s5
	addi $t1, $zero, 0	#o t1 isos me 0
	addi $t0, $zero, 0	#0 t0 isos me 0
	li $v0, 4	#printarei mhnyma
	la $a0, message
	syscall 
	
	li $v0, 8	#pernei to input tou xrhsth
	la $a0, userInput
	li $a1, 20
	move $t0, $a0	#apo8hkeyei to input tou xrhsth ston $t0
	syscall

	sub $t0, $t0, 1	#o t0 meionetai kata 1 gia na mporw na au3anw to t0 mesa sto loop

	do:	add $t0, $t0, 1	#t0 syn 1
		lb $t7, ($t0)	#bazei ston $t7 xarakthra toy input
		addi $t1, $zero, 0 #reset se 0 ton t1
	if1:	beq $t7, $s0, do	#an o xarakthras einai keno koita ton epomeno
		
		 while1: 
		 	bgt $t7, 57, if2		#an o xarakthras einai megalyteros tou 9 synexise sto epomeno if
		 	blt $t7, 48, if2		#an o xarakthras einai mikroteros tou 0 synexise sto epomeno if
		 	mul $t1, $t1, 10	#o ari8mos ison me 10*ari8mos
		 	sub $t7, $t7, 48	#o xarakthras ison me xarakthras-48
		 	add $t1, $t1, $t7	#o ari8mos ison me 10*ari8mos + xarakthras-48
		 	add $t0, $t0, 1		#syn 1 o t0
		 	lb $t7, ($t0)		#koita ton epomeno xarakthra
		 	j while1
	
	if2:	beq $t7, $s1, if2body	#an einai + o xarakthras phgene sto if2body
		beq $t7, $s2, if2body	#an einai - o xarakthras phgene sto if2body
		beq $t7, $s3, if2body	#an einai * o xarakthras phgene sto if2body
		beq $t7, $s4, if2body	#an einai / o xarakthras phgene sto if2body
		j if3			#an den einai tipota apta parapanw phgene sto if3
		if2body:	jal pop	#phgene sto pop
				move $t5, $t3	#apo8hkeyse thn prwth timh ston t5
				jal pop	#phgene sto pop
				move $t6, $t3	#apo8hkeyse thn deyterh timh ston t6
				jal calc	#phgene sto calc
				jal push	#phgene sto push
				j cont		#synexise 
		
		
	if3: bne $t7, $s5, push1	#to else if twn diafaneiwn (an den einai = kane push)
		beq $t7, $s5, cont	#an einai = synexise
	push1: jal push		#phgene sto push
cont:	bne $t7, $s5, do	#an den einai = synexise to loop
			
	bne $t4, 1, fail	#an den yparxei mia timh sto stack meta to loop phgene sto fail
	
	li $v0, 4
	la $a0, newLine		#typwse \n
	syscall
	li $v0, 4
	la $a0, goodMessage	#typwse to mhnyma gia to apotelesma
	syscall
	
	li $v0, 1		#typwse to apotelesma
	lw $a0, ($sp)
	syscall
	
	exit:			#termatismos toy programmatos
		li $v0, 10
		syscall
	
#labels----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
fail:	li $v0, 4
	la $a0, newLine		#typwse \n
	syscall
	li $v0, 4
	la $a0, failMessage	#typwse mhnyma la8ous
	syscall
	j exit 			#phgene sto exit
	
push:
	sub $sp, $sp, 4		#dhmiourghse xwro sto stack
	sw $t1, ($sp)		#save thn timh tou t1 sto stack
	add $t4, $t4, 1		#au3hse ton counter
	jr $ra			
	
pop:
	lw $t3, ($sp)		#apo8hkeyse thn timh tou teleutaiou stoixeiou tou stack ston t3
	add $sp, $sp, 4		#apodesmeuse xwro apo to stack
	sub $t4, $t4, 1		#meiwse ton counter
	jr $ra
	
calc:
	beq $t7, $s1, pros8esh	#an + phgene sto pros8esh
	beq $t7, $s2, afairesh	#an - phgene sto afairesh
	beq $t7, $s3, pol	#an * phgene sto pol
	beq $t7, $s3, diairesh	#an / phgene sto diairesh
	jr $ra
	
pros8esh:
	add $t1, $t5, $t6	#pro8sesh tou t5 me ton t6 kai apo8hkeysh ston t1
	jr $ra
	
afairesh: 
	sub $t1, $t6, $t5	#afairesh tou t6 me ton t5 kai apo8hkeysh ston t1
	jr $ra
	
pol:
	mul $t1, $t5, $t6	#pollaplasiasmos tou t5 me ton t6 kai apo8hkeysh ston t1
	jr $ra
	
diairesh: 
	div $t1, $t6, $t5	#diairesh tou t6 me ton t5 kai apo8hkeysh ston t1
	jr $ra
