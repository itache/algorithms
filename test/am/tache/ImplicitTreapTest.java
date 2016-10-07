package test.am.tache;

import am.itache.algorithms.trees.ImplicitTreap;
import am.itache.algorithms.trees.ImplicitTreap.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static am.itache.algorithms.trees.ImplicitTreap.*;

public class ImplicitTreapTest {
    private ImplicitTreap<Integer> treap;

    @Before
    public void init() {
        treap = new ImplicitTreap<>();
        for(int i = 1;  i <= 8; i++){
            treap.insert(i);
        }
    }

    @Test
    public void buildTreapShouldBeCorrect() {
        Assert.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", treap.toString());
    }

    @Test
    public void shouldRemoveElements() {
        treap.remove(3);
        Assert.assertEquals("[1, 2, 3, 5, 6, 7, 8]", treap.toString());
        treap.remove(0);
        Assert.assertEquals("[2, 3, 5, 6, 7, 8]", treap.toString());
        treap.remove(5);
        Assert.assertEquals("[2, 3, 5, 6, 7]", treap.toString());
    }
}
