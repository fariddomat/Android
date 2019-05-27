<?php

mysql_connect("localhost","","")
	or die("no connection");

	mysql_select_db("u168268997_news");
        mysql_query("SET NAMES 'utf8'");

	$news_date=$_POST['news_date'];
    $news_title= $_POST['news_title'];
	$news_body= $_POST['news_body'];
	$query =mysql_query("INSERT INTO news ( news_date, news_title, news_body ) VALUES ( '$news_date', '$news_title', '$news_body' ) ");
	
	if(!$query)
    {
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add News!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "News Successfully Added!";
    echo json_encode($response);

?>