
$.mobile.page.prototype.options.domCache = true;

$(document).ready(function(){

    function warningOfNeccessInput(input){
        $(input).css({'border':'solid 1px red'});
    }

    $('.js-addAddress input').blur(function()          //whenever you click off an input element
    {
        if( !$(this).val() ) {                      //if it is blank.
            $(this).css({'border':'solid 1px red'});
        }else{
            $(this).css({'border':0});
        }
    });

    $( document ).on( "click", "#submit_order", function() {

        var items = [];
        for(var idx = 0 ;idx < shoppingCart.shoppingItemsArray.length;idx++){
            items.push({'item_id': shoppingCart.shoppingItemsArray[idx].id ,"count":shoppingCart.shoppingItemsArray[idx].quantity,
                'price':shoppingCart.shoppingItemsArray[idx].price ,'name':shoppingCart.shoppingItemsArray[idx].name});
        }
        $('#order_items').val(JSON.stringify(items));

        $('#order_address').val($('#newOrderAddress').val());
        $('#order_phone').val($('#newOrderPhone').val());
        $('#order_remarks').val($('#orderRemarks').val());

    });

    $('.addressHint').on( "click", "#submit_order", function() {

    });


    $("#orderConfirm").submit(function( event ) {

        if($('#newOrderAddress') && !$('#newOrderAddress').val()){
            warningOfNeccessInput($('#newOrderAddress'));
            return false;
        }

        if($('#newOrderPhone') && !$('#newOrderPhone').val()){
            warningOfNeccessInput($('#newOrderPhone'));
            return false;
        }

        var values = $(this).serialize();

        shoppingCart.empty();

        return true;
    });

    shoppingCart.loadShoppingCart();
    shoppingCart.updateTotal();

});


