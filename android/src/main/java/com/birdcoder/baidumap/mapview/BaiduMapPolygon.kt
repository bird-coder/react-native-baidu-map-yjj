package com.birdcoder.baidumap.mapview

import android.content.Context
import android.graphics.Color
import com.baidu.mapapi.map.Polygon
import com.baidu.mapapi.map.PolygonOptions
import com.baidu.mapapi.map.Stroke
import com.baidu.mapapi.model.LatLng
import com.birdcoder.baidumap.toPx
import com.facebook.react.views.view.ReactViewGroup

/**
 * Created by yujiajie on 2019/3/10.
 */

class BaiduMapPolygon(context: Context) : ReactViewGroup(context), BaiduMapOverlay {
    private var polygon: Polygon? = null
    private val options = PolygonOptions()

    var strokeColor: Int = Color.BLACK
        set(value) {
            field = value
            polygon?.let { it.stroke = Stroke(it.stroke.strokeWidth, value) }
        }

    var strokeWidth: Int = 1f.toPx()
        set(value) {
            field = value
            polygon?.let { it.stroke = Stroke(value, it.stroke.strokeWidth) }
        }

    fun setFillColor(color: Int) {
        options.fillColor(color)
        polygon?.fillColor = color
    }

    fun setPoints(points: List<LatLng>) {
        options.points(points)
        polygon?.points = points
    }

    override fun addTo(mapView: BaiduMapView) {
        options.stroke(Stroke(strokeWidth, strokeColor))
        polygon = mapView.map.addOverlay(options) as Polygon
    }

    override fun remove() {
        polygon?.remove()
    }
}