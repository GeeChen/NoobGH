<?php

    include "connection.php";

    $userid = $_POST['username'];
    $num = $_POST['ticnum'];

    $query = "update TICKET set TicketDeleted='1' where TicketNo='$num' and UserID='$userid'";
    $result = mysqli_query($con, $query);

    if ($result) {
        // Successful
        $response = array("code" => 1);
    } else {
        // Failed
        $response = array("code" => 0);
    }

    echo json_encode($response);

?>