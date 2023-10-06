<?php
	
	require '../controllers/ControllerEmployee.php';
  	$controller = new ControllerEmployee('../config/DB_Connect.php');

  	$json_result = array();

  	if (isset($_GET['idx'])) {
  		$result = $controller->getEmployeeByEmployeeId($_GET['idx']);
  		if (!empty($result)) {
  			$json_result = array("code"=>1, "record"=>$result);
  		} else {
  			$json_result = array("code"=>0, "message"=>"Search employee not found!");
  		}
  	}

	// Translate data to JSON object
  	echo json_encode($json_result, JSON_UNESCAPED_UNICODE);

?>