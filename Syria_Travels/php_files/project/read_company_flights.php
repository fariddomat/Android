<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$c_id = $obj->{'c_id'};

@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
	
        $query        =mysqli_query($con," SELECT * FROM trip_info where t_company_id='$c_id' and t_status ='available'");
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
        	$post["f_id"]         = $rows["t_id"];
        	$post["f_from"]       = $rows["t_from"];
        	$post["f_to"]   = $rows["t_to"];
        	$post["f_departure_date"]   = $rows["t_departure_date"];
            $post["f_departure_time"]    = $rows["t_departure_time"];
            $post["f_duration"]       = $rows["t_duration"];
            $post["f_econ_seats"]         = $rows["t_econ_seats"];
        	$post["f_first_seats"]       = $rows["t_first_seats"];
            $post["f_econ_price"]         = $rows["t_econ_price"];
        	$post["f_first_price"]       = $rows["t_first_price"];
        	$post["f_child_price"]       = $rows["t_child_price"];
            $post["f_status"]         = $rows["t_status"];
        	$post["f_total_weight_value"]       = $rows["t_total_weight_value"];
        	$post["f_extra_weight_price"]       = $rows["t_extra_weight_price"];
            
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


?>