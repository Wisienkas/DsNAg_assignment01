package dk.sdu.nirei12.zipper;

public class ByteToBits {

	private int int8Buffer;
	private boolean[] result;
	private int index;
	private boolean EOF = false;
	
	public ByteToBits(int startByte){
		int8Buffer = startByte;
		if(startByte == -1){
			EOF = true;
		}else{
			getResult();
		}
	}
	
	public boolean hasNextBit(){
		return index >= 8 ? false : true && !EOF;
	}
	
	public static void main(String[] args) {
		int r  = 1;
		int h = 51;
		r <<= 8;
		for (int i = 0; i < 8; i++) {
//			System.out.println(r);
			r >>= 1;
			if((h & r) == r){
				System.out.print("1");
			}else{
				System.out.print("0");
			}
		}
	}
	
	/**
	 * Convert the byte to an array of bits ( boolean array )
	 */
	private void getResult() {
		int count = 1;
		count <<= 7;
		result = new boolean[8];
		for (int i = 0; i < 8; i++) {
			result[i] = (count & int8Buffer) == count? true : false;
			count >>>= 1;
		}
//		for (boolean b : result) {
//			System.out.print(b ? "1" : "0");
//		}
//		System.out.println();
		index = 0;
	}

	public boolean nextBit() throws Exception{
		if(EOF) return false;
		if(index >= result.length){
			throw new Exception("No more bits!");
		}
		return result[index++];
	}
	
}
