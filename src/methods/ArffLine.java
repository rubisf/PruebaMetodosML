package machineL.methods;

import java.util.LinkedList;
import java.util.List;

public class ArffLine {
	
	private List<Float> features;
	
	public ArffLine(){
		this.features = new LinkedList<Float>();
	}
	
	public void addFeature(Float f){
		this.features.add(f);
	}
	
	public float getFeatureAt(int pos){
		return this.features.get(pos);
	}
	
	public int getNumFeatures(){
		return this.features.size();
	}
	
	public void set(int i, float f){
		this.features.set(i, f);
	}
	
	public String toString(){
		return features.toString();
	}
	
	public List<Float> getAsList(){
		List<Float> f = new LinkedList<Float>();
		for(int i = 0;i<this.getNumFeatures();i++){
			f.add(this.getFeatureAt(i));
		}
		return f;
	}
	

}
