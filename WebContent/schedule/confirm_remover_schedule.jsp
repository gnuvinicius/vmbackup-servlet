<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div>
	<div class="alert alert-danger" style="text-align: center" role="alert">
		<p>Excluir o clone da sequencia ${sequence}?</p>
		<a href="schedule?logica=deleteSchedule&sequence=${sequence}" class="btn btn-danger" role="button">apagar</a>
	</div>
</div>