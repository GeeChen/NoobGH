<?php

    include "connection.php";

    $query = "select * from TICKET where TicketServe='0' and TicketDeleted='0' order by TicketNo asc limit 6"; 
    $result = mysqli_query($con, $query);

    $numRows = mysqli_num_rows($result);
    if ($numRows > 0) {

        $response = array("code" => 1);

        $i = 0;
        while ($row = mysqli_fetch_assoc($result)) {
            $response["tic_" . $i] = $row["TicketNo"];
            $i++;
        }

    } else {
        $response = array("code" => -1);
    }

    echo json_encode($response);

    // $row = mysqli_fetch_assoc($result);

    // $response = array('current_tic' => $row["TicketNo"]);

    // echo mysqli_num_rows($result);

    // echo json_encode($response);
?>