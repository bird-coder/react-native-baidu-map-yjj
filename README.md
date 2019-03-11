# react-native-baidu-map-sdk
该项目基于[qiuxiang/react-native-baidumap-sdk](https://github.com/qiuxiang/react-native-baidumap-sdk.git)库修改，
修复了原库由于缺少minSdkVersion和targetSdkVersion导致的编译不通过问题，
并在原库基础上增加了坐标转化reserve接口的poi信息返回，增加了suggestion搜索建议

*注意：本项目环境为AndroidStudio-3.3.2，RN-5.8+，如编译遇到问题，请更新至最新版本

## 安装

安装方法可参考[qiuxiang/react-native-baidumap-sdk](https://github.com/qiuxiang/react-native-baidumap-sdk.git)库

### 特别注意

### Ios

```
platform :ios, '8.0'
此处改为9.0版本
target 'RNBaiduMap' do
此处填写您的项目名称
```

## 用法

### 基本用法
```javascript
import { MapView } from 'react-native-baidu-map-sdk'

render() {
  return <MapView center={{ latitude: 39.2, longitude: 112.4 }} />
}
```

### 显示卫星图
```javascript
<MapView satellite />
```

### 监听地图事件
```javascript
import { MapView } from 'react-native-baidu-map-sdk'

render() {
  return (
    <MapView
      onLoad={() => console.log('onLoad')}
      onClick={point => console.log(point)}
      onStatusChange={status => console.log(status)}
    />
  )
}
```

### 定位并关联定位图层
```javascript
import { MapView, Location } from 'react-native-baidu-map-sdk'

await Location.init()
Location.addLocationListener(location => this.setState({ location }))
Location.start()

state = { location: null }

render() {
  return <MapView location={this.state.location} locationEnabled />
}
```

### 添加标记
```javascript
<MapView>
  <MapView.Marker
    color="#2ecc71"
    title="This is a marker"
    onPress={this.onPress}
  />
</MapView>
```

### 添加自定义图片标记
```javascript
<MapView>
  <MapView.Marker
    title="This is a image marker"
    image="flag"
    coordinate={{ latitude: 39, longitude: 113 }}
  />
</MapView>
```

### 添加自定义 View 标记
```javascript
<MapView>
  <MapView.Marker
    icon={() => (
      <View>
        <Image source={image} />
        <Text>This is a custom marker</Text>
      </View>
    )}
  />
</MapView>
```

### 点聚合
```javascript
onStatusChange = status => this.cluster.update(status)

renderMarker = item => (
  <MapView.Marker
    key={item.extra.key}
    coordinate={item.coordinate}
  />
)

render() {
  return (
    <MapView onStatusChange={this.onStatusChange}>
      <MapView.Cluster
        ref={ref => this.cluster = ref}
        markers={this.markers}
        renderMarker={this.renderMarker}
      />
    </MapView>
  )
}
```

### 显示热力图

```javascript
points = [
  {
    latitude: 39,
    longitude: 113,
    intensity: 16,
  },
  ...
]

<MapView>
  <MapView.HeatMap
    points={this.points}
    radius={20}
    opacity={0.5}
  />
</MapView>
```

### 地理编码/逆地理编码
```javascript
import { Geocode } from 'react-native-baidu-map-sdk'

const searchResult = null
Geocode.search('海龙大厦').then((res) => {
    searchResult = res
})
const reverseResult = null
Geocode.reverse({ latitude: 39, longitude: 113 }).then((res) => {
    reverseResult = res
})
```

### 搜索建议
```javascript
import { Suggestion } from 'react-native-baidu-map-sdk'

const SuggestionResult = null
Suggestion.requestSuggestion({ latitude: 39, longitude: 113 }).then((res) => {
    SuggestionResult = res
})
```
