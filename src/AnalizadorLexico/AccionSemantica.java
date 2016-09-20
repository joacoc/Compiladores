package AnalizadorLexico;

public class AccionSemantica {
	
	TablaSimbolos tablaSimbolo;
	Error error;
	
	public AccionSemantica (TablaSimbolos ts){
		this.tablaSimbolo = ts;
	}
	
	public void ejecutarError(){
	}
	
	public void ejecutar(Token t) {
		tablaSimbolo.addSimbolo(t);
	}
	
}
