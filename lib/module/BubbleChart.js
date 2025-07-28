"use strict";

import React from 'react';
import { requireNativeComponent } from 'react-native';
import ScrollEnhancer from "./ScrollEnhancer.js";
import MoveEnhancer from "./MoveEnhancer.js";
import ScaleEnhancer from "./ScaleEnhancer.js";
import HighlightEnhancer from "./HighlightEnhancer.js";
import { jsx as _jsx } from "react/jsx-runtime";
class BubbleChart extends React.Component {
  getNativeComponentName() {
    return 'RNBubbleChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNBubbleChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNBubbleChart = requireNativeComponent('RNBubbleChart', BubbleChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(BubbleChart))));