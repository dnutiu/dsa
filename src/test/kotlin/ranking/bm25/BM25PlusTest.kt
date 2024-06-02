package ranking.bm25

import dev.nuculabs.dsa.ranking.bm25.Bm25Plus
import dev.nuculabs.dsa.ranking.bm25.Document
import kotlin.test.Test
import kotlin.test.assertEquals

class BM25PlusTest {
    @Test
    fun test_index_and_indexSize() {
        // Setup
        val bm25Plus = Bm25Plus()

        val document1 = Document(1, "Ana are mere")
        val document2 = Document(2, "Ana Ana Ana Ana Ana Ana Ana Ana")

        // Test
        bm25Plus.indexAll(document1, document2)

        // Assert
        assertEquals(2, bm25Plus.indexSize())
    }

    @Test
    fun test_termQuery() {
        // Given
        val bm25Plus = Bm25Plus()

        val document1 = Document(1, "Ana are mere")
        val document2 = Document(2, "Ana Ana Ana Ana Ana Ana Ana Ana")

        // Then
        bm25Plus.index(document1)
        bm25Plus.index(document2)

        assertEquals(
            listOf(0.4936823874431607 to document2, 0.3133956394555762 to document1),
            bm25Plus.termQuery("Ana")
        )
        assertEquals(listOf(0.8491490237651933 to document1), bm25Plus.termQuery("mere"))
        assertEquals(listOf(), bm25Plus.termQuery("batman"))
        assertEquals(
            listOf(0.4936823874431607 to document2, 0.3133956394555762 to document1),
            bm25Plus.termQuery("ana")
        )
    }

    @Test
    fun test_termsQuery() {
        // Setup
        val bm25Plus = Bm25Plus()

        val document1 = Document(
            1,
            "A linked list is a fundamental data structure which consists of Nodes that are connected to each other."
        )
        val document2 =
            Document(2, "The Linked List data structure permits the storage of data in an efficient manner.")
        val document3 =
            Document(3, "The space and time complexity of the linked list operations depends on the implementation.")
        val document4 = Document(
            4,
            "The operations that take O(N) time takes this much because you have to traverse the list’s for at least N nodes in order to perform it successfully. On the other hand, operations that take O(1) time do not require any traversals because the list holds pointers to the head first Node and tail last Node."
        )

        bm25Plus.indexAll(document1, document2, document3, document4)

        // Test
        val results = bm25Plus.termsQuery("linked", "list", "complexity")

        // Assert
        assertEquals(1.5966769323799244 to document3, results.first())
    }
}