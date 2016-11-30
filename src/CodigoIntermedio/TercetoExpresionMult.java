package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TokenMatriz;

public class TercetoExpresionMult extends TercetoExpresion {

	private String registroAux1;
	private String registroAux2;
	private String registroAux3;

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
			String registroAX = controladorTercetos.getReg3(elementos.get(1).getToken() );
			assembler += MOV + " " + registroDX +", " + "0"  + '\n'; //seteo en 0 el registro DX
			

			if(elementos.get(1).getNombreVar().startsWith("mat")){
				verificarMatriz((TokenMatriz)elementos.get(1).t, controladorTercetos);
				assembler = assembler + "MOV " + registroAX + ", " + elementos.get(1).getNombreVar() + "[EBX]\n";
			}else
				assembler = assembler + "MOV " + registroAX + ", " + elementos.get(1).getNombreVar() + '\n';
			
			String registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());

			if(elementos.get(2).getNombreVar().startsWith("mat")){
				verificarMatriz((TokenMatriz)elementos.get(2).t, controladorTercetos);
				assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + "[EBX]\n";
			}else
				assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + '\n';
			
			this.setRegistro(registro);
			
			assembler += hacerConversiones(registroAX, registroDX, registro);
			
			registroAX = registroAux1;
			registroDX = registroAux2;
			registro = registroAux3;
			
			assembler = assembler + opAssembler + " " + registroAX + ", " + registro + '\n';
			
			controladorTercetos.liberarRegistro(registro);
			elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
			registro = controladorTercetos.getProxRegLibre(elementos.get(0).getToken());
			this.setRegistro(registro);				
			
			assembler =  assembler + MOV + " " + registro +", " + "EAX"  + '\n';
	
			controladorTercetos.liberarRegistro(registroAX);
			controladorTercetos.liberarRegistro(registroDX);
		}
		else
//			//caso 2: (OP, registro, variable)
			if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
//				
				String registroDX = controladorTercetos.getReg4(elementos.get(1).getToken());
				String registroAX = controladorTercetos.getReg3(elementos.get(1).getToken() );
				assembler += MOV + " " + registroAX +", " + terceto1.getRegistro()  + '\n';
				String registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
				

				if(elementos.get(2).getNombreVar().startsWith("mat")){
					verificarMatriz((TokenMatriz)elementos.get(2).t, controladorTercetos);
					assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + "[EBX]\n";
				}else
					assembler = assembler + "MOV " + registro + ", " + elementos.get(2).getNombreVar() + '\n';
				
				this.setRegistro(registro);
				assembler += hacerConversiones(registroAX, registroDX, registro);
				registroAX = registroAux1;
				registroDX = registroAux2;
				registro = registroAux3;
				
				assembler = assembler + opAssembler + " " + registroAX + ", " + registro + '\n';
				controladorTercetos.liberarRegistro(registro);
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
				registro = controladorTercetos.getProxRegLibre(elementos.get(0).getToken());
				this.setRegistro(registro);				
				
				assembler =  assembler + MOV + " " + registro +", " + "EAX"  + '\n';
			
				controladorTercetos.liberarRegistro(registroAX);
				controladorTercetos.liberarRegistro(registroDX);
			}
//
		return assembler;
	}

	private String hacerConversiones(String registroAX, String registroDX, String registro) {
		registroAux1 = registroAX;
		registroAux2 = registroDX;
		registroAux3 = registro;
		String assembler = "";
		if (elementos.get(2).getToken().getTipo().equals( AnalizadorLexico.variableI) ){ 
			assembler = assembler + "MOV " + registroDX + ", " + registroAX + '\n';		
			assembler = assembler + "MOV " + "AX" + ", " + registro + '\n';
			assembler = assembler + "CWDE" + '\n';
			controladorTercetos.liberarRegistro(registro);
			elementos.get(2).getToken().setTipo(AnalizadorLexico.variableL);
			registroAux1 = "EAX";
			registroAux2 = "EDX";
			registroAux3 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
			elementos.get(2).getToken().setTipo(AnalizadorLexico.variableI);
			assembler = assembler + "MOV " + registroAux3 + ", " + registroAux1 + '\n';
			assembler = assembler + "MOV " + registroAX + ", " + registroDX + '\n';
		}

		if (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ){ 
			assembler = assembler + "CWDE" + '\n';
			registroAux1 = "EAX";
			registroAux2 = "EDX";
		}
		
		return assembler;
	}

	

}
