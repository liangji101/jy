
$.mobile.page.prototype.options.domCache = true;

$(document).ready(function(){

    var submitAddressId, submitAddress,submitPhone, newAddedAddresss=[];

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

        if($('.firstTimeAddAddress') && $('.firstTimeAddAddress').length){
            // first time add address, no address ID
            updateSubmitInfo(JSON.stringify(items),
                '' ,
                $('#newOrderAddress').val(),
                $('#newOrderPhone').val(),
                $('#orderRemarks').val());
        }else{
            // already have it, just use default message
            updateSubmitInfo(JSON.stringify(items),
                $('.addressDefault-id').val(),
                $('.addressDefault-address').data('default-address'),
                $('.addressDefault-phone').data('default-phone'),
                $('#orderRemarks').val());
        }

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

    function updateDefaultAddress(address, phone, id){
        // update default item
        $('.addressDefault-address').html('<span class="addressTitle">收货地址:</span>' + address);
        $('.addressDefault-address').data('default-address' , address);

        $('.addressDefault-phone').html('<span class="addressTitle">联系电话:</span>' + phone);
        $('.addressDefault-phone').data('default-phone', phone);

        $('.addressDefault-id').val(id);

    }


    function updateSubmitInfo(items, addressId, address,phone, remark){

        if(items){
            $('#order_items').val(items);
        }
        if(addressId){
            $('#order_address_id').val(addressId);
        }

        if(address){
            $('#order_address').val(address);
        }

        if(phone){
            $('#order_phone').val(phone);
        }

        if(remark){
            $('#order_remarks').val($('#orderRemarks').val());
        }

    }
    // make select listener
    $(document).on( "vclick", ".addressListSelection .addressItem", function() {

        // clear current default or selected
        $('.addressListSelection .fa-check').removeClass('fa-check');
        $('.js-address-checked',this).addClass('fa-check');

        // update default item
        updateDefaultAddress($('.addressItem-address',this).data('default-address'),
            $('.addressItem-phone',this).data('default-phone'),
            $('.addressItem-id',this).val());

    });

    $(document).on( "vclick", " .addressListSelection .useNewAddress", function() {
        $('.addNewAddressIntoList').slideDown();
        $(this).fadeOut();
    });
    $(document).on( "vclick", ".addNewAddressIntoList .cancelNewAddress", function() {
        $('.addNewAddressIntoList').slideUp();
        $(".addressListSelection .useNewAddress").fadeIn();
    });

    $(document).on( "vclick", ".addNewAddressIntoList .confirmNewAddress", function() {
        // append to
        var addr = $('.addNewAddressIntoList .addOrderAddress').val();
        if(!addr){
            warningOfNeccessInput($('.addNewAddressIntoList .addOrderAddress'));
            return;
        }
        var phone = $('.addNewAddressIntoList .addOrderPhone').val();
        if(!phone){
            warningOfNeccessInput($('.addNewAddressIntoList .addOrderPhone'));
            return;
        }

        $('.addNewAddressIntoList').slideUp();

        updateDefaultAddress(addr,phone ,'');

        $('.addressListSelection .fa-check').removeClass('fa-check');// remove all other check
        $('.addressListSelection').prepend(
            '<div class="addressItem addressCandidate">' +
                '<div style="float: left">'+
                    '<div class="addressItem-address" data-default-address="'+ addr +'"><span class="addressTitle">收货地址:</span>' + addr + '</div>'+
                    '<div class="addressItem-phone" data-default-phone="'+ phone +'"><span class="addressTitle">联系电话:</span>'+  phone+ '</div>'+
                    '<input class="addressItem-id" type="hidden" value=""/>'+
                '</div>'+
                    '<div class="addressItem-bage">'+
                        '<div class="swithDefaultAddress">'+
                        '   <i class="fa fa-2x fa-check js-address-checked"></i></a>'+
                        '</div>'+
                    '</div>'+
                '</div>'
        )

        $(".addressListSelection .useNewAddress").fadeIn();
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


