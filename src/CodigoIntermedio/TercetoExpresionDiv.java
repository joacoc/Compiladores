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
			assembler = assembler + getAssemblerVarVar();
		}
		else
			if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) )
			//caso 2: (OP, registro, variable)
			assembler = assembler + getAssemblerRegVar();
		return assembler;
	}
	
	private String getAssemblerRegVar() {				
		String registroDX = "";
		String registroAX = "";
		String assembler = "";
		Terceto terceto1 = null;
		
		if (!elementos.get(1).esToken())
			terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
		Terceto terceto2 = null;
		if (!elementos.get(2).esToken())
			terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
		String opAssembler = "IDIV";
		
		controladorTercetos.OcuparRegistro(reg4Integer);
		controladorTercetos.OcuparRegistro(reg3Integer);

		if (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableI) ){	
			//divisor en AX
			registroDX = this.reg4Integer;
			//movemos el valor del registro a AX
			registroAX = this.reg3Integer;
			String registroABorrar = controladorTercetos.getTerceto(elementos.get(1).getNumeroTerceto() ).getRegistro();
			assembler = assembler + MOV + " " + registroAX + ", " + registroABorrar  + '\n';
			controladorTercetos.liberarRegistro(registroABorrar);
			this.setRegistro(registroABorrar);
			registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		}
		else{
			//divisor en EAX
			registroDX = this.reg4Long;
			registroAX = this.reg3Long;
			String registroABorrar = controladorTercetos.getTerceto(elementos.get(1).getNumeroTerceto() ).getRegistro();
			assembler = assembler + MOV + " " + registroAX + ", " + registroABorrar  + '\n';
			controladorTercetos.liberarRegistro(registroABorrar);
			this.setRegistro(registroAX);
			if (elementos.get(2).getToken().getTipo().equals( AnalizadorLexico.variableI) ){
				elementos.get(2).getToken().setTipo(AnalizadorLexico.variableL);
				registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
				elementos.get(2).getToken().setTipo(AnalizadorLexico.variableI);
			}
			else
				registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		}
		if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL) ) ){
			assembler = assembler + "CWDE"  + '\n';
			registroAX = "EAX";
		}
//		assembler = assembler + MOV + " " + registroAX +", " + elementos.get(1).getNombreVar()  + '\n';
		if (registroAX == "EAX")
			assembler = assembler + "CDQ" + '\n';
		else
			assembler = assembler + "CWD" + '\n';
		
		assembler = assembler + MOV + " " + registro +", " + elementos.get(2).getNombreVar()  + '\n';
		this.setRegistro(registro);
		
		//chequeamos que no sea cero el divisor
		if (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.constanteI) )
			assembler = assembler + getAssemblerErrorDivCero(registro, true); 
		else{
			assembler = assembler + getAssemblerErrorDivCero(registro, false);
			if (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ){
				assembler = assembler + "CWD" + '\n';
				registroAX = "EAX";
			}
		} 
		
		assembler = assembler + "IDIV" + " " + registro + '\n';
		if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableI){
			registro = controladorTercetos.getRegistroInteger(registro);
			registroAX = "AX";
			this.setRegistro(registro);
		}
	
		assembler = assembler + MOV + " " + this.getRegistro() +", " + registroAX  + '\n';
		controladorTercetos.liberarRegistro(registroAX);
		controladorTercetos.liberarRegistro(registroDX);
		return assembler;
}
	
	private String getAssemblerVarVar() {
		controladorTercetos.OcuparRegistro(reg4Integer);
		controladorTercetos.OcuparRegistro(reg3Integer);
		String registroDX = "";
		String registroAX = "";
		String registro= "";
		String assembler = "";
		if (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableI)){	
			//dividendo en DX
			registroDX = this.reg4Integer;
			//divisor en AX
			registroAX = this.reg3Integer;
			this.setRegistro(registroAX);
			registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
			if (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL) )
				registroAX = "EAX";					
		}
		else{
			//dividendo en EDX
			registroDX = this.reg4Long;
			this.setRegistro(registroDX);
			//divisor en EAX
			registroAX = this.reg3Long;
			this.setRegistro(registroAX);
			if (elementos.get(2).getToken().getTipo() == AnalizadorLexico.variableI){
				elementos.get(2).getToken().setTipo(AnalizadorLexico.variableL);
				registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
				elementos.get(2).getToken().setTipo(AnalizadorLexico.variableI);
			}
			else
				registro = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		}
		if ( (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL) ) ){
			assembler = assembler + MOV + " " + "AX" +", " + elementos.get(1).getNombreVar()  + '\n';
			assembler = assembler + "CWDE"  + '\n';
			registroAX = "EAX";
		}
		else
			assembler = assembler + MOV + " " + registroAX +", " + elementos.get(1).getNombreVar()  + '\n';
		assembler = assembler + MOV + " " + registroDX +", " + "0"  + '\n';
		if (registroAX.equals("EAX") )
			assembler = assembler + "CDQ" + '\n';
		else
			assembler = assembler + "CWD" + '\n';
		
		assembler = assembler + MOV + " " + registro +", " + elementos.get(2).getNombreVar()  + '\n';
		this.setRegistro(registro);
		
		//chequeamos que no sea cero el divisor
		if (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.constanteI) )
			assembler = assembler + getAssemblerErrorDivCero(registro, true); 
		else{
			assembler = assembler + getAssemblerErrorDivCero(registro, false);
			if (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableL) ){
				assembler = assembler + "CWD" + '\n';
				registroAX = "EAX";
			}
			
		} 
		
		assembler = assembler + "IDIV" + " " + registro + '\n';
		if (elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableI) ){
			registro = controladorTercetos.getRegistroInteger(registro);
			registroAX = "AX";
			this.setRegistro(registro);
		}
	
		assembler = assembler + MOV + " " + this.getRegistro() +", " + registroAX  + '\n';
		controladorTercetos.liberarRegistro(registroAX);
		controladorTercetos.liberarRegistro(registroDX);

		return assembler;
	}
	

	public static String verificarMatriz(TokenMatriz tokenMatriz, ControladorTercetos controladorTercetos){
		String assembler = "";
		assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+ "," +(tokenMatriz.getColumnas()*tokenMatriz.getFilas()*4) + "\n";
		assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
		return assembler;
	}
	
	private String getAssemblerErrorDivCero(String registro, boolean integer) {
		String assembler = "CMP " + registro + ", 0" + '\n';
		assembler = assembler + "JE LabelDivCero" + '\n';
		return assembler;
	}
		
//TODO: Despues del pull me aparecio esto aca:
	/*
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
		controladorTercetos.liberarRegistro("EAX");
		return assembler;
		
	}
	*/
}
