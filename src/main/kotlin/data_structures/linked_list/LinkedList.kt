package dev.nuculabs.dsa.data_structures.linked_list

/**
 * Represents a node in the linked list.
 */
class Node<T>(val value: T, var next: Node<T>? = null)

/**
 * Represents a generic linked list.
 */
class LinkedList<T> : Iterable<Node<T>> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size: Int = 0

    /**
     * Returns the first value from the list.
     */
    fun getFirst(): T? {
        return head?.value
    }

    /**
     * Returns the last value from the list.
     */
    fun getLast(): T? {
        return tail?.value
    }

    /**
     * Appends value at the end of the list.
     */
    fun append(value: T) {
        val newNode = Node(value, null)
        if (head == null) {
            head = newNode
            tail = newNode
        } else {
            tail!!.next = newNode
            tail = newNode
        }
        size += 1
    }

    /**
     * Returns from the list
     */
    fun get(position: Int): T? {
        var currentPosition = 0
        var currentNode = head
        while (currentPosition <= position && currentNode != null) {
            if (currentPosition == position) {
                return currentNode.value
            }
            currentNode = currentNode.next
            currentPosition += 1
        }
        return null
    }

    /**
     * Deletes an element from the linked list at the given position.
     */
    fun delete(position: Int) {
        if (size == 0 || position < 0 || position >= size) {
            return
        }
        // delete head
        if (position == 0) {
            head = head?.next
            // if the list size is 1 then we don't have elements anymore
            if (size == 1) {
                tail = null
            }
            size -= 1
            return
        }
        // delete anything from tail
        var currentNode = head
        var previousNode: Node<T>?
        var currentPosition = 0
        while (true) {
            previousNode = currentNode
            currentNode = currentNode?.next
            currentPosition += 1
            if (currentPosition == position) {
                previousNode!!.next = currentNode?.next
                if (currentPosition == size - 1) {
                    tail = previousNode
                }
                break
            }
        }
        size -= 1
    }

    /**
     * Coverts the Linked List to a Java List.
     */
    fun toJavaList(): List<T> {
        return this.toList().map { it.value }
    }

    /**
     * Returns the size of the Linked List.
     */
    fun size(): Int {
        return size
    }

    /**
     * NodeIterator that iterates over linked list nodes.
     */
    class NodeIterator<T>(linkedList: LinkedList<T>) : Iterator<Node<T>> {
        private var currentNode: Node<T>? = linkedList.head

        override fun hasNext(): Boolean {
            return currentNode != null
        }

        override fun next(): Node<T> {
            val node = currentNode!!
            currentNode = currentNode?.next
            return node
        }

    }

    /**
     * Returns a list iterator.
     */
    override fun iterator(): Iterator<Node<T>> {
        return NodeIterator(this)
    }
}
