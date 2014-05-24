package dk.sdu.nirei12.zipper.datastructure;

public class StringToBits {
	
	public static boolean[] toBit(String s){
//		System.out.println("Converting String to Bit");
		boolean result[] = new boolean[s.length()];
		int i = 0;
		for (char c : s.toCharArray()) {
			result[i++] = c == '1' ? true : false;
//			System.out.print(c);
		}
//		System.out.println();
		for (boolean b : result) {
//			System.out.print(b ? "1" : "0");
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("test for String To Bits");
		String s = "1000111";
		toBit(s);
	}
	
}
