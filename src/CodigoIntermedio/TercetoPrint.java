package CodigoIntermedio;

public class TercetoPrint extends Terceto {

	public TercetoPrint(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
	}

	public String getAssembler() {
		return ("invoke MessageBox, NULL, addr " + elementos.get(1).getToken().getNombre() + ", addr " + elementos.get(1).getToken().getNombre() + ", MB_OK");
	}

}
