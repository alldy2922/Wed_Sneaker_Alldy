var app = angular.module("cart-app", [])
app.controller("cart-ctrl", function ($scope, $http) {
    $scope.cart = [];
    $scope.vouchers = [];
    $http.get("/cart/check-login")
        .then(function(response) {
            if (response.data) {
                // User is logged in, fetch the cart data from the database
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            } else {
                // User is not logged in, fetch the cart data from the session
                $http.get("/cart/find-all")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            }
        })
        .catch(function(error) {
            console.log('Error checking login status:', error);
        });

    //thực hiện thanh toán
    $scope.paySelected = function() {
        // Lọc ra các sản phẩm đã chọn
        var selectedItems = $scope.cart.filter(function(item) {
            return item.selected;
        });
    
        // Thực hiện các thao tác cần thiết trước khi chuyển hướng
        console.log('Các sản phẩm đã chọn:', selectedItems);
    
        // Ví dụ: chuyển hướng đến trang thanh toán
        window.location.href = '/thanh-toan';
    };
    //chỉnh số lượng size
    $scope.updateTotal = function() {
        $scope.totalSelected = 0;
        angular.forEach($scope.cart, function(item) {
            if (item.selected) {
                $scope.totalSelected += item.soLuong * item.donGiaSauGiam;
            }
        });
    };    
    $scope.updateSl = function(id, soLuong) {
        if (soLuong <= 0) {
            alertify.error("Số lượng phải là số nguyên > 0!!!");
            return;
        }
        if (!parseInt(soLuong)) {
            alertify.error("Số lượng phải là số nguyên > 0!!!");
            return;
        }
        
        // Lưu trữ trạng thái selected của các sản phẩm đã chọn trước đó
        var selectedItems = $scope.cart.filter(function(item) {
            return item.selected;
        });
        
        $http.put("/cart/update-sl/" + id + "/" + soLuong)
            .then(function(response) {
                $scope.cart = response.data;
                
                // Sau khi cập nhật số lượng thành công, phục hồi lại trạng thái selected của các sản phẩm đã chọn
                angular.forEach(selectedItems, function(item) {
                    angular.forEach($scope.cart, function(cartItem) {
                        if (cartItem.id === item.id) {
                            cartItem.selected = true;
                        }
                    });
                });
                
                $scope.updateTotal(); // Cập nhật tổng tiền sau khi thay đổi số lượng
            })
            .catch(function(error) {
                console.log('Lỗi khi cập nhật số lượng:', error);
            });
    };
    
    //

    // $scope.updateSl = function (id, soLuong) {
    //     if (soLuong <= 0) {
    //         alertify.error("Số lượng phải là số nguyên > 0!!!")
    //         return
    //     }
    //     if (!parseInt(soLuong)) {
    //         alertify.error("Số lượng phải là số nguyên > 0!!")
    //         return
    //     }
    //     $http.put("/cart/update-sl/" + id + "/" + soLuong).then(function (r){
    //         console.log(r.data)
    //         $scope.cart = r.data;
    //     }).catch(e=>{
    //         $scope.cart.forEach(c =>{
    //             if(c.id == id){
    //                 document.getElementById(c.id).value =  c.soLuong
    //             }
    //         })
    //         alertify.error(e.data.sl)
    //         console.log(e)
    //     })
    // }

    $scope.removeProductIncart = function (idCTSP) {
        alertify.confirm("Xóa sản phẩm khỏi giỏ hàng? ", function () {
            $http.delete("/cart/remove/" + idCTSP).then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                $scope.getTotal();
                // let index = $scope.cart.findIndex(c => c.id == idCTSP);
                // $scope.cart.slice(index,1)
            })
        }, function () {})
    }
    $scope.removeAllProductIncart = function () {
        alertify.confirm("Xóa hết giỏ hàng? ", function () {
            $http.delete("/cart/removeAll").then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                console.log(response.data())
            })
        }, function () {})
    }
    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
        return totalPrice;
    }

//    show voucher
    $http.get("/check-out/voucher").then(resp => {
        console.log(resp.data)
        $scope.vouchers = resp.data;
    }).catch(error => {
        console.log(error)
    })

    $scope.showDetails = function (index) {
        $scope.selectedVoucher = $scope.vouchers[index];
    };

    $scope.hideDetails = function () {
        $scope.selectedVoucher = null;
    };

})




