
$.mobile.page.prototype.options.domCache = true;

$(document).ready(function(){

    shoppingCart.loadShoppingCart();

    shoppingCart.updateTotal();

    $( document ).on( "vclick", "#submit_shop", function() {

        var items = [];
        for(var idx = 0 ;idx < shoppingCart.shoppingItemsArray.length;idx++){
            items.push({'item_id': shoppingCart.shoppingItemsArray[idx].id ,"count":shoppingCart.shoppingItemsArray[idx].quantity,
                        'price':shoppingCart.shoppingItemsArray[idx].price ,'name':shoppingCart.shoppingItemsArray[idx].name});
        }
        $('#form_items').val(JSON.stringify(items));



    });


    $("#shopConfirm").submit(function( event ) {

        var values = $(this).serialize();
        shoppingCart.saveShoppingCart();

        if(!shoppingCart.productsEnoughtToShip()){
            return false; // can't submit
        }

        return true;
    });

});


