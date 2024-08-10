var app = angular.module("cart-app", []);
app.controller("cart-ctrl", function ($scope, $http) {
    $scope.cart = [];
    $scope.vouchers = [];
    $scope.user = [];
    $scope.cartUser = [];
    $scope.productDetails = {};
    $scope.datacheck = [];
    $scope.sanPhamId = [];
    $scope.selectedProducts = JSON.parse(localStorage.getItem('selectedProducts')) || [];
    $scope.totalSelected = 0;

    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    $http.get("/cart/check-login")
        .then(function (response) {
            if (response.data) {
                // Người dùng đã đăng nhập, lấy dữ liệu giỏ hàng từ cơ sở dữ liệu
                $http.get("/cart/find-all-sp")
                    .then(function (r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong:", $scope.cart);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            } else {
                // Người dùng chưa đăng nhập, lấy dữ liệu giỏ hàng từ session
                $http.get("/cart/find-all")
                    .then(function (r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong:", $scope.cart);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            }
        })
        .catch(function (error) {
            console.log('Error checking login status:', error);
        });

    $scope.checkboxChanged = function () {
        console.log('Checkbox value:', $scope.checkboxValue);
        // Thực hiện xử lý dựa trên giá trị của checkbox
    };

    $scope.checkThanhToan = function () {
        if(response.data){
            $http.get("/cart/find-all-sp")
            .then(function (r) {
                console.log(r.data);
                $scope.cart = r.data;
                console.log("soLuong:", $scope.cart);
            })
            .catch(function (e) {
                console.log(e);
            });
        } else{
            $http.get("/cart/find-all")
            .then(function (r) {
                console.log(r.data);
                $scope.cart = r.data;
                console.log("soLuong:", $scope.cart);
            })
            .catch(function (e) {
                console.log(e);
            });
        }
    };

    $scope.updateTotal = function () {
        $scope.totalSelected = 0;
        angular.forEach($scope.cart, function (item) {
            if (item.selected) {
                $scope.totalSelected += item.soLuong * item.donGiaSauGiam;
            }
        });
    };

    $scope.updateSl = function (id, soLuong) {
        if (soLuong <= 0 || !Number.isInteger(Number(soLuong))) {
            alertify.error("Số lượng phải là số nguyên > 0!!");
            return;
        }
        // Lưu lại trạng thái của checkbox trước khi cập nhật
        var checkboxState = {};
        $scope.cart.forEach(function (item) {
            checkboxState[item.id] = item.selected;
        });

        $http.put("/cart/update-sl/" + id + "/" + soLuong).then(function (r) {
            console.log(r.data);
            $scope.cart = r.data;

            // Khôi phục lại trạng thái của checkbox sau khi cập nhật thành công
            $scope.cart.forEach(function (item) {
                item.selected = checkboxState[item.id] || false;
            });

            // Cập nhật tổng số tiền đã chọn
            $scope.updateTotal();
            $scope.updateSelectedProductQuantity(id, soLuong); // Cập nhật số lượng sản phẩm đã chọn

        }).catch(function (e) {
            $scope.cart.forEach(function (c) {
                if (c.id == id) {
                    document.getElementById(c.id).value = c.soLuong;
                }
            });
            alertify.error(e.data.sl);
            console.log(e);
        });
    };

    $scope.removeProductIncart = function (idCTSP) {
        alertify.confirm("Xóa sản phẩm khỏi giỏ hàng? ", function () {
            $http.delete("/cart/remove/" + idCTSP).then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                $scope.getTotal();
            })
        }, function () { });
    };

    $scope.toggleSelection = function (product) {
        var index = $scope.selectedProducts.findIndex(p => p.id === product.id);
        if (index > -1) {
            $scope.selectedProducts.splice(index, 1); // Xóa sản phẩm nếu đã được chọn
        } else {
            $scope.selectedProducts.push(product); // Thêm sản phẩm nếu chưa được chọn
        }

        // Xóa các bản sao bằng cách chuyển mảng thành Set rồi lại chuyển thành mảng
        $scope.selectedProducts = Array.from(new Set($scope.selectedProducts.map(p => p.id)))
            .map(id => $scope.selectedProducts.find(p => p.id === id));

        localStorage.setItem('selectedProducts', JSON.stringify($scope.selectedProducts)); // Lưu sản phẩm đã chọn vào localStorage
        console.log($scope.selectedProducts);
        console.log(product.id); // Hiển thị sản phẩm đã chọn trong console
    };

    $scope.updateSelectedProductQuantity = function (id, soLuong) {
        var product = $scope.selectedProducts.find(p => p.id === id);
        if (product) {
            product.soLuong = soLuong;
            localStorage.setItem('selectedProducts', JSON.stringify($scope.selectedProducts));
        }
    };

    $scope.removeAllProductIncart = function () {
        alertify.confirm("Xóa hết giỏ hàng? ", function () {
            $http.delete("/cart/removeAll").then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                console.log(response.data());
            });
        }, function () { });
    };

    // Function to checkout selected products
    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.sanPhamId.length; i++) {
            totalPrice += $scope.sanPhamId[i].soLuong * $scope.sanPhamId[i].donGiaSauGiam;
        }
        return totalPrice;
    };

    $scope.getSanPhamId = function (id) {
        $http.get("/cart/find-sp?id=" + id).then(resp => {
            console.log(resp.data);
            $scope.sanPhamId = resp.data;
            console.log("data san pham id", $scope.sanPhamId);
            localStorage.setItem('myData', JSON.stringify(resp.data));
        }).catch(error => {
            console.log(error);
        });
    };

    $scope.getSanPhamIdNologin = function (id) {
        $http.get("/cart/find-sp-id?id=" + id).then(resp => {
            console.log(resp.data);
            $scope.sanPhamId = resp.data;
            localStorage.setItem('myData', JSON.stringify(resp.data));
        }).catch(error => {
            console.log(error);
        });
    };

    // Hiển thị voucher
    $http.get("/check-out/voucher").then(resp => {
        console.log(resp.data);
        $scope.vouchers = resp.data;
    }).catch(error => {
        console.log(error);
    });

    $scope.showDetails = function (index) {
        $scope.selectedVoucher = $scope.vouchers[index];
    };

    $scope.hideDetails = function () {
        $scope.selectedVoucher = null;
    };

    // Kiểm tra xem có sản phẩm nào được chọn hay không
    $scope.hasSelectedProducts = function () {
        return $scope.selectedProducts.length > 0;
    };

    // Chuyển đến trang thanh toán khi có sản phẩm được chọn
    $scope.goToCheckout = function () {
        if ($scope.hasSelectedProducts()) {
            window.location.href = '/thanh-toan';
        } else {
            alert('Vui lòng chọn ít nhất một sản phẩm để thanh toán.');
        }
    };
});
