<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$e_id = $obj->{'e_id'};

@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
		
		$company_id=mysqli_query($con,"select * from employee where e_id='$e_id'");
		$c=mysqli_fetch_array($company_id);
		$m=$c["e_comapny_id"];
        $query  =mysqli_query($con," select * from reservations r,trip_info t where r_company_id='$m' and r_status<>'canceled' and r_cancel_request='yes' and t.t_id=r.r_trip_id");
	
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
        	$post["r_flight_id"]        = $rows["r_trip_id"];
        	$post["r_client_id"]        = $rows["r_client_id"];
            
        	$post["r_status"]       = $rows["r_status"];
			
			$post["r_date"]       = $rows["r_date"];
			$post["r_time"]       = $rows["r_time"];
			$post["t_departure_date"]       = $rows["t_departure_date"];
			$post["t_departure_time"]       = $rows["t_departure_time"];
			$post["r_round_trip"]       = $rows["r_round_trip"];
			
			$post["r_type"]       = $rows["r_type"];
			$post["r_seats_num"]       = $rows["r_seats_num"];
			$post["r_children_num"]       = $rows["r_children_num"];
           
        	array_push($response["posts"], $post);
$rows =mysqli_fetch_array($query);
    	}

    	 echo json_encode($response,  JSON_UNESCAPED_UNICODE);
    }
    else 
    {
    
	}
	mysqli_close($con);


?>