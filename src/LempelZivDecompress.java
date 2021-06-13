import java.util.*;
import java.io.*;

public class LempelZivDecompress {

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
                System.out.println(decompress(fileText.toString()));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }
    
    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public static String decompress(String compressed) {
        // TODO (currently just returns the argument).
        char[] charList = compressed.toCharArray();
        StringBuilder decompStr = new StringBuilder();

        String searchTerm = "";

        for(char a: charList){
            if(a == '\n'){
                continue;
            }
            searchTerm += a;
            if (searchTerm.matches("\\[[0-9]+\\|[0-9]+\\|.*\\]")){
                //String[] v = searchTerm.split("\\[[0-9]+\\|[0-9]+\\|.*\\]");
                String val = searchTerm.replace("[","").replace("]","");
                String[] values = val.split("\\|");

                int offset = Integer.parseInt(values[0]);
                int length = Integer.parseInt(values[1]);
                String charac = "\n";

                if(values.length>2){
                    charac = values[2];
                }
                if(offset ==0 && length == 0){
                    decompStr.append(charac);
                } else {
                    for(int i = 0; i <length; i++){
                        int strlength = decompStr.length();
                        decompStr.append(decompStr.charAt(strlength - offset));
                    }
                    decompStr.append(charac);
                }

                searchTerm = "";
            }
        }

        return decompStr.toString();
    }
}
