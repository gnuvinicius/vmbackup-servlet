<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="infowrap">
	<div id="infobox" class="margin-left">
		<h3>Atualizar Agendamento</h3>
		<form action="schedule?logica=updateSchedule" method="post">
			<table>
				<tr>
					<td><label>Maquina Virtual</label>
					 <input class="form-control" type="hidden" name="schedule_id" value="${schedule_id}">
					 <input class="form-control" type="hidden" name="sequence" value="${sequence}"></td>
					<td><select class="form-control" name="guest">
							<option selected>${guest}</option>
							<c:forEach var="guest" items="${guestDao.lista}">
								<option value="${guest.hostname}">${guest.hostname}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><label>Storage de destino</label></td>
					<td><select class="form-control" name="storage">
							<option selected>${storage}</option>
							<c:forEach var="storage" items="${storageDao.lista}">
								<option value="${storage.name}">${storage.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" class="btn btn-default"
						value="Salvar"> <input type="reset"
						class="btn btn-default" value="Limpar"></td>
				</tr>
			</table>
		</form>
	</div>
</div>