<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-sm-1">
    </div>
    <div class="col-lg-11">
        <c:url var="auth_add" value="/author/add" context="${context}"/>
        <sf:form modelAttribute="author" action="${auth_add}" cssClass="form-inline" method="post">
            <sf:input path="authorName" cssClass="form-control"/>
            <input type="submit" class="btn btn-info" value="Add"><br>
            <sf:errors path="authorName" cssStyle="color: #f39c12"/><br>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </sf:form>
        <c:forEach var="author" items="${authorList}">
            <sf:form cssClass="form-inline" method="post"
                     modelAttribute="author" id="updateAuthor${author.authorId}">
                <sf:hidden path="authorId" value="${author.authorId}"/>
                <span>
                     <c:if test="${author.expired == null}">
                         <sf:input id="name${author.authorId}" value="${author.authorName}" cssClass="form-control"
                                   readonly="true" path="authorName"></sf:input>

                         <a id="edit${author.authorId}" href="#" onclick="view(${author.authorId})">edit</a>
                         <span id="buttons${author.authorId}" hidden="">
                            <a href="#" onclick="updateAuthor(${author.authorId})">update</a>
                            <a href="#" onclick="expireAuthor(${author.authorId})">expire</a>
                            <a href="#" onclick="hide(${author.authorId})">cancel</a>
                         </span>
                     </c:if>
                    <c:if test="${author.expired != null}">
                        <sf:input path="authorName" class="form-control" readonly="true"
                                  value="${author.authorName}"/>
                        <span> Is expired since <fmt:formatDate value="${author.expired}" pattern="MM/dd/yyyy"/></span>
                    </c:if>

        </span>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </sf:form>
            <c:url var="auth_expire" value="/author/expire" context="${context}"/>
            <sf:form method="post" action="${auth_expire}" modelAttribute="author"
                     id="expireAuthor${author.authorId}">
                <sf:hidden path="authorId" value="${author.authorId}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </sf:form>
        </c:forEach>
    </div>
    <br><br><br>
</div>