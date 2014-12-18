
$.mobile.page.prototype.options.domCache = true;

$(document).ready(function(){



    function toggleSelectOnCategray(categray,selected){

        if(selected){
            $(categray).addClass('shop-categray-selected');
            $('.shop-categray-item',categray).addClass('shop-categray-item-selected');
            $('.shop-categray-item-decorate',categray).removeClass('hidden');

        }else{
            $(categray).removeClass('shop-categray-selected');
            $('.shop-categray-item',categray).removeClass('shop-categray-item-selected');
            $('.shop-categray-item-decorate',categray).addClass('hidden');
        }
    }

    function toggleProductOnCategrayClick(categray){

        var cateId = $(categray).data('categray-id');
        $('.shop-product-categray-list').each(function(idx,item){

            if($(item).data('categray-id') == cateId){
                $(item).removeClass('hidden');
            }else{
                $(item).addClass('hidden');
            }
        })
    }

    $( document ).on( "vclick", ".shop-categray", function() {
        var me = this;
        $('.shop-categray').each(function(idx,item){
            toggleSelectOnCategray(item, me == item ? true: false);
        })
        toggleProductOnCategrayClick(me);
    });

    $( document ).on( "click", "#submit_shop", function() {

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

    var defaultCategray = $('.shop-categray')[0];
    toggleSelectOnCategray(defaultCategray,true);
    toggleProductOnCategrayClick(defaultCategray);


    shoppingCart.loadShoppingCart();
    shoppingCart.updateTotal();

});


