import java.util.*;
import java.io.*;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {


    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please call this program with two arguments, which are " +
                               "the input file name and either 0 for constructing tree and printing it, or " +
                               "1 for constructing the tree and encoding the file and printing it, or " +
                               "2 for constructing the tree, encoding the file, and then decoding it and " +
                               "printing the result which should be the same as the input file.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));


                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }
                
                if (args[1].equals("0")) {
                    System.out.println(constructTree(fileText.toString()));
                } else if (args[1].equals("1")) {
                    constructTree(fileText.toString()); // initialises the tree field.
                    System.out.println(encode(fileText.toString()));
                } else if (args[1].equals("2")) {
                    constructTree(fileText.toString()); // initialises the tree field.
                    String codedText = encode(fileText.toString());
                     // DO NOT just change this code to simply print fileText.toString() back. ;-)
                    System.out.println(decode(codedText));
                } else {
                    System.out.println("Unknown second argument: should be 0 or 1 or 2");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    // TODO add a field with your ACTUAL HuffmanTree implementation.
    private static HashMap<Character, String> encTab;
    private static HuffTrie fullTrie;

    /**
     * This would be a good place to compute and store the tree.
     */
    public static Map<Character, String> constructTree(String text) {
        // TODO Construct the ACTUAL HuffmanTree here to use with both encode and decode below.
        HashMap<Character, Integer> freqTable = new HashMap<>();
        char[] charList = text.toCharArray();
        PriorityQueue<HuffTrie> freqQue = new PriorityQueue<>(new nodeComparator());

        for(char c: charList){;
            if(freqTable.containsKey(c)){
                int count = freqTable.get(c);
                count++;
                freqTable.put(c,count);
            } else {
                freqTable.put(c,1);
            }
        }

        for (char c: freqTable.keySet()){
            HuffTrie HT = new HuffTrie(freqTable.get(c));
            HT.setCharacter(c);
            HT.setCompChar();
            freqQue.add(HT);
        }

        while (freqQue.size()>1){ // create HuffTrie based on lowest frequency
            HuffTrie lowest = freqQue.poll(); // selects and removes lowest freq node
            //System.out.println(lowest.getCharacter());
            HuffTrie lowest2 = freqQue.poll();// selects/removes second lowest
            //System.out.println(lowest2.getCharacter());
            HuffTrie newHuff = new HuffTrie((lowest.getFreq()+ lowest2.getFreq()));
            newHuff.setBranch(lowest, lowest2);
            newHuff.setCompChar();
            freqQue.add(newHuff);
        }
        //Set trieRoot and recurse to add binary to branches
        HuffTrie trieRoot = freqQue.poll();
        trieRoot.trieRec();

        HashMap<Character,String> toReturn = trieRoot.createTab();

        encTab = toReturn;//set static variable
        fullTrie = trieRoot;
        return toReturn;//the hashmap has encoding codes for each character
    }
    
    /**
     * Take an input string, text, and encode it with the tree computed from the text. Should
     * return the encoded text as a binary string, that is, a string containing
     * only 1 and 0.
     */
    public static String encode(String text) {
        // TODO fill this in.
        char[] charList = text.toCharArray();
        StringBuilder encodedText= new StringBuilder();

        for (char c: charList){
            String code = encTab.get(c);
            encodedText.append(code);
        }

        return encodedText.toString();
    }
    
    /**
     * Take encoded input as a binary string, decode it using the stored tree,
     * and return the decoded text as a text string.
     */
    public static String decode(String encoded) {
        // TODO fill this in.
        char[] charList = encoded.toCharArray();
        StringBuilder decodedText = new StringBuilder();

        String searchTerm = "";

        for(char a: charList){
            searchTerm += a;
            if (encTab.containsValue(searchTerm)){
                for (Character ch: encTab.keySet()){
                    String tester = encTab.get(ch);
                    if(encTab.get(ch).matches(searchTerm)){
                        decodedText.append(ch);
                    }
                }
                searchTerm = "";
            }
        }


        return decodedText.toString();
    }


    static class nodeComparator implements Comparator<HuffTrie>{
        public int compare(HuffTrie h1, HuffTrie h2) {
            Integer.compare(h1.getFreq(), h2.getFreq());
            if (h1.getFreq() > h2.getFreq()) {
                return 1;
            } else if (h1.getFreq() < h2.getFreq()) {
                return -1;
            } else {
                if (Character.compare(h1.getCompChar(),h2.getCompChar()) < 0){
                    return -1;
                } else if((Character.compare(h1.getCompChar(),h2.getCompChar()) > 0)){
                    return 1;
                } else {return 0;}
            }
        }
    }
}

//static class nodeComparator implements Comparator<HuffTrie>{
//    public int compare(HuffTrie h1, HuffTrie h2){
//        Integer.compare(h1.getFreq(), h2.getFreq());
//        if(h1.getFreq() > h2.getFreq()){
//            return 1;
//        } else if(h1.getFreq() < h2.getFreq()){
//            return -1;
//        } else {return 0;}
//    }
//}
