import java.io.* ;

/** This small class with a main method shows you one way of creating
    an array of characters from a string and how to obtain their ascii
    codes */

public class DecodePhrase {
    
    public static void main (String[] args) {
        
        String inputString = "gj2roo3!1hkw3gt2" ;

        int ascii = 0 ;
        
        /** Converting all the string into an array of characters */
         
        char[] ch = inputString.toCharArray() ;
        
        /** Obtaining the ascii codes of the characters in the array */
        
        for (int i = 0 ; i < inputString.length() ; i++) {
            ascii = (int)(ch[i]) ;
            System.out.println(ascii + " ") ;
        }    
            
    }
}