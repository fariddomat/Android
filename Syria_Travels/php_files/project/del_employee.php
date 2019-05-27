<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$e_id = $obj->{'e_id'};

$companyid = $obj->{'companyid'};


$m_date = $obj->{'c_date'};
$m_time = $obj->{'c_time'};
//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
  
mysqli_query($con,"DELETE FROM employee WHERE e_id='$e_id' ");

$details='delete employee';

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id) 
VALUES (Null,'$m_date','$m_time','remove_employee','$details','$companyid')");
              
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>