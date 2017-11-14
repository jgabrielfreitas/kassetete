package br.ufs.kassetete

import io.reactivex.observers.BaseTestConsumer

class EvaluationBridge<T> internal constructor(internal val consumer: BaseTestConsumer<T, *>) {

    infix fun consumes(dsl: ItemsReceivedSynounyms) = receives(dsl)

    infix fun receives(dsl: ItemsReceivedSynounyms): ValuesAssertions<T> {

        val handler = ValuesAssertions(this)

        return when (dsl) {
            is allItems -> handler
            is onNextEvents -> handler
            is emissions -> handler
        }
    }

    infix fun breaksWith(dsl: SequenceFailedSynounyms) = failsWith(dsl)

    infix fun failsWith(dsl: SequenceFailedSynounyms): ErrorAssertions<T> {

        val handler = ErrorAssertions(this)

        return when (dsl) {
            is error -> handler
            is exception -> handler
            is throwable -> handler
        }
    }

    infix fun and(dsl: ObserverSynounyms): EvaluationBridge<T> =
            when (dsl) {
                is destination -> this
                is observer -> this
                is subscriber -> this
            }
}
