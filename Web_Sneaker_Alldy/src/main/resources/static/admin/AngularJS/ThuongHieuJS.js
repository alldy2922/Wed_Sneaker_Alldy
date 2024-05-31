var app = angular.module("thuonghieu",[])
app.controller("thuonghieu-ctrl", function ($scope, $http) {
    const url = "http://localhost:8080/admin/thuong-hieu"
    var getUrlWithId = function (id) {
        return url + "/" + id;
    }
    $scope.itemss = [];
    $scope.form = {};
    // get all
    $scope.getAll = function () {
        $http.get("/admin/thuong-hieu/find-all").then(r => {
            console.log(r.data)
            $scope.itemss = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getAll()
    //delete
    $scope.delete = function (id) {
        if(confirm("Bạn muốn xóa sản phẩm này?")) {
            var urlWithId = getUrlWithId(id)
            $http.delete(urlWithId).then(function (res) {
                location.reload();
                // $scope.itemss.deleteData(res.data)
                alert("Xóa Thành Công !");
            }).catch(error =>{
                alert("Lỗi Xóa Sản Phẩm !")
                console.log("error", error);
            })
        }
    }
    // chi tiết
    $scope.findById = function (id){
        var urlWithId = getUrlWithId(id);
        $http.get(urlWithId).then(function (res){
            const thuonghieu = res.data;
            $scope.id = thuonghieu.id;
            $scope.ten = thuonghieu.ten;
            $scope.ngayTao = thuonghieu.ngayTao;
            $scope.ngayCapNhat = thuonghieu.ngayCapNhat;
        });
    }
    // add
    $scope.create = function (){

        console.log("add")
        if($scope.ten == undefined ||$scope.ten.length ==0){
            document.getElementById('erTen').innerText = "Vui lòng nhập tên thương hiệu"
            return;
        }
        if($scope.ten.length > 100){
            document.getElementById('erTen').innerText = "Tên thương hiệu tối đa 100 ký tự"
            return;
        }
        var thuonghieu = {
            ten:$scope.ten
        }
        $http.post(url, thuonghieu).then(function (response){
            // location.reload();
            $scope.ten = ""
            $scope.itemss.push(response.data)
            alert("Thêm Thành Công !");
        }).catch(error =>{
            $scope.erTen = error.data.ten
        });
    }
    //update
    $scope.update = function (id){
        if($scope.ten == undefined || $scope.ten.length ==0){
            document.getElementById('erTenUd').innerText = "Vui lòng nhập tên thương hiệu"
            return;
        }
        if($scope.ten.length > 100){
            document.getElementById('erTenUd').innerText = "Tên thương hiệu tối đa 100 ký tự"
            return;
        }
        var urlWithId = getUrlWithId(id)
        var thuonghieu = {
            ten: $scope.ten
        }
        $http.put(urlWithId, thuonghieu).then(function (resp){
            location.reload();
            // $scope.itemss.merge(r, data)
            alert("Cập Nhật Thành Công")
        })
    }
})
