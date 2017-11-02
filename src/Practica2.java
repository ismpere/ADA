import java.util.ArrayList;
public class Practica2 {
	
	static ArrayList<Integer> posV1, posV2;
	static ArrayList<String> cadenas, uno, dos, cadFin;
	static int prof1, prof2, lMax;
	
	public static void main(String[] args){
		posV1 = new ArrayList<Integer>(); posV2 = new ArrayList<Integer>();
		uno = new ArrayList<String>(); dos = new ArrayList<String>();
		cadFin = new ArrayList<String>(); cadenas = new ArrayList<String>();
		String cad1, cad2;
		lMax = 0; prof1 = 0; prof2 = 0;
		
		//cad1 = "GCCCTAGCG";
		//cad2 = "GCGCAATG";
		cad1 = "a1b2c3d4e";
		cad2 = "zz1yy2xx3ww4vv";
		//cad1 = "abcdefghijklmnzyxwvutsrqpo";
		//cad2 = "opqrstuvwxyzabcdefghijklmn";
		//cad1 = "bcacbcabbaccbab";
		//cad2 = "bccabccbbabacbc";
		uno = toArrayList(cad1);
		dos = toArrayList(cad2);
		System.out.println(extraeCadenas(uno,dos));
	}
	public static ArrayList<String> extraeCadenas(ArrayList<String> x, ArrayList<String> y){
		aniadeConjunto(cadenasComunes(x, y,0,0));
		return getMasLargas();
	}
	public static ArrayList<String> cadenasComunes(ArrayList<String> x, ArrayList<String> y, int c1, int c2){
		ArrayList<Integer> posV1Aux, posV2Aux;
		ArrayList<String> cadAux1, cadAux2, cadComun;
		ArrayList<String> cadenas;
		cadenas = new ArrayList<String>();
		prof1 = 0; prof2 = 0;
		cadComun = primeraCadena(x,y,c1,c2, false);
		prof1 = 0; prof2 = 0;
		if(!cadComun.isEmpty()){
			cadenas.add(toCadena(cadComun));
			if(cadComun.size()>lMax){
				lMax = cadComun.size();
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
			while(n1<uno.size()){
				cadAux1 = new ArrayList<String>(uno.subList(n1, uno.size()));
				cadenas.addAll(unirArray(cadComun, cadenasComunes(cadAux1, cadAux2,n1,n2)));
				if(getMaxLong(cadenas)>lMax){
					lMax = getMaxLong(cadenas);
				}
				n1++;
			}
		}	
		return cadenas;
	}
	public static ArrayList<String> unirArray(ArrayList<String> x, ArrayList<String> y){
		if(x.isEmpty()){
			return y;
		}else{
			ArrayList<String> z;
			z = new ArrayList<String>();
			for(int i =0; i<y.size(); i++){
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
	public static ArrayList<String> primeraCadena (ArrayList<String> x, ArrayList<String> y,
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
		    
		    listaFin.addAll(primeraCadena(sublist1,sublist2,c1,c2, slaux));
		    return listaFin;
		  }
		}

}