package br.ufs.kassetete

sealed class LifecycleEventSelector
object fail : LifecycleEventSelector()
object succeed : LifecycleEventSelector()
object complete : LifecycleEventSelector()
object notComplete : LifecycleEventSelector()
object terminate : LifecycleEventSelector()
object runForever : LifecycleEventSelector()
object receiveNoEvents : LifecycleEventSelector()

sealed class ObserverSynounyms
object destination : ObserverSynounyms()
object observer : ObserverSynounyms()
object subscriber : ObserverSynounyms()

sealed class ItemsReceivedSynounyms
object allItems : ItemsReceivedSynounyms()
object onNextEvents : ItemsReceivedSynounyms()
object emissions : ItemsReceivedSynounyms()

sealed class SequenceFailedSynounyms
object error : SequenceFailedSynounyms()
object exception : SequenceFailedSynounyms()
object throwable : SequenceFailedSynounyms()