package Main;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.ControladorArchivo;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorSintactico.AnalizadorSintactico;
import AnalizadorSintactico.Parser;
import Window.MainWindow;

public class Main {

	private static BufferedReader codigo;

	private static StringBuilder getCodigo(BufferedReader ubicacion){
		
		StringBuilder buffer = new StringBuilder();
		try{			
			//lectura de ubicacion de archivo
//			System.out.print("Ingrese la ubicacion del archivo: ");
//			BufferedReader ubicacion = new BufferedReader(new InputStreamReader(System.in));
			codigo = new BufferedReader( new FileReader( ubicacion.readLine() ) );
			String readLine;
			//TODO: Check que el \n no arruine nada
			while ((readLine = codigo.readLine())!= null) {
				buffer.append(readLine+"\n");
			}
			buffer.append("$");
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return buffer;
	}
	
	public static void main(String[] args) throws IOException {
		
		InputStream is = new ByteArrayInputStream(args[0].getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		StringBuilder codigo = null;
		System.out.println(args[0]);
		codigo= new StringBuilder( getCodigo( br ) );
		ControladorArchivo archivo =new ControladorArchivo( codigo );
		TablaSimbolos ts = new TablaSimbolos();
    	AnalizadorLexico analizadorLexico = new AnalizadorLexico(archivo,ts);
    	AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico( );
    	
        Parser parser;
        parser = new Parser();
        parser.setLexico(analizadorLexico);
        parser.setTS(ts);
        parser.setSintactico(analizadorSintactico);
        parser.setControladorArchivo(archivo);
        parser.run();

        System.out.println( analizadorLexico.mostrarTokens() );
        System.out.println( analizadorLexico.mostrarTs() );
        System.out.println(analizadorLexico.mostrarWarning());
        System.out.println(analizadorLexico.mostrarErrorComp());
        System.out.println(analizadorSintactico.getErroresSint());
        System.out.println(analizadorSintactico.getEstructuras());
        
	}
}
