import React from 'react';
import {
  requireNativeComponent,
} from 'react-native';



class PieChart extends React.Component {
  getNativeComponentName() {
    return 'RNPieChart'
  }

  getNativeComponentRef() {
    return this.nativeComponentRef
  }

  render() {
    return <RNPieChart {...this.props} ref={ref => this.nativeComponentRef = ref} />;
  }
}



var RNPieChart = requireNativeComponent('RNPieChart', PieChart, {
  nativeOnly: {onSelect: true, onChange: true}
});

export default PieChart
