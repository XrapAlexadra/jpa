<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<table class="table table-hover">
    <caption>Список товаров</caption>
    <thead>
    <tr>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col">Наименование</th>
        <th scope="col">Цена</th>
        <th scope="col">Количество</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.productList}" var="item" varStatus="index">
        <tr>
            <th scope="row"><c:out value="${index.count}"/></th>
            <td>
                <a href="${pageContext.request.contextPath}/change?pId=${item.id}">
                    <img src="${item.image}" alt="${item.name}" height="70">
                </a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/change?pId=${item.id}">
                    <c:out value="${item.name}"/>
                </a>
            </td>
            <td><c:out value="${item.price}"/></td>
            <td><c:out value="${item.quantity}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
