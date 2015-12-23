package machineL.methods;

import java.util.LinkedList;
import java.util.List;

public class Kmeans {
	
	private List<ArffLine> training;
	private int k;
	private List<ArffLine> centroids;
	private List<List<ArffLine>> clasification;
	
	private void resetClasification(){
		clasification = new LinkedList<List<ArffLine>>();
		for(int i = 0;i<k;i++){
			clasification.add(new LinkedList<ArffLine>());
		}
	}
	
	public Kmeans(int k, List<ArffLine> training){
		this.k = k;
		this.training = training;
		this.centroids = getRandomCentroids();
		this.clasification = new LinkedList<List<ArffLine>>();
	}
	
	/**
	 * Extrae un n�mero aleatorio de una columna determinada
	 * @return
	 */
	private float getAleatorio(int col){
		ArffLine elems = new ArffLine();
		int num = 0;
		for(int i = 0;i<(training.get(0)).getNumFeatures();i++){
			elems.addFeature((Float) (training.get(i)).getFeatureAt(col));
		}
		
		num = Random.randInt(0, elems.getNumFeatures()-1);
		
		return elems.getFeatureAt(num);
	}
	
	/**
	 * Extrae un n�mero medio
	 * @return
	 */
	private float getMedio(int col, List<ArffLine> elems){
		float sum = 0;
		for(int i = 0;i<elems.size();i++){
			sum = sum + (Float) (elems.get(i)).getFeatureAt(col);
		}
		return sum/elems.size();
	}
	
	private List<ArffLine> getRandomCentroids(){
		List<ArffLine> l = new LinkedList<ArffLine>();
		
		for(int i=0;i<k;i++){
			int dim = (this.training.get(0)).getNumFeatures();
			ArffLine l2 = new ArffLine();
			for(int j = 0;j<dim;j++){
				l2.addFeature(getAleatorio(j));
			}
			l.add(l2);
		}
		
		return l;
	}
	
	private int getMinIndex(ArffLine vecinos){
		//EL campe�n es:
		float min = Float.MAX_VALUE;
		int minIndex = 0; //this is what you looking for
		for(int i = 0; i < vecinos.getNumFeatures(); i++){
		if(vecinos.getFeatureAt(i) < min){
			min = vecinos.getFeatureAt(i);
			minIndex = i;
			}
		}
		return minIndex;
	}
	
	//Distancia eucl�dea. Hago el size de la instancia porque el training puede contener el resultado
	private float distancia(ArffLine training,ArffLine instance){
			float dist = 0;
			for(int i = 0;i<instance.getNumFeatures();i++){
				dist += Math.sqrt(((float)training.getFeatureAt(i)-(float)instance.getFeatureAt(i))*((float)training.getFeatureAt(i)-(float)instance.getFeatureAt(i)));
			}
			return dist;
	}
	
	public int predecir(ArffLine testInstance){
		ArffLine distancias = new ArffLine();
		
		//CALCULO LA DISTANCIA EUCLIDEA DE CUALQUIER CENTROIDO A LA testInstance
		for(int i=0;i<this.centroids.size();i++){
			float dist = this.distancia(centroids.get(i), testInstance);
			distancias.addFeature(dist);
		}
		return this.getMinIndex(distancias);
	}
	
	public void clasificar(){
		List<ArffLine> centOld = null;
		List<List<ArffLine>> clasiOld = new LinkedList<List<ArffLine>>();;
		int iter = 0;
		while(! clasiOld.toString().equals(this.clasification.toString())  || iter == 0){
			clasiOld = this.clasification;
			this.resetClasification();
			if(iter==0){
				centOld = new LinkedList<ArffLine>(this.centroids);
			}
			else
				centOld = new LinkedList<ArffLine>(this.centroids);
			for(int i = 0;i<this.training.size();i++){
				//Lo clasifico
				int indexCentr = this.predecir(training.get(i));
				List<ArffLine> clasi = this.clasification.get(indexCentr);
				clasi.add(training.get(i));
				this.clasification.set(indexCentr, clasi);
			}
			
			//CALCULO LOS NUEVOS CENTROIDES
			for(int i = 0; i < this.centroids.size();i++){
				ArffLine centroid =  this.centroids.get(i);
				for(int j = 0; j < centroid.getNumFeatures();j++){
					centroid.set(j, this.getMedio(j, centOld));
				}
			}
			iter = iter + 1;
		}
	}
	
	public static void main(String[] args){
		List<ArffLine> l = ArffReader.getDataFromFile("sampleFiles/baloncesto.arff");
		Kmeans k = new Kmeans(3,l);
		k.clasificar();
		//Imprimo la salida
		for(int i = 0;i<k.clasification.size();i++){
			System.out.println("Grupo"+i);
			System.out.println(k.clasification.get(i));
			
		}
		
	}

}
