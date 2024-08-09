var app = angular.module("ctdhtra-app", [])
app.controller("ctdhtra-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    const pathName = location.pathname;
    const maDH = pathName.substring(pathName.lastIndexOf("/"))
    $scope.cart = [];
    $scope.sanPhamTra = []
    $scope.selectedProducts = [];


// lưu bằng local
    // $scope.selectedProducts = []; // Khởi tạo danh sách sản phẩm đã chọn

    // // Hàm để chọn sản phẩm và lưu vào localStorage
    // $scope.selectProduct = function (product) {
    //     const existingProduct = $scope.selectedProducts.find(p => p.id === product.id);

    //     if (existingProduct) {
    //         // Nếu sản phẩm đã tồn tại, chỉ cập nhật số lượng
    //         existingProduct.selectedQuantity += product.soLuong;
    //         existingProduct.totalPrice = existingProduct.donGiaSauGiam * existingProduct.selectedQuantity;
    //     } else {
    //         // Nếu sản phẩm chưa tồn tại, thêm mới vào danh sách
    //         $scope.selectedProducts.push({
    //             ...product,
    //             selectedQuantity: product.soLuong,
    //             totalPrice: product.donGiaSauGiam * product.soLuong
    //         });
    //     }

    //     // Lưu danh sách sản phẩm đã chọn vào localStorage
    //     localStorage.setItem('selectedProducts', JSON.stringify($scope.selectedProducts));
    //     console.log("Sản phẩm đã chọn sau khi thêm:", $scope.selectedProducts);
    // };

    // $scope.changeQuantity = function (product, change) {
    //     if (!product.selectedQuantity) {
    //         product.selectedQuantity = 1;
    //     }

    //     var newQuantity = product.selectedQuantity + change;

    //     if (newQuantity < 1) {
    //         newQuantity = 1;
    //     } else if (newQuantity > product.soLuong) {
    //         newQuantity = product.soLuong;
    //         alertify.error("Số lượng không được vượt quá số lượng hiện có");
    //     }

    //     product.selectedQuantity = newQuantity;
    //     product.totalPrice = product.selectedQuantity * product.donGiaSauGiam; // Cập nhật tổng tiền

    //     // Cập nhật danh sách sản phẩm đã chọn
    //     var index = $scope.selectedProducts.findIndex(p => p.id === product.id);
    //     if (index > -1) {
    //         $scope.selectedProducts[index] = product;
    //     } else {
    //         $scope.selectedProducts.push(product);
    //     }

    //     // Cập nhật tổng tiền hoàn
    //     $scope.getTotalRefund();
    // };

    // $scope.getTotalRefund = function() {
    //     let totalRefund = 0;
    //     $scope.selectedProducts.forEach(product => {
    //         totalRefund += product.totalPrice;
    //     });
    //     return totalRefund;
    // };
    
    // $scope.getTotalPrice = function () {
    //     let total = 0;
    //     $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
    //     return total
    // }

    // // Giả sử có một nút hoặc một sự kiện để lưu lại danh sách sản phẩm đã chọn
    // $scope.saveSelectedProducts = function () {
    //     localStorage.setItem('selectedProducts', JSON.stringify($scope.selectedProducts));
    // };

    // // Tải danh sách sản phẩm đã chọn từ localStorage
    // $scope.loadSelectedProducts = function () {
    //     const storedProducts = localStorage.getItem('selectedProducts');
    //     if (storedProducts) {
    //         $scope.selectedProducts = JSON.parse(storedProducts);

    //         // Tính toán lại totalPrice cho mỗi sản phẩm
    //         $scope.selectedProducts.forEach(product => {
    //             product.totalPrice = product.donGiaSauGiam * product.selectedQuantity;
    //         });

    //         console.log("Danh sách sản phẩm đã chọn sau khi tải:", $scope.selectedProducts);
    //     } else {
    //         console.log("Không có sản phẩm nào được chọn.");
    //         $scope.selectedProducts = [];
    //     }
    // };

    // // Gọi hàm loadSelectedProducts() khi khởi tạo controller
    // $scope.loadSelectedProducts();

    $http.get("/cart/check-login")
        .then(function (response) {
            if (response.data) {
                // User is logged in, fetch the cart data from the database
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
                // User is not logged in, fetch the cart data from the session
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

    console.log("ma", maDH)
    $http.get("/don-hang" + maDH).then(r => {
        $scope.donHang = r.data
        console.log("$donHang", $scope.donHang = r.data)

    }).catch(e => console.log(e));
    //hiện chi tiết đơn trả
    $http.get("/don-hang/tra" + maDH).then(r => {
        $scope.donHangTra = r.data
        console.log("$donHangTra", $scope.donHangTRa = r.data)
        console.log("$MaHangTra", maDH)
    }).catch(e => console.log(e));
    //
    $http.get("/chi-tiet-don-hang" + maDH).then(r => {
        $scope.chiTietDonHang = r.data;
    }).catch(e => console.log(e))
    //hiện chi tiết sản phẩm trả
    $http.get("/don-hang/get-ctsp-tra" + maDH).then(r => {
        $scope.sanPhamTra = r.data;
        console.log("spt", $scope.sanPhamTra)
    }).catch(e => console.log(e))

    $scope.getTotalPrice = function () {
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
        return total
    }

    // Hàm tính tổng tiền cho các sản phẩm trả
    $scope.getTotalPriceSanPhamTra = function () {
        let total = 0;
        $scope.sanPhamTra.forEach(
            (d) => (total += d.donGiaSauGiam * d.soLuong)
        );
        return total;
    };
})