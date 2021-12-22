<?php

include "dbFunctions.php"; 


if (isset($_POST)) {

    $name = $_POST['name'];
    $gpa = $_POST['gpa'];

    $insertQuery = "INSERT INTO user(name, gpa) 
                VALUES  
                ('$name','$gpa')";
//echo $insertQuery;
    $status = mysqli_query($link, $insertQuery) or die(mysqli_error($link));

    if ($status) {
        $response["success"] = "1";
    } else {
        $response["success"] = "0";
    }
    echo json_encode($response);
}
