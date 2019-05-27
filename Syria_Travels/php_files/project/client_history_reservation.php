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
		
        $query  =mysqli_query($con," select * from reservations r,trip_info t,companies c where r.r_client_id='$c_id'
		and t.t_id=r.r_trip_id and r.r_company_id=c.c_id and t.t_status!='available'");
	
	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}

	$rows =mysqli_fetch_array($query);
	if ($rows) 
	{
        
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["flight_id"]        = $rows["r_trip_id"];
			$post["company_id"]        = $rows["c_id"];
        	$post["company_name"]        = $rows["c_name"];
        	$post["f_date"]     = $rows["t_departure_date"];
        	$post["f_time"]     = $rows["t_departure_time"];
            $post["f_from"]    = $rows["t_from"];
            $post["f_to"]       = $rows["t_to"];
           
            $post["c_icon"]       = $rows["c_icon"];
			$post["c_type"]       = $rows["c_type"];
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    $response["success"] = 0;
$response["message"] = "no data!";
die(json_encode($response));
	}
	mysqli_close($con);


?>