function checkUserId(formName) {
	var userId = document.getElementById("chosenUserId");
	var message = document.getElementById("chooseUserMessage").value;

	if (userId.value != 0 && userId.value != "undefined") {
		$(formName).attr("data-target", "#userForm");
	} else {
		$(formName).attr("data-target", "");
		alert(message);
	}
}