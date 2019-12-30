<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-body">
		<p>Status da clonagem: ${statusThreadClone}</p>
		<p>${comandCurrent}</p>
		<%=new java.util.Date()%>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="form-inline" style="text-align: center">
			<form action="schedule?logica=updateController" method="post">
	
				<div class="form-group">
					<label>Início</label>
					<input class="form-control" type="time" name="hourBegin" value="${hourBegin}">
					<label>Fim</label>
					<input class="form-control" type="time" name="hourEnd" value="${hourEnd}">
					<button type="submit" class="btn btn-info">salvar</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- tabela agendamento -->
<table class="table table-hover">
	<thead>
		<tr>
			<th>sequência</th>
			<th>posição</th>
			<th>VM</th>
			<th>VMware ESXi</th>
			<th>origem</th>
			<th>destino</th>
			<th>ação</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="schedule" items="${scheduleList}">
			<tr>
				<td>${schedule.sequence}</td>
				<td>
					<a href="schedule?logica=upSchedule&sequence=${schedule.sequence}"><span class="glyphicon glyphicon-menu-up"></span></a>
					<a href="schedule?logica=downSchedule&sequence=${schedule.sequence}"><span class="glyphicon glyphicon-menu-down"></span></a>
				</td>
				<td>${schedule.guest.hostname}</td>
				<td title="${schedule.guest.hypervisor.ipaddr}">${schedule.guest.hypervisor.hostname}</td>
				<td>${schedule.guest.storageName}</td>
				<td title="${schedule.datastore.url}">${schedule.datastore.name}</td>
				<td>
					<a href="schedule?logica=beginThisClone&sequence=${schedule.sequence}"><span class="glyphicon glyphicon-save" title="clonar agora!"></span></a>
					<a href="schedule?logica=editSchedule&sequence=${schedule.sequence}"><span class="glyphicon glyphicon-pencil" title="editar"></span></a>
					<a href="schedule?logica=removeSchedule&sequence=${schedule.sequence}"><span class="glyphicon glyphicon-remove-circle" title="excluir"></span></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>