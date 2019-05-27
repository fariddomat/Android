<?php
mysql_connect("localhost","root","")
	or die("no connection");

mysql_select_db("u168268997_news");

$sqlI=mysql_query("select * from users 
            WHERE 
                username ='".$_GET['username']."'");
if(!$sqlI){
	$response["success"] = 0;
    $response["message"] = "Database Error1. Please Try Again!";
    die(json_encode($response)); 
}
$row=mysql_fetch_assoc($sqlI);


if ($row) 
        {        
            if ($_GET['password'] === $row['password']) 
            {
                $response["success"] = 1;
                $response["message"] = "Login successful!";
                die(json_encode($response));
            }
            else
            {
                $response["success"] = 0;
                $response["message"] = "Invalid password!";
                die(json_encode($response));
            }
        }
        else
        {
            $response["success"] = 0;
            $response["message"] = "this username in not in our database!";
            die(json_encode($response));
        }	
mysql_close;

?>