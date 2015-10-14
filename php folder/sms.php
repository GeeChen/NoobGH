<?php
    // include("connection.php");

    function send_sms($phoneNum, $current, $ticnumber){
        $user = "GeeChenGc";
        $password = "RLbZLGVPVHCScW";
        $api_id = "3558420";
        $baseurl ="http://api.clickatell.com";
     
        $msg = "We are now serving number $current, your ticket number is $ticnumber. Please come back soon to have your service. ";
        $text = urlencode($msg);
        $to = $phoneNum;
     
        // auth call
        $url = "$baseurl/http/auth?user=$user&password=$password&api_id=$api_id";
     
        // do auth call
        $ret = file($url);
     
        // explode our response. return string is on first line of the data returned
        $sess = explode(":",$ret[0]);
        
        if ($sess[0] == "OK") {
     
            $sess_id = trim($sess[1]); // remove any whitespace
            $url = "$baseurl/http/sendmsg?session_id=$sess_id&to=$to&text=$text";
     
            // do sendmsg call
            $ret = file($url);
            /*$send = explode(":",$ret[0]);
     
            if ($send[0] == "ID") {
                echo "successnmessage ID: ". $send[1];
            } else {
                echo "send message failed";
            }*/
        } else {
            echo "Authentication failure: ". $ret[0];
        }
    }

?>