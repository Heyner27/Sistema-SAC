<%-- 
    Document   : Formulario
    Created on : 7/08/2024, 2:34:37 a. m.
    Author     : Heyner Beltran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <div>INGRESO</div>
        <form name="Ingreso" action="RecibeDatos" method="get">
            
            Usuario: <input type="text" name="usuario" value="" />  
            <br><br>
            Clave: <input type="password" name="clave" value="" />  
            <br><br>
            <input type= "submit" value="enviar" />
            <br> <br>
        </form>
    </body>
</html>
