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
%token S_DISTINTO
%%

%{
package AnalizadorSintactico;
import java.util.ArrayList;
import AnalizadorLexico.*;
import AnalizadorLexico.Error;
import AnalizadorSintactico.*;
import CodigoIntermedio.*;
import java.util.ArrayList;
import CodigoIntermedio.*;
%}

programa_principal  : ID declaraciones '{' bloque_de_sentencia '}' { 
												tablaSimbolo.addUso(((Token)$1.obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                    | ID '{' bloque_de_sentencia '}' { 
												tablaSimbolo.addUso(((Token)$1.obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addEstructura (new Error ( analizadorS.estructuraPRINCIPAL,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                    | ID declaraciones '{' bloque_de_sentencia  { analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                    | ID declaraciones bloque_de_sentencia '}'  { analizadorS.addError (new Error ( analizadorS.errorLlaveA,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
                    | error declaraciones '{' bloque_de_sentencia '}' { analizadorS.addError (new Error ( analizadorS.errorProgram,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    | ID error '{' bloque_de_sentencia '}'    { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
                    | ID declaraciones '{' '}'         { 
												tablaSimbolo.addUso(((Token)$1.obj).getNombre(),analizadorS.usoNombrePrograma);
												analizadorS.addError (new Error ( analizadorS.errorSentencias,"ERROR SINTACTICO", controladorArchivo.getLinea() )); } 
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
												tablaSimbolo.addUso(t1.getNombre(),analizadorS.usoVariable);
	 											} 												
	 											
											}
										//agregar estructura	
										 analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
										 }

			| tipo error ';' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionVar,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
			
										 
		    | tipo lista_variables { analizadorS.addError (new Error ( analizadorS.errorPuntoComa,"ERROR SINTACTICO",    controladorArchivo.getLinea() )); }
		    | error lista_variables ';' { analizadorS.addError (new Error ( analizadorS.errorTipo,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
            
            | matriz { 
							/*Chequear que la variable ya no este declarada*/
							TokenMatriz t = (TokenMatriz)$1.obj;
							TokenMatriz t1 = new TokenMatriz("mat@" + t.getNombre(), t.getUso(), t.getFilas(), t.getColumnas() );
							
							if (tablaSimbolo.existe(t1.getNombre())){
									analizadorCI.addError (new Error ( analizadorCI.errorMatrizRedeclarada,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
							}
							else{
								//la variable no fue declarada
								String tipo = t.getTipo();
								t1.setTipo(tipo);
								t1.setMatriz(t.getMatriz());
								tablaSimbolo.addSimbolo(t1);
								tablaSimbolo.addUso(t1.getNombre(),analizadorS.usoVariableArreglo);
								System.out.println(t1.getNombre());
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
                			$$ = new ParserVal(lista); }
                ;

declaracion_matriz : MATRIX ID '[' CTEI ']' '[' CTEI ']' {  Token t = (Token) $2.obj ;
															TokenMatriz tm = new TokenMatriz(t.getNombre(), t.getUso(), ((Token)$4.obj).getValor(), ((Token)$7.obj).getValor());
															$$ = new ParserVal( tm );
															analizadorS.addEstructura (new Error ( analizadorS.estructuraDECLARACION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); 
														 }
				   | MATRIX error '[' CTEI ']' '[' CTEI ']' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   | MATRIX ID '[' error ']' '[' CTEI ']' { analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   | MATRIX ID '[' CTEI ']' '[' error ']'	{ analizadorS.addError (new Error ( analizadorS.errorDeclaracionMatriz,"ERROR SINTACTICO", controladorArchivo.getLinea() )); }
				   ;

matriz : tipo declaracion_matriz inicializacion ';' anotacion { 	
															TokenMatriz declaracion_matriz = (TokenMatriz) $2.obj;
															String tipo = ((Token)$1.obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															String orientacion = $5.sval;
															ArrayList<ArrayList<Token>> valores = (ArrayList<ArrayList<Token>>) $3.obj;
															
															setTercetosMatriz(orientacion,valores,declaracion_matriz);
															$$ = new ParserVal(declaracion_matriz);
														}

       | tipo declaracion_matriz ';' anotacion 				{	
       														TokenMatriz declaracion_matriz = (TokenMatriz) $2.obj;
       														String tipo = ((Token)$1.obj).getNombre();
       														String orientacion = $4.sval;
															declaracion_matriz.setTipo(tipo);

															setTercetosMatriz(orientacion,null,declaracion_matriz);	
															$$ = new ParserVal(declaracion_matriz);
															}
       | tipo declaracion_matriz inicializacion ';'{
       														TokenMatriz declaracion_matriz = (TokenMatriz) $2.obj;
       														String tipo = ((Token)$1.obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															ArrayList<ArrayList<Token>> valores = (ArrayList<ArrayList<Token>>) $3.obj;
															setTercetosMatriz("F",valores,declaracion_matriz);
															$$ = new ParserVal(declaracion_matriz);
													}
       | tipo declaracion_matriz ';'{						
   															TokenMatriz declaracion_matriz = (TokenMatriz) $2.obj;
   															String tipo = ((Token)$1.obj).getNombre();
															declaracion_matriz.setTipo(tipo);	
															setTercetosMatriz("F",null,declaracion_matriz);
															$$ = new ParserVal(declaracion_matriz);
									}

       ;


anotacion : ANOTACIONC {$$ = new ParserVal("C");}
          | ANOTACIONF {$$ = new ParserVal("F");}
          ;
inicializacion : '{' filas '}' {$$ = new ParserVal($2.obj);}
                ;

filas : filas ';' fila {ArrayList<ArrayList<Token>> lista = (ArrayList<ArrayList<Token>>)$1.obj;
						lista.add(((ArrayList<Token>)$3.obj));
						$$ = new ParserVal(lista);
					}
      | fila	{
      				ArrayList<ArrayList<Token>> lista = new ArrayList<>();
      				lista.add((ArrayList<Token>)$1.obj);
      				$$ = new ParserVal(lista);}
      ;

fila : fila ',' CTEI {ArrayList<Token> lista = (ArrayList<Token>)$1.obj;
						lista.add(((Token)$3.obj));
						$$ = new ParserVal(lista);
					}
      | fila ',' CTEL {ArrayList<Token> lista = (ArrayList<Token>)$1.obj;
						lista.add(((Token)$3.obj));
						$$ = new ParserVal(lista);
      }
      | CTEI {	ArrayList<Token> lista = new ArrayList<>();
      			Token t= (Token) $1.obj;
			 	t.setTipo(analizadorL.variableI);
				lista.add(t); 
      			$$ = new ParserVal( lista );
      		}

	  | CTEL {  ArrayList<Token> lista = new ArrayList<>();
      			Token t= (Token) $1.obj;
			 	t.setTipo(analizadorL.variableL);
				lista.add(t); 
      			$$ = new ParserVal( lista );
	  }
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
						Token t1 = tablaSimbolo.getToken( "var@" + ( (Token) $1.obj).getNombre() );
						
		    			if  ( t1 == null ) 
							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

						$$ = new ParserVal( t1 );}
    
            	| celda_matriz {//chequeo semantico variable no declarada
						Token t1 = tablaSimbolo.getToken(((Token) $1.obj).getNombre()) ;
						System.out.println(t1.getNombre());
				    			if (t1 == null) 
		 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
								$$ = new ParserVal( (Token) $1.obj );}
                ;

operador_menos_menos : ID S_RESTA_RESTA { 	//Se realiza la resta
											String valor = "-";
											Token t1 = tablaSimbolo.getToken("var@" + ((Token) $1.obj).getNombre() ) ;
											TercetoExpresion terceto;

											if ( t1.getTipo() == analizadorL.variableI ){
												Token cteAux =  new Token("_i1",analizadorL.CTEI) ;
												cteAux.setTipo(analizadorL.variableI);	
												cteAux.setValor(1);
												terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( t1 ), new TercetoSimple( cteAux ),  controladorTercetos.getProxNumero() );
											}
											else{	
												Token cteAux =  new Token("_l1",analizadorL.CTEL) ;
												cteAux.setTipo(analizadorL.variableL);	
												cteAux.setValor(1);
												terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( t1 ), new TercetoSimple( cteAux ),  controladorTercetos.getProxNumero() );
											}
											controladorTercetos.addTerceto (terceto);

											//Se realiza la asignacion							
											valor =":=";
											TercetoAsignacion tercetoAsignacion = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( t1 ), new TercetoSimple( new Token( controladorTercetos.numeroTercetoString() ) ), controladorTercetos.getProxNumero() );
											controladorTercetos.addTerceto (tercetoAsignacion);
											analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
											
											$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ) );
											
											//chequeo semantico variable no declarada
							    			if (t1 == null) 
        			 							analizadorCI.addError (new Error ( analizadorCI.errorNoExisteVariable,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));

											//agregar estructuras
											analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() )); }								
						| celda_matriz  S_RESTA_RESTA { 	
															//agregando terceto
															String valor = "-";

															TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ) ,new TercetoSimple( (Token)$1.obj ), new TercetoSimple( new Token("_i1",analizadorL.CTEI) ), controladorTercetos.getProxNumero() );
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

asignacion_sin_punto_coma : lado_izquierdo S_ASIGNACION expresion { 
																	analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
																	
																	String valor =":=";	
																	Token t1 = (Token) $1.obj;
																	Token t2 = (Token) $3.obj;
																	
																	if ( (t1 != null) && (t2 != null) ){
																		if(!tipoCompatible(t1,t2))
																			analizadorCI.addError (new Error ( analizadorCI.errorFaltaAllow,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
																	}
																	
																	//if ( (t1 != null) && (t2 != null) )
																	//	if ((t1.getTipo().equals("longint")) && (t2.getTipo().equals("integer")))
																	//		t2.setTipo("longint");
																	TercetoAsignacion terceto;
																	terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( t2 ), controladorTercetos.getProxNumero() );
																	
																	controladorTercetos.addTerceto (terceto);								

																	$$ = new ParserVal((Token)$1.obj);
																	}
																	
                           | operador_menos_menos { analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
                           | lado_izquierdo S_ASIGNACION error { analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }      
                           | error S_ASIGNACION expresion { analizadorS.addError (new Error ( analizadorS.errorAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                           | lado_izquierdo '=' expresion { analizadorS.addError (new Error ( analizadorS.errorSimboloAsignacion,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }

asignacion :  asignacion_sin_punto_coma ';' 
		;

expresion : expresion '+' termino	{ 	
										String valor ="+";
										String tipo = getTipoCompatibleSuma((Token)$1.obj,(Token)$3.obj);
										TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() ) ;
										nuevo.setTipo(tipo);
										$$ = new ParserVal(nuevo);
									}
      | expresion '-' termino 		{	String valor ="-";
      									String tipo = getTipoCompatibleSuma((Token)$1.obj,(Token)$3.obj);
										TercetoExpresion terceto = new TercetoExpresion ( new TercetoSimple( new Token("-",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() ) ;
										nuevo.setTipo(tipo);
										$$ = new ParserVal(nuevo);
									}
      | termino						
;


termino : termino '*' factor	{	String valor ="*";
										TercetoExpresionMult terceto = new TercetoExpresionMult ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
										nuevo.setTipo("longint");
										$$ = new ParserVal(nuevo);
								}
    | termino '/' factor		{ String valor ="/";
    									String tipo = getTipoCompatibleDivision((Token)$1.obj,(Token)$3.obj);
										TercetoExpresionDiv terceto = new TercetoExpresionDiv ( new TercetoSimple( new Token("/",(int) valor.charAt(0) ) ),new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
										nuevo.setTipo(tipo);
										$$ = new ParserVal(nuevo);
    							}
    | factor					
;

factor : CTEI  { Token t= (Token) $1.obj;
				 t.setTipo(analizadorL.variableI);
				 $$ = new ParserVal( (Token)t ); }
        | CTEL {  Token t= (Token) $1.obj;
				  t.setTipo(analizadorL.variableL);
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
 
print : PRINT '(' MULTI_LINEA ')' ';' {	TercetoPrint terceto = new TercetoPrint ( new TercetoSimple( (Token)$1.obj ),new TercetoSimple( (Token)$3.obj ), null, controladorTercetos.getProxNumero() );
										controladorTercetos.addTerceto (terceto);
										controladorTercetos.addPrint( ((Token)$3.obj).getNombre() );
										analizadorS.addEstructura (new Error ( analizadorS.estructuraPrint,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea()  )); }
		  | PRINT '(' error ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }	 
		  | PRINT '(' MULTI_LINEA ')' error{ analizadorS.addError (new Error ( analizadorS.errorPrint1,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
		  | error '(' MULTI_LINEA ')' ';' { analizadorS.addError (new Error ( analizadorS.errorPrint2,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
		    ;

cuerpo_for : sentencia 
		   | '{' bloque_de_sentencia '}' 
		   ;

sentencia_for_parte1:	FOR  '(' asignacion {
				 									TercetoLabel tercetoLabel = new TercetoLabel(null,null,null,controladorTercetos.getProxNumero());
				 									controladorTercetos.addTerceto(tercetoLabel);
				 									controladorTercetos.apilarFor();
				 								}
				 		condicion_sin_parentesis {
				 									TercetoFor terceto = new TercetoFor ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
													terceto.setTipoSalto(((Token)$5.obj).getNombre());
													controladorTercetos.addTerceto(terceto);	
													controladorTercetos.apilar();	
													$$ = new ParserVal ($3.obj);
											 }
					;

sentencia_for : sentencia_for_parte1 ';' asignacion_sin_punto_coma ')' 	{ 
											 			Token asig = (Token)$1.obj;
														Token asigUlt = (Token)$3.obj;
				 										if (controladorTercetos.errorControlFOR(asig,asigUlt) )
	 														analizadorCI.addError (new Error ( analizadorCI.errorVariableControlFOR,"ERROR DE GENERACION DE CODIGO INTERMEDIO", controladorArchivo.getLinea()  ));
				 									}
				cuerpo_for { 	
								TercetoFor terceto = new TercetoFor ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
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



sentecia_if_condicion : IF  condicion 	{	TercetoIf terceto = new TercetoIf ( new TercetoSimple( (new Token( controladorTercetos.BF) ) ), new TercetoSimple(new Token( controladorTercetos.numeroTercetoString() ) ), null, controladorTercetos.getProxNumero() );
											terceto.setTipoSalto(((Token)$2.obj).getNombre());
											controladorTercetos.addTerceto (terceto);
											controladorTercetos.apilar(); 
										}
					  ;

sentencia_seleccion  : sentecia_if_condicion  cuerpo_if ELSE {	
													TercetoIf terceto = new TercetoIf ( new TercetoSimple( new Token( controladorTercetos.BI)  ), null, null, controladorTercetos.getProxNumero() );
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

condicion_sin_parentesis : expresion operador expresion {	TercetoComparacion terceto = new TercetoComparacion ( new TercetoSimple( (Token)$2.obj ) ,new TercetoSimple( (Token)$1.obj ), new TercetoSimple( (Token)$3.obj ), controladorTercetos.getProxNumero() );
															controladorTercetos.addTerceto (terceto);
															String tipo;
															if ((((Token)$1.obj).getTipo().equals("longint")) || (((Token)$3.obj).getTipo().equals("longint")))
																tipo = "longint";
															else
																tipo= "integer";
															Token nuevo = new Token( controladorTercetos.numeroTercetoString() );
															nuevo.setTipo(tipo);
															nuevo.setNombre(((Token) $2.obj).getNombre());
															$$ = new ParserVal(nuevo);
															analizadorS.addEstructura( new Error ( analizadorS.estructuraCONDICION,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ) ); 
														}
                          |  error operador expresion { analizadorS.addError (new Error ( analizadorS.errorCondicionI,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                          |  expresion operador error  { analizadorS.addError (new Error ( analizadorS.errorCondicionD,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
                          ;


condicion : '(' condicion_sin_parentesis ')' { $$ = $2;}
          | condicion_sin_parentesis ')' { analizadorS.addError (new Error ( analizadorS.errorParentesisA,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          | '(' condicion_sin_parentesis error { analizadorS.addError (new Error ( analizadorS.errorParentesisC,"ERROR SINTACTICO", controladorArchivo.getLinea()  )); }
          ;


tipo : INTEGER {  $$ = new ParserVal(  new Token( analizadorL.variableI ) ); }
     | LONGINT {  $$ = new ParserVal(  new Token( analizadorL.variableL ) ); }
     ; 

celda_matriz : ID '[' expresion ']' '[' expresion ']' { Token t1 = tablaSimbolo.getToken( "mat@" + ((Token) $1.obj).getNombre() ) ;
														//calcular la posicion de memoria de la celda
														String bits = "_l" + ((TokenMatriz)t1).getBits(); 
														//cantidad de filas y columnas de la matriz
														String filas = "_l" + String.valueOf( ( (TokenMatriz) t1).getFilas() );
														String columnas = "_l" +String.valueOf( ((TokenMatriz) t1).getColumnas() );
														if ( ((TokenMatriz) t1).porFilas() ){
															Token filaBuscada = (Token) $3.obj;
															Token colBuscada = (Token) $6.obj;
															filaBuscada.setTipo(analizadorL.variableL);
															colBuscada.setTipo(analizadorL.variableL);
															String valor;
															
															if (filaBuscada.getNombre().startsWith("mat@")) {
																//La fila se accede accediendo a una posicion de una matriz
																valor = "*";
																TercetoExpresion tercetoMult = new TercetoExpresion ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(filas,analizadorL.CTEL) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoMult);
															}else{
																valor = "*";
																TercetoExpresionMult tercetoMult = new TercetoExpresionMult ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( filaBuscada ), new TercetoSimple( new Token(filas,analizadorL.CTEL) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoMult);
															}

															if (colBuscada.getNombre().startsWith("mat@")) {
																//La fila se accede accediendo a una posicion de una matriz
																valor = "+";
																TercetoExpresion tercetoSuma = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoSuma);
															}else{
																valor = "+";
																TercetoExpresion tercetoSuma = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( colBuscada ), controladorTercetos.getProxNumero() );
																controladorTercetos.addTerceto (tercetoSuma);
															}

															valor = "*";

															TercetoControl tercetoControl = new TercetoControl (  new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(bits,analizadorL.CTEL) ), (TokenMatriz) t1,controladorTercetos.getProxNumero() );
																
															//TercetoExpresion tercetoMultBits = new TercetoExpresion ( new TercetoSimple( new Token("*",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( new Token(bits,analizadorL.CTEL) ), controladorTercetos.getProxNumero() );
															
															//tercetoMultBits.setPosicion(Integer.parseInt(controladorTercetos.numeroTercetoString()));
															controladorTercetos.addTerceto (tercetoControl);

															// TercetoControl TercetoContro = new TercetoControl(new TercetoSimple(new Token(":=",(int) (:=) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-2) )) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), controladorTercetos.getProxNumero() );

															// //Se realiza la asignacion							
															// valor =":=";
															// TercetoAsignacion tercetoAsignacion = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( t1 ), new TercetoSimple( new Token( controladorTercetos.numeroTercetoString() ) ), controladorTercetos.getProxNumero() );
															// controladorTercetos.addTerceto (tercetoAsignacion);
															// analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
											
														 	//TercetoAsignacion tercetoAsig = new TercetoAsignacion( new TercetoSimple( new Token(":=",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( t1 ), controladorTercetos.getProxNumero() );	
															//controladorTercetos.addTerceto(tercetoAsig);
															TokenMatriz ret = new TokenMatriz( new Token(controladorTercetos.numeroTercetoString()), (( (TokenMatriz) t1).getFilas() ), (( (TokenMatriz) t1).getColumnas() ) );
															ret.setNombre(t1.getNombre());
															ret.setTipo(t1.getTipo());

															$$ = new ParserVal(ret);

															//suma de la base con el calculo de los bytes que tengo que saltar
															//valor = "+";
															//TercetoExpresion tercetoSumaBase = new TercetoExpresion ( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( t1 ), controladorTercetos.getProxNumero() );
															//controladorTercetos.addTerceto (tercetoSumaBase);
															//TercetoAsignacion tercetoAsig = new TercetoAsignacion( new TercetoSimple( new Token("+",(int) valor.charAt(0) ) ),new TercetoSimple( (new Token( String.valueOf( controladorTercetos.getProxNumero()-1) )) ), new TercetoSimple( t1 ), controladorTercetos.getProxNumero() );
															//controladorTercetos.addTerceto(tercetoAsig);


														}
														//$$ = new ParserVal( t1 );

														if (t1 == null) {
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
		 | S_DISTINTO		{ String valor = "!=";
		 					  $$ = new ParserVal(  new Token("!=",analizadorL.S_DISTINTO )); }
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
		if(t1.getTipo().equals("integer")){
			if(t2.getTipo().equals("integer")){
				return true;
			}
			else
				if(t2.getTipo().equals("longint")){
					if(allow){
						return true;
					}
					else 
						return false;
				}
		}else{
			if(t1.getTipo().equals("longint")){
				if(t2.getTipo().equals("integer"))
					if(allow)
						return true;
					else
						return false;
				else
					if(t2.getTipo().equals("longint"))
						return true;
			}
		}
}
		return false;
}

public String getTipoCompatibleSuma (Token t1,Token t2){
	if ((t1.getTipo().equals("longint")) || (t2.getTipo().equals("longint")))
		return "longint";
	else
		return "integer";
}
public String getTipoCompatibleDivision (Token t1,Token t2){
	if ((t1.getTipo().equals("longint")) && (t1.getTipo().equals("longint")))
		return "longint";
	else if ((t1.getTipo().equals("longint")) && (t1.getTipo().equals("integer")))
			return "longint";
	else
		return "integer";
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

public Token[][] getMatriz(ArrayList<ArrayList<Token>> tokens, TokenMatriz declaracion_matriz){
	Token[][] arregloTokens = new Token[declaracion_matriz.getFilas()][declaracion_matriz.getColumnas()]; 
	int caux = 0, faux = 0;
	for(ArrayList<Token> a : tokens){
		for(Token t : a){
			arregloTokens[caux][faux] = t;
			caux++;		
		}
		caux=0;
		faux++;
	}

	return arregloTokens;
}

public boolean setTercetosMatriz(String orientacion, ArrayList<ArrayList<Token>> tokens, TokenMatriz declaracion_matriz){
	Token matriz[][];
	Token inicializador;
	String tipo = declaracion_matriz.getTipo();
	if (tokens!=null) {
		matriz = getMatriz(tokens, declaracion_matriz);
		if ((orientacion.equals("C"))) {
			//Orientacion por columnas
			for (int caux = 0; caux < declaracion_matriz.getColumnas() ; caux++ ) {
				for (int faux = 0; faux < declaracion_matriz.getFilas() ; faux++ ) {
					Token t = matriz[faux][caux];
					if (tipoCompatible(declaracion_matriz,t)) {

						if (tipo.equals("integer")) 
							inicializador = new Token("_i0", analizadorL.CTEI);
						else
							inicializador = new Token("_l0", analizadorL.CTEL);
						inicializador.setValor(0);			

						//TercetoAsignacion terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( inicializador ), new TercetoSimple( t ), controladorTercetos.getProxNumero() );
						//controladorTercetos.addTerceto (terceto);
						//$$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
						analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
					}
					else{
						//ERROR de compatibilidad
						return false;
					}		
				}
			}
		}else{
			//Orientacion por filas
			for (int faux = 0; faux < declaracion_matriz.getFilas() ; faux++ ) {
				for (int caux = 0; caux < declaracion_matriz.getColumnas() ; caux++ ) {
					Token t = matriz[faux][caux];
					if (tipoCompatible(declaracion_matriz,t)) {

						if (tipo.equals("integer")) 
							inicializador = new Token("_i0", analizadorL.CTEI);
						else
							inicializador = new Token("_l0", analizadorL.CTEL);
						inicializador.setValor(0);			

						//Terceto terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( inicializador ), new TercetoSimple( t ), controladorTercetos.getProxNumero() );
						// controladorTercetos.addTerceto (terceto);
						// $$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
						analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
					}
					else{
						//ERROR de compatibilidad
						return false;
					}		
				}
			}
		}
	}else{
		matriz = new Token[declaracion_matriz.getFilas()][declaracion_matriz.getColumnas()]; 
		//No se paso una lista de valores para inicializar la matriz, por lo tanto
		//lo creo como si fuera por filas pero con tokens de valor 0
		 
		for (int caux = 0; caux < declaracion_matriz.getColumnas() ; caux++ ) {
			for (int faux = 0; faux < declaracion_matriz.getFilas() ; faux++ ) {
				//No es necesario chequear la compatibilidad
				if (tipo.equals("integer")) 
					inicializador = new Token("_i0", analizadorL.CTEI);
				else
					inicializador = new Token("_l0", analizadorL.CTEL);
				inicializador.setValor(0);			

				Token t = matriz[faux][caux];	
				// Terceto terceto = new TercetoAsignacion ( new TercetoSimple( new Token(":=",analizadorL.S_ASIGNACION ) ),new TercetoSimple( inicializador ), new TercetoSimple( inicializador ), controladorTercetos.getProxNumero() );
				// controladorTercetos.addTerceto (terceto);
				// $$ = new ParserVal(new Token( controladorTercetos.numeroTercetoString() ));
				analizadorS.addEstructura (new Error ( analizadorS.estructuraASIG,"ESTRUCTURA SINTACTICA", controladorArchivo.getLinea() ));
			}
		}
	}
	declaracion_matriz.setMatriz(matriz);
	return true;
}

void yyerror(String s) {
	if(s.contains("under"))
		System.out.println("par:"+s);
}