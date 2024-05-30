var app = angular.module("kieudang",[])
app.controller("kieudang-ctrl", function ($scope, $http) {
    const url = "http://localhost:8080/admin/kieu-dang"
    var getUrlWithId = function (id){
        return url + "/"+ id;
    }
    //Chi tiet
    $scope.findById = function (id) {
        var urlWithId = getUrlWithId(id);
        $http.get(urlWithId).then(function (res) {
            const kieudang = res.data;
            $scope.id = kieudang.id;
            $scope.ten = kieudang.ten;
            $scope.ngayTao = kieudang.ngayTao;
            $scope.ngayCapNhat = kieudang.ngayCapNhat;
        });
    }
    $scope.itemss = [];
    $scope.form = {

    };

    $scope.getAll = function () {
        $http.get("/admin/kieu-dang/find-all").then(r => {
            console.log(r.data)
            $scope.itemss = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getAll()
// xóa
    $scope.delete = function (id) {
        if(confirm("Bạn muốn xóa Kiểu Dáng này?")){
        var urlWithId = getUrlWithId(id)
        $http.delete(urlWithId).then(function (res) {
            location.reload();
            alertify.success("Xóa Kiểu Dáng thành công")
        }).catch(error =>{
            alertify.error("Xóa Kiểu Dáng thất bại")
            console.log("error", error);
        })
    }
    }

    //add
    $scope.erTen = ''
    $scope.create = function () {
        var kieuDang = {
                ten: $scope.ten
            }
            $http.post(url, kieuDang).then(function (response) {
                // location.reload();
                $scope.ten = ''
                $scope.itemss.push(response.data)
                alertify.success("Thêm Kiểu Dáng thành công")
            }).catch(e =>{
                console.log(e)
                alertify.error("Thêm Kiểu dáng thất bại")
                $scope.erTen = e.data.ten == undefined ? "" : e.data.ten;
            });

    };
    //update
    $scope.erTenUd = ''
    $scope.update = function (id) {
        var urlWithId = getUrlWithId(id)
        var kieudang = {
            ten: $scope.ten,
        }
        $http.put(urlWithId, kieudang).then(function (resp) {
            location.reload();
            alert("Cập nhật thành công");
        }).catch(e =>{
            $scope.erTenUd = e.data.ten == undefined ? "" : e.data.ten
        });
    };

})