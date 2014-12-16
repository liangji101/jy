(function (window, document) {


    var shoppingCart = {

    };

    shoppingCart.shoppingItemsArray = [];
    // special fields for items
    shoppingCart.reservedFields = ['quantity', 'id', 'price', 'name', 'shipping', 'tax', 'taxRate'];


    shoppingCart.getShoppingItemById = function(id){
        var data = null,
            me = this;
        $.each(me.shoppingItemsArray, function (idx,item) {
            if (item.id && item.id == id) {
                data = item;
            }
        });
        return data;
    }

    shoppingCart.incrementShoppingItemById = function(id,amount){

        var me = this;
        var diff = amount || 1;
        diff = parseInt(diff, 10);

        $.each(me.shoppingItemsArray, function (idx,item) {
            if (item.id && item.id == id) {
                item.quantity += diff;
            }
        });
        return;
    }

    shoppingCart.decrementShoppingItemById = function(id,amount){

        var me = this;
        var diff = amount || 1;
        diff = parseInt(diff, 10);

        var array = [];
        $.each(me.shoppingItemsArray, function (idx,item) {
            array.push(item);
            if (item.id && item.id == id) {
                item.quantity -= diff;
                if(item.quantity <= 0){
                    item.quantity = 0;
                    array.pop();
                }
            }
        });
        me.shoppingItemsArray = array;
        return;
    }

    shoppingCart.setAmountOfShoppingItemById = function(id,amount){

        var me = this;
        var diff = amount || 1;
        diff = parseInt(diff, 10);
        if(diff < 0)return;

        $.each(me.shoppingItemsArray, function (idx,item) {
            if (item.id && item.id == id) {
                item.quantity = diff;
            }
        });
        return;
    }

    shoppingCart.getTotalPrice = function(){

        var total = 0.0;

        $.each(this.shoppingItemsArray, function (idx,item) {
            total += parseFloat(item.price) * parseInt(item.quantity);
        });
        return total;
    },

    shoppingCart.getTotalQuantity = function(){

        var total = 0;

        $.each(this.shoppingItemsArray, function (idx,item) {
            total +=  parseInt(item.quantity);
        });
        return total;
    },

    shoppingCart.addNewItem = function(options){
        var data = {},
            me = this;
        // already exist
        if(me.getShoppingItemById(options.id)){
            me.incrementShoppingItemById(options.id);
            return;
        }

        $.each(me.reservedFields, function (idx,field) {
            var key = field;
            if (options[key]) {
                data[key] = options[key];
            }
        });
        me.shoppingItemsArray.push(data);

        return data;
    },

    // storage
    shoppingCart.saveShoppingCart = function () {

        var items = {},me = this;
        localStorage.setItem("shopping_items", JSON.stringify(me.shoppingItemsArray));

    },

     shoppingCart.loadShoppingCart = function () {

        // empty without the update
        var items = localStorage.getItem("shopping_items");

        if (!items) {
            return;
        }

        // we wrap this in a try statement so we can catch
        // any json parsing errors. no more stick and we
        // have a playing card pluckin the spokes now...
        // soundin like a harley.
        try {

            this.shoppingItemsArray = JSON.parse(items);

        } catch (e){
            console.error( "Error Loading data: " + e );
        }
    },


        shoppingCart.updateTotal = function (){

            $("#js-shop-total-price").text(shoppingCart.getTotalPrice());
            $("#js-shop-total-count").text(shoppingCart.getTotalQuantity());
        }


   // events listed here

    $(document).ready(function(){

        $( document ).on( "click", ".countChangeActionAdd", function() {

            var prodcut = $(this).closest( ".js-product-item");

            if(!prodcut)return;

            var name = $('.js-product-item-name',prodcut).data('value'),
                price = $('.js-product-item-price',prodcut).data('value'),
                qauntity = $('.js-product-item-quantity',prodcut).val(),
                id = $('.js-product-item-id',prodcut).data('value');

            qauntity = parseInt(qauntity) + 1;

            $('.js-product-item-quantity',prodcut).val(qauntity);

            shoppingCart.addNewItem({
                'quantity': qauntity,
                'id': id,
                'price': price,
                'name':name
            });

            $('.countChangeAction',prodcut).removeClass('hidden');

            shoppingCart.updateTotal();

        });

        $( document ).on( "click", ".countChangeActionMinus", function() {

            var prodcut = $(this).closest( ".js-product-item");

            if(!prodcut)return;

            var id = $('.js-product-item-id',prodcut).data('value'),
                qauntity = $('.js-product-item-quantity',prodcut).val();

            qauntity = parseInt(qauntity) - 1 > 0 ? parseInt(qauntity) - 1:0;

            $('.js-product-item-quantity',prodcut).val(qauntity);

            shoppingCart.decrementShoppingItemById(id);

            shoppingCart.updateTotal();
        });

        $( document ).on( "click", ".shopping_cart_empty", function() {

            shoppingCart.shoppingItemsArray = [];
            shoppingCart.saveShoppingCart();
            shoppingCart.updateTotal();
        });

    });

    window.shoppingCart = shoppingCart;

}(window, document));

/************ HTML5 Local Storage Support *************/
(function () {if (!this.localStorage)if (this.globalStorage)try {this.localStorage=this.globalStorage}catch(e) {}else{var a=document.createElement("div");a.style.display="none";document.getElementsByTagName("head")[0].appendChild(a);if (a.addBehavior) {a.addBehavior("#default#userdata");var d=this.localStorage={length:0,setItem:function (b,d) {a.load("localStorage");b=c(b);a.getAttribute(b)||this.length++;a.setAttribute(b,d);a.save("localStorage")},getItem:function (b) {a.load("localStorage");b=c(b);return a.getAttribute(b)},
    removeItem:function (b) {a.load("localStorage");b=c(b);a.removeAttribute(b);a.save("localStorage");this.length=0},clear:function () {a.load("localStorage");for (var b=0;attr=a.XMLDocument.documentElement.attributes[b++];)a.removeAttribute(attr.name);a.save("localStorage");this.length=0},key:function (b) {a.load("localStorage");return a.XMLDocument.documentElement.attributes[b]}},c=function (a) {return a.replace(/[^-._0-9A-Za-z\xb7\xc0-\xd6\xd8-\xf6\xf8-\u037d\u37f-\u1fff\u200c-\u200d\u203f\u2040\u2070-\u218f]/g,
    "-")};a.load("localStorage");d.length=a.XMLDocument.documentElement.attributes.length}}})();