import React from 'react';
import {
  requireNativeComponent,
} from 'react-native';

import MoveEnhancer from './MoveEnhancer'
import ScaleEnhancer from "./ScaleEnhancer";
import HighlightEnhancer from "./HighlightEnhancer";

class HorizontalBarChart extends React.Component {
  getNativeComponentName() {
    return 'RNHorizontalBarChart'
  }

  getNativeComponentRef() {
    return this.nativeComponentRef
  }

  render() {
    return <RNHorizontalBarChart {...this.props} ref={ref => this.nativeComponentRef = ref} />;
  }

}



var RNHorizontalBarChart = requireNativeComponent('RNHorizontalBarChart', HorizontalBarChart, {
  nativeOnly: {onSelect: true, onChange: true}
});

export default HighlightEnhancer(ScaleEnhancer(MoveEnhancer(HorizontalBarChart)))