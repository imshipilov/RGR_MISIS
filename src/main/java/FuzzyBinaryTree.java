import java.util.Comparator;

class FuzzyBinaryTree<T> {
    private FuzzyNode<T> root; // Значение корня дерева
    private Comparator<T> comparator;

    public FuzzyBinaryTree(Comparator<T> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    // Добавляем новый узел в дерево
    public void add(T value) {
        root = addRecursive(root, value);
    }

    // Рекурсивный метод для добавления узла
    private FuzzyNode<T> addRecursive(FuzzyNode<T> current, T value) {
        if (current == null) {
            System.out.println("Добавлено четкое число со значением " +  value);
            return new FuzzyNode<>(value);
        }
        if (comparator.compare(value, current.value) < 0) {
            current.left = addRecursive(current.left, value);
        } else if (comparator.compare(value, current.value) > 0) {
            current.right = addRecursive(current.right, value);
        } else {
            System.out.println("Вы пытаетесь добавить уже существующее значение!");
        }
        return current;
    }


    public void printTree() {
        System.out.println("Выводим дерево...");
        printTree(root, "", true);
    }

    private void printTree(FuzzyNode<T> node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix +  "└── " + node.value);
            printTree(node.left, prefix + (isTail ? "    " : "│   "), false);
            printTree(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    public boolean isTreeValid() {
        return countNodes(root) >= 7;
    }

    private int countNodes(FuzzyNode<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}
