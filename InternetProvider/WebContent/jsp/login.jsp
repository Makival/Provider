<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login</title>
<jsp:include page="/WEB-INF/jspf/headerMin.jspf" />
<style type="text/css">
</style>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.password" var="password" />
<fmt:message bundle="${loc}" key="local.loginFillMessage"
	var="login_fill_message" />
<fmt:message bundle="${loc}" key="local.passFillMessage"
	var="pass_fill_message" />
<fmt:message bundle="${loc}" key="local.userNotFoundMessage"
	var="user_not_found_message" />
<fmt:message bundle="${loc}" key="local.userProfileBlockedMessage"
	var="user_blocked_message" />
<fmt:message bundle="${loc}" key="local.userAlreadyLoggedMessage"
	var="user_already_logged_message" />
<fmt:message bundle="${loc}" key="local.signinButton.name"
	var="sign_in_button" />
</head>
<body>
	<%@include file="/WEB-INF/jspf/languageNavBar.jspf"%>
	<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
	<div class="container">
		<form action="FrontController" method="post">
			<div class="form-group row">
				<label for="login" class="col-sm-1 col-form-label">${login}</label>
				<div class="col-sm-4">
					<input type="text" pattern="([A-Z]{1})([A-Za-z0-9-_]{5,50})"
						oninvalid="this.setCustomValidity('${login_fill_message}')"
						oninput="setCustomValidity('')" class="form-control" id="login"
						name="login" value="" required />
				</div>
			</div>
			<div class="form-group row">
				<label for="password" class="col-sm-1 col-form-label">${password}</label>
				<div class="col-sm-4">
					<input type="password" pattern="[^{}#()]{3,50}"
						oninvalid="this.setCustomValidity('${pass_fill_message}')"
						oninput="setCustomValidity('')" class="form-control" id="password"
						name="password" value="" required />
				</div>
			</div>
			<input type="hidden" name="command" value="log_in" /> <input
				type="submit" class="btn btn-outline-secondary btn-md"
				value="${sign_in_button}" />
		</form>
		<br />

		<c:if test="${requestScope.userNotFound eq 'TRUE'}">
			<P>${user_not_found_message}</P>
		</c:if>
		<c:if test="${requestScope.userProfileBlocked eq 'TRUE'}">
			<P>${user_blocked_message}</P>
		</c:if>

		<c:if test="${requestScope.userAlreadyLogged eq 'TRUE'}">
			<P>${user_already_logged_message}</P>
		</c:if>
	</div>
</body>
</html>