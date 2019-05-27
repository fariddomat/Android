<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$c_id =$obj->{'c_id'};

@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
	
        $query        =mysqli_query($con," SELECT * FROM employee where e_comapny_id='$c_id'");
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
        	$post["e_id"]         = $rows["e_id"];
        	$post["e_username"]       = $rows["e_username"];
        	$post["e_password"]   = $rows["e_password"];
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