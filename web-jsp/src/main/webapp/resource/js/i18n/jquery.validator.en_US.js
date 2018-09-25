/*********************************
 * Themes, rules, and i18n support
 * Locale: English
 *********************************/
(function ($) {
    /* Global configuration
     */
    $.validator.config({
        //stopOnError: false,
        //theme: 'yellow_right',
        defaultMsg: "This field is not valid.",
        loadingMsg: "Validating...",
        
        // Custom rules
        rules: {
            digits: [/^\d+$/, "Please enter only digits."]
            ,letters: [/^[a-z]+$/i, "{0}Only can input letters."]
            ,tel: [/^(?:(?:0\d{2,3}[- ]?[1-9]\d{6,7})|(?:[48]00[- ]?[1-9]\d{6}))$/, "Telephone number format is not correct."]
            ,mobile: [/^1[3-9]\d{9}$/, "Phone number format is not correct."]
            ,email: [/^(?:[a-z0-9]+[_\-+.]?)*[a-z0-9]+@(?:([a-z0-9]+-?)*[a-z0-9]+\.)+([a-z]{2,})+$/i, "Email address format is not correct."]
            ,qq: [/^[1-9]\d{4,}$/, "QQ number format is not correct."]
            ,date: [/^\d{4}-\d{1,2}-\d{1,2}$/, "Please enter the correct date,such as:yyyy-mm-dd"]
            ,time: [/^([01]\d|2[0-3])(:[0-5]\d){1,2}$/, "Please enter the correct time,such as:14:30 or 14:30:00"]
            ,ID_card: [/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/, "Please enter the correct ID number."]
            ,url: [/^(https?|ftp):\/\/[^\s]+$/i, "The url format is not correct."]
            ,postcode: [/^[1-9]\d{5}$/, "The postal code format is not correct."]
            ,chinese: [/^[\u0391-\uFFE5]+$/, "Please enter in Chinese."]
            ,username: [/^\w{3,12}$/, "Please enter the 3-12 digits、letters、underlines."]
            ,password: [/^[0-9a-zA-Z]{6,16}$/, "The password is composed of 6 to 16 digits、letters."]
            ,accept: function (element, params){
                if (!params) return true;
                var ext = params[0];
                return (ext === '*') ||
                       (new RegExp(".(?:" + (ext || "png|jpg|jpeg|gif") + ")$", "i")).test(element.value) ||
                       this.renderMsg("Only accept {1} suffix", ext.replace('|', ','));
            }
                
            }
        });
    /* Default error messages
     */
    $.validator.config({
        messages: {
            required: "This field is required.",
            remote: "Please try another name.",
            integer: {
                '*': "Please enter an integer.",
                '+': "Please enter a positive integer.",
                '+0': "Please enter a positive integer or 0.",
                '-': "Please enter a negative integer.",
                '-0': "Please enter a negative integer or 0."
            },
            match: {
                eq: "{0} must be equal to {1}.",
                neq: "{0} must be not equal to {1}.",
                lt: "{0} must be less than {1}.",
                gt: "{0} must be greater than {1}.",
                lte: "{0} must be less than or equal to {1}.",
                gte: "{0} must be greater than or equal to {1}."
            },
            range: {
                rg: "Please enter a number between {1} and {2}.",
                gt: "Please enter a number greater than or equal to {1}.",
                lt: "Please enter a number less than or equal to {1}."
            },
            checked: {
                eq: "Please check {1} items.",
                rg: "Please check between {1} and {2} items.",
                gt: "Please check at least {1} items.",
                lt: "Please check no more than {1} items."
            },
            length: {
                eq: "Please enter {1} characters.",
                rg: "Please enter a value between {1} and {2} characters long.",
                gt: "Please enter at least {1} characters.",
                lt: "Please enter no more than {1} characters.",
                eq_2: "",
                rg_2: "",
                gt_2: "",
                lt_2: ""
            }
        }
    });


    /* Themes
     */
    var TPL_ARROW = '<span class="n-arrow"><b>◆</b><i>◆</i></span>';
    $.validator.setTheme({
        'simple_right': {
            formClass: 'n-simple',
            msgClass: 'n-right'
        },
        'simple_bottom': {
            formClass: 'n-simple',
            msgClass: 'n-bottom'
        },
        'yellow_top': {
            formClass: 'n-yellow',
            msgClass: 'n-top',
            msgArrow: TPL_ARROW
        },
        'yellow_right': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgArrow: TPL_ARROW
        },
        'yellow_right_effect': {
            formClass: 'n-yellow',
            msgClass: 'n-right',
            msgArrow: TPL_ARROW,
            msgShow: function($msgbox, type){
                var $el = $msgbox.children();
                if ($el.is(':animated')) return;
                if (type === 'error') {
                    $el.css({
                        left: '20px',
                        opacity: 0
                    }).delay(100).show().stop().animate({
                        left: '-4px',
                        opacity: 1
                    }, 150).animate({
                        left: '3px'
                    }, 80).animate({
                        left: 0
                    }, 80);
                } else {
                    $el.css({
                        left: 0,
                        opacity: 1
                    }).fadeIn(200);
                }
            },
            msgHide: function($msgbox, type){
                var $el = $msgbox.children();
                $el.stop().delay(100).show().animate({
                    left: '20px',
                    opacity: 0
                }, 300, function(){
                    $msgbox.hide();
                });
            }
        }
    });
})(jQuery);