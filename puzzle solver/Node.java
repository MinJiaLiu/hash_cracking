
public class Node {

    int value;
    // string tells whethere its a simple or compound
    String classified;
    Node child1;
    Node child2;
    Node child3;
    Node child4;

    public Node(int value, String classifed) {

        this.value = value;
        // intialize all the child to be null
        this.child1 = null;
        this.child2 = null;
        this.child3 = null;
        this.child4 = null;

        // two ways to classfy compound or simple ;
        this.classified = classifed;

    }

    // setting the child to particular node
    public void setc1(Node one) {
        this.child1 = one;
    }

    public void setc2(Node two) {
        this.child2 = two;
    }

    public void setc3(Node three) {
        this.child3 = three;
    }

    public void setc4(Node four) {
        this.child4 = four;
    }

    public boolean isChild() {
        if (this.child1 == null && this.child2 == null && this.child3 == null && this.child4 == null) {
            return true;
        }
        return false;
    }
}
