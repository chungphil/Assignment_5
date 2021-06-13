
import java.util.*;
import java.io.*;


public class LempelZivCompress {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please call this program with one argument which is the input file name.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }

                System.out.println(compress(fileText.toString()));
                String out = compress(fileText.toString());

            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }
    
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public static String compress(String input) {
        StringBuilder output = new StringBuilder();

        int cursor = 0;
        int wSize = 100;

        while (cursor < input.length()){
            int length = 0;
            int prevMatch = 0;
            String searchS = "";

            if (cursor == input.length()-1){
                break;
            }
            while (true){
                String searchW = input.substring((cursor < wSize)?0:cursor-wSize,(cursor<1)?0:cursor);//search window
                String searchT = input.substring(cursor, cursor+length+1);//search term
//                searchS = searchS + input.charAt(cursor+length);
                boolean matchBool = searchW.contains(searchT);
                if (matchBool){
                    int wCursor = searchW.indexOf(searchT);
                    prevMatch = (cursor>wSize)? cursor - ((cursor -wSize)+wCursor): cursor - wCursor;
                    length++;
                } else {
                    int offset = prevMatch;
//                    int offset = (prevMatch<1&&length ==0)?prevMatch:cursor-prevMatch;

                    String toAppend = "[" + offset + "|" + length + "|" + input.substring(cursor+length,cursor+length+1) + "]";
                    output.append(toAppend);
                    cursor = cursor + length + 1;
                    break;
                }
            }
        }
        return output.toString();
    }
}
