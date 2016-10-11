package AnalizadorLexico;

import java.util.Hashtable;

public class Token{
	
	String valor;
	String tipo;
	int uso;
	Hashtable<String, Integer> tablaTokens = new Hashtable<>();
	AnalizadorLexico analizador = new AnalizadorLexico(null,null);
	
	public Token (String valor, int uso){
		this.valor = valor;
		this.uso = uso;

		if ( (uso>=analizador.IF) && (uso<=analizador.LONGINT) )
			tipo = "Palabra reservada";
		else
			if (uso==analizador.ID)
				tipo = "Identificador";		
			else
				if (uso==analizador.MULTI_LINEA) 
					tipo = "Cadena de caracteres";
				else
					if (uso==analizador.CTEL) 
						tipo = "Constante long";
					else
						if (uso==analizador.S_ASIGNACION)
							tipo = "Asignacion";
						else
							if (uso==analizador.S_MAYOR_IGUAL)
								tipo = "Simbolo Mayor igual";
							else
								if (uso==analizador.S_MENOR_IGUAL)
									tipo = "Simbolo Menor igual";
								else
									if (uso==analizador.S_EXCLAMACION_IGUAL)
										tipo = "Simbolo distinto";
									else
										if (uso==analizador.CTEI)
											tipo = "Constante entera";
										else
											if (uso==analizador.S_RESTA_RESTA)
												tipo = "Simbolo --";
											else
												if (uso==analizador.CTEI)
													tipo = "Constante entera";
												else
													if (uso==analizador.COMENTARIO)
														tipo = "Comentario";
													else
														if (uso==analizador.ANOTACIONF)
															tipo = "Anotacion por filas";
														else
															if (uso==analizador.ANOTACIONC)
																tipo = "Anotacion por columnas";
															else
																tipo = "Simbolo";	
			
	}
	
	public int getToken() {
		if (valor.charAt(0) <= 256 ){ 		//ACORDARME QUE LO CAMBIE
			char c = valor.charAt(0);
			return c;
		}
		else{
			return 1;	//Poner aca lo de variables, palabra reservada o constantes
		}		
	}
	
	public String getValor(){
		return valor;
	}
	
	public int getUso() {
		return uso;
	}
	
	public String getTipo(){
		return tipo;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String imprimirToken(){
		String imprimir = tipo + ": " + valor + " [" +uso + "] ";
		return imprimir;
	}
}
