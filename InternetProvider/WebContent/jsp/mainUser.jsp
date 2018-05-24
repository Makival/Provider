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
<jsp:include page="/WEB-INF/jspf/headerMin.jspf" />
<style type="text/css">
</style>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.registrationButton.name"
	var="reg_button" />
<fmt:message bundle="${loc}" key="local.refillButton.name"
	var="refill_button" />
<fmt:message bundle="${loc}" key="local.executeButton.name"
	var="execute_button" />
<fmt:message bundle="${loc}" key="local.changeServicePlanButton.name"
	var="change_sp_button" />
<fmt:message bundle="${loc}" key="local.greetingsMessage"
	var="greetings" />
<fmt:message bundle="${loc}" key="local.userlist" var="user_list" />
<fmt:message bundle="${loc}" key="local.login" var="login" />
<fmt:message bundle="${loc}" key="local.name" var="name" />
<fmt:message bundle="${loc}" key="local.surname" var="surname" />
<fmt:message bundle="${loc}" key="local.role" var="role" />
<fmt:message bundle="${loc}" key="local.email" var="email" />
<fmt:message bundle="${loc}" key="local.status" var="status" />
<fmt:message bundle="${loc}" key="local.birthDate" var="birth_date" />
<fmt:message bundle="${loc}" key="local.regDate" var="reg_date" />
<fmt:message bundle="${loc}" key="local.cashBalance" var="cash_balance" />
<fmt:message bundle="${loc}" key="local.trafficBalance"
	var="traffic_balance" />
<fmt:message bundle="${loc}" key="local.servicePlan" var="service_plan" />
<fmt:message bundle="${loc}" key="local.signingDate" var="signing_date" />
<fmt:message bundle="${loc}" key="local.trafficLimit"
	var="traffic_limit" />
<fmt:message bundle="${loc}" key="local.unlim" var="unlim" />
<fmt:message bundle="${loc}" key="local.refillSumm" var="refill_summ" />
<fmt:message bundle="${loc}" key="local.monthlyFee" var="monthly_fee" />
<fmt:message bundle="${loc}" key="local.userListHeader" var="user_list" />
<fmt:message bundle="${loc}" key="local.userUpdateSuccessMessage"
	var="user_update_success_message" />
<fmt:message bundle="${loc}" key="local.summFillMessage"
	var="summ_fill_message" />
<fmt:message bundle="${loc}" key="local.refillBalanceSuccessMessage"
	var="refill_balance_success_message" />
<fmt:message bundle="${loc}" key="local.servicePlanChosenSuccess"
	var="sp_chosen_success_message" />
<fmt:message bundle="${loc}" key="local.servicePlanNotFound"
	var="sp_update_not_found_message" />
<fmt:message bundle="${loc}" key="local.clientAccountHeader"
	var="client_account_header" />
<fmt:message bundle="${loc}" key="local.servicePlanDetailsHeader"
	var="service_plan_details_header" />
<fmt:message bundle="${loc}" key="local.insufficientFundsMessage"
	var="insufficient_funds_message" />
<title>Main page</title>
</head>
<body>
	<%@include file="/WEB-INF/jspf/mainNavBar.jspf"%>
	<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
	<div class="container">
		<p>${client_account_header}</p>
		<div class="row">
			<div class="col-sm-3 bg-info div-cell">${cash_balance}</div>
			<div class="col-sm-3 bg-info div-cell">${traffic_balance}</div>
			<div class="col-sm-2">
				<button type="button" id="refillShowButton"
					class="btn btn-outline-secondary">${refill_button}</button>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3">${requestScope.clientAccount.cashBalance}</div>
			<div class="col-sm-3">${requestScope.clientAccount.trafficBalance}</div>
		</div>
		<form action="FrontController" id="refillBalanceForm" method="post"
			style="display: none">
			<div class="form-group row">
				<label for="surname" class="col-sm-3 col-form-label">${refill_summ}</label>
				<div class="col-sm-2">
					<input type="text" pattern="([1-9]{1}[0-9]{0,3})(\.[0-9]{1,2})?$"
						oninvalid="this.setCustomValidity('${summ_fill_message}')"
						oninput="setCustomValidity('')" class="form-control"
						name="refillSumm" id="refillSumm" value="" required />
				</div>
				<input type="hidden" name="command" value="refill_balance" /> <input
					type="submit" class="btn btn-outline-secondary btn-sm"
					value="${execute_button}" /><br />
			</div>
		</form>
		<c:if test="${requestScope.refillBalanceSuccess eq 'TRUE'}">
			<p>${refill_balance_success_message}</p>
		</c:if>

		<br>
		<p>${service_plan_details_header}</p>
		<div class="row">
			<div class="col-sm-4  bg-info div-cell">${service_plan}</div>
			<div class="col-sm-2  bg-info div-cell">${signing_date}</div>
			<div class="col-sm-2  bg-info div-cell">${traffic_limit}</div>
			<div class="col-sm-2  bg-info div-cell">${monthly_fee}</div>
		</div>
		<div class="row">
			<div class="col-sm-4">${requestScope.servicePlan.name}</div>
			<div class="col-sm-2">${requestScope.servicePlan.signingDate}</div>
			<c:if test="${requestScope.servicePlan.trafficLimit == 0}">
				<div class="col-sm-2">${unlim}</div>
			</c:if>
			<c:if test="${requestScope.servicePlan.trafficLimit != 0}">
				<div class="col-sm-2">${requestScope.servicePlan.trafficLimit}</div>
			</c:if>
			<div class="col-sm-2">${requestScope.servicePlan.monthlyFee}</div>
		</div>
		<br>
		<form action="FrontController" method="get">
			<input type="hidden" name="command" value="find_service_plans" /> <input
				type="submit" class="btn btn-outline-secondary btn-sm"
				value="${change_sp_button}" /><br />
		</form>
		<c:if test="${requestScope.servicePlanChosenSuccess eq 'TRUE'}">
			<p>${sp_chosen_success_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanNotFound eq 'TRUE'}">
			<p>${sp_update_not_found_message}</p>
		</c:if>
		<c:if test="${requestScope.userUpdateSuccess eq 'TRUE'}">
			<p>${user_update_success_message}</p>
		</c:if>
		<c:if test="${requestScope.summInvalidated eq 'TRUE'}">
			<p>${summ_fill_message}</p>
		</c:if>
		<c:if test="${requestScope.insufficientFunds eq 'TRUE'}">
			<p>${insufficient_funds_message}</p>
		</c:if>
		<script>
			$(function() {
				$("#refillShowButton").click(function() {
					$("#refillBalanceForm").show();
				});
			});
		</script>
	</div>
</body>
</html>