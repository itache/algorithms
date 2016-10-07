package am.itache.algorithms.trees;

import java.util.Random;

public class ImplicitTreap<E extends Comparable<E>> {
    private Node<E> root;

    public ImplicitTreap() {
    }

    private ImplicitTreap(Node<E> root) {
        this.root = root;
    }

    public static <E extends Comparable<E>> ImplicitTreap<E> merge(ImplicitTreap<E> left, ImplicitTreap<E> right) {
        return new ImplicitTreap<>(merge(left.root, right.root));
    }

    private static <E extends Comparable<E>> Node<E> merge(Node<E> left, Node<E> right) {
        if (right == null) return left;
        if (left == null) return right;
        Node<E> result;
        if (left.priority > right.priority) {
            left.right = merge(left.right, right);
            result = left;
        } else {
            right.left = merge(left, right.left);
            result = right;
        }
        result.recalcSize();
        return result;
    }

    public static <E extends Comparable<E>> Pair<ImplicitTreap<E>> split(ImplicitTreap<E> treap, int pos) {
        Pair<Node<E>> roots = split(treap.root, pos);
        return new Pair<>(new ImplicitTreap<>(roots.left), new ImplicitTreap<>(roots.right));
    }

    private static <E extends Comparable<E>> Pair<Node<E>> split(Node<E> node, int pos) {
        if (node == null) {
            return new Pair<>(null, null);
        }
        int currentIndex = ((node.left == null) ? 0 : node.left.size) + 1;
        if (currentIndex <= pos) {
            Pair<Node<E>> splitted = split(node.right, pos - currentIndex);
            node.right = splitted.left;
            node.recalcSize();
            return new Pair<>(node, splitted.right);
        } else {
            Pair<Node<E>> splitted = split(node.left, pos);
            node.left = splitted.right;
            node.recalcSize();
            return new Pair<>(splitted.left, node);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if(root != null) {
            writeNode(root, sb);
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.append("]").toString();
    }

    private void writeNode(Node<E> node, StringBuilder sb) {
        if(node == null) return;
        writeNode(node.left, sb);
        sb.append(node.element).append(", ");
        writeNode(node.right, sb);
    }

    public void insert(E value) {
        root = ImplicitTreap.merge(root, new Node<>(value));
    }


    public void insert(int pos, E value) {
        if (pos < 0 || pos >= root.size) {
            throw new IllegalArgumentException();
        }
        Pair<Node<E>> splitted = split(this.root, pos);
        Node<E> newNode = new Node<>(value);
        root = merge(merge(splitted.left, newNode), splitted.right);
    }

    public E remove(int pos) {
        Pair<Node<E>> splitted = split(root, pos);
        Pair<Node<E>> extractRemoved = split(splitted.right, 1);
        root = merge(splitted.left, extractRemoved.right);
        return  extractRemoved.left.element;
    }

    public static class Pair<E> {
        public final E left;
        public final E right;

        public Pair(E left, E right) {
            this.left = left;
            this.right = right;
        }
    }

    private static final class Node<E> {
        private Node<E> left, right;
        private E element;
        private int size = 1;
        private int priority;
        private Node(E el) {
            element = el;
            priority = new Random().nextInt(1000);
        }

        private void recalcSize() {
            size = ((left != null) ? left.size : 0) + ((right != null) ? right.size : 0) + 1;
        }
    }
}
