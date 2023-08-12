package mayton.gis;

import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRadix {

    static String TEST_IP = "01010101000000000000000011100110";

    static ConcurrentRadixTree<String> radixTree;

    @BeforeAll
    static void init() {
        radixTree = new ConcurrentRadixTree<>(new DefaultCharArrayNodeFactory());
        radixTree.put("010101010000000000000000", "*");
    }

    @Test
    @Disabled
    void test_getValuesForKeysStartingWith() {
        Iterable<String> res = radixTree.getValuesForKeysStartingWith(TEST_IP);
        Iterator<String> it = res.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.next(), "*");
    }

    @Test
    void test_getValuesForClosestKeys() {
        Iterable<String> res = radixTree.getValuesForClosestKeys(TEST_IP);
        Iterator<String> it = res.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.next(), "*");
    }

}
