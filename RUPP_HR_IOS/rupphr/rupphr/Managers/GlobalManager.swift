//
//  GlobalManager.swift
//  rupphr
//
//  Created by SENGHORT on 3/30/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit
import Foundation

struct LTConstants {
    static let Preference_Lanague_str = "Preference_Lanague_str"
    static let Preference_Init_str = "Preference_Init_str"
}

extension UINavigationController {
    
    func pop(animated: Bool) {
        _ = self.popViewController(animated: animated)
    }
    
    func popToRoot(animated: Bool) {
        _ = self.popToRootViewController(animated: animated)
    }
}

extension DateFormatter {
    
    convenience init (format: String) {
        self.init()
        dateFormat = format
        locale = Locale.current
    }
}

extension Date {
    
    func toString (format:String) -> String? {
        return DateFormatter(format: format).string(from: self)
    }
}

extension String {
    
    var length: Int {
        return self.count
    }
    
    var floatValue: Float {
        return (self as NSString).floatValue
    }
    
    func substring(_ from: Int) -> String {
        let start = index(startIndex, offsetBy: from)
        return String(self[start ..< endIndex])
    }
    
    func replace(target: String, withString: String) -> String {
        return self.replacingOccurrences(of: target, with: withString, options: NSString.CompareOptions.literal, range: nil)
    }
    
    func toDate (format: String) -> Date? {
        return DateFormatter(format: format).date(from: self)
    }
    
    func toDateString (inputFormat: String, outputFormat:String) -> String? {
        if let date = toDate(format: inputFormat) {
            return DateFormatter(format: outputFormat).string(from: date)
        }
        return nil
    }
    
    func toDateStringTo( inputDateFormat inputFormat: String, ouputDateFormat outputFormat: String ) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = inputFormat
        let date = dateFormatter.date(from: self)
        dateFormatter.dateFormat = outputFormat
        return dateFormatter.string(from: date!)
    }
    
}

//MARK: -  HUD VIEW
var hudView = UIView()
var animImage = UIImageView(frame: CGRect(x: 0, y: 0, width: 50, height: 50))

extension UIView {
    
    func showHUD(_ inView: UIView) {
        hudView.frame = CGRect(x: 0, y: 0, width: inView.frame.size.width, height: inView.frame.size.height)
        hudView.backgroundColor = UIColor.white
        hudView.alpha = 0.5
        
        let imagesArr = ["h0", "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9"]
        let images = NSMutableArray()
        for i in 0 ..< imagesArr.count {
            images.add(UIImage(named: imagesArr[i])!)
        }
        animImage.animationImages = images as [AnyObject] as? [UIImage]
        //animImage.animationImages = images as [AnyObject]
        animImage.animationDuration = 0.7
        animImage.center = hudView.center
        hudView.addSubview(animImage)
        animImage.startAnimating()
        
        inView.addSubview(hudView)
    }
    
    func hideHUD() {
        hudView.removeFromSuperview()
    }
}

class GlobalManager: NSObject {
    
    static let shared = GlobalManager()
    static let preferences = UserDefaults.standard
    
    static let kDateMonthFormat = "yyyy'-'MM"
    static let kDateDayFormat = "yyyy'-'MM'-'dd"
    static let kDateHourFormat = "yyyy'-'MM'-'dd'-'HH"
    let dfHour = DateFormatter()
    let dfPrettyDay = DateFormatter()
    let dfPrettyDayLong = DateFormatter()
    
    let dfSorted = DateFormatter()
    let dfNormal = DateFormatter()
    let dfNormalSorted = DateFormatter()
    let dfFromServer = DateFormatter()
    let dfToServer = DateFormatter()
    let dfPretty = DateFormatter()
    let dfGraphFromServer = DateFormatter()
    
    override init() {
        super.init()
        // df.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss"
        dfHour.dateFormat = "yyyy'-'MM'-'dd'-'HH" //DO NOT CHANGE
        // dfDay.dateFormat = "yyyy'-'MM'-'dd" //DO NOT CHANGE
        dfPrettyDay.dateFormat = "d MMM yy"
        dfPrettyDayLong.dateFormat = "EEEE, d MMMM yy"
        
        dfSorted.dateFormat = "yyyy/MM/dd h:mm a"
        dfPretty.dateFormat = "EEE',' d MMM',' h':'mm a"
        dfNormal.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss"
        dfNormalSorted.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS"
        dfFromServer.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSSZZZ"
        dfToServer.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.000'ZZZ"
        dfGraphFromServer.dateFormat = "yyyy'-'M'-'d"
        
        dfSorted.timeZone = TimeZone.current
        dfNormal.timeZone = TimeZone.current
        dfNormalSorted.timeZone = TimeZone.current
        dfPretty.timeZone = TimeZone.current
        dfFromServer.timeZone = TimeZone(secondsFromGMT: 0)
        dfToServer.timeZone = TimeZone(secondsFromGMT: 0)
        dfGraphFromServer.timeZone = TimeZone(secondsFromGMT: 0)
    }
    
    class func dateStringForUTCDay(_ date:Date) -> String {
        let dfLocalDay = DateFormatter()
        dfLocalDay.timeZone = TimeZone(secondsFromGMT: 0)
        dfLocalDay.dateFormat = kDateDayFormat
        
        return dfLocalDay.string(from: date)
    }
    
    class func dateForLocalToUTCDay(_ date:Date) -> Date { // TODO Is this wrong?
        let dfUTCDay = DateFormatter()
        dfUTCDay.timeZone = TimeZone(secondsFromGMT: 0)
        dfUTCDay.dateFormat = kDateDayFormat
        
        return dfUTCDay.date(from: dateStringForLocalDay(date))!
    }
    
    class func dateForUTCToLocalDay(_ date:Date) -> Date { // TODO Is this wrong?
        let dfLocalDay = DateFormatter()
        dfLocalDay.timeZone = TimeZone.current
        dfLocalDay.dateFormat = kDateDayFormat
        
        return dfLocalDay.date(from: dateStringForUTCDay(date))!
    }
    
    class func dateStringForLocalDay(_ date:Date) -> String {
        let dfLocalDay = DateFormatter()
        dfLocalDay.timeZone = TimeZone.current
        dfLocalDay.dateFormat = kDateDayFormat
        
        return dfLocalDay.string(from: date)
    }
    
    class func dateForLocalDay(_ date:Date) -> Date {
        let dfLocalDay = DateFormatter()
        dfLocalDay.timeZone = TimeZone.current
        dfLocalDay.dateFormat = kDateDayFormat
        
        return dfLocalDay.date(from: dfLocalDay.string(from: date))!
    }
    
    class func dateForUTCDay(_ date:Date) -> Date{
        let dateString = dateStringForUTCDay(date)
        
        let dfUTCDay = DateFormatter()
        dfUTCDay.timeZone = TimeZone(secondsFromGMT: 0)
        dfUTCDay.dateFormat = kDateDayFormat
        
        return dfUTCDay.date(from: dateString)!
    }
    
    class func dateForLocalStartOfWeek(_ date:Date) -> Date {
        let iso8601 =  Calendar(identifier: Calendar.Identifier.iso8601)
        return iso8601.date(from: (iso8601 as NSCalendar).components([.yearForWeekOfYear, .weekOfYear ], from: date))!
    }
    
    class func dateForLocalMonth(_ date:Date) -> Date {
        let dfLocalDay = DateFormatter()
        dfLocalDay.timeZone = TimeZone.current
        dfLocalDay.dateFormat = kDateMonthFormat
        
        return dfLocalDay.date(from: dfLocalDay.string(from: date))!
    }
    
    class func dateStringForMonth(_ date:Date) -> String {
        let dfLocalDay = DateFormatter()
        dfLocalDay.timeZone = TimeZone.current
        dfLocalDay.dateFormat = kDateMonthFormat
        
        return dfLocalDay.string(from: date)
    }
    
    class func dateForUTCMonth(_ date: Date) -> Date {
        let dateString = dateStringForMonth(date)
        
        let dfUTCDay = DateFormatter()
        dfUTCDay.timeZone = TimeZone(secondsFromGMT: 0)
        dfUTCDay.dateFormat = kDateMonthFormat
        
        return dfUTCDay.date(from: dateString)!
    }
    
    class func dayDateStringWithFormat(_ date: Date, format: String) -> String {
        let dateForLocalDay = GlobalManager.dateForLocalDay(date)
        let formatter: DateFormatter = DateFormatter()
        formatter.dateFormat = format
        formatter.timeZone = TimeZone.autoupdatingCurrent
        
        return formatter.string(from: dateForLocalDay)
    }
    
    class func dateUTCToLocal(_ date:Date)-> Date {
        let totalGMTseconds = NSTimeZone.local.secondsFromGMT()
        
        return date.addingTimeInterval(Double(-totalGMTseconds))
    }
    
    class func dateLocalToUTC(_ date:Date)-> Date {
        let totalGMTseconds = NSTimeZone.local.secondsFromGMT()
        
        return date.addingTimeInterval(Double(totalGMTseconds))
    }
    
    class func utcOffsetString()-> String {
        let totalGMTseconds = NSTimeZone.local.secondsFromGMT()
        let hours = abs(totalGMTseconds)/60/60 % 24
        let mins = abs(totalGMTseconds)/60 % 60
        let signStr = (totalGMTseconds > 0) ? "+" : "-"
        let hoursStr = ((hours<10) ? "0" : "") + "\(hours)"
        let minsStr = ((mins<10) ? "0" : "") + "\(mins)"
        let utcOffsetStr = "\(signStr)\(hoursStr):\(minsStr)"
        
        return utcOffsetStr
    }
    
    class func colorBlend(_ color1:UIColor, color2:UIColor, ratio:Double) -> UIColor{
        let ratio1 = CGFloat(min(1.0, max(ratio,0.0)))
        let ratio2 = 1.0 - ratio1
        
        var r1: CGFloat = 0
        var r2: CGFloat = 0
        var g1: CGFloat = 0
        var g2: CGFloat = 0
        var b1: CGFloat = 0
        var b2: CGFloat = 0
        var a1: CGFloat = 0
        var a2: CGFloat = 0
        color1.getRed(&r1, green: &g1, blue: &b1, alpha: &a1)
        color2.getRed(&r2, green: &g2, blue: &b2, alpha: &a2)
        
        let red = r1 * ratio1 + r2 * ratio2
        let green = g1 * ratio1 + g2 * ratio2
        let blue = b1 * ratio1 + b2 * ratio2
        let alpha = a1 * ratio1 + a2 * ratio2
        
        return UIColor(red: red, green: green, blue: blue, alpha: alpha)
    }
    
    open func timeAgoSinceDate(_ date: NSDate, numericDates: Bool) -> String {
        let calendar = NSCalendar.current
        let unitFlags: Set<Calendar.Component> = [.minute, .hour, .day, .weekOfYear, .month, .year, .second]
        let now = NSDate()
        let earliest = now.earlierDate(date as Date)
        let latest = (earliest == now as Date) ? date : now
        let components = calendar.dateComponents(unitFlags, from: earliest,  to: latest as Date)
        
        
        if (components.year! >= 1){
            return convertToKhmerDate(date)
        }
        else if (components.month! >= 1){
            return convertToKhmerDate(date)
        } else if (components.weekOfYear! >= 2) {
            return "\(convertFullNumber("\(String(describing: components.weekOfYear))")) សប្តាហ៍មុន"
        } else if (components.weekOfYear! >= 1){
            if (numericDates){
                return "១ សប្តាហ៍មុន"
            } else {
                return "សប្តាហ៍មុន"
            }
        } else if (components.day! >= 2) {
            return "\(convertFullNumber("\(String(describing: components.day))")) ថ្ងៃមុន"
        } else if (components.day! >= 1){
            if (numericDates){
                return "១ ថ្ងៃមុន"
            } else {
                return "ម្សិលមិញ"
            }
        } else if (components.hour! >= 2) {
            return "\(convertFullNumber("\(String(describing: components.hour))")) ម៉ោងមុន"
        } else if (components.hour! >= 1){
            if (numericDates){
                return "១ ម៉ោងមុន"
            } else {
                return "មួយម៉ោងមុន"
            }
        } else if (components.minute! >= 2) {
            return "\(convertFullNumber("\(String(describing: components.minute))")) នាទីមុន"
        } else if (components.minute! >= 1){
            if (numericDates){
                return "១ នាទីមុន"
            } else {
                return "A minute ago"
            }
        } else if (components.second! >= 3) {
            return "\(convertFullNumber("\(String(describing: components.second))")) នាទីមុន"
        } else {
            return "មុននេះបន្តិច"
        }
        
    }
    
    open func convertNumber2Kh(_ Num:String) -> String{
        switch Num {
        case "0":
            return "០"
        case "1":
            return "១"
        case "2":
            return "២"
        case "3":
            return "៣"
        case "4":
            return "៤"
        case "5":
            return "៥"
        case "6":
            return "៦"
        case "7":
            return "៧"
        case "8":
            return "៨"
        case "9":
            return "៩"
        default:
            return ""
        }
    }
    
    open func convertFullNumber(_ Num: String) -> String{
        var data = ""
        for index in 0..<Num.length {
            data += convertNumber2Kh("\(Num)\([index])")// [index]
        }
        
        return data
    }
    
    open func convertToKhmerDate(_ date: NSDate) -> String{
        let format="yyyy-MM-dd'"
        let dateFmt = DateFormatter()
        dateFmt.dateFormat = format
        let newreadableDate = dateFmt.string(from: date as Date)
        // split string
        let month = newreadableDate.split{$0 == "-"}.map(String.init)
        switch month[1] {
        case "01":
            return convertFullNumber(month[2]) + " មករា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "02":
            return convertFullNumber(month[2]) + " កម្ភះ " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "03":
            return convertFullNumber(month[2]) + " មិនា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "04":
            return convertFullNumber(month[2]) + " មេសា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "05":
            return convertFullNumber(month[2]) + " ឧសភា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "06":
            return convertFullNumber(month[2])  + " មិថុនា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "07":
            return convertFullNumber(month[2])  + " កក្តដា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "08":
            return convertFullNumber(month[2])  + " សីហា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "09":
            return convertFullNumber(month[2])  + " កញ្ញា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "10":
            return convertFullNumber(month[2])  + " តុលា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "11":
            return convertFullNumber(month[2])  + " វិច្ខិកា " + "ឆ្នាំ " + convertFullNumber(month[0])
        case "12":
            return convertFullNumber(month[2]) + " ធ្នូ " + "ឆ្នាំ " + convertFullNumber(month[0])
        default:
            return ""
        }
    }
    
    open func debug(_ param: String) {
        print(param)
    }
    
    class func scaleImage(_ image: UIImage, toSize newSize: CGSize) -> UIImage {
        let newRect = CGRect(x: 0,y: 0, width: newSize.width, height: newSize.height).integral
        UIGraphicsBeginImageContextWithOptions(newSize, false, 0)
        let context = UIGraphicsGetCurrentContext()
        context!.interpolationQuality = .high
        let flipVertical = CGAffineTransform(a: 1, b: 0, c: 0, d: -1, tx: 0, ty: newSize.height)
        context?.concatenate(flipVertical)
        context?.draw(image.cgImage!, in: newRect)
        let newImage = UIImage(cgImage: (context?.makeImage()!)!)
        UIGraphicsEndImageContext()
        
        return newImage
    }
    
    open func UIImage2CGimage(_ image: UIImage?) -> CGImage? {
        if let tryImage = image, let tryCIImage = CIImage(image: tryImage) {
            return CIContext().createCGImage(tryCIImage, from: tryCIImage.extent)
        }
        
        return nil
    }

}
