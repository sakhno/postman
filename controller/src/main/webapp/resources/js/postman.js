/**
 * Created by antonsakhno on 09.03.16.
 */
$(document).on("click", "#notifybutton", function(event){
    event.preventDefault();
    $.post("/track", { track : track, user : user}, function(responseJson){
        switch (responseJson){
            case "success":
                $("#success").fadeIn();
                break;
            case "exists":
                $("#exists").fadeIn();
                break;
            default:
                $("#dberror").fadeIn();
                break
        }
    });
});
$(document).on('click', '#historytable > tbody > tr', function() {
    var track = $(this).data('value');
    $('#newtrack').val(track);
    $('#newtrackform').submit();
});
$(document).on('submit', '#newtrackform', function(){
    $('#loading').fadeIn();
});
$(document).on("click", "#confirmemail", function(event){
    event.preventDefault();
    $.post("/users/confirmemail", { userid : userid }, function(resposeJson){
       if(resposeJson){
           $("#success").fadeIn();
       } else {
           $("#dberror").fadeIn();
       }
    });
});