package dk.sdu.nirei12.zipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Decoder implements Runnable {

	public static void main(String[] args) {
		File in = new File(args[0]);
		File out = new File(args[1]);
		Thread job = new Thread(new Decoder(in, out));
		job.start();
		try {
			job.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private File in, out;
	private int[] chars = new int[256];
	private long totalChars = 0;
	private int loops;

	public Decoder(File in, File out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		System.out.println("Starting to decode file: " + in.getName());
		getHeader();
		System.out.println(totalChars);
		CharCountHeap cch = new CharCountHeap();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] > 0) {
				cch.addChars(i, chars[i]);
			}
		}
		CharTreeNode ctn = cch.processOptimizedTree();
		convert(ctn);
		// REST IS ONLY STATISTICS!
		long outputSize = Util.readSize(out);
		long inputSize = Util.readSize(in);
		System.out.println("Encoded File was: \t" + inputSize + " MB!");
		System.out.println("Decoded File was: \t" + outputSize + " MB!");
		System.out.println("Decoding finished to file: " + out.getName());
	}


	private void convert(CharTreeNode ctn) {
		OutputStreamer os = new OutputStreamer(out);
		InputStreamer is = new InputStreamer(in);
		try {
			is.setupStream();
			// Skipping header
			for (int i = 0; i < 1024; i++) {
				is.readByte();
			}
			os.startStream();
			int input = is.readBit();
			loops = 0;
			CharTreeNode node = ctn;
//			String path = "";
			node.key--;
			while (input != -1 && loops < totalChars) {
				node = (input == 1) ? node.right : node.left;
//				path += (input == 1) ? "1" : "0";
				node.key--;
				if(node.data != null){
//					System.out.println(path);
//					path = "";
					if(node.key < 0){
//						System.out.println("key used more than it should be");
//						System.out.println("KEY: " + (int)node.data);
					}
					os.writeByte((int) node.data);
					node = ctn;
					node.key--;
					loops++;
				}
				input = is.readBit();
			}
			if(loops != totalChars){
				System.out.println("Missing some chars in decoded file!");
			}else{
				System.out.println("stopped normally last input was : " + input);
			}
			os.endFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getHeader() {
		try {
			InputStreamer is = new InputStreamer(in);
			is.setupStream();
			for (int i = 0; i < 256; i++) {
				for (int j = 0; j < 4; j++) {
					int input;
					input = is.readByte();
					if (input > 0xff || input < 0x00) {
						System.err.println("BUGGY!");
						System.exit(1);
					}
					chars[i] += (int) (Math.pow(256, 3 - j) * input);
				}
				totalChars += chars[i];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
