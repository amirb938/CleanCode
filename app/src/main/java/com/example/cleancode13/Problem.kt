package com.example.cleancode13

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

var counter = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun {
            counter++
        }
    }
    println("Counter = $counter")
}

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
//                        println("Name : ${Thread.currentThread().name}")
                    }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}