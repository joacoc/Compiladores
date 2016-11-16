package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.TokenMatriz;

public class TercetoAsignacion extends Terceto{

	public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public String getAssembler() {
		String assembler = "";
		if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableL){
			
		}
		if ( elementos.get(2).esToken() ) {
			//Si es una matriz tengo que hacer el chequeo de rango
			if(elementos.get(1).getNombreVar().startsWith("mat")){
				if(elementos.get(2).getNombreVar().startsWith("mat")){
					//Ambos son matrices;
					assembler = assembler + "CMP " + controladorTercetos.getUltimoRegistro() + "," +((TokenMatriz)elementos.get(1).getToken()).getColumnas()*((TokenMatriz)elementos.get(1).getToken()).getFilas()*4 + "\n";
					assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
					//Solo se puede acceder a la matriz utilizando BX
					//Por lo tanto primero se usa una variable auxilia 'matrix' para almancenar el valor
					
					assembler = assembler + "MOV " +"matrix" +"," +"BX\n";
					assembler = assembler + "MOV " +"BX" +"," +controladorTercetos.getUltimoRegistro() +"\n"; 
					assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[BX]" +"," +elementos.get(2).getNombreVar()+"\n";
					assembler = assembler + "MOV " +"BX" +"," +"matrix\n";
				}
				else{
					assembler = assembler + "CMP " + controladorTercetos.getUltimoRegistro() + "," +((TokenMatriz)elementos.get(1).getToken()).getColumnas()*((TokenMatriz)elementos.get(1).getToken()).getFilas()*4 + "\n";
					assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
					assembler = assembler + "MOV " +"matrix" +"," +"BX\n";
					assembler = assembler + "MOV " +"BX" +"," +controladorTercetos.getUltimoRegistro()+"\n"; 
					assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[BX]" +"," +elementos.get(2).getNombreVar()+"\n";
					assembler = assembler + "MOV " +"BX" +"," +"matrix\n";
				}
			}else
				assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + ", " + elementos.get(2).getNombreVar()+ '\n';
			}	
		else{
			Terceto terceto = controladorTercetos.getTerceto(elementos.get(2).getNumeroTerceto() );
			assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + ", " + terceto.getRegistro() + '\n';
			controladorTercetos.liberarRegistro( terceto.getRegistro() );
			
		}
		return assembler;
	}
}
