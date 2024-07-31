package cs2720.p3;

/**
 * Class representing a node of a certain type.
 */
public class NodeType<T extends Comparable<T>> {
    public T info;
    public NodeType<T> left;
    public NodeType<T> right;

    /**
     * Constructor for a NodeType.
     *
     * @param item the item for the node to contain of type T
     */
    public NodeType(T item) {
        this.info = item;
        this.left = null;
        this.right = null;
    } // NodeType<T>
} // NodeType
