var app = angular.module('diaChi', []);
app.controller('diaChiCtrl', function ($scope, $http) {
    $scope.user = {
        gioiTinh: null
    };
    $scope.gioiTinh = [
        {value: "null", text: "Không xác định"},
        {value: "true", text: "Nam"},
        {value: "false", text: "Nữ"}
    ]
    const token = "954c787d-2876-11ee-96dc-de6f804954c9";
    const headers = {headers: {token: token}}
    const headersShop = {headers: {token: token, ShopId: 4379549}}
    $scope.citys = []
    $scope.wards = []
    $scope.districts = []
    $scope.voucherDH = ""
    $scope.loginIn = false;
    $scope.isSelectSaveDC = false;
    $scope.form = {}
    $scope.items = []
    $scope.diaChi = {};
    $scope.diaChiByTaiKhoan = []

    const labelAddImg = '<label for="pro-image" id="labelAddImg" class="addImg d-flex align-items-center justify-content-center" >' +
        '<i class="bx bxs-image-add"></i>' +
        '</label>'

    $scope.getValue = function () {
        $scope.textInner = "Thành Phố: " + $scope.city + "/ Quận huyện: " + $scope.district + " / Xã: " + $scope.xa
    }
    $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", headers).then(res => {
        $scope.citys = res.data.data
    }).catch(err => console.log(err))
    $scope.districtChange = function (id) {
        $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + id, headers).then(res => {
            $scope.wards = res.data.data;
            console.log(res.data)
        }).catch(err => console.log(err))
    }
    $scope.cityChange = function (id) {
        if (id.length == 0) {
            document.getElementById('district').length = 0;
            document.getElementById('ward').length = 0;
        } else {
            let data = {province_id: parseInt(id)}
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + id, headers).then(res => {
                $scope.districts = res.data.data;
                // $scope.districtChange($scope.districts[0].DistrictID)
            }).catch(err => console.log(err))
        }
    }
    $scope.create = function () {

        if($scope.diaChiByTaiKhoan.length>=5){
            alertify.error("Chỉ được tối đa 5 địa chỉ")
            return
        }

        let indexCity = $scope.citys.findIndex(c => c.ProvinceID == $scope.thanhPhoCode)
        let indexDistrict = $scope.districts.findIndex(d => d.DistrictID == $scope.quanHuyenCode)
        let indexWard = $scope.wards.findIndex(w => w.WardCode == $scope.xaPhuongCode)
        var diaChi = {
            thanhPhoCode: $scope.thanhPhoCode,
            thanhPhoName: $scope.citys[indexCity] == undefined ? "" : $scope.citys[indexCity].ProvinceName,
            quanHuyenCode: $scope.quanHuyenCode,
            quanHuyenName: $scope.districts[indexDistrict] == undefined ? "" : $scope.districts[indexDistrict].DistrictName,
            xaPhuongCode: $scope.xaPhuongCode,
            xaPhuongName: $scope.wards[indexWard] == undefined ? "" : $scope.wards[indexWard].WardName,
            diaChiChiTiet: $scope.diaChiChiTiet
        }
        $http.post("http://localhost:8080/add", diaChi).then(r => {
            $scope.diaChiByTaiKhoan.push(r.data)
            $("#addDiaChi").modal("hide")
            $scope.diaChi = {}
            $scope.thanhPhoCode = ""
            $scope.quanHuyenCode = ""
            $scope.xaPhuongCode = ""
            alertify.success("Thêm địa chỉ thành công")
        }).catch(error => {
            alertify.error("Vui lòng đầy đủ thông tin địa chỉ")
            console.log(error)
            // $scope.errDiaChiChiTiet = error.data.diaChiChiTiet == undefined ? "" : error.data.diaChiChiTiet
            // $scope.eThanhPho = error.data.thanhPhoCode ? "" : error.data.thanhPhoCode
            // $scope.eQuanHuyen = error.data.quanHuyenCode ? "" : error.data.quanHuyenCode
            // $scope.eXaPhuong = error.data.xaPhuongCode ? "" : error.data.xaPhuongCode

        })
    }
    // $scope.update = function () {
    //     let anhDaiDien = document.getElementById("pro-image").files.length == 0 ? null : document.getElementById("pro-image").files[0];
    //     let formData = new FormData();
    //     if ($scope.user.gioiTinh == undefined) $scope.user.gioiTinh = null;
    //     formData.append("ThongTinKhachHang", new Blob([JSON.stringify($scope.user)], {
    //         type: 'application/json'
    //     }))
    //     formData.append("img", anhDaiDien)
    //
    //     $http.put("/khach-hang", formData, {
    //         transformRequest: angular.identity,
    //         headers: {'Content-Type': undefined}
    //     }).then(r => {
    //         alert("Cập nhật thành công")
    //         document.getElementById("imgUser").src = "/image/loadImageUser/" + r.data.username
    //     }).catch(e => {
    //         console.log(e)
    //         document.getElementById("hoVaTenER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
    //         document.getElementById("soDienThoaiER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
    //         document.getElementById("emailER").innerText = e.data.email == undefined ? "" : e.data.email;
    //     })
    // }
    $scope.update = function () {
        let anhDaiDien = document.getElementById("pro-image").files.length == 0 ? null : document.getElementById("pro-image").files[0];
        let formData = new FormData();
        if ($scope.user.gioiTinh == "null") $scope.user.gioiTinh = null;
        formData.append("ThongTinKhachHang", new Blob([JSON.stringify($scope.user)], {
            type: 'application/json'
        }))
        formData.append("img", anhDaiDien)

        $http.put("/khach-hang", formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(r => {
            // location.reload();
            alertify.success("Cập nhật thành công")
            document.getElementById("imgUser").src = "/image/loadImageKhachHang/" + r.data.username
        }).catch(e => {
            document.getElementById("hoVaTenER").innerText = e.data.hoVaTen == undefined ? "" : e.data.hoVaTen;
            document.getElementById("soDienThoaiER").innerText = e.data.soDienThoai == undefined ? "" : e.data.soDienThoai;
            document.getElementById("emailER").innerText = e.data.email == undefined ? "" : e.data.email;
        })
    }
    $scope.removeErrors = function (id) {
        document.getElementById(id).innerText = ""
    }

    $scope.removeFile = function (id) {
        document.getElementById("pro-image").files = new DataTransfer().files
        $(".preview-images-zone").append(labelAddImg);
    }
    $scope.addFile = function () {
        document.getElementById("labelAddImg").remove()
    }
    $http.get("/khach-hang/getUser").then(r => {
        r.data.gioiTinh = r.data.gioiTinh+""
        $scope.user = r.data
        $scope.user.ngaySinh = new Date(r.data.ngaySinh)
        console.log("datauser",$scope.user)
        if ($scope.user.anhDaiDien == null) $(".preview-images-zone").append(labelAddImg);
        else {
            let imgUser = new DataTransfer();
            imgUser.items.add(new File([$scope.user.anhDaiDien], $scope.user.anhDaiDien, {
                lastModified: new Date()
            }))
            document.getElementById("pro-image").files = imgUser.files;
        }
        ;

    }).catch(e => console.log(e))
     document.getElementById("gioiTinh").value = $scope.user.gioiTinh+""
    $scope.detail = function (id) {
        $http.get("/khach-hang/detail/" + id).then(r => {
            r.data.gioiTinh = JSON.stringify(r.data.gioiTinh)
            $scope.khachHangDetail = r.data;
            $scope.khachHangDetail.ngaySinh = new Date(r.data.ngaySinh)
            console.log($scope.khachHangDetail)
        }).catch(e => console.log(e))
    }

    $http.get("/dia-chi-khach-hang")
        .then(function (response) {
            console.log("dia-chi:s" , response.data)
            $scope.diaChiByTaiKhoan = response.data;
        })
        .catch(function (error) {
        });
    $scope.idDiaChi = "";
    $scope.updateDiaChi = function () {
        let indexCity = $scope.citys.findIndex(c => c.ProvinceID == $scope.thanhPhoCode)
        let indexDistrict = $scope.districts.findIndex(d => d.DistrictID == $scope.quanHuyenCode)
        let indexWard = $scope.wards.findIndex(w => w.WardCode == $scope.xaPhuongCode)
        var url = "/update" + "/" + $scope.idDiaChi;
        var updateDiaChi = {
            thanhPhoCode: $scope.thanhPhoCode,
            thanhPhoName: $scope.citys[indexCity] == undefined ? "" : $scope.citys[indexCity].ProvinceName,
            quanHuyenCode: $scope.quanHuyenCode,
            quanHuyenName: $scope.districts[indexDistrict] == undefined ? "" : $scope.districts[indexDistrict].DistrictName,
            xaPhuongCode: $scope.xaPhuongCode,
            xaPhuongName: $scope.wards[indexWard] == undefined ? "" : $scope.wards[indexWard].WardName,
            diaChiChiTiet: $scope.diaChiChiTiet
        }
        $http.put(url, updateDiaChi).then(function (r) {
            let index = $scope.diaChiByTaiKhoan.findIndex(d => d.id == $scope.idDiaChi)
            $scope.diaChiByTaiKhoan[index] = r.data
            console.log($scope.diaChiByTaiKhoan[index])
            $scope.idDiaChi = ""
            alertify.success("Cập nhật địa chỉ thành công")
            $scope.diaChi = {}
            $("#diaChiUpdate").modal("hide")
        }).catch(error => {
            alertify.error("Vui lòng đầy đủ thông tin địa chỉ")
            // console.log(error)
            // $scope.errDiaChiChiTiet = error.data.diaChiChiTiet
            // $scope.eThanhPho = error.data.thanhPhoCode
            // $scope.eQuanHuyen = error.data.quanHuyenCode
            // $scope.eXaPhuong = error.data.xaPhuongCode
        })
    }
    // $scope.getDiaChi = function (id){
    //     var url ="/chi-tiet" + "/" + id;
    //     console.log(url)
    //     $http.get(url).then(function (r){
    //        this.detail = r.data;
    //        this.detail.thanhPhoCode = this.detail.thanhPhoCode +""
    //         // $scope.khachHang.getDistricts(this.detail.thanhPhoCode)
    //         this.detail.quanHuyenCode = this.detail.quanHuyenCode + ""
    //         // $scope.khachHang.getWards(this.detail.quanHuyenCode)
    //         this.detail.xaPhuongCode = this.detail.xaPhuongCode + ""
    //     })
    // }
    $scope.getDiaChi = function (id) {
        var data = {
            id: id
        }
        $scope.idDiaChi = id;
        $http.post("/dia-chi-by-id",data).then(function (response) {
            let diaChi = response.data;
            $scope.diaChiChiTiet = diaChi.diaChiChiTiet;
            $scope.thanhPhoCode = diaChi.thanhPhoCode + ""
            $scope.cityChange(diaChi.thanhPhoCode)
            $scope.quanHuyenCode = diaChi.quanHuyenCode + ""
            $scope.districtChange(diaChi.quanHuyenCode)
            $scope.xaPhuongCode = diaChi.xaPhuongCode;
            console.log(response.data)
        })
    }
    $scope.deleteById = function (id) {
        alertify.confirm("Xóa địa chỉ?", function () {
            $http.delete("/deleteDC/" + id).then(r => {
                let index = $scope.diaChiByTaiKhoan.findIndex(d => d.id == id)
                $scope.diaChiByTaiKhoan.splice(index,1)
                alertify.success("Xóa địa chỉ thành công")
            }).catch(e => {
                alertify.error("Thêm địa chỉ thành công")
            })
        },function (){})
    }

    $scope.setMacDinh = function (id){
        alertify.confirm("Đặt địa chỉ làm mặc đinh?", function () {
            $http.get("/dia-chi/set-mac-dinh/" + id).then(r => {
                $http.get("/dia-chi-khach-hang")
                .then(function (response) {
                    $scope.diaChiByTaiKhoan = response.data;
                })
                .catch(function (error) {
                });
                alertify.success("Đặt địa chỉ làm mặc định thành công");
            }).catch(e => {
                alertify.error("Đặt địa chỉ làm mặc định thất bại!")
            })
        }, function () {
        })

    }
});
