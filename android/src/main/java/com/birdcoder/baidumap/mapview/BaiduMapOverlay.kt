package com.birdcoder.baidumap.mapview

/**
 * Created by yujiajie on 2019/3/10.
 */

interface BaiduMapOverlay {
    fun addTo(mapView: BaiduMapView)
    fun remove()
}