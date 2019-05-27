<?php
mysql_connect("localhost","root","")
	or die("no connection");

mysql_select_db("u168268997_news");

$sqlI=mysql_query("select * from users 
            WHERE 
                username ='".$_POST['username']."' AND password='".$_POST['password']."'");
if(!$sqlI){
	echo "Could not successfully run query ($sql) from DB:".mysql_error();
	exit;
}
$row=mysql_fetch_assoc($sqlI);


if ($row) 
        {
                $response["success"] = 1;
                $response["message"] = "Login successful!";
                die(json_encode($response));
            
        }
        else
        {
            $response["success"] = 0;
            $response["message"] = "login faild!";
            die(json_encode($response));
        }	
mysql_close;

?>