package dk.sdu.nirei12.zipper;

import java.io.File;
import java.io.IOException;

import dk.sdu.nirei12.zipper.inputStream.InputStreamer;

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
	
	public Decoder(File in, File out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		getHeader();
		System.out.println(totalChars);
		CharCountHeap cch = new CharCountHeap();
		for (int i = 0; i < chars.length; i++) {
			if(chars[i] > 0){
				cch.addChars(i, chars[i]);
			}
		}
		CharTreeNode ctn = cch.processOptimizedTree();
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
					chars[i] += (int) (Math.pow(0xff, 3 - j) * input);
				}
				totalChars += chars[i];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
