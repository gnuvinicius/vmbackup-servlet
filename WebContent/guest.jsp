<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">

	<div class="col-md-8 col-md-offset-2" style="text-align: center">
		<c:if test="${statusScanThisHypervisor == false}">
			<div class="alert alert-danger" role="alert">Já existe um scan em andamento, tente novamente mais tarde!</div>
		</c:if>
		<c:if test="${statusScanThisHypervisor == true}">
			<div class="alert alert-info" role="alert">scan de hypervisor em andamento!
				<div class="progress">
					<div class="progress-bar" role="progressbar" aria-valuenow="${value}" aria-valuemin="0" aria-valuemax="100" style="width: ${value};">${value}</div>
				</div>
			</div>
		</c:if>
	</div>
</div>

<a href="hypervisor?logica=scanHypervisor&hostname=${hypervisor}" type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-refresh" title="scanear"></span> scanear ${hypervisor}</a>

<table class="table table-hover">
	<thead>
		<tr>
			<th>Hostname</th>
			<th>VMware ESXi</th>
			<th>ação</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="guest" items="${guestList}">
			<tr>
				<td><a href="guest?logica=showGuest&hostname=${guest.hostname}">${guest.hostname}</a></td>
				<td>${guest.hypervisor.hostname}</td>
				<td><a href="guest?logica=removerGuest&hostname=${guest.hostname}"><span class="glyphicon glyphicon-remove-circle"  title="excluir"></span></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<a href="index.jsp"><span class="glyphicon glyphicon-menu-left" title="voltar"></span> voltar</a>