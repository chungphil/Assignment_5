import java.util.*;
import java.io.*;

public class KMP {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please call this program with " +
                               "two arguments which is the input file name " +
                               "and the string to search.");
            // This is testing on "lenna"
//            try {
//                Scanner s = new Scanner(new File("war_and_peace.txt"));
//
//                // Read the entire file into one String.
//                StringBuilder fileText = new StringBuilder();
//                while (s.hasNextLine()) {
//                    fileText.append(s.nextLine() + "\n");
//                }
//
////                System.out.println(fileText.length());
//                System.out.println(fileText.toString().charAt(8));
////                int[] M = matchTable(test);
////                for (int i: M){
////                    System.out.println(i);
////                }
////                System.out.println(search(fileText.toString(), test));//args[1]
//            } catch (FileNotFoundException e) {
//                System.out.println("Unable to find file called " + args[0]);
//            }

        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }

                System.out.println(search(fileText.toString(), args[1]));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    /**
     * Perform KMP substring search on the given text with the given pattern.
     * 
     * This should return the starting index of the first substring match if it
     * exists, or -1 if it doesn't.
     */
    public static int search(String text, String pattern) {
        // TODO
        int[] M = matchTable(pattern);

        int k = 0;
        int i = 0;
        int n = text.length();

        while ((k+i)< n){
            if (pattern.charAt(i) == text.charAt(k+i)){
                i++;
                if (i == M.length){
                    return k;
                }
            }
            else if (M[i]== -1){
                k = k + i + 1;
                i = 0;
            }
            else{
                k = k + i - M[i];
                i = M[i];
            }

        }

        return -1;
    }

    public static int[] matchTable(String pattern){
        int m = pattern.length();
        int[] mTab = new int[m];

        mTab[0] = -1;
        mTab[1] = 0;
        int j = 0;
        int pos = 2;

        while (pos < m){
            if (pattern.charAt(pos-1) == pattern.charAt(j)){
                mTab[pos] = j+1;
                pos++;
                j++;
            }
            else if (j >0 ){
                j = mTab[j];
            }
            else{
                mTab[pos] = 0;
                pos++;
            }
        }
    return mTab;
    }

}
