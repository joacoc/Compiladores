%token ID
%token MULTI_LINEA
%token CTEL 
%token S_ASIGNACION
%token S_MAYOR_IGUAL
%token S_MENOR_IGUAL
%token S_EXCLAMACION_IGUAL
%token CTEI
%token S_RESTA_RESTA
%token COMENTARIO
%token ANOTACIONF
%token ANOTACIONC

%token IF
%token ELSE 
%token ENDIF
%token PRINT
%token FOR
%token PROGRAMA
%token MATRIX
%token ALLOW 
%token TO
%token INTEGER
%token LONGINT
%%

%{
package AnalizadorSintactico;
import AnalizadorLexico.*;
import AnalizadorLexico.Error;
import AnalizadorSintactico.*;
%}

programa_principal  : PROGRAMA declaraciones '{' bloque_de_sentencia '}' {System.out.println("abs");}
                    | PROGRAMA declaraciones '{' bloque_de_sentencia  { analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                    | PROGRAMA declaraciones bloque_de_sentencia '}'  { analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                    | error declaraciones '{' bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    | PROGRAMA error '{' bloque_de_sentencia '}'    { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    | PROGRAMA declaraciones '{' '}'         { analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    ;


declaraciones : declaraciones declaracion 
			  | declaracion 
			  ;
             
declaracion : tipo lista_variables ';'
            /*|error lista_variables ';' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }*/
            | tipo error ';' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
            | allow
            ;
allow : ALLOW tipo TO tipo ';'
		;

lista_variables : lista_variables ',' variable
                | variable
                ;
                

variable : ID
          | MATRIX ID '[' CTEI ']' '[' CTEI ']' inicializacion anotacion
          | MATRIX ID '[' CTEI ']' '[' CTEI ']' anotacion
          ;

inicializacion : S_ASIGNACION '{' filas '}' ;

anotacion : ANOTACIONF 
			| ANOTACIONC
			;

filas : filas ';' fila
      | fila
      ;

fila : fila ',' CTEI
      | CTEI
      ;

bloque_de_sentencia : bloque_de_sentencia sentencia
                    | sentencia 
                    ;

sentencia : print
		  | sentencia_seleccion
		  | asignacion 
		  | sentencia_for 
		  | operador_menos_menos ';'
		  ;

lado_izquierdo : ID
            	| celda_matriz
                ;

operador_menos_menos : ID S_RESTA_RESTA
			;

asignacion : lado_izquierdo S_ASIGNACION expresion ';'
			| lado_izquierdo error expresion ';' { analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
			| lado_izquierdo S_ASIGNACION error ';' { analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
		;

expresion : expresion '+' termino
      | expresion '-' termino
      | termino
;

termino : termino '*' factor
    | termino '/' factor
    | factor
;

factor : CTEI 
        | CTEL
        | ID
        | operador_menos_menos 
		| celda_matriz
        ;

print : PRINT '(' MULTI_LINEA ')' ';' {analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
	  | PRINT '(' error ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }	 
	  | PRINT '(' MULTI_LINEA ')' error{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
	  | error '(' MULTI_LINEA ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
      ;

sentencia_for : FOR '(' asignacion ';' condicion ';' asignacion ')' '{' bloque_de_sentencia '}' {analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
              | FOR asignacion ';' condicion ';' asignacion ')' '{' bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
              | FOR '(' asignacion ';' condicion ';' asignacion '{' bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
              ;


sentencia_seleccion  : IF  condicion  '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                          | IF  condicion  sentencia ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                
                     | IF  condicion bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF  condicion  '{' bloque_de_sentencia ELSE '{' bloque_de_sentencia '}' ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
                     | IF  condicion  '{' bloque_de_sentencia '}' ELSE bloque_de_sentencia '}' ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF  condicion  '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF  condicion  '{' bloque_de_sentencia '}' ELSE '{' bloque_de_sentencia '}' ENDIF error { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     
                     | IF  condicion  sentencia ELSE sentencia ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | IF  condicion  sentencia ELSE sentencia ENDIF error { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF  condicion  '{' bloque_de_sentencia '}' ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | IF  condicion  bloque_de_sentencia '}' ENDIF ';'  { analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                     | IF  condicion  '{' bloque_de_sentencia ENDIF ';'  { analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF  condicion  '{' bloque_de_sentencia '}' ENDIF error { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }

                     | IF condicion '{' bloque_de_sentencia '}' ELSE sentencia ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | IF condicion bloque_de_sentencia '}' ELSE sentencia ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF condicion '{' bloque_de_sentencia ELSE sentencia ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF condicion '{' bloque_de_sentencia '}' ELSE sentencia ENDIF error {  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                    
 					 | IF condicion sentencia ELSE '{' bloque_de_sentencia '}' ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | IF condicion sentencia ELSE  bloque_de_sentencia '}' ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF condicion sentencia ELSE '{' bloque_de_sentencia  ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     | IF condicion sentencia ELSE '{' bloque_de_sentencia '}' ENDIF error {  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     

                     | IF  condicion  sentencia ENDIF error {  analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                     ;


condicion : '(' factor operador factor ')' 
          | factor operador factor ')' { analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '(' factor operador factor error { analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '(' error operador factor ')'{ analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '(' factor operador error ')' { analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          ;


tipo : INTEGER
     | LONGINT
     | MATRIX
     ; 


celda_matriz : ID '[' ID ']' '[' ID ']'
              | ID '[' CTEI ']' '[' CTEI ']'
              ;
operador : '<' 
		 | '>' 
		 | S_MAYOR_IGUAL 
		 | S_MENOR_IGUAL 
		 | '='
		 ;
		 
%%

AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;

public void setLexico(AnalizadorLexico al) {
       analizadorL = al;
}

public void setSintactico (AnalizadorSintactico as){
	analizadorS = as;
}

public void setTS (TablaSimbolos ts){
	tablaSimbolo = ts;
}

public void setControladorArchivo ( ControladorArchivo ca){
	controladorArchivo = ca;
}

int yylex()
{
   	int val = analizadorL.yylex().getUso();
    return val;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
