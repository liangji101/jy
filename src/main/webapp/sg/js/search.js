
$(document).ready(function(){

    $(window).bind('beforeunload', function(){
        shoppingCart.saveShoppingCart();
    });

    shoppingCart.loadShoppingCart();
    shoppingCart.updateTotal();


    $('#searchForm').submit(function(){

//        var q = $('.searchBar-input',this).val();

//        window.location.href = location.origin + location.pathname + location.search.replace(/&key=[^&]*/g,'&key='+q);

//        window.location.reload();

//        return false;
    });
});
