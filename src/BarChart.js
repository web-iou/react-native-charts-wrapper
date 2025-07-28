import React from 'react';
import {
  requireNativeComponent,
} from 'react-native';

import MoveEnhancer from './MoveEnhancer'
import ScaleEnhancer from "./ScaleEnhancer";
import HighlightEnhancer from "./HighlightEnhancer";
import ScrollEnhancer from "./ScrollEnhancer";

class BarChart extends React.Component {
  getNativeComponentName() {
    return 'RNBarChart'
  }

  getNativeComponentRef() {
    return this.nativeComponentRef
  }

  render() {
    return <RNBarChart {...this.props} ref={ref => this.nativeComponentRef = ref} />;
  }
}



var RNBarChart = requireNativeComponent('RNBarChart', BarChart, {
  nativeOnly: {onSelect: true, onChange: true}
})

export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(BarChart))))
