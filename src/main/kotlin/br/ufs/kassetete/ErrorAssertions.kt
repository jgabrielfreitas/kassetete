package br.ufs.kassetete

import kotlin.reflect.KClass


class ErrorAssertions<T> internal constructor(private val bridge: EvaluationBridge<T>) {

    infix fun equalsTo(error: Throwable): EvaluationBridge<T> {
        bridge.consumer.assertError(error)
        return bridge
    }

    infix fun matching(predicate: (Throwable) -> Boolean): EvaluationBridge<T> {
        bridge.consumer.assertError(predicate)
        return bridge
    }

    infix fun ofType(klass: KClass<out Throwable>): EvaluationBridge<T> {
        bridge.consumer.assertError(klass.java)
        return bridge
    }

}