<?php
	
	require '../models/Employee.php';

	class ControllerEmployee {

		private $db;
		private $db_path;
		private $pdo;
		
		public function __construct($db_path) {
			$this->db_path = $db_path;
			require_once $this->db_path;
			$this->db = new DB_Connect();
			$this->pdo = $this->db->connect();
		}

		public function __destruct() { }

		public function getEmployeeByEmployeeId($employeeId) {
			$stmt = $this->pdo->prepare("SELECT * 
										FROM hr_employee
										WHERE id = :employeeId");

			$result = $stmt->execute(
								array('employeeId' => $employeeId) );

			foreach ($stmt as $row) {
				$item = new Employee();
				$item->id 			= $row['id'];
				$item->empCode 		= $row['empCode'];
				$item->empNameKh 	= $row['empNameKh'];
				$item->empNameEng 	= $row['empNameEng'];
				$item->dob 			= $row['dob'];
				$item->sex 			= $row['sex'];
				$item->phone 		= $row['phone'];
				$item->jobTitle 	= $row['jobTitle'];
				$item->kamPrak 		= $row['kamPrak'];
				$item->spouses 		=  $row['spouses'];
				$item->children 	= $row['children'];
				$item->deptNo 		= $row['deptNo'];
				$item->hiredDate 	= $row['hiredDate'];
				$item->contract 	= $row['contract'];
				$item->deactivated 	= $row['deactivated'];
				$item->photo 		= $row['photo'];

				return $item;
			}

			return null;
		}

		public function getEmployees() {
			$stmt = $this->pdo->prepare("SELECT * 
										FROM hr_employee");

	        $stmt->execute();

	        $array = array();
	        $ind = 0;
	        foreach ($stmt as $row) {
	            $item = new Employee();
				$item->id 			= $row['id'];
				$item->empCode 		= $row['empCode'];
				$item->empNameKh 	= $row['empNameKh'];
				$item->empNameEng 	= $row['empNameEng'];
				$item->dob 			= $row['dob'];
				$item->sex 			= $row['sex'];
				$item->phone 		= $row['phone'];
				$item->jobTitle 	= $row['jobTitle'];
				$item->kamPrak 		= $row['kamPrak'];
				$item->spouses 		=  $row['spouses'];
				$item->children 	= $row['children'];
				$item->deptNo 		= $row['deptNo'];
				$item->hiredDate 	= $row['hiredDate'];
				$item->contract 	= $row['contract'];
				$item->deactivated 	= $row['deactivated'];
				$item->photo 		= $row['photo'];

	            $array[$ind] = $item;
	            $ind++;
	        }
	        return $array;
		}

	}

?>