<?php
 
header('Content-Type: application/json');
 



include("dbconnection.php");




$amount= $_GET["amount"];
$currency= $_GET["currency"];
$rate= 20000;

if ($currency=="lbp"){
$result=  $amount*$rate;
}
else
{
    $result= $amount/$rate;
}

$query = $mysqli->prepare("INSERT INTO users_info (amount,currency) VALUES (?, ?)");
$query->bind_param("is", $amount, $currency);
$query->execute();


$response = [];
$response["amount_result"]=$amount; 
$response["currency"]= $currency;
$response["result"]= $result; 
 $json_response = json_encode($response);
echo $json_response;
// Initialize curl
// $ch = curl_init();
 
// // URL for Scraping
// curl_setopt($ch, CURLOPT_URL,
//     'https://www.geeksforgeeks.org/matlab-data-types/');
 
// // Return Transfer True
// curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
// $output = curl_exec($ch);
 
// // Closing cURL
// curl_close($ch);
 
// // For web page display
// echo '<head>';
// echo '<meta http-equiv="content-type"
//     content="text/html; charset=utf-8" />';
// echo '</head>';
// echo '<body>';
 
// echo '<h1>Web Scraping using cURL</h1>';
 
// // Checking for images
// preg_match_all(
// '!https://media.geeksforgeeks.org/wp-content/uploads/(.*)/(.*).png!',
//     $output, $data
// );
 
// foreach ($data[0] as $list) {
//     echo "<img src='$list'/>";
// }
 
// echo '</body>';
 
?>