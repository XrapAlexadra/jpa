<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.github.xrapalexandra.kr.model.Role" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Подключаем Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>web</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/Rating.css" type="text/css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">LOGO</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/productList">Каталог</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/shopAddress">Магазины</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/basket">Корзина</a>
            </li>
            <c:if test="${sessionScope.user.role == Role.ADMIN}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/adminProductList">Список товаров</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/addProduct">Добавить товар</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/adminBasket">Заказы</a>
                </li>
            </c:if>
        </ul>
        <c:choose>
            <c:when test="${sessionScope.user ==  null}">
                <form class="form-inline my-2 my-lg-0">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit" formmethod="GET"
                            formaction="${pageContext.request.contextPath}/login">Войти
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <nav class="navbar navbar-light bg-light">
                    <span class="navbar-text">
                        Вы вошли как ${sessionScope.user.login}
                     </span>
                </nav>
                <form class="form-inline my-2 my-lg-0">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit" formmethod="GET"
                            formaction="${pageContext.request.contextPath}/exit">Выйти
                    </button>
                </form>
            </c:otherwise>
        </c:choose>

    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-2" style="background-color: #f5f5f5">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>
            </ul>
        </div>
        <div class="col-10">
            <c:if test="${requestScope.message != null}">
                <div class="alert alert-primary" role="alert">
                        ${requestScope.message}
                </div>
            </c:if>
            <c:if test="${requestScope.error != null}">
                <div class="alert alert-danger" role="alert">
                        ${requestScope.error}
                </div>
            </c:if>
            <c:if test="${requestScope.pageJsp!= null}">
                <c:import url="${requestScope.pageJsp}"/>
            </c:if>
        </div>
    </div>


    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <!-- После подключения jQuery, Popper и Bootstrap JS -->
    <%--    <script>--%>
    <%--        $(function () {--%>
    <%--            $('[data-toggle="popover"]').popover({trigger: 'hover'});--%>
    <%--        });--%>
    <%--    </script>--%>
</body>
</html>