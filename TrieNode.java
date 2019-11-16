import java.util.HashMap;

public class TrieNode {

    //private HashMap<Character, TrieNode> children; /*maps from character to matching node, quick look up*/
    final TrieNode root = new TrieNode();
    final TrieNode[] trieNodes = new TrieNode[26];
    public int endOfWord = 0; //increment to one if it is the end of the word?

//    TrieNode(TrieNode root) {
//        this.root = new TrieNode();
//        this.current = new TrieNode;
//
//    }


    /*O(k), k is the length of key & space is O(26 * k *N) where n is number of keys*/
    public void insert(String word){

        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {

            if(current.trieNodes[word.charAt(i)-'a'] ==null){
                current.trieNodes[word.charAt(i)-'a'] = new TrieNode();
            }
            current = current.next(word.charAt(i));
        }
        //current.setEndOfWord(true);
        current.endOfWord++;


    }

    //method that, given a String word, will identify the node that corresponds to the last letter of the word, if it exists in the tree:
    public int lookUp(String word){  //don't lookup from root every time, build on past calls (is char a child of previous node rather than lookup from scratch
        //todo look up by char, then checking if next node is a child of char (keeping state/return node reference

        TrieNode currentNode = root; //.next(word.charAt(0)); //current pointer is the first char from the root
        for(int i = 0; i<word.length(); i++) {
//            int index = dictionary.getIndex(word.charAt(i)); //make this method that simply returns the position of the i'th character in the alphabet
            //TrieNode child = currentNode.children[index];

            if (currentNode.next(word.charAt(i)) == null || currentNode == null) {
                return 0;
            }
                currentNode = currentNode.next(word.charAt(i));
                //currentNode = child;//current is set to the next pointer every time
//            if(current.isWord){  //todo does this have to be current.isWord or just (isWord)
//                return true;

        }
        return currentNode.endOfWord;
        }

    public TrieNode next(final char c){ //makes sure it doesn't exceed 26
        return trieNodes[c-'a'];
    }

//    boolean activeSpellCheck(){ //todo actively check every char input and if one is wrong then offer spell check
//
//    }
//
//    boolean setEndOfWord(boolean isWord){
//
//    }



}
