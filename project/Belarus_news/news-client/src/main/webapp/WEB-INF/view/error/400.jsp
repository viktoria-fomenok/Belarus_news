<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
  <title>News</title>
  <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" context="${context}"/>">
  <script src="<c:url value="/resources/js/jquery.min.js" context="${context}"/>"></script>
  <script src="<c:url value="/resources/js/bootstrap.min.js" context="${context}"/>"></script>
</head>
<body>
<div class="row text-center">
  <br>
  <div class="row">
    <div class="col-lg-8">
      <h1>News Portal</h1>
    </div>
    <div class="col-lg-1">
      <br>
    </div>
  </div>
  <hr color="black" size="10px">
</div>

<div class="row">
  <div class="col-sm-4 text-center">
    <ul class="nav nav-pills nav-stacked">
      <c:url var="href_hope_page" value="/client" context="${context}">
        <c:param name="command" value="home-page"/>
      </c:url>
      <li><a href="<c:out value="${href_hope_page}"/>">Main page</a></li>
    </ul>
  </div>
  <div class="col-lg-4">
  <br><br><br>
    <h1>400 - Bad Request</h1>
  </div>
</div>


<div class="row text-center">
  &copy; Veremeychik Egor
</div>

</body>
</html>