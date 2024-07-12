var app = angular.module("lstt",[])
app.controller("lstt-ctrl", function ($scope, $http) {

    $scope.lichSu = []


    $scope.getAllLichSu = function getRecentActivities() {
        $http.get("/admin/lich-su-thao-tac/getAll").then(r => {
            $scope.lichSu = r.data
          console.log( r.data)
        }).catch(e => console.log(e))

    }
    $scope.getAllLichSu()
// Gọi hàm để lấy hoạt động gần đây của người dùng 'user1'

})