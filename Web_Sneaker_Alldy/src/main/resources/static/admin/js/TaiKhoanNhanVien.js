var app = angular.module('nhanvien-admin', []);
app.controller('ctrl', function ($scope, $http) {

    $scope.nhanVien = [];
    $scope.nhanVienDetail = {};
    $scope.nhanVienAdd = {gioiTinh : null};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    $scope.keyWord = ""

    $scope.getAll = function (page){
        $scope.pageNumber = page
        $http.get("/admin/nhan-vien/get-all?page="+page+"&keyWord="+$scope.keyWord).then(r =>{
            $scope.nhanVien = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
        }).catch(e => console.log(e))
    }
    $scope.getPageNumbers = function (totalPages){
        $scope.pageNumbers = [];
        for (let i = 0; i< totalPages;i++){
            $scope.pageNumbers.push(i);
        }
    }
    $scope.getAll(0)

    $scope.detail = function (id){
        $http.get("/admin/nhan-vien/detail/"+id).then(r => {
            r.data.gioiTinh = JSON.stringify(r.data.gioiTinh)
            $scope.nhanVienDetail = r.data;
            $scope.nhanVienDetail.ngaySinh = new Date(r.data.ngaySinh)
            console.log($scope.nhanVienDetail)
        }).catch(e => console.log(e))
    }
    $scope.update = function (){
        $http.put("/admin/nhan-vien/update",$scope.nhanVienDetail).then(r => {
            let index = $scope.nhanVien.findIndex(n => n.username == $scope.nhanVienDetail.username);
            $scope.nhanVien[index] = $scope.nhanVienDetail
            alertify.success("Cập nhật nhân viên thành công")
        }).catch(e => {
            alertify.error("Cập nhật nhân viên thất bại")
            document.getElementById("hoVaTenPutER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
            document.getElementById("soDienThoaiPutER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
            document.getElementById("vaiTroPutER").innerText = e.data.vaiTro == undefined ? "" : e.data.vaiTro;
            document.getElementById("emailPutER").innerText = e.data.email  == undefined ? "" : e.data.email;
        })
    }
    $scope.add = function (){
        $http.post("/admin/nhan-vien",$scope.nhanVienAdd).then(r => {
            $scope.getAll($scope.pageNumber);
            $scope.nhanVienAdd = {gioiTinh : null};
            $('#viewAdd').modal('hide');
            $("#cancelModal").click()
            alertify.success("Thêm nhân viên thành công")
        }).catch(e => {
            alertify.error("Thêm nhân viên thất bại")
            document.getElementById("usernameAddER").innerText = e.data.username == undefined ? "" : e.data.username;
            document.getElementById("hoVaTenAddER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
            document.getElementById("soDienThoaiAddER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
            document.getElementById("vaiTroAddER").innerText = e.data.vaiTro == undefined ? "" : e.data.vaiTro;
            document.getElementById("emailAddER").innerText = e.data.email  == undefined ? "" : e.data.email;
        })
    }

    $scope.removeErrors = function (id){
        document.getElementById(id).innerText = ""
    }
    $scope.deleteByUsername = function (username){
        alertify.confirm("Xóa nhân viên?" , function () {
            $http.delete("/admin/nhan-vien/"+username).then(r =>{

                if($scope.nhanVien.length==1){
                    if($scope.pageNumber>0) $scope.pageNumber--;
                }
                $scope.getAll($scope.pageNumber);

                alertify.success("Xóa nhân viên thành công")
            }).catch(e => {
                alertify.error("Xóa nhân viên thất bại")
            })
        }, function () {
            alertify.error("Xóa nhân viên thất bại")
        })
    }
})