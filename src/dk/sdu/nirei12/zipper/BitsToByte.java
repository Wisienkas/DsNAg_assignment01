package dk.sdu.nirei12.zipper;

public class BitsToByte {

	private int result;
	private int index = 0;
	
	public BitsToByte(){
		
	}
	
	public int getResult(){
		return result;
	}
	
	public boolean filled(){
		return index >= 8;
	}
	
	public int addBit(boolean b){
		if(index >= 8){
			return -1;
		}
		result <<= 1;
		result += b ? 1 : 0;
		index++;
		return result;
	}
}
