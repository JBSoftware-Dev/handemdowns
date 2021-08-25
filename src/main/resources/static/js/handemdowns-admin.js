/*!
 * Handemdowns JavaScript
 * Copyright 2016 handemdowns.com
 */

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

    // Bootstrap Alerts
    $('.alert .close').click(function(e){
        e.preventDefault();
        $(this).parent().hide();
    });

    // Sidebar
    $('#side-menu').metisMenu();

    //Loads the correct sidebar on window load, collapses the sidebar on window resize.
    // Sets the min-height of #page-wrapper to window size
    $(window).bind("load resize", function() {
        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100;
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    var element = $('ul.nav a').filter(function() {
        return this.href == url || url.href.indexOf(this.href) == 0;
    }).addClass('active').parent().parent().addClass('in').parent();
    if (element.is('li')) {
        element.addClass('active');
    }
});