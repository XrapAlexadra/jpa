<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<table class="table table-hover">
    <caption>Заказы</caption>
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
    <c:forEach items="${requestScope.allOrders}" var="item">
        <tr style="background-color: #f2eff6">
            <th scope="col"><c:out value="${item.id}"/></th>
            <th scope="col"><c:out value="${item.user.login}"/></th>
            <th scope="col"><c:out value="${item.status}"/></th>
            <th scope="col"></th>
                <form method="post" action="${pageContext.request.contextPath}/changeBasket">
            <th scope="col">
                <label>
                    <select name="newStatus">
                        <option value="ORDER">ORDER</option>
                        <option value="CONFIRMED">CONFIRMED</option>
                        <option value="PAID">PAID</option>
                    </select>
                </label>
            </th>
            <th scope="col">
                <button class="btn btn-primary" type="submit" name="orderId" value="${item.id}">Изменить</button>
            </th>
            </form>

        </tr>
        <c:forEach items="${item.contentList}" var="content" varStatus="index">
            <tr>
                <th scope="row"><c:out value="${index.count}"/></th>
                <td>
                    <a href="${pageContext.request.contextPath}/product?pId=${content.product.id}">
                        <img src="${content.product.image}"
                             alt="${content.product.name}" height="70">
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/product?pId=${content.product.id}">
                        <c:out value="${content.product.name}"/>
                    </a>
                </td>
                <td><c:out value="${content.product.price}"/></td>
                <td><c:out value="${content.orderQuantity}"/></td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>

</table>
<form method="post" action="${pageContext.request.contextPath}/adminBasket">
    <label>
        <button class="btn btn-primary" type="submit">Списать товары</button>
    </label>
</form>