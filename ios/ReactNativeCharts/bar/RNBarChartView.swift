//  Created by xudong wu on 24/02/2017.
//  Copyright wuxudong
//

import DGCharts
import SwiftyJSON

class RNBarChartView: RNBarChartViewBase {

    let _chart: BarChartView
    let _dataExtract : BarDataExtract

    override var chart: BarChartView {
        return _chart
    }

    override var dataExtract: DataExtract {
        return _dataExtract
    }

    override init(frame: CoreGraphics.CGRect) {
        self._chart = BarChartView(frame: frame)
        self._dataExtract = BarDataExtract()
        super.init(frame: frame)
        _chart.renderer=BaseBarRenderer(dataProvider: _chart,animator: _chart.chartAnimator,viewPortHandler: _chart.viewPortHandler)
        self._chart.delegate = self
        self.addSubview(_chart)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
