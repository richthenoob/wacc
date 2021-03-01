.data
msg_0:
	.word 4
	.ascii "%d\0"
msg_1:
	.word 2
	.ascii "\0"

.text
.global main
main:
	PUSH {lr}
	PUSH {lr}
	ADD sp, sp, #4 
	SUB sp, sp, #4 
	BL f_getPair
	MOV r5, r0
	STR r5, [sp,#0]
	SUB sp, sp, #4 
	LDR r5, [sp,#-8]
	STR r5, [sp,#0]
	LDR r5, [sp,#0]
	MOV r0, r5
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #4 
	LDR r0, =0
	POP {pc}
	.ltorg
f_getPair:
	SUB sp, sp, #4 
	LDR r0, =8
	BL malloc
	MOV r4, r0
	LDR r5, =10
	LDR r0, =4
	BL malloc
	STR r5, [r0]
	STR r0, [r4]
	LDR r5, =15
	LDR r0, =4
	BL malloc
	STR r5, [r0]
	STR r0, [r4,#4]
	STR r4, [sp,#0]
	LDR r4, [sp,#0]
	MOV r0, r4
	POP {pc}
	POP {pc}
	.ltorg
p_print_ln:
	PUSH {lr}
	LDR r0, =msg_1
	ADD r0, r0, #4 
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}
p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_0
	ADD r0, r0, #4 
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}
