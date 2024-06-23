var app = angular.module("donhangUser-app", [])
app.controller("donhangUser-ctrl", function ($scope, $http) {
    $scope.cart =[];
    $http.get("/cart/check-login")
        .then(function(response) {
            if (response.data) {
                // User is logged in, fetch the cart data
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong: chi tiet sp", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            } else {
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
        })

})