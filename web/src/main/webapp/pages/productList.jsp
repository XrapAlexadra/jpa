<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="row row-cols-1 row-cols-md-4">
    <c:forEach items="${requestScope.productList}" var="item">
        <div class="col mb-3">
            <div class="card h-100 text-center">
                <a href="${pageContext.request.contextPath}/product?pId=${item.id}" class="card-link">
                    <img src="${item.image}" class="card-img-top" alt="${item.name}">
                </a>
                <div class="card-body">
                    <a href="${pageContext.request.contextPath}/product?pId=${item.id}"
                       class="card-link">${item.name} ${item.price}</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <li class="page-item">
            <c:if test="${requestScope.page == 1}">
                <a class="page-link" href="#"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </c:if>
            <c:if test="${requestScope.page != 1}">
                <a class="page-link" href="${pageContext.request.contextPath}/productList?page=${requestScope.page-1}"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </c:if>
        </li>
        <с:forEach var="i" begin="1" end="${requestScope.pageCount}">
            <c:if test="${i == requestScope.page}">
                <li class="page-item active" aria-current="page">
                    <a class="page-link" href="#">${i} <span class="sr-only">(current)</span></a>
                </li>
            </c:if>
            <c:if test="${i != requestScope.page}">
                <li class="page-item">
                    <a class="page-link" href="${pageContext.request.contextPath}/productList?page=${i}">
                            ${i}
                    </a>
                </li>
            </c:if>
        </с:forEach>
        <li class="page-item">
            <c:if test="${requestScope.page == requestScope.pageCount}">
                <a class="page-link" href="#"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </c:if>
            <c:if test="${requestScope.page != requestScope.pageCount}">
                <a class="page-link" href="${pageContext.request.contextPath}/productList?page=${requestScope.page+1}"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </c:if>
        </li>
    </ul>
</nav>