<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div>
	<div class="alert alert-danger" style="text-align: center" role="alert">
		<p>Excluir o ${hypervisorHostname}?</p>
		<a href="hypervisor?logica=deleteHypervisor&hostname=${hypervisorHostname}" class="btn btn-danger" role="button">apagar</a>
	</div>
</div>