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
class BaiduMapTextManager : SimpleViewManager<BaiduMapText>() {
    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "BaiduMapText"
    }

    override fun createViewInstance(context: ThemedReactContext): BaiduMapText {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return BaiduMapText(context)
    }

    @ReactProp(name = "coordinate")
    fun setCoordinate(text: BaiduMapText, coordinate: ReadableMap) {
        text.setCoodinate(coordinate.toLatLng())
    }

    @ReactProp(name = "content")
    fun setContent(text: BaiduMapText, content: String) {
        text.setContent(content)
    }

    @ReactProp(name = "fontSize")
    fun setFontSize(text: BaiduMapText, fontSize: Int) {
        text.setFontSize(fontSize.toFloat().toPx())
    }

    @ReactProp(name = "rotation")
    fun setRotate(text: BaiduMapText, rotation: Float) {
        text.setRotate(rotation)
    }

    @ReactProp(name = "color", customType = "Color")
    fun setColor(text: BaiduMapText, color: Int) {
        text.setColor(color)
    }

    @ReactProp(name = "backgroundColor", customType = "Color")
    fun setBgColor(text: BaiduMapText, color: Int) {
        text.setBgColor(color)
    }
}