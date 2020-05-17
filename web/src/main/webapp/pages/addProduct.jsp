<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="card col-10">
    <h5 class="card-header">Добавить товар</h5>
    <div class="card-body">
        <div class="panel panel-default">
            <div class="panel-body">
                <form method="post" action="${pageContext.request.contextPath}/addProduct">
                    <div class="form-row">
                        <label for="name" class="col-3">Наименование</label>
                        <div class="col-9">
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="price" class="col-3">Цена</label>
                        <div class="col-9">
                            <input type="text" class="form-control" id="price" pattern="[0-9\.]{1,10}$"
                                   name="price" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="quantity" class="col-3">Количество</label>
                        <div class="col-9">
                            <input type="text" class="form-control" id="quantity" pattern="[0-9]{1,10}$"
                                   name="quantity" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="description" class="col-3">Описание</label>
                        <div class="col-9">
                            <input type="text" class="form-control" id="description" name="description">
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="image" class="col-3">Изображение</label>
                        <div class="col-9">
                            <input type="text" class="form-control" id="image" name="image">
                        </div>
                    </div>
                    <br>
                    <button class="btn btn-primary col-2" type="submit">
                            Добавить
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>


