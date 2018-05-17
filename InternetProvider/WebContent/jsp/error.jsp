<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<jsp:include page="/WEB-INF/jspf/headerMin.jspf"/>	
		<fmt:setLocale value="${sessionScope.local}" />
		<fmt:setBundle basename="localization.local" var="loc" />	
		<fmt:message bundle="${loc}" key="local.userCreationErrorMessage" var="user_creation_error_message" />
		<fmt:message bundle="${loc}" key="local.userUpdateErrorMessage" var="user_update_error_message" />
		<fmt:message bundle="${loc}" key="local.userBlockErrorMessage" var="user_block_error_message" />
		<fmt:message bundle="${loc}" key="local.userAlreadyBlockedMessage" var="user_already_blocked_message" />
		<fmt:message bundle="${loc}" key="local.userNotFoundErrorMessage" var="user_not_found_error_message" />
		<fmt:message bundle="${loc}" key="local.refillBalanceErrorMessage" var="refill_balance_error_message" />
		<fmt:message bundle="${loc}" key="local.servicePlanChosenError" var="sp_chosen_error_message" />
		<fmt:message bundle="${loc}" key="local.accessDeniedMessage" var="access_denied_message" />
		<fmt:message bundle="${loc}" key="local.commandErrorMessage" var="command_error_message" />
		<fmt:message bundle="${loc}" key="local.servicePlanNotFound" var="sp_not_found_message" />
		<fmt:message bundle="${loc}" key="local.promoCreationErrorMessage" var="promo_creation_error_message" />
		<fmt:message bundle="${loc}" key="local.promoRemoveErrorMessage" var="promo_remove_error_message" />
		<fmt:message bundle="${loc}" key="local.promoNotFoundErrorMessage" var="promo_not_found_message" />	
		<fmt:message bundle="${loc}" key="local.errorPageHeader" var="error_page_header" />	
		<fmt:message bundle="${loc}" key="local.errorPageTitle" var="error_page_title" />	
		<title>${error_page_title}</title>
	</head>
	<body>
		<%@include file="/WEB-INF/jspf/minNavBar.jspf"%>
		<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
		
		<p>${error_page_header}</p>
		
		<c:if test="${requestScope.userCreationError eq 'TRUE'}">
			<p>${user_creation_error_message}</p>
		</c:if>	
		<c:if test="${requestScope.userUpdateError eq 'TRUE'}">
			<p>${user_update_error_message}</p>
		</c:if>	
		<c:if test="${requestScope.userBlockError eq 'TRUE'}">
			<p>${user_block_error_message}</p>
		</c:if>	
		<c:if test="${requestScope.userAlreadyBlocked eq 'TRUE'}">
			<p>${user_already_blocked_message}</p>
		</c:if>	
		<c:if test="${requestScope.userNotFound eq 'TRUE'}">
			<p>${user_not_found_error_message}</p>
		</c:if>		
		<c:if test="${requestScope.refillBalanceError eq 'TRUE'}">
			<p>${refill_balance_error_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanChosenError eq 'TRUE'}">
			<p>${sp_chosen_error_message}</p>
		</c:if>
		<c:if test="${requestScope.accessViolation eq 'TRUE'}">
			<p>${access_denied_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanNotFound eq 'TRUE'}">
			<p>${sp_not_found_message}</p>
		</c:if>
		<c:if test="${requestScope.commandError eq 'TRUE'}">
			<p>${command_error_message}</p>
		</c:if>
		<c:if test="${requestScope.promoCreationError eq 'TRUE'}">
			<p>${promo_creation_error_message}</p>
		</c:if>		
		<c:if test="${requestScope.promoRemoveError eq 'TRUE'}">
			<p>${promo_remove_error_message}</p>
		</c:if>
		<c:if test="${requestScope.promoNotFound eq 'TRUE'}">
			<p>${promo_not_found_message}</p>
		</c:if>		
		
	</body>
</html>