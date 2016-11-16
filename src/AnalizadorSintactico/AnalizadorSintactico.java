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
	static final String estructuraDECLARACION = "Sentencia de declaracion de variables.";

	//descripciones usos de identificadores
	static final String usoVariable = "nombre de variable";
	static final String usoVariableArreglo = "nombre de arreglo";
	static final String usoNombrePrograma = "nombre del programa";
	
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
	static final String errorProgram = "No es correcto el nombre del programa. ";
	static final String errorDeclaracionVar = "No se declaró correctamente las variables. ";
	static final String errorSentencias = "No se declararon sentencias dentro del programa. ";
	static final String errorAsignacion = "Error en la asignacion.";
	static final String errorTipo = "Error al declarar el tipo.";
	static final String errorSimboloAsignacion = "La asignacion es con :=";
	static final String errorDeclaracionMatriz = "No se declaró correctamente la matriz ";
	static final String errorPalabraFOR = "Se esperaba la palabra for en minuscula ";
	static final String errorCeldaMatriz = "Se espera una constante entera o variable dentro de los corchetes ";
	static final String errorTipo_operacion = "Los tipos de las variables son incompatibles.";
	static final String errorPalabraIF = "Se esperaba la palabra if en minuscula ";


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
	
	public boolean hayErrores() {
		return (!erroresSint.isEmpty());
	}
}
