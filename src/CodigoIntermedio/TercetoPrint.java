package CodigoIntermedio;

public class TercetoPrint extends Terceto {

	public TercetoPrint(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
	}

	public String getAssembler() {
		// TODO Auto-generated method stub

		String assembler = "invoke MessageBox, NULL, addr "+ this.getTerceto(1).getToken().getNombre() +", addr "+this.getTerceto(1).getToken().getNombre()+"MB_OK \n"
				+ "invoke ExitProcess, 0";
		
		return assembler;
	}

}
