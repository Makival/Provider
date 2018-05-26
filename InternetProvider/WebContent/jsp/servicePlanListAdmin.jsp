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
<script src="js/spModal.js"></script>
<style type="text/css">
</style>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="localization.local" var="loc" />
<fmt:message bundle="${loc}" key="local.nextButton.name"
	var="next_button" />
<fmt:message bundle="${loc}" key="local.prevButton.name"
	var="prev_button" />
<fmt:message bundle="${loc}" key="local.addServicePlanButton.name"
	var="add_sp_button" />
<fmt:message bundle="${loc}" key="local.updateServicePlanButton.name"
	var="update_sp_button" />
<fmt:message bundle="${loc}" key="local.deleteServicePlanButton.name"
	var="delete_sp_button" />
<fmt:message bundle="${loc}" key="local.executeButton.name"
	var="execute_button" />
<fmt:message bundle="${loc}" key="local.closeButton.name"
	var="close_button" />
<fmt:message bundle="${loc}" key="local.addPromoButton.name"
	var="add_promo_button" />
<fmt:message bundle="${loc}" key="local.deletePromoButton.name"
	var="delete_promo_button" />
<fmt:message bundle="${loc}" key="local.servicePlanId"
	var="service_plan_id" />
<fmt:message bundle="${loc}" key="local.promoId" var="promo_id" />
<fmt:message bundle="${loc}" key="local.servicePlan" var="service_plan" />
<fmt:message bundle="${loc}" key="local.trafficLimit"
	var="traffic_limit" />
<fmt:message bundle="${loc}" key="local.monthlyFee" var="monthly_fee" />
<fmt:message bundle="${loc}" key="local.accessCost" var="access_cost" />
<fmt:message bundle="${loc}" key="local.promo" var="promo" />
<fmt:message bundle="${loc}" key="local.accessDiscount"
	var="access_discount" />
<fmt:message bundle="${loc}" key="local.trafficBonus"
	var="traffic_bonus" />
<fmt:message bundle="${loc}" key="local.servicePlanDescription"
	var="description" />
<fmt:message bundle="${loc}" key="local.servicePlanCard"
	var="service_plan_card" />
<fmt:message bundle="${loc}" key="local.chooseServicePlanMessage"
	var="choose_sp_message" />
<fmt:message bundle="${loc}" key="local.insufficientFundsMessage"
	var="insufficient_funds_message" />
<fmt:message bundle="${loc}" key="local.idFillMessage"
	var="id_fill_message" />
<fmt:message bundle="${loc}" key="local.servicePlanFillMessage"
	var="sp_fill_message" />
<fmt:message bundle="${loc}" key="local.trafficFillMessage"
	var="traffic_fill_message" />
<fmt:message bundle="${loc}" key="local.feeFillMessage"
	var="fee_fill_message" />
<fmt:message bundle="${loc}" key="local.accessCostFillMessage"
	var="access_cost_fill_message" />
<fmt:message bundle="${loc}" key="local.descriptionFillMessage"
	var="description_fill_message" />
<fmt:message bundle="${loc}" key="local.promoFillMessage"
	var="promo_fill_message" />
<fmt:message bundle="${loc}" key="local.accessDiscountFillMessage"
	var="access_discount_fill_message" />
<fmt:message bundle="${loc}" key="local.trafficBonusFillMessage"
	var="traffic_bonus_fill_message" />
<fmt:message bundle="${loc}"
	key="local.servicePlanCreationSuccessMessage"
	var="sp_creation_success_message" />
<fmt:message bundle="${loc}" key="local.servicePlanUpdateSuccessMessage"
	var="sp_update_success_message" />
<fmt:message bundle="${loc}" key="local.servicePlanWasntUpdatedMessage"
	var="sp_wasnt_updated_message" />
<fmt:message bundle="${loc}" key="local.promoCreationSuccessMessage"
	var="promo_creation_success_message" />
<fmt:message bundle="${loc}" key="local.servicePlanIsUnderPromoMessage"
	var="sp_under_promo_message" />
<fmt:message bundle="${loc}" key="local.servicePlanHasNoPromoMessage"
	var="sp_has_no_promo_message" />
<fmt:message bundle="${loc}" key="local.promoRemoveSuccessMessage"
	var="promo_remove_success_message" />
<fmt:message bundle="${loc}" key="local.servicePlanRemoveSuccessMessage"
	var="sp_remove_success_message" />

<fmt:message bundle="${loc}" key="local.goToUserProfileButton.name"
	var="go_to_user_profile_button" />
<fmt:message bundle="${loc}" key="local.signoutButton.name"
	var="sign_out_button" />
<fmt:message bundle="${loc}" key="local.goToMainButton.name"
	var="go_to_main_button" />
<fmt:message bundle="${loc}" key="local.applyFilterButton.name"
	var="apply_filter_button" />

<fmt:message bundle="${loc}" key="local.minValue" var="min_value" />
<fmt:message bundle="${loc}" key="local.maxValue" var="max_value" />
<title>Service plan list</title>
</head>
<body>
	<%@include file="/WEB-INF/jspf/mainNavBar.jspf"%>
	<%@include file="/WEB-INF/jspf/footerBar.jspf"%>
	<div class="container">

		<%@include file="/WEB-INF/jspf/servicePlanCriteriaNavBar.jspf"%>
		<form action="FrontController" id="getSpListForm" class="invisible"
			method="get">
			<input type="hidden" name="minSpId" value="${requestScope.minSpId}" />
			<input type="hidden" name="maxSpId" id="maxSpId"
				value="${requestScope.maxSpId}" /> <input type="hidden"
				name="lastSpId" id="lastSpId" value="${requestScope.lastSpId}" /> <input
				type="hidden" name="command" value="find_service_plans" /> <input
				type="hidden" name="direction" id="spListDirection" value="" /> <input
				type="hidden" name="pageNumber" id="pageNumber"
				value="${requestScope.pageNumber}" /> <input type="submit"
				class="btn btn-outline-secondary btn-sm"
				value="${find_service_plans}" /><br />
		</form>

		<c:if test="${requestScope.spCreationSuccess eq 'TRUE'}">
			<p>${sp_creation_success_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanUpdateSuccess eq 'TRUE'}">
			<p>${sp_update_success_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanWithoutUpdate eq 'TRUE'}">
			<p>${sp_wasnt_updated_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanRemoveSuccess eq 'TRUE'}">
			<p>${sp_remove_success_message}</p>
		</c:if>
		<c:if test="${requestScope.promoCreationSuccess eq 'TRUE'}">
			<p>${promo_creation_success_message}</p>
		</c:if>
		<c:if test="${requestScope.servicePlanIsUnderPromo eq 'TRUE'}">
			<p>${sp_under_promo_message}</p>
		</c:if>
		<c:if test="${requestScope.promoRemoveSuccess eq 'TRUE'}">
			<p>${promo_remove_success_message}</p>
		</c:if>
		<h3>${service_plan_list}</h3>
		<table class="table table-bordered table-striped" id="spTable">
			<tr>
				<th>${service_plan_id}</th>
				<th>${service_plan}</th>
				<th>${traffic_limit}</th>
				<th>${monthly_fee}</th>
				<th>${access_cost}</th>
				<th>${description}</th>
				<th>${promo_id}</th>
				<th>${promo}</th>
				<th>${access_discount}</th>
				<th>${traffic_bonus}</th>
			</tr>
			<c:forEach var="servicePlan" items="${requestScope.servicePlanList}">
				<tr>
					<td class="td_id">${servicePlan.id}</td>
					<td class="td_sp_name">${servicePlan.name}</td>
					<td class="td_sp_traffic">${servicePlan.trafficLimit}</td>
					<td class="td_sp_fee">${servicePlan.monthlyFee}</td>
					<td class="td_sp_access_cost">${servicePlan.accessCost}</td>
					<td class="td_sp_description">${servicePlan.description}</td>
					<td class="td_promo_id">${servicePlan.promoId}</td>
					<td class="td_promo_name">${servicePlan.promoName}</td>
					<td class="td_promo_access_discount">${servicePlan.promoDiscount}</td>
					<td class="td_promo_traffic_bonus">${servicePlan.promoTrafficBonus}</td>
				</tr>
			</c:forEach>
		</table>

		<script>
			$('tr').click(function(e) {
				e.preventDefault();
				$('tr').removeClass('table-primary');
				$(this).addClass('table-primary');
				var spId = document.getElementById("spId");
				spId.value = $(this).find(".td_id").html();
				var promoSpId = document.getElementById("promoSpId");
				promoSpId.value = $(this).find(".td_id").html();
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
				<c:if test="${requestScope.lastSpId == requestScope.maxSpId}">
					<li class="page-item disabled"><a class="page-link" href="#"
						id="nextList">${next_button}</a></li>
				</c:if>
				<c:if test="${requestScope.lastSpId != requestScope.maxSpId}">
					<li class="page-item"><a class="page-link" href="#"
						id="nextList">${next_button}</a></li>
				</c:if>
			</ul>
		</nav>

		<input type="hidden" name="chooseSpMessage" id="chooseSpMessage"
			value="${choose_sp_message}" /> <input type="hidden"
			name="promoExistsMessage" id="promoExistsMessage"
			value="${sp_under_promo_message}" /> <input type="hidden"
			name="noPromoMessage" id="noPromoMessage"
			value="${sp_has_no_promo_message}" />

		<button type="button" class="btn btn-outline-secondary btn-sm"
			id="openSpFormAdd" data-toggle="modal" onclick="prepareSpAddForm()"
			data-target="#spForm">${add_sp_button}</button>
		<button type="button" class="btn btn-outline-secondary btn-sm"
			id="openSpFormUpdate" data-toggle="modal"
			onclick="prepareSpUpdateForm(openSpFormUpdate)" data-target="#spForm">${update_sp_button}</button>
		<button type="button" class="btn btn-outline-secondary btn-sm"
			id="openSpFormDelete" data-toggle="modal"
			onclick="prepareSpDeleteForm(openSpFormDelete)" data-target="#spForm">${delete_sp_button}</button>
		<br> <br>
		<button type="button" class="btn btn-outline-secondary btn-sm"
			id="openPromoFormAdd" data-toggle="modal"
			onclick="preparePromoAddForm(openPromoFormAdd)"
			data-target="#promoForm">${add_promo_button}</button>
		<button type="button" class="btn btn-outline-secondary btn-sm"
			id="openPromoFormDelete" data-toggle="modal"
			onclick="preparePromoDeleteForm(openPromoFormDelete)"
			data-target="#promoForm">${delete_promo_button}</button>

		<form action="FrontController" method="post">
			<input type="hidden" name="spId" id="spId" pattern="[\d]{1,10}"
				value="0" />
			<div class="modal fade" data-backdrop="false" id="spForm"
				tabindex="-1" role="dialog" aria-labelledby="spFormLabel"
				aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="spFormLabel">${service_plan_card}</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group row">
								<label for="name" class="col-sm-3 col-form-label">${service_plan}</label>
								<div class="col-sm-4">
									<input type="text"
										pattern="([A-Z]{1})([A-Za-z0-9-_\s]{5,50})"
										oninvalid="this.setCustomValidity('${sp_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="name" id="name" value="" required />
								</div>
							</div>
							<div class="form-group row">
								<label for="trafficLimit" class="col-sm-3 col-form-label">${traffic_limit}</label>
								<div class="col-sm-4">
									<input type="text" pattern="([1-9]{1})([0-9]{0,3})?$"
										oninvalid="this.setCustomValidity('${traffic_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="trafficLimit" id="trafficLimit" value="" required />
								</div>
							</div>
							<div class="form-group row">
								<label for="monthlyFee" class="col-sm-3 col-form-label">${monthly_fee}</label>
								<div class="col-sm-4">
									<input type="text"
										pattern="([1-9]{1}[0-9]{0,3})(\.[0-9]{1,2})?$"
										oninvalid="this.setCustomValidity('${fee_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="monthlyFee" id="monthlyFee" value="" required />
								</div>
							</div>
							<div class="form-group row">
								<label for="accessCost" class="col-sm-3 col-form-label">${access_cost}</label>
								<div class="col-sm-4">
									<input type="text"
										pattern="([1-9]{1}[0-9]{0,3})(\.[0-9]{1,2})?$"
										oninvalid="this.setCustomValidity('${access_cost_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="accessCost" id="accessCost" value="" required />
								</div>
							</div>
							<div class="form-group row">
								<label for="description" class="col-sm-3 col-form-label">${description}</label>
								<div class="col-sm-4">
									<input type="text" pattern="[^{}#()]{5,50}"
										oninvalid="this.setCustomValidity('${description_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="description" id="description" value="" required />
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">${close_button}</button>
							<input type="hidden" name="command" id="sp_command" value="" />
							<input type="submit" class="btn btn-outline-secondary btn-sm"
								id="updateSp" value="${execute_button}" /><br />
						</div>
					</div>
				</div>
			</div>
		</form>

		<form action="FrontController" method="post">
			<input type="hidden" name="promoSpId" id="promoSpId"
				pattern="[\d]{1,10}" value="0" /> <input type="hidden"
				name="promoId" id="promoId" pattern="[\d]{1,10}" value="0" />
			<div class="modal fade" data-backdrop="false" id="promoForm"
				tabindex="-1" role="dialog" aria-labelledby="promoFormLabel"
				aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="promoFormLabel">${promo_card}</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="form-group row">
								<label for="promoName" class="col-sm-4 col-form-label">${promo}</label>
								<div class="col-sm-4">
									<input type="text"
										pattern="([A-Za-z]{1})([A-Za-z0-9-_\s]{5,50})"
										oninvalid="this.setCustomValidity('${promo_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="promoName" id="promoName" value="" required />
								</div>
							</div>
							<div class="form-group row">
								<label for="accessDiscount" class="col-sm-4 col-form-label">${access_discount}</label>
								<div class="col-sm-4">
									<input type="text" pattern="([1-9]{1}[\d]{0,1})|0"
										oninvalid="this.setCustomValidity('${access_discount_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="accessDiscount" id="accessDiscount" value="" required />
								</div>
							</div>
							<div class="form-group row">
								<label for="trafficBonus" class="col-sm-4 col-form-label">${traffic_bonus}</label>
								<div class="col-sm-4">
									<input type="text" pattern="([1-9]{1}[\d]{0,1})|0"
										oninvalid="this.setCustomValidity('${traffic_bonus_fill_message}')"
										oninput="setCustomValidity('')" class="form-control"
										name="trafficBonus" id="trafficBonus" value="0" required />
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">${close_button}</button>
							<input type="hidden" name="command" id="promo_command" value="" />
							<input type="submit" class="btn btn-outline-secondary btn-sm"
								id="updateSp" value="${execute_button}" /><br />
						</div>
					</div>
				</div>
			</div>
		</form>

		<c:if test="${not empty requestScope.validationErrorList}">
			<c:forEach var="validation_error"
				items="${requestScope.validationErrorList}">
				<c:if test="${validation_error eq 'InvalidId'}">
					<p>${id_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidName'}">
					<p>${sp_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidLimit'}">
					<p>${traffic_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidFee'}">
					<p>${fee_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidAccessCost'}">
					<p>${access_cost_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidDescription'}">
					<p>${description_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidPromoId'}">
					<p>${id_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidPromoName'}">
					<p>${promo_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidPromoTrafficBonus'}">
					<p>${traffic_bonus_fill_message}</p>
				</c:if>
				<c:if test="${validation_error eq 'InvalidPromoAccessDiscount'}">
					<p>${access_discount_fill_message}</p>
				</c:if>
			</c:forEach>
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
	</div>
</body>
</html>