# react-native-baidu-map-sdk
该项目基于[qiuxiang/react-native-baidumap-sdk](https://github.com/qiuxiang/react-native-baidumap-sdk.git)库修改，
修复了原库由于缺少minSdkVersion和targetSdkVersion导致的编译不通过问题，
并在原库基础上增加了坐标转化reserve接口的poi信息返回，增加了suggestion搜索建议

*注意：本项目环境为AndroidStudio-3.3.2，RN-5.8+，如编译遇到问题，请更新至最新版本

## 安装
安装方法可参考[qiuxiang/react-native-baidumap-sdk](https://github.com/qiuxiang/react-native-baidumap-sdk.git)库

## 引入项目
```
npm i react-native-baidu-map-sdk
```
### 或
```
yarn add react-native-baidu-map-sdk
```

## 配置
### Android
```
react-native link react-native-baidu-map-sdk
获取 Android 开发密钥， 在 AndroidManifest 中添加：
<application>
    <meta-data
      android:name="com.baidu.lbsapi.API_KEY"
      android:value="开发密钥" />
</application>
```

### IOS
```
使用 cocoapods 配置
在 ios 目录下新建文件 Podfile：
platform :ios, '9.0'

# The target name is most likely the name of your project.
target '您的项目名称' do
  # Your 'node_modules' directory is probably in the root of your project,
  # but if not, adjust the `:path` accordingly
  pod 'React', :path => '../node_modules/react-native', :subspecs => [
    'Core',
    'CxxBridge', # Include this for RN >= 0.47
    'DevSupport', # Include this to enable In-App Devmenu if RN >= 0.43
    'RCTText',
    'RCTNetwork',
    'RCTWebSocket', # needed for debugging
    # Add any other subspecs you want to use in your project
  ]
  # Explicitly include Yoga if you are using RN >= 0.42.0
  pod 'yoga', :path => '../node_modules/react-native/ReactCommon/yoga'

  # Third party deps podspec link
  pod 'DoubleConversion', :podspec => '../node_modules/react-native/third-party-podspecs/DoubleConversion.podspec'
  pod 'GLog', :podspec => '../node_modules/react-native/third-party-podspecs/GLog.podspec'
  pod 'Folly', :podspec => '../node_modules/react-native/third-party-podspecs/Folly.podspec'

  pod 'react-native-baidu-map-sdk', path: '../node_modules/react-native-baidu-map-sdk/ios'
end

post_install do |installer|
  installer.pods_project.targets.each do |target|
    if target.name == "React"
      target.remove_from_project
    end
  end
end
然后运行 pod install
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
