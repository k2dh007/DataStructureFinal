package RBtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RBtree1 {

	private RedBlackNode nil = new RedBlackNode();
	private RedBlackNode root = nil;

    public RBtree1() {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

	private void leftRotate(RedBlackNode x){

		leftRotateFixup(x);

		RedBlackNode y;
		y = x.right;
		x.right = y.left;

		if (!isNil(y.left))
			y.left.parent = x;
		y.parent = x.parent;

		if (isNil(x.parent))
			root = y;

		else if (x.parent.left == x)
			x.parent.left = y;

		else
			x.parent.right = y;

		y.left = x;
		x.parent = y;
	}

	private void leftRotateFixup(RedBlackNode x){

		if (isNil(x.left) && isNil(x.right.left)){
			x.numLeft = 0;
			x.numRight = 0;
			x.right.numLeft = 1;
		}

		else if (isNil(x.left) && !isNil(x.right.left)){
			x.numLeft = 0;
			x.numRight = 1 + x.right.left.numLeft +
					x.right.left.numRight;
			x.right.numLeft = 2 + x.right.left.numLeft +
					  x.right.left.numRight;
		}

		else if (!isNil(x.left) && isNil(x.right.left)){
			x.numRight = 0;
			x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;

		}

		else{
			x.numRight = 1 + x.right.left.numLeft +
				     x.right.left.numRight;
			x.right.numLeft = 3 + x.left.numLeft + x.left.numRight +
			x.right.left.numLeft + x.right.left.numRight;
		}

	}
	private void rightRotate(RedBlackNode y){

		rightRotateFixup(y);

        RedBlackNode x = y.left;
        y.left = x.right;

        if (!isNil(x.right))
            x.right.parent = y;
        x.parent = y.parent;

        if (isNil(y.parent))
            root = x;

        else if (y.parent.right == y)
            y.parent.right = x;

        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;

	}

	private void rightRotateFixup(RedBlackNode y){

		if (isNil(y.right) && isNil(y.left.right)){
			y.numRight = 0;
			y.numLeft = 0;
			y.left.numRight = 1;
		}

		else if (isNil(y.right) && !isNil(y.left.right)){
			y.numRight = 0;
			y.numLeft = 1 + y.left.right.numRight +
				  y.left.right.numLeft;
			y.left.numRight = 2 + y.left.right.numRight +
				  y.left.right.numLeft;
		}

		else if (!isNil(y.right) && isNil(y.left.right)){
			y.numLeft = 0;
			y.left.numRight = 2 + y.right.numRight +y.right.numLeft;

		}

		else{
			y.numLeft = 1 + y.left.right.numRight +
				  y.left.right.numLeft;
			y.left.numRight = 3 + y.right.numRight +
				  y.right.numLeft +
			y.left.right.numRight + y.left.right.numLeft;
		}

	}


    public void insert(Integer key) {
        insert(new RedBlackNode(key));
    }

	private void insert(RedBlackNode z) {

			RedBlackNode y = nil;
			RedBlackNode x = root;

			while (!isNil(x)){
				y = x;

				if (z.key.compareTo(x.key) < 0){

					x.numLeft++;
					x = x.left;
				}

				else{
					x.numRight++;
					x = x.right;
				}
			}

			z.parent = y;

			if (isNil(y))
				root = z;
			else if (z.key.compareTo(y.key) < 0)
				y.left = z;
			else
				y.right = z;

			z.left = nil;
			z.right = nil;
			z.color = RedBlackNode.RED;

			insertFixup(z);

	}

	private void insertFixup(RedBlackNode z){

		RedBlackNode y = nil;

		while (z.parent.color == RedBlackNode.RED){

			if (z.parent == z.parent.parent.left){

				y = z.parent.parent.right;

				if (y.color == RedBlackNode.RED){
					z.parent.color = RedBlackNode.BLACK;
					y.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					z = z.parent.parent;
				}

				else if (z == z.parent.right){

					z = z.parent;
					leftRotate(z);
				}

				else{

					z.parent.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					rightRotate(z.parent.parent);
				}
			}

			else{

				y = z.parent.parent.left;

				if (y.color == RedBlackNode.RED){
					z.parent.color = RedBlackNode.BLACK;
					y.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					z = z.parent.parent;
				}

				else if (z == z.parent.left){

					z = z.parent;
					rightRotate(z);
				}

				else{

					z.parent.color = RedBlackNode.BLACK;
					z.parent.parent.color = RedBlackNode.RED;
					leftRotate(z.parent.parent);
				}
			}
		}

	root.color = RedBlackNode.BLACK;

	}
	public RedBlackNode treeMinimum(RedBlackNode node){

		while (!isNil(node.left))
			node = node.left;
		return node;
	}
	public RedBlackNode treeMaximum(RedBlackNode node){

		while (!isNil(node.right))
			node = node.right;
		return node;
	}
	public RedBlackNode treeSuccessor(RedBlackNode x){

		if (!isNil(x.left) )
			return treeMinimum(x.right);

		RedBlackNode y = x.parent;

		while (!isNil(y) && x == y.right){

			x = y;
			y = y.parent;
		}

		return y;
	}
	
	public RedBlackNode treePredecessor(RedBlackNode x)
    {
        if (!isNil(x.left))
            return treeMaximum(x.left);
                
        RedBlackNode y = x.parent;
 
        while (!isNil(y) && x == y.left)
        {
            x = y;
            y = y.parent;
        }
        
        return y;
    }
	/*public RedBlackNode treePredecessor(RedBlackNode node)
    {
        if (node == null)
            return null;
        
        if (node.left != null)
            return treeMaximum(node.left);
                
        RedBlackNode y = node.parent;
        RedBlackNode x = node;
        while (y != null && x == y.left)
        {
            x = y;
            y = y.parent;
        }
        
        return y;
    }*/

	public RedBlackNode remove(Integer key){
		RedBlackNode z = search(key);
		if(z == null) return null;

		RedBlackNode x = nil;
		RedBlackNode y = nil;

		if (isNil(z.left) || isNil(z.right))
			y = z;

		else y = treeSuccessor(z);

		if (!isNil(y.left))
			x = y.left;
		else
			x = y.right;

		x.parent = y.parent;

		if (isNil(y.parent))
			root = x;

		else if (!isNil(y.parent.left) && y.parent.left == y)
			y.parent.left = x;

		else if (!isNil(y.parent.right) && y.parent.right == y)
			y.parent.right = x;

		if (y != z){
			z.key = y.key;
		}

		fixNodeData(x,y);

		if (y.color == RedBlackNode.BLACK)
			removeFixup(x);
		
		return z;
	}

	public void remove(RedBlackNode v){

		RedBlackNode z = search(v.key);

		RedBlackNode x = nil;
		RedBlackNode y = nil;

		if (isNil(z.left) || isNil(z.right))
			y = z;

		else y = treeSuccessor(z);

		if (!isNil(y.left))
			x = y.left;
		else
			x = y.right;

		x.parent = y.parent;

		if (isNil(y.parent))
			root = x;

		else if (!isNil(y.parent.left) && y.parent.left == y)
			y.parent.left = x;

		else if (!isNil(y.parent.right) && y.parent.right == y)
			y.parent.right = x;

		if (y != z){
			z.key = y.key;
		}

		fixNodeData(x,y);

		if (y.color == RedBlackNode.BLACK)
			removeFixup(x);
	}
	private void fixNodeData(RedBlackNode x, RedBlackNode y){

		RedBlackNode current = nil;
		RedBlackNode track = nil;

		if (isNil(x)){
			current = y.parent;
			track = y;
		}

		else{
			current = x.parent;
			track = x;
		}

		while (!isNil(current)){

			if (y.key != current.key) {

				if (y.key.compareTo(current.key) > 0)
					current.numRight--;

				if (y.key.compareTo(current.key) < 0)
					current.numLeft--;
			}

			else{

				if (isNil(current.left))
					current.numLeft--;
				else if (isNil(current.right))
					current.numRight--;

				else if (track == current.right)
					current.numRight--;
				else if (track == current.left)
					current.numLeft--;
			}

			track = current;
			current = current.parent;

		}

	}
	private void removeFixup(RedBlackNode x){

		RedBlackNode w;

		while (x != root && x.color == RedBlackNode.BLACK){

			if (x == x.parent.left){

				w = x.parent.right;

				if (w.color == RedBlackNode.RED){
					w.color = RedBlackNode.BLACK;
					x.parent.color = RedBlackNode.RED;
					leftRotate(x.parent);
					w = x.parent.right;
				}

				if (w.left.color == RedBlackNode.BLACK &&
							w.right.color == RedBlackNode.BLACK){
					w.color = RedBlackNode.RED;
					x = x.parent;
				}

				else{

					if (w.right.color == RedBlackNode.BLACK){
						w.left.color = RedBlackNode.BLACK;
						w.color = RedBlackNode.RED;
						rightRotate(w);
						w = x.parent.right;
					}

					w.color = x.parent.color;
					x.parent.color = RedBlackNode.BLACK;
					w.right.color = RedBlackNode.BLACK;
					leftRotate(x.parent);
					x = root;
				}
			}

			else{

				w = x.parent.left;

				if (w.color == RedBlackNode.RED){
					w.color = RedBlackNode.BLACK;
					x.parent.color = RedBlackNode.RED;
					rightRotate(x.parent);
					w = x.parent.left;
				}

				if (w.right.color == RedBlackNode.BLACK &&
							w.left.color == RedBlackNode.BLACK){
					w.color = RedBlackNode.RED;
					x = x.parent;
				}

				else{

					 if (w.left.color == RedBlackNode.BLACK){
						w.right.color = RedBlackNode.BLACK;
						w.color = RedBlackNode.RED;
						leftRotate(w);
						w = x.parent.left;
					}

					w.color = x.parent.color;
					x.parent.color = RedBlackNode.BLACK;
					w.left.color = RedBlackNode.BLACK;
					rightRotate(x.parent);
					x = root;
				}
			}
		}
		x.color = RedBlackNode.BLACK;
	}

	public RedBlackNode search(Integer key){

		RedBlackNode current = root;

		while (!isNil(current)){

			if (current.key.equals(key))

				return current;

			else if (current.key.compareTo(key) < 0)
				current = current.right;

			else
				current = current.left;
		}

		return null;
	}
	public RedBlackNode numGreater(Integer key){

		return findNumGreater(root,key);

	}
	public RedBlackNode numSmaller(Integer key){

		return findNumSmaller(root,key);

	}
	public RedBlackNode findNumSmaller(RedBlackNode node, Integer key){
		node = treeMaximum(node);
		while(!isNil(node) && key.compareTo(node.key)<0){
			node = treePredecessor(node);
		}
		return node;
	}
	/*public int findNumGreater(RedBlackNode node, Integer key){

		if (isNil(node))
			return 0;

		else if (key.compareTo(node.key) < 0)
            return 1+ node.numRight + findNumGreater(node.left,key);

		else
			return findNumGreater(node.right,key);

	}*/

    public List getGreaterThan(Integer key, Integer maxReturned) {
        List list = new ArrayList();
        getGreaterThan(root, key, list);
        return list.subList(0, Math.min(maxReturned, list.size()));
    }


    private void getGreaterThan(RedBlackNode node, Integer key,
                                List list) {
        if (isNil(node)) {
            return;
        } else if (node.key.compareTo(key) > 0) {
            getGreaterThan(node.left, key, list);
            list.add(node.key);
            getGreaterThan(node.right, key, list);
        } else {
            getGreaterThan(node.right, key, list);
        }
    }
    public RedBlackNode findNumGreater(RedBlackNode node, Integer key){
		node = treeMinimum(node);
    	while(!isNil(node) && key.compareTo(node.key)>0){
			node = treeSuccessor(node);
		}
		return node;
	}
	/*public int findNumSmaller(RedBlackNode node, Integer key){

		if (isNil(node)) return 0;

		else if (key.compareTo(node.key) <= 0)
			return findNumSmaller(node.left,key);

		else
			return 1+ node.numLeft + findNumSmaller(node.right,key);

	}*/

	public boolean isNil(RedBlackNode node){

		return node == nil;

	}
	public int size(){

		return root.numLeft + root.numRight + 1;
	}
	
	int i=0;
	public int nb(){
		i=0;
		if (isNil(root))
		      return 0;
		    else {
		      nb(root.left);
		      if(root.color==RedBlackNode.BLACK)i++;
		      nb(root.right);
		    }
		return i;
	}
	
	private void nb(RedBlackNode tree){
		if (isNil(tree))
		      return;
		    else {
		      nb(tree.left);
		      if(tree.color==RedBlackNode.BLACK)i++;
		      nb(tree.right);
		    }
	}
	
	public int bh() {
		if (root == null)   
		      return 0;    
		int leftBlackHeight = bh(root.left);
		    if (leftBlackHeight == 0)
		         return leftBlackHeight;
		int rightBlackHeight = bh(root.right);
		    if (rightBlackHeight == 0)
		         return rightBlackHeight;
		    if (leftBlackHeight != rightBlackHeight)
		          return 0;
		    else{
		      return leftBlackHeight;
		    }
		}
	
	public int bh(RedBlackNode tree) {
		if (isNil(tree))   
		      return 1;    
		int leftBlackHeight = bh(tree.left);
		    if (leftBlackHeight == 0)
		         return leftBlackHeight;
		int rightBlackHeight = bh(tree.right);
		    if (rightBlackHeight == 0)
		         return rightBlackHeight;
		    if (leftBlackHeight != rightBlackHeight)
		          return 0;
		    else{
		        if(tree.color==RedBlackNode.BLACK) return leftBlackHeight+1;
		        else return leftBlackHeight;
		    }
		}

	public void inorder() {
	    if (root == null)
	      return;
	    else {
	      inorder(root.left);
	      System.out.println(root.key + ((root.color==0)?" B":" R"));
	      inorder(root.right);
	    }
	  }
	public void inorder(RedBlackNode tree) {
	    if (isNil(tree))
	      return;
	    else {
	      inorder(tree.left);
	      System.out.println(tree.key + ((tree.color==0)?" B":" R"));
	      inorder(tree.right);
	    }
	  }

}



