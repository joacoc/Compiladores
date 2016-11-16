package AnalizadorLexico;

import java.util.ArrayList;

public class TokenMatriz extends Token{

	int filas, columnas;	
	
	//Orientacion. False == filas. True == columnas. 
	//Por defecto = filas.
	boolean orientacion = false;
	Token[][] arregloTokens;
	int pos;
	
	public TokenMatriz(String numeroTercetoString) {
		super(numeroTercetoString);
		// TODO Auto-generated constructor stub
	}
	
	public TokenMatriz(String nombre, int uso) {
		super(nombre,uso);
	}
	
	public TokenMatriz(Token t, long filas, long columnas){
		super(t.getNombre(),t.getUso());
		setDimensiones((int)filas, (int) columnas);
	}
	
	public TokenMatriz(String nombre, int uso, long filas, long columnas) {
		super(nombre,uso);
		setDimensiones((int)filas,(int)columnas);
	}

	public void setDimensiones(int filas, int columnas){
		this.filas = filas;
		this.columnas = columnas;
	}
	
	public void setOrientacion(String c){
		if(c!= null && c.equals("C"))
			orientacion = true;
		else 
			orientacion = false;
	}

	public int getFilas(){
		System.out.println("filas: ");		System.out.println(filas);
		return filas;
	}
	
	public int getColumnas(){
		return columnas;
	}
	
	public boolean porFilas(){
		return !orientacion;
	}
	
	public String getBits(){
		AnalizadorLexico al =new AnalizadorLexico(null, null);
		if (this.getTipo() == al.constanteI)
			return "2"; //esta en bytes ver que onda.
		else
			return "4"; 
	}
	
	public void setMatriz(Token[][] arregloTokens){
		this.arregloTokens = arregloTokens;
	};
	
	public Token[][] getMatriz(){
		return this.arregloTokens;
	}
	
	public String getValores(){
		StringBuilder buffer = new StringBuilder();
		
		for(int i = 0; i<filas; i++)
			for(int k = 0; k<columnas; k++)
				buffer.append(arregloTokens[i][k].getValor()+",");
		buffer.deleteCharAt(buffer.length()-1);
		buffer.append("\n");
		return buffer.toString();
	}
	
	public void setPos(int pos){
		this.pos = pos;
	}
	
	public int getPos(){
		return pos;
	}
	
}
