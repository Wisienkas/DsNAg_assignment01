package dk.sdu.nirei12.zipper.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dk.sdu.nirei12.zipper.ByteToBits;

public class ByteToBitsTester {

	@Test
	public void testRightBits() {
		// 239 111  47  15  15   7   3   1
		// 128  64  32  16   8   4   2   1
		// 111  47  15  15   7   3   1   1
		//  1    1   1   0   1   1   1   1
		// 239 == 1110111
		int in = 239;
		int result = 0x00;
		ByteToBits btb = new ByteToBits(in);
		String out;
		try {
			for (int i = 0; i < 8; i++) {
				out = btb.nextBit() ? "1" : "0";
				result <<= 1;
				result += out == "1" ? 0x01 : 0x00;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(in, result);
	}
	
	@Test
	public void testException(){
		int in = 239;
		int result = 0x00;
		ByteToBits btb = new ByteToBits(in);
		String out;
		// Should only be able to run 8 times not 9
		try {
			for (int i = 0; i < 9; i++) {
				out = btb.nextBit() ? "1" : "0";
				result <<= 1;
				result += out == "1" ? 0x01 : 0x00;
			}
			fail("Never got exception");
		} catch (Exception e) {
		}
	}

}
