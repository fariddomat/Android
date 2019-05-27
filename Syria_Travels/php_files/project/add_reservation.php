<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
//$r_id = $obj->{'r_id'};
$r_flight_id = $obj->{'r_flight_id'};
$r_client_id = $obj->{'r_client_id'};
$r_company_id = $obj->{'r_company_id'};
$r_client_fname = $obj->{'r_client_fname'};
$r_client_lname = $obj->{'r_client_lname'};
$r_client_national_number = $obj->{'r_client_national_number'};
$r_client_country = $obj->{'r_client_country'};
$r_client_city = $obj->{'r_client_city'};
$r_client_phone = $obj->{'r_client_phone'};
$r_date = $obj->{'r_date'};
$r_time = $obj->{'r_time'};
$r_seats_num = $obj->{'r_seats_num'};
$r_children_num = $obj->{'r_children_num'};
$r_extra_weight_value = $obj->{'r_extra_weight_value'};
$r_type = $obj->{'r_type'};
$r_card_number = $obj->{'r_card_number'};
$r_expire_date = $obj->{'r_expire_date'};
$r_card_svv = $obj->{'r_card_svv'};
$r_round_trip = $obj->{'r_round_trip'};
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
mysqli_query($con,"INSERT INTO reservations(r_id,r_trip_id,r_client_id,r_company_id,r_employee_id,r_client_fname,r_client_lname,
r_client_national_number, r_client_country, r_client_city, r_client_email_phone, r_date, r_time,
r_seats_num, r_children_num, r_extra_weight_value,r_type, r_status, r_cancel_request, r_card_number, r_expire_date, r_card_svv,r_round_trip)
 VALUES
  (NULL,'$r_flight_id','$r_client_id','$r_company_id',NULL,'$r_client_fname','$r_client_lname',
'$r_client_national_number','$r_client_country', '$r_client_city', '$r_client_phone', '$r_date', '$r_time',
'$r_seats_num', '$r_children_num', '$r_extra_weight_value','$r_type', 'hold', NULL, '$r_card_number', '$r_expire_date', '$r_card_svv','$r_round_trip')");
//mysqli_close($con);

//
  //$posts = array($json);
   header('Content-type: application/json');
    $response["success"] = 0;
$response["message"] = "add!";
die(json_encode($response)); 

  ?>