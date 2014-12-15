
$.mobile.page.prototype.options.domCache = true;

$(document).ready(function(){

//    $( document ).on( "vclick", "#submit_shop", function() {
//        var items = [];
//        for(var idx = 0 ;idx < simpleCart.items().length;idx++){
//            items.push({'item_id': simpleCart.items()[idx].fields().original ,"count":simpleCart.items()[idx].fields().quantity});
//        }
////        values += "&items=" + encodeURIComponent(JSON.stringify(items));
//
//        event.preventDefault();
//
//        $.ajax({
//            type: "POST",
//            url: "shopCar/confirm",
//            data: JSON.stringify({"shop_id":"1", "items": items}),
//            dataType: "json",
//            success: function(data){
//                with(window.document)
//                {
//                    open();
//                    write(data.responseText);
//                    close();
//                }
//            },
//            error:function(data){
//                with(window.document)
//            {
//                open();
//                write(data.responseText);
//                close();
//            }
//            }
//        })
//    });

    $( document ).on( "vclick", "#submit_shop", function() {

        var items = [];
        for(var idx = 0 ;idx < simpleCart.items().length;idx++){
            items.push({'item_id': simpleCart.items()[idx].fields().original ,"count":simpleCart.items()[idx].fields().quantity});
        }
        $('#form_items').val(JSON.stringify(items));
    });


    $("#shopConfirm").submit(function( event ) {

        var values = $(this).serialize();

        return true;
    });
});


