Number(M.getCookie(r)) ? M._alert("商品已在购物车咯") : 
                        ("function" == typeof M.updateCartNum && M.updateCartNum(1), M.setCookie("WD_c_l", Number(M.getCookie("WD_c_l")) + t), M.setCookie(r, t), M.setCookie("WD_i", M.getCookie("WD_i") ? M.getCookie("WD_i") + "|" + i: i), M._confirm("已添加购物车", ["再逛逛", ""], ["去结算", ""],
                    function() {
                        location.href = "/" + M.cartUrl() + "?umk=" + Item.sellerID
                    }))