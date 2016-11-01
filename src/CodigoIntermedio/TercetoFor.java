package CodigoIntermedio;

public class TercetoFor extends Terceto {

	public TercetoFor(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}

	public String getAssembler() {
		// TODO Auto-generated method stub
		String assembler = "";
		
	
		//Terceto izq. 
		String operador = elementos.get(0).getToken().getNombre();
		//Terceto del medio == condicion; 
		
		//Cuando termina el for salta
		if (operador == controladorTercetos.BF){
			//elementos.get(2) == null
			//TODO: JG O JF? 
			assembler = "JG Label" + elementos.get(2).getToken().getNombre() + '\n';
		}
		else{
			//BI	
			assembler = "JUMP Label" + elementos.get(1).getToken().getNombre() + '\n';
			assembler = assembler + "Label" + elementos.get(1).getToken().getNombre() + ":	" + '\n';
		}
		return assembler;
	}
}
