<?php

	require '../controllers/ControllerUser.php';
  	$controller = new ControllerUser('../config/DB_Connect.php');

  	if( !empty($_POST['username']) )
    	$username = $_POST['username'];

  	if( !empty($_POST['password']) )
      	$password = $_POST['password'];

    if( !empty($username) && !empty($password) ) {
      
      $json_result = array();

      $user = $controller->loginUser($username, $password);
      if( !empty($user) ) {
          	// update the hash
          	$json_result = array("code"=>1, "record"=>$user);
    	} else {
      		$json_result = array("code"=>0, "message"=>"Invalid username & password or you are being denied to access. Please try again.");
    	}
  	} else {
      $json_result = array("code"=>0, "message"=>"Authentication failure to access.");
    }

     // Translate data to JSON object
     echo json_encode($json_result, JSON_UNESCAPED_UNICODE);

?>