var app = angular.module("lstt-app",[])
app.controller("lstt-ctrl", function ($scope, $http) {

    $scope.lichSu = []
    $scope.form ={};
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    var isfilter = false;

    // Danh sách số trang
    $scope.currentPage = 0; // Trang hiện tại
    $scope.totalPages = 0; // Tổng số trang
    $scope.pages = []; // Danh sách số trang

    // Hàm lấy dữ liệu

    $scope.lichSuThaoTac = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        init() {
            $http.get("/admin/lich-su-thao-tac/getAll").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers();
                this.getList(0); // Gọi hàm để lấy dữ liệu trang đầu tiên
            }).catch(e => console.log(e));
        },
        getList(pageNumber) {
            this.page = pageNumber;
            $http.get("/admin/lich-su-thao-tac/getAll?pageNumber=" + pageNumber).then(r => {
                $scope.lichSu = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers();
            }).catch(e => console.log(e));
        },
        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >= 2 ? this.page - 2 : 0

            if (lengthLast > this.totalPage) {
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if (lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    };

    // Khởi tạo khi trang được tải
    $scope.lichSuThaoTac.init();
    $scope.lichSuThaoTac.getList(0);

    // Khởi tạo trang đầu tiên

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