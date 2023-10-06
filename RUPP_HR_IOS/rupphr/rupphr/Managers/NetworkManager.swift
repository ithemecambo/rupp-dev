//
//  NetworkManager.swift
//  rupphr
//
//  Created by SENGHORT on 4/9/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit
import Alamofire
import AFNetworking

class Reachability {
    class func isConnected() -> Bool {
        var zeroAddress = sockaddr_in(sin_len: 0, sin_family: 0, sin_port: 0, sin_addr: in_addr(s_addr: 0), sin_zero: (0, 0, 0, 0, 0, 0, 0, 0))
        zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
        zeroAddress.sin_family = sa_family_t(AF_INET)
        
        let defaultRouteReachability = withUnsafePointer(to: &zeroAddress) {
            $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {zeroSockAddress in
                SCNetworkReachabilityCreateWithAddress(nil, zeroSockAddress)
            }
        }
        var flags: SCNetworkReachabilityFlags = SCNetworkReachabilityFlags(rawValue: 0)
        if SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) == false {
            return false
        }
        // Working for Cellular and WIFI
        let isReachable = (flags.rawValue & UInt32(kSCNetworkFlagsReachable)) != 0
        let needsConnection = (flags.rawValue & UInt32(kSCNetworkFlagsConnectionRequired)) != 0
        let ret = (isReachable && !needsConnection)
        
        return ret
    }
    
    class func isConnectedToInternet() ->Bool {
        return AFNetworkReachabilityManager().isReachable
    }
}

struct API {
    static let BASE_URL = "https://rupphr.000webhostapp.com/human_resource/rest/"
    static let EMPLOYEE_URL = API.BASE_URL + "employee_api.php"
    static let PROFILE_API_URL = API.BASE_URL + "profile_api.php"
    static let PROFILE_INFO_URL = API.BASE_URL + "profile_info.php?idx="
}

struct API_Response_Key {
    static let Success_Code = 1
    static let Fail_Code = 0
}

enum KEY: String {
    case Code = "code"
    case Record = "record"
    case Message = "message"
}

class NetworkManager: NSObject {
    
    static let shared = NetworkManager()
    
    typealias SuccessHandler = (DataResponse<Any>) -> Void
    typealias FailureHandler = (Error) -> Void
    
    open func requestDataByPostEmployee(_ strURL : String, params : [String : Any]?,
                                 success : @escaping SuccessHandler,
                                 failure :@escaping FailureHandler) {

        if Reachability.isConnected() == true {
            Alamofire.request(strURL, method: .post, parameters: params,
                              encoding: JSONEncoding.default, headers: nil).downloadProgress(queue: DispatchQueue.global(qos: .utility)) { progress in
                                    print("Progress: \(progress.fractionCompleted)")
                                }
                                .validate { request, response, data in
                                    return .success
                                }
                                .responseJSON { response in
                                    success(response)
                                    debugPrint(response)
            }
        }
    }

    open func requestDataByGetEmployee(_ strURL: String, inView: UIView, params: [String : Any]?,
                                       success: @escaping SuccessHandler,
                                       failure:@escaping FailureHandler) {
        if Reachability.isConnected() == true {
            Alamofire.request(strURL, method: .get, parameters: params, encoding: JSONEncoding.default, headers: nil).downloadProgress(queue: DispatchQueue.global(qos: .utility)) { progress in
                    print("Progress: \(progress.fractionCompleted)")
                    inView.showHUD(inView)
                }
                .validate { request, response, data in
                    return .success
                }
                .responseJSON { response in
                    inView.hideHUD()
                    success(response)
                    debugPrint(response)
            }
        }
    }
    
}
