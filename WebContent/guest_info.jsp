<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<b>${hostname}</b><br>
<br>

Hostname: ${hostname}<br>
Vmid: ${vmid}<br>
Vmx: ${vmx}<br>
Hypervisor: ${hypervisor}<br>
Storage Name: ${storageName}<br>
Storage Url: ${storageUrl}<br>
<br>
<c:forEach var="virtualdisk" items="${virtualDiskList}">
	vmdk: ${virtualdisk.vmdk}<br>
	Flat vmdk: ${virtualdisk.flat}<br>
</c:forEach>

<a href="guest?logica=guest&hypervisor=${hypervisor}"><span class="glyphicon glyphicon-menu-left" title="voltar"></span> voltar</a>