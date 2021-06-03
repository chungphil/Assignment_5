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
        // TODO (currently just returns the argument).

//        System.out.println(input.substring(32,33));
//        System.out.println(input.substring(13));
//        int match = input.substring(0, 15).indexOf(input.substring(32, 33));
//        boolean matchbool = input.substring(0,15).contains(input.substring(32,33));
//        System.out.println(match);
//        System.out.println(matchbool);

        int cursor = 0;
        int wSize = 100;

        while (cursor < input.length()){
            int length = 0;
            int prevMatch = 0;

            while (true){
                String searchW = input.substring((cursor < wSize)?0:cursor-wSize,(cursor<1)?0:cursor);
                String searchT = input.substring(cursor, cursor+length+1);
                boolean matchBool = searchW.contains(searchT);
                if (matchBool){
                    int wCursor = searchW.indexOf(searchT);
                    prevMatch = (cursor>wSize)? cursor - (wSize-wCursor): wCursor;
                    length++;
                } else {
//                    int offset = (prevMatch>0)?cursor - (wSize+prevMatch):prevMatch;
                    int offset = (prevMatch<1)?prevMatch:cursor-prevMatch;

                    System.out.println("[" + offset + "," + length + "," + input.substring(cursor+length,cursor+length+1) + "]");
                    cursor = cursor + length + 1;
                    break;
                }
            }
        }


        return input;
    }
}
