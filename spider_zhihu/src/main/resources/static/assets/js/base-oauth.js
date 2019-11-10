$(function() {
	checkToken();//校验token
});
//校验token
var checkToken = function() {
	$.ajax({
		type: "POST",
		url: "/login/checkToken",
		data: {token: $.cookie("token")},
		dataType: 'json',
		success: function(data) {
			if(!data.success) {
				window.location.href = "/login.html";
			}
		},
		error: function() {}
	});
}
