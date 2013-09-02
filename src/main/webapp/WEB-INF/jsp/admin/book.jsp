<%-- 
    Document   : buku
    Created on : Aug 3, 2013, 2:57:10 PM
    Author     : awanlabs
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="registration">
            
            <div id="message">
                <c:if test="${param.save !=null}">
                    <div id="success">
                        Buku <span style="color: #fa9600">${title}</span> Berhasil disimpan
                    </div>
                </c:if>
                
                <c:if test="${param.edit !=null}">
                    <div id="success">
                        Buku <span style="color: #fa9600">${title}</span> Berhasil diubah
                    </div>
                </c:if>
                
                <c:if test="${param.delete !=null}">
                    <div id="success">
                        Buku <span style="color: #fa9600">${title}</span> Berhasil dihapus
                    </div>
                </c:if>
            </div>
            
            <div id="header-reg">
                Manajemen Buku
            </div>
            <div id="content-reg">
                <a href="<c:url value="/admin/book/add" />" class="button"><img src="<c:url value="/img/add.png" />"/>Tambah Buku</a>
                
                <div id="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <td>Judul</td>
                                <td>Penulis</td>
                                <td>Kategori</td>
                                <td>Aksi</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${books}" var="book">
                            <tr>
                                <td>${book.title}</td>
                                <td>${book.author}</td>
                                <td>${book.category.name}</td>
                                <td><a href="<c:url value="/admin/book/edit/${book.id}" />"><img src="<c:url value="/img/pen.png" />"/> Edit</a> |
                                    <a onclick="return confirm('Yakin Hapus?')" href="<c:url value="/admin/book/delete/${book.id}" />"><img src="<c:url value="/img/trash.png" />"/> Delete</a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
