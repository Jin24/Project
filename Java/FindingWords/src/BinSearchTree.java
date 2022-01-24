/**
 * @author Jin 
 * Student number: 250964902
 * 
 * This class uses the Binary tree to search through the nodes created by the BinSearchTreeNode class by inputing a String. 
 */

public class BinSearchTree {

	private BinSearchTreeNode root;

	/**
	 * Takes a String as a parameter and searches the Binary tree node that contains the word.
	 * @param searchWord
	 * @return
	 */
	public BinSearchTreeNode getWord(String searchWord) {
		if (searchWord == null) {
			return null;
		}
		BinSearchTreeNode found = search(root, searchWord); // Uses the private method search() until it finds the word
															// and returns the node containing the word, otherwise
															// returns null;
		return found;
	}

	/**
	 * Searches Binary tree.
	 * Accepts a Node "root" and the word being searched as the parameter.
	 * @param r
	 * @param searchWord
	 * @return
	 */
	private BinSearchTreeNode search(BinSearchTreeNode r, String searchWord) {
		if (r == null) {
			return null; // Tree is empty, null is returned
		} else if (r.getWord().equals(searchWord)) {
			return r; // Found the word, returning the node containing the word
		} else if (r.getWord().compareTo(searchWord) > 0) {
			return search(r.getLeft(), searchWord); // Search would go to the left node
		} else {
			return search(r.getRight(), searchWord);// Search would go to the right node
		}

	}
	
	
	/**
	 * Inserts a word at a specific position in the binary tree by using the position and file name from the parameters.
	 * @param theWord
	 * @param theFileName
	 * @param thePosition
	 */

	public void insertWord(String theWord, String theFileName, int thePosition) {
		if (search(root, theWord) != null) {
			BinSearchTreeNode node = search(root, theWord);
			LinkedList Lp = node.getFiles();
			Lp.insertWord(theFileName, thePosition);
		} else if (root == null){
			root = new BinSearchTreeNode (theWord, theFileName, thePosition);
		} else {
			insert(root, theWord, theFileName, thePosition);
		}

	
	}

	private void insert(BinSearchTreeNode r, String theWord, String theFile, int thePosition) {
		BinSearchTreeNode node = new BinSearchTreeNode(theWord, theFile, thePosition);
		if (r == null) {
			root = node;
		} else if (r.getWord().compareTo(theWord) > 0) {
			if (r.getLeft() == null) {
				r.setLeft(node);
			} else {
				insert(r.getLeft(), theWord, theFile, thePosition);
			}
		} else if (r.getWord().compareTo(theWord) < 0) {
			if (r.getRight() == null) {
				r.setRight(node);
			} else {
				insert(r.getRight(), theWord, theFile, thePosition);
			}
		}

	}
}