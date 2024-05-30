var app = angular.module("resetPassword-app",[])
app.controller("resetPassword-ctrl", function($scope, $http){
    $scope.data = {};
    $scope.showPassword = function (idInput,idButton){
        let input = document.getElementById(idInput);
        let button = document.getElementById(idButton)
        if(input.type == "password") {
            button.className = " bx bxs-hide";
            input.type="text"
        }else {
            input.type="password"
            button.className = " bx bxs-show"
        }
    }
    $scope.resetPassword = function (){
        alertify.confirm("Đổi mật khẩu?", function () {
            $http.put("/admin/doi-mat-khau",$scope.data).then(r =>{
                alertify.success("Đổi mật khẩu thành công")
                $scope.data = {};
            }).catch(e => {
                alertify.error("Đổi mật khẩu thất bại")
                document.getElementById("passCuER").innerText = e.data.oldPass == undefined ? "" : e.data.oldPass;
                document.getElementById("passNewER").innerText = e.data.newPass == undefined ? "" : e.data.newPass;
                document.getElementById("verifyPassNewER").innerText = e.data.verifyNewPass == undefined ? "" : e.data.verifyNewPass;
            })
        }, function () {
        })
    }
    $scope.removeErrors = function (id){
        document.getElementById(id).innerText = ""
    }
})