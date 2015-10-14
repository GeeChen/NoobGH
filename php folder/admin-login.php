<?php include "connection.php"; ?>

		<html>  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		 
		<title>Smart Queuing System</title>
		<link rel="stylesheet" href="style.css" type="text/css" />
		</head>  
		<body>  
		<div id="main">
		
		<?php
	
	if(!empty($_SESSION['LoggedIn']) && !empty($_SESSION['adminID']))
	{
		 ?>
	 
		 <h1>Administrator Area</h1>
		 <p>Thanks for logging in! You are <code><?=$_SESSION['adminID']?></code>.</p>
		  
		 <?php
	}
	elseif(!empty($_POST['adminID']) && !empty($_POST['adminPass']))
	{
		$adminID = mysqli_real_escape_string($con,$_POST['adminID']);
		$adminPass = md5(mysqli_real_escape_string($con,$_POST['adminPass']));
		 
		$checklogin = mysqli_query($con,"SELECT * FROM ADMINISTRATOR WHERE AdminID = '".$adminID."' AND AdminPass = '".$adminPass."'");
		
		if(mysqli_num_rows($checklogin) == 1)
		{
			$row = mysqli_fetch_array($checklogin);

			 
			$_SESSION['adminID'] = $adminID;
			$_SESSION['LoggedIn'] = 1;
			 
			echo "<h1>Success</h1>";
			echo "<p>We are now redirecting you to the administrator area.</p>";
			echo "<meta http-equiv='refresh' content='2;admin-update.php' />";
		}
		else
		{
			echo "<h1>Error</h1>";
			echo "<p>Sorry, your account could not be found. Please <a href=\"admin-login.php\">click here to try again</a>.</p>";
		}
	}
	else
	{
		?>
		 
	   <h1>Administrator Login</h1>
		 
	   <p>Thanks for visiting! Please either login below, or <a href="admin-register.php">click here to register</a>.</p>
		 
		<form method="post" action="admin-login.php" name="loginform" id="loginform">
		<fieldset>
			<label for="adminID">AdminID:</label><input type="text" name="adminID" id="adminID" /><br />
			<label for="adminPass">AdminPass:</label><input type="password" name="adminPass" id="adminPass" /><br />
			<input type="submit" name="login" id="login" value="Login" />
		</fieldset>
		</form>
		 
	   <?php
	}
	?>
	 
	</div>
	</body>
	</html>