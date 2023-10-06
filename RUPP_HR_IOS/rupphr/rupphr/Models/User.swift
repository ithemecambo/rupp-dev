//
//  User.swift
//  rupphr
//
//  Created by SENGHORT on 4/2/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit

struct User: Codable {
    var header: String?
    var text: String?
    
    static let shared = User()
    
    func loadData() -> [User] {
        var userList = [User]()
        
        userList.append(User(header: "លេខសំគាល់", text: "២៣៤៣៤៨៧៩"))
        userList.append(User(header: "ឈ្មោះ", text: "ឃាម ហុង"))
        userList.append(User(header: "តួនាទី", text: "អុនប្រធានការិយាល័យ"))
        userList.append(User(header: "នាយកដ្ឋានលេខ", text: "១២៥៦"))
        userList.append(User(header: "ថ្ងៃ ខែ ឆ្នាំ កំណើត", text: "២៥ តុលា ១៩៧៩"))
        userList.append(User(header: "ភេទ", text: "ប្រុស"))
        userList.append(User(header: "លេខទូរសព្ទ័", text: "០១០៧៥៥៥៧៩"))
        userList.append(User(header: "កាំប្រាក់", text: "ក២.២"))
        userList.append(User(header: "ប្តីឬប្រពន្ធ", text: "១"))
        userList.append(User(header: "ចំនួនកូន", text: "២"))
        userList.append(User(header: "ថ្ងៃចូលបំរើការងារ", text: "១៥ តុលា ២០០៤"))
        userList.append(User(header: "បុគ្គលិក", text: ""))
        userList.append(User(header: "បម្រើការងារ", text: ""))
        
        return userList
    }
}

struct Record: Decodable {
    var code: Int?
    var record: Employee?
}

struct Employee: Decodable {
    var id: Int?
    var empCode: String?
    var empNameKh: String?
    var empNameEng: String?
    var dob: String?
    var sex: String?
    var phone: String?
    var jobTitle: String?
    var kamPrak: String?
    var spouses: Int?
    var children: Int?
    var deptNo: Int?
    var hiredDate: String?
    var contract: Int?
    var deactviated: Int?
    var photo: String?
}
