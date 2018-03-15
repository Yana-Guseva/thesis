package org.eltech.ddm.clustering.cdbase;

import java.util.ArrayList;

import org.eltech.ddm.clustering.Cluster;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

public class CDBasedCluster extends Cluster {
	
	public CDBasedCluster(String id) {
		super(id);
		
	}
	
//	public void setNewCenter(double[] newCenter) {
//		this.newCenter = newCenter;
//	}
//
//	public double[] getNewCenter() {
//		return this.newCenter;
//	}
//
//	public void resetNewCenter() {
//		for (int i = 0; i < newCenter.length; i++)
//			newCenter[i] = 0;
//	}
//
//	public Object clone() {
//		CDBasedCluster o = null;
//		o = (CDBasedCluster)super.clone();
//
//		if(newCenter != null)
//			o.newCenter = newCenter.clone();
//
//		return o;
//	}

}
