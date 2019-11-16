import java.util.ArrayList;
import java.util.List;


public class Trie implements Addable {

    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }



//    public void setRoot(){
//
//    }
//
//
//    public void getChildren(){
//
//    }



    @Override
    public void add(String word) {

        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {

            if(current.trieNodes[word.charAt(i)-'a'] ==null){
                current.trieNodes[word.charAt(i)-'a'] = new TrieNode();
            }
            current = current.next(word.charAt(i));
        }
        //current.setEndOfWord(true);
        current.endOfWord++;
//        TrieNode current = root;
//
//        for (int i = 0; i < word.length(); i++) {
//            current = current.getChildren()
//                    .computeIfAbsent(word.charAt(i), c -> new TrieNode());
//        }
//        current.setEndOfWord(true);

    }

    public static void main(String[] args){
        final List<String> setOfStrings = new ArrayList<>();
        setOfStrings.add("pqrs");
        setOfStrings.add("pprt");
        setOfStrings.add("psst");
        setOfStrings.add("qqrs");
        setOfStrings.add("pqrs");
        final TrieNode trie = new TrieNode();
        setOfStrings.forEach(trie::insert);
        System.out.println(trie.lookUp("psst"));
        System.out.println(trie.lookUp("psstq"));
        System.out.println(trie.lookUp("qqrs"));
        System.out.println(trie.lookUp("psst"));



    }
}
