import java.util.ArrayList;
public class Practica2 {
	
	static ArrayList<Integer> posV1, posV2;
	static ArrayList<String> cadenas, uno, dos;
	static ArrayList<String> cadFin;
	static int cP1, cP2, prof1, prof2, lMax, nivel;
	
	public static void main(String[] args){
		posV1 = new ArrayList<Integer>();
		posV2 = new ArrayList<Integer>();
		cadenas = new ArrayList<String>();
		uno = new ArrayList<String>();
		dos = new ArrayList<String>();
		cadFin = new ArrayList<String>();
		String cad1, cad2;
		lMax = 0;
		cP1 = 0;
		cP2 = 0;
		prof1 = 0;
		prof2 = 0;
		nivel = 0;
		/*uno.add("G"); uno.add("C"); uno.add("C"); uno.add("C"); uno.add("T"); uno.add("A");
		uno.add("G"); uno.add("C"); uno.add("G");
		
		dos.add("G"); dos.add("C"); dos.add("G"); dos.add("C");
		dos.add("A"); dos.add("A"); dos.add("T"); dos.add("G");*/
		
		cad1 = "GCCCTAGCG";
		cad2 = "GCGCAATG";
		//cad1 = "a1b2c3d4e";
		//cad2 = "zz1yy2xx3ww4vv";
		//cad1 = "bcacbcabbaccbab";
		//cad2 = "bccabccbbabacbc";
		uno = toArrayList(cad1);
		dos = toArrayList(cad2);
		
		/*uno.add("b"); uno.add("c"); uno.add("a"); uno.add("c"); uno.add("b");
		uno.add("c"); uno.add("a"); uno.add("b"); uno.add("b"); uno.add("a");
		uno.add("c"); uno.add("c"); uno.add("b"); uno.add("a"); uno.add("b");
		dos.add("b"); dos.add("c"); dos.add("c"); dos.add("a"); dos.add("b");
		dos.add("c"); dos.add("c"); dos.add("b"); dos.add("b"); dos.add("a");
		dos.add("b"); dos.add("a"); dos.add("c"); dos.add("b"); dos.add("c");*/
		
		//System.out.println(toCadena(funcion(uno,dos,0,0, false)));
		System.out.println(metodoBueno(dos,uno));
		System.out.println(lMax);
		//System.out.println(posV1);
		//System.out.println(posV2);
	}
	public static ArrayList<String> metodoBueno(ArrayList<String> x, ArrayList<String> y){
		aniadeConjunto(cadena(uno, dos,0,0));
		return getMasLargas();
	}
	public static ArrayList<String> cadena(ArrayList<String> x, ArrayList<String> y, int c1, int c2){
		nivel ++;
		System.out.println("Se invoca funcion con cadenas: \n" + x + "\n" + y);
		ArrayList<Integer> posAux1, posAux2;
		ArrayList<String> cadAux1, cadAux2, cad, cadA;
		ArrayList<String> cadenas;
		cadenas = new ArrayList<String>();
		prof1 = 0; prof2 = 0;
		cad = funcion(x,y,c1,c2, false);
		System.out.println("+++++Cadena comun: " + cad + " +++++");
		prof1 = 0; prof2 = 0;
		if(!cad.isEmpty()){
			cadenas.add(toCadena(cad));
			if(cad.size()>lMax){
				lMax = cad.size();
			}
		}
		posAux1 = (ArrayList<Integer>) posV1.clone();
		posAux2 = (ArrayList<Integer>) posV2.clone();
		posV1.clear(); posV2.clear();
		
		System.out.println("Posiciones: \n" + posAux1 + "\n" + posAux2);
		while(posAux1.size()>0){
			cad.remove(cad.size()-1);
			System.out.println("****Subcadena: " + cad + " ****");
			int n1 = (posAux1.remove(posAux1.size()-1))+1;
			int n2;
			System.out.println("Posiciones con nivel " + nivel + ":\n" + posAux1 + "\n" + posAux2);
			if(posAux2.size()<2){
				n2=c2;
				cadAux2=y;
			}else{
				n2 = (posAux2.remove(posAux2.size()-2))+1;
				cadAux2 = new ArrayList<String>(dos.subList(n2, dos.size()));
			}
			cP1 = n1-1;
			cP2 = n2-1;
			System.out.println("---Indices=> n1:" + n1 + "  n2: " + n2 + " --");
			while(n1<uno.size()){
				cadAux1 = new ArrayList<String>(uno.subList(n1, uno.size()));
				cadA = unirArray(cad, cadena(cadAux1, cadAux2,n1,n2));
				cadenas.addAll(cadA);
				if(getMaxLong(cadenas)>lMax){
					lMax = getMaxLong(cadenas);
				}
				//System.out.println("Cadenas con indice " + n1 + " y nivel " + nivel +" : " +cadenas);
				n1++;
				cP1++;
			}
			//System.out.println("Posiciones fuera del bucle: \n" + posAux1 + "\n" + posAux2);
		}	
		//System.out.println("\nDevuelve cadenas: \n" + cadenas);
		nivel --;
		return cadenas;
	}
	public static ArrayList<String> unirArray(ArrayList<String> x, ArrayList<String> y){
		if(x.isEmpty()){
			return y;
		}else{
			ArrayList<String> z, aux;
			z = new ArrayList<String>();
			for(int i =0; i<y.size(); i++){
				aux = (ArrayList<String>) x.clone();
				z.add((toCadena(x) + y.get(i)));
			}
			return z;
		}
	}
	public static String toCadena(ArrayList<String> s){
		String aux = "";
		for(int i =0; i<s.size(); i++){
			aux = aux + s.get(i);
		}
		return aux;
	}
	public static void aniadeConjunto(ArrayList<String> x){
		for(int i =0; i<x.size(); i++){
			if(!cadFin.contains(x.get(i)))
					cadFin.add(x.get(i));
		}
	}
	public static ArrayList<String> getMasLargas(){
		ArrayList<String> aux = new ArrayList<String>();
		for(int i=0; i<cadFin.size(); i++){
			if(cadFin.get(i).length()==lMax){
				aux.add(cadFin.get(i));
			}
		}
		return aux;
	}
	public static ArrayList<String> toArrayList(String s){
		ArrayList<String> x = new ArrayList<String>();
		for(int i=0; i<s.length(); i++){
			x.add(String.valueOf(new char[]{s.charAt(i)}));
		}
		return x;
	}
	public static int getMaxLong(ArrayList<String> x){
		int l = 0;
		int lAux;
		for(int i=0; i<x.size(); i++){
			lAux = x.get(i).length();
			if(lAux>l){
				l=lAux;
			}
		}
		return l;
	}
	public static ArrayList<String> funcion (ArrayList<String> x, ArrayList<String> y,
			int c1, int c2, boolean sl){
		  int j = 0;
		  boolean slaux = false;
		  ArrayList<String> listaFin = new ArrayList<String>();
		  ArrayList<String> sublist2 = new ArrayList<String>();
		  if(x.isEmpty() || y.isEmpty()){
			if(sl){
	    		prof2--;
	    	}
		    return listaFin;
		  }else{
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
		    
		    listaFin.addAll(funcion(sublist1,sublist2,c1,c2, slaux));
		    return listaFin;
		  }
		}

}