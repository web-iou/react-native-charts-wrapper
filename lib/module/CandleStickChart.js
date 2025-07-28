"use strict";

import { requireNativeComponent } from 'react-native';
import React from 'react';
import MoveEnhancer from "./MoveEnhancer.js";
import ScaleEnhancer from "./ScaleEnhancer.js";
import HighlightEnhancer from "./HighlightEnhancer.js";
import ScrollEnhancer from "./ScrollEnhancer.js";
import { jsx as _jsx } from "react/jsx-runtime";
class CandleStickChart extends React.Component {
  getNativeComponentName() {
    return 'RNCandleStickChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNCandleStickChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNCandleStickChart = requireNativeComponent('RNCandleStickChart', CandleStickChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(CandleStickChart))));