import java.util.ArrayList;
import java.lang.Math;

public class Tree {

    // root is our topmost node! initial node
    Node root;
    ArrayList<Integer> output = new ArrayList<Integer>();

    public Tree(int value) {
        // intializing our root given particular value
        root = new Node(value, "unknown");
    }

    public Node getOne(Node node) {
        return node.child1;
    }

    public Node getTwo(Node node) {
        return node.child2;
    }

    public Node getThree(Node node) {
        return node.child3;
    }

    public Node getFour(Node node) {
        return node.child4;
    }

    public Node returnR(Tree tree) {
        return tree.root;
    }

    // GOT THIS CODE FROM
    // https://www.geeksforgeeks.org/level-order-tree-traversal/?fbclid=IwAR2wnANQ_EwIbhd-G5HRmPf9ZDP3d-2-gFELUb3eGkbWag37OzkouJkkdpM

    /* function to print level order traversal of tree */
    void printLevelOrder() {
        int h = height(root);
        int i;
        for (i = 1; i <= h; i++)
            printGivenLevel(root, i);
    }

    /*
     * Compute the "height" of a tree -- the number of nodes along the longest path
     * from the root node down to the farthest leaf node.
     */
    int height(Node root) {
        if (root == null)
            return 0;
        else {
            /* compute height of each subtree */
            int height_1 = height(root.child1);
            int height_2 = height(root.child2);
            int height_3 = height(root.child3);
            int height_4 = height(root.child4);

            int max_12 = Math.max(height_1, height_2);
            int max_34 = Math.max(height_3, height_4);
            /* larger height of each subtree will be the height */
            return Math.max(max_12, max_34) + 1;
        }
    }

    /* Print nodes at the given level */
    void printGivenLevel(Node root, int level) {
        if (root == null)
            return;
        if (level == 1) {
            // add the value of each of the lowest level to the output
            if (root.isChild()) {
                output.add(root.value);
            }
        } else if (level > 1) {
            printGivenLevel(root.child1, level - 1);
            printGivenLevel(root.child2, level - 1);
            printGivenLevel(root.child3, level - 1);
            printGivenLevel(root.child4, level - 1);
        }
    }

}