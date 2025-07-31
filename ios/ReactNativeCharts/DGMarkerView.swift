import DGCharts
import UIKit
import SwiftyJSON
class DGMarkerView: MarkerView {
    private let stackView = UIStackView()

    override init(frame: CGRect) {
        super.init(frame: frame)
        setupUI()
        setupLayout()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupUI()
        setupLayout()
    }
    
    private func setupUI() {
        backgroundColor = .white
        layer.cornerRadius = 4
        clipsToBounds = true
        layer.shadowColor = UIColor.black.cgColor
        layer.shadowOpacity = 0.08
        layer.shadowOffset = CGSize(width: 0, height: 3)
        layer.shadowRadius = 5
        layer.masksToBounds = false
                
        // Configure stack view
        stackView.axis = .vertical
        stackView.spacing = 4
        stackView.alignment = .leading
        stackView.distribution = .fill
        addSubview(stackView)
    }

    private func setupLayout() {
        stackView.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            stackView.topAnchor.constraint(equalTo: topAnchor, constant: 10),
            stackView.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -10),
            stackView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 12),
            stackView.trailingAnchor.constraint(lessThanOrEqualTo: trailingAnchor, constant: -12)
        ])
    }

    override func refreshContent(entry: ChartDataEntry, highlight: Highlight) {
        guard let chartView = chartView,
              chartView.highlightPerTapEnabled,
              let data = chartView.data else {
            return
        }
        stackView.arrangedSubviews.forEach { $0.removeFromSuperview() }


        // ✅ 以下保留你原来的逻辑
       var label: String
        if let object = entry.data as? JSON {
            if object["marker"].exists() {
                label = object["marker"].stringValue
                if highlight.stackIndex != -1, object["marker"].array != nil {
                    label = object["marker"].arrayValue[highlight.stackIndex].stringValue
                }
                let labelView = UILabel()
                labelView.font = UIFont.systemFont(ofSize: 11)
                labelView.textColor = UIColor(red: 140/255.0, green: 140/255.0, blue: 140/255.0, alpha: 1)
                labelView.text = label
                stackView.addArrangedSubview(labelView)
            }
        }
        var dataIndex = -1
        if(chartView is BarChartView){
            for i in  0..<data.dataSetCount{
                let dataSet = data.dataSet(at: i)
                let index = dataSet?.entryIndex(entry: entry)
                if(!(index == -1)){
                    dataIndex=index!
                    break
                }
            }
        }
        let xValue = entry.x
        for i in 0..<data.dataSetCount {
            if let dataSet = data.dataSet(at: i),
               dataSet.isVisible{
                let matchEntry:ChartDataEntry
                if(chartView is BarChartView){
                    matchEntry = dataSet.entryForIndex(dataIndex)!
                }else{
                    matchEntry = dataSet.entriesForXValue(xValue).first!
                }
                                let rowStackView = UIStackView()
                                rowStackView.axis = .horizontal
                                rowStackView.spacing = 4
                                rowStackView.alignment = .center
                
                                let dotView = UIView()
                                dotView.backgroundColor = dataSet.colors.first
                                dotView.layer.cornerRadius = 3
                                dotView.translatesAutoresizingMaskIntoConstraints = false
                                NSLayoutConstraint.activate([
                                    dotView.widthAnchor.constraint(equalToConstant: 6),
                                    dotView.heightAnchor.constraint(equalToConstant: 6)
                                ])
                
                                let label = UILabel()
                                label.font = UIFont.boldSystemFont(ofSize: 11)
                                label.textColor = UIColor(red: 38/255.0, green: 38/255.0, blue: 38/255.0, alpha: 1)
                                label.text = "\(dataSet.label ?? "数据集 \(i)"): \(matchEntry.y)"
                
                                rowStackView.addArrangedSubview(dotView)
                                rowStackView.addArrangedSubview(label)
                
                                stackView.addArrangedSubview(rowStackView)
            }

        }

        // ✅ 更新 layout
        stackView.setNeedsLayout()
        stackView.layoutIfNeeded()

        let fittingSize = stackView.systemLayoutSizeFitting(
            UIView.layoutFittingCompressedSize,
            withHorizontalFittingPriority: .fittingSizeLevel,
            verticalFittingPriority: .fittingSizeLevel
        )

        self.frame.size = CGSize(
            width: fittingSize.width + 24,
            height: fittingSize.height + 20
        )

        self.setNeedsLayout()
        self.layoutIfNeeded()
    }


    override func offsetForDrawing(atPoint point: CGPoint) -> CGPoint {
        guard let chart = chartView else { return .zero }
       
        let markerSize = self.bounds.size
        let chartWidth = chart.bounds.width
        let chartHeight = chart.bounds.height
        let padding: CGFloat = 8  // 安全边距
        
        // ✅ 1. 计算Y轴偏移 - 使MarkerView在图表中垂直居中
        let chartCenterY = chartHeight / 2
        let offsetY = chartCenterY - point.y - markerSize.height / 2
        
        // ✅ 2. 默认在point右侧显示
        var offsetX: CGFloat = 10  // 右侧偏移10pt
        
        // ✅ 3. 检查右侧空间是否足够
        let markerRightEdge = point.x + offsetX + markerSize.width
        if markerRightEdge > chartWidth - padding {
            // 如果右侧空间不足，改为左侧显示
            offsetX = -markerSize.width - 10  // 左侧偏移10pt
        }
        
        // ✅ 4. 检查左侧是否超出边界（极端情况）
        let markerLeftEdge = point.x + offsetX
        if markerLeftEdge < padding {
            offsetX = -point.x + padding  // 强制右对齐
        }
        
        return CGPoint(x: offsetX, y: offsetY)
    }
}
