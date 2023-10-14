package dsaa.lab08;

import java.util.NoSuchElementException;

public class BST<T> {
	int size = 0;
	private class Node{
		T value;
		Node left,right,parent;
		public Node(T v) {
			value=v;
		}
		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}		
	private Node root=null;

	public BST() {
	}

	public T getElement(T toFind) {
		Node res = Search(toFind, root);
		if(res == null) return null;
		return res.value;
	}

	public T successor(T elem) {
		Node x = Search(elem, root);
		if(x == null) return null;
		if(x.right != null)
		{
			Node min = x.right;
			while(min.left != null)
			{
				min = min.left;
			}
			return min.value;
		}
		Node y = x.parent;
		while(y != null && x.value.equals(y.right.value))
		{
			x = y;
			y = y.parent;
		}
		if(y != null)
		{
			return y.value;
		}
		return null;
	}

	Node successorNode(Node node)
	{
		Node x = Search(node.value, root);
		if(x == null) return null;
		if(x.right != null)
		{
			Node min = x.right;
			while(min.left != null)
			{
				min = min.left;
			}
			return min;
		}
		Node y = x.parent;
		while(y != null && x.value.equals(y.right.value))
		{
			x = y;
			y = y.parent;
		}
		if(y != null)
		{
			return y;
		}
		return null;
	}


	public String toStringInOrder() {
		String res = InorderRecursive(root);
		if(res.length() != 0)
		{
			res = res.substring(0, res.length()-2);
		}
		return res;
	}

	String InorderRecursive(Node root) {
		String res = "";
		if (root != null) {
			res += InorderRecursive(root.left);
			res += (root.value + ", ");
			res += InorderRecursive(root.right);
		}
		return res;
	}

	public String toStringPreOrder() {
		String res = PreorderRecursive(root);
		if(res.length() != 0)
		{
			res = res.substring(0, res.length()-2);
		}
		return res;
	}

	String PreorderRecursive(Node root) {
		String res = "";
		if (root != null) {
			res += (root.value + ", ");
			res += PreorderRecursive(root.left);
			res += PreorderRecursive(root.right);
		}
		return res;
	}

	public String toStringPostOrder() {
		String res = PostorderRecursive(root);
		if(res.length() != 0)
		{
			res = res.substring(0, res.length()-2);
		}
		return res;
	}

	String PostorderRecursive(Node root) {
		String res = "";
		if (root != null) {
			res += PostorderRecursive(root.left);
			res += PostorderRecursive(root.right);
			res += (root.value + ", ");
		}
		return res;
	}


	public boolean add(T elem) {
		size++;
		Node z = new Node(elem);
		Node y = null;
		Node x = root;
		while(x != null)
		{
			y = x;
			if(((Comparable<T>)elem).compareTo(x.value) < 0)
			{
				x = x.left;
			}
			else
			{
				x = x.right;
			}
		}
		z.parent = y;
		if(y == null)
		{
			root = z;
		}
		else if(((Comparable<T>)elem).compareTo(y.value) < 0)
		{
			y.left = z;
		}
		else
		{
			y.right = z;
		}
		return true;
	}

	Node Search(T value, Node root)
	{
		if(root == null || value.equals(root.value))
			return root;
		if(((Comparable<T>)value).compareTo(root.value) < 0)
			return Search(value, root.left);
		return Search(value, root.right);
	}

	T remove(T value)
	{
		Node toRemove = Search(value, root);
		if(toRemove == null) return null;
		size--;
		if(toRemove.left == null || toRemove.right == null)
		{
			return removeNodeNo2Children(toRemove);
		}
		return removeNode2Children(toRemove);
	}

	T removeNodeNo2Children(Node node) {
		Node child = node.left;
		if(node.left == null)
		{
			child = node.right;
		}

		if (node == root)
		{
			root = child;
		}
		else if (((Comparable<T>)node.value).compareTo(node.parent.value) < 0)
		{
			node.parent.left = child;
		}
		else
		{
			node.parent.right = child;
		}
		if(child != null)
		{
			child.parent = node.parent;
		}
		return node.value;
	}

	T removeNode2Children(Node node)
	{
		Node successor = successorNode(node);
		T saved = node.value;
		node.value = successor.value;
		if(successor == node.right)
		{
			node.right = successor.right;
			successor.right.parent = node;
		}
		else
		{
			successor.parent.left = successor.right;
			if(successor.right != null)
			{
				successor.right.parent = successor.parent;
			}
		}
		return saved;
	}
	
	public void clear() {
		size = 0;
		root = null;
	}

	public int size() {
		return size;
	}

	public int NumberOfLeaves()
	{
		return RecursiveNumberOfLeaves(root);
	}

	int RecursiveNumberOfLeaves(Node current)
	{
		if(current == null) return 0;
		if(current.right == null && current.left == null) return 1;
		return RecursiveNumberOfLeaves(current.right) + RecursiveNumberOfLeaves(current.left);
	}

}
