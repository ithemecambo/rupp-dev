//
//  Header.swift
//  rupphr
//
//  Created by SENGHORT on 4/2/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit

struct Header: Codable {
    
    static let shared = Header()
    
    var title: String?
    var userList: [MData]?
    
    func loadData() -> [Header] {
        var list = [Header]()
        
        list.append(Header(title: "BASE INFO", userList: [MData(icon: "", header: "", text: ""),
                                                          MData(icon: "birthday", header: "Birthday", text: "May 25 1992"),
                                                          MData(icon: "gender", header: "Gender", text: "Male"),
                                                          MData(icon: "interested", header: "Interested", text: "New research"),
                                                          MData(icon: "language", header: "Languages", text: "English, France, China"),
                                                          MData(icon: "religious", header: "Religious", text: "Buddhist")]))
        list.append(Header(title: "CONTACT INFO", userList: [MData(icon: "", header: "", text: ""),
                                                             MData(icon: "call", header: "Mobile", text: "(+855)89-856-650"),
                                                          MData(icon: "email", header: "Email", text: "johnnine@gmail.com"),
                                                          MData(icon: "address", header: "Address", text: "Doun Penh, Phnom Penh"),
                                                          MData(icon: "facebook", header: "Facebook", text: "johnnine.kotas_facebook"),
                                                          MData(icon: "skype", header: "Skype", text: "johnnine.kotas_skype"),
                                                          MData(icon: "line", header: "Line", text: "johnnine.kotas_line"),
                                                          MData(icon: "telegram", header: "Telegrem", text: "johnnine.kotas_telegram"),
                                                          MData(icon: "twitter", header: "Twitter", text: "johnnine.kotas_twitter"),
                                                          MData(icon: "linkedin", header: "LinkedIn", text: "johnnine.kotas_linkedIn")]))
        list.append(Header(title: "WORK", userList: [MData(icon: "", header: "", text: ""),
                                                        MData(icon: "rupp", header: "", text: "Royal University of Phnom Penh"),
                                                          MData(icon: "ezeapp", header: "", text: "EZE APP Co., Ltd"),
                                                          MData(icon: "softbloom", header: "", text: "Softbloom Co., Ltd"),
                                                          MData(icon: "rupp", header: "", text: "Assistant Lecture")]))
        list.append(Header(title: "EDUCATION", userList: [MData(icon: "", header: "", text: ""),
                                                          MData(icon: "rupp", header: "", text: "Royal University of Phnom Penh"),
                                                          MData(icon: "puc", header: "", text: "Paññāsāstra University of Cambodia"),
                                                          MData(icon: "uminho", header: "", text: "University of Minho")]))
        list.append(Header(title: "PROFESSIONAL SKILLS", userList: [MData(icon: "", header: "", text: ""),
                                                          MData(icon: "ios", header: "", text: "IOS Developer"),
                                                          MData(icon: "android", header: "", text: "Android Developer"),
                                                          MData(icon: "api", header: "", text: "API website service")]))
        list.append(Header(title: "PLACES", userList: [MData(icon: "", header: "", text: ""),
                                                       MData(icon: "address", header: "", text: "Phnom Penh"),
                                                       MData(icon: "address", header: "", text: "Kandal, Phnom Penh, Cambodia")]))
        
        return list
    }

}

struct MData: Codable {
    var icon: String?
    var header: String?
    var text: String?
}

/*
 BASIC INFO
 
 - Birthday        May 25 1992
 - Gender        Male
 - Interested        New research
 - Languages        English, France, China
 - Religious        Buddhist
 
 CONTACT INFO
 
 - Mobile        089856650
 - Email            johnnine@gmail.com
 - Address        St 158, No 9, Sangkat Doun Penh, Khan Doun Penh, Phmon Penh
 - Facebook        johnnine.kotas
 - Skype            johnnine.kotas
 - Line            johnnine.kotas
 - Telegrem        johnnine.kotas
 - Twitter        johnnine.kotas
 - LinkedIn        johnnine.kotas
 
 WORK
 - Royal University of Phnom Penh
 
 EDUCATION
 - Royal University of Phnom Penh
 - IFL
 - China
 
 PROFESSIONAL SKILLS
 - IT Programming
 - Network
 
 PLACES
 - St 168, No 9, Sangkat Doun Penh, Khan Doun Penh, Phnom Penh
 */
