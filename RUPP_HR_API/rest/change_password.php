<?php

	require '../controllers/ControllerUser.php';
	$controller = new ControllerUser('../config/DB_Connect.php');

	if( !empty($_POST['idx']) )
        $idx = $_POST['idx'];

    if( !empty($_POST['currentPassword']) )
        $currentPassword = $_POST['currentPassword'];

    if( !empty($_POST['newPassword']) )
        $newPassword = $_POST['newPassword'];

    if( !empty($_POST['retypePassword']) )
        $retypePassword = $_POST['retypePassword'];

    if( !empty($idx) && !empty($currentPassword) && !empty($newPassword) && !empty($retypePassword) ) {
      
      	$json_result = array();

      	$user = $controller->getUserByUserId($idx);
 		if ( !empty($user) ) {
 			if ( $user->password === $currentPassword) {
 				$json_result = array("code"=>1, "record"=>$user);
 				if ( $newPassword === $retypePassword) {

 					$itm = new User();
			        $itm->password = $newPassword;
			        $itm->status = 1;
			        $itm->id = $idx;

			        if($user != null) {
			        	$itm->id = $user->id;
			        	$controller->updatePassword($itm);

			        	$result = $controller->getUserByUserId($idx);

			        	$json_result = array("code"=>1, "record"=>$result);
			        }
 				} else {
 					$json_result = array("code"=>0, "message"=>"លេខសម្ងាត់ថ្មី នឹងលេខសម្ងាត់បញ្ជាក់មិនត្រឹមត្រូវទេ ។");
 				}
 			} else {
 				$json_result = array("code"=>0, "message"=>"លេខសម្ងាត់ចាស់របស់អ្នកមិនត្រឹមត្រូវទេ ។");
 			}
 		} else {
 			$json_result = array("code"=>0, "message"=>"ការផ្ទៀងផ្ទាត់បរាជ័យក្នុងការចូលដំណើរការ ។");
 		}

  	} else {
      	$json_result = array("code"=>0, "message"=>"ការចូលដំណើរការមិនត្រឹមត្រូវ");
    }

 	// Translate data to JSON object
  	echo json_encode($json_result, JSON_UNESCAPED_UNICODE);

?>