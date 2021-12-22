<?php

include "dbFunctions.php"; 

$user_id = $_REQUEST["user_id"];
// SQL query returns multiple database records.
$query = "SELECT * FROM user WHERE user_id=$user_id"; 
$result = mysqli_query($link, $query);

/*
while ($row = mysqli_fetch_assoc($result)) {
    $users[] = $row;
}
*/

$user = mysqli_fetch_assoc($result);

mysqli_close($link);

// return null if no result
if($user != null)
	echo json_encode($user);
else{
	echo "invalid_id";
}
?>
