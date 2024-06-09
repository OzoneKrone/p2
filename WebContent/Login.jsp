<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="it.unisa.control.HtmlUtils"%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
	
	<title>Login</title>
</head>
<body>
	<%@ include file="./fragments/header.jsp" %>
		<%@ include file="./fragments/menu.jsp" %>
	
		<div id="main" class="clear">
	
	<h2>Login</h2>

	<form action="Login" method="post" id="myform">
			<%if(request.getParameter("action")!=null && request.getParameter("action").equalsIgnoreCase("error") ){ %>
				<div class="tableRow">
					<p></p>
					<p class="error">Username o password errati!</p> 
				</div>
			
			<%}else if(request.getParameter("action")!=null && request.getParameter("action").equalsIgnoreCase("checkout")){ %>
					<input type="hidden" name="checkout"/><br><br>		
				
			<%}%>
			
			<div class="tableRow">
			<p>Username:</p>
			<p><input type="text" name="un" required placeholder="inserisci username" value="<%= HtmlUtils.escapeHtml(request.getParameter("un")) %>"/></p>
			<div class="tableRow">
			<p>Password:</p>
			<p><input type="password" name="pw" required placeholder="inserisci password" value="<%= HtmlUtils.escapeHtml(request.getParameter("pw")) %>"/></p>
			</div>
			<div class="tableRow">
			<p></p>
			<p><input type="submit" value="login"> &nbsp;&nbsp;&nbsp; <a href="<%= request.getContextPath() %>/Registrazione.jsp">non sei registrato?</a></p>		
			</div>
		</form>
	</div>
	
		<%@ include file="./fragments/footer.jsp" %>
	
</body>
</html>
