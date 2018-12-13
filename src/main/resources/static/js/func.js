$(function () {
    $("#loginBtn").click(function () {
        var data = {
            username: $("#username").val(),
            password: $("#password").val()
        };
        $.ajax({
            url: "/check",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "JSON",
            contentType: "application/json",
            success: function () {
                $(window).attr("location", "/index")
            },
            error: function (res) {
                $(window).attr("location", "/index")
            }
        })
    })
});