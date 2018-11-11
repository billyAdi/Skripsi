<?php
$servername = "localhost";
$username = "root";
$password = "";

$conn = new mysqli($servername, $username, $password);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "DROP DATABASE piktora";
$conn->query($sql);
 
$sql = "CREATE DATABASE piktora";
$conn->query($sql);
 
$conn->close();
?>