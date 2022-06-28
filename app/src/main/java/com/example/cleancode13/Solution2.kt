package com.example.cleancode13


import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun massiveRun2(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines 
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

val counterContext = newSingleThreadContext("counterXContext")
var counterX = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun2 {
            // confine each increment to a single-threaded context
            withContext(counterContext) {
                counterX++
            }
        }
    }
    println("counterX = $counterX")
}