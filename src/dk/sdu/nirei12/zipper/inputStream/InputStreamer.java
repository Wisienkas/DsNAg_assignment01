package dk.sdu.nirei12.zipper.inputStream;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import dk.sdu.nirei12.zipper.datastructure.ByteToBits;

public class InputStreamer {

	private File file;
	private BufferedReader br;

	private ByteToBits btb;

	public InputStreamer(File file) {
		this.file = file;
	}

	public void setupStream() throws FileNotFoundException {
		FileReader fr = new FileReader(file);
		this.br = new BufferedReader(fr);
	}

	/**
	 * All reads over 255 (2⁸ - 1) will return -2
	 * 
	 * @return -1 if end of <br>
	 *         -2 if unsupported char <br>
	 *         0-255 supported chars
	 * @throws IOException
	 */
	public int readByte() throws IOException {
		int result = this.br.read();
		if( result >= 256 ) result = 0xb6;
		return result;
	}

	/**
	 * @return 0 for false <br>
	 *         1 for true <br>
	 *         -1 for End of File
	 * @throws IOException
	 */
	public int readBit() throws IOException{
		int result = 0;
		try{
			if(btb == null || !btb.hasNextBit()){
				int by = readByte();
				if(by == -1){
					return -1;
				}else{
					btb = new ByteToBits(by);
				}
			}
			result += btb.nextBit() ? 1 : 0;
		}catch (EOFException eof){
			br.close();
			result = -1;
		}catch(Exception e){
			try{
				btb = new ByteToBits(readByte());
			}catch (EOFException eof){
				result = -1;
			}
		}
		return result;
	}
}
