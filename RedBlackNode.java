package RBtree;

class RedBlackNode {

    public static final int BLACK = 0;

    public static final int RED = 1;

	public Integer key;

    RedBlackNode parent;

    RedBlackNode left;

    RedBlackNode right;

    public int numLeft = 0;

    public int numRight = 0;

    public int color;

    RedBlackNode(){
        color = BLACK;
        numLeft = 0;
        numRight = 0;
        parent = null;
        left = null;
        right = null;
    }

	RedBlackNode(Integer key){
        this();
        this.key = key;
	}
}
