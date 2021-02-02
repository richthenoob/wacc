lexer grammar BasicLexer;

//operators
PLUS: '+' ;
MINUS: '-' ;
MUL: '*';
DIV: '/';
MOD: '%';
GT: '>';
GTE: '>=';
LT: '<';
LTE: '<=';
ASSIGN: '=';
EQ: '==';
NEQ: '!=';
AND: '&&';
OR: '||';
NOT: '!';
LEN: 'len';
ORD: 'ord';
CHR: 'chr';

//quote
SQUOTE: '\'';
DQUOTE: '"';

//separators
OPEN_PARENTHESES: '(' ;
CLOSE_PARENTHESES: ')' ;
OPEN_BRACKETS: '[';
CLOSE_BRACKETS: ']';
COMMA: ',';
SEMI: ';';

//pair
PAIR: 'pair';
FST: 'fst';
SND: 'snd';

//comment
COMMENT: '#' ~[\r\n]* '\r'? '\n' -> skip ;

//characters
fragment ESCAPED: '0'
| 'b'
| 't'
| 'n'
| 'f'
| 'r'
| SQUOTE
| DQUOTE
| '\\' ;
WS: [ \t\n\r]+ -> skip ;
fragment CHARACTER: ~[\\'"] | '\\' ESCAPED ;

//numbers
fragment INT_SIGN: '+' | '-' ;
fragment DIGIT: '0'..'9' ;

//func
BEGIN: 'begin';
END: 'end';
IS: 'is';

//stat
READ: 'read';
FREE: 'free';
RETURN: 'return';
EXIT: 'exit';
PRINT: 'print';
PRINTLN: 'println';
IF: 'if';
THEN: 'then';
ELSE: 'else';
FI: 'fi';
WHILE: 'while';
DONE: 'done';
SKP: 'skip';
DO: 'do';

//assignments
NEWPAIR: 'newpair';
CALL: 'call';

//types
INT: 'int';
BOOL: 'bool';
CHAR: 'char';
STR: 'string';

//literals
INT_LITER: (INT_SIGN)? (DIGIT)+;
BOOL_LITER: 'true' | 'false';
CHAR_LITER: SQUOTE CHARACTER SQUOTE ;
STR_LITER: DQUOTE (CHARACTER)* DQUOTE ;
PAIR_LITER: 'null';

//ident
IDENT: [_a-zA-Z][_a-zA-Z0-9]* ;