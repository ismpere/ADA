import java.util.ArrayList;
public class Practica2 {
	
	static ArrayList<Integer> posV1, posV2;
	static int prof1, prof2;
	
	public static void main(String[] args){
		posV1 = new ArrayList<Integer>();
		posV2 = new ArrayList<Integer>();
		prof1 = 0;
		prof2 = 0;
		ArrayList<String> uno = new ArrayList<String>();
		ArrayList<String> dos = new ArrayList<String>();
		uno.add("G");
		uno.add("C");
		uno.add("C");
		uno.add("C");
		uno.add("T");
		uno.add("A");
		uno.add("G");
		uno.add("C");
		uno.add("X");
		uno.add("G");
		
		dos.add("G");
		dos.add("C");
		dos.add("G");
		dos.add("C");
		dos.add("A");
		dos.add("A");
		dos.add("T");
		dos.add("G");
		System.out.println(funcion(uno, dos).toString());
		System.out.println(posV1.toString());
		System.out.println(posV2.toString());
	}
	public static ArrayList<String> cadena(ArrayList<String> x, ArrayList<String> y){
		
		return funcion(x,y);
	}
	public static ArrayList<String> funcion (ArrayList<String> x, ArrayList<String> y){
		  int j = 0;
		  ArrayList<String> listaFin = new ArrayList<String>();
		  ArrayList<String> sublist1 = new ArrayList<String>(x.subList(1,x.size()));
		  ArrayList<String> sublist2 = new ArrayList<String>();
		  if(x.isEmpty() || y.isEmpty()){
		    return listaFin;
		  }else{
			prof1++;
		    while((j<y.size()) && (x.get(0)!=y.get(j))){
		      j++;
		    }
		    if(j<y.size()){
		    	listaFin.add(x.get(0));
		    	prof2+=j;
		    	posV1.add(prof1-1);
		    	posV2.add(prof2);
		    	if(j==y.size()-1){
		    		return listaFin;
		    	}else{
		    		prof2++;
		    		sublist2 = new ArrayList<String>(y.subList(j+1, y.size()));
		    	}
		    }else{
		    	sublist2 = y;
		    }
		    
		    listaFin.addAll(funcion(sublist1,sublist2));
		    
		    return listaFin;
		  }
		}

}
