function prepareSpAddForm() {
	commandValue = "service_plan_add";
	setCommand(commandValue, "sp_command");
	prepareSpAttributes(false);
}

function prepareSpUpdateForm(formName) {
	commandValue = "service_plan_update";
	setCommand(commandValue, "sp_command");
	var isIdCorrect = checkId(formName, "#spForm");
	if (isIdCorrect) {
		prepareSpAttributes(true);
	}
	;
}

function prepareSpDeleteForm(formName) {
	commandValue = "service_plan_remove";
	setCommand(commandValue, "sp_command");
	var isIdCorrect = checkId(formName, "#spForm");
	if (isIdCorrect) {
		prepareSpAttributes(true);
	}
	;
}

function preparePromoAddForm(formName) {
	var commandValue = "promo_add";
	var dataTargetValue = "#promoForm";
	var isIdCorrect = checkId(formName, dataTargetValue);
	setCommand(commandValue, "promo_command");
	if (isIdCorrect) {
		checkPromoValidity(true, formName, dataTargetValue);
	}
}

function preparePromoDeleteForm(formName) {
	var commandValue = "promo_remove";
	var dataTargetValue = "#promoForm";
	var isIdCorrect = checkId(formName, dataTargetValue);
	setCommand(commandValue, "promo_command");
	if (isIdCorrect) {
		checkPromoValidity(false, formName, dataTargetValue);
		preparePromoAttributes();
	}
}

function prepareSpAttributes(fillAttributesIn) {
	var spName = document.getElementById("name");
	var traffic = document.getElementById("trafficLimit");
	var fee = document.getElementById("monthlyFee");
	var accessCost = document.getElementById("accessCost");
	var description = document.getElementById("description");

	if (fillAttributesIn) {
		var currentRow = $("#spTable").find(".table-primary");

		spName.value = currentRow.find(".td_sp_name").html();
		traffic.value = currentRow.find(".td_sp_traffic").html();
		fee.value = currentRow.find(".td_sp_fee").html();
		accessCost.value = currentRow.find(".td_sp_access_cost").html();
		description.value = currentRow.find(".td_sp_description").html();

	} else {
		spName.value = "";
		traffic.value = "";
		fee.value = "";
		accessCost.value = "";
		description.value = "";
	}
	;
}

function preparePromoAttributes() {
	var promoId = document.getElementById("promoId");
	var promoName = document.getElementById("promoName");
	var accessDiscount = document.getElementById("accessDiscount");
	var trafficBonus = document.getElementById("trafficBonus");
	var currentRow = $("#spTable").find(".table-primary");

	promoId.value = currentRow.find(".td_promo_id").html();
	promoName.value = currentRow.find(".td_promo_name").html();
	accessDiscount.value = currentRow.find(".td_promo_access_discount").html();
	trafficBonus.value = currentRow.find(".td_promo_traffic_bonus").html();
}

function setCommand(commandValue, elementId) {
	var command = document.getElementById(elementId);
	command.value = commandValue;
}

function checkSpId() {
	var spId = document.getElementById("chosenSpId");
	var message = document.getElementById("chooseSpMessage").value;
	if (spId.value == 0) {
		alert(message);
		return false;
	}
}

function checkId(formName, dataTargetValue) {
	var spId = document.getElementById("spId");
	var message = document.getElementById("chooseSpMessage").value;
	var isIdCorrect;
	if (spId.value != 0 && spId.value != "undefined") {
		$(formName).attr("data-target", dataTargetValue);
		isIdCorrect = true;
	} else {
		$(formName).attr("data-target", "");
		isIdCorrect = false;
		alert(message);

	}
	return isIdCorrect;
}

function checkPromoValidity(addPromo, formName, dataTargetValue) {
	var isOperationValid;
	var currentRow = $("#spTable").find(".table-primary");
	var promoIdValue = currentRow.find(".td_promo_id").html();

	if (addPromo) {
		var message = document.getElementById("promoExistsMessage").value;
		isOperationValid = (promoIdValue == 0);
	} else {
		var message = document.getElementById("noPromoMessage").value;
		isOperationValid = (promoIdValue > 0);
	}

	if (isOperationValid) {
		$(formName).attr("data-target", dataTargetValue);
	} else {
		$(formName).attr("data-target", "");
		alert(message);
	}

}