<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
    pageEncoding="ISO-8859-2"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-2">
		<title>Rest Git Hub Exception view</title>
	</head>
	<body>
		<p>
			Ponizej znajduja sie informacje wyjasniajace brak poprawnych wynikow :
		</p>
		<ul>
			<li>requested URL : ${url}</li>
			<li>class name : ${exception.className}</li>
			<li>method name : ${exception.methodName}</li>
			<li>exception message : ${exception.message}</li>
		</ul>
	</body>
</html>