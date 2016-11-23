<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" scope="request"
       value="${(empty pageContext.request.contextPath) ? '/' :pageContext.request.contextPath}"/>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <tiles:insertAttribute name="stylecss"/>
</head>
<body>
<div class="row text-center">
    <tiles:insertAttribute name="header"/>
</div>

<div class="row">
    <div class="col-sm-4 text-center">
        <tiles:insertAttribute name="menu"/>
    </div>
    <div class="col-lg-4">
        <tiles:insertAttribute name="body"/>
    </div>
</div>


<div class="row text-center">
    <tiles:insertAttribute name="footer"/>
</div>

<tiles:insertAttribute name="scriptjs"/>
</body>
</html>