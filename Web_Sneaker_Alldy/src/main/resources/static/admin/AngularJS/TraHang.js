var app = angular.module("trahang-app", [])
app.controller("trahang-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.donHangAdd = {
        phuongThucThanhToan: "0"
    }
    $scope.chiTietDonHang = []
    $scope.sanPham = [];
    const limit = 10;
    $scope.er = {}
    $scope.dateNow = new Date().getTime();
    $scope.khachHang = []
    $scope.erAdd = {}
    $scope.sanPhamTra = [];

    $http.get("/admin/don-hang/get-tra?trangThai=1" ).then(r => {
       console.log(r)
    })


    $scope.closeModal = function (id) {
        $(id).modal('hide')
        $scope.donHang = {}
    }
    $("#chuaXacNhanDetail").on('hide.bs.modal', function () {
        $scope.donHangChuaXacNhan = {}
        console.log("11")
        $scope.chiTietDonHang.length = 0
    });
    $("#donHangDetail").on('hide.bs.modal', function () {
        $scope.donHang = {}
        $scope.chiTietDonHang.length = 0
    });

    $scope.getTotalPrice = function () {
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
        return total
    }
    ///////////////////////
    $scope.getSanPham = function () {
        $http.get("/admin/san-pham/1/get-all-ctsp").then(r => {
            $scope.sanPham = r.data
        }).catch(e => console.log(e))
    }
    $scope.getSanPham()
    $scope.addChiTietDonHang = function (item) {
        $scope.chiTietDonHang.push({
            sanPham: item.sanPham,
            anh: item.sanPhamDTO.anh.length == 0 ? "default.png" : item.sanPhamDTO.anh[0],
            size: item.size,
            soLuong: 1,
            donGia: item.sanPhamDTO.giaBan,
            donGiaSauGiam: item.sanPhamDTO.giaNiemYet,
            idChiTietSanPham: item.id
        })
        $scope.er.soLuongSP = ""
    }
    $scope.searchSanPham = function () {
        $http.get("/admin/san-pham/1/get-all-ctsp?keyWord=" + $scope.inputProduct).then(r => {
            $scope.sanPham = r.data
        }).catch(e => console.log(e))
    }
    $scope.checkSanPhamInDonHang = function (idCTSP) {
        let result = false;
        $scope.chiTietDonHang.forEach(d => {
            if (d.idChiTietSanPham == idCTSP) {
                result = true;
            }
        })
        return result;
    }

    ///////////////////////////////////////
    //Get Khách Hàng
    $scope.keyWordKhachHang = ""
    $scope.getAllKhachHang = function () {
        $http.get("/admin/khach-hang/get-all-khach-hang?limit=1000&&keyWord=" + $scope.keyWordKhachHang).then(r => {
            $scope.khachHang = r.data.content;
        }).catch(e => console.log(e))
    }
    $scope.getAllKhachHang()
    $scope.addKhachHangToDonHang = function () {
        let value = document.getElementById("khachHangSL").value
        if (value != 'null') {

            $http.get("/admin/khach-hang/detail/" + value).then(r => {
                $scope.donHangAdd.tenNguoiNhan = r.data.hoVaTen
                $scope.donHangAdd.email = r.data.email
                $scope.donHangAdd.soDienThoai = r.data.soDienThoai
                $scope.getDiaChiKhachHang(value)
            })

            $scope.donHangAdd.nguoiSoHuu = {
                username: value

            }
            document.getElementById("btnAddKh").style.display = "none";
        } else {
            $scope.donHangAdd = {
                phuongThucThanhToan: $scope.donHangAdd.phuongThucThanhToan,
                tenNguoiNhan: "Khách lẻ",
                soDienThoai: "0000000000"
            }
            document.getElementById("btnAddKh").style.display = "block";
        }
    }
    $scope.getDiaChiKhachHang = function (username) {
        $http.get("/dia-chi/get-mac-dinh/" + username).then(r => {
            if (r.data.thanhPhoCode == undefined) return
            $scope.donHangAdd.thanhPhoCode = r.data.thanhPhoCode + ""

            //Lấy quận huyện
            $scope.giaoHangNhanh.getDistricts(r.data.thanhPhoCode)//hàm lấy quận huyện truyền vào thành phố
            $scope.donHangAdd.quanHuyenCode = r.data.quanHuyenCode + "" // set selected quận huyện

            $scope.giaoHangNhanh.getWards(r.data.quanHuyenCode)//hàm lấy xã truyền vào quận huyện
            $scope.donHangAdd.xaPhuongCode = r.data.xaPhuongCode + "" //set selected xã

            $scope.donHangAdd.diaChiChiTiet = r.data.diaChiChiTiet

            $scope.giaoHangNhanh.getFeeShippedAdd()
        })
    }
    $scope.addKhachHang = function () {
        var data = {
            username: $scope.donHangAdd.soDienThoai,
            password: $scope.donHangAdd.soDienThoai,
            hoVaTen: $scope.donHangAdd.tenNguoiNhan,
            soDienThoai: $scope.donHangAdd.soDienThoai,
            email: $scope.donHangAdd.email
        }
        $http.post("/admin/khach-hang", data).then(r => {
            var khachHangSL = document.getElementById("khachHangSL")
            var option = document.createElement("option");
            option.text = data.hoVaTen + ' - ' + data.soDienThoai;
            option.value = data.username
            khachHangSL.add(option, khachHangSL[khachHangSL.length - 1]);
            khachHangSL.value = option.value;
            document.getElementById("btnAddKh").style.display = "none";
            alertify.success("Lưu khách hàng thành công")
        }).catch(e => {
            alertify.error("Lưu khách hàng thất bại")
            if (e.data.soDienThoai != undefined) $scope.erAdd.soDienThoai = e.data.soDienThoai
            $scope.erAdd.tenNguoiNhan = e.data.hoVaTen
            $scope.erAdd.email = e.data.email
        })

        console.log(data)
    }

    ///////Hàm dùng chung
    //hủy đơn 
    $scope.id = []
    $scope.trangThaiDonHang = 6
    $scope.huyDH = function () {
        console.log($scope.trangThaiDonHang)
         if ($scope.trangThaiDonHang == 6) {
            $scope.chuaXacNhanTraHang.huyDH();
        } else if ($scope.trangThaiDonHang == 7) {
            $scope.daXacNhan.huyDH();
        }
        $scope.getSanPham()
        $scope.inputProduct = ""

    }

    /////////////////////Check Box
    $scope.setCheckAll = function (id, name) {
        console.log($scope.trangThaiDonHang)
        let setCheckbox = document.getElementById(id)

        let checkBox = document.getElementsByName(name)
        if (setCheckbox.checked == true) {
            checkBox.forEach(c => {
                c.checked = true
            })
        } else {
            checkBox.forEach(c => {
                c.checked = false
            })
        }
         if ($scope.trangThaiDonHang == 7) {
            $scope.daXacNhan.checkButton();
        } else if ($scope.trangThaiDonHang == 6) {
            $scope.chuaXacNhanTraHang.checkButton();
        }
    }
    $scope.checkAllChecked = function (name, idCheckBoxSetAll) {
        34
        let checkBox = document.getElementsByName(name)
        let check = true;
        checkBox.forEach(c => {
            if (c.checked == false) {
                check = false
            }
        })
        document.getElementById(idCheckBoxSetAll).checked = check
        if ($scope.trangThaiDonHang == 7) {
            $scope.daXacNhan.checkButton();
        } else if ($scope.trangThaiDonHang == 6) {
            $scope.chuaXacNhanTraHang.checkButton();
        }
    }

    ////////////////////////////////////////
    $scope.clearFormAdd = function () {
        $scope.donHangAdd = {
            phuongThucThanhToan: "0",
            tenNguoiNhan: "Khách lẻ",
            soDienThoai: "0000000000"
        }
        $scope.chiTietDonHang.length = 0
        $('#mySelect2').val('null').trigger('change');
        document.getElementById("khachHangSL").value = "null"
        document.getElementById("btnAddKh").style.display = "block";
    }
    $scope.themDonHang = function () {
        alertify.confirm("Tạo đơn hàng?", function () {
            let indexCity = $scope.giaoHangNhanh.citys.findIndex(c => c.ProvinceID == $scope.donHangAdd.thanhPhoCode)
            let indexDistrict = $scope.giaoHangNhanh.districts.findIndex(d => d.DistrictID == $scope.donHangAdd.quanHuyenCode)
            let indexWard = $scope.giaoHangNhanh.wards.findIndex(w => w.WardCode == $scope.donHangAdd.xaPhuongCode)

            $scope.donHangAdd.thanhPhoName = $scope.giaoHangNhanh.citys[indexCity] == undefined ? "" : $scope.giaoHangNhanh.citys[indexCity].ProvinceName;
            $scope.donHangAdd.quanHuyenName = $scope.giaoHangNhanh.districts[indexDistrict] == undefined ? "" : $scope.giaoHangNhanh.districts[indexDistrict].DistrictName;
            $scope.donHangAdd.xaPhuongName = $scope.giaoHangNhanh.wards[indexWard] == undefined ? "" : $scope.giaoHangNhanh.wards[indexWard].WardName
            console.log($scope.donHangAdd, $scope.chiTietDonHang)

            let chiTietDonHang = [];
            $scope.chiTietDonHang.forEach(c => {
                chiTietDonHang.push({
                    id: c.id,
                    donHangID: $scope.chuaXacNhan.detail.ma,
                    sanPhamCT: c.idChiTietSanPham,
                    soLuong: c.soLuong,
                    donGia: c.donGia,
                    donGiaSauGiam: c.donGiaSauGiam
                })
            })
            let formData = new FormData();
            formData.append("donHang", new Blob([JSON.stringify($scope.donHangAdd)], {
                type: 'application/json'
            }))
            formData.append("chiTietDonHang", new Blob([JSON.stringify(chiTietDonHang)], {
                type: 'application/json'
            }))
            $http.post("/admin/don-hang", formData, {
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined }
            }).then(r => {
                alertify.success("Thêm thành công")
                $scope.chuaThanhToan.init()
                $scope.chuaXacNhan.init()
                $scope.chuaThanhToan.getList($scope.chuaThanhToan.page)
                $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                $scope.donHangAdd = {
                    phuongThucThanhToan: "0",
                    tenNguoiNhan: "Khách lẻ",
                    soDienThoai: "0000000000"
                }
                $scope.chiTietDonHang.length = 0
                $('#mySelect2').val('null').trigger('change');
                document.getElementById("khachHangSL").value = "null"
                document.getElementById("btnAddKh").style.display = "block";
                $('#add').modal('hide')
                $scope.getSanPham()
                $scope.inputProduct = ""
            }).catch(e => {
                $scope.erAdd = e.data
                console.log(e)
                alertify.error("Thêm thất bại")
            })
        }, function () { })
    }
    /////////////////////////////////////////
    $scope.updateTrangThaiDonHang = function (ma, trangThai) {
        let success = true;
        $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=" + trangThai).then(r => {
            success = true;
        }).catch(e => {
            console.log(e)
            success = false
        })
        return success
    }
    ////////////////////////////////////////////
    $scope.giaoHangNhanh = {
        tokenShop: "954c787d-2876-11ee-96dc-de6f804954c9",
        headers: { headers: { token: "954c787d-2876-11ee-96dc-de6f804954c9" } },
        headersShop: { headers: { token: "954c787d-2876-11ee-96dc-de6f804954c9", ShopId: 4379549 } },
        districts: [],
        wards: [],
        getCitys() {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/province", this.headers).then(res => {
                this.citys = res.data.data
            }).catch(err => console.log(err))
        },
        getDistricts(idCity) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + idCity, this.headers).then(res => {
                this.districts = res.data.data;
            }).catch(err => console.log(err))
        },
        getWards(idDistrict) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + idDistrict, this.headers).then(res => {
                this.wards = res.data.data;
            }).catch(err => console.log(err))
        },
        cityChange(idCity) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=" + idCity, this.headers).then(res => {
                this.districts = res.data.data;
                this.wards.length = 0
            }).catch(err => console.log(err))
            // $scope.chuaXacNhan.detail.quanHuyenCode = this.districts[0].DistrictID +""
        },
        districtChange(idDistrict) {
            $http.get("https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=" + idDistrict, this.headers).then(res => {
                this.wards = res.data.data;
            }).catch(err => console.log(err))
        },
        getFeeShipped() {
            let data = {
                "service_type_id": 2,
                "to_district_id": parseInt($scope.chuaXacNhan.detail.quanHuyenCode),
                "to_ward_code": $scope.chuaXacNhan.detail.xaPhuongCode,
                "height": 10,
                "length": 10,
                "weight": 200,
                "width": 10
            }
            $http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", data, this.headersShop).then(res => {
                $scope.chuaXacNhan.detail.phiGiaoHang = res.data.data.total;
            }).catch(err => console.log(err));
        },
        getFeeShippedAdd() {
            let data = {
                "service_type_id": 2,
                "to_district_id": parseInt($scope.donHangAdd.quanHuyenCode),
                "to_ward_code": $scope.donHangAdd.xaPhuongCode,
                "height": 10,
                "length": 10,
                "weight": 200,
                "width": 10
            }
            $http.post("https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee", data, this.headersShop).then(res => {
                $scope.donHangAdd.phiGiaoHang = res.data.data.total;
            }).catch(err => console.log(err));
        }
    }
    $scope.giaoHangNhanh.getCitys()
    //////////////////////////////////////////// các trang trạng thái admin

    // trạng thái trả hàng
    $scope.chuaXacNhanTraHang = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        id: [],
        pages: [],
        sdtSearch: "",
        init() {
            $scope.trangThaiDonHang = 6
            $http.get("/admin/don-hang/get-by-trangthai-tra?trangThai=1").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })

        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 6
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai-tra?trangThai=1&pageNumber=" + pageNumber ).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                console.log("this",this.list);
                console.log(this.totalPage)
                this.setPageNumbers()
            })
        },

        xacNhanDH(ma) {
            alertify.confirm("Xác nhận đơn hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=7").then(r => {
                    if ($scope.chuaXacNhanTraHang.page == $scope.chuaXacNhanTraHang.totalPage - 1) {
                        if ($scope.chuaXacNhanTraHang.list.length == 1 && $scope.chuaXacNhanTraHang.page > 0) {
                            $scope.chuaXacNhanTraHang.page--;
                        }
                    }
                    $scope.chuaXacNhanTraHang.getList($scope.chuaXacNhanTraHang.page)
                    $scope.chuaXacNhanTraHang.init()
                    document.getElementById('checkAllChuaXacNhan').checked = false
                    $scope.daXacNhanTraHang.totalElement++
                    
                }).catch(e => {
                    alertify.success("Xác nhận thành công")
                    console.log(e)
                })

            }, function () {
                alertify.error("Xác nhận thất bại")
            })
        },
        xacNhanDHAll() {
            alertify.confirm("Xác nhận đơn hàng?", function () {
                let checkBox = document.getElementsByName('checkChuaXacNhan')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.chuaXacNhanTraHang.id.push(c.value)
                        $scope.daXacNhan.totalElement++
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=7", $scope.chuaXacNhanTraHang.id).then(r => {
                    if ($scope.chuaXacNhanTraHang.page == $scope.chuaXacNhanTraHang.totalPage - 1) {
                        if ($scope.chuaXacNhanTraHang.list.length == 1 && $scope.chuaXacNhanTraHang.page > 0) {
                            $scope.chuaXacNhanTraHang.page--;
                        }
                    }
                    $scope.chuaXacNhanTraHang.getList($scope.chuaXacNhanTraHang.page)
                    $scope.chuaXacNhanTraHang.init()
                    $scope.chuaXacNhanTraHang.id = []
                    document.getElementById('checkAllChuaXacNhan').checked = false
                    alertify.success("Xác nhận thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Xác nhận thất bại")
                })
            }, function () {
                alertify.error("Xác nhận thất bại")
            })
        },
        setIdDonHang(id) {
            this.id = []
            this.id.push(id)
        },
        setAllIdDonHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkChuaXacNhan')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huyDH() {
          if (!$scope.lyDoTraHang || $scope.lyDoTraHang.trim().length === 0) {
                $scope.messLyDo = "Không để trống lý do hủy";
                return;
            } else if ($scope.lyDoTraHang.length > 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự";
                return;
            }

            $http.put("/admin/don-hang/tra-don-hang?lyDoTraHang=" + $scope.lyDoTraHang, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page);
                this.init();
                $scope.lyDoTraHang = null;
                $scope.messLyDo = "";
                this.id = [];
                $('#closeHuy').click();
                document.getElementById('checkAllChuaXacNhanTraHang').checked = false;
              
            }).catch(e => {
                console.log(e);
                alertify.success("Hủy đơn hàng thành công");
            });
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                this.detail = r.data;
                this.detail.thanhPhoCode = this.detail.thanhPhoCode + ""

                //Lấy quận huyện
                $scope.giaoHangNhanh.getDistricts(this.detail.thanhPhoCode)//hàm lấy quận huyện truyền vào thành phố
                this.detail.quanHuyenCode = this.detail.quanHuyenCode + "" // set selected quận huyện

                $scope.giaoHangNhanh.getWards(this.detail.quanHuyenCode)//hàm lấy xã truyền vào quận huyện
                this.detail.xaPhuongCode = this.detail.xaPhuongCode + "" //set selected xã

                $('#chuaXacNhanTraHangDetail').modal('show')
                console.log(r)
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
            $http.get("/admin/don-hang/get-ctsp-tra?ma=" + ma).then(r => {
                $scope.sanPhamTra = r.data;
                console.log("spt",$scope.sanPhamTra)
            }).catch(e => console.log(e))
        },

        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >= 2 ? this.page - 2 : 0

            if (lengthLast > this.totalPage) {
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if (lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        checkButton() {
            let checkboxs = document.getElementsByName('checkChuaXacNhan')
            let check = true;
            checkboxs.forEach(c => {
                if (c.checked == true) {
                    check = false;
                }
            })
            document.getElementById("huyAll1").disabled = check;
            document.getElementById("xacNhanAll1").disabled = check;
        }
    }
    $scope.chuaXacNhanTraHang.init()
    $scope.chuaXacNhanTraHang.getList(0)

    $scope.daXacNhan = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        id: [],
        sdtSearch: "",
        init() {
            $scope.trangThaiDonHang = 7
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=7").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 7
            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=7&pageNumber=" + pageNumber + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {

            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >= 2 ? this.page - 2 : 0

            if (lengthLast > this.totalPage) {
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if (lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        chuyenGiao(ma) {
            alertify.confirm("Hoàn tiền đơn hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=8").then(r => {
                    if ($scope.daXacNhan.page == $scope.daXacNhan.totalPage - 1) {
                        if ($scope.daXacNhan.list.length == 1 && $scope.daXacNhan.page > 0) {
                            $scope.daXacNhan.page--;
                        }
                    }
                    $scope.daXacNhan.getList($scope.daXacNhan.page)
                    $scope.daXacNhan.init()
                    document.getElementById('checkAllDaXacNhan').checked = false
                }).catch(e => {
                    console.log(e)
                    alertify.success("Hoàn tiền thành công")
                })
            }, function () {
                alertify.error("Hoàn tiền thất bại")
            })
        },
        setIdDonHang(id) {
            this.id = []
            this.id.push(id)
        },
        setAllIdDonHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkDaXacNhan')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huyDH() {
            if (!$scope.lyDoTraHang || $scope.lyDoTraHang.trim().length === 0) {
                $scope.messLyDo = "Không để trống lý do hủy";
                return;
            } else if ($scope.lyDoTraHang.length > 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự";
                return;
            }

            $http.put("/admin/don-hang/tra-don-hang?lyDoTraHang=" + $scope.lyDoTraHang, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page);
                this.init();
                $scope.lyDoTraHang = null;
                $scope.messLyDo = "";
                this.id = [];
                $('#closeHuy').click();
                document.getElementById('checkAllDaXacNhanTraHang').checked = false;
            }).catch(e => {
                console.log(e);
                alertify.success("Hủy đơn hàng thành công");
            });           
        },
        chuyenGiaoDHAll() {

            alertify.confirm("Chuyển giao đơn hàng?", function () {
                let checkBox = document.getElementsByName('checkDaXacNhan')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.daXacNhan.id.push(c.value)
                        // $scope.dangGiao.totalElement++
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=8", $scope.daXacNhan.id).then(r => {
                    if ($scope.daXacNhan.page == $scope.daXacNhan.totalPage - 1) {
                        if ($scope.daXacNhan.list.length == 1 && $scope.daXacNhan.page > 0) {
                            $scope.daXacNhan.page--;
                        }
                    }
                    $scope.daXacNhan.getList($scope.daXacNhan.page)
                    $scope.daXacNhan.init()
                    $scope.daXacNhan.id = []
                    document.getElementById('checkAllDaXacNhan').checked = false

                   
                }).catch(e => {
                    console.log(e)
                    alertify.success("Chuyển giao thành công")
                })
            }, function () {
                alertify.error("Chuyển giao thất bại")
            })
        },
        checkButton() {
            let checkboxs = document.getElementsByName('checkDaXacNhan')
            let check = true;
            checkboxs.forEach(c => {
                if (c.checked == true) {
                    check = false;
                }
            })
            document.getElementById("huyAll2").disabled = check;
            document.getElementById("xacNhanAll2").disabled = check;
        }
    }
    $scope.daXacNhan.init()

    $scope.hoanThanhTraHang = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        sdtSearch: "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=8&pageNumber=" + this.page + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 0
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=8&pageNumber=" + pageNumber + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >= 2 ? this.page - 2 : 0

            if (lengthLast > this.totalPage) {
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if (lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;

        }
    }

    $scope.huyTraHang = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        sdtSearch: "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=9&pageNumber=" + this.page + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 9
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=9&pageNumber=" + pageNumber + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >= 2 ? this.page - 2 : 0

            if (lengthLast > this.totalPage) {
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if (lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }
    $scope.trangThaiDonHang = 9
    //trả hàng 
    $scope.trahang = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        sdtSearch: "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&pageNumber=" + this.page + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 0
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&pageNumber=" + pageNumber + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                $scope.donHang = r.data;
                $('#donHangDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >= 2 ? this.page - 2 : 0

            if (lengthLast > this.totalPage) {
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if (lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }
    $scope.trangThaiDonHang = 6
    //don hang user
    $scope.donHangChuaXacNhanKh = []
    $scope.donHangUser = function (trangThai) {
        $http.get("/don-hang/get-by-trangThai-khachHang?trangThai=" + trangThai).then(resp => {
            $scope.donHangChuaXacNhanKh = resp.data;
            console.log(resp.data)
        }).catch(err => {
            console.log(err);
        })
    }
    //
    $scope.loadMultipleDonHangUsers = function() {
        let promises = [];
        let trangThais = [6, 7, 8, 9];

        trangThais.forEach(trangThai => {
            promises.push(
                $http.get("/don-hang/get-by-trangThai-khachHang?trangThai=" + trangThai).then(resp => {
                    return resp.data;
                }).catch(err => {
                    console.log(err);
                    return [];
                })
            );
        });

        Promise.all(promises).then(results => {
            // Hợp nhất kết quả từ các trạng thái khác nhau
            $scope.donHangChuaXacNhanKh = results.flat();
            console.log($scope.donHangChuaXacNhanKh);
            // Đảm bảo giao diện được cập nhật
            $scope.$applyAsync();
        });
    };

    $scope.donHangUser(5)

    $scope.findByMaDonHangUser = function (ma) {
        $http.get("/don-hang/" + ma).then(function (res) {
            const donHang = res.data;
            $scope.maDonHang = donHang.ma
            $scope.nguoiNhan = donHang.tenNguoiNhan
            $scope.soDT = donHang.soDienThoai
            $scope.email = donHang.email
            $scope.ghiChu = donHang.ghiChu
            $scope.diaChiCT = donHang.diaChiChiTiet
            $scope.tienGiam = donHang.tienGiam
            $scope.phiGiaoHang = donHang.phiGiaoHang
            $scope.lyDoHuy = donHang.lyDoHuy
            $scope.lyDoTraHang = donHang.lyDoTraHang
            $scope.chiTietDon = donHang.chiTietDonHang
        })
    }
    $scope.huyDonUser = function (ma) {
        lyDoHuy = $scope.lyDoHuy
        $http.put("/don-hang/huy-don-hang-user?ma=" + ma, lyDoHuy).then(function (res) {
            // location.reload()
            let index = $scope.donHangChuaXacNhanKh.findIndex(d => d.ma == ma)
            $scope.donHangChuaXacNhanKh.splice(index, 1);
            alertify.success("Hủy đơn hàng thành công")
        })
    }
    // $scope.traDonUser = function (ma) {
    //     lydoTraHang = $scope.lyDoTraHang
    //     $http.put("/don-hang/tra-don-hang-user?ma=" + ma, lyDoTraHang).then(function (res) {
    //         // location.reload()
    //         let index = $scope.donHangChuaXacNhanKh.findIndex(d => d.ma == ma)
    //         $scope.donHangChuaXacNhanKh.splice(index, 1);
    //         alertify.success("Yêu cầu trả đơn hàng thành công")
    //     })
    // }
    // Khai báo biến lyDoTraHang trong scope
    $scope.lyDoTraHang = "";

    // Định nghĩa hàm traDonUser
    $scope.traDonUser = function (ma) {
        const lydoTraHang = $scope.lyDoTraHang; // Sử dụng let hoặc const để khai báo biến cục bộ

        // Kiểm tra nếu lydoTraHang tồn tại và không rỗng
        if (!lydoTraHang) {
            alertify.error("Bạn cần nhập lý do trả hàng");
            return;
        }

        // Gửi yêu cầu HTTP PUT
        $http.put("/don-hang/tra-don-hang-user?ma=" + ma, lydoTraHang)
            .then(function (res) {
                // Tìm và xóa đơn hàng khỏi danh sách
                let index = $scope.donHangChuaXacNhanKh.findIndex(d => d.ma == ma);
                if (index !== -1) {
                    $scope.donHangChuaXacNhanKh.splice(index, 1);
                }
                alertify.success("Yêu cầu trả đơn hàng thành công");
            })
            .catch(function (error) {
                // Xử lý lỗi khi gửi yêu cầu HTTP
                console.error("Có lỗi xảy ra khi gửi yêu cầu trả đơn hàng:", error);
                alertify.error("Có lỗi xảy ra khi gửi yêu cầu trả đơn hàng");
            });
    };

    $scope.setChiTietDH = function (maCTDH) {
        console.log(maCTDH);
        $scope.danhGia.chiTietDonHang = maCTDH
    }
    $scope.danhGia = {}
    $scope.erDanhGia = {}
    $scope.addDanhGia = function () {
        alertify.confirm("Đánh giá sản phẩm?", function () {
            $http.post("/nhan-xet", $scope.danhGia).then(r => {
                $('#danhGia').modal('hide')
                $scope.donHangChuaXacNhanKh.forEach(d => {
                    d.chiTietDonHang.forEach(c => {
                        if (c.id == r.data.idCTDH) {
                            c.nhanXet = r.data
                        }
                    })
                })
                alertify.success("Đánh giá sản phẩm thành công")
                $scope.donHangUser(4)
                $scope.danhGia = {}
            }).catch(e => {
                alertify.error("Đánh giá sản phẩm thất bại")
                $scope.erDanhGia = e.data
            })
        }, function () {
            alertify.error("Đánh giá sản phẩm thất bại")
        })
    }
    $scope.danhGiaUpdate = {}
    $scope.erDanhGiaUpdate = {}
    $scope.viewUpdateDanhGia = function (id) {
        $http.get("/nhan-xet/detail/" + id).then(r => {
            $scope.danhGiaUpdate = r.data
            console.log($scope.danhGiaUpdate)
            document.getElementById("formDanhGiapdateUpdate").className = "ng-valid ng-dirty ng-valid-parse"
            document.getElementById("star-" + $scope.danhGiaUpdate.rating + "update").className = "star star-3 ng-untouched ng-valid ng-not-empty ng-dirty ng-valid-parse"
        })
    }
    $scope.updateDanhGia = function () {
        alertify.confirm("Bạn chỉ được một lần cập nhật đánh giá sản phẩm. Bạn có chắc muốn chỉnh sửa?", function () {
            let data = {
                id: $scope.danhGiaUpdate.id,
                chiTietDonHang: $scope.danhGiaUpdate.idCTDH,
                rating: $scope.danhGiaUpdate.rating,
                tieuDe: $scope.danhGiaUpdate.tieuDe,
                noiDung: $scope.danhGiaUpdate.noiDung
            }
            $http.put("/nhan-xet", data).then(r => {
                $('#danhGiaUpdate').modal('hide')
                alertify.success("Cập nhật đánh giá sản phẩm thành công")
                $scope.danhGia = {}
            }).catch(e => {
                $scope.erDanhGiaUpdate = e.data
                alertify.error("Cập nhật đánh giá sản phẩm thất bại")
            })
        }, function () {
            alertify.error("Cập nhật đánh giá sản phẩm thất bại")
        })

    }
    $scope.thanhToan = function (ma) {
        $http.get("/thanh-toan/" + ma).then(r => {
            location.href = r.data.vnPayUrl
        })
    }


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

    $scope.login = function () {
        var expires = (new Date(Date.now() + 60 * 1000)).toUTCString();
        document.cookie = "url=" + window.location.href + "; expires=" + expires;
        location.href = "/dang-nhap";
    }
})
$('#khachHangSL').select2({
    dropdownParent: $('#add')
});