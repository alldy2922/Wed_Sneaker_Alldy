var app = angular.module("lstt-app",[])
app.controller("lstt-ctrl", function ($scope, $http) {

    $scope.lichSu = []
    $scope.form ={};
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    var isfilter = false;

    $scope.getAllLichSu = function getRecentActivities() {
        $http.get("/admin/lich-su-thao-tac/getAll").then(r => {
            $scope.lichSu = r.data.content
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
            console.log( r.data)
        }).catch(e => console.log(e))

    }
    $scope.getAllLichSu()
// Gọi hàm để lấy hoạt động gần đây của người dùng 'user1'

    $scope.clearFilter = function (){

        $scope.pageNumber = 0
        $scope.pageNumbers = []
        $http.get("/admin/lich-su-thao-tac/getAll").then(r => {
            document.getElementById('lengthFilter').innerText = ""
            $scope.lichSu = r.data
            $scope.getPageNumbers(r.data.totalPages)
            $scope.filterDto = {}
            isfilter = false;
        }).catch(e => console.log(e))
    }
    $scope.getAll = function (pageNumber){
        $scope.pageNumber = pageNumber;


        $http.get("/admin/lich-su-thao-tac/getAll?pageNumber="+pageNumber).then(r => {
            $scope.lichSu = r.data.content
            // $scope.filterData = {}
        }).catch(e => console.log(e))

    }

    $scope.getPageNumbers = function (totalPages){
        $scope.pageNumbers = []
        for (let i = 0; i< totalPages;i++){
            $scope.pageNumbers.push(i);
        }
    }

    $scope.filteredData = [];

    $scope.applyFilterByName = function() {
        var url = '/admin/lich-su-thao-tac/locName';
        $http.get(url, { params: { name: $scope.filterData.ten } })
            .then(function(response) {
                $scope.lichSu = response.data;
            }, function(error) {
                console.error('Error occurred:', error);
            });
    };

    $scope.applyFilterByDate = function() {
        var url = '/admin/lich-su-thao-tac/locDate';
        $http.get(url, {
            params: {
                ngayBatDau: $scope.filterData.ngayBatDau,
                ngayKetThuc: $scope.filterData.ngayKetThuc
            }
        })
            .then(function(response) {
                $scope.lichSu = response.data;
            }, function(error) {
                console.error('Error occurred:', error);
            });
    };
    $scope.filter = {};

    $scope.filterData = function() {
        $http.get('/admin/lich-su-thao-tac/filter', {
            params: {
                ten: $scope.filter.ten || '',
                ngayBatDau: $scope.filter.ngayBatDau || '',
                ngayKetThuc: $scope.filter.ngayKetThuc || ''
            }
        }).then(function(response) {
            $scope.lichSu = response.data.content;
            console.log($scope.lichSu)
        }, function(error) {
            console.error('Error occurred:', error);
        });
    };
})