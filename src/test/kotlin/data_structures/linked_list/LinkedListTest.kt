package data_structures.linked_list

import dev.nuculabs.dsa.data_structures.linked_list.LinkedList
import dev.nuculabs.dsa.data_structures.linked_list.emptyList
import dev.nuculabs.dsa.data_structures.linked_list.listOf
import dev.nuculabs.dsa.data_structures.linked_list.toArray
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
    fun deleteInvalidPosition() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")

        // Test
        assertThrows(IllegalArgumentException::class.java) {
            linkedList.delete(-1)
        }
        assertThrows(IllegalArgumentException::class.java) {
            linkedList.delete(99)
        }
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

    @Test
    fun insertFirst() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("Second")

        // Test
        linkedList.insert("First", 0)

        // Assert
        assertContentEquals(listOf("First", "Second"), linkedList.toJavaList())
        assertEquals("First", linkedList.getFirst())
        assertEquals("Second", linkedList.getLast())
    }

    @Test
    fun insertLast() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")

        // Test
        linkedList.insert("Second", linkedList.size())

        // Assert
        assertContentEquals(listOf("First", "Second"), linkedList.toJavaList())
        assertEquals("First", linkedList.getFirst())
        assertEquals("Second", linkedList.getLast())
    }

    @Test
    fun insertMiddle() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")

        // Test
        linkedList.insert("Middle", linkedList.size() - 1)

        // Assert
        assertContentEquals(listOf("First", "Middle", "Second"), linkedList.toJavaList())
        assertEquals("First", linkedList.getFirst())
        assertEquals("Second", linkedList.getLast())
    }

    @Test
    fun reverseOneElement() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")

        // Test
        linkedList.reverse()

        // Assert
        assertContentEquals(listOf("First"), linkedList.toJavaList())
    }

    @Test
    fun reverseTwoElements() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")

        // Test
        linkedList.reverse()

        // Assert
        assertContentEquals(listOf("Second", "First"), linkedList.toJavaList())
        assertEquals("Second", linkedList.getFirst())
        assertEquals("First", linkedList.getLast())
    }

    @Test
    fun reverseFiveElements() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")
        linkedList.append("Third")
        linkedList.append("Fourth")
        linkedList.append("Fifth")


        // Test
        linkedList.reverse()

        // Assert
        assertContentEquals(listOf("Fifth", "Fourth", "Third", "Second", "First"), linkedList.toJavaList())
        assertEquals("Fifth", linkedList.getFirst())
        assertEquals("First", linkedList.getLast())
    }

    @Test
    fun toArray() {
        // Setup
        val linkedList = LinkedList<String>()
        linkedList.append("First")
        linkedList.append("Second")

        // Test
        val result = linkedList.toArray()

        // Assert
        assertContentEquals(arrayOf("First", "Second"), result)
    }

    @Test
    fun emptyList() {
        // Test
        val list = LinkedList.emptyList<String>()
        // Assert
        assertEquals(0, list.size())
    }

    @Test
    fun listOf() {
        // Test
        val list = LinkedList.listOf("One", "Two", "Three")
        // Assert
        assertEquals(3, list.size())
        assertContentEquals(listOf("One", "Two", "Three"), list.toJavaList())
    }
}