package data_structures.linked_list

import dev.nuculabs.dsa.data_structures.linked_list.LinkedList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertContentEquals


class LinkedListTest {

    @Test
    fun getFirst_EmptyList() {
        // Setup
        val linkedList = LinkedList<String>()

        // Test
        assertNull(linkedList.getFirst())
    }

    @Test
    fun getFirst() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        assertEquals("First", linkedList.getFirst())
    }

    @Test
    fun getLast_Empty() {
        // Setup
        val linkedList = LinkedList<String>()

        // Test
        assertNull(linkedList.getLast())
    }

    @Test
    fun getLast() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        assertEquals("Third", linkedList.getLast())
    }

    @Test
    fun get() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        assertEquals("First", linkedList.get(0))
        assertEquals("Second", linkedList.get(1))
        assertEquals("Third", linkedList.get(2))
        assertEquals(null, linkedList.get(3))
    }

    @Test
    operator fun iterator() {
        // Setup
        val iteratorOutput = arrayOfNulls<String>(3)
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        linkedList.forEachIndexed { index, it ->
            iteratorOutput[index] = it.value
        }

        // Assert
        assertContentEquals(arrayOf("First", "Second", "Third"), iteratorOutput)
    }

    @Test
    fun size() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        assertEquals(3, linkedList.size())
    }

    @Test
    fun deleteHead() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        linkedList.delete(0)

        // Assert
        assertEquals(listOf("Second", "Third"), linkedList.toJavaList())
        assertEquals("Second", linkedList.getFirst())
        assertEquals("Third", linkedList.getLast())

    }

    @Test
    fun deleteMiddle() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        linkedList.delete(1)

        // Assert
        assertEquals(listOf("First", "Third"), linkedList.toJavaList())
        assertEquals("First", linkedList.getFirst())
        assertEquals("Third", linkedList.getLast())

    }


    @Test
    fun deleteLast() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        linkedList.delete(2)

        // Assert
        assertEquals(listOf("First", "Second"), linkedList.toJavaList())
        assertEquals("First", linkedList.getFirst())
        assertEquals("Second", linkedList.getLast())
    }

    @Test
    fun toJavaList() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")

        // Test
        val result = linkedList.toJavaList()

        // Assert
        assertContentEquals(listOf("First", "Second", "Third"), result)
    }
}