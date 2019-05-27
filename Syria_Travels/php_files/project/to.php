<?php
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
	
        $query        =mysqli_query($con," SELECT distinct t_to FROM trip_info");
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
        	$post["t_to"]       = $rows["t_to"];
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