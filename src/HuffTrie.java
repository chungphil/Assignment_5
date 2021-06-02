public class HuffTrie {
    private int freq;
    private char charac;
    private char bin;
    private HuffTrie branchL;
    private HuffTrie branchR;


    public HuffTrie(int f, char charac) {
    }

    public void setBin(char b) {
        this.bin = b;
    }

    public void setBranch(HuffTrie branch, int lr){
        if(lr == 0){
            this.branchL = branch;
        } else{
            this.branchR = branch;
        }
    }

}
