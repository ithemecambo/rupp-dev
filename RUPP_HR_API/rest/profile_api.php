<?php
	
	require '../controllers/ControllerEmployee.php';
  	$controller = new ControllerEmployee('../config/DB_Connect.php');

  	$json_result = array();

    // $token_key = "aNeN2jFTvkKS2QBXKY";
    // $api_key = $_GET['token_key'];
    
  	if (isset($_POST['idx']) && isset($_POST['token_key'])) {
  		$result = $controller->getEmployeeByEmployeeId($_POST['idx']);
  		if (!empty($result)) {
  			$json_result = array("code"=>1, "record"=>$result);
  		} else {
  			$json_result = array("code"=>0, "message"=>"Search employee not found!");
  		}
  	}

	// Translate data to JSON object
  	echo json_encode($json_result, JSON_UNESCAPED_UNICODE);

?>