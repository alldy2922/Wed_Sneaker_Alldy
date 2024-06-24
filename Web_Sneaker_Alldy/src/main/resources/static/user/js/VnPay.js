var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http) {
    $scope.orderTotal = 100; // Số tiền của đơn hàng
    $scope.orderInfo = 'Order123'; // Thông tin đơn hàng

    $scope.submitOrder = function() {
        var data = {
            amount: $scope.orderTotal,
            orderInfo: $scope.orderInfo
        };
        console.log(data)
        $http.post('/vnPay/submitOrder', data).then(function(response) {
            window.location.href = response.data;
        }, function(error) {
            console.log('Lỗi: ' + error);
        });
    };
});