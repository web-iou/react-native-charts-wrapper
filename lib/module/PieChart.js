"use strict";

import React from 'react';
import { requireNativeComponent } from 'react-native';
import { jsx as _jsx } from "react/jsx-runtime";
class PieChart extends React.Component {
  getNativeComponentName() {
    return 'RNPieChart';
  }
  getNativeComponentRef() {
    return this.nativeComponentRef;
  }
  render() {
    return /*#__PURE__*/_jsx(RNPieChart, {
      ...this.props,
      ref: ref => this.nativeComponentRef = ref
    });
  }
}
var RNPieChart = requireNativeComponent('RNPieChart', PieChart, {
  nativeOnly: {
    onSelect: true,
    onChange: true
  }
});
export default PieChart;