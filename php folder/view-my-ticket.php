<?php

    include("connection.php");
    
    $userid = $_POST['username'];
    $query = "select * from TICKET where UserID='$userid' and TicketServe='0' and TicketDeleted='0'";
    $result = mysqli_query($con, $query);
    
    $numrow = mysqli_num_rows($result);

    if ($numrow == 1) {
        // Successful
        $row = mysqli_fetch_assoc($result);
        $response = array("code" => 1, "tic" => $row["TicketNo"]);
    } else {
        // Failed
        $response = array("code" => 0);
    }
    
    echo json_encode($response);
?>