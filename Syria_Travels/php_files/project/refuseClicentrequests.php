<?php  
$json = file_get_contents('php://input');
$obj = json_decode($json);
$flightid = $obj->{'f_id'};
$clientid = $obj->{'c_id'};

//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");

mysqli_query($con,"UPDATE reservations SET r_status='refuse' WHERE r_client_id='$clientid' and r_flight_id='$flightid'");
//mysqli_close($con);

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>