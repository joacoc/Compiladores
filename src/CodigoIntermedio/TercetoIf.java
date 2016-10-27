package CodigoIntermedio;

public class TercetoIf extends Terceto {

	public TercetoIf(TercetoSimple izq, TercetoSimple medio, TercetoSimple der,	int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
	}

	public String getAssembler() {
		String assembler = "";
		String operador = elementos.get(0).getToken().getNombre();
		
		if (operador == controladorTercetos.BF){
			assembler = "JLE Label" + elementos.get(2).getToken().getNombre() + '\n';
		}
		else{
			assembler = "JUMP Label" + elementos.get(1).getToken().getNombre() + '\n';
			assembler = assembler + "Label" + elementos.get(1).getToken().getNombre() + ":	" + '\n';
		}
		return assembler;
	}

}
