package AnalizadorSintactico;

import java.awt.List;
import java.util.ArrayList;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Error;
import AnalizadorLexico.TablaSimbolos;

public class AnalizadorSintactico {
	
	//descripcion estructura sintactica
	static final String estructuraPrint = "Sentencia para imprimir por pantalla ";
	static final String estructuraIF = "Sentencia de seleccion IF ";
	static final String estructuraFOR = "Sentencia de iteracion FOR ";
	static final String estructuraASIG = "Sentencia de ASIGNACIÓN ";
	static final String estructuraCONDICION = "Sentencia de CONDICION ";
	static final String estructuraPRINCIPAL = "Estructura del programa principal ";
	static final String estructuraALLOW = "Sentencia ALLOW.";

	
	
	//descripciones errores sintacticos
	static final String errorPrint1 = "Existe un error en la sentencia print ";
	static final String errorPrint2 = "Se espera la palabra reservada 'PRINT' al comienzo de la sentencia ";
	static final String errorParentesisA = "Parentesis no equilibrados. Faltó abrir parentesis. ";
	static final String errorParentesisC = "Parentesis no equilibrados. Faltó cerrar parentesis. ";
	static final String errorCondicionI = "No se reconoce el lado izquierdo de la condicion ";
	static final String errorCondicionD = "No se reconoce el lado derecho de la condicion ";
	static final String errorPuntoComa = "Se espera un ';' al final de la sentencia ";
	static final String errorLlaveA = "Llaves no equilibradas. Faltó abrir llaves en el programa principal. ";
	static final String errorLlaveC = "Llaves no equilibradas. Faltó cerrar llaves en el programa principal. ";	
	static final String errorLlaveAIF = "Llaves no equilibradas. Faltó abrir llaves en la rama del IF. ";
	static final String errorLlaveCIF = "Llaves no equilibradas. Faltó cerrar llaves en la rama del IF. ";	
	static final String errorLlaveAELSE = "Llaves no equilibradas. Faltó abrir llaves en la rama del ELSE. ";
	static final String errorLlaveCELSE = "Llaves no equilibradas. Faltó cerrar llaves en la rama del ELSE. ";	
	static final String errorProgram = "Se esperaba la palabra reservada 'PROGRAM' ";
	static final String errorDeclaracionVar = "No se declaró correctamente las variables. ";
	static final String errorSentencias = "No se declararon sentencias dentro del programa. ";
	static final String errorAsignacion = "Error en la asignacion.";
	static final String errorFaltoPalabraIF = "Error en la palabra reservada if";
	
	ArrayList<Error> erroresSint;
	ArrayList<Error > estructuras;
	AnalizadorLexico analizadorL;
	TablaSimbolos tablaS;
	
	public AnalizadorSintactico() {
		erroresSint = new ArrayList<Error>();
		estructuras = new ArrayList<Error>();
	}
	
	public void addError( Error error ){
		erroresSint.add(error);
	}
	
	public void addEstructura(Error estructura){
		estructuras.add(estructura); //usamos el error tambien para almacenar la estructura para mostrarla 
	}
	
	public String getErroresSint(){
		String aux = "Errores Sintacticos: \n";
		for (Error e:erroresSint){
			aux = aux + e.Imprimir() + "\n";
		}
		if (erroresSint.size() == 0)
			return "No hay errores sintacticos.";
		else
			return aux;
	}
	
	public String getEstructuras(){
		String aux = "Estructuras encontradas: \n";
		for (Error e:estructuras){
			aux = aux + e.Imprimir() + "\n";
		}
		if (estructuras.size() == 0)
			return "No se encontraron estructuras sintacticas";
		else
			return aux;
	}
}
