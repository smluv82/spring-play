package leetcode.tree;

/**
 * https://leetcode.com/problems/invert-binary-tree/
 */
public class InvertBinaryTree {

    public TreeNode invertTree(TreeNode root) {
        // 시간 복잡도 : O(n) : 노드를 방문하면서 교환하는거라 n개 * O(1) = O(n), 공간복잡도 : O(h)
        return invert(root);
    }

    private TreeNode invert(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode left = invert(root.left);
        TreeNode right = invert(root.right);
        root.left = right;
        root.right = left;

        return root;
    }
}
