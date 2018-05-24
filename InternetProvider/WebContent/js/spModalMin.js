function checkSpId() {
	var spId = document.getElementById("chosenSpId");
	var message = document.getElementById("chooseSpMessage").value;
	if (spId.value == 0) {
		alert(message);
		return false;
	}
}
