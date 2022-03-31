<?php
//Get API of specific rate
include("dbinfo.php");

$rate = $_GET["rate"]; 

$query = $mysqli->prepare("SELECT * FROM converter WHERE rate = ?");
$query->bind_param("i", $rate);
$query->execute();

$array = $query->get_result();

$response = [];

while($course = $array->fetch_assoc()){
    $response[] = $course;
}

$json_response = json_encode($response);
echo $json_response;

?>