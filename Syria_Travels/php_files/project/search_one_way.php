<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$f_from = $obj->{'f_from'};
$f_to = $obj->{'f_to'};
$f_departure_date = $obj->{'f_departure_date'};
$type = $obj->{'type'};
$c_type = $obj->{'c_type'};
$adult_num = $obj->{'adult_num'};
$child_num = $obj->{'child_num'};
//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

    mysqli_query($con,"SET NAMES 'utf8'");

$sqlI=mysqli_query($con,"select * from trip_info t, companies c
            WHERE c.c_id=t.t_company_id and
                t.t_from ='$f_from' and t.t_to='$f_to' and t.t_departure_date='$f_departure_date' and t.t_status ='available' and c.c_type='$c_type'");
if(!$sqlI) 
	{
		$response["success"] = 0;
                $response["message"] = "database error!";
                die(json_encode($response,  JSON_UNESCAPED_UNICODE));	
	}
$row=mysqli_fetch_assoc($sqlI);
if ($row) 
        {        
                $response["success"] = 1;
                $response["message"] = "find flight!";
                $response["posts"]   = array();
                while ($row) {
        	$post= array();
        	
                $post["f_id"]=$row["t_id"];
                
                $post["f_company_id"]=$row["t_company_id"];
                $post["f_departure_time"]=$row["t_departure_time"];
                $post["f_duration"]=$row["t_duration"];
                $post["f_econ_seats"]=$row["t_econ_seats"];
                $post["f_first_seats"]=$row["t_first_seats"];
                if($type=='Economy'){
                        
                        $post["price"]=$row["t_econ_price"];
                }else{       
                        $post["price"]=$row["t_first_price"];
                }
                $post["f_child_price"]=$row["t_child_price"];
                $post["f_status"]=$row["t_status"];
                
                $post["f_total_weight_value"]=$row["t_total_weight_value"];
                $post["f_extra_weight_price"]=$row["t_extra_weight_price"];
                $post["c_name"]=$row["c_name"];
                $icon=$row["c_icon"];
                $post["icon"]="$icon";
                array_push($response["posts"], $post);
                $row =mysqli_fetch_array($sqlI);
                }
                echo json_encode($response,  JSON_UNESCAPED_UNICODE);;
        }
else 
    {
    	$response["success"] = 0;
    	$response["message"] = "No flight Available!";
    	die(json_encode($response,  JSON_UNESCAPED_UNICODE));
	}

mysqli_close($con);
?>