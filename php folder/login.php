<?php
	include "connection.php";
	
	if(isset($_POST["name"]))
	{
		$username = $_POST["name"];
	}
	
	if(isset($_POST["pass"]))
	{
		$userpass = $_POST["pass"];
	}
	
	$query = "SELECT * FROM USER WHERE UserID = '$username' AND UserPass = '$userpass'";

	if ( !($result=mysqli_query($con, $query)) )
	{
		echo "Query error.";
	}

	$num_rows = mysqli_num_rows($result);
	
	$response = array();
	
	if($num_rows == 1)
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