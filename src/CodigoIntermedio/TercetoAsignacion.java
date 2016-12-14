package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.CeldaAS;
import AnalizadorLexico.Token;
import AnalizadorLexico.TokenMatriz;

public class TercetoAsignacion extends Terceto{

	private String registroAux;
	private boolean actVar = false;
	String registro2 = "";
	String tipo = "integer";
	public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public void set_actVar(boolean actVar){
		this.actVar = actVar;
	}
	
	public String getAssembler() {
		String assembler = "";
		Terceto terceto2 = null;
		if (!elementos.get(2).esToken())
			terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
	

		//tire aca arriba lo de la matriz
		//tendria que ser todo codigo de matrices, sino le erre al sol el conflicto
			//Si es una matriz tengo que hacer el chequeo de rango
		
				if (( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() )) {
					//caso 1: (ASIG, variable, variable)
					//Si es una matriz tengo que hacer el chequeo de rango
					if(elementos.get(1).getNombreVar().startsWith("mat")){
						if(elementos.get(2).getNombreVar().startsWith("mat")){
							//Ambas variables son matrices.
							
							String regAux = controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro();
							String aux = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
							assembler = assembler + "MOV " + aux +  ", " +  elementos.get(2).getNombreVar() + "[" + regAux  + "]" +"\n";
							
							assembler = assembler + "MOV " + regAux +  ", " + controladorTercetos.getRegMatriz(0) + "\n";
							controladorTercetos.liberarRegistro(controladorTercetos.getRegMatriz(0));
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[" +regAux + "]" +", " + aux +"\n";
							controladorTercetos.liberarRegistro(controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro());
							controladorTercetos.liberarRegistro(aux);
						}
						else{
							assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(1).getToken());
							String regAux = controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro(); 
							String aux = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
							assembler = assembler + "MOV " + aux +"," +elementos.get(2).getNombreVar()+"\n";
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[" + regAux + "]" +"," +aux+"\n";
							controladorTercetos.liberarRegistro(regAux);
							controladorTercetos.liberarRegistro(aux);
						}
					}else 
						if(elementos.get(2).getNombreVar().startsWith("mat")){
							String aux = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
							assembler = assembler + "MOV " + aux +"," +elementos.get(2).getNombreVar()+"\n";
							String regAux = controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro(); 
							assembler = assembler + "MOV " + aux +"," +elementos.get(2).getNombreVar()+"[" + regAux +"]" +"\n";
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() +"," +aux +"\n";
							controladorTercetos.liberarRegistro(controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro());
							controladorTercetos.liberarRegistro(aux);
						}else{
							//Ninguna de las variables es una matriz

							registro2 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
							if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
								//puede que la primer instruccion no vaya
								assembler = assembler + "MOV " + "EAX" + ", " + elementos.get(2).getNombreVar() + '\n';
								assembler = assembler + verificarConversionAsig(registro2);
								//registro2 = controladorTercetos.getRegistroInteger(registro2);
							}
							else
								if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
									assembler = assembler + "MOV " + registro2 + ", " + elementos.get(2).getNombreVar() + '\n';
									assembler = assembler + crearAssemblerConversionVar(registro2);
									registro2 = registroAux;
								}
								else{
									assembler = assembler + "MOV " + registro2 + ", " + elementos.get(2).getNombreVar() + '\n';
				
								}
					
								
							if(this.tipo.equals("longint") && elementos.get(1).getToken().getTipo().equals("integer")){
//								String regLibre = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
								assembler = assembler + "CWDE" + '\n';
								assembler = assembler + "MOV" + " " +  elementos.get(1).getNombreVar() + ", " + registro2.substring(1) + '\n';
							}else
								assembler = assembler + "MOV" + " " +  elementos.get(1).getNombreVar() + ", " + registro2 + '\n';
							controladorTercetos.liberarRegistro(registro2);
						}
				}
				else{
					//caso 2: (ASIG, variable, registro)
					registro2 = controladorTercetos.getTerceto(elementos.get(2).getNumeroTerceto() ).getRegistro();
					if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (terceto2.getTerceto(0).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
						//puede que la primer instruccion no vaya
						assembler = assembler + "MOV " + "EAX" + ", " + registro2 + '\n';
						assembler = assembler + verificarConversionAsig(registro2);
						registro2 = controladorTercetos.getRegistroInteger(registro2);
					}
					else
						if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (terceto2.getTerceto(0).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
							assembler = assembler + "MOV " + registro2 + ", " + elementos.get(2).getNombreVar() + '\n';
							assembler = assembler + crearAssemblerConversionVar(registro2);
							registro2 = registroAux;
						}
			
					if(elementos.get(1).getNombreVar().startsWith("mat")){
						
						//Este token se usa para que use un registro EX para calcular la direccion de la matriz.
						Token t = new Token("2");
						t.setTipo("longint");
						
						String regAux = controladorTercetos.getProxRegLibre(t);
						assembler = assembler + "MOV " + regAux +  ", " + controladorTercetos.getRegMatriz(0) + "\n";
						controladorTercetos.liberarRegistro(controladorTercetos.getRegMatriz(0)); 
						assembler = assembler + "MOV " +  elementos.get(1).getNombreVar() +"[" +regAux +"], " + registro2 + '\n';	
						controladorTercetos.liberarRegistro(regAux);
					}
					else
						assembler = assembler + "MOV " +  elementos.get(1).getNombreVar() + ", " + registro2 + '\n';
						controladorTercetos.liberarRegistro(registro2);			
				}
				controladorTercetos.liberarRegistro(registro2);			
			return assembler;
	}
		
	private String crearAssemblerConversionVar(String registro){
		String assembler = "";
		assembler = assembler + "MOV"  + " " + "AX" + ", " + registro + '\n';
		assembler = assembler + "CWDE" + '\n';
		controladorTercetos.liberarRegistro(registro);
		elementos.get(2).getToken().setTipo(AnalizadorLexico.variableL);
		registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
		registroAux = registro;
		assembler = assembler + "MOV"  + " " + registro + ", " + "EAX" + '\n';
		elementos.get(2).getToken().setTipo(AnalizadorLexico.variableI);
		//TODO: VA O NO VA? 
		controladorTercetos.liberarRegistro(registro);
		return assembler;
	}
	
	public String verificarMatriz(TokenMatriz tokenMatriz){
		String assembler = "";
		assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+ "," +(tokenMatriz.getColumnas()*tokenMatriz.getFilas()*4) + "\n";
		assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
		return assembler;
	}
	
	private String verificarConversionAsig(String registro2){
		this.tipo = "longint";
		
		String assembler = "";
		String reg = "EAX";
		controladorTercetos.OcuparRegistro("EAX");
		registroAux=reg;
		assembler = assembler + "CMP " + reg + ", 0"  + '\n';
		assembler = assembler + "JL LabelNEG" + controladorTercetos.getNumTercetoActual() + '\n';
		//es positivo el valor
		String maximo = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		assembler = assembler + "MOV " + registro2 + ", EAX\n";
		assembler = assembler + "MOV " + maximo + ", " + CeldaAS.maximo + '\n';
		assembler = assembler + "SUB EAX, " + maximo + '\n';
		assembler = assembler + "CMP EAX, 0" + '\n';
		assembler = assembler + "JG LabelERRORPERDIDA" + '\n';
		assembler = assembler + "JMP labelSIGUE" + controladorTercetos.getNumTercetoActual() + '\n';
		controladorTercetos.liberarRegistro(maximo);		
		
		//es negativo el valor
		assembler = assembler + "LabelNEG" + controladorTercetos.getNumTercetoActual() +":" + '\n';
		String minimo = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		assembler = assembler + "MOV " + registro2 + ", EAX\n";
		assembler = assembler + "MOV " + minimo + ", " + CeldaAS.minimo + '\n';
		assembler = assembler + "SUB EAX, " + minimo + '\n';
		assembler = assembler + "CMP EAX, " +  "0" + '\n';
		assembler = assembler + "JL LabelERRORPERDIDA" + '\n';
		controladorTercetos.liberarRegistro(minimo);
		controladorTercetos.liberarRegistro("EAX");
		assembler = assembler + "labelSIGUE" + controladorTercetos.getNumTercetoActual() + ":" + '\n';
		return assembler;
		
	}

}
