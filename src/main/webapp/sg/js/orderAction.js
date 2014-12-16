
$.mobile.page.prototype.options.domCache = true;

$(document).ready(function(){

    shoppingCart.loadShoppingCart();

    shoppingCart.updateTotal();

    $( document ).on( "click", "#submit_order", function() {

        var items = [];
        for(var idx = 0 ;idx < shoppingCart.shoppingItemsArray.length;idx++){
            items.push({'item_id': shoppingCart.shoppingItemsArray[idx].id ,"count":shoppingCart.shoppingItemsArray[idx].quantity});
        }
        $('#order_items').val(JSON.stringify(items));

    });

    $("#orderConfirm").submit(function( event ) {

        shoppingCart.saveShoppingCart();

        var values = $(this).serialize();
        return true;
    });

});


