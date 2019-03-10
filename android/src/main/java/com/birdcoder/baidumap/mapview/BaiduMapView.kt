package com.birdcoder.baidumap.mapview

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.baidu.mapapi.map.*
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode
import com.baidu.mapapi.model.LatLng
import com.birdcoder.baidumap.toLatLng
import com.birdcoder.baidumap.toLatLngBounds
import com.birdcoder.baidumap.toPoint
import com.birdcoder.baidumap.toWritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.events.RCTEventEmitter

/**
 * Created by yujiajie on 2019/3/10.
 */

class BaiduMapView(context: Context) : FrameLayout(context) {
    private val emitter = (context as ThemedReactContext).getJSModule(RCTEventEmitter::class.java)
    private val markers = HashMap<String, BaiduMapMarker>()

    val mapView = MapView(context)
    val map: BaiduMap by lazy { mapView.map }

    var compassDisabled = false
        set(value) {
            field = value
            map.setCompassEnable(!value)
        }

    var paused = false
        set(value) {
            if (!field && value) {
                mapView.onPause()
                removeView(mapView)
            }

            if (field && !value) {
                addView(mapView)
                mapView.onResume()
            }

            field = value
        }

    init {
        mapView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        map.setMyLocationConfiguration(MyLocationConfiguration(LocationMode.NORMAL, true, null))
        super.addView(mapView)

        map.setOnMapLoadedCallback {
            emit(id, "onLoad")
            emitStatusChangeEvent(map.mapStatus)

            if (!compassDisabled) {
                map.setCompassEnable(false)
                map.setCompassEnable(true)
            }
        }

        map.setOnMapClickListener(object : BaiduMap.OnMapClickListener {
            override fun onMapPoiClick(poi: MapPoi): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val data = poi.position.toWritableMap()
                data.putString("name", poi.name)
                data.putString("uid", poi.uid)
                emit(id, "onClick", data)
                map.hideInfoWindow()
                return true
            }

            override fun onMapClick(latLng: LatLng) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                emit(id, "onClick", latLng.toWritableMap())
                map.hideInfoWindow()
            }
        })

        map.setOnMapDoubleClickListener { latLng ->
            emit(id, "onDoubleClick", latLng.toWritableMap())
        }

        map.setOnMapLongClickListener { latLng ->
            emit(id, "onLongClick", latLng.toWritableMap())
        }

        map.setOnMapStatusChangeListener(object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(status: MapStatus) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onMapStatusChangeStart(status: MapStatus, reason: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onMapStatusChange(status: MapStatus) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onMapStatusChangeFinish(status: MapStatus) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                emitStatusChangeEvent(status)
            }
        })

        map.setOnMarkerClickListener { marker ->
            val markerView = markers[marker.id]
            markerView?.active = true
            emit(markerView?.id, "onPress")
            true
        }

        map.setOnMarkerDragListener(object : BaiduMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(marker: Marker) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                emitDragEvent(marker, "onDragEnd")
            }

            override fun onMarkerDragStart(marker: Marker) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                map.hideInfoWindow()
                emitDragEvent(marker, "onDragStart")
            }

            override fun onMarkerDrag(marker: Marker) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                emitDragEvent(marker, "onDrag")
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            parent.requestDisallowInterceptTouchEvent(true)
        } else if (event.action == MotionEvent.ACTION_UP) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(event)
    }

    fun emit(id: Int?, name: String, data: WritableMap = Arguments.createMap()) {
        id?.let { emitter.receiveEvent(it, name, data) }
    }

    fun emitDragEvent(marker: Marker, event: String) {
        val markerView = markers[marker.id]
        markerView?.let {
            emit(it.id, event, it.position?.toWritableMap()!!)
        }
    }

    fun emitStatusChangeEvent(status: MapStatus) {
        val data = Arguments.createMap()
        data.putMap("center", status.target.toWritableMap())
        data.putMap("region", status.bound.toWritableMap())
        data.putDouble("zoomLevel", status.zoom.toDouble())
        data.putDouble("overlook", status.overlook.toDouble())
        data.putDouble("rotation", status.rotate.toDouble())
        emit(id, "onStatusChange", data)
    }

    fun setStatus(args: ReadableArray?) {
        val target = args!!.getMap(0)
        val duration = args.getInt(1)
        val mapStatusBuilder = MapStatus.Builder()

        if (target.hasKey("center")) {
            mapStatusBuilder.target(target.getMap("center").toLatLng())
        }

        if (target.hasKey("zoomLevel")) {
            mapStatusBuilder.zoom(target.getDouble("zoomLevel").toFloat())
        }

        if (target.hasKey("overlook")) {
            mapStatusBuilder.overlook(target.getDouble("overlook").toFloat())
        }

        if (target.hasKey("rotation")) {
            mapStatusBuilder.rotate(target.getDouble("rotation").toFloat())
        }

        if (target.hasKey("point")) {
            val point = target.getMap("point").toPoint()
            mapStatusBuilder.target(map.projection.fromScreenLocation(point))
        }

        if (target.hasKey("region")) {
            setStatus(MapStatusUpdateFactory.newLatLngBounds(
                    target.getMap("region").toLatLngBounds()), duration)
        }else {
            setStatus(MapStatusUpdateFactory.newMapStatus(mapStatusBuilder.build()), duration)
        }
    }

    private fun setStatus(statusUpdate: MapStatusUpdate, duration: Int) {
        if (duration == 0) {
            map.setMapStatus(statusUpdate)
        }else {
            map.animateMapStatus(statusUpdate, duration)
        }
    }

    fun add(view: View) {
        if (view is BaiduMapOverlay) {
            view.addTo(this)
            when (view) {
                is BaiduMapMarker -> markers[view.marker?.id!!] = view
            }
        }
    }

    fun remove(view: View) {
        if (view is BaiduMapOverlay) {
            view.remove()
            when (view) {
                is BaiduMapMarker -> markers.remove(view.marker?.id)
            }
        }
    }
}