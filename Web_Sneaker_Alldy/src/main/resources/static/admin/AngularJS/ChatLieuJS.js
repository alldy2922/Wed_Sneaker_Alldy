var app = angular.module("chat-lieu", [])
app.controller("chat-lieu-ctrl", function ($scope, $http){
    $scope.form = {}
    $scope.items = []
    $scope.findALL = function (){
        $http.get("/admin/chat-lieu/find-all").then(resp => {
            console.log(resp.data)
            $scope.items = resp.data;
        }).catch(error => {
            console.log(error)
        })
    }
    $scope.findALL()

    $scope.create = function (){
        if($scope.ten==undefined || $scope.ten.length==0) {
            document.getElementById("eTen").innerText = "Vui lòng nhập tên"
            return
        }
        if($scope.ten.length>100){
            document.getElementById("eTen").innerText = "Tên tối đa 100 ký tự"
            return
        }
        var chatLieu = {
            ten: $scope.ten,
        }
        $http.post("http://localhost:8080/admin/chat-lieu/add", chatLieu).then(r => {
            location.reload();
            alert("Thêm Thành Công")
        })
    }
    $scope.delete = function (id){
        var url = "/admin/chat-lieu/delete" + "/" + id;
        $http.delete(url).then(function (r){
            alert("Xóa Thành Công")
            location.reload();
        })
    }
    $scope.getChatLieu = function (id){
        var url = "/admin/chat-lieu/chi-tiet" + "/" + id;
        $http.get(url).then(function (r){

            let chatLieu = r.data;
            $scope.id = chatLieu.id;
            $scope.ten = chatLieu.ten;
            $scope.ngayTao = chatLieu.ngayTao;
            $scope.ngayCapNhat = chatLieu.ngayCapNhat;
        })
    }

    $scope.update = function (id){
        if($scope.ten==undefined || $scope.ten.length==0) {
            document.getElementById("eTenUd").innerText = "Vui lòng nhập tên"
            return
        }
        if($scope.ten.length>100){
            document.getElementById("eTenUd").innerText = "Tên tối đa 100 ký tự"
            return
        }
        var url = "/admin/chat-lieu/update" + "/" + id;
        var updateChatLieu = {
            id: $scope.id,
            ten: $scope.ten,
            ngayTao: $scope.ngayTao,
            ngayCapNhat: $scope.ngayCapNhat,
        }
        $http.put(url, updateChatLieu).then(function (r){
            location.reload();
            alert("Cập nhật Thành công")
        })
    }
})