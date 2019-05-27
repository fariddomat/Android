<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$username = $obj->{'username'};
$password = $obj->{'password'};


$m_date = $obj->{'c_date'};
$m_time = $obj->{'c_time'};
//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

    mysqli_query($con,"SET NAMES 'utf8'");

$sqlI=mysqli_query($con,"select * from clients 
            WHERE 
                client_username ='$username' and client_password='$password'");
if($sqlI){
$row=mysqli_fetch_assoc($sqlI);
if ($row) 
        {        
                if($row["isAdmin"]=="yes"){
                $response["success"] = 1;
                $response["message"] = "Admin Login successful!";
				$response["client_id"]=$row["client_id"];
                $id=$response["client_id"];
                
                }
                else{
                    $response["success"] = 3;
					$response["message"] = "Client Login successful!";
					$response["client_id"]=$row["client_id"];
                    $id=$response["client_id"];
                } 
            $details='login to system : '.$username;

            mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id)
            VALUES (Null,'$m_date','$m_time','login','$details','$id')");
                die(json_encode($response));
                }
        }

$sqlI=mysqli_query($con,"select * from employee 
            WHERE 
                e_username ='$username' and e_password='$password'");
if($sqlI){
$row=mysqli_fetch_assoc($sqlI);
if ($row) 
        {  
					
				$response["success"] = 4;
                $response["message"] = "employee Login successful!";
				$response["e_id"]=$row["e_id"];
                $response["company_id"]=$row["e_comapny_id"];
                $id=$response["e_id"];
                $details='login to system : '.$username;

            mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id)
            VALUES (Null,'$m_date','$m_time','e_login','$details','$id')");
                die(json_encode($response));}
}
$sqlI=mysqli_query($con,"select * from companies 
            WHERE 
                c_username ='$username' and c_password='$password'");
if($sqlI){
$row=mysqli_fetch_assoc($sqlI);
if ($row) 
        {        
                $response["success"] = 2;
                $response["message"] = "Company Login successful!";
                $response["c_id"]=$row["c_id"];
                $response["c_name"]=$row["c_name"];
                $response["c_type"]=$row["c_type"];
                $id=$response["c_id"];
                $details='login to system : '.$username;

            mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id)
            VALUES (Null,'$m_date','$m_time','c_login','$details','$id')");
                die(json_encode($response));
        }
}
$response["success"] = 0;
$response["message"] = "Login faild!";
die(json_encode($response));
mysqli_close($con);
?>