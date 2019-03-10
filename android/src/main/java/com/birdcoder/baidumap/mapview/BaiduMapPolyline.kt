package com.birdcoder.baidumap.mapview

import android.content.Context
import com.baidu.mapapi.map.Polyline
import com.baidu.mapapi.map.PolylineOptions
import com.baidu.mapapi.model.LatLng
import com.facebook.react.views.view.ReactViewGroup

/**
 * Created by yujiajie on 2019/3/10.
 */

class BaiduMapPolyline(context: Context) : ReactViewGroup(context), BaiduMapOverlay {
    private var polyline: Polyline? = null
    private val options = PolylineOptions()

    fun setLineColor(color: Int) {
        options.color(color)
        polyline?.color = color
    }

    fun setLineWidth(width: Int) {
        options.width(width)
        polyline?.width = width
    }

    fun setPoints(points: List<LatLng>) {
        options.points(points)
        polyline?.points = points
    }

    override fun addTo(mapView: BaiduMapView) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        polyline = mapView.map.addOverlay(options) as Polyline
    }

    override fun remove() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        polyline?.remove()
    }
}