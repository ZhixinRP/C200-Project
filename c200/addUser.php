<?php

include "dbFunctions.php";


if (isset($_POST)) {

    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    $insertQuery = "INSERT INTO user(username, email, password) 
                VALUES  
                ('$username','$email','$password')";
    echo $insertQuery;
    $status = mysqli_query($link, $insertQuery) or die(mysqli_error($link));

    if ($status) {
        $response["success"] = "1";
    } else {
        $response["success"] = "0";
    }
    echo json_encode($response);
}
