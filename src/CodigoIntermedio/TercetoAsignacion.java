package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.CeldaAS;
import AnalizadorLexico.TokenMatriz;

public class TercetoAsignacion extends Terceto{

	private String registroAux;
	private boolean actVar = false;
	
	public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public void set_actVar(boolean actVar){
		this.actVar = actVar;
	}
	
	public String getAssembler() {
		String assembler = "";
		String registro2 = "";

		//tire aca arriba lo de la matriz
		//tendria que ser todo codigo de matrices, sino le erre al sol el conflicto
			//Si es una matriz tengo que hacer el chequeo de rango
		
				if (( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() )) {
					//caso 1: (ASIG, variable, variable)

					registro2 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
						
					//Si es una matriz tengo que hacer el chequeo de rango
					if(elementos.get(1).getNombreVar().startsWith("mat")){
						if(elementos.get(2).getNombreVar().startsWith("mat")){
							//Ambas variables son matrices.
							assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[" + elementos.get(1).getNombreVar().substring(4, elementos.get(1).getNombreVar().length())  +"]" +", " +elementos.get(2).getNombreVar()+"[EBX]" +"\n";
							controladorTercetos.liberarRegistro(controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro());
//							assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(1).getToken());
//							assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(2).getToken());
						}
						else{
							System.out.print("registros libres a la matri:");
							System.out.println(controladorTercetos.getCantRegistros());
//							assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(1).getToken());
							assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[EBX]" +"," +elementos.get(2).getNombreVar()+"\n";
							controladorTercetos.liberarRegistro(controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro());
						}
					}else 
						if(elementos.get(2).getNombreVar().startsWith("mat")){
//							assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(2).getToken());
							assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() +"," +elementos.get(2).getNombreVar()+"[EBX]" +"\n";
							controladorTercetos.liberarRegistro(controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro());
						}else{
							//Ninguna de las variables es una matriz
							
							if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
								//puede que la primer instruccion no vaya
								assembler = assembler + "MOV " + "EAX" + ", " + registro2 + '\n';
								assembler = assembler + verificarConversionAsig(registro2);
//								registro2 = controladorTercetos.getRegistroInteger(registro2);
							}
							else
								if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
									assembler = assembler + crearAssemblerConversionVar(registro2);
									registro2 = registroAux;
								}
								else
									assembler = assembler + "MOV " + registro2 + ", " + elementos.get(2).getNombreVar() + '\n';
							

							assembler = assembler +   "MOV" + " " +  elementos.get(1).getNombreVar() + ", " + registro2 + '\n';
							controladorTercetos.liberarRegistro(registro2);
						}
				}
				else{
					//caso 2: (ASIG, variable, registro)
					registro2 = controladorTercetos.getTerceto(elementos.get(2).getNumeroTerceto() ).getRegistro();
					if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
						//puede que la primer instruccion no vaya
						assembler = assembler + "MOV " + "EAX" + ", " + registro2 + '\n';
//						assembler = assembler + verificarConversionAsig(registro2);
						registro2 = controladorTercetos.getRegistroInteger(registro2);
					}
					else{
						if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
							assembler = assembler + crearAssemblerConversionVar(registro2);
							registro2 = registroAux;
						}
					}
					
					if(elementos.get(1).getNombreVar().startsWith("mat")){
						if(actVar)
							assembler = assembler + "MOV " +"[" +  elementos.get(1).getNombreVar().substring(4, elementos.get(1).getNombreVar().length())+getRegistro() +"], " + registro2 +'\n';
						else
							assembler = assembler + "MOV " + elementos.get(1).getNombreVar() +"[" +getRegistro() +"], " + registro2 +'\n';
					}
					else
						assembler = assembler + "MOV " +  elementos.get(1).getNombreVar() + ", " + registro2 + '\n';
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
		String assembler = "";
		String reg = "EAX";
		controladorTercetos.OcuparRegistro("EAX");
		registroAux=reg;
		assembler = assembler + "CMP " + reg + ", 0"  + '\n';
		assembler = assembler + "JL LabelNEG" + controladorTercetos.getNumTercetoActual() + '\n';
		//es positivo el valor
		String maximo = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		assembler = assembler + "MOV " + maximo + ", " + CeldaAS.maximo + '\n';
		assembler = assembler + "SUB EAX, " + maximo + '\n';
		assembler = assembler + "CMP EAX, 0" + '\n';
		assembler = assembler + "JG LabelERRORPERDIDA" + '\n';
		assembler = assembler + "JMP labelSIGUE" + controladorTercetos.getNumTercetoActual() + '\n';
		controladorTercetos.liberarRegistro(maximo);		
		
		//es negativo el valor
		assembler = assembler + "LabelNEG" + controladorTercetos.getNumTercetoActual() +":" + '\n';
		String minimo = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
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
