package com.birdcoder.baidumap.mapview

import com.birdcoder.baidumap.toLatLng
import com.birdcoder.baidumap.toPx
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

/**
 * Created by yujiajie on 2019/3/10.
 */

@Suppress("unused")
class BaiduMapCircleManager : SimpleViewManager<BaiduMapCircle>() {
    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "BaiduMapCircle"
    }

    override fun createViewInstance(context: ThemedReactContext): BaiduMapCircle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return BaiduMapCircle(context)
    }

    @ReactProp(name = "center")
    fun setCenter(circle: BaiduMapCircle, center: ReadableMap) {
        circle.setCenter(center.toLatLng())
    }

    @ReactProp(name = "radius")
    fun setRadius(circle: BaiduMapCircle, radius: Int) {
        circle.setRadius(radius)
    }

    @ReactProp(name = "fillColor", customType = "Color")
    fun setFillColor(circle: BaiduMapCircle, fillColor: Int) {
        circle.setFillColor(fillColor)
    }

    @ReactProp(name = "strokeColor", customType = "Color")
    fun setStrokeColor(circle: BaiduMapCircle, strokeColor: Int) {
        circle.strokeColor = strokeColor
    }

    @ReactProp(name = "strokeWidth")
    fun setStrokeWidth(circle: BaiduMapCircle, strokeWidth: Float) {
        circle.strokeWidth = strokeWidth.toPx()
    }
}