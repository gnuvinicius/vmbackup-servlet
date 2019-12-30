<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<a href="hypervisor?logica=cadastro_hypervisor" type="button" class="btn btn-default" aria-label="Left Align">
	<span class="glyphicon glyphicon-floppy-disk" title="novo"></span> novo vmware</a>

<!-- tabela vmware -->
<table class="table table-hover">
	<thead>
		<tr>
			<th>hostname</th>
			<th>endereço IP</th>
			<th>Ação</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="hypervisor" items="${ hypervisorList }">
			<tr>
				<td><a
					href="guest?logica=guest&hypervisor=${ hypervisor.hostname }">${ hypervisor.hostname }</a></td>
				<td>${ hypervisor.ipaddr }</td>
				<td>
					<a href="hypervisor?logica=editHypervisor&hostname=${ hypervisor.hostname }">
						<span class="glyphicon glyphicon-pencil" title="editar"></span></a>
					<a href="hypervisor?logica=removerHypervisor&hostname=${ hypervisor.hostname }">
						<span class="glyphicon glyphicon-remove-circle" title="excluir"></span></a>
				</td>
			</tr>

		</c:forEach>
	</tbody>
</table>