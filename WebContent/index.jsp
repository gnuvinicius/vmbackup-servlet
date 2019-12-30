<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>vmbackup</title>
<link rel="stylesheet" href="resources/css/bootstrap.css">
<link rel="stylesheet" href="resources/css/style.css">
<meta name="viewport" content="width=device-width">
</head>

<!-- Include dos javascripts -->
<script src="resources/js/bootstrap.js"></script>
<script src="resources/js/jquery-3.1.1.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>

<!-- Começo da pagina -->
<body>

	<div class="col-md-10 col-md-offset-1 top-buffer bottom-buffer">

		<div class="panel panel-default fixed-panel ">
			<div class="panel-body ">

				<div class="row">
					<div class="col-md-5">

						<!-- Menu do vmware esxi -->
						<nav class="navbar navbar-default">
							<div class="container-fluid">
								<div class="navbar-header">
									<a href="index.jsp" class="navbar-brand">.VMBackup</a>
								</div>
								<ul class="nav navbar-nav"></ul>
								<ul class="nav navbar-nav navbar-right"></ul>
							</div>
						</nav>

						<%-- panel lado esquerdo --%>
						<div class="panel panel-default fixed-panel-left">
							<div class="panel-body scroll">
							<%-- conteudo do lado esquerdo --%>
								<%
									if (request.getParameter("file") != null) {
										String file = request.getParameter("file");
								%>
								<jsp:include page='<%=file%>' />
								<%
									}
								%>
							</div>
						</div>
					</div>

					<%-- Menu do agendamento --%>
					<div class="col-md-7">
						<nav class="navbar navbar-default">
							<div class="container-fluid">
								<div class="navbar-header">
								</div>
								<ul class="nav navbar-nav"></ul>
								<ul class="nav navbar-nav navbar-right">
									<li>
										<a href="hypervisor?logica=hypervisor"><span class="glyphicon glyphicon-list-alt"></span> vmware</a>
									</li>
									<li>
										<a href="log?logica=log"><span class="glyphicon glyphicon-list-alt"></span> log</a>
									</li>
									<li>
										<a href="schedule?logica=selectHypervisor"><span class="glyphicon glyphicon-tasks"></span> agendamento</a>
									</li>
								</ul>
							</div>
						</nav>

						<%-- panel lado direito --%>
						<div class="panel panel-default fixed-panel-right">
							<div class="panel-body scroll">
								<%-- conteudo do painel direito --%>

								<%
									if (request.getParameter("file2") != null) {
										String file2 = request.getParameter("file2");
								%>
								<jsp:include page='<%=file2%>' />
								<%
									}
								%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>