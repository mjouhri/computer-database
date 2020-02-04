 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<style><%@ include file ="../css/main.css"%></style>
<style><%@ include file ="../css/bootstrap.min.css"%></style>



 <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" media="screen">
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
            <h1 id="homeTitle">
                 <c:out value="${nbComputers}"/>
                  <spring:message code="lbl.computersFound" />
                  
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="name" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="http://localhost:8080/cdb_project/addcomputer">
 						<spring:message code="lbl.addComputer" /></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">
                    <spring:message code="lbl.edit"/></a> 
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="lbl.computerName" /> - 
                             <a href="http://localhost:8080/cdb_project?orderBy=name" id="orderBy">
                                        <i class="fa fa-sort fa-lg"></i>
                              </a>
                        </th>
                        <th>
                            <spring:message code="lbl.introducedDate" /> - 
                            <a href="http://localhost:8080/cdb_project?orderBy=introduced" id="orderBy">
                                        <i class="fa fa-sort fa-lg"></i>
                              </a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="lbl.discontinuedDate" /> - 
                            <a href="http://localhost:8080/cdb_project?orderBy=discontinued" id="orderBy">
                                        <i class="fa fa-sort fa-lg"></i>
                              </a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="lbl.company" />
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                
                <c:forEach items="${computers}" var="computer">
				    <tr>      
				        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="1111">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">${computer.getName()}</a>
                        </td>
                        <td>${computer.getIntroduced()}</td>
                        <td>${computer.getDiscontinued()}</td>
                        <td>${computer.getCompany().getNameCompany()}</td>
				    </tr>
				</c:forEach>
                
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
        
             <ul class="pagination">
             	<li>
             		<form method="GET" action="">
             		
             			<button name = "page" value="${page - 1}" type="submit"> &laquo;</button>
             			<c:forEach var="i" begin="1" end="${nbPages}" step="1">
             				<button name = "page" value="${ i }" type="submit"> ${ i }</button>
             			</c:forEach>
             			<button name = "page" value="${page + 1}" type="submit"> &raquo;</button>
             			
             			<button class="btn btn-default" name = "nbPages" value="10" type="submit" >10</button>
			            <button class="btn btn-default" name = "nbPages"  value="50" type="submit" >50</button>
			            <button class="btn btn-default" name = "nbPages"  value="100" type="submit" >100</button>            			 
             		</form>            	       
             	</li>

        </ul>
	</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >

        </div>

    </footer>
    

<script><%@ include file ="../js/jquery.min.js"%></script>
<script><%@ include file ="../js/bootstrap.min.js"%></script>
<script><%@ include file ="../js/dashboard.js"%></script>

</body>
</html>