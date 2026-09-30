package leetcode.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/description/
 */
public class MaximumDepthOfBinaryTree {

    public int maxDepth(TreeNode root) {

        // dfs 재귀함수로 구한다.
        return getDepth(root);
    }

    private int getDepth(TreeNode root) {
        if (root == null) {
            System.out.println("getDepth root is null");
            return 0;
        }
        System.out.println("getDepth start: " + root.val);

        int left = getDepth(root.left);
        int right = getDepth(root.right);
        int result = 1 + Math.max(left, right);
        System.out.println("getDepth val : " + root.val + ", result : " + result);
        return result;
    }


    @Test
    void test() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        int result = maxDepth(root);
        System.out.println(result);
        assertThat(result).isEqualTo(3);
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
