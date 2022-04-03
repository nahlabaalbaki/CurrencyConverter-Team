<?php
 
// Initialize curl
$ch = curl_init();
 
// URL for Scraping
curl_setopt($ch, CURLOPT_URL,
    'https://lirarate.org/');
 

    // IMPORTANT : if this url does not work, then we need to go to lirarate.org and extract it again
    //from the Network inspect tab (It is getting updated due to the many requests made from it);
    curl_setopt($ch, CURLOPT_URL,  
    'https://lirarate.org/wp-json/lirarate/v2/rates?currency=LBP&_ver=t20224321');
// Return Transfer True
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
$output = curl_exec($ch);
 
// Closing cURL
curl_close($ch);


//the $ouptut received is a Json object so we start by decoding it.
$array = json_decode($output, true);

//The Json object above has two objects each containing two 2d arrays : "buy" and "sell".
//it contains all of the rates for buying and selling USD back since it was at 1500 LL only.
//However, we are only interested in the newest rate aka the last element in the list in the "sell" object

//to extract it, we start by saving the length of the "sell" array in the $sizez variable by using the count() method.
$size= count($array["sell"]);

//We know that the most recent rate is in the last array of the 2d array "sell", so we get it by using $array["sell"][$size-1];
$rate_array=$array["sell"][$size-1];

//Since every rate contains two elements, the id and the actual rate, we use end() to get the last element of the $rate_array,which
//is our desired rate;
$rate= end($rate_array);

//finally, we save the rate in the $result array, then we encode it as json object
//and we finally return it.
$result=[];
$result["rate"]=$rate;
$json_response = json_encode($result);
echo $json_response;

 
?>