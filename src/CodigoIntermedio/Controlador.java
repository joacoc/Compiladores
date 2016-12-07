package CodigoIntermedio;

import java.util.ArrayList;

import AnalizadorLexico.TokenMatriz;

public class Controlador {

	public Controlador(){	
	}
	
	public static final String control(ArrayList<TercetoSimple> elementos,ControladorTercetos controladorTercetos){
		String assembler = "";
					
		boolean agregado = false;
		
		//Si es una matriz tengo que hacer el chequeo de rango
		if(elementos.get(1).getNombreVar().startsWith("mat")){
			if(elementos.get(2).getNombreVar().startsWith("mat")){
				//Ambos son matrices;
				agregado = true;
				assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(1).getToken(), controladorTercetos);
				//assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
				//assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[EBX]" +"," +elementos.get(2).getNombreVar()+"\n";

				
				assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(2).getToken(), controladorTercetos);
				
				//assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
				//assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[EBX]" +"," +elementos.get(2).getNombreVar()+"\n";

				//Solo se puede acceder a la matriz utilizando BX
				//Por lo tanto primero se usa una variable auxilia 'matrix' para almancenar el valor
				
//				assembler = assembler + "MOV " +"matrix" +"," + "EBX\n";
//				assembler = assembler + "MOV " +"EBX" +"," + controladorTercetos.getAnteUltimoRegistro() +"\n";
//				assembler = assembler + "MOV " +"auxMatrix" +"," + elementos.get(2).getNombreVar()+"[EBX]\n"; 
//				assembler = assembler + "MOV " +"EBX" +"," + controladorTercetos.getUltimoRegistro() +"\n";
//				assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[EBX]" +"," +"auxMatrix\n";
//				assembler = assembler + "MOV " +"EBX" +"," +"matrix\n";
			}
			else{
				agregado = true;
				assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(1).getToken(), controladorTercetos);
				assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
				assembler = assembler + "MOV " + elementos.get(1).getNombreVar() + "[EBX]" +"," +elementos.get(2).getNombreVar()+"\n";

			}

		}if(elementos.get(2).getNombreVar().startsWith("mat")){
			agregado = true;
			assembler = assembler + verificarMatriz((TokenMatriz) elementos.get(2).getToken(), controladorTercetos);
			assembler = assembler + "MOV " +"EBX" +"," +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+"\n"; 
			assembler = assembler + "MOV " + elementos.get(1).getNombreVar() +"," +elementos.get(2).getNombreVar()+"[EBX]" +"\n";
		}
		
		return assembler;
	}
	
	public static String verificarMatriz(TokenMatriz tokenMatriz, ControladorTercetos controladorTercetos){
		String assembler = "";
		assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+ "," +(tokenMatriz.getColumnas()*tokenMatriz.getFilas()*4) + "\n";
		assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
		return assembler;
	}

	public String getAssembler() {
		return "";
	}
}
