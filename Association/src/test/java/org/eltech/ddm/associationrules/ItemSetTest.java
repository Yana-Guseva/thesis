package org.eltech.ddm.associationrules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemSetTest {

	ItemSet itemSet;

	@Before
	public void setUp() throws Exception {
		Item item1 = new Item("1");
		List<String> tidList = new ArrayList<String>();
		tidList.add("1");
		tidList.add("3");
		tidList.add("5");
		item1.setTidList(tidList);

		Item item3 = new Item("3");
		tidList = new ArrayList<String>();
		tidList.add("1");
		tidList.add("3");
		tidList.add("4");
		item3.setTidList(tidList);

		Item item5 = new Item("5");
		tidList = new ArrayList<String>();
		tidList.add("0");
		tidList.add("1");
		item5.setTidList(tidList);

		itemSet = new ItemSet(item1, item3, item5);
	}

	@Test
	public void testItemSet() {
		Assert.assertEquals("1", itemSet.getItemIDList().get(0));
		Assert.assertEquals("3", itemSet.getItemIDList().get(1));
		Assert.assertEquals("5", itemSet.getItemIDList().get(2));

		Assert.assertEquals(1, itemSet.getTIDList().size());
		Assert.assertEquals("1", itemSet.getTIDList().toArray()[0]);
	}

	@Test
	public void testEqualsObject() {

		Item item1 = new Item("1");
		Item item3 = new Item("3");
		Item item5 = new Item("5");

		ItemSet itemSetT = new ItemSet(item1, item3, item5);
		Assert.assertEquals(true, itemSetT.equals(itemSet));

		ItemSet itemSetT2 = new ItemSet(item5, item3, item1);
		Assert.assertEquals(true, itemSetT2.equals(itemSet));

		ItemSet itemSetF = new ItemSet(item1, item3);
		Assert.assertEquals(false, itemSetF.equals(itemSet));

		Item item4 = new Item("4");
		ItemSet itemSetF2 = new ItemSet(item1, item3, item4);
		Assert.assertEquals(false, itemSetF2.equals(itemSet));
	}

	@Test
	public void testClone() {
		ItemSet itemSetC = (ItemSet) itemSet.clone();

		Assert.assertEquals(false, itemSetC==itemSet);

		Assert.assertEquals(true, itemSetC.equals(itemSet));

		Assert.assertEquals("1", itemSetC.getItemIDList().get(0));
		Assert.assertEquals("3", itemSetC.getItemIDList().get(1));
		Assert.assertEquals("5", itemSetC.getItemIDList().get(2));

		Assert.assertEquals(1, itemSetC.getTIDList().size());
		Assert.assertEquals("1", itemSetC.getTIDList().toArray()[0]);
	}

}
