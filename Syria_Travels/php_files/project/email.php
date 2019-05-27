  <?php
  include 'smtp/Send_Mail.php';
$to = "user@gmail.com";
$subject = "Test mail";
$message = "Hello! This is a simple email message.";
$from = "fromuser@gmail.com";
$headers = "From:" . $from;
mail($to,$subject,$message,$headers);
echo "Mail Sent.";
?>