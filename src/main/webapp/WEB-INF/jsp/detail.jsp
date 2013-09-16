
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Category : ${book.category.name}</h2>
        <!--<img src="<c:url value="/img/${book.image}_big.jpg"/>" class="gede"/>-->
        <img height="320" src="<c:url value="/img/${book.image}.jpg"/>" class="gede"/>
        <div id="title-desc">
            <div id="title">
                <p class="title">${book.title}</p>
                <p class="author">Penulis: ${book.author}</p>
            </div>
            <div id="desc">
                <p>${book.description}</p>
            </div>
            <div id="buy" >
                 <p class="price">Harga : Rp ${book.price}</p>
                 <form method="post" action="<c:url value="/public/cart/add/${book.id}" />">
                     <input type="submit" value="Add To Cart"/>
                 </form>
            </div>

        </div>

<!--        <h2>Rekomendasi</h2>
        <c:forEach items="${recommendBooks}" var="book">
            <div class="bookBox">
                <p class="title"><a href="<c:url value="/public/book/detail/${book.id}" />">${book.title}</></p>
                <img width="129" class="thumbnail" src="<c:url value="/img/${book.image}.jpg"/>"/></a>
                <p class="price">Harga : Rp. ${book.price}</p>
                <img src="<c:url value="/img/buy-button.png"/>"/>
            </div>
        </c:forEach>   -->
    </body>
</html>
