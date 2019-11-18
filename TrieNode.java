import java.util.HashMap;

public class TrieNode {

    TrieNode next[] = new TrieNode[28];
    char value;
    String stringToHere = "";



    /*O(k), k is the length of key & space is O(26 * k *N) where n is number of keys*/
    public boolean add(String restOfWord) {

        if (restOfWord == null) {
            return false;
        }
        char ch = restOfWord.charAt(0);


        TrieNode next = this.getNext(ch);
        if (next == null) {
            next = new TrieNode();
            next.setValue(ch);
            next.setWordToHere(this.stringToHere + ch);
            this.setNext(ch, next);
        }

        // If adding the last/only character
        if (restOfWord.length() == 1) {
            if (next.isTerminatorSet()) {
                return false;
            }
            next.setTerminator();
            return true;
        } else {
            return next.add(restOfWord.substring(1));
        }


    }

    // return suggestion or null
    public String spellcheck(String restOfWord, int numOfErrorsAllowed) {
        char ch = restOfWord.charAt(0);

        if (!Character.isLetter(ch) && ch != '\'') {
            throw new IllegalStateException("Bad character");
        }
        TrieNode ideal = this.getNext(ch);
        if(ideal==null){
            if (restOfWord.length() > 1 && numOfErrorsAllowed > 0) {
                // character we want insnt there, try others
                for (int i = 1; i < this.next.length; i++) {
                    TrieNode potentialNext = this.next[i];
                    if (potentialNext != null) {
                        String attemptSwap = potentialNext.spellcheck(restOfWord.substring(1), numOfErrorsAllowed - 1);
                        if (attemptSwap != null) {
                            return attemptSwap;
                        }

                        String attemptSkip = this.spellcheck(restOfWord.substring(1), numOfErrorsAllowed - 1);
                        if (attemptSkip != null) {
                            return attemptSkip;
                        }
                    }
                }
            }

        }else{
            if (restOfWord.length() == 1) {
                if (ideal.isTerminatorSet()) {
                    return ideal.stringToHere;
                }
                return ideal.spellcheck(restOfWord, numOfErrorsAllowed -1);
            } else {
                String attemptLegit = ideal.spellcheck(restOfWord.substring(1), numOfErrorsAllowed);
                if (attemptLegit != null) {
                    return  attemptLegit;
                }
                if (numOfErrorsAllowed > 0) {
                    for (int i = 1; i < this.next.length; i++) {
                        TrieNode potentialNext = this.next[i];
                        if (potentialNext != null) {
                            String attemptSkip = potentialNext.spellcheck(restOfWord.substring(1), numOfErrorsAllowed - 1);
                            if (attemptSkip != null) {
                                return attemptSkip;
                            }
                        }
                    }
                }
            }
        }
        return null;

    }

    public boolean lookUp (String restOfWord) {
        char ch = restOfWord.charAt(0);

        if (!Character.isLetter(ch) && ch != '\'') {
            throw new IllegalStateException("Bad character");
        }
        TrieNode next = this.getNext(ch);
        if(next==null){
            return false;

        }else{
            if (restOfWord.length() == 1) {
                if (next.isTerminatorSet()) {
                    return true;
                }
                return false;
            } else {
                return next.lookUp(restOfWord.substring(1));
            }
        }


    }


    public void setValue(char ch){
        value = ch;
    }

    public void setWordToHere(String wordToHere) {
        stringToHere = wordToHere;
    }

    public char getValue(){
        return value;
    }

    public TrieNode getNext(char ch){
        return next[TrieNode.getIndex(ch)];
    }

    public TrieNode getNext(int i){
        if(!(i>0 && i<=27)){
            return null;

        }
        return next[i];
    }

    public boolean setNext(char ch, TrieNode node){
        next[TrieNode.getIndex(ch)] = node;
        return true;
    }

    public TrieNode getTerminator(){
        return next[0];
    }

    //marks terminator as non null (the Trienode is just a placeholder
    public void setTerminator(){
        next[0] = new TrieNode();
    }

    public boolean isTerminatorSet(){
        return next[0] != null;
    }

    public String toString(){
        String string = "";

        for (int i =0; i<27; i++){
            if(next[i]==null){
                string+= "null";
            }else{
                string += "+++";

            }
        }
        return string;
    }

    /*returns index 1-27 for valid words*/
    public static int getIndex(char ch){
        if(ch >= 'a' && ch<= 'z'){
            return (int)ch-'a'+1;
        }else if(ch>='A'&& ch<= 'Z'){
            return (int)ch-'A'+1;
        }else if(ch == '\'') {
            return 27;
        }
        return -1;

    }




// method adds a new word which is 1st starting with its letter
// need this method since it is the only one which modifies the trie root


//            public TrieNode next ( final char c){ //makes sure it doesn't exceed 26
//                return trieNodes[c - 'a'];
//            }

//    boolean activeSpellCheck(){ //todo actively check every char input and if one is wrong then offer spell check
//
//    }
//
//    boolean setEndOfWord(boolean isWord){
//
//    }


        }


