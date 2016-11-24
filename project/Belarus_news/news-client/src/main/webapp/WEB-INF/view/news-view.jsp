<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" scope="request"
       value="${(empty pageContext.request.contextPath) ? '/' :pageContext.request.contextPath}"/>
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

    <h2>${newsTO.news.title} (by ${newsTO.author.authorName})</h2>
    <c:forEach items="${newsTO.tagList}" var="tag">
                <span>
                   <kbd> ${tag.tagName};</kbd>
                </span>
    </c:forEach>
    <p>${newsTO.news.fullText}</p>
    <hr>
    <nav>
      <ul class="pager">
        <c:if test="${pager.previousId != null}">
          <c:url var="href_previous" value="/client" context="${context}">
            <c:param name="command" value="news-view"/>
            <c:param name="newsId" value="${pager.previousId}"/>
            <c:forEach items="${criteria.authorList}" var="id">
              <c:param name="authorList" value="${id}"/>
            </c:forEach>
            <c:forEach items="${criteria.tagList}" var="id">
              <c:param name="tagList" value="${id}"/>
            </c:forEach>
          </c:url>
          <li><a href="<c:out value="${href_previous}"/>">Previous</a></li>
        </c:if>
        <c:if test="${pager.nextId != null}">
          <c:url var="href_next" value="/client" context="${context}">
            <c:param name="command" value="news-view"/>
            <c:param name="newsId" value="${pager.nextId}"/>
            <c:forEach items="${criteria.authorList}" var="id">
              <c:param name="authorList" value="${id}"/>
            </c:forEach>
            <c:forEach items="${criteria.tagList}" var="id">
              <c:param name="tagList" value="${id}"/>
            </c:forEach>
          </c:url>
          <li><a href="<c:out value="${href_next}"/>">Next</a></li>
        </c:if>

      </ul>
    </nav>
    <hr>

    <form id="comment-add" action="<c:url value="/client" context="${context}"/>" method="POST">
      <input type="hidden" name="command" value="comment-add">
      <input type="hidden" name="newsId" value="${newsTO.news.newsId}">
      <c:if test="${param.error != null}">
        <div class="alert-danger form-group">
          Comment text must be between 1 and 30 characters long
        </div>
      </c:if>
      <div class="form-group">

        <textarea name="commentText" form="comment-add" class="form-control" rows="3"></textarea>
      </div>
      <button type="submit"  class="btn btn-default">
        <span class="glyphicon glyphicon-comment"></span> Add comment
      </button>

    </form>
    <c:forEach items="${newsTO.commentList}" var="comment">
            <span>
                <fmt:formatDate value="${comment.creationDate}" pattern="MM/dd/YYYY"/>
            </span>

      <div class="row alert alert-info">
        <div class="col-lg-11">
          <p class="text-left">${comment.commentText}</p>
        </div>
        <div class="col-sm-1">
        </div>
      </div>
    </c:forEach>
  </div>
</div>


<div class="row text-center">
  &copy; Veremeychik Egor
</div>

</body>
</html>