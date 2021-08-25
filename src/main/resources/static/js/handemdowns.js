/*!
 * Handemdowns JavaScript
 * Copyright 2016 handemdowns.com
 */

$.fn.exist = function(){
    return $(this).length > 0;
};

function getUrlParam(key) {
    var uri = window.location.search.substring(1);
    var params = uri.split('&');
    for (var i = 0; i < params.length; i++) {
        var param = params[i].split('=');
        if (param[0] == key) {
            return param[1];
        }
    }
}

function updateUrlParam(key, value) {
    var uri = window.location.search.substring(1);
    var i = uri.indexOf('#');
    var hash = i === -1 ? ''  : uri.substr(i);
    uri = i === -1 ? uri : uri.substr(0, i);

    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (!value) {
        uri = uri.replace(new RegExp("([?&]?)" + key + "=[^&]*", "i"), '');
        if (uri.slice(-1) === '?') {
            uri = uri.slice(0, -1);
        }
        if (uri.indexOf('?') === -1) uri = uri.replace(/&/, '?');
    } else if (uri.match(re)) {
        uri = uri.replace(re, '$1' + key + "=" + value + '$2');
    } else {
        uri = uri + separator + key + "=" + value;
    }
    return uri + hash;
}

function getGeoLocation(successCallback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            $.post('/set-location', {lat: position.coords.latitude, lon: position.coords.longitude}, function(response) {
                if (response.status == "success") {
                    var location = response.data;
                    successCallback(location);
                }
            }).fail(function(xhr) {
                console.log(xhr);
            });
        }, function(error) {
            switch(error.code) {
                case error.PERMISSION_DENIED:
                    console.log("User denied the request for GeoLocation.");
                    break;
                case error.POSITION_UNAVAILABLE:
                    console.log("GeoLocation information is unavailable.");
                    break;
                case error.TIMEOUT:
                    console.log("The request to get user location timed out.");
                    break;
                case error.UNKNOWN_ERROR:
                    console.log("An unknown error occurred.");
                    break;
            }
        });
    } else {
        console.log("GeoLocation is not supported by this browser.  Try updating your browser.");
    }
}

function getLocationDisplayName(location) {
    return location.area != null ? location.area : location.city;
}

function ajaxLogin(successCallback) {
    var username = $('#username').val();
    var password = $('#password').val();
    var errorMessage =  $('#login-error-message');
    $.ajax({
        url: '/login/submit',
        type: 'POST',
        data: {'username': username, 'password': password},
        dataType: 'json',
        success: function(response) {
            if(response.success) {
                $('#login-modal').modal('toggle');
                successCallback();
            } else {
                errorMessage.find('span:first').text("Unexpected Error: Unable to log in automatically.");
                errorMessage.show();
            }
        },
        error: function(response) {
            var reason = response.responseText.split(": ").pop().replace("}", "");
            errorMessage.find('span:first').text(reason);
            errorMessage.show();
        }
    });
}

function validateSearch() {
    var isValid = true;
    var topic = $('#topic');
    var btnSearch = $('#btn-search');

    topic.removeClass("error-outline");
    btnSearch.removeClass("btn-danger");
    if (!topic.val()) {
        topic.addClass("error-outline");
        btnSearch.addClass("btn-danger");
        isValid = false;
    }
    return isValid;
}

function getCsrfHeader() {
    return $("meta[name='_csrf_header']").attr("content");
}

function getCsrfToken() {
    return $("meta[name='_csrf']").attr("content");
}

$(document).ajaxSend(function(e, xhr) {
    xhr.setRequestHeader(getCsrfHeader(), getCsrfToken());
});

$(document).ready(function() {
    // Preloader
	$('#status').fadeOut();
	$('#preloader').delay(100).fadeOut();
	$('body').delay(100).css({'overflow':'visible'});

	// Social Icons
    if ($('#social-icons').exist()) {
        SocialShareKit.init();
    }

    // Bootstrap Alerts
    $('.alert .close').click(function(e){
        e.preventDefault();
        $(this).parent().hide();
    });

    // Nav Dropdown
	$('ul.nav li.nav-dropdown').click(function() {
		if ($(window).width() > 768) {
			$(this).addClass('open');
		}
	}, function() {
		$(this).find('*').removeClass('open');
	});

    // Category Menu
	$('.category-menu .category').click(function (e) {
		e.preventDefault();
		$('.category-menu').find('a[data-toggle="dropdown"]').text("Category: " + $(this).text()).append(" <span class='caret'></span>");
		$('#category-search').val($(this).data("category-code"));
		$.post('/set-category', {category: $(this).data("category-code")});
	});

    // Back To Top Button
	$(window).scroll(function() {
		if ($(this).scrollTop()>70) {
			$('.back-top').fadeIn();
		} else {
			$('.back-top').fadeOut();
		}
	});

    // Login Modal
    $('#login-action').click(function(e) {
        e.preventDefault();
        $('#login-modal').modal('toggle');
    });
});
