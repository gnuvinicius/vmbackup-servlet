<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<table class="table table-hover">
	<thead>
		<tr>
			<th>data</th>
			<th>Maquina Virtual</th>
			<th>Duração (min)</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="log" items="${logList}">
			<c:set var="howlong">
				<fmt:formatNumber type="number" minFractionDigits="2"
					maxFractionDigits="2" value="${log.timeCloneVmdk/60}" />
			</c:set>
			<tr>
				<td>${log.date}</td>
				<td>${log.nameGuest}</td>
				<td>${howlong}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>