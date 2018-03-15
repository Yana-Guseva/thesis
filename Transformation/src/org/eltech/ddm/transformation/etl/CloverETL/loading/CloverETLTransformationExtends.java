package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eltech.ddm.transformation.etl.CloverETL.CloverETLTransformation;
import org.reflections.Reflections;

/**
 * Gets CloverETLTransformation extends
 * 
 * @author SemenchenkoA
 *
 */
public class CloverETLTransformationExtends {
	private Map<Integer, String> transformationExtends = null;
	private Set<Class<? extends CloverETLTransformation>> subTypes;
	
	public Map<Integer, String> getTransformationExtends(){
		transformationExtends = new HashMap<Integer, String>();
		
		Reflections reflections = new Reflections("org.eltech.ddm.transformation.etl.CloverETL.*");
		subTypes = reflections.getSubTypesOf(CloverETLTransformation.class);
		
		//System.out.println("CloverETLTransformation extends: "+subTypes.toString());
		
		for(int i = 0; i < subTypes.size(); i++){
			String temp = subTypes.toArray()[i].toString().substring(subTypes.toArray()[i].toString().lastIndexOf(".")+1);
			transformationExtends.put(i, temp);
			System.out.println(temp);
		}
		
		return transformationExtends;	
	}
	
	public Class<? extends CloverETLTransformation> getClassByName(String className){
		for(Class<? extends CloverETLTransformation> classes : subTypes){
			if (classes.toString().substring(classes.toString().lastIndexOf(".")+1).equals(className)){
				return classes;
			}
		}
		return null;
	}
}
