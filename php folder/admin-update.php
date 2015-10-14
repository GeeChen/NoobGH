<?php

    include "connection.php";

    if (isset($_POST["nextnumber"])) {
        $current_number = $_POST["displaytic"];
        $query = "update TICKET set TicketServe='1' where TicketNo='$current_number'";
        mysqli_query($con, $query);
    }

    ob_start();
    include "get_current_next.php";
    ob_end_clean();

    if ($response["code"] != -1 && array_key_exists("tic_5", $response)) {
        include "sms.php";

        $tnum = $response["tic_5"];
        $query = "SELECT USER.UserTel from USER inner join TICKET on USER.UserID = TICKET.USerID where TICKET.TicketNo = '$tnum'";

        $result = mysqli_query($con, $query);
        $row = mysqli_fetch_assoc($result);

        send_sms($row["UserTel"], $response["tic_0"], $response["tic_5"]);
    }
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

    <header>

      <h1 class="logo">
        <b><font color="white">Welcome To <br> Smart Queuing System</font></b>
      </h1>
	  
	  <h3 class="tagline"><font color="red">Administrator Use Only*</font></h3>
	  
	  <nav class="nav primary-nav">
        <a href="http://queuingserver.esy.es/admin-login.php">Logout</a>
      </nav>

    </header>

    <!-- Lead -->

    <section class="row-alt">
      <div class="lead container">
                        
    <form method="post" action="#">
            <br><font color="magenta"><h1>We are now serving number :</h1></font></br>
            <br><input type="text" size=10 name="displaytic" id="displaytic" maxlength="15" style="height:80px ; text-align: center;"
                    value="<?php
                        if ($response["code"] == -1) {
                            echo "No Ticket";
                        } else {
                            echo $response["tic_0"];
                        }
                    ?>"
                /></br>
            <br><input type="submit" name="nextnumber" id="nextnumber" value="Next Number"></br>           
    </form>
            
     </div>
    </section>

    <!-- Footer -->

    <footer class="primary-footer container group">

      <small>Established at year 2015</small>

    </footer>

  </body>
</html>