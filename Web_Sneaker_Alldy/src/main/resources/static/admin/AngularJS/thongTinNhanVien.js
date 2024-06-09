 var app = angular.module("thongTinNhanVien-app", [])
app.controller("thongTinNhanVien-ctrl", function ($scope, $http) {
    $scope.user = {
        gioiTinh : null
    };
    $scope.gioiTinh = [
        {value : null, text : "Không xác định"},
        {value : true, text : "Nam"},
        {value : false, text : "Nữ"}
    ]
    const labelAddImg = '<label for="pro-image" id="labelAddImg" class="addImg d-flex align-items-center justify-content-center" >' +
        '<i class="bx bxs-image-add"></i>' +
        '</label>'


    $http.get("/admin/nhan-vien/getUser").then(r => {
        r.data.gioiTinh = JSON.stringify(r.data.gioiTinh) 
        $scope.user = r.data
        $scope.user.ngaySinh = new Date(r.data.ngaySinh)
        if ($scope.user.anhDaiDien == null) $(".preview-images-zone").append(labelAddImg);
        else {
            let imgUser = new DataTransfer();
            imgUser.items.add(new File([$scope.user.anhDaiDien],$scope.user.anhDaiDien,{
                lastModified:new Date()
            }))
            document.getElementById("pro-image").files = imgUser.files;
        };
        // document.getElementById("gioiTinh").value = $scope.user.gioiTinh+""
    }).catch(e => console.log(e))

    $scope.update = function () {
        alertify.confirm("Đổi mật khẩu?", function () {
            let anhDaiDien = document.getElementById("pro-image").files.length == 0 ? null : document.getElementById("pro-image").files[0];
            let formData = new FormData();
            if($scope.user.gioiTinh == undefined) $scope.user.gioiTinh = null;
            formData.append("nhanVien",new Blob([JSON.stringify($scope.user)], {
                type: 'application/json'
            }))
            formData.append("img",anhDaiDien)

            $http.put("/admin/nhan-vien/thong-tin-ca-nhan", formData,{
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(r => {
                alertify.success("Cập nhật thông tin thành công")
                document.getElementById("imgUser").src = "/image/loadImageUser/"+r.data.username
            }).catch(e => {
                alertify.error("Cập nhật thông tin thất bại")
                document.getElementById("hoVaTenER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
                document.getElementById("soDienThoaiER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
                document.getElementById("emailER").innerText = e.data.email  == undefined ? "" : e.data.email ;
            })
        }, function () {
        })
    }
    $scope.removeErrors = function (id){
        document.getElementById(id).innerText = ""
    }

    $scope.removeFile = function (id) {
        document.getElementById("pro-image").files = new DataTransfer().files
        $(".preview-images-zone").append(labelAddImg);
    }
    $scope.addFile = function () {
        $scope.removeErrors('erImg')
        if(document.getElementById("pro-image").files[0].size > 1 * 1024 * 1024 ){
            return
        }
        document.getElementById("labelAddImg").remove()
    }

})