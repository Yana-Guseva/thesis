package org.eltech.ddm.inputdata.cursor;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.junit.Before;
import org.junit.Test;

public class MultiStreamCursorTest {
	MiningInputStream data;
	int numberVectors;
	int size = 2;
	ArrayList<MiningInputStream> result;

	@Before
	public void setUp() throws Exception {
		data = new MiningArffStream( "..\\data\\arff\\" + "iris.arff");
		numberVectors = data.getVectorsNumber();
		result = new ArrayList<MiningInputStream>();

	}

	@Test
	public void testBlockCursor() {
		int start = 0;
		int block = numberVectors/size;
		for (int i = 0; i < size; i++) {
			MiningInputStream mis = null;
			try {
				mis = new HorPartStreamCursor(data, start, start + block-1, 1);
			} catch (MiningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(mis);
			start = start + block;
		}

		int startBlock = 0;
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < block; i++) {
				try {
					MiningVector mv0 = data.getVector(startBlock + i);
					System.out.println(" mv0 = " + mv0);

					MiningVector mv1 = result.get(j).getVector(i);
					System.out.println(" mv1 = " + mv1);

					assertArrayEquals(mv0.getValues(), mv1.getValues(), 0);

				} catch (MiningException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			startBlock = startBlock + block;
		}


	}

	@Test
	public void testCycleCursor() {
		int start = 0;
		int end = numberVectors - size;
		for (int i = 0; i < size; i++) {
			MiningInputStream mis = null;
			try {
				mis = new HorPartStreamCursor(data, start++, end++, size);
			} catch (MiningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(mis);
		}



		for (int i = 0; i < numberVectors; i++) {
			try {
				MiningVector mv0 = data.getVector(i);
				System.out.println(" mv0 = " + mv0);

				MiningVector mv1 = result.get(i%size).getVector(i/size);
				System.out.println(" mv1 = " + mv1);

				assertArrayEquals(mv0.getValues(), mv1.getValues(), 0);

			} catch (MiningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}



}
