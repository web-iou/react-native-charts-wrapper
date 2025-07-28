"use strict";

import React from 'react';
import { requireNativeComponent } from 'react-native';
import MoveEnhancer from "./MoveEnhancer.js";
import ScaleEnhancer from "./ScaleEnhancer.js";
import HighlightEnhancer from "./HighlightEnhancer.js";
import ScrollEnhancer from "./ScrollEnhancer.js";
import { jsx as _jsx } from "react/jsx-runtime";
class CombinedChart extends React.Component {
  getNativeComponentName() {
    return 'RNCombinedChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNCombinedChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNCombinedChart = requireNativeComponent('RNCombinedChart', CombinedChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(CombinedChart))));