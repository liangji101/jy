
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

    $(document).on( "vclick", ".swithDefaultAddress", function() {

        if($('.fa-chevron-right',this) && $('.fa-chevron-right',this).hasClass('fa-rotate-90')){
            $('.fa-chevron-right',this).removeClass('fa-rotate-90');
            $('.addressListSelection').slideToggle();
        }else{
            $('.fa-chevron-right',this).addClass('fa-rotate-90');
            $('.addressListSelection').slideToggle();

        }
    });

    // make select listener
    $(document).on( "vclick", ".addressItem", function() {

        // clear current default or selected
        $('.addressSelected').addClass('addressUnselected');
        $('.addressSelected').removeClass('addressSelected');
        $('.fa-check').removeClass('fa-check');

        $(this).removeClass('addressUnselected');
        $(this).addClass('addressSelected');
        $('.js-fa-placeholder-icon',this).addClass('fa-check');

    });

    $(document).on( "vclick", ".useNewAddress", function() {
        $('.addNewAddressIntoList').slideDown();
        $(this).fadeOut();
    });


    $(document).on( "vclick", ".confirmNewAddress", function() {
        // append to
        var addr = $('#addOrderAddress').val();
        var phone = $('#addOrderPhone').val();
        if(!addr || !phone){
            alert('请填写详细的电话地址');
            return;
        }
        $('.addNewAddressIntoList').slideUp();
        $('.addressSelected').removeClass('addressSelected');

        $('.addressListSelection').prepend(
            '<div class="addressItem addressCandidate addressSelected" style="display: inline-block;width: 100%;text-shadow: none;"><div style="float: left">'+
                '<div>收货地址:' + addr + '</div>'+
                '<div>联系电话:'+  phone+ '</div>'+
                '</div>'+
                '<div class="addressItem-bage">'+
                '<div class="swithDefaultAddress hidden" style="color: #ffffff">'+
                '   <i class="fa fa-2x fa-check"></i></a>'+
                '</div>'+
                '</div>'+
                '</div>'
        )

        $('.useNewAddress').fadeIn();
    });


    $("#orderConfirm").submit(function( event ) {

        // exist the element and have no value
        if($('#newOrderAddress') && $('#newOrderAddress').length &&  !$('#newOrderAddress').val()){
            warningOfNeccessInput($('#newOrderAddress'));
            return false;
        }

        // exist the element and have no value
        if($('#newOrderPhone') && $('#newOrderPhone').length && !$('#newOrderPhone').val()){
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


