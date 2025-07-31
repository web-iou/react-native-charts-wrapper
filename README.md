# React Native Charts Wrapper

> **📢 重要更新**: 这是一个维护版本，修复了 TypeScript 定义文件中的 JSDoc 注释格式问题。原仓库请访问：[wuxudong/react-native-charts-wrapper](https://github.com/wuxudong/react-native-charts-wrapper)

This library is React Native wrapper of popular Native charting library [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) and [Charts](https://github.com/danielgindi/Charts)

## Recent Updates

### v1.0.2 (Latest)
- ✅ 更新BarChart组件，新增圆角半径属性并优化数据提取逻辑
- ✅ 新增BaseBarRenderer类以支持自定义条形图的高亮和绘制功能
- ✅ 优化CustomMarker的布局参数，调整边距和内边距以改善外观
- ✅ 增强LineChart和BarChart组件的新功能
- ✅ 新增自定义markerview及高亮整组功能

### v1.0.1
- ✅ Fixed JSDoc comment format in `index.d.ts`
- ✅ Corrected `@platform` tag syntax from `{ios}` to `ios`
- ✅ Updated comment blocks from `/* */` to `/** */` format
- ✅ Improved TypeScript definition documentation

## Introduction

Inspired by [react-native-mp-android-chart](https://github.com/mskec/react-native-mp-android-chart) and [react-native-ios-charts](https://github.com/Jpadilla1/react-native-ios-charts)

React Native Charts Wrapper is built on MPAndroidChart(v3.1.0) & Charts(v3.3.0), support both android & iOS.

## Installation

### iOS

1. 安装依赖：
```bash
npm install react-native-charts-wrapper
# 或者
yarn add react-native-charts-wrapper
```

2. 在您的 iOS 项目的 Podfile 中指定 DGCharts 的 git 源：

```ruby
# 在 Podfile 中添加以下行
pod 'DGCharts', :git => 'https://github.com/web-iou/Charts.git', :branch => 'master'
```

3. 运行 pod install：
```bash
cd ios && pod install
```

### 详细配置说明

#### 方法一：在 Podfile 中直接指定

在您的 iOS 项目的 Podfile 中添加以下行（建议放在 target 块之前）：

```ruby
# 指定 DGCharts 的 git 源
pod 'DGCharts', :git => 'https://github.com/web-iou/Charts.git', :branch => 'master'
```


### 注意事项

- 确保您的网络连接正常，因为需要从 GitHub 下载 Charts 仓库
- 如果下载速度较慢，可以考虑使用镜像源或 VPN
- 建议使用 `:branch => 'master'` 来获取最新版本，或者指定具体的 tag 来确保版本一致性

### Android

Android 配置保持不变，无需额外配置。

