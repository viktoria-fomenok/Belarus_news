<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="date" class="java.util.Date" scope="request"/>
<c:url var="news_edit" value="/news/edit" context="${context}"/>
<hr>

<c:if test="${OPTIMISTIC_LOCK}">

    <div class="alert alert-danger">
        The News has been changed by another user.
    </div>
</c:if>
<sf:form modelAttribute="newsRecord" action="${news_edit}" method="post">
    <sf:hidden path="news.newsId"/>
    <sf:hidden path="news.version"/>
    <div class="form-group">
        <label path="news.title" for="news-title">Title</label>
        <sf:input path="news.title" id="news-title" cssClass="form-control"/>
        <sf:errors path="news.title" cssStyle="color: #f39c12"/>
    </div>
    <div class="form-group">
        <label name="news.shortText" for="news-brief">Brief</label>
        <sf:input id="news-brief" path="news.shortText" cssClass="form-control"/>
        <sf:errors path="news.shortText" cssStyle="color: #f39c12"/>
    </div>
    <div class="form-group">
        <label name="news.fullText" for="news-content">Content</label>
        <sf:textarea path="news.fullText" id="news-content" cssClass="form-control"
                     rows="7"></sf:textarea>
        <sf:errors path="news.fullText" cssStyle="color: #f39c12"/>
    </div>
    <div class="form-group">
        <label for="news-creation-date">Creation Date</label>
        <input class="form-control" id="news-creation-date" readonly type="text" value="<fmt:formatDate value="${newsRecord.news.creationDate}"
                 pattern="MM/dd/yyyy" />" max="${date}">
    </div>
    <div class="form-group">
        <label for="news-date">Modification Date</label>
        <input class="form-control" id="news-date" readonly type="text" value="<fmt:formatDate value="${newsRecord.news.modificationDate}"
                 pattern="MM/dd/yyyy" />">

    </div>
    <div class="btn-group" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                <span class="glyphicon glyphicon-tags" aria-hidden="true"> Tags</span>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach items="${tagList}" var="tag">
                    <li role="presentation">
                        <sf:checkbox path="tagIdList" value="${tag.tagId}"/>${tag.tagName}
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                <span class="glyphicon glyphicon-font" aria-hidden="true"> Authors</span>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach items="${authorList}" var="author">
                    <li role="presentation">
                        <sf:radiobutton path="authorId" value="${author.authorId}"/>${author.authorName}
                    </li>
                </c:forEach>

            </ul>
        </div>
    </div>
    <sf:errors path="authorId" cssStyle="color: #f39c12"/>
    <input type="submit" class="btn btn-default" value="Save">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</sf:form>
<br><br><br>

