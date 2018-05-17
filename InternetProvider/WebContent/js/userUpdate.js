function passwordMatchCheck(newPassword, passwordVerification, errorMessage) {
	var passwordValue = newPassword.value;
	var passwordVerificationValue = passwordVerification.value;
	var messageValue = errorMessage.value;

	var isPassMatch = passwordValue == passwordVerificationValue;

	if (!isPassMatch) {
		alert(messageValue);
	}

	return isPassMatch;
}