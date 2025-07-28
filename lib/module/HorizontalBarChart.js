"use strict";

import React from 'react';
import { requireNativeComponent } from 'react-native';
import MoveEnhancer from "./MoveEnhancer.js";
import ScaleEnhancer from "./ScaleEnhancer.js";
import HighlightEnhancer from "./HighlightEnhancer.js";
import { jsx as _jsx } from "react/jsx-runtime";
class HorizontalBarChart extends React.Component {
  getNativeComponentName() {
    return 'RNHorizontalBarChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNHorizontalBarChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNHorizontalBarChart = requireNativeComponent('RNHorizontalBarChart', HorizontalBarChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default HighlightEnhancer(ScaleEnhancer(MoveEnhancer(HorizontalBarChart)));