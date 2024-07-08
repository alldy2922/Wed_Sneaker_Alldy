var app = angular.module("banhang-app", [])
app.controller("banhang-ctrl", function ($scope, $http) {
    $scope.donHang = {}
    $scope.donHangAdd = {
        phuongThucThanhToan: "0",
        tenNguoiNhan : "Khách lẻ",
        soDienThoai : "0000000000"
    }
    $scope.chiTietDonHang = []
     $scope.sanPhamCT = [];
    $scope.soLuong = ""
    $scope.selectedProduct = [];
    $scope.selectedSize =[];
    const limit = 10;
    $scope.productDetails = [];
    $scope.products = [];
    $scope.er = {}
    $scope.dateNow = new Date().getTime();
    $scope.khachHang = []
    $scope.erAdd = {}
    $scope.sanPhamTable = [];
    var isfilter = false;

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
    $scope.sanPham = [];

    $scope.getSanPham = function() {
        $http.get("/admin/san-pham/get-all")
            .then(function(response) {
                $scope.products = response.data.content;
                $scope.getPageNumbers(response.data.totalPages);
                $scope.filterData = {};
            })
            .catch(function(error) {
                console.error("Lỗi khi lấy sản phẩm:", error);
            });
    };

    $scope.getSanPham();
    $scope.getPageNumbers = function (totalPages) {
        $scope.pageNumbers = []
        for (let i = 0; i < totalPages; i++) {
            $scope.pageNumbers.push(i);
        }
    }

    $scope.getAll = function (pageNumber) {
        $scope.pageNumber = pageNumber;

        if (!isfilter) {
            $http.get("/san-pham/get-all?pageNumber=" + pageNumber).then(r => {
                $scope.products = r.data.content;
                // $scope.filterData = {}
            }).catch(e => console.log(e))
        } else {
            $http.post("/san-pham/filter?pageNumber=" + pageNumber, $scope.filterDto).then(r => {
                $scope.products = r.data.content;
            }).catch(e => console.log(e))
        }
    }

  // Example quantity, replace with your actual quantity
    $scope.quantity = {};
    $scope.isOutOfStock = false; // Initially, assume the product is not out of stock


//

    $scope.getProductDetails = function(maSP) {
        $http.get("/chi-tiet-san-pham/" + maSP + "/get-all").then(r => {
            console.log("dtaaw",r.data)
            $scope.productDetails = r.data;
        }).catch(e => console.log(e))
    };
    $scope.getProductDetails();


    $scope.addChiTietDonHang = function () {
        if (!$scope.selectedSize) {
            // Nếu người dùng chưa chọn size, thông báo hoặc xử lý khác tùy nhu cầu
            alertify.error("Vui Lòng Chọn Size")
            return;
        }
        if ($scope.selectedSize.soLuong <=0) {
            // Nếu người dùng chưa chọn size, thông báo hoặc xử lý khác tùy nhu cầu
            alertify.error("Sản Phảm Đã Hết")
            return;
        }


        var existingItem = $scope.chiTietDonHang.find(function(item) {
            return item.idChiTietSanPham === $scope.selectedSize.id;
        });

        if (existingItem) {
            if (existingItem.soLuong + 1 > $scope.selectedSize.soLuong) {
                alertify.error("Số Lượng Sản Phẩm Không Đủ");
                return;
            }
            existingItem.soLuong++;
        } else {
            // Nếu sản phẩm chưa tồn tại, kiểm tra số lượng có vượt quá số lượng trong kho không
            if (1 > $scope.selectedSize.soLuong) {
                alertify.error("Số Lượng Sản Phẩm Không Đủ");
                return;
            }
            // Nếu chưa tồn tại, thêm mới vào danh sách
            
            $scope.chiTietDonHang.push({
                sanPham: $scope.selectedSize.sanPham,
                anh: $scope.selectedSize.sanPhamDTO.anh.length == 0 ? "default.png" : $scope.selectedSize.sanPhamDTO.anh[0],
                size: $scope.selectedSize.size,
                soLuong: 1,
                donGia: $scope.selectedSize.sanPhamDTO.giaBan,
                donGiaSauGiam: $scope.selectedSize.sanPhamDTO.giaNiemYet,
                idChiTietSanPham: $scope.selectedSize.id
            });
        }

        // Đóng modal khi đã thêm vào danh sách
        $('#productModal').modal('hide');

        // Reset trường nhập liệu
        $scope.er.soLuongSP = null; // Hoặc giá trị khác thay vì chuỗi rỗng
    };
    $scope.searchSanPham = function () {
        $http.get("/admin/san-pham/search?ten=" + $scope.inputProduct).then(r => {
            $scope.products = r.data
            console.log("12", $scope.products)
        }).catch(e => console.log(e))

    }
        $scope.closeModel = function() {
        $('#productModal').modal('hide');  // Close the modal programmatically
    };
    $scope.checkSanPhamInDonHang = function (idCTSP) {
        let result = false;
        $scope.chiTietDonHang.forEach(d => {
            if (d.idChiTietSanPham == idCTSP) {
                result = true;
            }
        })
        return result;
    }


    $scope.openModal = function(product) {
        $scope.selectedProduct = product;
        $scope.selectedSize = null;
        $scope.soLuong = ''
        $scope.getProductDetails(product.ma);
        $('#productModal').modal('show');
    };

    $scope.getSoLuong = function (idCTSP) {
        $http.get("/chi-tiet-san-pham/1/" + idCTSP).then(r => {
            if (r.data <=0){
                $scope.soLuong = "Còn lại " + 0 + " sản phẩm"
                return
            }else{
                $scope.soLuong = "Còn lại " + r.data + " sản phẩm"
            }

        }).catch(e => console.log(e))
    }

    $scope.getSanPhamCT = function() {
        $http.get("/admin/san-pham/1/get-all-ctsp")
            .then(function(response) {
                $scope.sanPhamCT = response.data;
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    $scope.getData = function(size) {
        // In ra console thông tin của size được chọn

        $scope.selectedSize = size;
        console.log("Size được chọn:", size);
        // Thực hiện các công việc khác tùy vào nhu cầu của bạn
    };
    $scope.getSanPhamCT();
    //validate so luong
    $scope.validateSoLuong = function(item) {
        if (item.soLuong < 0) {
            item.invalid = true;
            return
        } else {
            item.invalid = false;
            // Call your existing functions to handle valid input
            $scope.updateSoLuong(item.idChiTietSanPham, item.soLuong);
            $scope.subtractSoLuong(item.idChiTietSanPham);
        }
    },

 ////////////////
    // $scope.openSizeModal = function(sanPham) {
    //     $scope.selectedProduct = sanPham;
    //     $scope.availableSizes = sanPham.sanPhamDTO.sizes; // Chỉ định danh sách kích thước
    //     $('#sizeModal').modal('show'); // Mở modal
    // };
    //
    // $scope.selectSize = function(size) {
    //     $scope.selectedProduct.size = size; // Cập nhật kích thước cho sản phẩm
    //     $('#sizeModal').modal('hide'); // Đóng modal
    // };

    ////////////////////////
    $scope.themDonHang = function (trangThai){
        alertify.confirm("Tạo đơn hàng?", function () {
            let chiTietDonHang = [];
            $scope.chiTietDonHang.forEach(c => {
                if(c.soLuong<=0){
                    alertify.error("Số Lượng Không hợp lệ");
                        return;
                }
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
                $scope.getSanPhamCT()
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
    $scope.searchText = '';
    $scope.filteredCustomers = [];
    $scope.donHangAdd = {};

    $scope.filterOpen = false;

    $scope.filterCustomers = function() {
        $scope.filterOpen = true;
        // Add your filtering logic here
    };

    $scope.closeFilter = function() {
        $scope.filterOpen = false;
    };

    $scope.filterCustomers = function() {
        var searchTextLower = $scope.searchText.toLowerCase();
        $scope.filteredCustomers = $scope.khachHang.filter(function(customer) {
            return customer.hoVaTen.toLowerCase().includes(searchTextLower) ||
                customer.soDienThoai.includes($scope.searchText);
        });
    };

    $scope.selectCustomer = function(customer) {
        $scope.searchText = customer.hoVaTen + ' - ' + customer.soDienThoai;
        $scope.selectedCustomer = customer;
        $scope.filteredCustomers = [];
        $scope.donHangAdd.tenNguoiNhan = customer.hoVaTen;
        $scope.donHangAdd.soDienThoai = customer.soDienThoai;
        $scope.donHangAdd.username = customer.soDienThoai;
        $scope.closeFilter();
    };


    $scope.keyWordKhachHang = ""
    $scope.getAllKhachHang = function (){
        $http.get("/admin/khach-hang/get-all-khach-hang?limit=1000&&keyWord="+$scope.keyWordKhachHang).then(r =>{
            $scope.khachHang = r.data.content;
        }).catch(e => console.log(e))
    }
    $scope.getAllKhachHang()
    $scope.addKhachHangToDonHang = function (){
        let value = document.getElementById("khachHangSL").value
        console.log("test2",value)
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
        console.log("test3")
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

            // Find the index of the product detail in the order by its ID
            let index = $scope.chiTietDonHang.findIndex(c => c.idChiTietSanPham == idCTSP);
            let chiTietDonHang = $scope.chiTietDonHang[index];

            // Check the available stock for the product with the requested quantity
            $http.get(`/admin/san-pham/1/kiem-tra-so-luong/${idCTSP}?soLuong=${soLuong}&idCTDH=${chiTietDonHang.id ? chiTietDonHang.id : ""}`).then(response => {
                // Update the quantity if the stock check passes
                $scope.chiTietDonHang[index].soLuong = soLuong;
                $scope.getTotalPrice();  // Recalculate the total price
            }).catch(error => {
                if (chiTietDonHang.id === undefined) {
                    // If the product is new and not yet added to the order, reset the quantity to 1
                    chiTietDonHang.soLuong = 1;
                } else {
                    // If the product is already in the order, reset to the previous valid quantity
                    $http.get(`/admin/chi-tiet-don-hang/detail/${chiTietDonHang.id}`).then(response => {
                        $scope.chiTietDonHang[index].soLuong = response.data.soLuong;
                    }).catch(e => console.log(e));
                }
                // Show an error message if the quantity exceeds the available stock
                alertify.error("Số lượng đã vượt quá số lượng sản phẩm!");
            });
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
    $scope.lyDoDoi = '';
    $scope.messLyDoDoi = '';

    $scope.maDonHang = "";
    $scope.layMaDonHang = function(item) {
       console.log(item)
        $scope.maDonHang = item
    };
    $scope.huyDH1 = function() {
        if ($scope.lyDoDoi.trim() === '') {
            $scope.messLyDoDoi = 'Vui lòng nhập lý do đổi hàng!';
        } else {
            $scope.messLyDoDoi = '';
            $http.put("/admin/don-hang/update-trang-thai-hoan?ma=" + $scope.maDonHang + "&trangThai=6" + "&lydo=" + encodeURIComponent($scope.lyDoDoi)).then( r =>  {
               console.log(r.data)
                alertify.success("Gửi yêu cầu Hoàn Thành công");
                $('#closeHuy').click()
            }).catch(e => {
                alertify.error("Gửi Yêu cầu Hoàn thất bại")
                console.log(e)
            })
        }
    }

})