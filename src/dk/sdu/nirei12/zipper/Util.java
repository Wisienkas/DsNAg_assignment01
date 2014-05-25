package dk.sdu.nirei12.zipper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Util {
	public static long readSize(File out2) {
		System.out.println("Counting bytes in file");
		long bytes = 0;
		try {
			FileReader fr = new FileReader(out2);
			while(fr.read() != -1){
				bytes++;
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public static void writeStatistics(long outSize, long totalChars, long size) {
		System.out.println("Output size of file should be: \t" + ((size / 8) + 1024) + " bytes");
		System.out.println("Output size of file was: \t" + outSize + " bytes");
		System.out.println("Original size of file was: \t" + (totalChars) + " bytes");
		System.out.println("reduction in size to = \t\t" + (outSize * 8 * 100) / (totalChars * 8) + "%");
	}
}
