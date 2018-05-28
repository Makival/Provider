<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<jsp:include page="/WEB-INF/jspf/header.jspf" />
<script src="js/userUpdate.js"></script>
<style type="text/css">
</style>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.passwordOld" var="password_old" />
<fmt:message bundle="${loc}" key="local.passwordVerification"
	var="password_verification" />
<fmt:message bundle="${loc}" key="local.passwordNew" var="password_new" />
<fmt:message bundle="${loc}" key="local.name" var="name" />
<fmt:message bundle="${loc}" key="local.avatar" var="avatar" />
<fmt:message bundle="${loc}" key="local.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.birthDate" var="birth_date" />
<fmt:message bundle="${loc}" key="local.role" var="role" />
<fmt:message bundle="${loc}" key="local.registrationButton.name"
	var="reg_button" />
<fmt:message bundle="${loc}" key="local.updateUserButton.name"
	var="update_user_button" />
<fmt:message bundle="${loc}" key="local.roleFillMessage"
	var="role_fill_message" />
<fmt:message bundle="${loc}" key="local.loginFillMessage"
	var="login_fill_message" />
<fmt:message bundle="${loc}" key="local.passFillMessage"
	var="pass_fill_message" />
<fmt:message bundle="${loc}" key="local.nameFillMessage"
	var="name_fill_message" />
<fmt:message bundle="${loc}" key="local.surnameFillMessage"
	var="surname_fill_message" />
<fmt:message bundle="${loc}" key="local.emailFillMessage"
	var="email_fill_message" />
<fmt:message bundle="${loc}" key="local.dateFillMessage"
	var="date_fill_message" />
<fmt:message bundle="${loc}" key="local.userFoundMesasge"
	var="user_found_message" />
<fmt:message bundle="${loc}" key="local.userWasntUpdatedMessage"
	var="user_wasnt_updated_message" />
<fmt:message bundle="${loc}" key="local.passwordNotMatchMessage"
	var="password_not_match_message" />
<fmt:message bundle="${loc}" key="local.passwordIncorrectMessage"
	var="user_password_incorrect_message" />
<fmt:message bundle="${loc}" key="local.languageDate"
	var="language_date" />
<title>User update</title>
</head>
<body>
	<%@include file="/WEB-INF/jspf/mainNavBar.jspf"%>
	<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
	<div class="container">
		<form action="FrontController" method="post" enctype="multipart/form-data"
			onsubmit="return passwordMatchCheck(password, passwordVerification, passError);">
			<div class="form-group row">
				<label for="login" class="col-sm-3 col-form-label">${login}</label>
				<div class="col-sm-4">
					<input type="text" pattern="([A-Z]{1})([A-Za-z0-9-_]{4,50})"
						oninvalid="this.setCustomValidity('${login_fill_message}')"
						oninput="setCustomValidity('')" class="form-control" name="login"
						id="login" value="${sessionScope.currentUser.login}" required />
				</div>
			</div>
			<div class="form-group row">
				<label for="currentPassword" class="col-sm-3 col-form-label">${password_old}</label>
				<div class="col-sm-4">
					<input type="password" pattern="[^{}#()]{3,50}"
						oninvalid="this.setCustomValidity('${pass_fill_message}')"
						oninput="setCustomValidity('')" class="form-control"
						name="currentPassword" id="currentPasswordId" value="" />
				</div>
			</div>
			<div class="form-group row">
				<label for="password" class="col-sm-3 col-form-label">${password_new}</label>
				<div class="col-sm-4">
					<input type="password" pattern="[^{}#()]{3,50}"
						oninvalid="this.setCustomValidity('${pass_fill_message}')"
						oninput="setCustomValidity('')" class="form-control"
						name="password" id="password" value="" />
				</div>
			</div>
			<div class="form-group row">
				<label for="passwordVerification" class="col-sm-3 col-form-label">${password_verification}</label>
				<div class="col-sm-4">
					<input type="password" pattern="[^{}#()]{3,50}"
						oninvalid="this.setCustomValidity('${pass_fill_message}')"
						oninput="setCustomValidity('')" class="form-control"
						name="passwordVerification" id="passwordVerificationId" value="" />
				</div>
			</div>
			<div class="form-group row">
				<label for="name" class="col-sm-3 col-form-label">${name}</label>
				<div class="col-sm-4">
					<input type="text" pattern="([A-Za-z]{2,20})"
						oninvalid="this.setCustomValidity('${name_fill_message}')"
						oninput="setCustomValidity('')" class="form-control" name="name"
						id="name" value="${sessionScope.currentUser.name}" required />
				</div>
			</div>
			<div class="form-group row">
				<label for="surname" class="col-sm-3 col-form-label">${surname}</label>
				<div class="col-sm-4">
					<input type="text" pattern="([A-Za-z]{2,20})"
						oninvalid="this.setCustomValidity('${surname_fill_message}')"
						oninput="setCustomValidity('')" class="form-control"
						name="surname" id="surname"
						value="${sessionScope.currentUser.surname}" required />
				</div>
			</div>
			<div class="form-group row">
				<label for="email" class="col-sm-3 col-form-label">${email}</label>
				<div class="col-sm-4">
					<input type="text"
						pattern="^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$"
						oninvalid="this.setCustomValidity('${email_fill_message}')"
						oninput="setCustomValidity('')" class="form-control" name="email"
						id="email" value="${sessionScope.currentUser.email}" required />
				</div>
			</div>
			<div class="form-group row">
				<label for="datepicker" class="col-sm-3 col-form-label">${birth_date}</label>
				<div class="col-sm-4">
					<input type="text"
						pattern="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](17|18|19|20)\d\d$"
						oninvalid="this.setCustomValidity('${date_fill_message}')"
						oninput="setCustomValidity('')" class="form-control"
						id="datepicker" name="birthDate"
						value="${sessionScope.currentUser.birthDate}" required readonly/>
				</div>
			</div>
			<div class="form-group row">
				<label for="avatar" class="col-sm-3 col-form-label">${avatar}</label>
				<div class="col-sm-4">
					<input type="file" accept=".png,.jpeg" title="Search for a file to add" class="form-control" name="avatar" id="avatar"
						value="" />
				</div>
			</div>

			<input type="hidden" name="passError" id="passError"
				value="${password_not_match_message}" /> <input type="hidden"
				name="command" value="user_update" /><br /> <input type="submit"
				class="btn btn-outline-secondary btn-md"
				value="${update_user_button}" />

			<c:if test="${requestScope.userWithoutUpdate eq 'TRUE'}">
				<p>${user_wasnt_updated_message}</p>
			</c:if>
			<c:if test="${requestScope.userPasswordIncorrect eq 'TRUE'}">
				<p>${user_password_incorrect_message}</p>
			</c:if>

			<c:if test="${not empty requestScope.validationErrorList}">
				<c:forEach var="validation_error"
					items="${requestScope.validationErrorList}">
					<c:if test="${validation_error eq 'InvalidLogin'}">
						<p>${login_fill_message}</p>
					</c:if>
					<c:if test="${validation_error eq 'InvalidPassword'}">
						<p>${pass_fill_message}</p>
					</c:if>
					<c:if test="${validation_error eq 'InvalidName'}">
						<p>${name_fill_message}</p>
					</c:if>
					<c:if test="${validation_error eq 'InvalidSurname'}">
						<p>${surname_fill_message}</p>
					</c:if>
					<c:if test="${validation_error eq 'InvalidEmail'}">
						<p>${email_fill_message}</p>
					</c:if>
					<c:if test="${validation_error eq 'InvalidDate'}">
						<p>${date_fill_message}</p>
					</c:if>
					<c:if test="${validation_error eq 'userPasswordNotMatch'}">
						<p>${password_not_match_message}</p>
					</c:if>
				</c:forEach>
			</c:if>

			<script>
				$(function() {
					$('#datepicker').fdatepicker({
						language : '${language_date}',
						format : 'dd-mm-yyyy',
						disableDblClickSelection : true,
						leftArrow : '<',
					rightArrow:'>',
						closeIcon : 'X',
						closeButton : true
					});
				});
				
				$('input[type=file]').bootstrapFileInput();
				$('.file-inputs').bootstrapFileInput();
			</script>
		</form>
	</div>
</body>
</html>