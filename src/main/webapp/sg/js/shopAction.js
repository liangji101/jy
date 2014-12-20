
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
                $('.loadingMore').show();
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

    $( document ).on( "vclick", ".loadingMore", function() {

        var me = this;
        var visiableCate = $('.shop-product-categray-list:not(.hidden)');
        if(visiableCate){
            var shop_id = $('#shop_id').val();
            var cateId = visiableCate.data('categray-id');
            var from = $('.js-product-item',visiableCate).last().attr('id');
            var offset = 20;

            $(me).html('<i class="fa fa-spinner fa-spin"></i>');
            $.getJSON(
                "shop/getitems?shop_id=" + parseInt(shop_id) +
                    "&category_id="+ parseInt(cateId)+ "&from="+ parseInt(from) +
                    "&offset="+offset,
                function(data) {
                    if(data.code == 0 && Array.isArray(data.data) && data.data.length > 0){
                        $(data.data).each(function(idx,item){
                            $(visiableCate).append(
                                '<li class="product-item js-product-item" id="'+ item.id +'">' +
                                 '<input type="hidden" class="js-product-item-id" value="'+ item.id +'"data-value="'+ item.id+ '"/>' +
                                '<div class="prod_icon" style="background-image: url("'+ item.pic_url + '")">'+ '</div>' +
                                '<div>'+
                                '<span class="product-title oneline js-product-item-name" data-value="'+ item.name+'">'+ item.name+ '</span>'+
                                '#set($itempricemain = $item.price/100)'+
                                '#set($itempriceleft = $item.price%100)'+
                                '<div class="prod_info">'+
                                '<div class="product-price js-product-item-price" data-value="'+ item.price +'">'+
                                '$itempricemain<span>.$itempriceleft</span>'+
                                '</div>'+
                                '<div class="product_stepper">'+
                                '<div class="product_stepper_minus countChangeAction countChangeActionMinus" style="">-</div>'+
                                '<div class="product_stepper_count">'+
                                '<input type="text" class="product_stepper_count_input item-quantity countChangeAction countChangeActionSet hidden js-product-item-quantity" style="padding-top: 2px" value="0" data-value="0"></div>'+
                                '<div class="product_stepper_add countChangeAction countChangeActionAdd">+</div>'+
                                '</div>'+
                                '</div>'+
                            '</div>'+
                            '</li>'
                            )
                        });

                        $(me).html('点击加载更多');

                    }else{
                        // code not 0
                        $(me).html('点击加载更多');
                        $(me).hide();
                    }
                }
            );
        }
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


