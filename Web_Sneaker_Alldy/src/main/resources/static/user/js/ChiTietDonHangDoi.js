var app = angular.module("ctdhdoi-app", [])
app.controller("ctdhdoi-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    const pathName = location.pathname;
    const maDH = pathName.substring(pathName.lastIndexOf("/"))
    $scope.cart = [];
    $scope.sanPhamDoi = []
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

    //hiện chi tiết đơn đổi
    $http.get("/don-hang/doi" + maDH).then(r => {
        $scope.donHangDoijs = r.data
        console.log("$donHangDoi", $scope.donHangDoi = r.data)
        console.log("trangThai", $scope.donHangDoi.trangThai);
        console.log("$MaHangDoi", maDH)
    }).catch(e => console.log(e));
    //
    $http.get("/chi-tiet-don-hang" + maDH).then(r => {
        $scope.chiTietDonHang = r.data;
    }).catch(e => console.log(e))
    //hiện chi tiết sản phẩm trả
    $http.get("/don-hang/get-ctsp-doi" + maDH).then(r => {
        $scope.sanPhamDoi = r.data;
        console.log("spt",$scope.sanPhamDoi)
    }).catch(e => console.log(e))
})