<?php

    include("connection.php");
    
    date_default_timezone_set('Asia/Kuala_Lumpur');
    
    $datenow = date('Y-m-d H:i:s');
    
    $userid = $_POST['username'];
    $query = "select * from TICKET where UserID='$userid' and TicketServe='0' and TicketDeleted='0'";
    $result =  mysqli_query($con, $query);
    $num_rows = mysqli_num_rows($result);
    
    if ($num_rows == 0){
        mysqli_query($con, "insert into TICKET (TicketTime, UserID) values ('$datenow', '$userid')");
        $ticket_number = mysqli_insert_id($con);
        
        $response = array ("tic_num" => $ticket_number);

    }else {
         $response = array ("tic_num" => -1);    
    } 
        
    
    echo json_encode ($response);
?>