package br.ufs.kassetete

import io.reactivex.observers.BaseTestConsumer

class LifecycleAssertionsHandler<T> internal constructor(
        private val consumer: BaseTestConsumer<T, *>) {

    infix fun should(selector: LifecycleEventSelector): LifecycleAssertionsHandler<T> {

        when (selector) {
            is fail -> consumer.assertError { true }
            is succeed -> consumer.assertNoErrors()
            is complete -> consumer.assertComplete()
            is notComplete -> consumer.assertNotComplete()
            is terminate -> consumer.assertTerminated()
            is runForever -> consumer.assertNotTerminated()
            is receiveNoEvents -> consumer.assertEmpty()
        }

        return this
    }

    infix fun asWell(dsl: ObserverSynounyms): EvaluationBridge<T> {

        val handler = EvaluationBridge(consumer)

        return when (dsl) {
            is destination -> handler
            is observer -> handler
            is subscriber -> handler
        }
    }
}