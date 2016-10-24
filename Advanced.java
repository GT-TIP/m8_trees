/**
 * Created by JennaKwon on 10/23/16.
 *
 * Question:
 * - Compute the least common ancestor (LCA) in a binary search tree
 * - Then, do the same for a binary tree
 *
 * Citation: Element of Programming Interviews 
 *
 */
public class Advanced {

    private static class BinaryTreeNode<Integer> {
        public Integer data;
        public BinaryTreeNode left;
        public BinaryTreeNode right;

        public BinaryTreeNode(Integer data, BinaryTreeNode left, BinaryTreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private static class Status {
        public int numTargetNodes;
        public BinaryTreeNode<Integer> ancestor;

        public Status(int numTargetNodes, BinaryTreeNode<Integer> node) {
            this.numTargetNodes = numTargetNodes;
            this.ancestor = node;
        }
    }

    public static BinaryTreeNode<Integer> LCABinarySearchTree(BinaryTreeNode<Integer> tree,
                                                              BinaryTreeNode<Integer> s,
                                                              BinaryTreeNode<Integer> b) {
        BinaryTreeNode<Integer> p = tree;
        while (p.data < s.data || p.data > b.data) {
            // Keep searching since p is outside of [s, b].
            while (p.data < s.data) {
                p = p.right; // LCA must be in p's right child.
            }
            while (p.data > b.data) {
                p = p.left; // LCA must be in p's left child.
            }
        }
        // Now, s.data >= p.data && p.data <= b.data.
        return p;
    }

    public static BinaryTreeNode<Integer> LCABinaryTree(BinaryTreeNode<Integer> tree,
                                              BinaryTreeNode<Integer> node0,
                                              BinaryTreeNode<Integer> node1) {
        return LCABinaryTreeHelper(tree, node0, node1).ancestor;
    }
    

    private static Status LCABinaryTreeHelper(BinaryTreeNode<Integer> tree,
                                    BinaryTreeNode<Integer> node0,
                                    BinaryTreeNode<Integer> node1) {
        if (tree == null) {
            return new Status(0, null);
        }

        Status leftResult = LCABinaryTreeHelper(tree.left, node0, node1);
        // Found both nodes in the left subtree.
        if (leftResult.numTargetNodes == 2) {
            return leftResult;
        }

        Status rightResult = LCABinaryTreeHelper(tree.right, node0, node1);
        // Found both nodes in the right subtree.
        if (rightResult.numTargetNodes == 2) {
            return rightResult;
        }

        int numTargetNodes = leftResult.numTargetNodes + rightResult.numTargetNodes
                + (tree == node0 ? 1 : 0) + (tree == node1 ? 1 : 0);
        return new Status(numTargetNodes, numTargetNodes == 2 ? tree : null);
    }

    public static void main(String[] args) {
        // 3
        // 2 5
        // 1 4 6
        BinaryTreeNode<Integer> BST = new BinaryTreeNode<>(3, null, null);
        BST.left = new BinaryTreeNode<>(2, null, null);
        BST.left.left = new BinaryTreeNode<>(1, null, null);
        
        BST.right = new BinaryTreeNode<>(5, null, null);
        BST.right.left = new BinaryTreeNode<>(4, null, null);
        BST.right.right = new BinaryTreeNode<>(6, null, null);
        // should output 3
        BinaryTreeNode<Integer> x = LCABinarySearchTree(BST, BST.left, BST.right);
        assert(x.data.equals(3));
        System.out.println(x.data);
        // should output 5
        x = LCABinarySearchTree(BST, BST.right.left, BST.right.right);
        assert(x.data.equals(5));
        System.out.println(x.data);
        // should output 5
        x = LCABinarySearchTree(BST, BST.right, BST.right.right);
        assert(x.data.equals(5));
        System.out.println(x.data);
        // should output 3
        x = LCABinarySearchTree(BST, BST.left.left, BST.right.right);
        assert(x.data.equals(3));
        System.out.println(x.data);
        // should output 3
        x = LCABinarySearchTree(BST, BST.left.left, BST);
        assert(x.data.equals(3));
        System.out.println(x.data);
        // should output 2
        x = LCABinarySearchTree(BST, BST.left, BST.left);
        assert(x.data.equals(2));
        System.out.println(x.data);
        
        // 3
        // 2 5
        // 1 4 6
        BinaryTreeNode<Integer> BT = new BinaryTreeNode<>(3, null, null);
        BT.left = new BinaryTreeNode<>(1, null, null);
        BT.left.left = new BinaryTreeNode<>(2, null, null);
        BT.right = new BinaryTreeNode<>(5, null, null);
        BT.right.left = new BinaryTreeNode<>(6, null, null);
        BT.right.right = new BinaryTreeNode<>(4, null, null);
        // should output 3
        BinaryTreeNode<Integer> y = LCABinaryTree(BT, BT.left, BT.right);
        assert(y.data.equals(3));
        System.out.println(y.data);
        // should output 5
        y = LCABinaryTree(BT, BT.right.left, BT.right.right);
        assert(y.data.equals(5));
        System.out.println(y.data);
        // should output 5
        y = LCABinaryTree(BT, BT.right, BT.right.right);
        assert(y.data.equals(5));
        System.out.println(y.data);
        // should output 3
        y = LCABinaryTree(BT, BT.left.left, BT.right.right);
        assert(y.data.equals(3));
        System.out.println(y.data);
        // should output 3
        y = LCABinaryTree(BT, BT.left.left, BT);
        assert(y.data.equals(3));
        System.out.println(y.data);
        // should output 2
        y = LCABinaryTree(BT, BT.left, BT.left);
        assert(y.data.equals(2));
        System.out.println(y.data);
    }
}
