<?php
//Post API
include("db_info.php");

$amount = $_POST["amount"];
$rate = $_POST["rate"];


$query = $mysqli->prepare("INSERT INTO converter (amount,rate) VALUES (?, ?)");
$query->bind_param("ss",$amount,$rate);
$query->execute();

$response = [];
$response["status"] = "Mabrouk!";

$json_response = json_encode($response);
echo $json_response;


?>