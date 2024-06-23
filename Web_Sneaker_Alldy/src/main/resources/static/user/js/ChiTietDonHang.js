var app = angular.module("ctdh-app", [])
app.controller("ctdh-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.chiTietDonHang = []
    $scope.cart =[];
    const pathName = location.pathname;
    const maDH = pathName.substring(pathName.lastIndexOf("/"))
    $scope.cart = [];

    $http.get("/don-hang" + maDH).then(r => {
        $scope.donHang = r.data
        console.log($scope.donHang = r.data)
    }).catch(e => console.log(e));
    $http.get("/chi-tiet-don-hang" + maDH).then(r => {
        $scope.chiTietDonHang = r.data;
    }).catch(e => console.log(e))

    $scope.getTotalPrice = function () {
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
        return total
    }

                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });

})