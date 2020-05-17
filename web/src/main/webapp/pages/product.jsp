<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="container">
    <div class="row">
        <div class="col-4">
            <div id="carousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carousel" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="${requestScope.product.image}"
                             class="d-block w-100" alt="${requestScope.product.name}">
                    </div>
                    <div class="carousel-item">
                        <img src="${requestScope.product.image}"
                             class="d-block w-100" alt="${requestScope.product.name}">
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
        </div>
        <div class="col-6">
            <span>${requestScope.product.name}</span>
            <br>
            <span><h5>Цена </h5>${requestScope.product.price}</span>
            <br>
            <h5>Описание</h5>
            <span>${requestScope.product.description}</span>
            <br>

            <br>
            <form method="post" action=${pageContext.request.contextPath}/addToOrder>
                <button type="submit" class="btn btn-primary" name="pId" value="${requestScope.product.id}">
                    В корзину
                </button>
            </form>
        </div>
        <div class="col-6">
            <c:if test="${requestScope.mark != null}">
                <div class="rating-result">
                    <c:forEach begin="1" end="${requestScope.mark}">
                        <span class="active"></span>
                    </c:forEach>
                    <c:forEach begin="1" end="${5 - requestScope.mark}">
                        <span></span>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <form method="post" action="${pageContext.request.contextPath}/rating">
                    <div class="rating-area">
                        <input type="radio" id="star-5" name="rating" value="5">
                        <label for="star-5" title="Оценка «5»"></label>
                        <input type="radio" id="star-4" name="rating" value="4">
                        <label for="star-4" title="Оценка «4»"></label>
                        <input type="radio" id="star-3" name="rating" value="3">
                        <label for="star-3" title="Оценка «3»"></label>
                        <input type="radio" id="star-2" name="rating" value="2">
                        <label for="star-2" title="Оценка «2»"></label>
                        <input type="radio" id="star-1" name="rating" value="1">
                        <label for="star-1" title="Оценка «1»"></label>
                    </div>
                    <button class="btn btn-primary col-2" type="submit" name="pId" value="${requestScope.product.id}">
                        Оценить
                    </button>
                </form>
            </c:if>
        </div>

    </div>
</div>
