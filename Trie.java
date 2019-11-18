import java.util.*;


public class Tree implements Addable{

    private Node<String> root;

    public Tree() {
        root = null;
    }

    public boolean add(String item) {
        root=add(item, root);
        return true;
    }

    public Node<String> add(String item, Node<String> node) {
        if (node == null) {
            return new Node<String>(item);
        } else if (node.data.compareTo(item) < 0) {
            node.right = add(item, node.right);
        } else {
            node.left = add(item, node.left);
        }
        return node;
    }

    public void print() {
        print(root);
    }

    public void print(Node<String> node) {
        if (node != null) {
            print(node.left);
            System.out.println(node.data);
            print(node.right);
        }
    }

    public String spellcheck(String item){
        return spellcheck(item, root);
    }

    public String spellcheck(String item, Node<String> node) {

        if (node.data.compareTo(item) == 0) {
            throw new IllegalStateException("exact match found");
        } else if (node.data.compareTo(item) < 0) {
            if(node.right==null) {
                return node.data;
            } else {
                return spellcheck(item, node.right);
            }

        } else {
            if(node.left==null) {
                return node.data;
            } else {
                return spellcheck(item, node.left);
            }

        }
    }
    public boolean lookUp(String item) {

        return lookUp(item, root);
    }

    public boolean lookUp(String item, Node<String> node) {
        if (node == null) {
            return false;
        }
        if (node.data.compareTo(item) == 0) {
            return true;
        } else if (node.data.compareTo(item) < 0) {
            return lookUp(item, node.right);
        } else {
            return lookUp(item, node.left);
        }
    }

}




