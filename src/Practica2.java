import java.util.ArrayList;
public class Practica2 {
	
	static ArrayList<Integer> posV1, posV2;
	static ArrayList<String> cadenas, uno, dos;
	static int cP1, cP2, prof1, prof2;
	
	public static void main(String[] args){
		posV1 = new ArrayList<Integer>();
		posV2 = new ArrayList<Integer>();
		cadenas = new ArrayList<String>();
		uno = new ArrayList<String>();
		dos = new ArrayList<String>();
		cP1 = 0;
		cP2 = 0;
		prof1 = 0;
		prof2 = 0;
		uno.add("G"); uno.add("C"); uno.add("C"); uno.add("C"); uno.add("T"); uno.add("A");
		uno.add("G"); uno.add("C"); uno.add("G");
		
		dos.add("G"); dos.add("C"); dos.add("G"); dos.add("C");
		dos.add("A"); dos.add("A"); dos.add("T"); dos.add("G");
		
		//[G, C, X, G]
		//[G, C, G, C, A, A, T, G]
		
		//System.out.println(prof1 + "-" + prof2);
		//System.out.println(toCadena(funcion(uno,dos,0,0, false)));
		System.out.println(cadena(uno, dos,0,0));
		//System.out.println(posV1);
		//System.out.println(posV2);
	}
	public static ArrayList<String> cadena(ArrayList<String> x, ArrayList<String> y, int c1, int c2){
		System.out.println("Se invoca funcion con cadenas: \n" + x + "\n" + y);
		ArrayList<Integer> posAux1, posAux2;
		ArrayList<String> cadAux1, cadAux2, subCad1, subCad2, cad, cadenasAux, cadA;
		ArrayList<String> cadenas;
		cadenas = new ArrayList<String>();
		prof1 = 0; prof2 = 0;
		cad = funcion(x,y,c1,c2, false);
		prof1 = 0; prof2 = 0;
		if(!cad.isEmpty()){
			cadenas.add(toCadena(cad));
		}
		posAux1 = (ArrayList<Integer>) posV1.clone();
		posAux2 = (ArrayList<Integer>) posV2.clone();
		posV1.clear(); posV2.clear();
		System.out.println("+++++Cadena comun: " + cad + " +++++");
		System.out.println("Posiciones: \n" + posAux1 + "\n" + posAux2);
		while(posAux1.size()>0){
			cad.remove(cad.size()-1);
			System.out.println("****Subcadena: " + cad + " ****");
			int tamV1 = posAux1.size();
			int n1 = (posAux1.remove(posAux1.size()-1))+1;
			int n2;
			if(posAux2.size()-2<0){
				n2=0;
			}else{
				n2 = (posAux2.remove(posAux2.size()-2))+1;
			}
			cP1 = n1-1;
			cP2 = n2-1;
			System.out.println("---Indices=> n1:" + n1 + "  n2: " + n2 + " --");
			cadAux2 = new ArrayList<String>(dos.subList(n2, dos.size()));
			while(n1<uno.size()){
				System.out.println("Profundidad1: " + prof1);
				System.out.println("Profundidad2: " + prof2);
				cadAux1 = new ArrayList<String>(uno.subList(n1, uno.size()));
				cadA = unirArray(cad, cadena(cadAux1, cadAux2,n1,n2));
				cadenas.addAll(cadA);
				System.out.println("Cadenas con indice " + n1 + ": " +cadenas);
				n1++;
				cP1++;
			}
			System.out.println("Posiciones2: \n" + posAux1 + "\n" + posAux2);
		}	
		System.out.println("\nDevuelve cadenas: \n" + cadenas);
		return cadenas;
	}
	public static ArrayList<String> unirArray(ArrayList<String> x, ArrayList<String> y){
		ArrayList<String> z, aux;
		z = new ArrayList<String>();
		for(int i =0; i<y.size(); i++){
			aux = (ArrayList<String>) x.clone();
			z.add((toCadena(x) + y.get(i)));
		}
		return z;
	}
	public static String toCadena(ArrayList<String> s){
		String aux = "";
		for(int i =0; i<s.size(); i++){
			aux = aux + s.get(i);
		}
		return aux;
	}
	public static ArrayList<String> funcion (ArrayList<String> x, ArrayList<String> y,
			int c1, int c2, boolean sl){
		  int j = 0;
		  boolean slaux = false;
		  ArrayList<String> listaFin = new ArrayList<String>();
		  ArrayList<String> sublist2 = new ArrayList<String>();
		  if(x.isEmpty() || y.isEmpty() || x.size()==1){
			if(sl){
	    		prof2--;
	    	}
		    return listaFin;
		  }else{
			ArrayList<String> sublist1 = new ArrayList<String>(x.subList(1,x.size()));
			prof1++;
		    while((j<y.size()) && (x.get(0)!=y.get(j))){
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
		    
		    listaFin.addAll(funcion(sublist1,sublist2,c1,c2, slaux));
		    return listaFin;
		  }
		}

}
