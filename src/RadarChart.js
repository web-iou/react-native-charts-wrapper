import React from 'react';
import {
  requireNativeComponent,
} from 'react-native';

class RadarChart extends React.Component {
  getNativeComponentName() {
    return 'RNRadarChart'
  }

  getNativeComponentRef() {
    return this.nativeComponentRef
  }

  render() {
    return <RNRadarChart {...this.props} ref={ref => this.nativeComponentRef = ref} />;
  }
}



var RNRadarChart = requireNativeComponent('RNRadarChart', RadarChart, {
  nativeOnly: {onSelect: true, onChange: true}
});

export default RadarChart