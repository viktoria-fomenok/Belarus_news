<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container">
    <div class="container">
        <c:url var="filter" value="/news/" context="${context}"/>
        <form role="form" action="${filter}" method="get">
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
                                <input type="checkbox" name="tagList"
                                <c:if test="${criteria.tagList.contains(tag.tagId)}">
                                       checked
                                </c:if> value="${tag.tagId}"> ${tag.tagName}
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
                            <li role="presentation"><input type="checkbox" name="authorList"
                            <c:if test="${criteria.authorList.contains(author.authorId)}">
                                                           checked
                            </c:if>
                                                           value="${author.authorId}"> ${author.authorName}</li>
                        </c:forEach>
                    </ul>
                </div>

                <button type="submit" class="btn btn-info">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Filter
                </button>


                <a href="<c:url value="/news/" context="${context}"/>" class="btn btn-warning">
                    <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> Reset
                </a>
            </div>
        </form>
    </div>
    <div class="text-danger col-lg-8">
        <c:forEach items="${authorList}" var="author">
            <c:if test="${criteria.authorList.contains(author.authorId)}">
                ${author.authorName};
            </c:if>
        </c:forEach>
    </div>
    <div class="text-danger col-lg-8">
        <c:forEach items="${tagList}" var="tag">
            <c:if test="${criteria.tagList.contains(tag.tagId)}">
                ${tag.tagName};
            </c:if>
        </c:forEach>
    </div>
</div>
<c:forEach items="${newsList}" var="news">
    <hr>
    <div>
        <span>
            <h3>
                <c:url var="news_view" value="/news/view/${news.news.newsId}" context="${context}">
                    <c:forEach items="${criteria.authorList}" var="id">
                        <c:param name="authorList" value="${id}"/>
                    </c:forEach>
                    <c:forEach items="${criteria.tagList}" var="id">
                        <c:param name="tagList" value="${id}"/>
                    </c:forEach>
                </c:url>
                <a href="<c:out value="${news_view}"/>">${news.news.title}</a>
            </h3>
            (by ${news.author.authorName})
            <fmt:formatDate value="${news.news.modificationDate}" pattern="MM/dd/YYYY"/>
        </span>
    </div>
    <div>${news.news.shortText}</div>
    <div class="text-center">

        <c:forEach items="${news.tagList}" var="tag">
                <span>
                   <kbd> ${tag.tagName};</kbd>
                </span>
        </c:forEach>

        <span class="label label-primary">Comments(${news.commentList.size()})</span><br>

        <div class="text-center">
            <span>
                <a href="<c:url value="/news/edit/${news.news.newsId}"
                context="${context}"/>">Edit</a>
                <input type="checkbox" value="${news.news.newsId}" form="delete-news" name="newsIdList">
            </span>
        </div>
    </div>
</c:forEach>
<c:url var="news_delete" value="/news/delete/${news.news.newsId}"
       context="${context}"/>
<form action="${news_delete}" id="delete-news" method="post">
    <input type="submit" class="btn btn-danger" value="Delete">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<div class="text-center">
    <ul class="pagination">
        <c:forEach begin="1" end="${countOfPages}" var="i">
            <li <c:if test="${i eq page}"> class="active"</c:if>>
                <c:url var="page_href" value="/news/page-${i}" context="${context}">
                    <c:forEach items="${criteria.authorList}" var="id">
                        <c:param name="authorList" value="${id}"/>
                    </c:forEach>
                    <c:forEach items="${criteria.tagList}" var="id">
                        <c:param name="tagList" value="${id}"/>
                    </c:forEach>
                </c:url>
                <a href="${page_href}">
                        ${i}
                </a>
            </li>
        </c:forEach>
    </ul>
</div>



