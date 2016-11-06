package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;

public class TercetoAsignacion extends Terceto{

	public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public String getAssembler() {
		String assembler = "";
		if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableL){
			
		}
		if ( elementos.get(2).esToken() ) 
			assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + ", " + elementos.get(2).getNombreVar()+ '\n';
		else{
			Terceto terceto = controladorTercetos.getTerceto(elementos.get(2).getNumeroTerceto() );
			assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + ", " + terceto.getRegistro() + '\n';
			controladorTercetos.liberarRegistro( terceto.getRegistro() );
			
		}
		return assembler;
	}
}
