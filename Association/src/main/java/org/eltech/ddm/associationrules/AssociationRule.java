package org.eltech.ddm.associationrules;

public class AssociationRule implements Cloneable{

	public ItemSet antecedent;
	public ItemSet consequent;
//	private int length;
	private double support;
	private double confidence;

	public AssociationRule() {
	}

	public AssociationRule(ItemSet antecedent, ItemSet consequent, double support,
			double confidence) {
		this.antecedent = antecedent;
		this.consequent = consequent;
//		length = antecedent.getItems().size() + consequent.getItems().size();
		this.support = support;
		this.confidence = confidence;
	}

	public int getAbsoluteSupport() {
		return (int) support;// TODO
	}

	public ItemSet getAntecedent() {
		return antecedent;
	}

	public double getConfidence() {
		return confidence;
	}

	public ItemSet getConsequent() {
		return consequent;
	}

//	public int getLength() {
//		return length;
//	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Override
	public boolean equals(Object o) {
		if(antecedent.equals(((AssociationRule)o).getAntecedent())
				&& consequent.equals(((AssociationRule)o).getConsequent())) {
			return true;
		}
		return false;
	}

	public Object clone() {
		AssociationRule o = new AssociationRule();

		o.antecedent = (ItemSet) antecedent.clone();
		o.consequent = (ItemSet)consequent.clone();
//		o.length = length;
		o.support = support;
		o.confidence = confidence;

		return o;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder()
			.append(antecedent.getItemIDList())
			.append(" => ")
			.append(consequent.getItemIDList())
			//.append(", support = ")
			//.append(support)
			.append(", confidence = ")
			.append(confidence);
		return  b.toString();
	}
}
