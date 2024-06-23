var app = angular.module("filter-app", [])
app.controller("filter-ctrl", function ($scope, $http) {
    $scope.title = "Tất cả sản phẩm"
    $scope.products = [];
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    $scope.maSpInDSYT = []
    var isfilter = false;
    $scope.cart = [];
    const pathName = location.pathname;
    const thuongHieu = pathName.substring(pathName.lastIndexOf("/"))
    $scope.filterDto = {
        dongSanPham : thuongHieu.substring(1)
    }
    $scope.thuongHieu = {}

    $http.get("/thuong-hieu"+thuongHieu).then(r => {
        $scope.thuongHieu = r.data
    })

    $http.post("/san-pham/filter", $scope.filterDto).then(r => {
        $scope.products = r.data.content;
        $scope.getPageNumbers(r.data.totalPages)
        console.log($scope.products)
    }).catch(e => console.log(e))



    $scope.getAll = function (pageNumber) {
        $scope.pageNumber = pageNumber;
        $http.post("/san-pham/filter", $scope.filterDto).then(r => {
                $scope.products = r.data.content;
                // $scope.filterData = {}
            }).catch(e => console.log(e))
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

                alertify.success("Đã xóa danh sách yêu thích!")
            }).catch(e => {
                heartButton.className = "fas fa-heart"//lỗi thì ko đổi giữ nguyên icon

                if (e.status == "401") {//Bắt lỗi chưa đăng nhập
                    $('#dangNhap').modal('show') // hiển thị modal
                }
            })
        }

    }


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

    $http.get("/cart/check-login")
        .then(function(response) {
            if (response.data) {
                // User is logged in, fetch the cart data
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong: chi tiet sp", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            } else {
                $http.get("/cart/find-all")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            }
        })

    //dang nhap
    $scope.login = function () {
        var expires = (new Date(Date.now() + 60 * 1000)).toUTCString();
        document.cookie = "url=" + window.location.href + "; expires=" + expires;
        location.href = "/dang-nhap";
    }
})