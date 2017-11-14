# Kassetete
> "DSLing for fun and profit"

## What
An experimental Kotlin DSL on top of RxJava2 testing APIs

## Why
It is just an exercise and a work-in-progress

## How

We just leverage some the awesome Kotlin features !!!

- Plain TestSubscriber / TestObserver way

```
val npe = NullPointerException("Cannot be null")
val broken = Flowable.error<Any>(npe)
    
broken.test().assertError(npe)
```

- Kassetete way

```
val npe = NullPointerException("Cannot be null")
val broken = Flowable.error<Any>(npe)
    
assert that broken failsWith exception equalsTo npe
```

Kassetete provides two DSL entry points : `expect` and `assert`.

- `expect` will offer DSLs over Flowable/Observable `lifecycle verifications`
- `assert` will offer DSLs over Flowable/Observable `values/errors verifications`

Some examples using `expect`

```
val frozen = Flowable.never<Any>()
expect that frozen should receiveNoEvents
```

```
val empty = Flowable.empty<Any>()
expect that empty should terminate
```

Some examples using `assert`

```
val target = Observable.just("A", "B", "C")
assert that target receives allItems matchingExactly listOf("A", "B", "C")
```

```
val error = IllegalAccessError("Forbidden")
val broken = Flowable.error<Any>(error)
        
assert that broken failsWith throwable matching {
    it.message!!.contentEquals("Forbidden")
}
```


Kassetete provides some synonyms in order to let us write the same statements with distinct words

```
val target = Flowable.just(1, 2, 3, 4)
	
assert that target receives emissions butNeverReceives 5
// or
assert that target consumes onNextEvents butNeverReceives 5
```

```
val flow = Flowable.error<Any>(IllegalArgumentException("Cannot be zero"))
	
assert that flow breaksWith error ofType IllegalArgumentException::class
// or
assert that flow failsWith exception ofType IllegalArgumentException::class
```

The values/errors related verifications are chainable using `and`

```
val target = Flowable.just(1, 2, 3, 4)

assert that
	target receives emissions matchingExactly listOf(1, 2, 3, 4) and
	destination consumes allItems butNeverReceives 5
```

You can shift from lifecycle DSL to values/errors DSL using `asWell`

```
val target = Observable.just("A", "B", "C")

expect that
	target should complete asWell
	observer consumes onNextEvents withTotalCount 3
```

## Does it worth ??

The DSL itself may offer cleaner and idiomatic constructions; but these are longer statements in general. 

It probably worths (by now) if you dont know so well the RxJava2 testing API semantics.


## Next steps
- Support for batch verifications with block DSL
- Cover all TestSubscriber / TestObserver APIs
- Improve tests and documentation

## Should I use it in production ??
- Probably no

## License

```
The MIT License (MIT)

Copyright (c) 2017 Ubiratan Soares

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

