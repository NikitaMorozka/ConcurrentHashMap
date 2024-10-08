import org.junit.Test;

import static org.junit.Assert.*;

public class ConcurentHashMapTest {


    @Test
    public void testPutAndGet() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        for (int i = 0; i < 100; i++) {
            assertNull(map.put("key" + i, i));
        }
        assertEquals(100, map.size());
        assertEquals((Integer) 1,map.get("key1"));
    }
    @Test
    public void testSizeAndRemove() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        for (int i = 0; i < 100; i++) {
            map.put("key" + i, i);
        }
        assertEquals(100, map.size());
        for (int i = 0; i < 5; i++) {
            map.remove("key" + i);
        }
        assertEquals(95,map.size());
    }


    @Test
    public void testConcurrentPutAndGet() throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(100);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                map.put("key" + i, i);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                map.get("key" + i);
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(100, map.size());

    }
    @Test
    public void testPutWithNullKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        assertThrows(IllegalArgumentException.class, () -> map.put(null, 1));
    }

    @Test
    public void testRemoveWithNullKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        assertThrows(IllegalArgumentException.class, () -> map.remove(null));
    }

    @Test
    public void testGetWithNullKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
    }

    @Test
    public void testClear() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        for (int i = 0; i < 100; i++) {
            map.put("key" + i, i);
        }
        assertEquals(100, map.size());
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void testContainsKey() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        map.put("Проверка", 1);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("Проверка"));
        assertFalse(map.containsKey("Проверкфа"));

    }

    @Test
    public void testContainsValue() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        map.put("Проверка", 101);
        assertEquals(1, map.size());
        assertTrue(map.containsValue(101));
        assertFalse(map.containsValue(102));
    }

    @Test
    public void testReplace() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(10);
        map.put("key1", 200);
        assertEquals((Integer) 200, map.get("key1"));
        map.replace("key1", 5467);
        assertEquals((Integer) 5467, map.get("key1"));
    }



}