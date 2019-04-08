<?php
function checkIndexHTML(){
	$pathXAMPP="C:/xampp/apache/conf/httpd.conf";
	$pathIndexPHP1="C:/xampp/htdocs/bootstrap/index.html";
	$pathIndexPHP2="C:/xampp/htdocs/bootstrap/docs/index.html";
	$contents=file_get_contents($pathXAMPP);
	preg_match('/\"C:\/xampp\/htdocs.*\"/', $contents, $matches);	
	if(file_exists($pathIndexPHP1)){
		if($matches[0]=="\"C:/xampp/htdocs/bootstrap/docs\""){
			$output=str_replace($matches[0],"\"C:/xampp/htdocs/bootstrap\"",$contents);
			file_put_contents($pathXAMPP,$output);
			exec("httpd -k restart");
		}
	}
	else if(file_exists($pathIndexPHP2)){
		if($matches[0]=="\"C:/xampp/htdocs/bootstrap\""){
			$output=str_replace($matches[0],"\"C:/xampp/htdocs/bootstrap/docs\"",$contents);
			file_put_contents($pathXAMPP,$output);
			exec("httpd -k restart");
		}
	}
}
checkIndexHTML();
?>