parser grammar BasicParser;

options {
  tokenVocab=BasicLexer;
}

prog: BEGIN (func)* stat END EOF;

func: type IDENT OPEN_PARENTHESES paramList CLOSE_PARENTHESES IS stat END;

paramList: param (COMMA param)*;

param: type IDENT;

stat: SKP
| type IDENT ASSIGN assignRhs
| assignLhs ASSIGN assignRhs
| READ assignLhs
| FREE expr
| RETURN expr
| EXIT expr
| PRINT expr
| PRINTLN expr
| IF expr THEN stat ELSE stat FI
| WHILE expr DO stat DONE
| BEGIN stat END
| stat SEMI stat
;

assignLhs: IDENT
| arrayElem
| pairElem
;

assignRhs: expr
| arrayLiter
| NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES
| pairElem
| CALL IDENT OPEN_PARENTHESES (argList)? CLOSE_PARENTHESES
;

argList: expr (COMMA expr)*;

pairElem: FST expr
| SND expr
;

//types
type: baseType
| type OPEN_BRACKETS CLOSE_BRACKETS
| pairType
;

baseType: INT
| BOOL
| CHAR
| STR
;

arrayType : type OPEN_BRACKETS CLOSE_BRACKETS;

pairType: PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES;

pairElemType: baseType
| arrayType
| PAIR
;

//operators
unaryOper: NOT | MINUS | LEN | ORD | CHR;

binaryOper: PLUS | MINUS | MUL | DIV | MOD
| GT | GTE | LT | LTE | EQ | NEQ
| AND | OR
;

//expressions
expr: INT_LITER
| BOOL_LITER
| CHAR_LITER
| STR_LITER
| PAIR_LITER
| IDENT
| arrayElem
| unaryOper expr
| expr binaryOper expr
| OPEN_PARENTHESES expr CLOSE_PARENTHESES
;

arrayElem: IDENT (OPEN_BRACKETS expr CLOSE_BRACKETS)+;

arrayLiter: OPEN_BRACKETS (expr (COMMA expr)*)? CLOSE_BRACKETS;
