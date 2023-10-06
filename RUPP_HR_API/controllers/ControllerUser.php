<?php

	require '../models/User.php';

	class ControllerUser {
		
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

		public function loginUser($username, $password) {
			$stmt = $this->pdo->prepare('SELECT * 
                                		FROM hr_user 
                                 		WHERE username = :username AND password = :password');

	        $result = $stmt->execute(
	                            array('username' => $username,
	                                    'password' => $password ) );

	        foreach ($stmt as $row) {
	            $itm = new User();
	            $itm->id 				= $row['id'];
	            $itm->employeeId 		= $row['employeeId'];
	            $itm->username 			= $row['username'];
	            $itm->password 			= $row['password'];
	            $itm->status 			= $row['status'];
	            $itm->deactivated 		= $row['deactivated'];
	            $itm->deactivatedDate 	= $row['deactivatedDate'];

	            return $itm;
	        }
	        return null;
		}

		public function updatePassword($item) {
			$stmt = $this->pdo->prepare('UPDATE hr_user SET password = :password, status = :status
										WHERE id = :id');

			$result = $stmt->execute(
									array('password' => $item->password, 
										'status' => $item->status,
										'id' => $item->id) );

			return $result ? true : false; 
		}

		public function getUserByUserId($employeeId) {
			$stmt = $this->pdo->prepare('SELECT * 
                                		FROM hr_user 
                                 		WHERE employeeId = :employeeId');

	        $result = $stmt->execute(
	                            array('employeeId' => $employeeId) );

	        foreach ($stmt as $row) {
	            $itm = new User();
	            $itm->id 				= $row['id'];
	            $itm->employeeId 		= $row['employeeId'];
	            $itm->password 			= $row['password'];
	            $itm->status 			= $row['status'];
	            $itm->deactivated 		= $row['deactivated'];
	            $itm->deactivatedDate 	= $row['deactivatedDate'];

	            return $itm;
	        }
	        return null;
		}
	}
?>