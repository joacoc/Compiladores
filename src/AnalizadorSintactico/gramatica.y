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
import java.util.ArrayList;
import AnalizadorLexico.*;
import AnalizadorLexico.Error;
import AnalizadorSintactico.*;
import CodigoIntermedio.*;
import Calculadora.*;
<<<<<<< HEAD
import java.util.ArrayList;

=======
import CodigoIntermedio.*;
>>>>>>> 28ade2e99055937d1282ef547cc4ed44ff3daefe
%}

programa_principal  : ID declaraciones '{' bloque_de_sentencia '}' { analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                    | ID '{' bloque_de_sentencia '}' { analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                    | ID declaraciones '{' bloque_de_sentencia  { analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                    | ID declaraciones bloque_de_sentencia '}'  { analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                    | error declaraciones '{' bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    | ID error '{' bloque_de_sentencia '}'    { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    | ID declaraciones '{' '}'         { analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    ;


declaraciones : declaraciones declaracion 
        	  | declaracion 
        	  ;
              
declaracion : tipo lista_variables ';' { 
											String tipo = ((Token) $1.obj).getNombre();
											
											for(Token t : (ArrayList<Token>)$2.obj ){ 
												/*Chequear que la variable ya no este declarada*/
												t.setTipo(tipo);
												tablaSimbolo.addSimbolo(t);
											}
										 
										 analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
										 }
			| tipo error ';' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
			
										 
		    | tipo lista_variables { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
		    | error lista_variables ';' { analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
            
            | tipo matriz { ((Token) $2.obj).setTipo(((Token)$1.obj).getNombre());
							tablaSimbolo.addSimbolo((Token) $2.obj);
							/*Checkear que no se haya agregado*/
							}
            | error matriz { analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
           
            | ALLOW tipo TO tipo ';' { allow = true;
            							analizadorS.addEstructura (new Error ( analizadorS.estructuraALLOW,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
            | ALLOW error TO tipo ';'{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
            | ALLOW tipo TO error ';'{ analizadorS.addError(new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
            ;

lista_variables : lista_variables ',' ID {	ArrayList<Token> lista = (ArrayList<Token>) $1.obj;
											lista.add((Token)$3.obj);
											$$ = new ParserVal(lista);
											}
											
                | ID	{	ArrayList<Token> lista = new ArrayList<>();
                			lista.add((Token)$1.obj);
                			$$ = new ParserVal(lista);}
                ;

declaracion_matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' {  analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
				   | MATRIX error '[' CTEI ']' '[' CTEI ']' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   | MATRIX ID '[' error ']' '[' CTEI ']' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   | MATRIX ID '[' CTEI ']' '[' error ']'	{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   ;

matriz : declaracion_matriz inicializacion ';' anotacion
       | declaracion_matriz ';' anotacion
       | declaracion_matriz inicializacion ';'
       | declaracion_matriz ';'

       ;

anotacion : ANOTACIONC
          | ANOTACIONF
          ;
inicializacion : '{' filas '}' 
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
      	  | asignacion_sin_punto_coma { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
  	  ;

lado_izquierdo : ID {$$ = new ParserVal(obtenerSimbolo(((Token) $1.obj).getNombre(),false)));}
            	| celda_matriz {$$ = new ParserVal(obtenerSimbolo(((Token) $1.obj).getNombre(),true)));}
                ;

operador_menos_menos : ID S_RESTA_RESTA {
										Token t1 = (Token) $1.obj;
										Token t = obtenerSimbolo(t1.getNombre(),false);
										if( t != null ){
											t.setValor(t.getValor()-1);
											tablaSimbolo.addSimbolo(t);
											$$ = new ParserVal(t);
										}
										/*TODO: else Error, no se declaro a la variable*/
										}
						| celda_matriz  S_RESTA_RESTA
						;

asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION expresion { String valor =":=";
																	Terceto terceto = new Terceto ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( $1.obj ), new TercetoSimple( $3.obj ), controladorTercetos.getProxNumero() );
																	controladorTercetos.addTerceto (terceto);
																	$$ = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
																	analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
																	Token t1 = (Token) $1.obj;
																	Token t2 = (Token) $3.obj;
																	if(tipoCompatible(t1,t2)){
																		System.out.println("Compatibles");
																		t1.setValor(t2.getValor());
																		tablaSimbolo.addSimbolo(t1);
																	}
																	/*TODO: else Error, tipos incompatibles */
																	} 
                            | operador_menos_menos { analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                           | lado_izquierdo S_ASIGNACION error { analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }      
                           | error S_ASIGNACION expresion { analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                           | lado_izquierdo '=' expresion { analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }

asignacion :  asignacion_sin_punto_coma ';'
		;

expresion : expresion '+' termino	{ 	String valor ="+";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( $1.obj ), new TercetoSimple( $3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
										 
		/*
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Suma().calcular(t1, t2);
											$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
									*/
									}
      | expresion '-' termino 		{	String valor ="-";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( $1.obj ), new TercetoSimple( $3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
      								/*
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Resta().calcular(t1, t2);
											$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
									*/
									}
      | termino						
;


termino : termino '*' factor	{	String valor ="*";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( $1.obj ), new TercetoSimple( $3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
									/*
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Multiplicacion().calcular(t1, t2);
										$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
								*/
								}
    | termino '/' factor		{ String valor ="/";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("/",(int) valor.charAt(0) ) ),new TercetoSimple( $1.obj ), new TercetoSimple( $3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
    
    /*		
										Token t1 = (Token) $1.obj;
										Token t2 = (Token) $3.obj;
										if(tipoCompatible(t1,t2)){
											Token res = new Division().calcular(t1, t2);
											$$ = new ParserVal(res);
										}else
											analizadorS.addError (new Error ( analizadorS.errorTipo_operacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));
								*/
								}
    | factor			{ $$ = new ParserVal( (Token)$1.obj ); }		
;

factor : CTEI {}
        | CTEL
        | ID { Token t1 = (Token) $1.obj;
        		System.out.println("chaaaaaaaaaaaaaaaaaaaaaaaaaaaau");
        		if (t1.getTipo() == null) 
        			 analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
        	}	 
        
        | operador_menos_menos 
		| celda_matriz
;
 
print : PRINT '(' MULTI_LINEA ')' ';' {analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
		  | PRINT '(' error ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }	 
		  | PRINT '(' MULTI_LINEA ')' error{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
		  | error '(' MULTI_LINEA ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
		    ;

sentencia_for : FOR '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' sentencia {analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
              | ID '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' sentencia { analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
      		  |	FOR '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' '{' bloque_de_sentencia '}' {analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
              | ID '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' '{' bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
              ;


cuerpo_if : '{' bloque_de_sentencia '}' 
		  | sentencia
          |  bloque_de_sentencia '}'    { analizadorS.addError (new Error ( analizadorS.errorLlaveAIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '{' bloque_de_sentencia     { analizadorS.addError (new Error ( analizadorS.errorLlaveCIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  ));}
		  ;
		            
cuerpo_else : '{' bloque_de_sentencia '}'
            | sentencia
			| bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorLlaveAELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          	| '{' bloque_de_sentencia  { analizadorS.addError (new Error ( analizadorS.errorLlaveCELSE,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
            ;         

sentencia_seleccion  : IF  condicion  cuerpo_if ELSE cuerpo_else ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | IF  condicion  cuerpo_if ELSE cuerpo_else ENDIF error { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                 	 | error  condicion  cuerpo_if ELSE cuerpo_else ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }                     
                                        	 
                     | IF  condicion  cuerpo_if ENDIF ';' { analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | IF  condicion  cuerpo_if ENDIF error { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                     | error  condicion  cuerpo_if ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     ;

condicion_sin_parentesis : expresion operador expresion {	Terceto terceto = new Terceto ( new TercetoSimple( $2.obj ) ,new TercetoSimple( $1.obj ), new TercetoSimple( $3.obj ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															$$ = new ParserVal(new Integer( controladorTercetos.getProxNumero()-1 ));
															analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                          |  error operador expresion { analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                          |  expresion operador error  { analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                          ;


condicion : '(' condicion_sin_parentesis ')' 
          | condicion_sin_parentesis ')' { analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '(' condicion_sin_parentesis error { analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          ;


tipo : INTEGER
     | LONGINT
     | MATRIX
     ; 

celda_matriz : ID '[' expresion ']' '[' expresion ']' {  }
             | ID '[' error ']' '[' expresion ']'  { analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
             | ID '[' expresion ']' '[' error ']'  { analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
             | ID '[' error ']' '[' error ']'  { analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }

             ;
             
operador : '<' 				{ String valor = "<";
							  $$ = new ParserVal(  new Token("<",(int) valor.charAt(0) ) ); }
		 | '>' 				{ String valor = ">";
		 					  $$ = new ParserVal(  new Token(">",(int) valor.charAt(0) ) ); }
		 | S_MAYOR_IGUAL 	{ $$ = new ParserVal(  new Token(">=", analizadorL.S_MAYOR_IGUAL ) ); }
		 | S_MENOR_IGUAL 	{ $$ = new ParserVal(  new Token("<=", analizadorL.S_MENOR_IGUAL ) ); }
		 | '='				{ String valor = "=";
		 					  $$ = new ParserVal(  new Token("=",(int) valor.charAt(0) ) ); }
		 ;


%%
ControladorTercetos controladorTercetos;
AnalizadorCodigoIntermedio analizadorCI;
AnalizadorLexico analizadorL;
AnalizadorSintactico analizadorS;
TablaSimbolos tablaSimbolo;
ControladorArchivo controladorArchivo;
boolean allow = false;

public void setLexico(AnalizadorLexico al) {
       analizadorL = al;
}
public void setCodigoIntermedio(AnalizadorCodigoIntermedio aci){
	analizadorCI = aci;
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

public void setControladorTercetos ( ControladorTercetos ct){
	controladorTercetos = ct;
}

int yylex()
{
	Token token = ((Token)(analizadorL).yylex());
   	int val = token.getUso();
   	yylval = new ParserVal(token);
    return val;
}

public boolean tipoCompatible(Token t1, Token t2){

if(t1.getTipo()!=null && t2.getTipo()!=null){
		System.out.println("Ambos tipos distintos de null");
		if(t1.getTipo().equals("integer")){
			if(t2.getTipo().equals("integer"))
				return true;
			else
				if(t2.getTipo().equals("longint"))
					if(allow)
						return true;
		}else{
			if(t1.getTipo().equals("longint")){
				if(t2.getTipo().equals("integer"))
					if(allow)
						return true;
				else
					if(t2.getTipo().equals("longint"))
						return true;
			}
		}
}
		return false;
}

public Token obtenerSimbolo(String nombre,boolean esMatriz){
	if(!esMatriz){
		if(tablaSimbolo.getToken(nombre+"integer")!= null)
			return tablaSimbolo.getToken(nombre+"integer");
		else if (tablaSimbolo.getToken(nombre+"long")!= null)
			return tablaSimbolo.getToken(nombre+"long");
	}else{
		if(tablaSimbolo.getToken(nombre+"matInteger")!= null)
			return tablaSimbolo.getToken(nombre+"matInteger");
		else if (tablaSimbolo.getToken(nombre+"matLong")!= null)
			return tablaSimbolo.getToken(nombre+"matLong");
	}
	return null;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}
