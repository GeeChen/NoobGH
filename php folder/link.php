<?php
	
	include "connection.php";
	 
	$num = $_POST ["ticnum"];
	$query = mysqli_query{$con, "UPDATE TICKET set TicketServe='1' where TicketNo='$num'"}
	
	if($query){
		
		echo 1;		
	}
	
	else{
		
		echo 0;
	}
	
>?