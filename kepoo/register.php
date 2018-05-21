<?php 

require_once ('include/DB_Function.php');
$db = new DB_Function();

$response = array('error' => FALSE);

if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {
 
    // menerima parameter POST ( nama, email, password )
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
 
    // Cek jika user ada dengan email yang sama
    if ($db->isUserExisted($email)) {
        // user telah ada
        $response["error"] = TRUE;
        $response["error_msg"] = "User has already in with email " . $email;
        echo json_encode($response);
    } else {
        // buat user baru
        $user = $db->saveUser($name, $email, $password);
        if ($user) {
            // simpan user berhasil
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            echo json_encode($response);
        } else {
            // gagal menyimpan user
            $response["error"] = TRUE;
            $response["error_msg"] = "Some problem occured on registratrion process";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Some Parameter (name, email, or password) is missing";
    echo json_encode($response);
}
?>