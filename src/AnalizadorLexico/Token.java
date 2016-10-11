package AnalizadorLexico;

import java.util.Hashtable;

public class Token{
	
	String valor;
	String tipo;
	String lexema;
	int uso;
	Hashtable<String, Integer> tablaTokens = new Hashtable<>();
	AnalizadorLexico analizador = new AnalizadorLexico(null,null);
	
	public Token (String valor, int uso){
		this.valor = valor;
		this.uso = uso;

		if ( (uso>=analizador.IF) && (uso<=analizador.LONGINT) )
			lexema = "Palabra reservada";
		else
			if (uso==analizador.ID)
				lexema = "Identificador";		
			else
				if (uso==analizador.MULTI_LINEA) 
					lexema = "Cadena de caracteres";
				else
					if (uso==analizador.CTEL) 
						lexema = "Constante long";
					else
						if (uso==analizador.S_ASIGNACION)
							lexema = "Asignacion";
						else
							if (uso==analizador.S_MAYOR_IGUAL)
								lexema = "Simbolo Mayor igual";
							else
								if (uso==analizador.S_MENOR_IGUAL)
									lexema = "Simbolo Menor igual";
								else
									if (uso==analizador.S_EXCLAMACION_IGUAL)
										lexema = "Simbolo distinto";
									else
										if (uso==analizador.CTEI)
											lexema = "Constante entera";
										else
											if (uso==analizador.S_RESTA_RESTA)
												lexema = "Simbolo --";
											else
												if (uso==analizador.CTEI)
													lexema = "Constante entera";
												else
													if (uso==analizador.COMENTARIO)
														lexema = "Comentario";
													else
														if (uso==analizador.ANOTACIONF)
															lexema = "Anotacion por filas";
														else
															if (uso==analizador.ANOTACIONC)
																lexema = "Anotacion por columnas";
															else
																lexema = "Simbolo";	
			
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
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public String imprimirToken(){
		String imprimir = lexema + ": " + valor + " [" +uso + "] ";
		return imprimir;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
}
