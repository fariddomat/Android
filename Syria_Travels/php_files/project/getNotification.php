<?php
@header('Content-Type: text/html; charset=utf-8');
$json = file_get_contents('php://input');
$obj = json_decode($json);
$c_id=$obj->{'id'};
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
	
        $query        =mysqli_query($con," SELECT * FROM exchanging_message e,companies c where c.c_id=e.m_company_id and e.m_client_id='$c_id' order by e.m_id desc");
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
        	$post["name"]       = $rows["c_name"];
			$icon=$rows["c_icon"];
            $post["icon"]       ="$icon";
            
        	$post["date"]       = $rows["m_date"];
        	$post["time"]       = $rows["m_time"];
        	$post["details"]       = $rows["m_details"];
        	$post["m_is_read"]       = $rows["m_is_read"];
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}
$query        =mysqli_query($con,"UPDATE exchanging_message set m_is_read='yes' where m_client_id='$c_id' ");
	
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