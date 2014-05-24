package dk.sdu.nirei12.zipper.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import dk.sdu.nirei12.zipper.datastructure.BitsToByte;

public class BitsToByteTester {

	@Test
	public void testGeneralPurpose() {
		// 1 1 1 0 1 1 1 1 = 237
		int should = 239;
		boolean[] bools = new boolean[]{true, true, true, false, true, true, true, true};
		BitsToByte btb = new BitsToByte();
		for (boolean b : bools) {
			btb.addBit(b);
		}
		assertEquals(should, btb.getResult());
	}
	
	@Test
	public void testLimit() {
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			BitsToByte btb = new BitsToByte();
			for (int j = 0; j < 8; j++) {
				btb.addBit(rnd.nextBoolean());
			}
			assertEquals(-1, btb.addBit(rnd.nextBoolean()));
		}
	}
}
