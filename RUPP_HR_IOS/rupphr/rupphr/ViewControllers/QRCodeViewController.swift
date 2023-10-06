//
//  QRCodeViewController.swift
//  rupphr
//
//  Created by SENGHORT on 3/30/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit
import Foundation
import AVFoundation

protocol ProfileDelegate {
    func scanFinished(scanResult: LBXScanResult, error: String?)
}

class QRCodeViewController: UIViewController, UIViewControllerTransitioningDelegate {

    open var scanStyle: LBXScanViewStyle? = LBXScanViewStyle()
    public var arrayCodeType:[AVMetadataObject.ObjectType]?
    open var scanObj: LBXScanWrapper?
    open var qRScanView: LBXScanView?
    open var isOpenInterestRect = false
    public  var isNeedCodeImage = false
    open var delegate: ProfileDelegate?
    let transition = CircularTransition()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    open func setNeedCodeImage(needCodeImg:Bool) {
        isNeedCodeImage = needCodeImg
    }
    open func setOpenInterestRect(isOpen:Bool){
        isOpenInterestRect = isOpen
    }
    
    override open func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.isNavigationBarHidden = true
        scanStyle?.centerUpOffset = 0
        scanStyle?.photoframeAngleStyle = LBXScanViewPhotoframeAngleStyle.Inner
        scanStyle?.photoframeLineW = 2
        scanStyle?.photoframeAngleW = 18
        scanStyle?.photoframeAngleH = 18
        scanStyle?.isNeedShowRetangle = false
        scanStyle?.anmiationStyle = LBXScanViewAnimationStyle.LineMove
        scanStyle?.colorAngle = UIColor(red: 0.0/255, green: 200.0/255.0, blue: 20.0/255.0, alpha: 1.0)
        scanStyle?.animationImage = #imageLiteral(resourceName: "qrcode_Scan_weixin_Line")
    }
    
    override open func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        drawScanView()
        perform(#selector(LBXScanViewController.startScan), with: nil, afterDelay: 0.3)
    }
    
    @objc open func startScan() {
        if (scanObj == nil) {
            var cropRect = CGRect.zero
            if isOpenInterestRect {
                cropRect = LBXScanView.getScanRectWithPreView(preView: self.view, style:scanStyle! )
            }
            if arrayCodeType == nil {
                arrayCodeType = [AVMetadataObject.ObjectType.qr,AVMetadataObject.ObjectType.ean13,AVMetadataObject.ObjectType.code128]
            }
            scanObj = LBXScanWrapper(videoPreView: self.view,objType:arrayCodeType!, isCaptureImg: isNeedCodeImage,cropRect:cropRect, success: { [weak self] (arrayResult) -> Void in
                if let strongSelf = self {
                    strongSelf.qRScanView?.stopScanAnimation()
                    strongSelf.handleCodeResult(arrayResult: arrayResult)
                }
            })
        }
        qRScanView?.deviceStopReadying()
        qRScanView?.startScanAnimation()
        scanObj?.start()
    }
    
    open func drawScanView() {
        if qRScanView == nil {
            qRScanView = LBXScanView(frame: self.view.frame,vstyle:scanStyle! )
            self.view.addSubview(qRScanView!)
        }
    }
    
    open func handleCodeResult(arrayResult:[LBXScanResult]) {
        if let delegate = self.delegate  {
            self.navigationController? .popViewController(animated: true)
            let result:LBXScanResult = arrayResult[0]
            delegate.scanFinished(scanResult: result, error: nil)
        } else {
            for result:LBXScanResult in arrayResult {
                print("%@",result.strScanned ?? "")
            }
            let result:LBXScanResult = arrayResult[0]
            showMsg(title: result.strBarCodeType, message: result.strScanned)
        }
    }
    
    override open func viewWillDisappear(_ animated: Bool) {
        NSObject.cancelPreviousPerformRequests(withTarget: self)
        qRScanView?.stopScanAnimation()
        scanObj?.stop()
    }
    
    func showMsg(title:String?, message:String?) {
        let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
        let vc: ProfileViewController = storyboard.instantiateViewController(withIdentifier: "storyboardProfile") as! ProfileViewController
        vc.employeeCode = message
        vc.transitioningDelegate = self
        vc.modalPresentationStyle = .custom

        self.present(vc, animated: true, completion: nil)
    }
    
    func animationController(forPresented presented: UIViewController, presenting: UIViewController, source: UIViewController) -> UIViewControllerAnimatedTransitioning? {
        transition.transitionMode = .present
        NSObject.cancelPreviousPerformRequests(withTarget: self)
        qRScanView?.stopScanAnimation()
        scanObj?.stop()
        return transition
    }
    
    func animationController(forDismissed dismissed: UIViewController) -> UIViewControllerAnimatedTransitioning? {
        transition.transitionMode = .dismiss
        self.startScan()
        return transition
    }
    
    deinit { }

}
