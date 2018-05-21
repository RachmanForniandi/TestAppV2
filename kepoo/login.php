<?php 

require_once ('include/DB_Function.php');
$db = new DB_Function();

$response = array('error' => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {
	$email = $_POST['email'];
	$password = $_POST['password'];

	$user = $db->getUserByEmailAndPassword($email, $password);

	if ($user != false) {
		$response["error"] = FALSE;
		$response["uid"] = $user["unique_id"];
		$response["user"]["name"] = $user["name"];
		$response["user"]["email"] = $user["email"];
		echo json_encode($response);
	}else{
		$response["error"] = TRUE;
		$response["error_msg"] = "Access denied. your Email/Password Wrong";
		echo json_encode($response);
	}
}else{
	$response["error"] = TRUE;
		$response["error_msg"] = "Some Parameter (Email or Password) is missing";
		echo json_encode($response);
}

?>