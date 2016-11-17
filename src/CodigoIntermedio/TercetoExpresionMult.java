package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;

public class TercetoExpresionMult extends TercetoExpresion {

	public TercetoExpresionMult(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
	}
	
	public String getAssembler() {
		String assembler = "";
		Terceto terceto1 = null;
		if (!elementos.get(1).esToken())
			terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
		Terceto terceto2 = null;
		if (!elementos.get(2).esToken())
			terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
		String opAssembler = "IMUL";
		
		//caso 1: (OP, variable, variable)
		if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			
			String registroDX = controladorTercetos.getReg4(elementos.get(1).getToken());
			String registroAX = controladorTercetos.getReg3(elementos.get(2).getToken() );
			assembler += MOV + " " + registroAX +", " + elementos.get(1).getNombreVar()  + '\n';
			String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
			System.out.println(registro);
			assembler = assembler + MOV + " " + registro +", " + elementos.get(2).getNombreVar()  + '\n';
			this.setRegistro(registro);
//			assembler += hacerConversiones(registro1, registro2);
//			registro1 = registroAux1;
//			registro2 = registroAux2;
			assembler = assembler + opAssembler + " " + registroAX + ", " + registro + '\n';
			assembler = assembler + "PUSH DX" + '\n';
			assembler = assembler + "PUSH AX" + '\n';
			assembler = assembler + "POP EAX" + '\n';
			if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableI){
				controladorTercetos.liberarRegistro(registro);
				elementos.get(1).getToken().setTipo(AnalizadorLexico.variableL);
				registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
				elementos.get(1).getToken().setTipo(AnalizadorLexico.variableI);
				this.setRegistro(controladorTercetos.getRegistroInteger(registro));				
			}
			assembler =  assembler + MOV + " " + registro +", " + "EAX"  + '\n';
	
			controladorTercetos.liberarRegistro(registroAX);
			controladorTercetos.liberarRegistro(registroDX);
		}
//		else
//			//caso 2: (OP, registro, variable)
//			if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
//				
//				controladorTercetos.OcuparRegistro(reg4Integer);
//				controladorTercetos.OcuparRegistro(reg3Integer);
//				String registroDX = "";
//				String registroAX = "";
//				
//
//				if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableI){	
//					//dividendo en DX
//					registroDX = this.reg4Integer;
//					this.setRegistro(registroDX);
//					//divisor en AX
//					registroAX = this.reg3Integer;
//					this.setRegistro(registroAX);
//				}
//				else{
//					//dividendo en EDX
//					registroDX = this.reg4Long;
//					this.setRegistro(registroDX);
//					//divisor en EAX
//					registroAX = this.reg3Long;
//					this.setRegistro(registroAX);
//				
//				}
//				assembler =assembler + MOV + " " + registroDX +", " + "0"  + '\n';					
//				assembler =assembler + MOV + " " + registroAX +", " + terceto1.getRegistro()  + '\n';
//				controladorTercetos.liberarRegistro(terceto1.getRegistro());
//				assembler = assembler + "CWD" + '\n';
//				
//				String registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
//				assembler =assembler + MOV + " " + registro +", " + elementos.get(2).getNombreVar()  + '\n';
//				this.setRegistro(registro);
//				assembler = assembler + opAssembler + " " + registro + '\n';
//				assembler = assembler + MOV + " " + this.getRegistro() +", " + registroAX  + '\n';
//				controladorTercetos.liberarRegistro(registroAX);
//				controladorTercetos.liberarRegistro(registroDX);
//			}

		return assembler;
	}

	

}
