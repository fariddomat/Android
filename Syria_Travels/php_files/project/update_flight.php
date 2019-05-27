<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$id = $obj->{'id'};
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
mysqli_query($con,"UPDATE trip_info SET t_from='$from',t_to='$to',t_departure_date='$departure_date',t_departure_time='$departure_time',t_duration='$duration',
t_econ_seats='$econ_sets',t_first_seats='$first_sets',t_seats='$total_sets',t_econ_price='$econ_price',t_first_price='$first_price',t_child_price='$child_price',
t_round_sold='1',t_status='$status',t_total_weight_value='$total_weight',t_extra_weight_price='$extra_weight_value' WHERE t_id='$id'");
//mysqli_close($con);
 $details='update trip with id: '.$id.' from : '.$from.' to :'.$to;

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id) 
VALUES (Null,'$m_date','$m_time','update_trip','$details','$c_id')");
                
                

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 
    $update="update";
    $m_details="update trip from ".$from." to ".$to;
  $date=date("Y-m-d h:i:s");
  $url='https://fcm.googleapis.com/fcm/send';
  $fcmdata="{
    \"to\":\"/topics/room\",
    \"data\":{
      \"message\":\"".$m_details."\",
      \"update\":\"".$update."\",
      \"messageType\":\"update\"
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