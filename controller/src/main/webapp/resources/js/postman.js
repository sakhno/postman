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