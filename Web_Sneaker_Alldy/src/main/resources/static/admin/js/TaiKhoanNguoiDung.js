var app = angular.module('nguoidung-admin', []);
app.controller('ctrl', function ($scope, $http) {

    $scope.khachHang = [];
    $scope.khachHangDetail = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    $scope.keyWord = ""


    $scope.getAll = function (page){
        $scope.pageNumber = page
        $http.get("/admin/khach-hang/get-all-khach-hang?page="+page+"&keyWord="+$scope.keyWord).then(r =>{
            $scope.khachHang = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
        }).catch(e => console.log(e))
    }
    $scope.getAll(0)
    $scope.getPageNumbers = function (totalPages){
        $scope.pageNumbers = [];
        for (let i = 0; i< totalPages;i++){
            $scope.pageNumbers.push(i);
        }
    }

    $scope.detail = function (id){
        $http.get("/admin/khach-hang/detail/"+id).then(r => {
            $scope.khachHangDetail = r.data;
            console.log($scope.khachHangDetail)
        }).catch(e => console.log(e))
    }

    $scope.delete = function (id){
        alertify.confirm("Bạn có chắc chắn muốn xóa khách hàng này?" , function () {
            $http.delete("/admin/khach-hang/"+id).then(r => {
                alertify.success("Xóa khách hàng thành công")
                if($scope.khachHang.length==1){
                    if($scope.pageNumber>0) $scope.pageNumber--;
                }
                $scope.getAll($scope.pageNumber);
            }).catch(e =>{
                console.log(e);
                alertify.error("Xóa khách hàng thất bại")
            })
        }, function () {
        })
    }
})