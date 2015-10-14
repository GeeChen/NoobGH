<?php include "connection.php"; ?>

		<html>  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
		 
		<title>Smart Queuing System</title>
		<link rel="stylesheet" href="style.css" type="text/css" />
		</head>  
		<body>  
		<div id="main">
		
		<?php
	
if(!empty($_POST['adminID']) && !empty($_POST['adminPass']))
{
    $username = mysqli_real_escape_string($con, $_POST['adminID']);
    $password = md5(mysqli_real_escape_string($con, $_POST['adminPass']));
     
     $checkusername = mysqli_query($con, "SELECT * FROM ADMINISTRATOR WHERE AdminID = '".$username."'");
      
     if(mysqli_num_rows($checkusername) == 1)
     {
        echo "<h1>Error</h1>";
        echo "<p>Sorry, You are not authorized to create an administrator account. Please <a href=\"admin-register.php\">go back and try again.</p>"; 
     }
     else
     {
        $registerquery = mysqli_query($con, "INSERT INTO ADMINISTRATOR (AdminID, AdminPass) VALUES('".$username."', '".$password."')");
        if($registerquery)
        {
            echo "<h1>Success</h1>";
            echo "<p>Your account was successfully created. Please <a href=\"admin-login.php\">click here to login</a>.</p>";
        }
        else
        {
            echo "<h1>Error</h1>";
            echo "<p>Sorry, your registration failed. Please <a href=\"admin-register.php\">go back and try again.</p>";    
        }       
     }
}
else
{
    ?>
     
   <h1>Register</h1>
     
   <p>Please enter your details below to register.</p>
     
    <form method="post" action="admin-register.php" name="registerform" id="registerform">
    <fieldset>
        <label for="username">AdminID:</label><input type="text" name="adminID" id="adminPass" /><br />
        <label for="password">AdminPass:</label><input type="password" name="adminPass" id="adminPass" /><br />
        <input type="submit" name="register" id="register" value="Register" />
    </fieldset>
    </form>
     
    <?php
}
?>
 
</div>
</body>
</html>