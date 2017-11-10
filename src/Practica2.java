import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * 
 * @author ismpere
 * @author davgome
 * @author vicrojo
 * Implementacion de la busqueda de cadenas comunes mas largas en dos cadenas 
 */
public class Practica2 {
	
	static ArrayList<Integer> posV1, posV2;
	static ArrayList<String> cadenas, uno, dos, cadFin;
	static int prof1, prof2, lMax;
	/**
	 * Metodo principal
	 */
	public static void main(String[] args){
		String fichero = "entrada7.txt";
		posV1 = new ArrayList<Integer>(); posV2 = new ArrayList<Integer>();
		uno = new ArrayList<String>(); dos = new ArrayList<String>();
		cadFin = new ArrayList<String>(); cadenas = new ArrayList<String>();
		String cad1 = ""; String cad2 = "";
		lMax = 0; prof1 = 0; prof2 = 0;
		double tInicio, tFinal;
		
		try{
			Scanner fichscan = new Scanner(new File(fichero));
			cad1=fichscan.next();
			cad2=fichscan.next();
			fichscan.close();
		}catch(Exception e){
			System.out.println(e);
		}
		uno = toArrayList(cad1);
		dos = toArrayList(cad2);
		
		tInicio = System.nanoTime();
		extraeCadenas(uno,dos);
		tFinal = System.nanoTime();
		System.out.println((tFinal-tInicio)/1000000000);
		
		PrintWriter escribeResult = null;
		try {
			escribeResult = new PrintWriter("salida_p2_ismpere_vicrojo_davgome.txt");
			if(cadenas.isEmpty()){
				escribeResult.write("No hay cadenas comunes");
			}else{
				for(int i=0; i<cadenas.size(); i++){
					escribeResult.write(cadenas.get(i) + "\n");
				}
			}
			escribeResult.close();
		} catch (IOException E) {
			System.out.println("No se puede crear el fichero de salida");
		}
	}
	/**
	 * Devuelve las cadenas mas largas comunes a las dos cadenas 
	 * @param x Primera cadena
	 * @param y	Segunda cadena
	 * @return	z Cadenas comunes mas largas
	 */
	public static void extraeCadenas(ArrayList<String> x, ArrayList<String> y){
		cadenasComunes(x, y, 0, 0, "");
		aniadeConjunto();
		System.out.println(cadenas);
	}
	/**
	 * Devuelve las cadenas comunes a las dos cadenas
	 * @param x	Primera cadena
	 * @param y	Segunda cadena
	 * @param c1	Posicion inicial de la primera cadena con respecto a la primera cadena global
	 * @param c2	Posicion inicial de la segunda cadena con respecto a la segunda cadena global
	 * @return	z Cadenas comunes a las dos cadenas
	 */
	public static void cadenasComunes(ArrayList<String> x, ArrayList<String> y, int c1, int c2,
			 String sc){
		ArrayList<Integer> posV1Aux, posV2Aux;
		ArrayList<String> cadAux1, cadAux2, cadComun;
		cadComun = primeraCadena(x,y,c1,c2, false);
		String cadena = sc + toCadena(cadComun);
		prof1 = 0; prof2 = 0;
		if(!cadComun.isEmpty() && (cadena.length())>=lMax && !cadenas.contains(cadena)){
			cadenas.add(cadena);
			if(cadena.length()>lMax){
				lMax = cadena.length();
			}
		}
		posV1Aux = (ArrayList<Integer>) posV1.clone();
		posV2Aux = (ArrayList<Integer>) posV2.clone();
		posV1.clear(); posV2.clear();
		
		while(posV1Aux.size()>0){
			cadComun.remove(cadComun.size()-1);
			int n1 = (posV1Aux.remove(posV1Aux.size()-1))+1;
			int n2;
			if(posV2Aux.size()<2){
				n2=c2;
				cadAux2=y;
			}else{
				n2 = (posV2Aux.remove(posV2Aux.size()-2))+1;
				cadAux2 = new ArrayList<String>(dos.subList(n2, dos.size()));
			}
			int tm2 = dos.size()-(n2);
			int tm;
			String scAux = sc + toCadena(cadComun);
			while(n1<uno.size()){
				if((uno.size()-(n1))<tm2){
					tm = (uno.size()-(n1));
				}else{
					tm = tm2;
				}
				if(tm+scAux.length()>=lMax){
					cadAux1 = new ArrayList<String>(uno.subList(n1, uno.size()));
					cadenasComunes(cadAux1, cadAux2,n1,n2,scAux);
					n1++;
				}else{
					break;
				}
			}
		}	
	}
	/**
	 * Devuelve los valores del array como un String unido
	 * @param s Cadena a transformar a String
	 * @return z cadena transformada a String
	 */
	public static String toCadena(ArrayList<String> s){
		String aux = "";
		for(int i =0; i<s.size(); i++){
			aux = aux + s.get(i);
		}
		return aux;
	}
	/**
	 * Devuelve la lista de cadenas no repetidas de las dos cadenas
	 * @param x	Cadena en la que se guardaran las cadenas
	 * @param y	Cadenas a evaluar
	 */
	public static void aniadeConjunto(){
		ArrayList<String> x = new ArrayList<String>();
		for(int i =0; i<cadenas.size(); i++){
			if(!x.contains(cadenas.get(i)) && cadenas.get(i).length()==lMax){
				x.add(cadenas.get(i));
			}
		}
		cadenas = x;
	}
	/**
	 * Devuelve cada letra de un String en un array
	 * @param s	String a convertir en array
	 * @return	z array equivalente al String
	 */
	public static ArrayList<String> toArrayList(String s){
		ArrayList<String> x = new ArrayList<String>();
		for(int i=0; i<s.length(); i++){
			x.add(String.valueOf(new char[]{s.charAt(i)}));
		}
		return x;
	}
	/**
	 * Devuelve la cadena mas larga comun de dos cadenas recorriendo la primera cadena en orden
	 * Esta cadena se caracteriza ya que su primer caracter es el primer valor comun de las dos cadenas,
	 * el segundo valor es el primer valor comun entre las dos cadenas recortadas desde el primer valor
	 * encontrado y asi sucesivamente gasta el final de la cadena.
	 * @param x	Primera cadena
	 * @param y	Segunda cadena
	 * @param c1	Profundidad en la que se empieza a buscar en la primera cadena
	 * @param c2	Profundidad en la que se empieza a buscar en la segunda cadena
	 * @param sl	Si la segunda cadena es una sublista o no
	 * @return	z Cadena mas larga comun recorriendo en orden la primera cadena
	 */
	public static ArrayList<String> primeraCadena (ArrayList<String> x, ArrayList<String> y,
			int c1, int c2, boolean sl){
		  ArrayList<String> listaFin = new ArrayList<String>();
		  if(x.isEmpty() || y.isEmpty()){
			if(sl){
	    		prof2--;
	    	}
		    return listaFin;
		  }else{
			int j = 0;
			boolean slaux = false;
			ArrayList<String> sublist2 = new ArrayList<String>();
			ArrayList<String> sublist1 = new ArrayList<String>(x.subList(1,x.size()));
			prof1++;
		    while((j<y.size()) && !(x.get(0).equals(y.get(j)))){
		      j++;
		    }
		    if(j<y.size()){
		    	listaFin.add(x.get(0));
		    	prof2+=j;
		    	posV1.add((prof1-1)+c1);
		    	posV2.add(prof2 + c2);
		    	if(j==y.size()-1){
		    		return listaFin;
		    	}else{
		    		prof2++;
		    		slaux = true;
		    		sublist2 = new ArrayList<String>(y.subList(j+1, y.size()));
		    	}
		    }else{
		    	sublist2 = y;
		    }
		    
		    listaFin.addAll(primeraCadena(sublist1,sublist2,c1,c2, slaux));
		    return listaFin;
		  }
		}

}
