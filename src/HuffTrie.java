import java.util.HashMap;

public class HuffTrie {
    private int freq;
    private char character;
    private char bin;
    private String encoder="";
    private HuffTrie branchL;
    private HuffTrie branchR;


    public HuffTrie(int f) {
        this.freq = f;

    }

    public void setCharacter(char c){
        this.character = c;
    }
    public char getCharacter(){
        return this.character;
    }
    public int getFreq(){
        return this.freq;
    }

    public void trieRec(){

        if (branchR != null && branchL != null){
            this.branchL.setBin('0');
            this.branchR.setBin('1');
            if(this.bin != 0){
                this.encoder += this.bin;
                this.branchL.addEnc(this.encoder);
                this.branchR.addEnc(this.encoder);
            }
            this.branchL.trieRec();
            this.branchR.trieRec();
        }else{
            this.encoder += this.bin;
        }
    }

    public HashMap<Character,String> createTab(){
        HashMap<Character,String> temp = new HashMap<>();
        if(this.character!= 0){
            temp.put(this.character,this.encoder);
        }
        if (branchR != null && branchL != null) {
            HashMap<Character, String> leftTemp = this.branchL.createTab();
            HashMap<Character, String> rightTemp = this.branchR.createTab();
            temp.putAll(leftTemp);
            temp.putAll(rightTemp);
        }
        return temp;
    }


    public void setBin(char b) {
        this.bin = b;
    }

    public void addEnc(String s){
        this.encoder += s;
    }

    public void setBranch(HuffTrie left, HuffTrie right){
        this.branchL = left;
        this.branchR = right;

    }

}
