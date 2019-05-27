<?php

	mysql_connect("localhost","root","")
	or die("no connection");

	mysql_select_db("u168268997_news");

	$username=$_POST['username'];
	$password=$_POST['password'];
	$query =mysql_query(" SELECT 1 FROM users WHERE 
                username ='$username'");
	

	if(!$query)
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));	
	}

	$row=mysql_fetch_assoc($query);
	if ($row) 
	{
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this username is already in use";
        die(json_encode($response));
    }

    $query = mysql_query("INSERT INTO users ( username, password ) VALUES ('$username', '$password ') ");
    if(!query)
    {
    	$response["success"] = 0;
        $response["message"] = "Database Error2. Please Try Again!";
        die(json_encode($response));	
    }

    $response["success"] = 1;
    $response["message"] = "Username Successfully Added!";
    die(json_encode($response));

?>