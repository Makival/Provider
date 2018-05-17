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
<style type="text/css">
</style>
	<fmt:setLocale value="${sessionScope.local}"/>
	<fmt:setBundle basename="localization.local" var="loc"/>
	<fmt:message bundle="${loc}" key="local.nextButton.name" var="next_button" />
	<fmt:message bundle="${loc}" key="local.prevButton.name" var="prev_button" />	
	<fmt:message bundle="${loc}" key="local.changeServicePlanButton.name" var="choose_sp_button" />
	<fmt:message bundle="${loc}" key="local.servicePlan" var="service_plan" />
	<fmt:message bundle="${loc}" key="local.trafficLimit" var="traffic_limit" />
	<fmt:message bundle="${loc}" key="local.monthlyFee" var="monthly_fee" />
	<fmt:message bundle="${loc}" key="local.accessCost" var="access_cost" />
	<fmt:message bundle="${loc}" key="local.servicePlanDescription" var="description" />
	<fmt:message bundle="${loc}" key="local.chooseServicePlanMessage" var="choose_sp_message" />
	<fmt:message bundle="${loc}" key="local.insufficientFundsMessage" var="insufficient_funds_message" />
	
	<fmt:message bundle="${loc}" key="local.applyFilterButton.name" var="apply_filter_button" />
	
	<fmt:message bundle="${loc}" key="local.minValue" var="min_value" />
	<fmt:message bundle="${loc}" key="local.maxValue" var="max_value" />
	
<title>Service plan list</title>
</head>
<body>
	<%@include file="/WEB-INF/jspf/mainNavBar.jspf"%>
	<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
	<div class="container">
		<%@include file="/WEB-INF/jspf/servicePlanCriteriaNavBar.jspf"%>
		<div class="form-group row">
			<div class="col-sm-12">
				<h3>${service_plan_list}</h3>
				<table class="table table-bordered table-striped" id="spTable">
					<tr>
						<th>Id</th>
						<th>${service_plan}</th>
						<th>${traffic_limit}</th>
						<th>${monthly_fee}</th>
						<th>${access_cost}</th>
						<th>${description}</th>
					</tr>
					<c:forEach var="servicePlan"
						items="${requestScope.servicePlanList}">
						<tr>
							<td class="td_id">${servicePlan.id}</td>
							<td>${servicePlan.name}</td>
							<c:if test="${servicePlan.promoTrafficBonus > 0 }">
								<td class="promo">${servicePlan.trafficLimit}</td>
							</c:if>
							<c:if test="${servicePlan.promoTrafficBonus == 0 }">
								<td>${servicePlan.trafficLimit}</td>
							</c:if>
							<td>${servicePlan.monthlyFee}</td>
							<c:if test="${servicePlan.promoDiscount > 0 }">
								<td class="promo">${servicePlan.accessCost}</td>
							</c:if>
							<c:if test="${servicePlan.promoDiscount == 0 }">
								<td>${servicePlan.accessCost}</td>
							</c:if>
							<td>${servicePlan.description}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<script>
		$('tr').click(function(e){
		    e.preventDefault();
		    $('tr').removeClass('table-primary'); 
		    $(this).addClass('table-primary');
		    var element = document.getElementById("chosenSpId");
		    element.value = $(this).find(".td_id").html(); 
		});		
		</script>

		<nav aria-label="Page navigation">
				<ul class="pagination justify-content-center">
					<c:if test="${requestScope.pageNumber == 1}">
						<li class="page-item disabled"><a class="page-link" href="#" id="prevList">${prev_button}</a></li>
					</c:if>
					<c:if test="${requestScope.pageNumber != 1}">
						<li class="page-item"><a class="page-link" href="#" id="prevList">${prev_button}</a></li>
					</c:if>
					<li class="page-item"><a class="page-link">${requestScope.pageNumber}</a></li>
					<c:if test="${requestScope.lastSpId == requestScope.maxSpId}">
						<li class="page-item disabled"><a class="page-link" href="#" id="nextList">${next_button}</a></li>
					</c:if>
					<c:if test="${requestScope.lastSpId != requestScope.maxSpId}">
						<li class="page-item"><a class="page-link" href="#" id="nextList">${next_button}</a></li>
					</c:if>
				</ul>
			</nav>

		<input type="hidden" name="chooseSpMessage" id="chooseSpMessage" value="${choose_sp_message}" />

		<form action="FrontController" method="post" onsubmit="return checkId()">
			<input type="hidden" name="chosenSpId" id="chosenSpId" value="0" /> 
			<input type="hidden" name="command" value="choose_service_plan" /> 
			<input type="submit" class="btn btn-outline-secondary btn-sm" id ="chooseSp" value="${choose_sp_button}" /><br />	
		</form>
		
		<c:if test="${requestScope.insufficientFunds eq 'TRUE'}">
			<p>${insufficient_funds_message}</p>
		</c:if>

		<script>
			var form = document.getElementById("getSpListForm");
			var element = document.getElementById("spListDirection");
			var pageNumber = document.getElementById("pageNumber");
			var maxSpId = document.getElementById("maxSpId");
			var lastSpId = document.getElementById("lastSpId");

			document.getElementById("prevList").addEventListener("click",
					function() {
						if (pageNumber.value > 1) {
							element.value = "prev";
							form.submit();
						}

					});

			document.getElementById("nextList").addEventListener("click",
					function() {
						if (maxSpId.value != lastSpId.value) {
							element.value = "next";
							form.submit();
						}
					});
		</script>
		
		<script>
		function checkId() {
			var spId = document.getElementById("chosenSpId");
			var message = document.getElementById("chooseSpMessage").value;
			if (spId.value == 0) {
				alert(message);
				return false; 
			}
		}
		</script>
	</div>				
</body>
</html>