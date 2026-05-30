package com.alexeycode.jsonmasker

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole

open class JsonMaskerBenchmark {
    @State(Scope.Thread)
    open class SmallJsonState {
        val json = """
            {
                "hello": "world"
            }
        """.trimIndent()
        val fields = setOf("hello")
        val mask = "#"
    }

    @State(Scope.Thread)
    open class LargeJsonState {
        val json = """
            {
                "user": {
                    "name": "Alex",
                    "password": "secret"
                },
                "creditCard": {
                    "number": "1234"
                },
                "items": [
                    {
                      "password": "qwerty"
                    }
                ],
                "user2": {
                    "name": "Alex",
                    "password": "secret"
                },
                "creditCard2": {
                    "number": "1234"
                },
                "items2": [
                    {
                      "password": "qwerty"
                    }
                ],
                "user3": {
                    "name": "Alex",
                    "password": "secret"
                },
                "creditCard3": {
                    "number": "1234"
                },
                "items3": [
                    {
                      "password": "qwerty"
                    }
                ],
                "user4": {
                    "name": "Alex",
                    "password": "secret"
                },
                "creditCard4": {
                    "number": "1234"
                },
                "items4": [
                    {
                      "password": "qwerty"
                    }
                ]
            }
        """.trimIndent()
        val fields = setOf("password", "creditCard", "creditCard2", "creditCard3", "creditCard4")
        val mask = "#"
    }

    @Benchmark
    fun maskSmallGson(state: SmallJsonState, blackhole: Blackhole) {
        val masked = maskGson(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskSmallJackson(state: SmallJsonState, blackhole: Blackhole) {
        val masked = maskJackson(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskSmallKotlinx(state: SmallJsonState, blackhole: Blackhole) {
        val masked = maskKotlinx(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskSmallCustom(state: SmallJsonState, blackhole: Blackhole) {
        val masked = mask(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskLargeGson(state: LargeJsonState, blackhole: Blackhole) {
        val masked = maskGson(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskLargeJackson(state: LargeJsonState, blackhole: Blackhole) {
        val masked = maskJackson(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskLargeKotlinx(state: LargeJsonState, blackhole: Blackhole) {
        val masked = maskKotlinx(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }

    @Benchmark
    fun maskLargeCustom(state: LargeJsonState, blackhole: Blackhole) {
        val masked = mask(state.json, state.fields, state.mask)
        blackhole.consume(masked)
    }
}