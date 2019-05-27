<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$from = $obj->{'from'};
$to = $obj->{'to'};
$departure_date =$obj->{'departure_date'};
$departure_time = $obj->{'departure_time'};
$duration = $obj->{'duration'};
$econ_sets = $obj->{'econ_sets'};
$first_sets = $obj->{'first_sets'};
$total_sets = $obj->{'total_sets'};
$econ_price = $obj->{'econ_price'};
$first_price = $obj->{'first_price'};
$child_price = $obj->{'child_price'};
$status = $obj->{'status'};
$total_weight = $obj->{'total_weight'};
$extra_weight_value = $obj->{'extra_weight_value'};
$c_id = $obj->{'c_id'};


$m_date = $obj->{'c_date'};
$m_time = $obj->{'c_time'};
//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
  /* grab the posts from the db */
  //$query = "SELECT post_title, guid FROM wp_posts WHERE 
  //  post_author = $user_id AND post_status = 'publish'
  // ORDER BY ID DESC LIMIT $number_of_posts";
mysqli_query($con,"INSERT INTO trip_info(t_id, t_from, t_to, t_departure_date, t_departure_time, t_arrival_time, t_duration, 
t_econ_seats, t_first_seats, t_seats, t_econ_price, t_first_price, t_child_price,
 t_round_sold, t_status, t_total_weight_value, t_extra_weight_price, t_company_id)
VALUES (NULL,'$from','$to','$departure_date','$departure_time','$departure_time','$duration',
'$econ_sets','$first_sets','$total_sets','$econ_price','$first_price','$child_price',
'2','$status','$total_weight','$extra_weight_value','$c_id')");

      $details='add trip from : '.$from.' to :'.$to;

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id) 
VALUES (Null,'$m_date','$m_time','add_trip','$details','$c_id')");
                
                
//mysqli_close($con);

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>