package com.birdcoder.baidumap.mapview

import com.birdcoder.baidumap.toLatLngList
import com.birdcoder.baidumap.toPx
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

/**
 * Created by yujiajie on 2019/3/10.
 */

@Suppress("unused")
class BaiduMapPolygonManager : SimpleViewManager<BaiduMapPolygon>() {
    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "BaiduMapPolygon"
    }

    override fun createViewInstance(context: ThemedReactContext): BaiduMapPolygon {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return BaiduMapPolygon(context)
    }

    @ReactProp(name = "points")
    fun setPoints(polygon: BaiduMapPolygon, points: ReadableArray) {
        polygon.setPoints(points.toLatLngList())
    }

    @ReactProp(name = "fillColor", customType = "Color")
    fun setFillColor(polygon: BaiduMapPolygon, fillColor: Int) {
        polygon.setFillColor(fillColor)
    }

    @ReactProp(name = "strokeColor", customType = "Color")
    fun setStrokeColor(polygon: BaiduMapPolygon, strokeColor: Int) {
        polygon.strokeColor = strokeColor
    }

    @ReactProp(name = "strokeWidth")
    fun setStrokeWidth(polygon: BaiduMapPolygon, strokeWidth: Float) {
        polygon.strokeWidth = strokeWidth.toPx()
    }
}