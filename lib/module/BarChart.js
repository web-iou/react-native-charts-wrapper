"use strict";

import React from 'react';
import { requireNativeComponent } from 'react-native';
import MoveEnhancer from "./MoveEnhancer.js";
import ScaleEnhancer from "./ScaleEnhancer.js";
import HighlightEnhancer from "./HighlightEnhancer.js";
import ScrollEnhancer from "./ScrollEnhancer.js";
import { jsx as _jsx } from "react/jsx-runtime";
class BarChart extends React.Component {
  getNativeComponentName() {
    return 'RNBarChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNBarChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNBarChart = requireNativeComponent('RNBarChart', BarChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(BarChart))));