<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-heading" style="text-align: center">.cadastrar novo vmware</div>
	<div class="panel-body">

		<form class="form-horizontal" action="schedule?logica=selectGuest" method="post">
			<div class="form-group">
				<label class="control-label col-sm-4">VMware ESXi: </label>
				<div class="col-sm-8">
					<select required class="form-control" name="hypervisor">
						<option value="" disabled="disabled" selected>escolha sua opção</option>
						<c:forEach var="hypervisor" items="${hypervisorList}">
							<option value="${hypervisor.hostname}">${hypervisor.hostname}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="form-group" style="border: 1px">
				<div class="col-sm-8">
				</div>
				<div class="col-sm-4">
					<button type="submit" class="btn btn-info">avançar</button>
				</div>
			</div>
		</form>

	</div>
</div>

<a href="index.jsp"><span class="glyphicon glyphicon-menu-left" title="voltar"></span> voltar</a>