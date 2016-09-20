package AnalizadorLexico;

public abstract class CeldaAbs {

	protected int proxEstado;
	
	public CeldaAbs(int proxEstado){
		this.proxEstado = proxEstado;
	}
	
	//Podria devolver un entero para decir que hubo un error al ejecutar la celda.
	//O que retorne el valor de la proxima celda. 
	
	public abstract int ejecutar_celda(Token t);	
	
	public int getEstado(){
		return proxEstado;
	}	

}
