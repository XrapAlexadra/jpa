<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
        <a class="nav-link active" id="basket-tab" data-toggle="tab" href="#basket" role="tab" aria-controls="home" aria-selected="true">Корзина</a>
    </li>
    <li class="nav-item" role="presentation">
        <a class="nav-link" id="order-tab" data-toggle="tab" href="#order" role="tab" aria-controls="contact" aria-selected="false">Мои заказы</a>
    </li>
</ul>
<div class="tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="basket" role="tabpanel" aria-labelledby="basket-tab">
        <c:choose>
            <c:when test="${sessionScope.basket.orders.size()==0 || sessionScope.basket == null}">
                <div class="alert alert-primary" role="alert">
                        Ваша корзина пуста
                </div>
            </c:when>
            <c:otherwise>
                <form id="delFromBasket" method="post" action="${pageContext.request.contextPath}/delFromBasket"></form>
                <form id="setOrder" method="post" action="${pageContext.request.contextPath}/basket"></form>

                <table class="table table-hover">
                    <caption>Товары в корзине</caption>
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col">Наименование</th>
                        <th scope="col">Цена</th>
                        <th scope="col">Количество</th>

                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.basket}" var="item" varStatus="index">
                        <tr>
                            <th scope="row"><c:out value="${index.count}"/></th>
                            <td>
                                <a href="${pageContext.request.contextPath}/product?pId=${item.id}">
                                    <img src="${item.image}" alt="${item.name}" height="70">
                                </a>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/product?pId=${item.id}">
                                    <c:out value="${item.name}"/>
                                </a>
                            </td>
                            <td><c:out value="${item.price}"/></td>
                            <td>
                                <label>
                                    <input form="setOrder" type="text" value="1"
                                           name="quantity" required pattern="[0-9]{1,5}" class="form-control">
                                </label>
                            </td>
                            <td>
                                <button class="btn btn-primary col-6" form="delFromBasket" type="submit" name="delProduct" value="${item.id}">
                                    Удалить
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <input form="setOrder" type="submit" value="Заказать">
            </c:otherwise>
        </c:choose>
    </div>
    <div class="tab-pane fade" id="order" role="tabpanel" aria-labelledby="order-tab">
        <c:if test="${requestScope.orderInProcess != null}">
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
                <c:forEach items="${requestScope.orderInProcess}" var="item">
                    <tr>
                        <th scope="col"><c:out value="${item.id}"/></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"><c:out value="${item.status}"/></th>
                    </tr>
                    <c:forEach items="${item.contentList}" var="content" varStatus="index">
                        <tr>
                            <th scope="row"><c:out value="${index.count}"/></th>
                            <td>
                                <a href="${pageContext.request.contextPath}/product?pId=${content.product.id}">
                                    <img src="${content.product.image}" alt="${content.product.name}" height="70">
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
        </c:if>
    </div>
</div>


