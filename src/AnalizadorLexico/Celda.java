package AnalizadorLexico;

public class Celda extends CeldaAbs {
	
	public Celda(int proxEstado){
		super(proxEstado);
	}
	
	@Override
	public int ejecutar_celda(Token t) {
		return proxEstado;
	}
}
