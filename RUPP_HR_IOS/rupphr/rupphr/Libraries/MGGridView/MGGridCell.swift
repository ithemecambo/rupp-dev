//
//  MGGridCell.swift
//  rupphr
//
//  Created by SENGHORT on 4/2/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit

class MGGridCell: UICollectionViewCell {
    
    var thumbNail = UIImageView()
    var labelHeader = UILabel()
    var labelTitle = UILabel()

    override init(frame: CGRect) {
        super.init(frame: frame)
        self.setup()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setup() {
        contentView.addSubview(thumbNail)
        thumbNail.translatesAutoresizingMaskIntoConstraints = false
        thumbNail.widthAnchor.constraint(equalToConstant: 22).isActive = true
        thumbNail.heightAnchor.constraint(equalToConstant: 22).isActive = true
        thumbNail.topAnchor.constraint(equalTo: layoutMarginsGuide.topAnchor, constant: 5).isActive = true
        thumbNail.leadingAnchor.constraint(equalTo: layoutMarginsGuide.leadingAnchor).isActive = true
        thumbNail.image = #imageLiteral(resourceName: "email")
        
        contentView.addSubview(labelHeader)
        labelHeader.translatesAutoresizingMaskIntoConstraints = false
        labelHeader.heightAnchor.constraint(equalToConstant: 30).isActive = true
        labelHeader.topAnchor.constraint(equalTo: layoutMarginsGuide.topAnchor).isActive = true
        labelHeader.leadingAnchor.constraint(equalTo: thumbNail.leadingAnchor, constant: 30).isActive = true
        labelHeader.font = UIFont.boldSystemFont(ofSize: 13)
        labelHeader.textColor = .gray
        labelHeader.textAlignment = .left
        labelHeader.text = "Label"
        
        contentView.addSubview(labelTitle)
        labelTitle.translatesAutoresizingMaskIntoConstraints = false
        labelTitle.heightAnchor.constraint(equalToConstant: 30).isActive = true
        labelTitle.topAnchor.constraint(equalTo: layoutMarginsGuide.topAnchor).isActive = true
        labelTitle.trailingAnchor.constraint(equalTo: layoutMarginsGuide.trailingAnchor).isActive = true
        labelTitle.font = UIFont.boldSystemFont(ofSize: 13)
        labelTitle.numberOfLines = 0
        labelTitle.textAlignment = .right
        labelTitle.text = "Value"
    }
    
}
