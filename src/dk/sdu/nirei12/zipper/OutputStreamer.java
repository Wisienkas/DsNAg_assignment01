package dk.sdu.nirei12.zipper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class OutputStreamer {
	
	private File file;
	private BufferedWriter bw;
	private BitsToByte btb;
	
	public OutputStreamer(File file){
		this.file = file;
	}
	
	public void startStream() throws IOException{
		PrintWriter pw = new PrintWriter(file);
		pw.write("");
		pw.close();
		this.bw = new BufferedWriter(new FileWriter(file));
	}
	
	public void writeByte(int out) throws IOException{
		bw.write(out);
	}
	
	public void writeBit(boolean b) throws IOException{
		if(this.btb == null){
			this.btb = new BitsToByte();
		}
		if(this.btb.addBit(b) == -1){
			bw.write(btb.getResult());
			this.btb = new BitsToByte();
			this.btb.addBit(b);
		}
	}
	
	public void endFile() throws IOException{
		if(btb != null){
			while (!btb.filled()) {
				btb.addBit(true);
			}
			writeByte(btb.getResult());
		}
		bw.flush();
		bw.close();
	}
}
