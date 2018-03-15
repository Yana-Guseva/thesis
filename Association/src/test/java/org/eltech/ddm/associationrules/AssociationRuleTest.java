package org.eltech.ddm.associationrules;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssociationRuleTest {

	AssociationRule assRule;

	@Before
	public void setUp() throws Exception {

		Item item1 = new Item("1");
		Item item3 = new Item("3");
		Item item5 = new Item("5");
		ItemSet itemSetA = new ItemSet(item1, item3, item5);

		Item item2 = new Item("2");
		ItemSet itemSetC = new ItemSet(item2);

		assRule = new AssociationRule(itemSetA, itemSetC, 0.75, 0.6);
	}

	@Test
	public void testAssRule() {
		Assert.assertEquals(3, assRule.getAntecedent().getItemIDList().size());
		Assert.assertEquals("1", assRule.getAntecedent().getItemIDList().get(0));
		Assert.assertEquals("3", assRule.getAntecedent().getItemIDList().get(1));
		Assert.assertEquals("5", assRule.getAntecedent().getItemIDList().get(2));

		Assert.assertEquals(1, assRule.getConsequent().getItemIDList().size());
		Assert.assertEquals("2", assRule.getConsequent().getItemIDList().get(0));

	}

	@Test
	public void testEqualsObject() {
		Item item1 = new Item("1");
		Item item3 = new Item("3");
		Item item5 = new Item("5");
		ItemSet itemSetA = new ItemSet(item1, item3, item5);

		Item item2 = new Item("2");
		ItemSet itemSetC = new ItemSet(item2);

		AssociationRule assRuleT = new AssociationRule(itemSetA, itemSetC, 1, 0.5);
		Assert.assertEquals(true, assRuleT.equals(assRule));

		Item item4 = new Item("4");
		ItemSet itemSetC2 = new ItemSet(item4);
		AssociationRule assRuleF = new AssociationRule(itemSetA, itemSetC2, 1, 0.5);
		Assert.assertEquals(false, assRuleF.equals(assRule));

		ItemSet itemSetA2 = new ItemSet(item1, item3);
		AssociationRule assRuleF2 = new AssociationRule(itemSetA2, itemSetC2, 1, 0.5);
		Assert.assertEquals(false, assRuleF2.equals(assRule));
	}

	@Test
	public void testClone() {

		AssociationRule assRuleC = (AssociationRule) assRule.clone();

		Assert.assertEquals(false, assRuleC==assRule);

		Assert.assertEquals(true, assRuleC.equals(assRule));

		Assert.assertEquals(3, assRule.getAntecedent().getItemIDList().size());
		Assert.assertEquals("1", assRule.getAntecedent().getItemIDList().get(0));
		Assert.assertEquals("3", assRule.getAntecedent().getItemIDList().get(1));
		Assert.assertEquals("5", assRule.getAntecedent().getItemIDList().get(2));

		Assert.assertEquals(1, assRule.getConsequent().getItemIDList().size());
		Assert.assertEquals("2", assRule.getConsequent().getItemIDList().get(0));
	}

}
