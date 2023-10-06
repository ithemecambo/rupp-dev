//
//  ProfileViewController.swift
//  rupphr
//
//  Created by SENGHORT on 3/30/18.
//  Copyright © 2018 SENGHORT. All rights reserved.
//

import UIKit
import Alamofire

class ProfileViewController: UITableViewController {
    
    @IBOutlet weak var visualClose: UIVisualEffectView!
    @IBOutlet weak var thumbClose: UIImageView!
    @IBOutlet weak var visualLanguage: UIVisualEffectView!
    @IBOutlet weak var thumbFlagEnglish: UIImageView!
    @IBOutlet weak var thumbFlagKhmer: UIImageView!
    @IBOutlet weak var buttonEnglish: UIButton!
    @IBOutlet weak var buttonKhmer: UIButton!
    @IBOutlet weak var thumbNail: UIImageView!
    @IBOutlet weak var headerView: UIView!
    @IBOutlet weak var labelInfo: UILabel!
    var employeeCode: String?
    var userList = [User]()
    var contract: String?
    var hudView = UIView()

    override func viewDidLoad() {
        super.viewDidLoad()
        if GlobalManager.preferences.string(forKey: LTConstants.Preference_Lanague_str) == "km" {
            self.enableKhmerLanguage()
        } else {
            self.enableEnglishLanguage()
        }
        self.thumbNail.layer.borderWidth = 3
        self.tableView.separatorStyle = .none
        self.labelInfo.text = "Employee_Info".localized()
        self.navigationController?.isNavigationBarHidden = true
        self.thumbNail.layer.borderColor = UIColor.white.cgColor
        self.thumbNail.layer.cornerRadius = thumbNail.frame.width / 2
        self.visualClose.layer.cornerRadius = self.visualClose.frame.width / 2
        self.visualLanguage.layer.cornerRadius = self.visualLanguage.frame.width / 2
        self.tableView.register(UINib(nibName: "ProfileCell", bundle: nil), forCellReuseIdentifier: "cell")
        let urlString = String(format: "%@%@", API.PROFILE_INFO_URL, employeeCode!)
        self.loadEmployeeData(urlString)
        self.buttonKhmer.addTarget(self, action: #selector(didClickKhmerButton(_:)), for: .touchUpInside)
        self.buttonEnglish.addTarget(self, action: #selector(didClickEnglishButton(_:)), for: .touchUpInside)
        hudView.frame = CGRect(x: 0, y: 0, width: hudView.frame.size.width/2, height: hudView.frame.size.height/2)
        hudView.backgroundColor = UIColor.white
        hudView.alpha = 0.5
    }

    @IBAction func didClickBackButton(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }

    @objc func didClickKhmerButton(_ sender: Any) {
        self.enableKhmerLanguage()
        Localize.setCurrentLanguage("km")
        GlobalManager.preferences.set("km", forKey: LTConstants.Preference_Lanague_str)
        self.tableView.reloadData()
    }
    
    @objc func didClickEnglishButton(_ sender: Any) {
        self.enableEnglishLanguage()
        Localize.setCurrentLanguage("en")
        GlobalManager.preferences.set("en", forKey: LTConstants.Preference_Lanague_str)
        self.tableView.reloadData()
    }
    
    func enableKhmerLanguage() {
        self.buttonKhmer.isHidden = true
        self.buttonEnglish.isHidden = false
        self.thumbFlagKhmer.isHidden = true
        self.thumbFlagEnglish.isHidden = false
        self.thumbFlagKhmer.image = #imageLiteral(resourceName: "flag_khmer")
    }
    
    func enableEnglishLanguage() {
        self.buttonKhmer.isHidden = false
        self.buttonEnglish.isHidden = true
        self.thumbFlagKhmer.isHidden = false
        self.thumbFlagEnglish.isHidden = true
        self.thumbFlagKhmer.image = #imageLiteral(resourceName: "flag_english")
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.userList.count
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 60
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! MGListCell
        self.setSeparatorCell(cell)
        tableView.separatorStyle = .singleLine
        let user = self.userList[indexPath.row]
        cell.labelHeader.text = user.header
        cell.labelTitle.text = user.text
        
        return cell
    }
    
    func setSeparatorCell(_ cell: UITableViewCell) {
        cell.selectionStyle = .none
        cell.preservesSuperviewLayoutMargins = false
        cell.separatorInset = UIEdgeInsets.zero
        cell.layoutMargins = UIEdgeInsets.zero
    }
    
    fileprivate func loadEmployeeData(_ url: String) {
        NetworkManager.shared.requestDataByGetEmployee(url, inView: hudView, params: nil, success: { (responseData) in
            let employee = try! JSONDecoder().decode(Record.self, from: responseData.data!)
            
            let code = String(format: "%d", (employee.record?.empCode)!)
            let deptNo = String(format: "%d", (employee.record?.deptNo)!)
            let spouses = String(format: "%d", (employee.record?.spouses)!)
            let children = String(format: "%d", (employee.record?.children)!)
            let dob = self.toDateString("yyyy-MM-dd", withString: "dd MMM, yyyy", data: (employee.record?.dob)!)
            let hiredDate = self.toDateString("yyyy-MM-dd", withString: "dd MMM, yyyy", data: (employee.record?.hiredDate)!)
            let deactviated = self.calculateWorkOfYear((employee.record?.hiredDate)!)
            if employee.record?.contract == 0 {
                self.contract = "Contract_Not_Kramkhan".localized()
            } else {
                self.contract = "Contract_Kramkhan".localized()
            }
            self.userList.append(User(header: "Employee_Code".localized(), text: code))
            self.userList.append(User(header: "Employee_Name".localized(), text: employee.record?.empNameKh))
            self.userList.append(User(header: "Employee_JobTitle".localized(), text: employee.record?.jobTitle))
            self.userList.append(User(header: "Employee_DeptNo".localized(), text: deptNo))
            self.userList.append(User(header: "Employee_DOB".localized(), text: dob))
            self.userList.append(User(header: "Employee_Sex".localized(), text: employee.record?.sex))
            self.userList.append(User(header: "Employee_Phone".localized(), text: employee.record?.phone))
            self.userList.append(User(header: "Employee_KamPrak".localized(), text: employee.record?.kamPrak))
            self.userList.append(User(header: "Employee_Spouses".localized(), text: spouses))
            self.userList.append(User(header: "Employee_Children".localized(), text: children))
            self.userList.append(User(header: "Employee_HiredDate".localized(), text: hiredDate))
            self.userList.append(User(header: "Employee_Contract".localized(), text: self.contract))
            self.userList.append(User(header: "Employee_Deativated".localized(), text: deactviated))
            self.tableView.reloadData()
        }) { (error) in
            print(error.localizedDescription)
        }
    }
    
    fileprivate func toDateString(_ format: String, withString: String, data: String) -> String {
        var toString: String?
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = format
        let dateString = dateFormatter.date(from: data)
        dateFormatter.dateFormat = withString
        toString = dateFormatter.string(from: dateString!)
        
        return toString!
    }
    
    fileprivate func calculateWorkOfYear(_ data: String) -> String {
        var yearToString: String?
        let formatter = DateFormatter()
        let yearStartWork = Int(self.toDateString("yyyy-MM-dd", withString: "yyyy", data: data))
        
        formatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let myString = formatter.string(from: Date())
        let yourDate = formatter.date(from: myString)
        formatter.dateFormat = "yyyy"
        let currentWork = Int(formatter.string(from: yourDate!))
        yearToString = String(format: "%d", (currentWork! - yearStartWork!))
        
        return yearToString!
    }

}

