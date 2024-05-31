var app = angular.module("dong-san-pham",[])
app.controller("dong-san-pham-ctrl",function ($scope, $http){

    $scope.form = {}
    $scope.items = []

    $scope.findAll = function () {
        $http.get("/admin/dong-san-pham/find-all").then(resp => {
            console.log(resp.data)
            $scope.items = resp.data;
        }).catch(error =>{
            console.log(error)
        })
    }
    $scope.findAll();

    $scope.getDongSanPham = function (id){
        var url = "/admin/dong-san-pham/chiTiet" +"/"+ id;
        console.log(url)
        $http.get(url).then(function (r){
            console.log(r.data)
            let dong = r.data;
            $scope.id = dong.id;
            $scope.ten = dong.ten;
            $scope.thuongHieu = dong.idThuongHieu;
            $scope.ngayTao = dong.ngayTao;
            $scope.ngayCapNhat = dong.ngayCapNhat;
        })
    }

    $scope.delete = function (id){
        var url = "/admin/dong-san-pham/delete" +"/"+ id;
        $http.delete(url).then(function (r){
            alert("Xóa thành công!!!")
            location.reload();
        })
    }

    $scope.create = function (){
        var mauSac = {
            ten: $scope.ten,
            thuongHieu: $scope.thuongHieu,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat
        }
        if($scope.thuongHieu == undefined){
            document.getElementById('erAddDongSP').innerText = "Vui lòng chọn thương hiệu"
            return;
        }
        if($scope.ten == undefined || $scope.ten.length==0) {
            document.getElementById("eTenDong").innerText = "Vui lòng nhập tên!!!";
            return
        }
        if($scope.ten.length>100){
            document.getElementById("eTenDong").innerText = "Tên tối đa 100 ký tự!!!";
            return
        }
            else{
        $http.post("/admin/dong-san-pham/add",mauSac).then(r => {
            location.reload();
            alert("Thêm thành công")
        })
            }
    }

    $scope.update = function (id) {
        if($scope.ten == undefined || $scope.ten.length==0) {
            document.getElementById("eTenDongUd").innerText = "Vui lòng nhập tên!!!";
            return
        }
        if($scope.ten.length>100){
            document.getElementById("eTenDongUd").innerText = "Tên tối đa 100 ký tự!!!";
            return
        }
        var url = "/admin/dong-san-pham/update" +"/"+ id;
        var updateDong = {
            id : id,
            ten: $scope.ten,
            thuongHieu: $scope.thuongHieu,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat
        }
        $http.put(url,updateDong).then(function (r){
            location.reload();
            alert("Cập nhật thành công")
        })
    }

    $scope.getThuongHieu = function (){
        $http.get("/admin/thuong-hieu/find-all").then(r => {
            console.log(r.data)
            $scope.thuongHieus = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getThuongHieu();
})