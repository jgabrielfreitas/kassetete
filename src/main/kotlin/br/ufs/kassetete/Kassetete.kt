package br.ufs.kassetete

import io.reactivex.Flowable
import io.reactivex.Observable

val expect: LifecycleHooking = LifecycleHooker()
val assert: ConditionalHooking = EvaluationHooker()

interface LifecycleHooking {

    infix fun <T : Any> that(target: Observable<T>): LifecycleAssertionsHandler<T>
    infix fun <T : Any> that(target: Flowable<T>): LifecycleAssertionsHandler<T>

}

class LifecycleHooker : LifecycleHooking {
    override fun <T : Any> that(target: Flowable<T>) = LifecycleAssertionsHandler(target.test())
    override fun <T : Any> that(target: Observable<T>) = LifecycleAssertionsHandler(target.test())
}

interface ConditionalHooking {

    infix fun <T : Any> that(target: Observable<T>): EvaluationBridge<T>
    infix fun <T : Any> that(target: Flowable<T>): EvaluationBridge<T>
}

class EvaluationHooker : ConditionalHooking {
    override fun <T : Any> that(target: Observable<T>) = EvaluationBridge(target.test())
    override fun <T : Any> that(target: Flowable<T>) = EvaluationBridge(target.test())
}