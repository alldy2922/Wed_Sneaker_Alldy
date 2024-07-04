var app = angular.module("cart-app", [])
app.controller("cart-ctrl", function ($scope, $http) {
    $scope.cart = [];
    $scope.vouchers = [];
    $scope.user = [];
    $scope.cartUser = [];
    $scope.productDetails ={};
    $scope.datacheck = [];
    $scope.sanPhamId = [];
    $scope.selectedProducts = [];

        // Check if the user is logged in
    $http.get("/cart/check-login")
        .then(function(response) {
            if (response.data) {
                // User is logged in, fetch the cart data from the database
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cartUser = r.data;
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
    $scope.checkboxChanged = function() {
        if(   $scope.selectedProducts.length==0){
            alertify.error("Vui long chon san pham can mua")
        }else {
            location.href = "/thanh-toan";
        }
        // Thực hiện xử lý dựa trên giá trị của checkbox
    };
    $scope.checkThanhToan = function () {
        $http.get("/cart/find-all-sp")
            .then(function(r) {
                console.log(r.data);
                $scope.cart = r.data;
                console.log("soLuong:", $scope.cart);
            })
            .catch(function(e) {
                console.log(e);
            });

    }

    $scope.updateSl = function (id, soLuong) {
        if (soLuong <= 0) {
            alertify.error("Số lượng phải là số nguyên > 0!!!")
            return
        }
        if (!parseInt(soLuong)) {
            alertify.error("Số lượng phải là số nguyên > 0!!")
            return
        }
        $http.put("/cart/update-sl/" + id + "/" + soLuong).then(function (r){
            console.log(r.data)
            $scope.cart = r.data;
        }).catch(e=>{
            $scope.cart.forEach(c =>{
                if(c.id == id){
                    document.getElementById(c.id).value =  c.soLuong
                }
            })
            alertify.error(e.data.sl)
            console.log(e)
        })
    }
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
    $scope.selectedProducts = JSON.parse(localStorage.getItem('selectedProducts')) || [];
    $scope.checkBox = false;
    $scope.toggleSelection = function(product) {
        var index = $scope.selectedProducts.findIndex(p => p.id === product.id);
        if (index > -1) {
            $scope.selectedProducts.splice(index, 1); // Remove product if already selected
        } else {
            $scope.selectedProducts.push(product); // Add product if not selected
        }

        // Remove duplicates by converting the array to a Set and back to an array
        $scope.selectedProducts = Array.from(new Set($scope.selectedProducts.map(p => p.id)))
            .map(id => $scope.selectedProducts.find(p => p.id === id));

        localStorage.setItem('selectedProducts', JSON.stringify($scope.selectedProducts));
        localStorage.setItem('selectedUser', JSON.stringify($scope.selectedProducts));
        $scope.cartUser = $scope.selectedProducts// Store the selected products in localStorage
        // Display the selected products in the console
    };
    $scope.removeAllProductIncart = function () {
        alertify.confirm("Xóa hết giỏ hàng? ", function () {
            $http.delete("/cart/removeAll").then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                console.log(response.data())
            })
        }, function () {})
    }
    // Function to checkout selected products

    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.sanPhamId.length; i++) {
            totalPrice += $scope.sanPhamId[i].soLuong * $scope.sanPhamId[i].donGiaSauGiam
        }
        return totalPrice;
    }

    $scope.getSanPhamId = function (id) {
        $http.get("/cart/find-sp?id="+ id).then(resp => {
            console.log(resp.data)
            $scope.sanPhamId = resp.data;
            console.log("data snapham í",$scope.sanPhamId)
            localStorage.setItem('myData', JSON.stringify(resp.data));
        }).catch(error => {
            console.log(error)
        })

    }

    $scope.getSanPhamIdNologin = function (id) {
        $http.get("/cart/find-sp-id?id="+ id).then(resp => {
            console.log(resp.data)
            $scope.sanPhamId = resp.data;
            localStorage.setItem('myData', JSON.stringify(resp.data));
        }).catch(error => {
            console.log(error)
        })

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



