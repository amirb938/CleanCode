package com.example.cleancode13

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis


suspend fun massiveRun04(action: suspend () -> Unit) {
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

val mutex = Mutex()
var counter04 = 0

fun main() = runBlocking {
    withContext(Dispatchers.Default) {
        massiveRun04 {
            // protect each increment with lock
            mutex.withLock {
                counter04++
            }
        }
    }
    println("counter04 = $counter04")
}