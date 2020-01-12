import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Implementacion de la practica 3 de Analisis y Diseño de Algoritmos
 * @author ismpere
 * @author vicrojo
 * @author davgome
 */
public class P3Ada {

	public static TreeSet<String> usr, pbl;
	public static ArrayList<String> filas, columnas;
	public static int[][] matriz;
	
	/**
	 * Devuelve los datos del fichero como una matriz de adyacencia
	 * con las siguientes etiquetas:
	 * 1 => Lee
	 * 2 => Escribe
	 * 3 => Cita
	 * @return matriz de adyacencia
	 */
	public static int[][] leerfichero(){
		//Deberiamos guardar una tabla hash con los usuarios y publicaciones
		//para saber cual esta en cada posicion de la matriz, ya que como en vez
		//de poner user1 por orden, nos meta manolo podemos tener problemas
		String fichero = "entrada08.txt";
		String usuario1 = "", usuario2 = "", accion = "";
		ArrayList<Integer>acto = new ArrayList<Integer>();
		ArrayList<String>actores = new ArrayList<String>();//Pares actor receptor
		filas = new ArrayList<String>();
		columnas = new ArrayList<String>();
		
		int lineas = 0; //Almacenamos cuantas lineas tiene el fichero de entrada
		
		try{
			FileInputStream fstream = new FileInputStream(fichero);
			DataInputStream entrada = new DataInputStream(fstream);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			String strLinea;
			while ((strLinea = buffer.readLine()) != null)   {
				lineas++;
			}
			entrada.close();
		}catch (Exception e){
				System.err.println("Ocurrio un error: " + e.getMessage());
		}
		
		try{
			Scanner fichscan = new Scanner(new File(fichero));
			for(int i=0; i<lineas; i++){
				usuario1=fichscan.next();
				actores.add(usuario1);
				accion=fichscan.next();
				usuario2=fichscan.next();
				int modo = 0;
				if(accion.equals("lee")){
					modo = 1;
				}else if(accion.equals("escribe")){
					modo = 2;
				}else{
					modo = 3;
				}
				acto.add(modo);
				actores.add(usuario2);
				aniadeUsuarios(usuario1, usuario2, modo);
			}
			fichscan.close();
			
		}catch(Exception e){
			System.err.println("Ocurrio un error: " + e.getMessage());
		}
			
		columnas.addAll(pbl);
		columnas.addAll(usr);
		filas.addAll(pbl);
			
		matriz=rellenarMatriz(filas, columnas, actores,acto);
					
		return matriz;
	}
	
	/**
	 * Mete los valores de las acciones en relacion a los pares de actores en la matriz.
	 * @param filas
	 * @param columnas
	 * @param actores
	 * @param acto
	 * @return matriz
	 */
	public static int[][]rellenarMatriz(ArrayList<String>filas, ArrayList<String>columnas,
			ArrayList<String>actores,ArrayList<Integer>acto){
		
		int[][]matriz=new int [filas.size()][columnas.size()];
		for(int i=0;i<filas.size();i++){
			for(int j=0;j<columnas.size();j++){
				if(i!=j){
					matriz[i][j]=-1;
				}else{
					matriz[i][j]=0;
				}
			}
		}	
		for(int k=0;k<actores.size();k=k+2){
			for(int j=0;j<columnas.size();j++){
				if(actores.get(k).equals(columnas.get(j))){
					for(int i=0;i<filas.size();i++){
						if(actores.get(k+1).equals(filas.get(i))){
							matriz[i][j]=acto.get(k/2);
						}
					}
				}
			}
		}
		
		return matriz;
	}
	
	/**
	 * Devuelve los nodos del grafo que estan relacionados con otro nodo
	 * con una etiqueta de valor como la que se pasa como argumento.
	 * Esto lo hace mediante una busqueda primero en anchura recorriendo solo las aristas
	 * que tengan la etiqueta que hemos seleccionado.
	 * @param p Publicacion
	 * @param e Etiqueta
	 * @return Usuarios relacionados con la publicacion p con una etiqueta e
	 */
	public static TreeSet<String> getUsuariosEtiquetaP(String p, int etiqueta){
		TreeSet<String> usuarios = new TreeSet<String>();
		
		int numP = Integer.parseInt(p.substring(2));
		
		for(int k=pbl.size();k<(pbl.size()+usr.size());k++){
			if(matriz[numP-1][k]==etiqueta){
				usuarios.add(columnas.get(k));
			}
		}
		return (TreeSet<String>)usuarios.clone();
	}
	
	/**
	 * Devuelve los lectores de una publicacion
	 * @param p Publicacion
	 * @return Lectores de la publicacion
	 */
	public static TreeSet<String> getLectoresP(String p){
		
		return getUsuariosEtiquetaP(p, 1);
	}
	
	/**
	 * Devuelve los escritores de una publicacion
	 * @param p Publicacion
	 * @return Escritores de la publicacion
	 */
	public static TreeSet<String> getEscritoresP(String p){
		
		return getUsuariosEtiquetaP(p, 2);
	}
	
	/**
	 * Informa de las lecturas que tiene una publicacion
	 * @param p Publicacion
	 */
	public static void lecturaP(String p){
		int lecturas = 0;
		
		if(pbl.contains(p)){			
			lecturas = getLectoresP(p).size();
			if(lecturas>0){
				System.out.println("Nº de lecturas de la publicacion " + p + ": " + lecturas + "\n");
			}else{
				System.out.println("La publicacion " + p + " no tiene ninguna lectura\n");
			}
		}else{
			System.out.println("Ese nombre no corresponde a ninguna publicacion\n");
		}
	}
	
	/**
	 * Devuelve una lista de publicaciones con las maximas lecturas
	 * @return Publicaciones con mayor numero de lecturas
	 */
	public static ArrayList<String> maxLecturas(){
		int maxLec = 1;
		ArrayList<String> pbls = new ArrayList<String>();
		for(Iterator<String> it = pbl.iterator(); it.hasNext();){
			String p = it.next();
			int l = getLectoresP(p).size();
			if(l>=maxLec){
				if(l>maxLec){
					pbls.clear();
					maxLec = l;
				}
				pbls.add(p);
			}	
		}
		
		return (ArrayList<String>)pbls.clone();
	}
	
	/**
	 * Devuelve una lista de impactados por una publicacion
	 * mediante una busqueda primero en profundidad, con unas condiciones
	 * de busqueda añadidas
	 * @param p Publicacion
	 * @param pr Lista de publicaciones recorridas en la busqueda en pp
	 * @return Impactados por esa publicacion
	 */
	public static TreeSet<String> getImpactadosP(String p, ArrayList<String> pr){
		TreeSet<String> impactados;
		
		impactados = getLectoresP(p);
		impactados.addAll(getEscritoresP(p));
		pr.add(p);
		
		int numP = Integer.parseInt(p.substring(2));
		
		for(int k=0;k<pbl.size();k++){
			if(matriz[numP-1][k]==3){
				String pAux = columnas.get(k);
				if(!pr.contains(pAux))
					impactados.addAll(getImpactadosP(pAux, pr));
			}
		}
		return (TreeSet<String>)impactados.clone();
	}
	
	/**
	 * Informa del impacto que tiene una publicacion
	 * @param p Publicacion
	 */
	public static void impactoP(String p){
		ArrayList<String> aux = new ArrayList<String>();
		if(pbl.contains(p)){
			int impacto = getImpactadosP(p,aux).size();
			if(impacto>0){
				System.out.println("La publicacion " + p + " tiene un impacto de: " + impacto + "\n");
			}else{
				System.out.println("La publicacion " + p + " no tiene ningun impacto\n");
			}
		}else{
			System.out.println("Ese nombre no corresponde a ninguna publicacion\n");
		}
	}
	
	/**
	 * Devuelve una lista de publicaciones que han impactado en todos los usuarios
	 * @return Publicaciones que han impactado en todos los usuarios
	 */
	public static ArrayList<String> impactoEnTodos(){
		ArrayList<String> publicaciones = new ArrayList<String>();
		ArrayList<String> aux;
		for(Iterator<String> it = pbl.iterator(); it.hasNext();){
			String p = it.next();
			aux = new ArrayList<String>();
			if(getImpactadosP(p, aux).equals(usr)){
				publicaciones.add(p);
			}	
		}
		
		return (ArrayList<String>)publicaciones.clone();
	}
	
	/**
	 * Aniade los usuarios y publicaciones al conjunto de usuarios y publicaciones segun 
	 * el modo con el que interactuan
	 * 1 => Lee
	 * 2 => Escribe
	 * 3 => Cita
	 * usr1 interacciona con usr2
	 * @param usr1 Primero usuario
	 * @param usr2 Segundo usuario
	 * @param modo Modo en el que interaccionan
	 */
	public static void aniadeUsuarios(String usr1, String usr2, int modo){
		if(modo==3){
			pbl.add(usr1);
			pbl.add(usr2);
		}else{
			usr.add(usr1);
			pbl.add(usr2);
		}
	}
	
	/**
	 * Metodo principal del programa
	 * @param args
	 */
	public static void main(String []args){
		usr = new TreeSet<String>();
		pbl = new TreeSet<String>();
		boolean ejecutar=true;
		String cadena;
		String publicacion;
		
		matriz=leerfichero(); //Guardamos los datos del fichero en una matriz de adyacencia
		
		Scanner in = new Scanner(System.in);
		
		while(ejecutar){
			System.out.println("Elija una de las siguientes opciones:");
			System.out.println("1: Saber el numero de lecturas de una publicacion.");
			System.out.println("2: Saber el impacto total de una publicacion.");
			System.out.println("exit: Finalizar la ejecución del programa.");
			System.out.println("Opcion elegida: ");
			cadena=in.next();
			switch (cadena){ //Ejecutamos un switch para las opciones de teclado
				case "1":
					System.out.println("\nIntroduzca el nombre de la publicacion");
					System.out.println("o escriba exit para volver al menu principal: ");
					publicacion = in.next();
					if(!publicacion.equals("exit"))
						lecturaP(publicacion);
					break;
				case "2":
					System.out.println("\nIntroduzca el nombre de la publicacion");
					System.out.println("o escriba exit para volver al menu principal: ");
					publicacion = in.next();
					if(!publicacion.equals("exit"))
						impactoP(publicacion);
					break;
				case "exit":
					ejecutar = false;
					System.out.println("\nHa elegido finalizar el programa.");
					break;
				default:
					System.out.println("No es una opcion valida, introduzca una opcion correcta.\n");
			}
		}
		
		in.close();
		
		//Escribimos en un fichero los datos deseados
		PrintWriter escribeResult = null;
		try {
			escribeResult = new PrintWriter("salida_p3_ismpere_vicrojo_davgome.txt");
			escribeResult.write("PUBLICACIONES MAS LEIDAS:\n");
			
			ArrayList<String> pbls = maxLecturas();
			
			if(pbls.isEmpty()){
				escribeResult.write("No se ha leido ninguna publicacion\n\n");
			}else{
				for(int i=0; i<pbls.size()-1; i++){
					escribeResult.write(pbls.get(i) + ", ");
				}
				escribeResult.write(pbls.get(pbls.size()-1) + "\n\n");
			}
			
			escribeResult.write("PUBLICACIONES QUE HAN IMPACTADO EN TODOS LOS USUARIOS:\n");
			
			pbls = impactoEnTodos();
			
			if(pbls.isEmpty()){
				escribeResult.write("Ninguna publicacion ha impactado en todos los usuarios");
			}else{
				for(int i=0; i<pbls.size()-1; i++){
					escribeResult.write(pbls.get(i) + ", ");
				}
				escribeResult.write(pbls.get(pbls.size()-1) + "\n\n");
			}
			
			escribeResult.close();
		} catch (IOException E) {
			System.out.println("No se puede crear el fichero de salida");
		}
		
	}	
}