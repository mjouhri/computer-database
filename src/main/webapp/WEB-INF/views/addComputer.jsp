 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style><%@ include file ="../css/main.css"%></style>
<style><%@ include file ="../css/font-awesome.css"%></style>
<style><%@ include file ="../css/bootstrap.min.css"%></style>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
  $.validate({
    lang: 'fr'
  });
</script>

<script>
   
var validator = $("#addForm").validate({
          rules: {
              "computerName":{
                 "required": true,
                 "minlength": 2,
              },
              "introduced":{
            	  "date": true,
               },
               "discontinued":{
             	  "date": true,
                } 
          }
          });
          
validator.form();
</script>

<a href="?locale=en"><img style="margin-left: 50px;" src="https://cdn.icon-icons.com/icons2/107/PNG/512/united_kingdom_flag_flags_18060.png" id="drapeau" width="24" height="24" alt="England"> </a>
<a href="?locale=fr"><img style="margin-left: 10px;" src="http://www.vogo-group.com/wp-content/uploads/2019/10/france_icon.png" id="drapeau" alt="France" width="24" height="24"></a>  

</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>  <spring:message code="lbl.addComputer"/> </h1>
                    <form id="addForm" action="addcomputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="lbl.computerName"/></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name"
                                 required>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="lbl.introducedDate"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date"
                                data-validation="date" data-validation-format="yyyy-mm-dd">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="lbl.discontinuedDate"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued"  placeholder="Discontinued date"
                                data-validation="date" data-validation-format="yyyy-mm-dd">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="lbl.company"/></label>
                                 
	                                <select class="form-control" name ="companyId" id="companyId" >
	                                <c:forEach items="${listCompany}" var="company">
	                                    <option value="${company.getIdCompany()}">${company.getNameCompany()}</option>
	                                </c:forEach>
	                                </select>
	                             
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary" >
                            or
                            <a href="http://localhost:8080/cdb_project" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>