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
    <div class="container">
      <div class="container">
        <form role="form" action="<c:url value="/client" context="${context}"/>" method="get">
          <input type="hidden" name="command" value="home-page">
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
                  <c:if test="${criteria.authorList.contains(author.authorId)}"> checked
                  </c:if>value="${author.authorId}"> ${author.authorName}</li>
                </c:forEach>
              </ul>
            </div>

            <button type="submit" class="btn btn-info">
              <span class="glyphicon glyphicon-search" aria-hidden="true"></span> Filter
            </button>
            <a href="<c:out value="${href_hope_page}"/>" class="btn btn-warning">
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
              <c:url var="href_view" value="/client" context="${context}">
                <c:param name="command" value="news-view"/>
                <c:param name="newsId" value="${news.news.newsId}"/>
                <c:forEach items="${criteria.authorList}" var="id">
                  <c:param name="authorList" value="${id}"/>
                </c:forEach>
                <c:forEach items="${criteria.tagList}" var="id">
                  <c:param name="tagList" value="${id}"/>
                </c:forEach>
              </c:url>
              <a href="<c:out value="${href_view}"/>">${news.news.title}</a>
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

      </div>
    </c:forEach>
    <c:if test="${newsList.size() eq 0}">
      <h2>Sorry, but News List is empty :(</h2>
    </c:if>
    <div class="text-center">

      <ul class="pagination">
        <c:forEach begin="1" end="${countOfPages}" var="i">
          <li <c:if test="${i eq page}"> class="active"</c:if>>
            <c:url var="href" value="/client" context="${context}">
              <c:param name="command" value="home-page"/>
              <c:param name="page" value="${i}"/>
              <c:forEach items="${criteria.authorList}" var="id">
                <c:param name="authorList" value="${id}"/>
              </c:forEach>
              <c:forEach items="${criteria.tagList}" var="id">
                <c:param name="tagList" value="${id}"/>
              </c:forEach>
            </c:url>
            <a href="<c:out value="${href}"/>">
                ${i}
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>
</div>


<div class="row text-center">
  &copy; Veremeychik Egor
</div>

</body>
</html>