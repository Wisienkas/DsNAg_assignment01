package dk.sdu.nirei12;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import dk.sdu.nirei12.zipper.BitsToByte;
import dk.sdu.nirei12.zipper.ByteToBits;
import dk.sdu.nirei12.zipper.InputStreamer;
import dk.sdu.nirei12.zipper.OutputStreamer;

public class StreamTester {

	@Test
	public void testStream() {
		String s = "Har du lavet noget eller? ellers så se lige mit igennem, " +
				"Jeg har lidt problemeasdasdrgff/\"\"11223344556677889900kjh<=)(/&%¤" +
				"#\"!\"#¤%&/()1234567890098765432{{[[]]}}~~r med at " +
				"den ikke decoder det helt rigtigt, " +
				"tror det er nogle bestemte tegn den ikke kan lide, ser det ud til " +
				"i diffen";
		File file = new File("TestFile.txt");
		OutputStreamer os = new OutputStreamer(file);
		try {
			os.startStream();
			System.out.println("Writing to file");
			for (int i = 0; i < s.length(); i++) {
				ByteToBits btb = new ByteToBits((int)s.charAt(i));
				while(btb.hasNextBit()){
					os.writeBit(btb.nextBit());
				}
			}
			os.endFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamer is = new InputStreamer(file);
		String in = "";
		try {
			is.setupStream();
			BitsToByte bitter = new BitsToByte();
			System.out.println("Reading from file");
			while(true){
				int i = is.readBit();
				if(i == -1){
					System.out.println("EOF");
					break;
				}
				bitter.addBit(i == 1 ? true : false);
				if(bitter.filled()){
					char c = (char)bitter.getResult();
					in += c;
					bitter = new BitsToByte();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s);
		System.out.println(in);
		assertEquals("Should be totally alike", s, in);
	}

}
