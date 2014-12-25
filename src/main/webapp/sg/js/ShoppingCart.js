(function (window, document) {


    var shoppingCart = {

    };

    shoppingCart.shoppingItemsCategraysDict = {};
    // special fields for items
    shoppingCart.reservedFields = ['quantity', 'id', 'cateId', 'price', 'name', 'shipping', 'tax', 'taxRate'];


    function isEmpty(map) {
        for (var key in map) {
            if (map.hasOwnProperty(key)) {
                return false;
            }
        }
        return true;
    }


    shoppingCart.getTotalPrice = function () {

        var total = 0.0;

        for (var cates in this.shoppingItemsCategraysDict) {
            var items = this.shoppingItemsCategraysDict[cates];
            for (var item in items) {
                var itemdetail = items[item];
                total += parseFloat(itemdetail.price || 0.0) * parseInt(itemdetail.quantity || 0);
            }
        }
        return total/100.0;

    };

    shoppingCart.getTotalQuantity = function () {

        var total = 0;

        for (var cates in this.shoppingItemsCategraysDict) {
            var items = this.shoppingItemsCategraysDict[cates];
            for (var item in items) {
                var itemdetail = items[item];
                total += parseInt(itemdetail.quantity || 0);
            }
        }
        return total;
    };

    shoppingCart.empty = function () {

        this.shoppingItemsCategraysDict = {};

        this.saveShoppingCart();

    };

    shoppingCart.findCateIdForProductId = function (productID) {

        for (var cates in this.shoppingItemsCategraysDict) {
            var items = this.shoppingItemsCategraysDict[cates];
            for (var item in items) {
                var itemdetail = items[item];
                if (item == productID || itemdetail.id == productID)return cates;
            }
        }
    };

    // storage
    shoppingCart.saveShoppingCart = function () {

        var items = {}, me = this;
        localStorage.setItem("categray_dict", JSON.stringify(me.shoppingItemsCategraysDict));
    };

    shoppingCart.loadShoppingCart = function () {

        // empty without the update
        var categraies = localStorage.getItem("categray_dict");

        if (!categraies) {
            return;
        }

        // we wrap this in a try statement so we can catch
        // any json parsing errors. no more stick and we
        // have a playing card pluckin the spokes now...
        // soundin like a harley.
        try {

            this.shoppingItemsCategraysDict = JSON.parse(categraies);

        } catch (e) {
            console.error("Error Loading data: " + e);
        }
    };


    shoppingCart.productsEnoughtToShip = function () {

        var definedMinFreeDelivery = 20;

        var totolp = parseFloat(shoppingCart.getTotalPrice());

        return totolp >= definedMinFreeDelivery;
    };

    shoppingCart.updateTotal = function () {

        var definedMinFreeDelivery = 20;
        var totolp = parseFloat(shoppingCart.getTotalPrice());

        if (totolp < definedMinFreeDelivery) {

            var number = Math.round((definedMinFreeDelivery - totolp) * 100) / 100;
            $(".js-checkout-hit").html('差 <span style="font-weight: 100;padding-left: 0.3em;padding-right: 0.3em;font-size: 18px">' + number + ' </span> 元起送');
            $(".js-confirm-hit").html('差 <span style="font-weight: 100;padding-left: 0.3em;padding-right: 0.3em;font-size: 18px">' + number + ' </span> 元起送');

            $(".js-cart-checkout").css({'background-color': '#7c7d80'});
        } else {
            $(".js-checkout-hit").css({'font-weight': 100, 'padding-left': '0.3em', 'padding-right': '0.3em', 'font-size': '18px'});
            $(".js-checkout-hit").text('去结算');

            $(".js-cart-checkout").css({'background-color': '#F54447'});

            $(".js-confirm-hit").css({'font-weight': 100, 'padding-left': '0.3em', 'padding-right': '0.3em', 'font-size': '18px'});
            $(".js-confirm-hit").text('确定');
        }
        $("#js-shop-total-price").text(totolp);
        $("#js-shop-total-count").text(shoppingCart.getTotalQuantity());

        // update categray shop list
        var me = this;
        $('.shop-categray').each(function (idx, item) {

            var cid = $(item).data('categray-id');
            if (cid && me.shoppingItemsCategraysDict && me.shoppingItemsCategraysDict[cid]) {
                var total = 0, cates = me.shoppingItemsCategraysDict[cid];
                for (var prop in cates) {

                    var itemdetail = cates[prop];
                    total += parseInt(itemdetail.quantity || 0);
                }
                if (total > 0) {
                    $('.shop-categray-item-edge', item).removeClass('hidden');
                    $('.shop-categray-item-edge', item).text(parseInt(total));
                } else {
                    $('.shop-categray-item-edge', item).addClass('hidden');
                }
            }else{
                $('.shop-categray-item-edge', item).text(0);
                $('.shop-categray-item-edge', item).addClass('hidden');
            }
        })
    };


    shoppingCart.updateCategrayDictOnProductCountChange = function (fields, action) {

        // update categray list
//            {
//                '12':{ // cateid
//                    '121212':{ // item id
//
//                        'name':'sssss',
//                        'price':'101',
//                        'quantity':'2'
//                    }
//                }
//            }

        this.shoppingItemsCategraysDict = this.shoppingItemsCategraysDict || {};

        var cateId = fields.cateId;
        var id = fields.id;


        this.shoppingItemsCategraysDict[cateId] = this.shoppingItemsCategraysDict[cateId] || {};

        if (action == 'add') {

            if (this.shoppingItemsCategraysDict[cateId][id]) {
                var obj = this.shoppingItemsCategraysDict[cateId][id];
                obj.quantity = parseInt(obj.quantity, 10) + 1;
            } else {
                this.shoppingItemsCategraysDict[cateId][id] = {'name': fields.name, 'price': fields.price, 'quantity': 1};
            }

        } else if (action == 'minus') {
            if (this.shoppingItemsCategraysDict[cateId][id]) {

                var obj = this.shoppingItemsCategraysDict[cateId][id];
                obj.quantity = parseInt(obj.quantity, 10) - 1;
                if (obj.quantity == 0) {
                    delete this.shoppingItemsCategraysDict[cateId][id];
                    if (isEmpty(this.shoppingItemsCategraysDict[cateId])) {
                        delete this.shoppingItemsCategraysDict[cateId];
                    }
                }
            }
        }

    };

    shoppingCart.getItemString = function () {

        var results = [];
        for (var cates in this.shoppingItemsCategraysDict) {
            var items = this.shoppingItemsCategraysDict[cates];
            for (var item in items) {
                var itemdetail = items[item];

                results.push({'item_id': item, "count": itemdetail.quantity,
                    'price': itemdetail.price, 'name': itemdetail.name,
                    'pic_url': itemdetail.pic_url});
            }
        }

        return JSON.stringify(results);
    };


    // events listed here

    shoppingCart.countChangeAddHanlder = function (ele) {

        var prodcut = $(ele).closest(".js-product-item");

        if (!prodcut)return;

        var name = $('.js-product-item-name', prodcut).data('value'),
            price = $('.js-product-item-price', prodcut).data('value'),
            qauntity = $('.js-product-item-quantity', prodcut).text(),
            id = $('.js-product-item-id', prodcut).data('value');

        qauntity = parseInt(qauntity) + 1;

        $('.js-product-item-quantity', prodcut).text(qauntity);

        $(ele).siblings(".product_stepper_count").removeClass('hidden');
        $(ele).siblings(".product_stepper_minus").removeClass('hidden');

        var categray = $(prodcut).closest(".js-shop-product-categray-list");

        var cateId = categray && categray.data('categray-id');

        if (!cateId) {
            cateId = shoppingCart.findCateIdForProductId(id);
        }

        if(cateId == undefined){ // make it default cateid
            cateId = 0;
            qauntity = 1;
        }

        var dataFields = {
            'quantity': qauntity,
            'id': id,
            'price': price,
            'name': name,
            'cateId': cateId
        }

        shoppingCart.updateCategrayDictOnProductCountChange(dataFields, 'add');

        shoppingCart.updateTotal();

        shoppingCart.saveShoppingCart();

    };

    shoppingCart.countChangeMinusHanlder = function (ele, options) {

        var deleteItemWhenMinusToZero = options && options.deleteItemWhenMinusToZero || false;
        var prodcut = $(ele).closest(".js-product-item");

        if (!prodcut)return;

        var id = $('.js-product-item-id', prodcut).data('value'),
            qauntity = $('.js-product-item-quantity', prodcut).text();

        qauntity = parseInt(qauntity) - 1 > 0 ? parseInt(qauntity) - 1 : 0;

        $('.js-product-item-quantity', prodcut).text(qauntity);

        var categray = $(prodcut).closest(".js-shop-product-categray-list");

        var cateId = categray && categray.data('categray-id');

        if (!cateId) {
            cateId = shoppingCart.findCateIdForProductId(id);
        }

        if(cateId == undefined){ // make it default cateid
            cateId = 0;
        }

        if (qauntity == 0) {
            if (deleteItemWhenMinusToZero) {
                prodcut.remove();
            }
            $(ele).siblings(".product_stepper_count").addClass('hidden');
            $(ele).addClass('hidden');
        }

        var dataFields = {
            'quantity': qauntity,
            'id': id,
            'cateId': cateId
        }

        shoppingCart.updateCategrayDictOnProductCountChange(dataFields, 'minus');

        shoppingCart.updateTotal();

        shoppingCart.saveShoppingCart();

    };

    window.shoppingCart = shoppingCart;

}(window, document));

/************ HTML5 Local Storage Support *************/
(function () {
    if (!this.localStorage)if (this.globalStorage)try {
        this.localStorage = this.globalStorage
    } catch (e) {
    } else {
        var a = document.createElement("div");
        a.style.display = "none";
        document.getElementsByTagName("head")[0].appendChild(a);
        if (a.addBehavior) {
            a.addBehavior("#default#userdata");
            var d = this.localStorage = {length: 0, setItem: function (b, d) {
                a.load("localStorage");
                b = c(b);
                a.getAttribute(b) || this.length++;
                a.setAttribute(b, d);
                a.save("localStorage")
            }, getItem: function (b) {
                a.load("localStorage");
                b = c(b);
                return a.getAttribute(b)
            },
                removeItem: function (b) {
                    a.load("localStorage");
                    b = c(b);
                    a.removeAttribute(b);
                    a.save("localStorage");
                    this.length = 0
                }, clear: function () {
                    a.load("localStorage");
                    for (var b = 0; attr = a.XMLDocument.documentElement.attributes[b++];)a.removeAttribute(attr.name);
                    a.save("localStorage");
                    this.length = 0
                }, key: function (b) {
                    a.load("localStorage");
                    return a.XMLDocument.documentElement.attributes[b]
                }}, c = function (a) {
                return a.replace(/[^-._0-9A-Za-z\xb7\xc0-\xd6\xd8-\xf6\xf8-\u037d\u37f-\u1fff\u200c-\u200d\u203f\u2040\u2070-\u218f]/g,
                    "-")
            };
            a.load("localStorage");
            d.length = a.XMLDocument.documentElement.attributes.length
        }
    }
})();


