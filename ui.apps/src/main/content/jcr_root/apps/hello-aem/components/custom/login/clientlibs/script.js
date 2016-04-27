
jQuery(function ($) {

    'use strict';

    $.ajaxSetup({
        statusCode: {
            403: $.noop
        }
    });

    function displayError(message) {
        alert("error: " + message);
    }

    $("#login").submit(function(event) {
        event.preventDefault();
        var form = this;
        var path = form.action;
        var user = form.inputEmail.value;
        var pass = form.inputPassword.value;
        //var errorMessage = form.errorMessage.value;
        //var resource = form.resource.value;

        // if no user is given, avoid login request
        if (!user) {
            return true;
        }

        // send user/id password to check and persist
        $.ajax({
              type: "POST",
              url: '/bin/hello-aem/login',
              data: {
                    _charset_: "utf-8",
                    inputEmail: user,
                    inputPassword: pass,
                },
              success: function (data, code, jqXHR) {
                console.log("success");
                var json = jQuery.parseJSON(data);
                
                var msg = json.msg;
                if (json.msg == 'error') {
                   $('#output').html(json.description);
                   $('#output').show(); 
                } else {
                    document.location = '/content/hello-aem/en/BootstrapTesting.html';
                }
              },
              error: function() {
                    displayError(errorMessage);
                    form.inputPassword.value = "";
                    console.log("error");
                },
              dataType: "text"
            });
        
        return true;
    });


    function init() {
        console.log("init");
        $('#output').hide();
    }
    
    init();
});