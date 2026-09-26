package leetcode.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://leetcode.com/problems/clone-graph/description/
 */
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}


public class CloneGraph {
    Map<Node, Node> map = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null)
            return null;

        if (map.containsKey(node))
            return map.get(node);

        Node copy = new Node(node.val);
        map.put(node, copy);

        for (Node next : node.neighbors) {
            copy.neighbors.add(cloneGraph(next));
        }

        return copy;
    }

    @Test
    void test() {
        Node node = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        node.neighbors.add(node2);
        node.neighbors.add(node3);
        node2.neighbors.add(node4);
        Node result = cloneGraph(node);
        assertThat(result).isNotNull();
        assertThat(result.val).isEqualTo(1);
    }
}
