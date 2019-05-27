<?php
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
	
        $query        =mysqli_query($con," SELECT * FROM companies");
	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}

	$rows =mysqli_fetch_array($query);
	if ($rows) 
	{
        $response["success"] = 1;
    	$response["message"] = "News Available!";
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["c_id"]         = $rows["c_id"];
        	$post["c_name"]       = $rows["c_name"];
        	$post["c_username"]   = $rows["c_username"];
        	$post["c_password"]   = $rows["c_password"];
            $post["c_details"]    = $rows["c_details"];
			$icon=$rows["c_icon"];
            $post["c_icon"]       ="$icon";
            
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    	$response["success"] = 0;
    	$response["message"] = "No News Available!";
    	die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($con);
?>			