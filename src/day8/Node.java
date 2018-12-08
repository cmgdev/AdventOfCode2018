package day8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {

    private List<Node> children = new ArrayList<>();
    private List<Integer> metadata = new ArrayList<>();

    public Node(Iterator<String> inputs) {
        int numChildren = Integer.parseInt(inputs.next());
        int numMetadatas = Integer.parseInt(inputs.next());

        for (int i = 0; i < numChildren; i++) {
            this.addChild(new Node(inputs));
        }

        for (int i = 0; i < numMetadatas; i++) {
            this.addMetadata(Integer.parseInt(inputs.next()));
        }
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void addMetadata(Integer data) {
        metadata.add(data);
    }

    public int getSumAllMetadata() {
        int sum = getMetadataSum();
        for (Node child : children) {
            sum += child.getSumAllMetadata();
        }
        return sum;
    }

    public int getMetadataSum() {
        return metadata.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int getNodeValue() {
        if (children.size() == 0) {
            return getMetadataSum();
        }

        int value = 0;
        for (int m : metadata) {
            if (m <= children.size()) {
                value += children.get(m - 1).getNodeValue();
            }
        }
        return value;
    }
}
