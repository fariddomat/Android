<?php

mysql_connect("localhost","root","")
	or die("no connection");

	mysql_select_db("u168268997_news");

        mysql_query("SET NAMES 'utf8'");
	$startfrom=$_GET['startfrom'];
	$endto =$_GET['endto'];


	$query        =mysql_query(" SELECT * FROM news order by news_id desc LIMIT $startfrom,$endto ");
	if(!$query) 
	{
		$response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));	
	}

	$rows =mysql_fetch_array($query);
	if ($rows) 
	{
        $response["success"] = 1;
    	$response["message"] = "News Available!";
    	$response["posts"]   = array();

    	while ($rows) 
    	{
        	$post                 = array();
        	$post["news_id"]      = $rows["news_id"];
        	$post["news_date"]    = $rows["news_date"];
        	$post["news_title"]   = $rows["news_title"];
        	$post["news_body"]    = $rows["news_body"];
        
        	array_push($response["posts"], $post);
$rows =mysql_fetch_array($query);
    	}

    	 echo json_encode($response);
    }
    else 
    {
    	$response["success"] = 0;
    	$response["message"] = "No News Available!";
    	die(json_encode($response));
	}
?>			