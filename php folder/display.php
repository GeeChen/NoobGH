<?php
    ob_start();

    include "ticket.php";

    ob_end_clean();

?>

<!DOCTYPE html>
<html lang="en">

  <head>
    <meta charset="utf-8">
    <title>Smart Queuing System</title>
    <link rel="stylesheet" href="http://queuingserver.esy.es/adminside.css">
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:100,300,400">
  </head>

  <body>

    <!-- Header -->

    <header class="primary-header container group">

      <h1 class="logo">
        <b><font color="white">Welcome To <br> Smart Queuing System</font></b>
      </h1>

    </header>
	
	<!-- Lead -->

    <section class="row-alt">
      <div class="lead container">
	
		<div id="page" align="center">

		<form method="post" style="width:800px" >
            <br><h1>Your queue number is :</h1></br>
            <br><input type="text" size=10 name="displaytic" id="displaytic" maxlength="15" style="height:80px ; text-align: center;" 
			   value="<?php
                 
				 echo $response["tic_num"];
     
                    ?>"
				
                /></br>
			
			  			
			
			<br><h3>Please be seated. You will be serve shortly.</h3></br>
	
	</form>
	
      </div>
    </section>

    <!-- Footer -->

    <footer class="primary-footer container group">

      <small>Established at year 2015</small>

    </footer>

  </body>
</html>