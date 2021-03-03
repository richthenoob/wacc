.data
msg_0:
	.word 83
	.ascii "OverflowError: the result is too small/large to store in a 4-byte signed-integer.\n"
msg_1:
	.word 6
	.ascii "%.*s\0"
msg_2:
	.word 4
	.ascii "%d\0"
msg_3:
	.word 2
	.ascii "\0"

.text
.global main
main:
	PUSH {lr}
	PUSH {lr}
	ADD sp, sp, #4 
	SUB sp, sp, #4 
	BL f_f
	LDR r5, =10
	STR r5, [sp,#-4]!
	MOV r5, r0
	STR r5, [sp,#0]
	LDR r5, [sp,#0]
	MOV r0, r5
	BL p_print_int
	BL p_print_ln
	ADD sp, sp, #4 
	LDR r0, =0
	POP {pc}
	.ltorg
f_f:
	SUB sp, sp, #4 
	LDR r4, =0
	STR r4, [sp,#0]
	B L0
	POP {pc}
	.ltorg
L1:
	LDR r4, [sp,#0]
	LDR r5, =1
	ADD r4, r4, r5 
	BLVS p_throw_overflow_error
	STR r4, [sp,#0]
L0:
	LDR r4, [sp,#0]
	LDR r5, [sp,#0]
	CMP r4, r5 
	MOVLT r4, #1
	MOVGE r4, #0
	CMP r4, #1 
	BEQ L1
	LDR r4, [sp,#0]
	MOV r0, r4
	POP {pc}
p_throw_overflow_error:
	LDR r0, =msg_0
	BL p_throw_runtime_error
p_print_ln:
	PUSH {lr}
	LDR r0, =msg_3
	ADD r0, r0, #4 
	BL puts
	MOV r0, #0
	BL fflush
	POP {pc}
p_print_int:
	PUSH {lr}
	MOV r1, r0
	LDR r0, =msg_2
	ADD r0, r0, #4 
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}
p_throw_runtime_error:
	BL p_print_string
	MOV r0, #-1
	BL exit
p_print_string:
	PUSH {lr}
	LDR r1, [r0]
	ADD r2, r0, #4 
	LDR r0, =msg_1
	ADD r0, r0, #4 
	BL printf
	MOV r0, #0
	BL fflush
	POP {pc}