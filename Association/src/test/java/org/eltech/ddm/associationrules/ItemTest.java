package org.eltech.ddm.associationrules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {

	Item item;


	@Before
	public void setUp() throws Exception {
		item = new Item("1");
		List<String> tidList = new ArrayList<String>();
		tidList.add("1");
		tidList.add("3");
		tidList.add("5");
		item.setTidList(tidList);

	}

	@Test
	public void testEqualsTrue() {
		Item itmT = new Item("1");
		Assert.assertEquals(true, itmT.equals(item));

		Item itmF = new Item("2");
		Assert.assertEquals(false, itmF.equals(item));
	}

	@Test
	public void testCompareTo() {
		Item itmLess = new Item("0");
		Assert.assertEquals(-1, itmLess.compareTo(item));

		Item itmEq = new Item("1");
		Assert.assertEquals(0, itmEq.compareTo(item));

		Item itmMore = new Item("2");
		Assert.assertEquals(1, itmMore.compareTo(item));
	}

	@Test
	public void testClone() {
		Item itm = (Item) item.clone();

		Assert.assertEquals(false, itm==item);

		Assert.assertEquals(true, itm.equals(item));
		Assert.assertEquals(true, itm.getTidList().equals(item.getTidList()));
	}

}
