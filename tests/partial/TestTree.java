package partial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import partial.Tree;

public class TestTree {

    @Test
    public void testTreeListOfT() {
        List<Integer> array = new ArrayList<Integer>();
        for (Integer i = 1; i <= 10; i++) {
            array.add(i);
        }
        assertEquals((Integer) 55, new Tree<Integer>(array).root.value);
    }

    @Test
    public void testCount() {
        List<Integer> array = new ArrayList<Integer>();
        for (Integer i = 1; i <= 17; i++) {
            array.add(i);
        }
        assertEquals(21, new Tree<Integer>(array, 4).getCount());
    }
    
    @Test
    public void testQuery() {
        List<Integer> array = new ArrayList<Integer>();
        for (Integer i = 2; i <= 24; i+=2) {
            array.add(i);
        }
        int[] expectedQuery = {8, 52, 84, 26, 28};
        assertEquals(21, new Tree<Integer>(array, 4).getQuery(8, 29));        
    }

}
