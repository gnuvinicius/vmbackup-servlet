<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-heading" style="text-align: center">.cadastrar novo vmware</div>
	<div class="panel-body">

		<form class="form-horizontal" action="schedule?logica=addSchedule" method="post">
			<div class="form-group">
				<label class="control-label col-sm-4">Maquina Virtual</label>
				<div class="col-sm-8">
					<select required class="form-control" name="guest">
						<option value="" disabled="disabled" selected>escolha sua opção</option>
						<c:forEach var="guest" items="${guestList}">
							<option value="${guest.hostname}">${guest.hostname}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-4">Storage de destino</label>
				<div class="col-sm-8">
					<select required class="form-control" name="datastore">
						<option value="" disabled="disabled" selected>escolha sua opção</option>
						<c:forEach var="datastore" items="${datastoreList}">
							<option value="${datastore.name}">${datastore.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-4"></div>
				<div class="col-sm-8">
					<button type="submit" class="btn btn-info">salvar</button>
					<button type="reset" class="btn btn-default">limpar</button>
				</div>
			</div>
		</form>

	</div>
</div>

<a href="schedule?logica=selectHypervisor"><span class="glyphicon glyphicon-menu-left" title="voltar"></span> voltar</a>