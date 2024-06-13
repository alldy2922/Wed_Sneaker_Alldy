var app = angular.module("banhang-app", [])
app.controller("banhang-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.donHangAdd = {
        phuongThucThanhToan: "0",
        tenNguoiNhan : "Khách lẻ",
        soDienThoai : "0000000000"
    }
    $scope.chiTietDonHang = []
    $scope.sanPham = [];
    const limit = 10;
    $scope.er = {}
    $scope.dateNow = new Date().getTime();
    $scope.khachHang = []
    $scope.erAdd = {}


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
    // $scope.getSanPham = function () {
    //     $http.get("/admin/san-pham/1/get-all-ctsp").then(r => {
    //         $scope.sanPham = r.data
    //     }).catch(e => console.log(e))
    // }
    // $scope.getSanPham()
    // $scope.addChiTietDonHang = function (item) {
    //     $scope.chiTietDonHang.push({
    //         sanPham: item.sanPham,
    //         anh: item.sanPhamDTO.anh.length == 0 ? "default.png" : item.sanPhamDTO.anh[0],
    //         size: item.size,
    //         soLuong: 1,
    //         donGia: item.sanPhamDTO.giaBan,
    //         donGiaSauGiam: item.sanPhamDTO.giaNiemYet,
    //         idChiTietSanPham: item.id
    //     })
    //     $scope.er.soLuongSP = ""
    // }
    // $scope.searchSanPham = function () {
    //     $http.get("/admin/san-pham/1/get-all-ctsp?keyWord=" + $scope.inputProduct).then(r => {
    //         $scope.sanPham = r.data
    //     }).catch(e => console.log(e))
    // }
    // $scope.checkSanPhamInDonHang = function (idCTSP) {
    //     let result = false;
    //     $scope.chiTietDonHang.forEach(d => {
    //         if (d.idChiTietSanPham == idCTSP) {
    //             result = true;
    //         }
    //     })
    //     return result;
    // }

    /////////////////////////////////////// test
        $scope.getSanPham = function () {
        $http.get("/admin/san-pham/1/get-all-ctsp").then(r => {
            $scope.sanPham = r.data
        }).catch(e => console.log(e))
    }
    $scope.getSanPham()
    $scope.addChiTietDonHang = function (item) {
        let existingItem = $scope.chiTietDonHang.find(d => d.idChiTietSanPham == item.id);
    
        if (existingItem) {
            // Nếu sản phẩm đã có trong danh sách, cộng dồn số lượng
            if (existingItem.soLuong < item.soLuong) {
                existingItem.soLuong += 1;
            } else {
                alert("Không đủ số lượng sản phẩm.");
            }
        } else {
            // Nếu sản phẩm chưa có trong danh sách, thêm mới
            if (item.soLuong > 0) {
                $scope.chiTietDonHang.push({
                    sanPham: item.sanPham,
                    anh: item.sanPhamDTO.anh.length == 0 ? "default.png" : item.sanPhamDTO.anh[0],
                    size: item.size,
                    soLuong: 1,
                    donGia: item.sanPhamDTO.giaBan,
                    donGiaSauGiam: item.sanPhamDTO.giaNiemYet,
                    idChiTietSanPham: item.id
                });
            } else {
                alert("Sản phẩm đã hết hàng.");
            }
        }
    
        $scope.er.soLuongSP = "";
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
    
    ////////////////////////end test
    $scope.themDonHang = function (trangThai){
        alertify.confirm("Tạo đơn hàng?", function () {
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
            $scope.donHangAdd.email = "quocthanh2929@gmail.com"
            $scope.donHangAdd.thanhPhoName = "a"
            $scope.donHangAdd.thanhPhoCode = 1
            $scope.donHangAdd.quanHuyenName = "a"
            $scope.donHangAdd.quanHuyenCode = 1
            $scope.donHangAdd.xaPhuongName = "a"
            $scope.donHangAdd.xaPhuongCode = "12"
            $scope.donHangAdd.diaChiChiTiet =  "a"
            $scope.donHangAdd.loai = 1
            $scope.donHangAdd.trangThai = trangThai;
            $scope.donHangAdd.tongTien = $scope.getTotalPrice()*100
            let formData = new FormData();
            formData.append("donHang", new Blob([JSON.stringify($scope.donHangAdd)], {
                type: 'application/json'
            }))
            formData.append("chiTietDonHang", new Blob([JSON.stringify(chiTietDonHang)], {
                type: 'application/json'
            }))
            $http.post("/admin/don-hang", formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(r => {
                $scope.chuaXacNhan.init()
                $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                if (r.data.vnPayUrl != undefined){
                    location.href = r.data.vnPayUrl
                }
                $scope.donHangAdd = {
                    phuongThucThanhToan : "0",
                    tenNguoiNhan : "Khách lẻ",
                    soDienThoai : "0000000000"
                }
                $scope.chiTietDonHang.length = 0
                $scope.getSanPham()
                $scope.inputProduct = ""
                $('#mySelect2').val('null').trigger('change');
                document.getElementById("khachHangSL").value = "null"
                $('#add').modal('hide')
                alertify.success("Thêm thành công")
            }).catch(e => {
                $scope.erAdd = e.data
                console.log(e)
                alertify.error("Thêm thất bại")
            })
        },function (){})
    }
    $scope.clearFormAdd=function (){
        $scope.donHangAdd = {
            phuongThucThanhToan : "0",
            tenNguoiNhan : "Khách lẻ",
            soDienThoai : "0000000000"
        }
        $scope.chiTietDonHang.length = 0
        $('#mySelect2').val('null').trigger('change');
        document.getElementById("khachHangSL").value = "null"
        document.getElementById("btnAddKh").style.display = "block";
    }
    /////////////////////////////////////////
    ///////////////////////////////////////
    //Get Khách Hàng
    $scope.keyWordKhachHang = ""
    $scope.getAllKhachHang = function (){
        $http.get("/admin/khach-hang/get-all-khach-hang?limit=1000&&keyWord="+$scope.keyWordKhachHang).then(r =>{
            $scope.khachHang = r.data.content;
        }).catch(e => console.log(e))
    }
    $scope.getAllKhachHang()
    $scope.addKhachHangToDonHang = function (){
        let value = document.getElementById("khachHangSL").value
        if(value!='null'){

            $http.get("/admin/khach-hang/detail/"+value).then(r => {
                $scope.donHangAdd.tenNguoiNhan = r.data.hoVaTen
                $scope.donHangAdd.email = r.data.email
                $scope.donHangAdd.soDienThoai = r.data.soDienThoai
            })

            $scope.donHangAdd.nguoiSoHuu={
                username : value
            }
            document.getElementById("btnAddKh").style.display = "none";
        }else{
            $scope.donHangAdd = {
                phuongThucThanhToan : $scope.donHangAdd.phuongThucThanhToan,
                tenNguoiNhan : "Khách lẻ",
                soDienThoai : "0000000000"
            }
            document.getElementById("btnAddKh").style.display = "block";

        }
    }
    $scope.addKhachHang = function (){
        var data = {
            username: $scope.donHangAdd.soDienThoai,
            password: $scope.donHangAdd.soDienThoai,
            hoVaTen: $scope.donHangAdd.tenNguoiNhan,
            soDienThoai: $scope.donHangAdd.soDienThoai,
            email: "quocthanh2929@gmail.com"
        }
        $http.post("/admin/khach-hang",data).then(r => {
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
            if(e.data.soDienThoai != undefined) $scope.erAdd.soDienThoai = e.data.soDienThoai
            $scope.erAdd.tenNguoiNhan = e.data.hoVaTen
        })

        console.log(data)
    }

    //////////////////////////////////////////////////////
    $scope.chuaXacNhan = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        id: [],
        pages: [],
        sdtSearch : "",
        init() {
            $scope.trangThaiDonHang = 2
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=5&loai=1").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })

        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 2
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=5&loai=1&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        xacNhanDH() {
            alertify.confirm("Xác nhận thanh toán đơn hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + $scope.chuaXacNhan.detail.ma + "?trangThai=4").then(r => {
                    if ($scope.chuaXacNhan.page == $scope.chuaXacNhan.totalPage - 1) {
                        if ($scope.chuaXacNhan.list.length == 1 && $scope.chuaXacNhan.page > 0) {
                            $scope.chuaXacNhan.page--;
                        }
                    }
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                    $scope.chuaXacNhan.init()
                    document.getElementById('checkAllChuaXacNhan').checked = false
                    $("#chuaXacNhanDetail").modal("hide")
                    if (r.data.vnPayUrl != undefined){
                        location.href = r.data.vnPayUrl
                    }
                    alertify.success("Xác nhận thanh toán thành công")
                }).catch(e => {
                    alertify.error("Xác nhận thanh toán thất bại")
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
                        $scope.chuaXacNhan.id.push(c.value)
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=4", $scope.chuaXacNhan.id).then(r => {
                    if ($scope.chuaXacNhan.page == $scope.chuaXacNhan.totalPage - 1) {
                        if ($scope.chuaXacNhan.list.length == 1 && $scope.chuaXacNhan.page > 0) {
                            $scope.chuaXacNhan.page--;
                        }
                    }
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                    $scope.chuaXacNhan.init()
                    $scope.chuaXacNhan.id = []
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

            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do hủy"
                alertify.error("Hủy đơn hàng thất bại")
                return
            } else if ($scope.lyDo.length == 200) {
                $scope.messLyDo = "Lý do hủy chỉ tối đa 200 ký tự"
                alertify.error("Hủy đơn hàng thất bại")
                return;
            }

            $http.put("/admin/don-hang/huy-don-hang?lyDo=" + $scope.lyDo, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = "";
                this.id = []
                $('#closeHuy').click()
                document.getElementById('checkAllChuaXacNhan').checked = false
                alertify.success("Hủy đơn hàng thành công")
                $scope.getSanPham()
                $scope.inputProduct = ""
            }).catch(e => {
                alertify.error("Hủy đơn hàng thất bại")
                console.log(e)
            })
        },
        getDetail(ma) {
            $http.get("/admin/don-hang/" + ma).then(r => {
                this.detail = r.data;
                $('#chuaXacNhanDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
        capNhat() {
            alertify.confirm("Cập nhật đơn hàng?", function () {
                let data = {
                    ma: $scope.chuaXacNhan.detail.ma,
                    nguoiSoHuu: {username: $scope.chuaXacNhan.detail.nguoiSoHuu},
                    tenNguoiNhan: $scope.chuaXacNhan.detail.tenNguoiNhan,
                    soDienThoai: $scope.chuaXacNhan.detail.soDienThoai,
                    email: "baphuoc03@gmail.com",
                    thanhPhoName: "a",
                    thanhPhoCode: 1,
                    quanHuyenName: "a",
                    quanHuyenCode: 1,
                    xaPhuongName: "a",
                    xaPhuongCode: "12",
                    ngayDatHang: $scope.chuaXacNhan.detail.ngayDatHang,
                    trangThai: $scope.chuaXacNhan.detail.trangThai,
                    diaChiChiTiet : "a",
                    phuongThucThanhToan : $scope.chuaXacNhan.detail.phuongThucThanhToan =='true' ? 0 : 1,
                    nhanVien : $scope.chuaXacNhan.detail.nhanVienDtoResponse == null ? null :  $scope.chuaXacNhan.detail.nhanVienDtoResponse.username
                }
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
                formData.append("donHang", new Blob([JSON.stringify(data)], {
                    type: 'application/json'
                }))
                formData.append("chiTietDonHang", new Blob([JSON.stringify(chiTietDonHang)], {
                    type: 'application/json'
                }))
                console.log(data)
                $http.put("/admin/don-hang", formData, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(r => {
                    let index = $scope.chuaXacNhan.list.findIndex(d => d.ma == $scope.chuaXacNhan.detail.ma)
                    $scope.chuaXacNhan.list[index] = $scope.chuaXacNhan.detail
                    alertify.success("Cập nhật thành công")
                    $scope.getSanPham()
                    $scope.inputProduct = ""
                    $scope.chuaXacNhan.init()
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                }).catch(e => {
                    $scope.er = e.data
                    console.log(e)
                    alertify.error("Cập nhật thất bại")
                })
            }, function () {
                alertify.error("Cập nhật thất bại")
            })

        },
        setPageNumbers() {
            let numbers = [];
            let i = this.page
            let lengthLast = this.totalPage <= 3 ? this.totalPage : this.page + 3
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        },
        updateSoLuong(idCTSP, soLuong) {
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            $http.get("/admin/san-pham/1/kiem-tra-so-luong/" + idCTSP + "?soLuong=" + soLuong + "&idCTDH=" + (chiTietDonHang.id == undefined ? "" : chiTietDonHang.id)).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
                $scope.getTotalPrice()
            }).catch(e => {
                if (chiTietDonHang.id == undefined) {
                    chiTietDonHang.soLuong = 1
                } else {
                    $http.get("/admin/chi-tiet-don-hang/detail/" + chiTietDonHang.id).then(r => {
                        $scope.chiTietDonHang[index].soLuong = r.data.soLuong
                    }).catch(e => console.log(e))
                }
                alertify.error("số lượng đã vượt quá số lượng sản phẩm!")
            })


        },
        subtractSoLuong(idCTSP) {
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            let soLuong = chiTietDonHang.soLuong - 1


            $http.get("/admin/san-pham/1/kiem-tra-so-luong/" + chiTietDonHang.idChiTietSanPham + "?soLuong=" + soLuong + "&idCTDH=" + (chiTietDonHang.id == undefined ? "" : chiTietDonHang.id)).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
            }).catch(e => {
                alertify.error("số lượng sản phẩm đã đạt giá trị tối thiểu")
            })


        },
        addSoLuong(idCTSP) {
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP)
            let chiTietDonHang = $scope.chiTietDonHang[index]
            let soLuong = parseInt(chiTietDonHang.soLuong) + 1

            $http.get("/admin/san-pham/1/kiem-tra-so-luong/" + chiTietDonHang.idChiTietSanPham + "?soLuong=" + soLuong + "&idCTDH=" + (chiTietDonHang.id == undefined ? "" : chiTietDonHang.id)).then(r => {
                $scope.chiTietDonHang[index].soLuong = soLuong
            }).catch(e => {
                alertify.error("số lượng sản phẩm đã đạt giá trị tối đa")
            })

        },
        removeSanPham(idCTDH) {
            let index = $scope.chiTietDonHang.findIndex(c => c.id == idCTDH)
            $scope.chiTietDonHang.splice(index, 1)
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
    $scope.chuaXacNhan.init()
    $scope.chuaXacNhan.getList(0)

    $scope.hoanThanh = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        sdtSearch : "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=4&loai=1&pageNumber=" + this.page+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 0
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=4&loai=1&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
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
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;

        }
    }

    $scope.huy = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        sdtSearch : "",
        init() {
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&loai=1&pageNumber=" + this.page+"&sdt="+this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },
        getList(pageNumber) {
            $scope.trangThaiDonHang = 0
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=0&loai=1&pageNumber=" + pageNumber+"&sdt="+this.sdtSearch).then(r => {
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
            let lengthFirst = this.totalPage >=2 ?  this.page - 2 : 0

            if(lengthLast>this.totalPage){
                lengthLast = this.totalPage
                i = lengthLast - 2
            }
            if(lengthFirst < 0) lengthFirst = 0

            for (lengthFirst; i > lengthFirst; lengthFirst++) {
                numbers.push(lengthFirst)
            }
            for (i; i < lengthLast; i++) {
                numbers.push(i)
            }
            this.pages = numbers;
        }
    }


    ///////Hàm dùng chung
    $scope.id = []
    $scope.trangThaiDonHang = 2
    $scope.huyDH = function () {
        console.log($scope.trangThaiDonHang)
        if ($scope.trangThaiDonHang == 1) {
            $scope.daXacNhan.huyDH();
        } else if ($scope.trangThaiDonHang == 2) {
            $scope.chuaXacNhan.huyDH();
        } else if ($scope.trangThaiDonHang == 3) {
            $scope.dangGiao.huyDH();
        }else if ($scope.trangThaiDonHang == 5) {
            $scope.chuaThanhToan.huyDH();
        }

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
        if ($scope.trangThaiDonHang == 1) {
            $scope.daXacNhan.checkButton();
        } else if ($scope.trangThaiDonHang == 2) {
            $scope.chuaXacNhan.checkButton()
        } else if ($scope.trangThaiDonHang == 3) {
            $scope.dangGiao.checkButton();
        }else if ($scope.trangThaiDonHang == 5) {
            $scope.chuaThanhToan.checkButton();
        }
    }
    $scope.checkAllChecked = function (name, idCheckBoxSetAll) {
        let checkBox = document.getElementsByName(name)
        let check = true;
        checkBox.forEach(c => {
            if (c.checked == false) {
                check = false
            }
        })
        document.getElementById(idCheckBoxSetAll).checked = check
        if ($scope.trangThaiDonHang == 1) {
            $scope.daXacNhan.checkButton();
        } else if ($scope.trangThaiDonHang == 2) {
            $scope.chuaXacNhan.checkButton()
        } else if ($scope.trangThaiDonHang == 3) {
            $scope.dangGiao.checkButton();
        }else if ($scope.trangThaiDonHang == 5) {
            $scope.chuaThanhToan.checkButton();
        }
    }

})