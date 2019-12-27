 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">

<style><%@ include file ="../css/main.css"%></style>
<style><%@ include file ="../css/bootstrap.min.css"%></style>
 <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" media="screen">




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
                 <c:out value="${nbComputers}" /> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="ServletMain" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="name" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="http://localhost:8080/cdb_project/addcomputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
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
                            Computer name - 
                             <a href="http://localhost:8080/cdb_project/ServletMain?orderBy=name" id="orderBy">
                                        <i class="fa fa-sort fa-lg"></i>
                              </a>
                        </th>
                        <th>
                            Introduced date - 
                            <a href="http://localhost:8080/cdb_project/ServletMain?orderBy=introduced" id="orderBy">
                                        <i class="fa fa-sort fa-lg"></i>
                              </a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                            <a href="http://localhost:8080/cdb_project/ServletMain?orderBy=discontinued" id="orderBy">
                                        <i class="fa fa-sort fa-lg"></i>
                              </a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
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
                
<!--                     <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">MacBook Pro</a>
                        </td>
                        <td>2006-01-10</td>
                        <td></td>
                        <td>Apple Inc.</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Connection Machine</a>
                        </td>
                        <td>1987-01-01</td>
                        <td></td>
                        <td>Thinking Machines</td>

                    </tr> -->
 <!--                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">PowerBook</a>
                        </td>
                        <td>1991-01-01</td>
                        <td>2006-01-01</td>
                        <td>Apple Inc.</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Commodore 64</a>
                        </td>
                        <td>1982-08-01</td>
                        <td>1994-01-01</td>
                        <td>Commodore International</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Altair 8800</a>
                        </td>
                        <td>1974-12-19</td>
                        <td></td>
                        <td>Micro Instrumentation and Telemetry Systems</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Canon Cat</a>
                        </td>
                        <td>1987-01-01</td>
                        <td></td>
                        <td>Canon</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Nokia 770</a>
                        </td>
                        <td></td>
                        <td></td>
                        <td>Nokia</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">NeXTcube</a>
                        </td>
                        <td>1988-01-01</td>
                        <td>1993-01-01</td>
                        <td>NeXT</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">IBM 650</a>
                        </td>
                        <td>1953-01-01</td>
                        <td>1962-01-01</td>
                        <td>IBM</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">PlayStation 2</a>
                        </td>
                        <td>2000-03-24</td>
                        <td></td>
                        <td>Sony</td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Archos 101</a>
                        </td>
                        <td></td>
                        <td></td>
                        <td></td>

                    </tr>
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick="">Nintendo 3DS</a>
                        </td>
                        <td>2010-03-23</td>
                        <td></td>
                        <td>Nintendo</td>

                    </tr> -->
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
        
             <ul class="pagination">
             	<li>
             		<form method="GET" action="ServletMain">
             		
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
    
<!-- <script type="text/javascript" src="https://github.com/excilys/training-java/blob/master/static/js/jquery.min.js"></script>
<script type="text/javascript" src="https://github.com/excilys/training-java/blob/master/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://github.com/excilys/training-java/blob/master/static/js/bootstrap.js"></script> -->
   
<!-- <script  src="../js/jquery.min.js"></script>
<script  src="WEB-INF/js/bootstrap.min.js"></script>
<script  src="WEB-INF/js/dashboard.js"></script> -->

<script><%@ include file ="../js/jquery.min.js"%></script>
<script><%@ include file ="../js/bootstrap.min.js"%></script>
<script><%@ include file ="../js/dashboard.js"%></script>

</body>
</html>