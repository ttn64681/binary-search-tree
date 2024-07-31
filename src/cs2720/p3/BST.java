package cs2720.p3;

/**
 * Class representing a binary search tree structure.
 */
public class BST<T extends Comparable<T>> {

    private NodeType<T> root;

    /**
     * Constructor for binary search tree.
     */
    public BST() {
        this.root = null;

    } // BST

    /**
     * Inserts an node containing a given item to BST.
     *
     * @param key the item to insert of type T
     */
    public void insert(T key) {
        // traverses the tree until leaf node is found, setting its left/right child
        // appropriately as a newNode containing the given item (key)
        this.root = insertNode(this.root, key);
    } // insert

    /**
     * Insert helper method to insert a node containing a given item (recursively).
     *
     * @param root the given node root/parent
     * @param key the item to insert of type T
     * @return root the tree starting from the root along with the inserted newNode
     */
    private NodeType<T> insertNode(NodeType<T> root, T key) {
        // checks if current root is null (aka parent is leaf node) or if tree root is null
        if (root == null) {
            NodeType<T> newNode = new NodeType<T>(key); // create newNode to return
            return newNode;
        } // if

        int comparison = key.compareTo(root.info);
        // traverses to appropriate leaf node where newNode will be inserted
        if (comparison < 0) { // traverse to left subtree
            root.left = insertNode(root.left, key);
        } else if (comparison > 0) { // traverse to right subtree
            root.right = insertNode(root.right, key);
        } else { // if key is a duplicate then return
            System.out.println("The item already exists in the tree.");
        } // if
        return root;
    } // insertNode

    /**
     * Deletes a node containing the given item from BST.
     *
     * @param key the item to delete of type T
     */
    public void delete(T key) {
        // traverses tree until key node is found, which is then deleted appropriately
        this.root = deleteNode(this.root, key);
    } // delete

    /**
     * Delete helper method to delete a node containing a given item (recursively).
     *
     * @param root the given node root/parent
     * @param key the item to delete of type T
     * @return root the tree starting from the root excluding the deleted node
     */
    public NodeType<T> deleteNode(NodeType<T> root, T key) {
        // checks if current root is null, meaning key was not found in the tree
        if (root == null) {
            System.out.println("The item is not present in the tree.");
            return root;
        } // if

        int comparison = key.compareTo(root.info);
        // traverses to find node to delete
        if (comparison < 0) { // traverse to left subtree
            root.left = deleteNode(root.left, key);
        } else if (comparison > 0) { // traverse to right subtree
            root.right = deleteNode(root.right, key);
        } else {
            // once key is found, if current node has no child (is leaf), return that node as null
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) { // if current node has just one child
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } // if

            // if current node has both children, find replace it with predecessor value
            NodeType<T> predecessor = getPred(root.left);
            root.info = predecessor.info;
            // then delete the predecessor node
            root.left = deleteNode(root.left, predecessor.info);
        } // if
        // return root and tree
        return root;
    } // deleteNode

    /**
     * Retrieves the predecessor node (node with largest value of left subtree).
     *
     * @param node the given left subtree node
     * @return node the predecessor node to return
     */
    private NodeType<T> getPred(NodeType<T> node) {
        // traverses right-most/largest sub-node
        while (node.right != null) {
            node = node.right;
        } // while
        return node;
    } // getPred

    /**
     * Searches for node containing given item.
     *
     * @param key the item to be found of Type T
     * @return boolean the boolean to return if node is found or not
     */
    public boolean search(T key) {
        // if the node is null, the node was not found
        // if the node is not null, the node was found
        return searchNode(this.root, key) != null;
    } // search

    /**
     * Search helper method to find a node containing the given item (recursively).
     *
     * @param root the given node root
     * @param key the item to be found of type T
     * @return root the returned root, either null or nonnull
     */
    private NodeType<T> searchNode(NodeType<T> root, T key) {
        // checks if current root is null or if the tree root is null
        if (root == null) {
            return null; // returns null node
        } // if

        int comparison = key.compareTo(root.info);
        // traverses to find node with key
        if (comparison == 0) { // key is found
            return root;
        } else if (comparison < 0) { // traverse to left subtree
            return searchNode(root.left, key);
        } else { // traverse to right subtree
            return searchNode(root.right, key);
        } // if
    } // searchNode

    /**
     * Traverses and prints the tree out in order.
     */
    public void inOrder() {
        if (this.root == null) {
            System.out.println("Tree is currently empty");
            return;
        } else {
            System.out.print("In-order: ");
            inOrderHelper(this.root);
        } // if
    } // inOrder

    /**
     * Helper method to recursively travel and print the tree in order (recursively).
     *
     * @param root the given node root/parent
     */
    private void inOrderHelper(NodeType<T> root) {
        if (root != null) {
            inOrderHelper(root.left); // goes to left-most node
            System.out.print(root.info + " "); // prints current node
            inOrderHelper(root.right); // checks for and goes to right-most node
        } // if
    } // inOrderHelper

    /**
     * Prints the nodes within the tree that has only one child.
     */
    public void getSingleParent() {
        System.out.print("Single Parents: ");
        getSingleParentHelp(this.root);
        System.out.println();
    } // getSingleParent

    /**
     * Helper method to recursively traverse tree and print any node with one child.
     *
     * @param node the given node root/parent
     */
    private void getSingleParentHelp(NodeType<T> node) {
        // as long as current node is not null, if the node has only one child,
        // print its item
        if (node != null) {
            if ((node.left != null && node.right == null) ||
                (node.left == null && node.right != null)) {
                System.out.print(node.info + " ");
            } // if

            // traverses to left-most node, printing any single-child nodes along the way
            getSingleParentHelp(node.left);
            // traverses to right-most node, printing any single-child nodes along the way
            getSingleParentHelp(node.right);
        } // if
    } // getSingleParentHelp

    /**
     * Counts and outputs the number of leaf nodes in the BST.
     */
    public void getNumLeafNodes() {
        int numLeafNodes = getNumLeafNodesHelp(this.root);
        System.out.println("The number of leaf nodes are: " + numLeafNodes);
    } // getNumLeafNodes

    /**
     * Helper method to revursively traverse BST and count the amount of leaf nodes in it.
     *
     * @param root the given node root/parent
     * @return int the number of leaf nodes
     */
    private int getNumLeafNodesHelp(NodeType<T> root) {
        if (root == null) { // base case (reached end of branch or tree is empty)
            return 0;
        } // if

        if (root.left == null && root.right == null) { // if root has no children (is leaf)
            return 1;
        } // if

        // traverses left and right subtree recursively, tallying any leaf nodes on both sides
        // and adding them at the very end after both trees have been fully traversed
        return getNumLeafNodesHelp(root.left) + getNumLeafNodesHelp(root.right);
    } // getNumLeafNodesHelp

    /**
     * Finds and prints the cousins of a given node.
     *
     * @param key the given node's item, of whose node's cousins is to be found
     */
    public void getCousins(T key) {
        NodeType<T> targetNode = searchNode(root, key);
        if (targetNode != null) { // as long as targetNode exists, check for cousins
            int targetLevel = getLevel(root, targetNode, 1); // targetNode's level
            System.out.print("Cousins of [ " + key + " ]: ");
            printCousins(root, targetNode, targetLevel);
            System.out.println();
        } else { // if targetNode is not present in tree
            System.out.println("The node is not present in the tree.");
        } // if
    } // getCousin

    /**
     * Helper method to get the level of a node in the BST, starting from level 1
     * (root node is level 1).
     *
     * @param root the node root/parent
     * @param node the given node to find the level of
     * @param level the current level of current node
     * @return int the level of the given node
     */
    private int getLevel(NodeType<T> root, NodeType<T> node, int level) {
        if (root == null) { // end of tree reached without finding node
            return 0;
        } else if (root == node) { // node reached, no need to go to next level
            return level;
        } // if

        // if target node not reached yet, check if its down the left subtree
        int nextLevel = getLevel(root.left, node, level + 1);
        // if target node found in left subtree, return its level
        if (nextLevel != 0) {
            return nextLevel;
        } else { // if not found in left subtree, check the right subtree(s) going up
            return getLevel(root.right, node, level + 1);
        } // if
    } // getLevel

    /**
     * Helper method to print the cousins of a given node.
     *
     * @param root the node root/parent
     * @param node the given node to find the level of
     * @param level the current level
     */
    private void printCousins(NodeType<T> root, NodeType<T> node, int level) {
        // if tree is empty or target node is the root node, return
        if (root == null || level < 2) {
            return;
        } // if

        // if level is 2, then either target node is on level 2 or the parents of target node
        // have been reached (in other words we are one level above the target node)...
        if (level == 2) {
            // if one of the current node's children is the target node, don't print and return
            if (root.left == node || root.right == node) {
                return;
            } // if
            // if neither child is target node, then check and print for cousins
            if (root.left != null) {
                System.out.print(root.left.info + " ");
            } // if
            if (root.right != null) {
                System.out.print(root.right.info + " ");
            } // if
        } else if (level > 2) {
            // if level is not yet 2 (not yet 1 above target node's level), keep recursively
            // traversing down the left subtree, and once finished, check/recurse down the right
            // subtree(s)...
            // decrement level each time going down the tree
            printCousins(root.left, node, level - 1);
            printCousins(root.right, node, level - 1);
        } // if
    } // printCousins

    /**
     * Helper method to retrieve the root node.
     *
     * @return root the BST's root
     */
    public NodeType<T> getRoot() {
        return this.root;
    } // getRoot

} // BST
