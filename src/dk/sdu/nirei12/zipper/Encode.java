package dk.sdu.nirei12.zipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import dk.sdu.nirei12.zipper.inputStream.InputStreamer;
import dk.sdu.nirei12.zipper.outputStream.OutputStreamer;

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
	private Map<Object, Object> map;
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
		// creates the optimized tree
		CharTreeNode ctn = cch.processOptimizedTree();
		makeMap(ctn);
		OutputStreamer os = new OutputStreamer(out);
		is = new InputStreamer(in);
		convert(os, is);
		long outSize = readSize(out);
		writeStatistics(outSize);
	}

	private void writeStatistics(long outSize) {
		System.out.println("Output size of file should be: \t" + ((size / 8) + 1024) + " bytes");
		System.out.println("Output size of file was: \t" + outSize + " bytes");
		System.out.println("Original size of file was: \t" + (totalChars) + " bytes");
		System.out.println("reduction in size to = \t\t" + (outSize * 8 * 100) / (totalChars * 8) + "%");
	}

	private long readSize(File out2) {
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

	private void makeMap(CharTreeNode ctn) {
		if(ctn.left != null && ctn.right != null){
			this.map = new HashMap<Object, Object>();
			String s = "";
			if(!s.isEmpty()){
				System.err.println("String should be empty");
				System.exit(1);
			}
			CharTreeNode.traversal(this.map, ctn, s);
		}
	}

	/** Write from input file to output file */
	private void convert(OutputStreamer os, InputStreamer is) {
		System.out.println("Writing to new file!");
		try {
			os.startStream();
			is.setupStream();
			makeHeader(os);
			int input = is.readByte();
			if(this.map == null){
				System.out.println("FML!");
			}
			while(input != -1){
				if(input == -2 ){
					input = 0;
				}
				for (boolean b : (boolean[])this.map.get(input)) {
					os.writeBit(b);
				}
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
		for (int c : chars) {
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
