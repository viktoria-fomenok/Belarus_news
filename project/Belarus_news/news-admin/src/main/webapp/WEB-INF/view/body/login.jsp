<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<br><br><br>
<security:authorize access="isAnonymous()">
    <c:if test="${error ne null}">
        <div class="row text-center">
            <div class="col-sm-2"></div>
            <div class="col-sm-10 alert alert-danger">
                    ${error}
            </div>
        </div>
    </c:if>

    <form action="<c:url value="/login" context="${context}"/>" method="POST" class="form-horizontal">
        <div class="form-group">
            <label for="mail" class="col-sm-2 control-label">Email</label>

            <div class="col-sm-10">
                <input type="text" class="form-control" name="username" id="mail" placeholder="User name">
            </div>
        </div>
        <div class="form-group">
            <label for="pass" class="col-sm-2 control-label">Password</label>

            <div class="col-sm-10">
                <input type="password" name="password" class="form-control" id="pass" placeholder="Password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success">Sign in</button>
            </div>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</security:authorize>
<security:authorize access="isAuthenticated()">
    <div class="row text-center">
        <div class="col-sm-2"></div>
        <div class="col-sm-10 alert alert-success">
            <h2>Welcome </h2>
        </div>

    </div>
</security:authorize>