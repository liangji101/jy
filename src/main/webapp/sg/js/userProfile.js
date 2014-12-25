$(document).ready(function () {

    function addNewAddress() {

        // exist the element and have no value
        if ($('#newOrderAddress') && $('#newOrderAddress').length && !$('#newOrderAddress').val()) {
            warningOfNeccessInput($('#newOrderAddress'));
            return false;
        }
        // exist the element and have no value
        if ($('#newOrderPhone') && $('#newOrderPhone').length && !$('#newOrderPhone').val()) {
            warningOfNeccessInput($('#newOrderPhone'));
            return false;
        }

        var address = $('#newOrderAddress').val(),
            phone = $('#newOrderPhone').val();

        addSpinner();

        $.getJSON('../address/default',
            { 'shop_id': getParameterByName('shop_id'),
                'address': address,
                'phone': phone,
                'address_id': ''},
            function (status) {

                removeSpinner();

                if (status.code == 0) {

                    document.location.reload();

                } else {
                    alert('添加新地址，请再次尝试');
                }
            });
    }

    $(".addNewAddressConfirm").click(function () {
        addNewAddress();
    });

    $(".addressItem").click(function () {
        makePost('../address?shop_id=' + getParameterByName('shop_id'), {
            'origUrl': 'user/profile?shop_id='+getParameterByName('shop_id')
        })
    });

    $('.backToShop').click(function(){
        makeGo(' ../shop?shop_id=' + getParameterByName('shop_id'));
    });

    $(function () {
        FastClick.attach(document.body);
    });

    // load snapshot
    $('.order-item').each(function (idx, item) {

        var string = $('.order-snapshots-string', item).val();
        try {
            var orders = JSON.parse(string);
            if (orders && Array.isArray(orders)) {
                var totalCount = 0, totalPrice = 0.0;
                $(orders).each(function (o_indx, order) {

                    totalCount += parseInt(order.count);
                    totalPrice += parseFloat(order.price || 0) * parseInt(order.count);

                    $('.order-snapshots', item).append(

                        '<div style="display: inline-block;width: 90%">' +
                            '<div style="float: left">' +
                            order.name +
                            '   </div>' +
                            '   <div style="float: right">' +
                            '       <span>￥</span> ' + parseFloat(order.price ? order.price / 100.0 : 0.0) + '<span style="padding: 0.2em">X</span>' + order.count +
                            '   </div>' +
                            ' </div>'
                    );
                });
                $('.order-snapshots', item).append(

                    '<div style="display: block;width: 90%;text-align: right;margin-top: 20px">' +
                        '  共计: ' + totalCount +
                        ' 件 <span style="color: red">￥ ' + parseFloat(totalPrice / 100.0) +
                        '</span>' +
                        ' </div>');
            }
        } catch (e) {
            console.error(e);
        }

    });


});


