package CodigoIntermedio;

public class TercetoPrint extends Terceto {

	String print = "print";
	
	public TercetoPrint(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
	}

	public String getAssembler() {
		// TODO Auto-generated method stub
		String assembler = "invoke MessageBox, NULL, addr "+ print +", addr "+print+", MB_OK \n";
		return assembler;
	}
	
	public void setPrint(String aux){
		this.print = this.print + aux;
	}

}
