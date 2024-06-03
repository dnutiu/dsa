package dev.nuculabs.dsa.ranking.bm25

import java.lang.Double.isFinite
import java.util.HashMap
import kotlin.math.log10

/**
 * Document models a simple document which contains a numeric id and text.
 */
data class Document(val id: Int, val text: String)

/**
 * TokenizedDocument models a document which is tokenized using a simple strategy tokenization strategy.
 */
data class TokenizedDocument(val document: Document, private val text: String) {
    private var tokens: List<String> = document.text.split(" ").map { token ->
        token.filter { it.isLetterOrDigit() }.lowercase()
    }.filter {
        it.isNotEmpty()
    }

    fun getTokens(): List<String> {
        return tokens
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TokenizedDocument

        return document == other.document
    }

    override fun hashCode(): Int {
        return document.hashCode()
    }

}

/**
 * BM25+ is a variation of the BM25 ranking algorithm.
 *
 * The algorithm is implemented using the following paper as a reference.
 * http://www.cs.otago.ac.nz/homepages/andrew/papers/2014-2.pdf
 */
class Bm25Plus {
    /**
     * The storage holds a mapping of document id -> document.
     */
    private var storage: MutableMap<Int, TokenizedDocument> = HashMap()

    /**
     * The term frequency index holds a mapping of term -> list of documents in which the term occurs.
     */
    private var termFrequencyIndex: MutableMap<String, HashSet<Int>> = HashMap()

    /**
     * The tuning parameters are used to tune the result of the algorithm.
     *
     * These values were taken directly from the paper.
     */
    private var tuningParameterB: Double = 0.3
    private var tuningParameterK1: Double = 1.6
    private var tuningParameterDelta: Double = 0.7

    private var totalTokens: Int = 0
    private var meanDocumentLengths: Double = 0.0

    /**
     * Returns the number of indexed documents.
     */
    fun indexSize(): Int {
        return storage.size
    }

    /**
     * Indexes a document.
     */
    fun index(document: Document) {
        // Tokenize the document, for educational purposes and simplicity we will consider tokens only
        // the words delimited by a space and transform them into lowercase.
        val tokenizedDocument = TokenizedDocument(document, document.text)

        // Document does not exist in index
        if (!storage.containsKey(document.id)) {
            storage[document.id] = tokenizedDocument

            totalTokens += tokenizedDocument.getTokens().size
            meanDocumentLengths = (totalTokens / storage.size).toDouble()

            // Index all tokens
            tokenizedDocument.getTokens().forEach {
                if (termFrequencyIndex.containsKey(it)) {
                    termFrequencyIndex[it]?.add(document.id)
                } else {
                    termFrequencyIndex[it] = HashSet()
                    termFrequencyIndex[it]?.add(document.id)
                }
            }
        }
    }

    /**
     * Indexes all the documents.
     */
    fun indexAll(vararg documents: Document) {
        documents.forEach {
            index(it)
        }
    }

    /**
     * Queries documents using the given term and returns a list of documents which contain the term ordered by
     * relevance.
     *
     */
    fun termQuery(term: String): List<Pair<Double, Document>> {
        val documentIds = termFrequencyIndex[term.lowercase()] ?: return emptyList()
        // Compute the RSV for each document.
        return documentIds.map {
            val document = storage[it] ?: return@map null
            val documentRsv = computeRsv(term.lowercase(), document)
            return@map documentRsv to document.document
            // Sort results by highest score and filter out Infinity scores, which mean that the term does not exist.
        }.filterNotNull().filter { isFinite(it.first) }.sortedByDescending { it.first }
    }

    /**
     * Queries documents using the given terms and returns a list of documents which contain the terms ordered by
     * relevance.
     *
     */
    fun termsQuery(vararg terms: String): List<Pair<Double, Document>> {

        val documentIds = terms.map { term ->
            Pair(term, termFrequencyIndex[term.lowercase()] ?: mutableSetOf())
        }.reduce { acc, pair ->
            // add all documents which contain them terms to the documents set.
            acc.second.addAll(pair.second)
            // return
            acc
        }.second

        // Compute the terms RSV sum for each document.
        return documentIds.map {
            val document = storage[it] ?: return@map null
            val documentRsv: Double = terms.sumOf { term -> computeRsv(term.lowercase(), document) }
            return@map documentRsv to document.document
            // Sort results by highest score and filter out Infinity scores, which mean that the term does not exist.
        }.filterNotNull().filter { isFinite(it.first) }.sortedByDescending { it.first }
    }

    /**
     * Computes the inverse document frequency for a given term.
     *
     * The IDF is defined as the total number of documents (N) divided by the documents that contain the term (dft).
     * In the BM25+ version the IDF is the (N+1)/(dft)
     */
    private fun computeInverseDocumentFrequency(term: String): Double {
        val numberOfDocumentsContainingTheTerm = termFrequencyIndex[term]?.size ?: 0
        return (storage.size + 1) / numberOfDocumentsContainingTheTerm.toDouble()
    }

    /**
     * Computes the RSV for the given term and document.
     * The RSV (Retrieval Status Value) is computed for every document using the BM25+ formula from the paper.
     */
    private fun computeRsv(
        term: String,
        document: TokenizedDocument
    ): Double {
        val inverseDocumentFrequencyLog: Double = log10(computeInverseDocumentFrequency(term.lowercase()))
        val termOccurringInDocumentFrequency: Double =
            document.getTokens().filter { token -> token == term.lowercase() }.size.toDouble()
        val documentLength: Double = document.getTokens().size.toDouble()

        val score =
            inverseDocumentFrequencyLog *
                    (
                            ((tuningParameterK1 + 1) * termOccurringInDocumentFrequency) /
                                    ((tuningParameterK1 * ((1 - tuningParameterB) + tuningParameterB * (documentLength / meanDocumentLengths))) + termOccurringInDocumentFrequency)
                                    + tuningParameterDelta
                            )
        return score
    }
}
