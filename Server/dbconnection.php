<?php

$db_host = "localhost";
$db_user = "root";
$db_pass = null;
$db_name = "currencyconverterdb";

$mysqli = new mysqli($db_host, $db_user, $db_pass, $db_name); //creating the connection with the database 

if(mysqli_connect_errno()){
    die("Conenction Failed!");
}

?>