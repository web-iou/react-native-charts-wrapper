"use strict";

import React from 'react';
import { requireNativeComponent } from 'react-native';
import { jsx as _jsx } from "react/jsx-runtime";
class RadarChart extends React.Component {
  getNativeComponentName() {
    return 'RNRadarChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNRadarChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNRadarChart = requireNativeComponent('RNRadarChart', RadarChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default RadarChart;