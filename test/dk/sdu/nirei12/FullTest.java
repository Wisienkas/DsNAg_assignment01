package dk.sdu.nirei12;

import java.io.File;

import org.junit.Test;

import dk.sdu.nirei12.zipper.Decoder;
import dk.sdu.nirei12.zipper.Encode;

public class FullTest {

	File ori, en1, de1, en2, de2;
	
	@Test
	public void testAll() {
		ori = new File("original");
		en1 = new File("en1");
		de1 = new File("de1");
		en2 = new File("en2");
		de2 = new File("de2");

		Encode en = new Encode(ori, en1);
		en.run();
		Decoder de = new Decoder(en1, de1);
		de.run();
		en = new Encode(de1, en2);
		en.run();
		de = new Decoder(en2, de2);
		de.run();
	}

}
