package br.ufs.kassetete

class ValuesAssertions<T> internal constructor(private val parent: EvaluationBridge<T>) {

    infix fun onlyItem(item: T): EvaluationBridge<T> {
        parent.consumer.assertValue(item)
        return parent
    }

    infix fun butNeverReceives(item: T): EvaluationBridge<T> {
        parent.consumer.assertNever(item)
        return parent
    }

    infix fun matchingExactly(items: Iterable<T>): EvaluationBridge<T> {
        parent.consumer.assertValueSequence(items)
        return parent
    }

    infix fun withTotalCount(total: Int): EvaluationBridge<T> {
        parent.consumer.assertValueCount(total)
        return parent
    }

}