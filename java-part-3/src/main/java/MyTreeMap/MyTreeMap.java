package MyTreeMap;

public class MyTreeMap {

    static class Node {
        String key;
        Integer value;
        Node left;
        Node right;

        public Node(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size = 0;

    public void put(String key, Integer value) {
        root = putNode(root, key, value);
    }

    private Node putNode(Node node, String key, Integer value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = putNode(node.left, key, value);
        else if (cmp > 0) node.right = putNode(node.right, key, value);
        else node.value = value;
        return node;
    }

    public Integer get(String key) {
        Node n = root;
        while (n != null) {
            int cmp = key.compareTo(n.key);
            if (cmp < 0) n = n.left;
            else if (cmp > 0) n = n.right;
            else return n.value;
        }
        return null;
    }
    
    public void printSorted() {
        inOrder(root);
        System.out.println();
    }
    
    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print("[" + node.key + " = " + node.value + "] ");
        inOrder(node.right);
    }

    public int size() {
        return size;
    }

    public boolean containKey(String key) {
        Node n = root;
        while (n != null) {
            int cmp = key.compareTo(n.key);
            if (cmp < 0) n = n.left;
            else if (cmp > 0) n = n.right;
            else return true;
        }
        return false;
    }

    public String firstKey() {
        if (root == null) return null;
        Node n = root;
        while (n.left != null) n = n.left;
        return n.key;
    }

    public String lastKey() {
        if (root == null) return null;
        Node n = root;
        while (n.right != null) n = n.right;
        return n.key;
    }

    public Integer remove(String key) {
        Integer old = get(key);
        if (old == null) return null;
        root = removeNode(root, key);
        size--;
        return old;
    }

    private Node removeNode(Node node, String key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = removeNode(node.left, key);
        else if (cmp > 0) node.right = removeNode(node.right, key);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node succ = node.right;
            while (succ.left != null) succ = succ.left;
            node.key = succ.key;
            node.value = succ.value;
            node.right = removeNode(node.right, succ.key);
        }
        return node;
    }
}
