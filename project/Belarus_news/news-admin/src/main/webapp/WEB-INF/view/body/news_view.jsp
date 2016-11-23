<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>${newsTO.news.title} (by ${newsTO.author.authorName})</h2>

<p>${newsTO.news.fullText}</p>
<hr>
<nav>
    <ul class="pager">
        <c:if test="${pager.previousId != null}">
            <c:url var="news_prev" value="/news/view/${pager.previousId}" context="${context}">
                <c:forEach items="${criteria.authorList}" var="id">
                    <c:param name="authorList" value="${id}"/>
                </c:forEach>
                <c:forEach items="${criteria.tagList}" var="id">
                    <c:param name="tagList" value="${id}"/>
                </c:forEach>
            </c:url>
            <li><a href="${news_prev}">Previous</a></li>
        </c:if>
        <c:if test="${pager.nextId != null}">
            <c:url var="news_next" value="/news/view/${pager.nextId}" context="${context}">
                <c:forEach items="${criteria.authorList}" var="id">
                    <c:param name="authorList" value="${id}"/>
                </c:forEach>
                <c:forEach items="${criteria.tagList}" var="id">
                    <c:param name="tagList" value="${id}"/>
                </c:forEach>
            </c:url>
            <li><a href="${news_next}">Next</a></li>
        </c:if>

    </ul>
</nav>
<hr>
<c:url var="com_add" value="/comment/add/${newsTO.news.newsId}" context="${context}"/>
<sf:form action="${com_add}" modelAttribute="comment">
    <div class="form-group">
        <sf:textarea path="commentText" cssClass="form-control" rows="3"></sf:textarea>
        <sf:errors path="commentText" cssStyle="color: #f39c12"/>
    </div>
    <button type="submit" class="btn btn-default">
        <span class="glyphicon glyphicon-comment"></span> Add comment
    </button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</sf:form>
<c:forEach items="${newsTO.commentList}" var="comment">
            <span>
                <fmt:formatDate value="${comment.creationDate}" pattern="MM/dd/YYYY"/>
            </span>

    <div class="row alert alert-info">
        <div class="col-lg-11">
            <p class="text-left">${comment.commentText}</p>
        </div>
        <div class="col-sm-1">
            <button type="submit" form="form-${comment.commentId}" class="btn btn-link">
                <span class="glyphicon glyphicon-remove"></span>
            </button>
        </div>
    </div>
    <c:url var="com_delete" value="/comment/delete/${newsTO.news.newsId}/${comment.commentId}" context="${context}"/>
    <form method="post" id="form-${comment.commentId}"
          action="${com_delete}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</c:forEach>
