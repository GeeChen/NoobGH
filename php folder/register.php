<?php

	include("connection.php");
	
	// Fetching variables of the form which travels in URL
	if(isset($_POST["name"]))
	{
		$username = $_POST["name"];
	}
	
	if(isset($_POST["pass"]))
	{
		$userpass = $_POST["pass"];
	}
	
	if(isset($_POST["contactNum"]))
	{
		$usertel = $_POST["contactNum"];
	}
		
	
	
	
	
	
	
	//Insert Query of SQL
	$query = mysqli_query($con, "insert into USER(UserID, UserPass, UserTel) values ('$username', '$userpass', '$usertel')");					
	
	$response = array();
	
	if($query)
	{
		$response ['code'] = 1;
	}
	else
	{
		$response ['code'] = 0;
	}
	
	$encoded = json_encode($response);
	
	echo $encoded;
		
	mysqli_close($con);
?>