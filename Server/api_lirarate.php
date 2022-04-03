<?php
 
// Initialize curl
$ch = curl_init();
 
// URL for Scraping
curl_setopt($ch, CURLOPT_URL,
    'https://lirarate.org/');
 
    curl_setopt($ch, CURLOPT_URL,  
    

    'https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t20224321');
// Return Transfer True
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
$output = curl_exec($ch);
 
// Closing cURL
curl_close($ch);

$array = json_decode($output, true);

$size= count($array["sell"]);
$rate_array=$array["sell"][$size-1];
$rate= end($rate_array);
$result=[];
$result["rate"]=$rate;
$json_response = json_encode($result);
echo $json_response;

 
?>