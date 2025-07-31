//
//  BaseBarRenderer.swift
//  react-native-charts-wrapper
//
//  Created by 陈翔 on 2025/7/30.
//

import Foundation
import DGCharts

class BaseBarRenderer: BarChartRenderer {
    
    private static var radius: CGFloat = 0
    private var transformer: Transformer?
    override func prepareBarHighlight(x: Double, y1: Double, y2: Double, barWidthHalf: Double, trans: Transformer, rect: inout CGRect) {
        transformer = trans
        guard let barData = dataProvider?.barData else { return  }
        let chart=dataProvider as? BarChartView
        let barCountInGroup = barData.dataSetCount
        let groupIndex = Int(x)
        
        let barCenterX = CGFloat(barData.barWidth * Double(barCountInGroup) / 2.0 + 0.1)
        let groupCenterX = CGFloat(groupIndex) + barCenterX
        
        let highlightBarWidthHalf = barWidthHalf * CGFloat(barCountInGroup)
        
        if barCountInGroup > 1 {
            super.prepareBarHighlight(x: groupCenterX, y1: dataProvider?.chartYMax ?? 0, y2: y2, barWidthHalf: highlightBarWidthHalf, trans: trans,rect: &rect)
        } else {
            super.prepareBarHighlight(x: x, y1: dataProvider?.chartYMax ?? 0, y2: y2, barWidthHalf: highlightBarWidthHalf, trans: trans,rect: &rect)
        }
    }
    override func drawDataSet(context: CGContext, dataSet: any BarChartDataSetProtocol, index: Int) {
      super.drawDataSet(context: context, dataSet: dataSet, index: index)
    }
}
