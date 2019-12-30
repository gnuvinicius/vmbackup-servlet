<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-heading" style="text-align: center">.editar ${hostname}</div>
	<div class="panel-body">

		<!-- Formulário -->
		<form class="form-horizontal" action="hypervisor?logica=updateHypervisor" method="post">
			<div class="form-group">
				<label class="control-label col-sm-3" for="hostname">hostname:</label>
				<div class="col-sm-8">
					<input class="form-control" type="hidden" name="hypervisor_id" value="${hypervisor_id}">
					<input class="form-control" type="text" name="hostname" placeholder="${hostname}" value="${hostname}">
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="ipaddr">Endereço IP:</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="ipaddr" maxlength="15" placeholder="${ipaddr}" value="${ipaddr}">
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="username">usuário:</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="user" placeholder="${user}" value="${user}">
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="password">senha:</label>
				<div class="col-sm-8">
					<input type="password" class="form-control" name="password" required="required">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-8">
					<button type="submit" class="btn btn-info">salvar</button>
					<button type="reset" class="btn btn-default">limpar</button>
				</div>
			</div>
		</form>
		<!-- Fim do formulário -->
		<c:if test="${not empty status}">
			<div class="alert alert-danger" style="text-align: center" role="alert">${status}</div>
		</c:if>
		<a href="index.jsp"><span class="glyphicon glyphicon-menu-left"
			title="voltar"></span> voltar</a>
	</div>
</div>
