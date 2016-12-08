package CodigoIntermedio;

import AnalizadorLexico.TokenMatriz;

public class TercetoControl extends TercetoExpresion {

	TokenMatriz t1;
	boolean act = false;
	
	public TercetoControl(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, TokenMatriz t1, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		this.t1 = t1;
		// TODO Auto-generated constructor stub
	}
	
	public void setAct(){
		this.act = true;
	}
	
	public TokenMatriz getTokenMatriz(){
		return t1;
	}
	
	@Override
	public String getAssembler() {
		// TODO Auto-generated method stub
		String assembler = super.getAssembler();
		boolean agregado = false;

		int num_actual = controladorTercetos.getNumTercetoActual();
		num_actual -= 1;
		
		controladorTercetos.setRegMatriz(controladorTercetos.getTerceto(num_actual).getRegistro());
		assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro() +", " +(t1.getColumnas()*t1.getFilas()*4) +"\n";
		assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
		
		if(act){
//			assembler = assembler +"MOV " + t1.getNombre().substring(4, t1.getNombre().length()) +", " +controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro() + "\n";
			controladorTercetos.liberarRegistros();
		}
		return assembler;
	}
	
	public String verificarMatriz(TokenMatriz tokenMatriz){
		String assembler = "";
		assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+ "," +(tokenMatriz.getColumnas()*tokenMatriz.getFilas()*4) + "\n";
		assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
		return assembler;
	}
	

}
