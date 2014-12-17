$.mobile.page.prototype.options.domCache = true;

$(document).ready(function () {

    $('.order-item').each(function (idx, item) {

        var string = $('.order-snapshots-string', item).val();
        try {
            var orders = JSON.parse(string);
            if (orders && Array.isArray(orders)) {
                var totalCount = 0,totalPrice = 0.0;
                $(orders).each(function (o_indx, order) {

                    totalCount += parseInt(order.count);
                    totalPrice += parseFloat(order.price) * parseInt(order.count);

                        $('.order-snapshots', item).append(

                        '<div style="display: inline-block;width: 90%">' +
                            '<div style="float: left">' +
                            order.name +
                            '   </div>' +
                            '   <div style="float: right">' +
                            '       <span>￥</span> ' + parseFloat(order.price/100.0) + '<span style="padding: 0.2em">X</span>' + order.count +
                            '   </div>' +
                            ' </div>'
                     );
                });
                $('.order-snapshots', item).append(

                    '<div style="display: block;width: 90%;text-align: right">' +
                        '  共计: ' + totalCount +
                        ' 件 <span style="color: red">￥ ' +  parseFloat(totalPrice/100.0)  +
                        '</span>' +
                        ' </div>');
            }
        } catch (e) {
            console.error(e);
        }

    });
});


