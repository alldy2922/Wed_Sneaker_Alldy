var app = angular.module("mau-sac",[])
app.controller("mau-sac-ctrl", function($scope, $http){

    $scope.form = {}
    $scope.items = []

    $scope.findAll = function () {
        $http.get("/admin/mau-sac/find-all").then(resp => {
            console.log(resp.data)
            $scope.items = resp.data;
        }).catch(error =>{
            console.log(error)
        })
    }
    $scope.findAll();

    $scope.getMauSacs = function (ma){
        var url = "/admin/mau-sac/chiTiet" +"/"+ ma;
        console.log(url)
        $http.get(url).then(function (r){
            console.log(r.data)
            let mauSac = r.data;
            $scope.ma = mauSac.ma;
            $scope.ten = mauSac.ten;
            $scope.ngayTao = mauSac.ngayTao;
            $scope.ngayCapNhat = mauSac.ngayCapNhat;
        })
    }
    $scope.delete = function (ma){
       var url = "/admin/mau-sac/delete" +"/"+ ma;
       $http.delete(url).then(function (r){
           alert("Dlete thành công!!!")
           location.reload();
       })
    }

    $scope.create = function (){
        var mauSac = {
            ten: $scope.ten,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat
        }
        if($scope.ten == undefined || $scope.ten.length==0) {
            document.getElementById("eTenMau").innerText = "Vui lòng nhập tên!!!";
            return
        }
        if($scope.ten.length>100) {
            document.getElementById("eTenMau").innerText = "Tên tối đa 100 ký tự!!!";
            return
        }
        else {
            $http.post("/admin/mau-sac/add", mauSac).then(r => {
                location.reload();
                alert("Thêm thành công")
            })
        }
    }

    $scope.update = function (ma) {
        if($scope.ten == undefined || $scope.ten.length==0) {
            document.getElementById("eTenMauUd").innerText = "Vui lòng nhập tên!!!";
            return
        }
        if($scope.ten.length>100) {
            document.getElementById("eTenMauUd").innerText = "Tên tối đa 100 ký tự!!!";
            return
        }
        var url = "/admin/mau-sac/update" +"/"+ ma;
        var updateMau = {
            ma: $scope.ma,
            ten: $scope.ten,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat
        }

            $http.put(url, updateMau).then(function (r) {
                location.reload();
                alert("Update thành công")
            })
    }

})