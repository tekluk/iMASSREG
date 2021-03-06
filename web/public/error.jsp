<%-- 
    Document   : error
    Created on : Dec 17, 2014, 11:54:29 AM
    Author     : Yoseph
--%>

<%@page import="org.lift.massreg.util.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MASSREG -  Error</title>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/style.css" />
        <script src="<%=request.getContextPath()%>/assets/js/login.js"></script>
        <!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Error</h1>
                <h2>Please check you have a valid username and password></h2>
                <div class="support-note">
                    <span class="note-ie">Sorry, only modern browsers.</span>
                </div>
                <img src="<%=request.getContextPath()%>/assets/images/error.png" alt="Error"/><br/>
                <a href="<%=request.getContextPath()%>/Index?action=<%= Constants.ACTION_LOGOUT%>">Return Back to Login page</a>
            </header>
        </div>
    </body>
</html>
