package net.ccbluex.liquidbounce.utils

import net.ccbluex.liquidbounce.utils.RandomImgUtils.random
import kotlin.math.pow


object MathUtils {

    fun getRandom(min: Int, max: Int): Int {
        return if (max < min) {
            0
        } else min + random!!.nextInt(max - min + 1)
    }

    fun interpolate(oldValue: Double, newValue: Double, interpolationValue: Double): Double {
        return (oldValue + (newValue - oldValue) * interpolationValue)
    }

    fun interpolateInt(oldValue: Int, newValue: Int, interpolationValue: Float): Int {
        return interpolate(oldValue.toDouble(), newValue.toDouble(), interpolationValue.toDouble()).toInt()
    }
    fun lerp(a: Array<Double>, b: Array<Double>, t: Double) = arrayOf(a[0] + (b[0] - a[0]) * t, a[1] + (b[1] - a[1]) * t)

    fun distanceSq(a: Array<Double>, b: Array<Double>): Double = (a[0] - b[0]).pow(2) + (a[1] - b[1]).pow(2)

    @JvmOverloads
    @JvmStatic
    fun simplifyPoints(
        points: Array<Array<Double>>,
        epsilon: Double,
        start: Int = 0,
        end: Int = points.size,
        outPoints: MutableList<Array<Double>> = mutableListOf()
    ): Array<Array<Double>> {
        val s = points[start]
        val e = points[end - 1]
        var maxDistSq = 0.0
        var maxNdx = 1
        for (i in (start + 1) until (end - 1)) {
            val distSq = distanceToSegmentSq(points[i], s, e)
            if (distSq > maxDistSq) {
                maxDistSq = distSq
                maxNdx = i
            }
        }

        // if that point is too far
        if (Math.sqrt(maxDistSq) > epsilon) {
            // split
            simplifyPoints(points, epsilon, start, maxNdx + 1, outPoints)
            simplifyPoints(points, epsilon, maxNdx, end, outPoints)
        } else {
            // add the 2 end points
            outPoints.add(s)
            outPoints.add(e)
        }

        return outPoints.toTypedArray()
    }

    fun distanceToSegmentSq(p: Array<Double>, v: Array<Double>, w: Array<Double>): Double {
        val l2 = distanceSq(v, w)
        if (l2 == 0.0) {
            return distanceSq(p, v)
        }
        return distanceSq(p, lerp(v, w, (((p[0] - v[0]) * (w[0] - v[0]) + (p[1] - v[1]) * (w[1] - v[1])) / l2).coerceAtMost(1.0).coerceAtLeast(0.0)))
    }
    @JvmStatic
    fun calcCurvePoint(points: Array<Array<Double>>, t: Double): Array<Double> {
        val cpoints = mutableListOf<Array<Double>>()
        for (i in 0 until (points.size - 1)) {
            cpoints.add(lerp(points[i], points[i + 1], t))
        }
        return if (cpoints.size == 1) {
            cpoints[0]
        } else {
            calcCurvePoint(cpoints.toTypedArray(), t)
        }
    }
    @JvmStatic
    fun getPointsOnCurve(points: Array<Array<Double>>, num: Int): Array<Array<Double>> {
        val cpoints = mutableListOf<Array<Double>>()
        for (i in 0 until num) {
            val t = i / (num - 1.0)
            cpoints.add(calcCurvePoint(points, t))
        }
        return cpoints.toTypedArray()
    }
}
