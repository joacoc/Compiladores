package AnalizadorLexico;

public class TokenMatriz extends Token{

	int filas, columnas = 0;
	long mat[][];
	//Orientacion. False == filas. True == columnas. 
	//Por defecto = filas.
	
	boolean orientacion = false;
	
	public TokenMatriz(String numeroTercetoString) {
		super(numeroTercetoString);
		// TODO Auto-generated constructor stub
	}
	

	public TokenMatriz(String nombre, int uso) {
		super(nombre,uso);
	}

	public void setDimensiones(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
		mat = new long[filas][columnas];
	}
	
	public void setOrientacion(char c){
		if(c=='F')
			orientacion = false;
		else 
			orientacion = true;
	}

	public void setValor(long valor, int fila, int columna){
		//O se puede hacer todo de una en el constructor
		//if(!orientacion)
			//Seteo
		//else
			//Seteo
	}

	public long getValor(int fila, int columna){
		return mat[fila][columna];
	}

}
