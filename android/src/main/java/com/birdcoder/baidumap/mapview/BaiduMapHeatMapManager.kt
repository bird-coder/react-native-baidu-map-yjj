package com.birdcoder.baidumap.mapview

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

/**
 * Created by yujiajie on 2019/3/10.
 */

@Suppress("unused")
class BaiduMapHeatMapManager : SimpleViewManager<BaiduMapHeatMap>() {
    override fun getName(): String {
        return "BaiduMapHeatMap"
    }

    override fun createViewInstance(context: ThemedReactContext): BaiduMapHeatMap {
        return BaiduMapHeatMap(context)
    }

    @ReactProp(name = "points")
    fun setPoints(heatMap: BaiduMapHeatMap, points: ReadableArray) {
        heatMap.setPoints(points)
    }

    @ReactProp(name = "radius")
    fun setRadius(heatMap: BaiduMapHeatMap, radius: Int) {
        heatMap.radius = radius
    }

    @ReactProp(name = "opacity")
    fun setOpacity(heatMap: BaiduMapHeatMap, opacity: Double) {
        heatMap.opacity = opacity
    }
}