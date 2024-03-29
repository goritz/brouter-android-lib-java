package com.routing.brouter

import java.io.File
import java.util.*

class RoutingParams private constructor(
    var baseDirectory: String,
    var lats: DoubleArray,
    var lons: DoubleArray,
    var bundledProfileFileName: String?,
    var customProfileFilePath: String?,
    val noGoLats: DoubleArray?,
    val noGoLons: DoubleArray?,
    val noGoRadis: DoubleArray?,
    var startDirection: Int,
    var turnInstructionMode: Int,
    var maxRunningTime: Long,
    var alternateIndex: Int,
    var validated: Boolean
) {
        private constructor(builder: Builder) : this(builder.baseDirectory,
            Util.buildDoubleArray(builder.fromLat, builder.toLat, builder.viaLats),
            Util.buildDoubleArray(builder.fromLon, builder.toLon, builder.viaLons),
            builder.bundledProfile.file, builder.customProfile,
            Util.buildDoubleArray(builder.noGoLats), Util.buildDoubleArray(builder.noGoLons),
            Util.buildDoubleArray(builder.noGoRadis),
            builder.startDirection, builder.turnInstructionMode.value, builder.maxRunningTime, builder.alternateIndex, false)

    internal fun validated() {
        validated = true
    }

    class Builder(val baseDirectory: String) {

        constructor(baseDirectory: File) : this(baseDirectory.toString())

        var fromLat = 0.0
        var fromLon = 0.0
        var toLat = 0.0
        var toLon = 0.0
        var bundledProfile = BundledProfile.CAR_FAST
        var customProfile: String? = null
        val viaLats: MutableList<Double> = ArrayList()
        val viaLons: MutableList<Double> = ArrayList()
        val noGoLats: MutableList<Double> = ArrayList()
        val noGoLons: MutableList<Double> = ArrayList()
        val noGoRadis: MutableList<Double> = ArrayList()
        var turnInstructionMode: TurnInstructionMode = TurnInstructionMode.NONE
        var startDirection = 0
        var maxRunningTime: Long = 60000
        var alternateIndex: Int = 0

        fun from(latitude: Double, longitude: Double) = apply { fromLat = latitude; fromLon = longitude }

        fun to(latitude: Double, longitude: Double) = apply { toLat = latitude; toLon = longitude }

        fun addVia(latitude: Double, longitude: Double) = apply { viaLats.add(latitude); viaLons.add(longitude) }

        fun addNoGo(latitude: Double, longitude: Double, radius: Double) = apply { noGoLats.add(latitude); noGoLons.add(longitude); noGoRadis.add(radius) }

        fun profile(profile: BundledProfile) = apply { this.bundledProfile = profile }

        fun customProfile(customProfile: String?) = apply { this.customProfile = customProfile }

        fun startDirection(startDirection: Int) = apply { this.startDirection = startDirection }

        fun turnInstructions(turnInstructionMode: TurnInstructionMode) = apply { this.turnInstructionMode = turnInstructionMode }

        fun maxRunningTime(maxRunningTime: Long) = apply { this.maxRunningTime = maxRunningTime }

        fun alternateIndex(alternateIndex: Int) = apply { this.alternateIndex = alternateIndex }

        fun build() = RoutingParams(this)
    }
}
