<?php  
$json = file_get_contents('php://input');
$obj = json_decode($json);
$flightid = $obj->{'f_id'};
$clientid = $obj->{'c_id'};


$e_id = $obj->{'e_id'};
$company_id = $obj->{'company_id'};
$m_date = $obj->{'m_date'};
$m_time = $obj->{'m_time'};
//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");

mysqli_query($con,"UPDATE reservations SET r_cancel_request='yes' WHERE r_client_id='$clientid' and r_trip_id='$flightid'");


$client_name=mysqli_query($con,"select * from reservation where _flight_id='$flightid' and r_client_id='$clientid'");
		$c=mysqli_fetch_array($client_name);
		$passenger=$c["r_client_fname"].' '.$c["r_client_lname"];
    $details='your employee accept reservation for passenger '.$passenger.' on trip '.$flightid;


mysqli_query($con,"INSERT INTO exchanging_message(m_id, m_date, m_time, m_company_id, m_employee_id, m_client_id, m_details)
 VALUES (Null,'$m_date','$m_time','$company_id','$e_id','$clientid','accepted')");

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id)
 VALUES (Null,'$m_date','$m_time','cancel_reservation','$details','$e_id')");
//mysqli_close($con);

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>