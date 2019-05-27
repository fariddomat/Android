<?php
$con=mysqli_connect("localhost","root","");

	mysqli_select_db($con,"project") 
	or die("no connection");

        mysqli_query($con,"SET NAMES 'utf8'");

        if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $image = $_POST['image'];
 $c_icon = $_POST['name'];
 //$sql = "INSERT INTO volleyupload (photo,name) VALUES ('$actualpath','$name')";
 $sql = "UPDATE companies SET c_icon='$c_icon.png' WHERE c_name='$c_icon'";
 
 
 $path = "../Syria-Travels-copy/assets/images/companies/icons/$c_icon.png";

 if(mysqli_query($con,$sql)){
 file_put_contents($path,base64_decode($image));
 echo "Successfully Uploaded";
 }
 
 mysqli_close($con);
 }else{
 echo "Error";
 }
?>