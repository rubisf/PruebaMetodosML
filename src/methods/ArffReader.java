package methods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ArffReader {
	
	private static BufferedReader br;

	public static List<ArffLine> getDataFromFile(String fileName){
		List<ArffLine> l = new LinkedList<ArffLine>();
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			Boolean leer = false;
			String s = "";
			while((s = br.readLine()) != null){
				if(leer && ! s.equals("")){
					ArffLine L1 = new ArffLine();
					String[] parts = s.split(",");
					for (String si : parts) {
					    L1.addFeature(Float.parseFloat(si));
					}
					l.add(L1);
				}
				if(s.equalsIgnoreCase("@data")){
					leer = true;
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		
	}

}
