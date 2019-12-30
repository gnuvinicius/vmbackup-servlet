<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-heading" style="text-align: center">.cadastrar novo vmware</div>
	<div class="panel-body">

		<!-- Formulário -->
		<form class="form-horizontal" action="hypervisor?logica=createHypervisor" method="post">

			<div class="form-group col-sm-12">
				<label class="control-label" for="hostname">hostname:</label>
				<input type="text" class="form-control" name="hostname" required="required">
				<label class="control-label" for="ipaddr">Endereço IP:</label>
				<input type="text" class="form-control" name="ipaddr" required="required">
				<label class="control-label" for="username">usuário:</label>
				<input type="text" class="form-control" name="user" required="required">
				<label class="control-label" for="password">senha:</label>
				<input type="password" class="form-control" name="password" required="required">
			</div>

			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-8">
					<button type="submit" class="btn btn-info">salvar</button>
					<button type="reset" class="btn btn-default">limpar</button>
				</div>
			</div>
		</form>
		<!-- Fim do formulário -->

		<c:if test="${not empty statusCadastroHypervisor}">
			<div class="alert alert-danger" style="text-align: center"
				role="alert">${statusCadastroHypervisor}</div>
		</c:if>

		<a href="index.jsp"><span class="glyphicon glyphicon-menu-left"
			title="voltar"></span> voltar</a>
	</div>
</div>