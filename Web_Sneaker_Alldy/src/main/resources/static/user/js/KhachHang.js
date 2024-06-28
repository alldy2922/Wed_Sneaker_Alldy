var app = angular.module('khachhang-user', []);
app.controller('ctrl', function ($scope, $http){

    $scope.khachHang = [];
    $scope.checkPassRe = false;
    $scope.khachHangAdd = {gioiTinh : null};
    $scope.showPassword = function (idInput,idButton) {
        let input = document.getElementById(idInput);
        let button = document.getElementById(idButton)
        if (input.type == "password") {
            button.className = " bx bxs-hide";
            input.type = "text"
        } else {
            input.type = "password"
            button.className = " bx bxs-show"
        }
    }
    $scope.add = function (){
        if ($scope.khachHangAdd.password1 != $scope.khachHangAdd.password) {
            $scope.checkPassRe = true;
        }else {
            $scope.checkPassRe = false;
            $http.post("/khach-hang", $scope.khachHangAdd).then(r =>  {
                alert("Đăng Ký Thành Công")
                location.href = "/dang-nhap"
            }).catch(e => {
                document.getElementById("usernameAddER").innerText = e.data.username == undefined ? "" : e.data.username;
                document.getElementById("passNewER").innerText = e.data.password == undefined ? "" : e.data.password;
                document.getElementById("verifyPassNewER").innerText = e.data.password == undefined ? "" : e.data.password;
                document.getElementById("hoVaTenAddER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
                document.getElementById("soDienThoaiAddER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
                document.getElementById("emailAddER").innerText = e.data.email == undefined ? "" : e.data.email;
            })
        }
    }
    $scope.removeErrors = function (id) {
        document.getElementById(id).innerText = "";
    }
       
   
})