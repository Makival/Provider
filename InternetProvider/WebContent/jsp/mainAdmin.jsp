<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    import="com.korotkevich.provider.entity.User"
    import ="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/jspf/headerMin.jspf"/>
<script src="js/userModal.js"></script>
<style type="text/css">
</style>
	<fmt:setLocale value="${sessionScope.local}"/>
	<fmt:setBundle basename="localization.local" var="loc"/>
	<fmt:message bundle="${loc}" key="local.registrationButton.name" var="reg_button" />
	<fmt:message bundle="${loc}" key="local.findUsersButton.name" var="find_users_button" />
	<fmt:message bundle="${loc}" key="local.userBlockButton.name" var="user_block_button" />
	<fmt:message bundle="${loc}" key="local.goToServicePlanListButton.name" var="go_to_sp_button" />
	<fmt:message bundle="${loc}" key="local.executeButton.name" var="execute_button" />
	<fmt:message bundle="${loc}" key="local.closeButton.name" var="close_button"/>		
	<fmt:message bundle="${loc}" key="local.nextButton.name" var="next_button" />
	<fmt:message bundle="${loc}" key="local.prevButton.name" var="prev_button" />
	<fmt:message bundle="${loc}" key="local.greetingsMessage" var="greetings"/>
	<fmt:message bundle="${loc}" key="local.userlist" var="user_list"/>
	<fmt:message bundle="${loc}" key="local.login" var="login"/>
	<fmt:message bundle="${loc}" key="local.name" var="name"/>
	<fmt:message bundle="${loc}" key="local.surname" var="surname"/>
	<fmt:message bundle="${loc}" key="local.role" var="role"/>
	<fmt:message bundle="${loc}" key="local.email" var="email"/>
	<fmt:message bundle="${loc}" key="local.status" var="status" />
	<fmt:message bundle="${loc}" key="local.birthDate" var="birth_date" />
	<fmt:message bundle="${loc}" key="local.regDate" var="reg_date" />
	<fmt:message bundle="${loc}" key="local.userListHeader" var="user_list"/>
	<fmt:message bundle="${loc}" key="local.confirmUserBlockHeader" var="confirm_user_block_header"/>
	<fmt:message bundle="${loc}" key="local.userCreationSuccessMessage" var="user_creation_success_message" />
	<fmt:message bundle="${loc}" key="local.userUpdateSuccessMessage" var="user_update_success_message" />
	<fmt:message bundle="${loc}" key="local.userBlockSuccessMessage" var="user_block_success_message" />
	<fmt:message bundle="${loc}" key="local.chooseUserMessage" var="choose_user_message" />
<title>Main page</title>
</head>
<body>
	
	<%@include file="/WEB-INF/jspf/mainNavBar.jspf"%>
	<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
	<div class="container">
		<form action="FrontController" id="getUserListForm" method="get">
			<input type="hidden" name="minUserId" value="${requestScope.minUserId}"/> 
			<input type="hidden" name="maxUserId" id="maxUserId" value="${requestScope.maxUserId}"/> 
			<input type="hidden" name="lastUserId" id="lastUserId" value="${requestScope.lastUserId}"/> 
			<input type="hidden" name="command" value="find_users" /> 
			<input type="hidden" name="direction" id="UserListDirection" value="" /> 
			<input type="hidden" name="pageNumber" id="pageNumber" value="${requestScope.pageNumber}" /> 
			<input type="submit" class="btn btn-outline-secondary btn-sm" value="${find_users_button}" />
		</form>
		<form action="FrontController" method="post">
			<input type="hidden" name="command" value="find_service_plans" /> 
			<input type="submit" class="btn btn-outline-secondary btn-sm" value="${go_to_sp_button}" /><br />	
		</form>

		<c:if test="${requestScope.userUpdateSuccess eq 'TRUE'}">
			<p>${user_update_success_message}</p>
		</c:if>

		<c:if test="${requestScope.userListVisibility eq 'TRUE'}">
		<h3>${user_list}</h3>
			<table class="table table-bordered table-striped" id="userTable">
				<tr>
					<th>Id</th>
					<th>${login}</th>
					<th>${name}</th>
					<th>${surname}</th>
					<th>${status}</th>
					<th>${role}</th>
					<th>${email}</th>
					<th>${birth_date}</th>
					<th>${reg_date}</th>
				</tr>
				<c:forEach var="user" items="${requestScope.userList}">
					<tr>
						<td class ="td_id">${user.id}</td>
						<td>${user.login}</td>
						<td>${user.name}</td>
						<td>${user.surname}</td>
						<td>${user.status}</td>
						<td>${user.role}</td>
						<td>${user.email}</td>
						<td>${user.birthDate}</td>
						<td>${user.registrationDate}</td>
					</tr>
				</c:forEach>
		</table>

		<script>
		$('tr').click(function(e){
		    e.preventDefault();
		    $('tr').removeClass('table-primary'); 
		    $(this).addClass('table-primary');
		    var element = document.getElementById("chosenUserId");
		    element.value = $(this).find(".td_id").html(); 
		});		
		</script>

			<nav aria-label="Page navigation">
				<ul class="pagination justify-content-center">
					<c:if test="${requestScope.pageNumber == 1}">
						<li class="page-item disabled"><a class="page-link" href="#"
							id="prevList">${prev_button}</a></li>
					</c:if>
					<c:if test="${requestScope.pageNumber != 1}">
						<li class="page-item"><a class="page-link" href="#"
							id="prevList">${prev_button}</a></li>
					</c:if>
					<li class="page-item"><a class="page-link">${requestScope.pageNumber}</a></li>
					<c:if test="${requestScope.lastUserId == requestScope.maxUserId}">
						<li class="page-item disabled"><a class="page-link" href="#"
							id="nextList">${next_button}</a></li>
					</c:if>
					<c:if test="${requestScope.lastUserId != requestScope.maxUserId}">
						<li class="page-item"><a class="page-link" href="#"
							id="nextList">${next_button}</a></li>
					</c:if>

				</ul>
			</nav>

			<input type="hidden" name="chooseUserMessage" id="chooseUserMessage" value="${choose_user_message}" />

			<form action="FrontController" method="post">
				<input type="hidden" name="chosenUserId" id="chosenUserId" pattern="[\d]{1,10}" value="0" />
				<div class="modal fade" data-backdrop="false" id="userForm" tabindex="-1" role="dialog" aria-labelledby="userFormLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="userFormLabel">${confirm_user_block_header}</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary" data-dismiss="modal">${close_button}</button>
								<input type="hidden" name="command" value="user_block" />
								<input type="submit" class="btn btn-outline-secondary btn-sm" id="updateSp" value="${execute_button}" /><br />
							</div>
						</div>
					</div>
				</div>
			</form>
			
		<button type="button" class="btn btn-outline-secondary btn-sm" id="openUserBlockForm" data-toggle="modal" onclick="checkUserId(openUserBlockForm)"
		data-target="#userForm">${user_block_button}</button>	

			<form action="FrontController" method="post">
				<input type="hidden" name="command" value="go_to_reg_page" /> 
				<input type="submit" class="btn btn-outline-secondary btn-sm" value="${reg_button}" /><br />	
			</form>

		
		<c:if test="${requestScope.userCreationSuccess eq 'TRUE'}">
			<p>${user_creation_success_message}</p>
		</c:if>	
		<c:if test="${requestScope.userBlockSuccess eq 'TRUE'}">
			<p>${user_block_success_message}</p>
		</c:if>	
	
		</c:if>
	</div>
		<script>
			var form = document.getElementById("getUserListForm");
			var element = document.getElementById("UserListDirection");
			var pageNumber = document.getElementById("pageNumber");
			var maxUserId = document.getElementById("maxUserId");
			var lastUserId = document.getElementById("lastUserId");

			document.getElementById("prevList").addEventListener("click",
					function() {
						if (pageNumber.value > 1) {
							element.value = "prev";
							form.submit();
						}

					});

			document.getElementById("nextList").addEventListener("click",
					function() {
						if (maxUserId.value != lastUserId.value) {
							element.value = "next";
							form.submit();
						}
					});
		</script>
			
</body>
</html>