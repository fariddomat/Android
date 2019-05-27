<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$option =$obj->{'option'};

@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");
    if($option=='company_history'){
    mysqli_query($con,"SET NAMES 'utf8'");
    $query        =mysqli_query($con," SELECT * FROM companies,event_history where c_id=event_actor_id and event_action 
    in ('c_login','add_trip','update_trip','add_employee','remove_employee') order by event_id desc");
        
	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}

	$rows =mysqli_fetch_array($query);
	if ($rows) 
	{
        //$response["success"] = 1;
    	//$response["message"] = "flights Available!";
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["name"]         = $rows["c_name"];
        	$post["action"]       = $rows["event_action"];
        	$post["date"]   = $rows["event_date"];
        	$post["time"]   = $rows["event_time"];
        	$post["details"]   = $rows["event_details"];
        	$post["icon"]   = $rows["c_icon"];
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    	$response["success"] = 0;
    	$response["message"] = "No data Available!";
    	die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($con);



}

else if($option=='employee_history'){
    
        $c_id =$obj->{'c_id'};
        $query        =mysqli_query($con," SELECT * FROM employee,event_history where e_comapny_id='$c_id' 
        and e_id=event_actor_id and event_action in ('e_login','e_logout') order by event_id desc");


	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}

	$rows =mysqli_fetch_array($query);
	if ($rows) 
	{
        //$response["success"] = 1;
    	//$response["message"] = "flights Available!";
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["name"]         = $rows["e_username"];
        	$post["action"]       = $rows["event_action"];
        	$post["date"]   = $rows["event_date"];
        	$post["time"]   = $rows["event_time"];
        	$post["details"]   = $rows["event_details"];
        	$post["icon"]   = "abc.png";
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    	//$response["success"] = 0;
    	//$response["message"] = "No flights Available!";
    	//die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($con);
}

else if($option=='reservation_history'){
    
        $c_id =$obj->{'c_id'};
        $query        =mysqli_query($con," SELECT * FROM employee,event_history where e_comapny_id='$c_id' 
        and e_id=event_actor_id and event_action in ('void_reservation','confirm_reservation','cancel_reservation') order by event_id desc");


	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}

	$rows =mysqli_fetch_array($query);
	if ($rows) 
	{
        //$response["success"] = 1;
    	//$response["message"] = "flights Available!";
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["name"]         = $rows["e_username"];
        	$post["action"]       = $rows["event_action"];
        	$post["date"]   = $rows["event_date"];
        	$post["time"]   = $rows["event_time"];
        	$post["details"]   = $rows["event_details"];
        	$post["icon"]   = "abc.png";
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    	//$response["success"] = 0;
    	//$response["message"] = "No flights Available!";
    	//die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($con);
}
else if($option=='client_history'){
    
        $query        =mysqli_query($con," SELECT * FROM clients,event_history where client_id=event_actor_id and event_action in 
        ('login','logout','signup') order by event_id desc");


	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}

	$rows =mysqli_fetch_array($query);
	if ($rows) 
	{
        //$response["success"] = 1;
    	//$response["message"] = "flights Available!";
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["name"]         = $rows["client_username"];
        	$post["action"]       = $rows["event_action"];
        	$post["date"]   = $rows["event_date"];
        	$post["time"]   = $rows["event_time"];
        	$post["details"]   = $rows["event_details"];
        	$post["icon"]   = "abc.png";
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    	//$response["success"] = 0;
    	//$response["message"] = "No flights Available!";
    	//die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($con);
}

?>