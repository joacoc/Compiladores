package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.CeldaAS;

public class TercetoAsignacion extends Terceto{

	private String registroAux;
	
	public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public String getAssembler() {
		String assembler = "";
		String registro2 = "";
		if ( elementos.get(2).esToken() ) {
			//caso 1: (ASIG, variable, variable){
			registro2 = controladorTercetos.getProxRegLibre( elementos.get(2).getToken() );
			assembler = assembler + "MOV" + " "  + registro2 + ", " +  elementos.get(2).getNombreVar() + '\n';
		}
		else
			//caso 2: (ASIG, variable, registro)
			registro2 = controladorTercetos.getTerceto(elementos.get(2).getNumeroTerceto() ).getRegistro();
		
		if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
			assembler = assembler + "MOV " + "EAX" + ", " + elementos.get(2).getNombreVar() + '\n';
			assembler = assembler + verificarConversionAsig(registro2);
			registro2 = controladorTercetos.getRegistroInteger(registro2);
		}
		else
			if ( (elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
				assembler = assembler + crearAssemblerConversionVar(registro2);
				registro2 = registroAux;
			}

		assembler = assembler + "MOV" + " " +  elementos.get(1).getNombreVar() + ", " + registro2 + '\n';
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
		return assembler;
	}
	
	private String verificarConversionAsig(String registro2){
		String assembler = "";
		String reg = "EAX";
		controladorTercetos.OcuparRegistro("EAX");
		registroAux=reg;
		assembler = assembler + "CMP " + reg + ", 0"  + '\n';
		assembler = assembler + "JL LabelNEG" + '\n';
		//es positivo el valor
		String maximo = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		assembler = assembler + "MOV " + maximo + ", " + CeldaAS.maximo + '\n';
		assembler = assembler + "SUB EAX, " + maximo + '\n';
		assembler = assembler + "CMP EAX, 0" + '\n';
		assembler = assembler + "JG LabelERRORPERDIDA" + '\n';
		controladorTercetos.liberarRegistro(maximo);		
		
		//es negativo el valor
		assembler = assembler + "LabelNEG:" + '\n';
		String minimo = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
		assembler = assembler + "MOV " + minimo + ", " + CeldaAS.minimo + '\n';
		assembler = assembler + "SUB EAX, " + minimo + '\n';
		assembler = assembler + "CMP EAX, 0" + '\n';
		assembler = assembler + "JL LabelERRORPERDIDA" + '\n';
		controladorTercetos.liberarRegistro(minimo);
		controladorTercetos.liberarRegistro("EAX");
		return assembler;
		
	}

}
