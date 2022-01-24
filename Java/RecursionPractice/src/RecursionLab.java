import java.io.*;

public class RecursionLab{
    	
		static String word = "";
		
    public static void reversePrint (String inString){
       
		if (inString.length() > 0)		// if string is not empty
		{
			word += inString.charAt (inString.length()-1);
			inString = inString.substring(0, inString.length()-1);
			reversePrint(inString);

		}
		else {
			System.out.println ("The reverse word is: " + word);
		}
    }
    
    public static String reverseString(String inString) {
    	if (inString.length() == 0) {
    		return inString;
    		
    	}
    	else {
    		return (inString.substring(1, inString.length())+  inString.charAt(0));
    	}
    }

	    
    public static void main(String[] args){
        String inString = "abcde";

		// test reversePrint
		reversePrint(inString);		
		System.out.println(reverseString(inString));
    }
}
