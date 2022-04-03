<?php
 
header('Content-Type: application/json');
 


//We include our dbconnection file to be able to communicate with our database.
include("dbconnection.php");



//We start by receving the data sent to us in th url from the front-end and we save it into the corresponding
//variables. 
$amount= $_GET["amount"];
$currency= $_GET["currency"];
$rate= $_GET["rate"];

//Then we calculate the amount based on the currency sent. 
if ($currency=="lbp"){
$result=  $amount*$rate;
}
else
{
    $result= $amount/$rate;
}


//Then we save the amount and currency entered by the user into our database.
$query = $mysqli->prepare("INSERT INTO users_info (amount,currency) VALUES (?, ?)");
$query->bind_param("is", $amount, $currency);
$query->execute();


//finally, we create our response array in which we save the data needed.

$response = [];
$response["amount_result"]=$amount; 
$response["currency"]= $currency;
$response["rate"]= $rate;
//since $result is an int we use strval() to convert it into a string.
$response["result"]= strval($result); 
//We the encode the array as a json object and finally echo it.
 $json_response = json_encode($response);
echo $json_response;

 
?>