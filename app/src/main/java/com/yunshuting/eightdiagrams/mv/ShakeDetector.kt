package com.yunshuting.eightdiagrams.mv


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class ShakeDetector(private val listener: OnShakeListener) : SensorEventListener {
    private var lastUpdate: Long = 0
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f
        private const val SHAKE_SLOP_TIME_MS = 500
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 不需要实现
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val currentTime = System.currentTimeMillis()
            val diffTime = currentTime - lastUpdate
            if (diffTime < SHAKE_SLOP_TIME_MS)
                return

            lastUpdate = currentTime

            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            val speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

            if (speed > SHAKE_THRESHOLD_GRAVITY) {
                listener.onShake()
            }

            lastX = x
            lastY = y
            lastZ = z
        }
    }

    interface OnShakeListener {
        fun onShake()
    }
}