<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$username = $obj->{'username'};
$password = $obj->{'password'};
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
  /* grab the posts from the db */
  //$query = "SELECT post_title, guid FROM wp_posts WHERE 
  //  post_author = $user_id AND post_status = 'publish'
  // ORDER BY ID DESC LIMIT $number_of_posts";
mysqli_query($con,"INSERT INTO employee (e_username,e_password,e_comapny_id) VALUES('$username', '$password', '$companyid') ");
//mysqli_close($con);
$details='add new employee';

mysqli_query($con,"INSERT INTO event_history(event_id, event_date, event_time, event_action, event_details, event_actor_id) 
VALUES (Null,'$m_date','$m_time','add_employee','$details','$companyid')");
                
//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>