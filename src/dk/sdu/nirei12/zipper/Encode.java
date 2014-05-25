package dk.sdu.nirei12.zipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Of course this has to be run in a thread of its own!
 * @author nire
 *
 */
public class Encode implements Runnable{
	
	/**
	 * Fullfill the args or get lost!
	 * @param args
	 */
	public static void main(String[] args) {
		File in = new File(args[0]);
		File out = new File(args[1]);
		Encode encoder = new Encode(in, out);
		Thread job = new Thread(encoder);
		job.start();
		try {
			job.join();
		} catch (InterruptedException e) {
			System.out.println("If you read this... something failed! :/ ");
			e.printStackTrace();
		}
	}
	
	public static long size = 0;
	
	private File in;
	private File out;
	private int[] chars = new int[256];
	private long totalChars;
	
	public Encode(File in, File out){
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		InputStreamer is = new InputStreamer(in);
		// creates and insert into the heap
		CharCountHeap cch = new CharCountHeap();
		countingUp(is, cch);
		System.out.println(totalChars);
		// creates the optimized tree
		CharTreeNode ctn = cch.processOptimizedTree();
		Map<Object, Object> map = new HashMap<Object, Object>();
		cch.makeMap(ctn, map);
		OutputStreamer os = new OutputStreamer(out);
		is = new InputStreamer(in);
		convert(os, is, map);
		long outSize = Util.readSize(out);
		Util.writeStatistics(outSize, totalChars, size);
	}

	/** Write from input file to output file */
	private void convert(OutputStreamer os, InputStreamer is, Map map) {
		System.out.println("Writing to new file!");
		try {
			os.startStream();
			is.setupStream();
			makeHeader(os);
			int input = is.readByte();
			if(map == null){
				System.out.println("FML!");
			}
			while(input != -1){
				if(input == -2 ){
					input = 0;
				}
				chars[input]--;
				if(chars[input] < 0){
					System.out.println("more chars of this kind found that first found??!?!");
				}
//				String path = "";
				for (boolean b : (boolean[])map.get(input)) {
					os.writeBit(b);
//					path += b ? "1" : "0";
				}
//				System.out.println(path);
//				path = "";
				input = is.readByte();
			}
			os.endFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("New file written!");
	}

	/** Will create the header! */
	private void makeHeader(OutputStreamer os) throws IOException {
		long headerCount = 0;
		for (int c : chars) {
//			headerCount += c;
//			for (int i = 0; i < 4; i++) {
//				int divisor = (int)Math.pow(256, 3 - i);
//				int result = c / divisor;
//				os.writeByte(result);
//				c = c - result * divisor;
//			}
//			System.out.println("c should be 0... c: " + c);
			int sampler = 1;
			sampler <<= 31;
			for (int i = 0; i < 4; i++) {
				int bitbyte = 0;
				for (int j = 0; j < 8; j++) {
					bitbyte <<= 1;
					bitbyte += (c & sampler) == sampler ? 1 : 0;
					sampler >>>= 1;
				}
				os.writeByte(bitbyte);
			}
		}
		System.out.println(headerCount);
	}

	/** Adds the char count to an int array from the file to be encoded */
	private void countingUp(InputStreamer is, CharCountHeap cch) {
		int input;
		try {
			is.setupStream();
			input = is.readByte();
			while(input != -1){
				// if -2 its not within 0-255 :O is that bad ? 
				if(input == -2){
					input = 0;
				}
				chars[input]++;
				totalChars++;
				// the line below count up the chars in the heap, 
				// if it wasn't in the heap it creates it
				cch.addChar(input);
				input = is.readByte();
			}
		} catch (IOException e) {
			System.out.println("WOOP WOOP! IO exception seems bad");
			e.printStackTrace();
		} finally {
		}
	}
	
}
