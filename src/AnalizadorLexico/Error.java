package AnalizadorLexico;

public class Error {

	String descripcion;
	//Warning o error de compilacion. ver despues pueden ser mas
	// puede que no sirva mas porque tenemos dos listas.
	String tipo;
	int linea;
	
	public Error(String descripcion, String tipo, int linea){
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.linea = linea;
	}

	public Error(String descripcion, String tipo){
		this.descripcion = descripcion;
		this.tipo = tipo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String Imprimir(){
		String l ="<b>";
		l= l + "Linea "+ String.valueOf(linea)+": ";
		l = l + "</b>";
		return l + descripcion ;
	}
	
}
