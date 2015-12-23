package methods;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Knn {
	
	private int k;
	private List<ArffLine> training;
	
	//Distancia eucl�dea. Hago el size de la instancia porque el training puede contener el resultado
	private float distancia(ArffLine training,ArffLine instance){
		float dist = 0;
		for(int i = 0;i<instance.getNumFeatures();i++){
			dist += Math.sqrt(((float)training.getFeatureAt(i)-(float)instance.getFeatureAt(i))*((float)training.getFeatureAt(i)-(float)instance.getFeatureAt(i)));
		}
		return dist;
	}
	
	/**
	 * Devuelve los k vecinos mas cercanos de un trainingSet
	 * @param trainingSet
	 * @param testInstance
	 * @return
	 */
	private ArffLine getVecinos( ArffLine testInstance){
		List<ArffLine> distancias = new LinkedList<ArffLine>(this.training);
		for (int i = 0; i < this.training.size(); i++){
			System.out.println(i);
			float dist = this.distancia(this.training.get(i),testInstance);
			distancias.get(i).addFeature(dist);
		}
		//Ordeno las distancias, que las he anadido en el ultimo elemento
		extracted_order(distancias);
		
		System.out.println("asdf");

			ArffLine vecinos = new ArffLine();
			for(int i = 0;i<k;i++){
				vecinos.addFeature(distancias.get(i).getFeatureAt(distancias.get(i).getNumFeatures() - 2));
			}
			
			return vecinos;
	}

	private void extracted_order(List<ArffLine> distancias) {
		Collections.sort(distancias, (o1, o2) -> {
			ArffLine ar1 = (ArffLine) o1;
			ArffLine ar2 = (ArffLine) o2;
			float dist1 = ar1.getFeatureAt(ar1.getNumFeatures() - 1);
		    float dist2 = ar2.getFeatureAt(ar2.getNumFeatures() - 1);
		    if (dist1 < dist2) return -1;
		    if (dist1 > dist2) return 1;
		    return 0;
		});
	}
	
	private float getMaximoRepetido(ArffLine vecinos){
		//EL campe�n es:
		float max = Float.MIN_VALUE;
		int maxFreq = 0;
		List<Float> vecinosar = vecinos.getAsList();
		for(int i = 0; i < vecinosar.size(); i++){
		if(Collections.frequency(vecinosar, (float)vecinosar.get(i)) > maxFreq){
			max = (float) vecinosar.get(i);
			maxFreq = Collections.frequency(vecinosar, (float)vecinosar.get(i));
			}
		}
		return max;
	}
	
	public float predecir(ArffLine testInstance){
		ArffLine vecinos = this.getVecinos(testInstance);
		float max = this.getMaximoRepetido(vecinos);
		return max;
	}
	
	
	
	public static void main(String[] args){
		

		
		List<ArffLine> l = ArffReader.getDataFromFile("flores.arff");
		Knn k = new Knn(5,l);
		//Primer ejemplo
		//List<Float> test = new LinkedList<Float>();
		//test.add((float) 0);
		//test.add((float) 60);
		//test.add((float) 30);
		//test.add((float) 1);
		
		//Segundo ejemplo
		//5.6,3.0,4.5,1.5
		ArffLine test = new ArffLine();
		test.addFeature((float) 3);
		test.addFeature((float) 3);
		test.addFeature((float) 3);
		test.addFeature((float) 3);
		
		System.out.println(k.predecir(test));
	}
	
	public Knn(int k, List<ArffLine> training){
		this.k = k;
		this.training = training;
		
	}
	
}
