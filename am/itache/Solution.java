package am.itache;

import java.util.Scanner;

import am.itache.algorithms.trees.ImplicitTreap;
import am.itache.algorithms.trees.ImplicitTreap.Pair;

import static am.itache.algorithms.trees.ImplicitTreap.merge;
import static am.itache.algorithms.trees.ImplicitTreap.split;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        ImplicitTreap<String> treap = fillTreap(scanner.nextLine().split(" "));
        while(scanner.hasNext()) {
            performOperation(scanner.nextLine().split(" "), treap);
        }
        System.out.println(treap);

    }

    private static void performOperation(String[] args, ImplicitTreap<String> treap) {
        int from = Integer.parseInt(args[1]) - 1;
        int to = Integer.parseInt(args[2]) - 1;
        Pair<ImplicitTreap<String>> splitted = split(treap, from);
        Pair<ImplicitTreap<String>> splittedRight = split(treap, to - from + 1);
        Operation operation = (args[0].equals("1")) ? Operation.ADD_TO_FRONT : Operation.ADD_TO_BACK;
        operation.perform(splitted.left, splittedRight.left, splittedRight.right);
    }

    private static ImplicitTreap<String> fillTreap(String[] numbers) {
        ImplicitTreap<String> treap = new ImplicitTreap<>();
        for(String i : numbers) {
            treap.insert(i);
        }
        return treap;
    }

    enum Operation {
        ADD_TO_FRONT {
            @Override
            public ImplicitTreap perform(ImplicitTreap start, ImplicitTreap middle, ImplicitTreap end) {
                return merge(merge(middle, start), end);
            }
        },
        ADD_TO_BACK {
            @Override
            public ImplicitTreap perform(ImplicitTreap start, ImplicitTreap middle, ImplicitTreap end) {
                return merge(merge(start, end), middle);
            }
        };

        abstract public ImplicitTreap perform(ImplicitTreap start, ImplicitTreap middle, ImplicitTreap end);

    }
}
