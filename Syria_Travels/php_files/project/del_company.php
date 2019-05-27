<?php  
//$json=$_GET ['json'];
$json = file_get_contents('php://input');
$obj = json_decode($json);
$c_id = $obj->{'c_id'};
//echo $json;

//Save
@header('Content-Type: text/html; charset=utf-8');

 
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");
  
mysqli_query($con,"DELETE FROM companies WHERE c_id='$c_id' ");

 $posts = array(1);
   header('Content-type: application/json');
    echo json_encode(array('posts'=>$posts)); 

  ?>