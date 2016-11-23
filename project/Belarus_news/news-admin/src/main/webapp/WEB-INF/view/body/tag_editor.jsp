<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-sm-1">
    </div>
    <div class="col-lg-10">
        <c:url var="tag_add" value="/tag/add/" context="${context}"/>
        <sf:form modelAttribute="tag" action="${tag_add}" cssClass="form-inline" method="post">
            <sf:input path="tagName" cssClass="form-control"/>
            <input type="submit" class="btn btn-info" value="Add"><br>
            <sf:errors path="tagName" cssStyle="color: #f39c12"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </sf:form>
        <c:forEach var="tag" items="${tagList}">
            <form class="form-inline" method="post" id="updateTag${tag.tagId}">
                <input name="tagId" type="hidden" value="${tag.tagId}">
        <span>
            <input id="name${tag.tagId}" class="form-control" type="text" value="${tag.tagName}"
                   readonly="readonly" name="tagName"/>
            <a id="edit${tag.tagId}" href="#" onclick="view(${tag.tagId})">edit</a>
            <span id="buttons${tag.tagId}" hidden="">
                <a href="#" onclick="updateTag(${tag.tagId})">update</a>
                <a href="#" onclick="deleteTag(${tag.tagId})">delete</a>
                <a href="#" onclick="hide(${tag.tagId})">cancel</a>
            </span>
        </span>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <c:url var="tag_delete" value="/tag/delete/" context="${context}"/>
            <form method="post" action="${tag_delete}" id="deleteTag${tag.tagId}">
                <input name="tagId" type="hidden" value="${tag.tagId}">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </c:forEach>

    </div>
    <br><br><br>
</div>