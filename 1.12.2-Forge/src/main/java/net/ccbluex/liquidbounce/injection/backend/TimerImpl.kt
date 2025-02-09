/*
 * Terra Hacked Client
 */

package net.ccbluex.liquidbounce.injection.backend

import net.ccbluex.liquidbounce.api.minecraft.util.ITimer
import net.ccbluex.liquidbounce.injection.implementations.IMixinTimer
import net.minecraft.util.Timer

class TimerImpl(val wrapped: Timer) : ITimer {
    override var timerSpeed: Float
        get() = (wrapped as IMixinTimer).timerSpeed
        set(value) {
            (wrapped as IMixinTimer).timerSpeed = value
        }
    override val renderPartialTicks: Float
        get() = wrapped.renderPartialTicks

    override fun equals(other: Any?): Boolean {
        return other is TimerImpl && other.wrapped == this.wrapped
    }
}

inline fun ITimer.unwrap(): Timer = (this as TimerImpl).wrapped
inline fun Timer.wrap(): ITimer = TimerImpl(this)