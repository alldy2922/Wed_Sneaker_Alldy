var app = angular.module("ctdh-app", [])
app.controller("ctdh-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    const pathName = location.pathname;
    const maDH = pathName.substring(pathName.lastIndexOf("/"))
    $scope.cart = [];
    $scope.sanPhamTra = []
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

    console.log("ma",maDH)
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
        console.log("spt",$scope.sanPhamTra)
    }).catch(e => console.log(e))

    $scope.getTotalPrice = function () {
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
        return total
    }
})