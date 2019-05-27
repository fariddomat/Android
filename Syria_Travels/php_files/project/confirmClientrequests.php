<?php  
$json = file_get_contents('php://input');
$obj = json_decode($json);
$flightid = $obj->{'f_id'};
$clientid = $obj->{'c_id'};
$type=$obj->{'type'};
$number=$obj->{'number'};

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

mysqli_query($con,"UPDATE reservations SET r_status='confirmed' WHERE r_client_id='$clientid' and r_trip_id='$flightid'");
if($type=="r"){
  mysqli_query($con,"UPDATE trip_info SET t_econ_seats=t_econ_seats-'$number' WHERE t_id='$flightid'");
}else{
  mysqli_query($con,"UPDATE trip_info SET t_first_seats=t_first_seats-'$number' WHERE t_id='$flightid'");
}
mysqli_query($con,"INSERT INTO exchanging_message(m_id, m_date, m_time, m_company_id, m_employee_id, m_client_id, m_details)
 VALUES (Null,'$m_date','$m_time','$company_id','$e_id','$clientid','accepted')");

$client_name=mysqli_query($con,"select * from reservation where r_flight_id='$flightid' and r_client_id='$clientid'");
		$c=mysqli_fetch_array($client_name);
		$passenger=$c["r_client_fname"].' '.$c["r_client_lname"];
    $details='your employee accept reservation for passenger '.$passenger.' on trip '.$flightid;

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id)
 VALUES (Null,'$m_date','$m_time','confirm_reservation','$details','$e_id')");
//mysqli_close($con);

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 
$m_details="confirmed";
  $date=date("Y-m-d h:i:s");
  $url='https://fcm.googleapis.com/fcm/send';
  $fcmdata="{
    \"to\":\"/topics/room\",
    \"data\":{
      \"message\":\"".$m_details."\",
      \"client_id\":\"".$clientid."\",
      \"e_id\":\"".$e_id."\",
      \"company_id\":\"".$company_id."\",
      \"messageType\":\"confirm\"
    }
    }";
  define("GOOGLE_API_KEY","AIzaSyBu33kWnxwL9CpSvbm_ZmNnQQfV4MyGQKw");
  $headers=array(
    'Authorization: key=' .GOOGLE_API_KEY,
    'Content-Type: application/json'
  ); 
  echo "work well before init curl";
    $ch = curl_init();
    echo "init well";
  curl_setopt($ch,CURLOPT_URL,$url);
  curl_setopt($ch,CURLOPT_POST,true);
  curl_setopt($ch,CURLOPT_HTTPHEADER,$headers);
  curl_setopt($ch,CURLOPT_RETURNTRANSFER,true);
  curl_setopt($ch,CURLOPT_SSL_VERIFYHOST,0);
  curl_setopt($ch,CURLOPT_SSL_VERIFYPEER,false);
  curl_setopt($ch,CURLOPT_POSTFIELDS,$fcmdata);
          curl_setopt($ch, CURLOPT_SSLVERSION, 6);
  echo "init finishe well";
    $result = curl_exec($ch);
    echo "Execute to get result";
    if ($result === false) {
        die('Curl failed: ' . curl_error($ch));
    }
    curl_close($ch);
    echo "function finished well";
    echo json_encode("well");
    return $result;

  ?>