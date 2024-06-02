var app = angular.module('checkOut', []);
app.controller('checkOutCtrl', function ($scope, $http) {
    const token = "954c787d-2876-11ee-96dc-de6f804954c9";
    const headers = {headers: {token: token}}
    const headersShop = {headers: {token: token, ShopId: 4379549}}
    $scope.feeShipped = 0
    $scope.giaGiam = 0
    $scope.sumTotal = 0
    $scope.vouchers = []
    $scope.citys = []
    $scope.wards = []
    $scope.cart = []
    $scope.districts = []
    $scope.disVoucher = false;
    $scope.voucherDH = ""
    $scope.loginIn = false;
    $scope.isSelectSaveDC = false;
    $scope.searchVoucher = false;
    $scope.getValue = function () {
        $scope.textInner = "Thành Phố: " + $scope.city + "/ Quận huyện: " + $scope.district + " / Xã: " + $scope.xa
    }


    $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", headers).then(res => {
        $scope.citys = res.data.data
    }).catch(err => console.log(err))


    $scope.cityChange = function (id) {
        if (id.length == 0) {
            document.getElementById('feeShipped').value = "";
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

    $scope.districtChange = function (id) {
        $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + id, headers).then(res => {
            $scope.wards = res.data.data;
            console.log(res.data)
        }).catch(err => console.log(err))
    }

    $scope.getFeeShipped = function (idDistrict, wardCode) {
        let data = {
            "service_type_id": 2,
            "to_district_id": parseInt(idDistrict),
            "to_ward_code": wardCode,
            "height": 10,
            "length": 10,
            "weight": 200,
            "width": 10,

        }

        $http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", data, headersShop).then(res => {
            console.log(res.data.data);
            $scope.feeShipped = res.data.data.total;
        }).catch(err => console.log(err));

    }

    //show voucher
    $scope.searchVC = function () {
        ma = $scope.maVCC
        $scope.searchVoucher = true
        $http.get("/admin/voucher/" + ma).then(function (res) {
            $scope.voucherS = res.data;
        })

    }
    $http.get("/check-out/voucher").then(resp => {
        console.log(resp.data)
        $scope.vouchers = resp.data;
    }).catch(error => {
        console.log(error)
    })
    $http.get("/check-out/khach-hang-voucher").then(resp => {
        console.log(resp.data)
        $scope.voucherInKhach = resp.data;
    }).catch(error => {
        console.log(error)
    })


//    check-out

    $scope.create = function () {
        alertify.confirm("Xác nhận thanh toán", function () {
            // let tongTien = document.getElementById("tongTien").value
            let indexCity = $scope.citys.findIndex(c => c.ProvinceID == $scope.thanhPhoCode)
            let indexDistrict = $scope.districts.findIndex(d => d.DistrictID == $scope.quanHuyenCode)
            let indexWard = $scope.wards.findIndex(w => w.WardCode == $scope.xaPhuongCode)
            console.log(indexWard)

            var donHang = {
                tenNguoiNhan: $scope.nguoiNhan,
                soDienThoai: $scope.soDienThoai,
                email: $scope.email,
                phuongThucThanhToan: $scope.phuongThucThanhToan,
                voucher: $scope.voucherDH,
                thanhPhoCode: $scope.thanhPhoCode,
                thanhPhoName: $scope.citys[indexCity] == undefined ? "" : $scope.citys[indexCity].ProvinceName,
                quanHuyenCode: $scope.quanHuyenCode,
                quanHuyenName: $scope.districts[indexDistrict] == undefined ? "" : $scope.districts[indexDistrict].DistrictName,
                xaPhuongCode: $scope.xaPhuongCode,
                xaPhuongName: $scope.wards[indexWard] == undefined ? "" : $scope.wards[indexWard].WardName,
                diaChiChiTiet: $scope.diaChiChiTiet,
                ghiChu: $scope.ghiChu,
                phiGiaoHang: $scope.feeShipped,
                tienGiam: $scope.giaGiam,
                tongTien: ($scope.sumTotal + $scope.feeShipped) * 100
            }
            var diaChi = {
                thanhPhoCode: $scope.thanhPhoCode,
                thanhPhoName: $scope.citys[indexCity] == undefined ? "" : $scope.citys[indexCity].ProvinceName,
                quanHuyenCode: $scope.quanHuyenCode,
                quanHuyenName: $scope.districts[indexDistrict] == undefined ? "" : $scope.districts[indexDistrict].DistrictName,
                xaPhuongCode: $scope.xaPhuongCode,
                xaPhuongName: $scope.wards[indexWard] == undefined ? "" : $scope.wards[indexWard].WardName,
                diaChiChiTiet: $scope.diaChiChiTiet
            }
            if ($scope.isSelectSaveDC) {
                $http.post("http://localhost:8080/dia-chi", diaChi).then(r => {
                })
            }
            $http.post("http://localhost:8080/check-out", donHang).then(r => {
                if (r.data.vnPayUrl == undefined) {
                    Swal.fire({
                        title: 'Đặt hàng thành công',
                        text: 'Cảm ơn bạn đã mua hàng tại Alldy Store!!!',
                        icon: 'success',
                        timer: 1200,
                        showConfirmButton: false
                    }).then(() => {
                        if ($scope.loginIn == false) {
                            window.location.href = "http://localhost:8080/gio-hang";
                        } else {
                            window.location.href = "http://localhost:8080/lich-su-mua-hang1";
                        }
                    });
                } else {
                    location.href = r.data.vnPayUrl
                }

            }).catch(err => {
                console.log(err)
                if(err.data.erSoLuong != undefined) window.location.href = "http://localhost:8080/gio-hang";
                $scope.errNguoiNhan = err.data.tenNguoiNhan
                $scope.errTienGiam = err.data.tienGiam
                $scope.errSoDienThoai = err.data.soDienThoai
                $scope.errEmail = err.data.email
                $scope.errThanhPhoCode = err.data.thanhPhoCode
                $scope.errQuanHuyenCode = err.data.quanHuyenCode
                $scope.errXaPhuongCode = err.data.xaPhuongCode
                $scope.errDiaChiChiTiet = err.data.diaChiChiTiet
                alertify.error(err.data.tienGiam)
            })
        }, function () {
        })
    }

    $scope.getDataAPI = function (maVC) {
        if ($scope.sumTotal == 0) {
            console.log("không có tiền")
            return;
        }
        var data = {
            voucher: maVC,
            tongThanhToan: $scope.sumTotal + 0
        }
        $scope.voucherDH = maVC
        $http.post('/using-voucher', data)
            .then(function (response) {
                $scope.giaGiam = response.data;
            })
            .catch(function (error) {
                console.log("lỗi")
            });
    };
    $scope.huyVoucher = function () {
        $scope.giaGiam = 0;
        $scope.voucherDH = "";
    }

    $http.get("/cart/find-all").then(r => {
        console.log(r.data)
        $scope.cart = r.data;
        for (var i = 0; i < $scope.cart.length; i++) {
            $scope.sumTotal += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
    }).catch(e => console.log(e))

    $scope.totalpayment = function () {
        var tien = 0;
        tien = ($scope.sumTotal + $scope.feeShipped) - $scope.giaGiam
        return tien
    }
//    disabledVoucher

    $http.get("/dia-chi-khach-hang")
        .then(function (response) {
            // Nếu API trả về tên tài khoản, hiển thị tên tài khoản
            $scope.loginIn = true;
            console.log(response.data)
            $scope.diaChiByTaiKhoan = response.data;
        })
        .catch(function (error) {
            // Không đăng nhập hoặc có lỗi, giữ trạng thái loggedIn là false
            $scope.loginIn = false;
        });
    $scope.getDiaChiById = function (idDiaChi) {
        var data = {
            id: idDiaChi
        }
        $http.post('/dia-chi-by-id', data)
            .then(function (response) {
                let diaChi = response.data;
                $scope.diaChiChiTiet = diaChi.diaChiChiTiet;
                $scope.thanhPhoCode = diaChi.thanhPhoCode + "";
                $scope.cityChange(diaChi.thanhPhoCode)
                $scope.quanHuyenCode = diaChi.quanHuyenCode + "";
                $scope.districtChange(diaChi.quanHuyenCode)
                $scope.xaPhuongCode = diaChi.xaPhuongCode;
                $scope.getFeeShipped(diaChi.quanHuyenCode, diaChi.xaPhuongCode)
                console.log(response.data)
            })
    }


    $http.get("/get-khach-hang-thanh-toan").then(function (resp) {
        let khachByThanhToan = resp.data
        $scope.nguoiNhan = khachByThanhToan.hoVaTen;
        $scope.soDienThoai = khachByThanhToan.soDienThoai;
        $scope.email = khachByThanhToan.email;
    })

    $http.get("/thanh-toan/dia-chi-mac-dinh").then(function (resp) {
        const diaChiMacDinh = resp.data
        $scope.diaChiChiTiet = diaChiMacDinh.diaChiChiTiet;
        $scope.thanhPhoCode = diaChiMacDinh.thanhPhoCode + "";
        $scope.cityChange(diaChiMacDinh.thanhPhoCode)
        $scope.quanHuyenCode = diaChiMacDinh.quanHuyenCode + "";
        $scope.districtChange(diaChiMacDinh.quanHuyenCode)
        $scope.xaPhuongCode = diaChiMacDinh.xaPhuongCode;
        $scope.getFeeShipped(diaChiMacDinh.quanHuyenCode, diaChiMacDinh.xaPhuongCode)
    })

    $scope.login = function () {
        var expires = (new Date(Date.now() + 60 * 1000)).toUTCString();
        document.cookie = "url=" + window.location.href + "; expires=" + expires;
        location.href = "/dang-nhap";
    }

//    so sánh ngày bắt đầu với ngày hiên tại
    $scope.isStartDateGreaterThanCurrentDate = function (startDate) {
        return new Date(startDate) > new Date();
    };

    //    show giỏ hàng
    $scope.cart = []
    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
        return totalPrice;
    }
    $http.get("/cart/find-all").then(r => {
        console.log(r.data)
        $scope.cart = r.data;
        console.log("soLuong:")
    }).catch(e => console.log(e))


});
