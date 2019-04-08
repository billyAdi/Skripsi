<?php

function createDatabase(){
	$servername = "localhost";
	$username = "root";
	$password = "";
	
	$conn = new mysqli($servername, $username, $password);

	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
	
	$sql = "CREATE DATABASE piktora";
	$conn->query($sql);
 
	$conn->close();
}

function dropDatabase(){
	$servername = "localhost";
	$username = "root";
	$password = "";
	
	$conn = new mysqli($servername, $username, $password);

	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
	
	$sql = "DROP DATABASE piktora";
	$conn->query($sql);
 
	$conn->close();
}

function checkDatabaseConfig(){
	$path="C:/xampp/htdocs/Piktora/httpdocs/application/config/database.php";

	if(file_exists($path)){
		$contents=file_get_contents($path);
		preg_match('/\'password\'.*,/', $contents, $matches);
		$expected_password="'password' => 'dmHx64%6',";
	
		if(count($matches)>0&&$matches[0]==$expected_password){
			$output=str_replace($matches[0],"'password' => 'piktora',",$contents);
			file_put_contents($path,$output);
		}
	}else{
		$contents=file_get_contents("C:/xampp/htdocs/Piktora/www/application/config/database-dev.php");
		$file = fopen("C:/xampp/htdocs/Piktora/www/application/config/database.php", "w");
		fwrite($file, $contents);
		fclose($file);
	}
}

function checkIndexPHP(){
	$pathXAMPP="C:/xampp/apache/conf/httpd.conf";
	$pathIndexPHP1="C:/xampp/htdocs/Piktora/httpdocs/index.php";
	$pathIndexPHP2="C:/xampp/htdocs/Piktora/www/index.php";
	$contents=file_get_contents($pathXAMPP);
	preg_match('/\"C:\/xampp\/htdocs.*\"/', $contents, $matches);	
	if(file_exists($pathIndexPHP1)){
		if($matches[0]=="\"C:/xampp/htdocs/Piktora/www\""){
			$output=str_replace($matches[0],"\"C:/xampp/htdocs/Piktora/httpdocs\"",$contents);
			file_put_contents($pathXAMPP,$output);
			exec("httpd -k restart");
			//sleep(3);
		}
	}
	else if(file_exists($pathIndexPHP2)){
		if($matches[0]=="\"C:/xampp/htdocs/Piktora/httpdocs\""){
			$output=str_replace($matches[0],"\"C:/xampp/htdocs/Piktora/www\"",$contents);
			file_put_contents($pathXAMPP,$output);
			exec("httpd -k restart");
			//sleep(3);
		}
	}
}

function migrateDatabase(){
	$curl = curl_init("http://localhost/migrate");
	curl_exec($curl);
	curl_close($curl);
}

dropDatabase();
createDatabase();
checkDatabaseConfig();
checkIndexPHP();
migrateDatabase();
?>