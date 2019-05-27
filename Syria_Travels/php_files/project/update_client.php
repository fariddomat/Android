<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$id = $obj->{'id'};
$username = $obj->{'username'};
$password = $obj->{'password'};
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
mysqli_query($con,"UPDATE clients SET client_username='$username',client_password='$password' WHERE client_id='$id'");
//mysqli_close($con);

//
  //$posts = array($json);
 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>