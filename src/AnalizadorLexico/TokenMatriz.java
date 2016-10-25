package AnalizadorLexico;

import java.util.ArrayList;

public class TokenMatriz extends Token{

	int filas = 0, columnas = 0;
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
	
	public TokenMatriz(String nombre, int uso, int filas, int columnas) {
		super(nombre,uso);
		setDimensiones(filas,columnas);
	}

	public void setDimensiones(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
		mat = new long[filas][columnas];
	}
	
	public void setOrientacion(String c){
		if(c!= null && c.equals("C"))
			orientacion = true;
		else 
			orientacion = false;
	}
	
	public void setValores(ArrayList<ArrayList<Long>> valores, String orientacion){
		setOrientacion(orientacion);
		
		int caux = 0, faux = 0;
		
		if(valores != null){
			//this.valorMatriz	
			if(!this.orientacion){
				//Orientacion por filas
				for(ArrayList<Long> a : valores){
					for(Long l : a){
						mat[faux][caux] = l.longValue();
						faux++;
					}
					caux++;
				}
			}else{
				//Orientacion por columnas
				for(ArrayList<Long> a : valores){
					for(Long l : a){
						mat[faux][caux] = l.longValue();
						caux++;
					}
					faux++;
				}
			}
		}
	}
	
	public void setValor(long valor, int fila, int columna){
		valorMatriz[fila][columna] = valor;
	}

	public long getValor(int fila, int columna){
		return mat[fila][columna];
	}

}
