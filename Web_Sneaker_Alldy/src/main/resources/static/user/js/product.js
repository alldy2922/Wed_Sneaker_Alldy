var app = angular.module("index-app", [])
app.controller("index-ctrl", function ($scope, $http) {
    $scope.title = "Tất cả sản phẩm"
    $scope.products = [];
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    $scope.maSpInDSYT = []
    var isfilter = false;

    $http.get("/san-pham/get-all").then(r => {
        $scope.products = r.data.content;
        $scope.getPageNumbers(r.data.totalPages)
        $scope.filterData = {}
    }).catch(e => console.log(e))


    $("#slider-range").slider({/////init range khoảng tiền
        range: true,
        min: 0,
        max: 10000000,
        step: 100000,
        values: [0, 10000000],
        slide: function (event, ui) {
            let min = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(ui.values[0])
            let max = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(ui.values[1])
            $scope.filterData.giaBan = ui.values[0]
            $scope.filterData.giaMax = ui.values[1]
            $("#min-price").html(min);
            $("#max-price").html(max);
        }
    })
    $scope.resetRange = function () {
        $("#min-price").html(new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(0));
        $("#max-price").html(new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(10000000));
        $("#slider-range").slider({
            range: true,
            min: 0,
            max: 10000000,
            step: 100000,
            values: [0, 10000000],
            slide: function (event, ui) {
                let min = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(ui.values[0])
                let max = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(ui.values[1])
                $scope.filterData.giaBan = ui.values[0]
                $scope.filterData.giaMax = ui.values[1]
                $("#min-price").html(min);
                $("#max-price").html(max);
            }
        })
    }
    //////////////////////////////////////////////////

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

    $scope.getPageNumbers = function (totalPages) {
        $scope.pageNumbers = []
        for (let i = 0; i < totalPages; i++) {
            $scope.pageNumbers.push(i);
        }
    }

    $scope.addDSYT = function (id) {
        let heartButton = document.getElementById("" + id)
        // let className = heartButton.className;
        let data = {
            "sanPham": id
        }
        if (heartButton.className == "far fa-heart" || heartButton.className == "far fa-heart ng-scope") {// thêm vào danh sách yêu thích
            $http.post("/danh-sach-yeu-thich/add", data).then(r => {
                heartButton.className = "fas fa-heart"
                alertify.success("Đã thêm vào danh sách yêu thích!")
                $scope.getMaSanPhamInDSTY()// gọi lại list $scope.maSpInDSYT

            }).catch(e => {
                heartButton.className = "far fa-heart" //lỗi thì ko đổi giữ nguyên icon
                if (e.status == "401") {//Bắt lỗi chưa đăng nhập
                    console.log("aaaa")
                    $('#dangNhap').modal('show') // hiển thị modal
                }
            })
        } else { // xóa khỏi danh sách yêu thích
            $http.delete("/danh-sach-yeu-thich/delete/" + id).then(r => {
                heartButton.className = "far fa-heart"

                let index = $scope.maSpInDSYT.findIndex(m => m == id + "");// xóa thì xóa mã sản phẩm ở trong list $scope.maSpInDSYT ko cần gọi lại api
                $scope.maSpInDSYT.splice(index, 1)

                alertify.success("Đã xóa sản phẩm ra khỏi yêu thích!!")
            }).catch(e => {
                heartButton.className = "fas fa-heart"//lỗi thì ko đổi giữ nguyên icon

                if (e.status == "401") {//Bắt lỗi chưa đăng nhập
                    $('#dangNhap').modal('show') // hiển thị modal
                }
            })
        }

    }


    ///////////////////////////////////////
    $scope.getPropertiesInFilter = function () {
        $http.get("/admin/mau-sac/find-all").then(r => {
            $scope.mauSac = r.data;
            console.log(r.data)
        }).catch(e => console.log(e))

        $http.get("/admin/chat-lieu/find-all").then(r => {
            $scope.chatLieu = r.data;
        }).catch(e => console.log(e))

        $http.get("/admin/thuong-hieu/find-all").then(r => {
            $scope.thuongHieu = r.data;
        }).catch(e => console.log(e))

        $http.get("/admin/xuat-xu/find-all").then(r => {
            $scope.xuatXu = r.data;
        }).catch(e => console.log(e))

        $http.get("/admin/kieu-dang/find-all").then(r => {
            $scope.kieuDang = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getPropertiesInFilter();
    $scope.filter = function (filterData) {

        for (const [key, value] of Object.entries(filterData)) {
            if(value.length==0) delete filterData[key]
        }
        console.log(filterData)

        $scope.pageNumber = 0
        $scope.filterDto = filterData

        $scope.pageNumbers = []
        $http.post("/san-pham/filter", $scope.filterDto).then(r => {
            if(Object.keys( $scope.filterDto ).length>0){
                document.getElementById('lengthFilter').innerText = Object.keys( $scope.filterDto ).length
            }else{
                document.getElementById('lengthFilter').innerText = ""
            }

            $scope.title = "Có " + r.data.totalElements + " sản phẩm liên quan"
            $scope.products = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
            isfilter = true;
        }).catch(e => console.log(e))
    }
    $scope.search = function (keyWord) {

        $scope.pageNumber = 0

        $scope.filterData = {}
        $scope.filterDto = {}
        $scope.resetRange()

        $scope.filterDto.ten = keyWord
        $scope.pageNumbers = []
        $http.post("/san-pham/filter", $scope.filterDto).then(r => {
            $scope.title = "Có " + r.data.totalElements + " sản phẩm liên quan"
            document.getElementById('lengthFilter').innerText = ""
            $scope.products = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
            isfilter = true;
        }).catch(e => console.log(e))
    }
    $scope.clearFilter = function () {
        $scope.pageNumber = 0
        $scope.pageNumbers = []
        $http.get("/san-pham/get-all").then(r => {
            document.getElementById('lengthFilter').innerText = ""
            $scope.title = "Tất cả sản phẩm"
            $scope.products = r.data.content;
            $scope.getPageNumbers(r.data.totalPages)
            $scope.filterData = {}
            $scope.filterDto = {}
            isfilter = false;

            $scope.resetRange()
        }).catch(e => console.log(e))
    }
    ///////////////////////////////////////
    ////////////////////////////////////////
    $scope.checkSanPhamInDSYT = function (maSP) {
        let reult = false;
        $http.get("/danh-sach-yeu-thich/check/" + maSP).then(r => {
            reult = r.data
        }).catch(e => console.log(e))
        return reult
    }

    $scope.getMaSanPhamInDSTY = function () {
        $http.get("/danh-sach-yeu-thich/get-ma-san-pham-in-dsyt").then(r => {
            $scope.maSpInDSYT = r.data
            // document.getElementById("buttonHeart").setAttribute("data-notify", ""+$scope.maSpInDSYT.length)
        }).catch(e => console.log(e))
    }
    $scope.getMaSanPhamInDSTY()

    $scope.addDSYT1 = function (id) {
        console.log(id)

        let data = {
            "sanPham": id
        }
        $http.post("/danh-sach-yeu-thich/add", data).then(r => {
            alert("Đã thêm vào danh sách yêu thích!")
        })
    }

    //    cart show
    $scope.cart = [];
    $http.get("/cart/check-login")
        .then(function(response) {
            if (response.data) {
                // User is logged in, fetch the cart data
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong san pham:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            } else {
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong san-pham:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            }
        })
        .catch(function(error) {
            console.log('Error checking login status:', error);
        });

    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
        return totalPrice;
    }

    //dang nhap
    $scope.login = function () {
        var expires = (new Date(Date.now() + 60 * 1000)).toUTCString();
        document.cookie = "url=" + window.location.href + "; expires=" + expires;
        location.href = "/dang-nhap";
    }
})