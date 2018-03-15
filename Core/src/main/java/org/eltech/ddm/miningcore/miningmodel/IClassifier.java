package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

public interface IClassifier {

	double apply(MiningVector miningVector) throws MiningException;
}