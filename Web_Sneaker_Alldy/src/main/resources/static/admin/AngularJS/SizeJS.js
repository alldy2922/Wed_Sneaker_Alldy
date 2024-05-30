var app = angular.module("size", [])
app.controller("size-ctrl" ,function ($scope, $http){
    $scope.form = {}
    $scope.items = []
    $scope.findAll = function (){
        $http.get("/admin/size/find-all").then(resp => {
            console.log(resp.data)
            $scope.items = resp.data;
        }).catch(error =>{
            console.log(error)
        })
    }
        $scope.findAll()


    $scope.create = function () {

            var size = {
                ma: $scope.ma,
                chieuDai: $scope.chieuDai,
            }
            $http.post("http://localhost:8080/admin/size/add", size).then(r => {
                location.reload();
                alert("Thêm thành công")
            }).catch(e => {
                document.getElementById("eSizeMa").innerText = e.data.ma == undefined ? "" : e.data.ma
                document.getElementById("eSizeChieuDai").innerText = e.data.chieuDai == undefined ? "" : e.data.chieuDai
            })
        }

    $scope.getSize = function (ma){
        var url ="/admin/size/chi-tiet" + "/" + ma;
        console.log(url)
        $http.get(url).then(function (r){
            console.log(r.data)
            let size = r.data;
            $scope.ma = size.ma;
            $scope.chieuDai = size.chieuDai;
            $scope.ngayTao = size.ngayTao;
            $scope.ngayCapNhat = size.ngayCapNhat;
        })
    }
   $scope.delete = function (ma){
        var url ="/admin/size/delete" + "/" + ma;
        $http.delete(url).then(function (r){
            alert("Xóa Thành công")
            location.reload();
        })
   }
   $scope.update = function (ma){
        var url = "/admin/size/update" + "/" +ma;
        var updateSize = {
            ma: $scope.ma,
            chieuDai: $scope.chieuDai,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat,
        }
        $http.put(url, updateSize).then(function (r){
            location.reload();
            alert("Cập nhật Thành công")
        }).catch(e => {
            document.getElementById("eSizeChieuDaiUd").innerText = e.data.chieuDai == undefined ? "" : e.data.chieuDai
        })
   }
    $scope.removeER = function (id) {
        document.getElementById(id).innerText = "";
    }
})

