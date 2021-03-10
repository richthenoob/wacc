parser grammar BasicParser;

@header {
    package ic.doc.antlr;
}

options {
  tokenVocab=BasicLexer;
}

/* ------------------------- PROGRAMS, CLASSES AND FUNCTIONS ------------------------- */
prog: BEGIN (class_)* (func)* stat END EOF;

class_: CLASS IDENT OPEN_CURLY_BRACES (paramList (func)*) CLOSE_CURLY_BRACES;

func: type IDENT OPEN_PARENTHESES paramList CLOSE_PARENTHESES IS stat END;

paramList: param (COMMA param)* | ();

param: type IDENT;



/* ----------------------------------- STATEMENTS ----------------------------------- */
stat: SKP                                                         #skip
| IDENT IDENT ASSIGN NEW IDENT OPEN_PARENTHESES CLOSE_PARENTHESES #declareNewClass
| IDENT IDENT ASSIGN IDENT                                        #assignNewClass
| type IDENT ASSIGN assignRhs                                     #declarativeAssignment
| assignLhs ASSIGN assignRhs                                      #assignment
| READ assignLhs                                                  #read
| FREE expr                                                       #free
| RETURN expr                                                     #return
| EXIT expr                                                       #exit
| PRINT expr                                                      #print
| PRINTLN expr                                                    #println
| IF expr THEN stat ELSE stat FI                                  #if
| WHILE expr DO stat DONE                                         #while
| BEGIN stat END                                                  #begin
| stat SEMI stat                                                  #semi
;

assignLhs: IDENT
| arrayElem
| pairElem
| CLASS_OBJECT
;

assignRhs: expr                                               #exprDup
| arrayLiter                                                  #arrayLiterDup
| NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES  #newPair
| pairElem                                                    #pairElemDup
| CALL IDENT OPEN_PARENTHESES argList CLOSE_PARENTHESES       #call
| CALL CLASS_OBJECT OPEN_PARENTHESES CLOSE_PARENTHESES         #callClassFunction
;

argList: expr (COMMA expr)* | ();

pairElem: FST expr #fstPairElem
| SND expr         #sndPairElem
;

/* ------------------------------------- TYPES ------------------------------------- */
type: baseType                          #baseTypeDup
| type OPEN_BRACKETS CLOSE_BRACKETS     #arrayTypeDup
| pairType                              #pairTypeDup
;

baseType: INT   #intType
| BOOL          #boolType
| CHAR          #charType
| STR           #strType
;

arrayType : type OPEN_BRACKETS CLOSE_BRACKETS;

pairType: PAIR OPEN_PARENTHESES pairElemType COMMA pairElemType CLOSE_PARENTHESES;

pairElemType: baseType
| arrayType
| PAIR
;

/* ---------------------------------- EXPRESSIONS ---------------------------------- */

/* Binary operators in order of precedence, with 1 being the highest and 6 being the lowest */
expr: expr (MUL | DIV | MOD) expr                             #binOp1Application
| expr (PLUS | MINUS) expr                                    #binOp2Application
| expr (GT | GTE | LT | LTE) expr                             #binOp3Application
| expr (EQ | NEQ) expr                                        #binOp4Application
| expr (AND) expr                                             #binOp5Application
| expr (OR) expr                                              #binOp6Application
| (intLiter| BOOL_LITER | CHAR_LITER | STR_LITER| PAIR_LITER) #literal
| IDENT                                                       #identifier
| arrayElem                                                   #arrayElemDup
| (NOT | MINUS | LEN | ORD | CHR) expr                        #unOpApplication
| OPEN_PARENTHESES expr CLOSE_PARENTHESES                     #brackets
| CLASS_OBJECT                                                 #classVariable
;

/* Numbers */
intSign: PLUS | MINUS;
intLiter: (intSign)? INTEGER;

/* Arrays */
arrayElem: IDENT (OPEN_BRACKETS expr CLOSE_BRACKETS)+;
arrayLiter: OPEN_BRACKETS (expr (COMMA expr)*)? CLOSE_BRACKETS;
