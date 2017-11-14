package br.ufs.kassetete.tests

import br.ufs.kassetete.*
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import java.util.concurrent.TimeUnit

class KasseteteTests {

    @Test fun `check for successful completion`() {
        val target = Observable.just("A", "B", "C")
        expect that target should complete
    }

    @Test fun `check for error signaling`() {
        val broken = Flowable.error<Any>(IllegalArgumentException("Invalid"))
        expect that broken should fail
    }

    @Test fun `check for no events`() {
        val frozen = Flowable.never<Any>()
        expect that frozen should receiveNoEvents
    }

    @Test fun `check for successfull termination`() {
        val empty = Flowable.empty<Any>()
        expect that empty should terminate
    }

    @Test fun `check for neverending sequence`() {
        val neverending = Observable.timer(1, TimeUnit.SECONDS)
        expect that neverending should runForever
    }

    @Test fun `check for receives single emission`() {
        val onlyOneItem = Observable.just("A")
        assert that onlyOneItem receives emissions onlyItem "A"
    }

    @Test fun `check for receives all with given order`() {
        val target = Observable.just("A", "B", "C")
        assert that target receives allItems matchingExactly listOf("A", "B", "C")
    }

    @Test fun `check for never receiving some item`() {
        val target = Flowable.just(1, 2, 3, 4)
        assert that target receives emissions butNeverReceives 5
    }

    @Test fun `check for error with given throwable`() {
        val npe = NullPointerException("Cannot be null")
        val broken = Flowable.error<Any>(npe)

        assert that broken failsWith exception equalsTo npe
    }

    @Test fun `check for error with given predicate`() {
        val error = IllegalAccessError("Forbidden")
        val broken = Flowable.error<Any>(error)

        assert that broken failsWith throwable matching {
            it.message!!.contentEquals("Forbidden")
        }
    }

    @Test fun `check for error of given type`() {
        val flow = Flowable.error<Any>(IllegalArgumentException("Cannot be zero"))
        assert that flow breaksWith error ofType IllegalArgumentException::class
    }

    @Test fun `chain assertions for values using AND`() {
        val target = Flowable.just(1, 2, 3, 4)

        assert that
                target receives emissions matchingExactly listOf(1, 2, 3, 4) and
                destination consumes allItems butNeverReceives 5
    }

    @Test fun `chain assertions from lifecycle to values using ASWELL`() {
        val target = Observable.just("A", "B", "C")

        expect that
                target should complete asWell
                observer consumes onNextEvents withTotalCount 3
    }

}