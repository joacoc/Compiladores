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
import java.util.ArrayList;
import CodigoIntermedio.*;
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
											Token t1 = new Token("var@" + t.getNombre(), t.getUso() );
											
											if (tablaSimbolo.existe(t1.getNombre())){
	 											analizadorCI.addError (new Error ( analizadorCI.errorVariableRedeclarada,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
											}
	 										else {
	 											//la variable no fue declarada
												t1.setTipo(tipo);
												tablaSimbolo.addSimbolo(t1);
	 											} 												
	 											
											}

										//agregar estructura	
										 analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
										 }

			| tipo error ';' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
			
										 
		    | tipo lista_variables { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
		    | error lista_variables ';' { analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
            
            | tipo matriz { 
							/*Chequear que la variable ya no este declarada*/
							TokenMatriz t = (TokenMatriz)$2.obj;
							TokenMatriz t1 = new TokenMatriz("mat@" + t.getNombre(), t.getUso() );
							
							if (tablaSimbolo.existe(t1.getNombre())){
									analizadorCI.addError (new Error ( analizadorCI.errorMatrizRedeclarada,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
							}
							else{
								//la variable no fue declarada
								String tipo = ((Token) $1.obj).getNombre();
								t1.setTipo(tipo);
								tablaSimbolo.addSimbolo(t1);
								}

							
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

declaracion_matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' {  Token t = (Token) $2.obj ;
															TokenMatriz tm = new TokenMatriz(t.getNombre(), t.getUso(), ((Token)$4.obj).getValor(), ((Token)$7.obj).getValor());
															$$ = new ParserVal( tm );
															analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
				   | MATRIX error '[' CTEI ']' '[' CTEI ']' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   | MATRIX ID '[' error ']' '[' CTEI ']' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   | MATRIX ID '[' CTEI ']' '[' error ']'	{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   ;

matriz : declaracion_matriz inicializacion ';' anotacion { TokenMatriz declaracion_matriz = (TokenMatriz) $1.obj;
															String orientacion = $3.sval;
															String ArrayList<ArrayList<long>> valores = (ArrayList<ArrayList<long>>) $2.obj;
															declaracion_matriz.setValores(valores,orientacion);
															$$ = new ParserVal(declaracion_matriz);
															}
       | declaracion_matriz ';' anotacion 				{	
       														TokenMatriz declaracion_matriz = (TokenMatriz) $1.obj;
															String orientacion = $3.sval;
															declaracion_matriz.setValores(null,orientacion);
															$$ = new ParserVal(declaracion_matriz);
															}
       | declaracion_matriz inicializacion ';'{
       														TokenMatriz declaracion_matriz = (TokenMatriz) $1.obj;
															String ArrayList<ArrayList<long>> valores = (ArrayList<ArrayList<long>>) $2.obj;
															declaracion_matriz.setValores(valores,null);
															$$ = new ParserVal(declaracion_matriz);
															}
       | declaracion_matriz ';'{							TokenMatriz declaracion_matriz = (TokenMatriz) $1.obj;
															declaracion_matriz.setValores(null,null);
															$$ = new ParserVal(declaracion_matriz);
															}

       ;


anotacion : ANOTACIONC {$$ = new ParserVal("C");}
          | ANOTACIONF {$$ = new ParserVal("F");}
          ;
inicializacion : '{' filas '}' {$$ = $2.obj;}
                ;

filas : filas ';' fila {ArrayList<ArrayList<long>> lista = (ArrayList<ArrayList<long>>)$1.obj;
						lista.add(((ArrayList<long>)$3.obj));
						$$ = lista;
					}
      | fila	{$$ = new ArrayList<ArrayList<long>>((ArrayList<long>)$1.obj);}
      ;

fila : fila ',' CTEI {ArrayList<long> lista = (ArrayList<long>)$1.obj;
						lista.add(((Token)$3.obj).getValor());
						$$ = lista;}
      | CTEI {$$ = new ArrayList<long>(((Token)$1).getValor());}
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

lado_izquierdo : ID {	//chequeo semantico variable no declarada
						Token t1 = tablaSimbolo.getToken( "var@" + ( (Token) $1.obj).getNombre() ) ;
		    			if  ( t1 == null ) 
							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

						$$ = new ParserVal( (Token) $1.obj );}
    
            	| celda_matriz {//chequeo semantico variable no declarada
						Token t1 = tablaSimbolo.getToken( "mat@" + (  (Token) val_peek(0).obj).getNombre() ) ;
				    			if (t1 == null) 
		 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
								$$ = new ParserVal( (Token) $1.obj );}
                ;

operador_menos_menos : ID S_RESTA_RESTA { 	//agregando terceto
											String valor = "-";
											Terceto terceto = new Terceto ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( (Token) $1.obj ), new TercetoSimple( new Token("_i1",analizadorL.CTEI) ),  controladorTercetos.getProxNumero() );
											controladorTercetos.addTerceto (terceto);
											$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
											
											//chequeo semantico variable no declarada
											Token t1 = tablaSimbolo.getToken(((Token) $1.obj).getNombre() ) ;
							    			if (t1 == null) 
        			 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));


											//agregar estructuras
											analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() )); }								
						| celda_matriz  S_RESTA_RESTA { 	
															//agregando terceto
															String valor = "-";
															Terceto terceto = new Terceto ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( (Token)$1.obj ), new TercetoSimple( new Token("_i1",analizadorL.CTEI) ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
																									
															//chequeo semantico variable no declarada
															Token t1 = tablaSimbolo.getToken( "mat@" + (  (Token) val_peek(0).obj).getNombre() ) ;
											    			if (t1.getTipo() == null) 
        			 											analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

															//agregar estructuras
															analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));								
													}	
						;

asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION expresion { String valor =":=";
																	Terceto terceto = new Terceto ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
																	controladorTercetos.addTerceto (terceto);
																	$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
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

expresion : 
/*Error reduce/reduce
		operador_menos_menos {
									//TODO
								} 
		|*/ expresion '+' termino	{ 	String valor ="+";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
									}
      | expresion '-' termino 		{	String valor ="-";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));

									}
      | termino						
;


termino : termino '*' factor	{	String valor ="*";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
								}
    | termino '/' factor		{ String valor ="/";
										Terceto terceto = new Terceto ( new TercetoSimple( new Token("/",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										$$ = new ParserVal( new Token( controladorTercetos.numeroTercetoString() ) );
    							}
    | factor					
;

factor : CTEI  { Token t= (Token) $1.obj;
				 t.setTipo(analizadorL.constanteI);
				 $$ = new ParserVal( (Token)t ); }
        | CTEL {  Token t= (Token) $1.obj;
				  t.setTipo(analizadorL.constanteL);
				  $$ = new ParserVal( (Token)t ); }
        | ID   { 
 				 Token t1 = tablaSimbolo.getToken( "var@" + ((Token) $1.obj).getNombre() ) ;
 				 $$ = new ParserVal( t1 );

    			 if (t1 == null) {
        			 analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
    			 }
         }
        | operador_menos_menos 
		| celda_matriz { $$ = new ParserVal( (Token)$1.obj ); }
;
 
print : PRINT '(' MULTI_LINEA ')' ';' {	Terceto terceto = new Terceto ( new TercetoSimple( (Token)$1.obj ),new TercetoSimple( (Token)$3.obj ), null, controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
		  | PRINT '(' error ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }	 
		  | PRINT '(' MULTI_LINEA ')' error{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
		  | error '(' MULTI_LINEA ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
		    ;

cuerpo_for : sentencia 
		   | '{' bloque_de_sentencia '}' 
		   ;

sentencia_for : FOR  '(' asignacion {controladorTercetos.apilarFor();}
				 condicion_sin_parentesis {	Terceto terceto = new Terceto ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
											controladorTercetos.addTerceto (terceto);
											controladorTercetos.apilar(); 
														}
				 ';' asignacion_sin_punto_coma ')' 
				cuerpo_for { 	
								Terceto terceto = new Terceto ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
								controladorTercetos.addTerceto (terceto);
								controladorTercetos.desapilar();
								controladorTercetos.desapilarFor();

								analizadorS.addEstructura (new Error ( analizadorS.estructuraFOR,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); }
              | ID '(' asignacion condicion_sin_parentesis ';' asignacion_sin_punto_coma ')' cuerpo_for { analizadorS.addError (new Error ( analizadorS.errorPalabraFOR,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
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



sentecia_if_condicion : IF  condicion 	{	Terceto terceto = new Terceto ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
											controladorTercetos.addTerceto (terceto);
											controladorTercetos.apilar(); 
										}
					  ;

sentencia_seleccion  : sentecia_if_condicion  cuerpo_if ELSE {	
													Terceto terceto = new Terceto ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
													controladorTercetos.addTerceto (terceto);
													controladorTercetos.desapilar();
													controladorTercetos.apilar();
										}
						cuerpo_else ENDIF ';' { 	controladorTercetos.desapilar();
													analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                 	 | error  condicion  cuerpo_if ELSE cuerpo_else ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }                     
                                        	 
                     | sentecia_if_condicion  cuerpo_if ENDIF ';' { controladorTercetos.desapilar();
                     												analizadorS.addEstructura (new Error ( analizadorS.estructuraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                     | sentecia_if_condicion cuerpo_if ENDIF error { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                     | error  condicion  cuerpo_if ENDIF ';' { analizadorS.addError (new Error ( analizadorS.errorPalabraIF,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }

                     ;

condicion_sin_parentesis : expresion operador expresion {	Terceto terceto = new Terceto ( new TercetoSimple( (Token)$2.obj ) ,new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
															analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); 
														}
                          |  error operador expresion { analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                          |  expresion operador error  { analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                          ;


condicion : '(' condicion_sin_parentesis ')' 
          | condicion_sin_parentesis ')' { analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '(' condicion_sin_parentesis error { analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          ;


tipo : INTEGER {  $$ = new ParserVal(  new Token( analizadorL.variableI ) ); }
     | LONGINT {  $$ = new ParserVal(  new Token( analizadorL.variableL ) ); }
     ; 

celda_matriz : ID '[' expresion ']' '[' expresion ']' { Token t1 = tablaSimbolo.getToken( "mat@" + ((Token) $1.obj).getNombre() ) ;
														$$ = new ParserVal( t1 );

														if (t1 == null) {
															tablaSimbolo.borrarSimbolo( ((Token) $1.obj).getNombre() );
														 	analizadorCI.addError (new Error ( analizadorCI.errorNoExisteMatriz,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
														}
    			  }
             | ID '[' error ']' '[' expresion ']'  { analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
             | ID '[' expresion ']' '[' error ']'  { analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
             | ID '[' error ']' '[' error ']'  { analizadorS.addError (new Error ( analizadorS.errorCeldaMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }

             ;
             
operador : '<' 				{ String valor = "<";
							  $$ = new ParserVal(  new Token("<",(int) valor.charAt(0) ) ); }
		 | '>' 				{ String valor = ">";
		 					  $$ = new ParserVal(  new Token(">",(int) valor.charAt(0) ) );
							}
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