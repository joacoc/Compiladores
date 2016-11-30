package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.CeldaAS;
import AnalizadorLexico.Token;
import AnalizadorLexico.TokenMatriz;

public class TercetoExpresionDiv extends TercetoExpresion{


		private String registroAux;

		public TercetoExpresionDiv(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
			super(izq, medio, der, numeroTerceto);
			elementos.get(0).getToken().setTipo( elementos.get(1).getToken().getTipo());
		}
		
		public String getAssembler() {
			String assembler = "";
			Terceto terceto1 = null;
			if (!elementos.get(1).esToken())
				terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
			Terceto terceto2 = null;
			if (!elementos.get(2).esToken())
				terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
			String opAssembler = "IDIV";
			
			//caso 1: (OP, variable, variable)
			if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
				
				controladorTercetos.OcuparRegistro(reg4Integer);
				controladorTercetos.OcuparRegistro(reg3Integer);
				String registroDX = "";
				String registroAX = "";
				
				if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableI){	
					//dividendo en DX
					registroDX = this.reg4Integer;
					this.setRegistro(registroDX);
					//divisor en AX
					registroAX = this.reg3Integer;
					this.setRegistro(registroAX);
				}
				else{
					//dividendo en EDX
					registroDX = this.reg4Long;
					this.setRegistro(registroDX);
					//divisor en EAX
					registroAX = this.reg3Long;
					this.setRegistro(registroAX);
				
				}

				if(elementos.get(1).getNombreVar().startsWith("mat")){
					verificarMatriz((TokenMatriz)elementos.get(1).t, controladorTercetos);
					assembler = assembler + "MOV " + registroAX + ", " + elementos.get(1).getNombreVar() + "[EBX]\n";
				}else
					assembler = assembler + "MOV " + registroAX + ", " + elementos.get(1).getNombreVar() + '\n';
				
				assembler = assembler + MOV + " " + registroDX +", " + "0"  + '\n';
				assembler = assembler + "CWD" + '\n';
				
				String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
				

				if(elementos.get(2).getNombreVar().startsWith("mat")){
					verificarMatriz((TokenMatriz)elementos.get(2).t, controladorTercetos);
					assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + "[EBX]\n";
				}else
					assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + '\n';
				
				this.setRegistro(registro);
				
				//chequeamos que no sea cero el divisor
				if (elementos.get(2).getToken().getTipo() == AnalizadorLexico.constanteI)
					assembler = assembler + getAssemblerErrorDivCero(registro, true); 
				else
					assembler = assembler + getAssemblerErrorDivCero(registro, false); 
				
				assembler = assembler + opAssembler + " " + registro + '\n';
				assembler = assembler + MOV + " " + this.getRegistro() +", " + registroAX  + '\n';
				controladorTercetos.liberarRegistro(registroAX);
				controladorTercetos.liberarRegistro(registroDX);
			}
			else
				//caso 2: (OP, registro, variable)
				if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
					
					String registroDX = "";
					String registroAX = "";
					

					if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableI){	
						if (elementos.get(2).getToken().getTipo() == AnalizadorLexico.variableL){
							String registro2 = "";
							registro2 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());

							if(elementos.get(2).getNombreVar().startsWith("mat")){
								verificarMatriz((TokenMatriz)elementos.get(2).t, controladorTercetos);
								assembler = assembler + "MOV " + registro2 + ", " + elementos.get(2).getNombreVar() + "[EBX]\n";
							}else
								assembler = assembler + "MOV " + registro2 + ", " + elementos.get(2).getNombreVar() + '\n';
							
							assembler = assembler + "MOV " + "EAX" + ", " + registro2 + '\n';
							assembler = assembler + verificarPerdida(registro2);
							registro2 = controladorTercetos.getRegistroInteger(registro2);
							controladorTercetos.liberarRegistro(registro2);
						}
							
						controladorTercetos.OcuparRegistro(reg4Integer);
						controladorTercetos.OcuparRegistro(reg3Integer);
						//dividendo en DX
						registroDX = this.reg4Integer;
						this.setRegistro(registroDX);
						//divisor en AX
						registroAX = this.reg3Integer;
						this.setRegistro(registroAX);
					}
					else{
						
						if (elementos.get(2).getToken().getTipo() == AnalizadorLexico.variableI){
							String registro1 = "";
							registro1 = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
							
							assembler = assembler + "MOV " + "AX" + ", " + registro1 + '\n';
							assembler = assembler + "CWDE" + '\n';
							elementos.get(2).getToken().setTipo(AnalizadorLexico.variableL);
							controladorTercetos.liberarRegistro(registro1);
							registro1 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
							elementos.get(2).getToken().setTipo(AnalizadorLexico.variableI);
							assembler = assembler + "MOV " + registro1 + ", " + "EAX" + '\n';
						}
						
						//dividendo en EDX
						registroDX = this.reg4Long;
						this.setRegistro(registroDX);
						//divisor en EAX
						registroAX = this.reg3Long;
						this.setRegistro(registroAX);
					
					}
					assembler =assembler + MOV + " " + registroDX +", " + "0"  + '\n';
					if (terceto1 == null){

						if(elementos.get(1).getNombreVar().startsWith("mat")){
							verificarMatriz((TokenMatriz)elementos.get(1).t, controladorTercetos);
							assembler = assembler + "MOV " + registroAX + ", " + elementos.get(1).getNombreVar() + "[EBX]\n";
						}else
							assembler = assembler + "MOV " + registroAX + ", " + elementos.get(1).getNombreVar() + '\n';
					}
					else{
						assembler =assembler + MOV + " " + registroAX +", " + terceto1.getRegistro()  + '\n';
						controladorTercetos.liberarRegistro(terceto1.getRegistro());
					}
					assembler = assembler + "CWD" + '\n';
					
//					if ( (elementos.get(0).getToken().getTipo()== AnalizadorLexico.variableL) && (elementos.get(0).getToken().getTipo()== AnalizadorLexico.variableI) ){
					String registro = controladorTercetos.getProxRegLibre(elementos.get(0).getToken());
					
					if(elementos.get(2).getNombreVar().startsWith("mat")){
						verificarMatriz((TokenMatriz)elementos.get(2).t, controladorTercetos);
						assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + "[EBX]\n";
					}else
						assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + '\n';
					
					this.setRegistro(registro);
			
					assembler = assembler + getAssemblerErrorDivCero(registro, false); 
					assembler = assembler + opAssembler + " " + registro + '\n';
					assembler = assembler + MOV + " " + this.getRegistro() +", " + registroAX  + '\n';
					controladorTercetos.liberarRegistro(registroAX);
					controladorTercetos.liberarRegistro(registroDX);
				}
			return assembler;
		}

		private String getAssemblerErrorDivCero(String registro, boolean integer) {
			String registroCero = "";
			if (integer)
				registroCero = controladorTercetos.getProxRegLibre(new Token("_i0",AnalizadorLexico.CTEI));
			else
				registroCero = controladorTercetos.getProxRegLibre(new Token("_l0",AnalizadorLexico.CTEL));
			String assembler = "CMP " + registro + ", 0" + '\n';
			assembler = assembler + "JE LabelDivCero" + '\n';
			controladorTercetos.liberarRegistro(registroCero);
			return assembler;
		}
		
		private String verificarPerdida(String registro2){
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
			assembler = assembler + "CMP EAX, 0" + '\n';
			assembler = assembler + "JL LabelERRORPERDIDA" + '\n';
			controladorTercetos.liberarRegistro(minimo);
			controladorTercetos.liberarRegistro("EAX");
			assembler = assembler + "labelSIGUE" + controladorTercetos.getNumTercetoActual() + ":" + '\n';
			return assembler;
			
		}

		public static String verificarMatriz(TokenMatriz tokenMatriz, ControladorTercetos controladorTercetos){
			String assembler = "";
			assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+ "," +(tokenMatriz.getColumnas()*tokenMatriz.getFilas()*4) + "\n";
			assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
			return assembler;
		}

}
