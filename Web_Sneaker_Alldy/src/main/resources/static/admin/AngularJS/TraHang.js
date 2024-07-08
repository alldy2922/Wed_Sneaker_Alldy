var app = angular.module("trahang-app", [])
app.controller("trahang-ctrl", function ($scope, $http) {
    $scope.trahang = {}
    $scope.trahangAdd = {
        phuongThucThanhToan: "0"
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
        $scope.trahang = {}
    }

    $scope.getSanPham = function () {
        $http.get("/admin/san-pham/1/get-all-ctsp").then(r => {
            $scope.sanPham = r.data
        }).catch(e => console.log(e))
    }
    $scope.getSanPham()

    $scope.checkSanPhamTrongDonHang = function (idCTSP) {
        let result = false;
        $scope.chiTietDonHang.forEach(d => {
            if (d.idChiTietSanPham == idCTSP) {
                result = true;
            }
        })
        return result;
    }

    //Get khách hàng
    $scope.keyWordKH = ""
    $scope.getAllKhachHang = function () {
        $http.get("/admin/khach-hang/get-all-khach-hang?limit=1000&&keyWord=" + $scope.keyWordKH).then(r => {
            $scope.khachHang = r.data.content;
        }).catch(e => console.log(e))
    }
    $scope.getAllKhachHang()

    //Hàm trạng thái dùng chung
    $scope.id = []
    $scope.trangThaiTraHang = 1

    //Checkbox
    $scope.setCheckAll = function (id, name) {
        console.log($scope.trangThaiTraHang)
        let setCheckbox = document.getElementById(id)

        let checkBox = document.getElementsByName(name)
        if (setCheckbox.checked = true) {
            checkBox.forEach(c => {
                c.checked = true
            })
        } else {
            checkBox.forEach(c => {
                c.checked = false
            })
        }
        if ($scope.trangThaiTraHang == 1) {
            $scope.daXacNhanTH.checkButton();
        } else if ($scope.trangThaiTraHang == 2) {
            $scope.chuaXacNhanTH.checkButton();
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
        if ($scope.trangThaiTraHang == 1) {
            $scope.daXacNhanTH.checkButton();
        } else if ($scope.trangThaiTraHang == 2) {
            $scope.chuaXacNhanTH.checkButton();
        }
    }

    ////////////////////////////

    $scope.chuaXacNhanTH = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        id: [],
        pages: [],
        sdtSearch: "",
        init() {
            $scope.trangThaiTraHang = 2
            $http.get("/admin/tra-hang/get-by-trangthai?trangThai=2").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },

        getList(pageNumber) {
            $scope.trangThaiTraHang = 2
            this.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=2&pageNumber=" + pageNumber + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },

        setIdTraHang(id) {
            this.id = [];
            this.id.push(id)
        },

        setAllIdTraHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkChuaXacNhan')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },

        huyTraHang() {
            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do"
                alertify.error("Huỷ đơn hàng thất bại")
                return
            } else if ($scope.lyDo.length == 200) {
                $scope.messLyDo = "Lý do huỷ chỉ tối đa 200 ký tự"
                alertify.error("Huỷ đơn hàng thất bại")
                return;
            }
            $http.put("admin/tra-hang/huy-tra-hang?lyDo=" + $scope.lyDo, this.id).then(r => {
                if (this.list.length == 1 && this.page > 0) {
                    this.page--;
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = "";
                this.id = []
                $('#closeHuy').click()
                document.getElementById('checkAllChuaXacNhan').checked = false
                alertify.success("Huỷ trả hàng thành công")
            }).catch(e => {
                alertify.error("Huỷ đơn hàng thất bại")
                console.log(e)
            })
        },

        xacNhanTraHang(ma) {
            alertify.confirm("Xác nhận trả hàng?", function () {

                $http.get("/admin/don-hang/update-trang-thai/" + ma + "?trangThai=1").then(r => {
                    if ($scope.chuaXacNhan.page == $scope.chuaXacNhan.totalPage - 1) {
                        if ($scope.chuaXacNhan.list.length == 1 && $scope.chuaXacNhan.page > 0) {
                            $scope.chuaXacNhan.page--;
                        }
                    }
                    $scope.chuaXacNhan.getList($scope.chuaXacNhan.page)
                    $scope.chuaXacNhan.init()
                    document.getElementById('checkAllChuaXacNhan').checked = false
                    $scope.daXacNhan.totalElement++
                    alertify.success("Xác nhận thành công")
                }).catch(e => {
                    alertify.error("Xác nhận thất bại")
                    console.log(e)
                })

            }, function () {
                alertify.error("Xác nhận thất bại")
            })
        },

        xacNhanTraHangAll() {
            alertify.confirm("Xác nhận trả tất cả?", function () {
                let checkBox = document.getElementsByName('checkChuaXacNhan')
                checkBox.forEach(c => {
                    if (c.checked == true) {
                        $scope.chuaXacNhan.id.push(c.value)
                        $scope.daXacNhan.totalElement++
                    }
                })

                $http.put("/admin/don-hang/update-trang-thai?trangThai=1", $scope.chuaXacNhan.id).then(r => {
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

                $('#chuaXacNhanDetail').modal('show')
            }).catch(e => console.log(e))

            $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
                $scope.chiTietDonHang = r.data;
            }).catch(e => console.log(e))
        },
    }
    $scope.chuaXacNhan.init()
    $scope.chuaXacNhan.getList(0)

    $scope.daXacNhanTH = {
        list: [],
        detail: {},
        totalElement: 0,
        totalPage: 0,
        page: 0,
        pages: [],
        id: [],
        sdtSearch: "",
        init() {
            $scope.trangThaiTraHang = 1
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1").then(r => {
                this.totalElement = r.data.totalElements;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },

        getList(pageNumber) {
            $scope.trangThaiTraHang = 1
            $scope.daXacNhan.page = pageNumber;
            $http.get("/admin/don-hang/get-by-trangthai?trangThai=1&pageNumber=" + pageNumber + "&sdt=" + this.sdtSearch).then(r => {
                this.list = r.data.content;
                this.totalPage = r.data.totalPages;
                this.setPageNumbers()
            })
        },

        getDetail(ma) {
            $http.get("/admin/tra-hang/" + ma).then(r => {
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
        },

        setIdTraHang(id) {
            this.id = []
            this.id.push(id)
        },

        setAllIdTraHang() {
            this.id = []
            let checkBox = document.getElementsByName('checkDaXachan')
            checkBox.forEach(c => {
                if (c.checked == true) {
                    this.id.push(c.value)
                }
            })
        },
        huy(ma) {
            alertify.confirm("Huỷ đơn hàng ?", function () {
                $http.get("/admin/tra-hang/update-trang-thai/" + ma + "?trangThai = 2").then(r => {
                    if ($scope.daXacNhanTH.page == $scope.daXacNhanTH.totalPage - 1) {
                        if ($scope.daXacNhanTH.list.length == 1 && $scope.daXacNhanTH.page > 0) {
                            $scope.daXacNhanTH.page--
                        }
                    }
                    $scope.daXacNhanTH.getList($scope.daXacNhanTH.page)
                    $scope.daXacNhanTH.init()
                    alertify.success("Huỷ thành công")
                }).catch(e => {
                    console.log(e)
                    alertify.error("Huỷ thất bại")
                })
            }, function () {
                alertify.error("Huỷ thất bại")
            })
        },

        HuyTraHang() {
            if ($scope.lyDo == null || $scope.length == 0 || $scope.lyDo == undefined) {
                $scope.messLyDo = "Không để trống lý do huỷ"
                return
            } else if ($scope.lyDo.length > 200) {
                $scope.messLyDo = "Lý do huỷ chỉ tối đa 200 ký tự"
                return;
            }

            $http.put("/admin/tra-hang/huy-tra-hang?lyDo =" + $scope.lyDo, this.id).then(r => {
                if (this.page == this.totalPage - 1) {
                    if (this.list.length == 1 && this.page > 0) {
                        this.page--;
                    }
                }
                this.getList(this.page)
                this.init()
                $scope.lyDo = null;
                $scope.messLyDo = ""
                this.id = []
                $('#closeHuy').click
                document.getElementById('checkAlldaXacNhan').checked = false
                alertify.success("Huỷ trả hàng thành công")
            }).catch(e => {
                console.log(e)
                alertify.error("Huỷ trả hàng thất bại")
            })
        }

    }
    $scope.daXacNhan.init()
})