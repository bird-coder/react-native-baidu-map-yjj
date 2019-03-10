package com.birdcoder.baidumap.mapview

import android.view.View
import com.birdcoder.baidumap.toLatLng
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp

/**
 * Created by yujiajie on 2019/3/10.
 */

@Suppress("unused")
class BaiduMapMarkerManager : ViewGroupManager<BaiduMapMarker>() {
    override fun getName(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "BaiduMapMarker"
    }

    override fun createViewInstance(context: ThemedReactContext): BaiduMapMarker {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return BaiduMapMarker(context)
    }

    override fun addView(marker: BaiduMapMarker, view: View, index: Int) {
        super.addView(marker, view, index)
        when (view) {
            is BaiduMapCallout -> marker.setInfoWindow(view)
            else -> marker.setMarkerView(view)
        }
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any> {
        return MapBuilder.of(
                "onDrag", MapBuilder.of("registrationName", "onBaiduMapDrag"),
                "onDragStart", MapBuilder.of("registrationName", "onBaiduMapDragStart"),
                "onDragEnd", MapBuilder.of("registrationName", "onBaiduMapDragEnd"),
                "onPress", MapBuilder.of("registrationName", "onBaiduMapPress"),
                "onCalloutPress", MapBuilder.of("registrationName", "onBaiduMapCalloutPress")
        )
    }

    companion object {
        const val SELECT = 0
        const val UPDATE = 1
    }

    override fun getCommandsMap(): Map<String, Int> {
        return mapOf(
                "select" to SELECT,
                "update" to UPDATE
        )
    }

    override fun receiveCommand(marker: BaiduMapMarker, commandId: Int, args: ReadableArray?) {
        when (commandId) {
            SELECT -> marker.active = true
            UPDATE -> marker.updateMarkerView()
        }
    }

    @ReactProp(name = "coordinate")
    fun setCoordinate(view: BaiduMapMarker, coordinate: ReadableMap) {
        view.position = coordinate.toLatLng()
    }

    @ReactProp(name = "title")
    fun setTitle(view: BaiduMapMarker, title: String) {
        view.setTitle(title)
    }

    @ReactProp(name = "color", customType = "Color")
    fun setColor(view: BaiduMapMarker, color: Int) {
        view.setColor(color)
    }

    @ReactProp(name = "image")
    fun setImage(view: BaiduMapMarker, image: String) {
        view.setImage(image)
    }

    @ReactProp(name = "selected")
    fun setSelected(view: BaiduMapMarker, selected: Boolean) {
        view.active = selected
    }

    @ReactProp(name = "draggable")
    fun setDraggable(view: BaiduMapMarker, draggable: Boolean) {
        view.setDraggable(draggable)
    }

    @ReactProp(name = "flat")
    fun setFlat(view: BaiduMapMarker, flat: Boolean) {
        view.setFlat(flat)
    }
}