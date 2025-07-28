import {
  requireNativeComponent,
} from 'react-native';
import React from 'react';
import MoveEnhancer from './MoveEnhancer'
import ScaleEnhancer from "./ScaleEnhancer";
import HighlightEnhancer from "./HighlightEnhancer";
import ScrollEnhancer from "./ScrollEnhancer";

class CandleStickChart extends React.Component {
  getNativeComponentName() {
    return 'RNCandleStickChart'
  }

  getNativeComponentRef() {
    return this.nativeComponentRef
  }

  render() {
    return <RNCandleStickChart {...this.props} ref={ref => this.nativeComponentRef = ref} />;
  }

}



var RNCandleStickChart = requireNativeComponent('RNCandleStickChart', CandleStickChart, {
  nativeOnly: {onSelect: true, onChange: true}
});

export default ScrollEnhancer(HighlightEnhancer(ScaleEnhancer(MoveEnhancer(CandleStickChart))))
