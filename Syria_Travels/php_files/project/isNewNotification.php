<?php
@header('Content-Type: text/html; charset=utf-8');
$json = file_get_contents('php://input');
$obj = json_decode($json);
 $c_id=$obj->{'c_id'};
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
	
        $query        =mysqli_query($con," SELECT * FROM exchanging_message where m_client_id='$c_id' and m_is_read='no'");
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
        	$post["new"]       = "yes";
            
        	array_push($response["posts"], $post);
    	
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    	$response["success"] = 0;
    	$response["message"] = "No News Available!";$response["posts"]   = array();
        $post                 = array();
        	$post["new"]       = "no";
            
        	array_push($response["posts"], $post);
    	die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($con);
?>			