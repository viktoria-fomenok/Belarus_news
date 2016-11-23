<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<br>
<div class="row">

    <div class="col-lg-8">
       <h1>News Portal - Administration</h1>
    </div>
    <div class="col-lg-1">
        <br>
        <security:authorize access="isAuthenticated()">

            <form action="<c:url value="/logout" context="${context}"/>" method="post">
                <input type="submit" class="btn btn-info" value="Logout"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
    </div>
</div>
<hr color="black" size="10px">

