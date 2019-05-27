<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$email = $obj->{'email'};
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
  /* grab the posts from the db */
  //$query = "SELECT post_title, guid FROM wp_posts WHERE 
  //  post_author = $user_id AND post_status = 'publish'
  // ORDER BY ID DESC LIMIT $number_of_posts";
mysqli_query($con,"INSERT INTO clients (client_email_phone, client_username, client_password)
 VALUES('$email', '$username','$password') ");

 $client=mysqli_query($con,"select * from clients where client_username='$username' ");
		$c=mysqli_fetch_array($client);
		$c_id=$c["client_id"];
    $details='new client register with username '.$username;

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id)
 VALUES (Null,'$m_date','$m_time','signup','$details','$c_id')");
//mysqli_close($con);

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>