<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<security:authorize access="hasRole('ROLE_ADMIN')">
    <ul class="nav nav-pills nav-stacked">
        <li><a href="<c:url value="/news/" context="${context}"/>">Main page</a></li>
        <li><a href="<c:url value="/news/add" context="${context}"/>">Add news</a></li>
        <li><a href="<c:url value="/author/edit" context="${context}" />">Add/edit authors</a></li>
        <li><a href="<c:url value="/tag/edit"  context="${context}"/>">Add/edit tags</a></li>
    </ul>
</security:authorize>

