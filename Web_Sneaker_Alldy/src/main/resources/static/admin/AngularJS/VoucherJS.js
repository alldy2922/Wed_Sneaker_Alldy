var app = angular.module("voucher", [])
app.controller("voucher-ctrl", function ($scope, $http) {
        const url = "http://localhost:8080/admin/voucher"
        var getUrlWithId = function (id) {
            return url + id;
        }
        const pathName = location.pathname;
        const id = pathName.substring(pathName.lastIndexOf("/"))

        $scope.voucherAdd = {
            danhSachKhach: [],
            loaiMucGiam: 'TIEN'
        }
        $scope.voucherAdd.loaiMucGiam = 'TIEN';
        $scope.khachHangVoucher = function (dieuKien) {
            $http.get("http://localhost:8080/admin/khach-hang/voucher?dieuKien=" + dieuKien).then(function (res) {
                console.log("k",res.data)
                $scope.findAllKhachHang = res.data
                $scope.findAllKhachHang.forEach(k => $scope.voucherAdd.danhSachKhach.forEach(k1 => {
                    if (k.username == k1.username) {
                        k.selected = true
                    }
                }))
            }).catch(err => console.log(err))
            console.log("khách ")

        }
        //    chi tiet
        $http.get("http://localhost:8080/admin/voucher" + id).then(function (res) {
            const voucher = res.data;
            $scope.ma = voucher.ma
            $scope.voucherAdd.moTa = voucher.moTa;
            $scope.voucherAdd.loaiMucGiam = voucher.loaiMucGiam;
            $scope.voucherAdd.mucGiam = voucher.mucGiam;
            $scope.voucherAdd.mucGiamToiDa = voucher.mucGiamToiDa;
            $scope.voucherAdd.giaTriDonHang = voucher.giaTriDonHang;
            $scope.voucherAdd.soLuong = voucher.soLuong;
            $scope.voucherAdd.ngayBatDau = new Date(voucher.ngayBatDau);
            $scope.voucherAdd.ngayKetThuc = new Date(voucher.ngayKetThuc);
            $scope.voucherAdd.hinhThucThanhToan = voucher.hinhThucThanhToan;
            $scope.voucherAdd.doiTuongSuDung = voucher.doiTuongSuDung;
            $scope.voucherAdd.danhSachKhach = voucher.danhSachKhachHang;
            $scope.voucherAdd.trangThai = voucher.trangThai
            $scope.ngayHienTai = new Date();

            console.log("khách 1", voucher.danhSachKhachHang)
            $scope.khachHangVoucher(3)

        });
        $scope.findAllKhachHang = []

        $scope.locKhach = "";
        $scope.handleOptionChange = function (optionValue) {
            $scope.khachHangVoucher(optionValue)
        };


        //update trangj thasi
        $scope.updateTrangThai = function (trangThai) {
            $http.put("http://localhost:8080/admin/voucher/cap-nhat-trang-thai" + id, trangThai).then(function (res) {
                location.reload();
            })
        }

        //Xóa mềm voucher
        $scope.xoaVoucher = function (id) {
            $http.put("http://localhost:8080/admin/voucher/xoa-voucher/" + id).then(function (res) {
                alertify.success("Xóa thành công");

                setTimeout(function () {
                    window.location.href = "http://localhost:8080/admin/voucher";
                }, 1000);
            })
        }

        $scope.danhSachKhach = []
        $scope.selectKhach = function (id) {
            var index = $scope.danhSachKhach.indexOf(id);
            if (index > -1) {
                $scope.danhSachKhach.splice(index, 1);
            } else {
                $scope.danhSachKhach.push(id);
            }
        }


//add
        $scope.create = function () {
            let formData = new FormData();
            formData.append("voucher", new Blob([JSON.stringify($scope.voucherAdd)], {
                type: 'application/json'
            }))

            formData.append("idKhach", new Blob([JSON.stringify($scope.danhSachKhach)], {
                type: 'application/json'
            }))
            $http.post(url, formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(function (response) {
                alertify.success("Thêm thành công");

                setTimeout(function () {
                    window.location.href = "http://localhost:8080/admin/voucher";
                }, 2000);
            }).catch(error => {
                $scope.erMoTa = error.data.moTa
                $scope.erMucGiam = error.data.mucGiam
                $scope.erMucGiamToiDa = error.data.mucGiamToiDa
                $scope.erLoaiMucGiam = error.data.loaiMucGiam
                $scope.erGiaTriDonHang = error.data.giaTriDonHang
                $scope.erNgayBatDau = error.data.ngayBatDau
                $scope.erNgayKetThuc = error.data.ngayKetThuc
                $scope.erSoLuong = error.data.soLuong
                $scope.erKhachHang = error.data.khachHang
                $scope.erdoiTuongSuDung = error.data.doiTuongSuDung
            })
        }
//update
        $scope.update = function () {
            var urlWithId = getUrlWithId(id)
            var voucher = {
                ngayKetThuc: $scope.voucherAdd.ngayKetThuc,
                ngayBatDau: $scope.voucherAdd.ngayBatDau,
                moTa: $scope.voucherAdd.moTa,
                loaiMucGiam: $scope.voucherAdd.loaiMucGiam,
                mucGiam: $scope.voucherAdd.mucGiam,
                giaTriDonHang: $scope.voucherAdd.giaTriDonHang,
                soLuong: $scope.voucherAdd.soLuong,
                hinhThucThanhToan: $scope.ngayKetThuc,
                mucGiamToiDa: $scope.voucherAdd.mucGiamToiDa
            }
            $http.put(urlWithId, voucher).then(function (resp) {
                location.reload();
                alert("Update success");
            })
        }
    }
)
;

