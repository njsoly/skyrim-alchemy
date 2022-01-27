package com.syntj.skyrim

object Timers {
    val times = mutableListOf<TimeElapsed>()

    fun timerStart(name: String? = null) : TimeElapsed {
        val newTimer = TimeElapsed(name, System.nanoTime() / 1000)
        times += newTimer
        return newTimer
    }
}

data class TimeElapsed(
    val name: String? = null,
    private var startMicros: Long,
    private var stopMicros: Long? = null
) {
    /** Stops the timer and returns the microseconds elapsed from [startMicros] to [stopMicros]. */
    fun stop() : Long {
        this.stopMicros = System.nanoTime() / 1000
        return elapsed()
    }

    fun elapsed() : Long {
        return if (stopMicros != null) {
            stopMicros!! - startMicros
        } else {
            (System.nanoTime() / 1000) - startMicros
        }
    }
}
