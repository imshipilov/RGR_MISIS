
class FuzzyNode<T> { // Узел дерева
    T value;
    double membershipValue; // степень принадлежности
    // Ссылки на левого и правого потомка
    FuzzyNode<T> left;
    FuzzyNode<T> right;

    FuzzyNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}