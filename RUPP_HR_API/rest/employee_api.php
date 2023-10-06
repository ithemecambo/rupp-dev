<?php
	
	require '../controllers/ControllerEmployee.php';
  	$controller = new ControllerEmployee('../config/DB_Connect.php');
  	$results = $controller->getEmployees();

  	$json_result = array();

  	if (!empty($results)) {
  		$json_result = array("code"=>1, "records"=>$results);
  	} else {
  		$json_result = array("code"=>1, "message"=>"Search employee not found!");
  	}

	// Translate data to JSON object
  	echo json_encode($json_result, JSON_UNESCAPED_UNICODE);

?>

