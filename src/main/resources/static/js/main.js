$(document).ready(function () {
    $("#search-form").submit(function (event) {
        event.preventDefault();
        ajax_submit();
    });
});

function ajax_submit() {
    var wholeNumber = $("#code").val();
    wholeNumber = wholeNumber.replace(/\s/g, '');
    if (!wholeNumber.startsWith('+')) {
        wholeNumber = "+" + wholeNumber;
    }
    var prefix;
    var number;
	var data = {}
	data["phoneData"] = $("#phoneData").val();
    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/default",
        data: JSON.stringify(data),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            for (var i = 0; i < data.result.length; i++) {
                if (wholeNumber.startsWith(data.result[i].code)) {
                    prefix = wholeNumber.substr(0, data.result[i].code.length);
                    number = wholeNumber.substr(data.result[i].code.length, wholeNumber.length);
                    break;
                }
            }
            ajax_search(prefix, number);
            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            var json = "<h4>Response from the server:</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });
}

function ajax_search(prefix, number) {
    var search = {}
    console.log("search : ", search);
    search["number"] = number;
    console.log("prefix : ", prefix);
    search["code"] = prefix;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/search",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            var resp = "<h4>Response</h4> <h5>Your number: " + prefix +
            " " + number + "</h5> <h5>Countries: ";
            for (var i = 0; i < data.result.length; i++) {
                for (var j = 0; j < data.result[i].countries.length; j++) {
                    if (j != data.result[i].countries.length - 1) {
                        resp += " " + data.result[i].countries[j] + "; ";
                    }
                    else {
                        resp += " " + data.result[i].countries[j];
                    }
                }
            }
            resp += "</h5>";
            $('#feedback').html(resp);
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            var json = "<h4>Response from the server:</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);
            console.log("ERROR : ", e);
        }
    });
}

function validateForm() {
    var wholeNumber = document.forms["search-form"]["code"].value;
    var regex = /^[+]?[0-9 ]{1,15}$/;

    if (wholeNumber == "") {
        alert("Enter a phone number.");
        return false;
    }
    if (!regex.test(wholeNumber)) {
        alert("Phone number must contain digits (e.g. '1 123 566' or '+3711234').");
        return false;
    }
    else {
        alert("Accepted.");
    }
}