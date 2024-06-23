var app = angular.module("trangChu-app", [])
app.controller("ctrl", function ($scope, $http) {
    $scope.products = [];
    $scope.maSpInDSYT = []
    $scope.cart = [];
    $scope.getSanPham = function (number) {
        let path = "ban-chay"
        if (number == 0) {
            path = "ban-chay"
        } else if (number == 1) {
            path = "khuyen-mai"
        } else {
            path = "san-pham-moi"
        }
        $http.get("/san-pham/" + path).then(r => {
            $scope.products = r.data
            console.log($scope.products)
        }).catch(e => console.log(e))
    }
    $scope.getSanPham(0)

    $scope.getMaSanPhamInDSTY = function () {
        $http.get("/danh-sach-yeu-thich/get-ma-san-pham-in-dsyt").then(r => {
            $scope.maSpInDSYT = r.data
            console.log($scope.maSpInDSYT.length)
            // document.getElementById("buttonHeart").setAttribute("data-notify", ""+$scope.maSpInDSYT.length)
        }).catch(e => console.log(e))
    }
    $scope.getMaSanPhamInDSTY()

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
                $scope.getMaSanPhamInDSTY()
            }).catch(e => {
                heartButton.className = "far fa-heart"

                if (e.status == "401") {//Bắt lỗi chưa đăng nhập
                    $('#dangNhap').modal('show') // hiển thị modal
                }
            })
        } else { // xóa khỏi danh sách yêu thích
            $http.delete("/danh-sach-yeu-thich/delete/" + id).then(r => {
                heartButton.className = "far fa-heart"

                let index = $scope.maSpInDSYT.findIndex(m => m == id + "");
                $scope.maSpInDSYT.splice(index, 1)

                alertify.success("Đã xóa sản phẩm ra khỏi yêu thích!")
            }).catch(e => {
                heartButton.className = "fas fa-heart"

                if (e.status == "401") {//Bắt lỗi chưa đăng nhập
                    $('#dangNhap').modal('show') // hiển thị modal
                }
            })
        }

    }
    ///////////get Sản phẩm theo thương hiệu
    $http.post("/san-pham/filter?limit=4", $scope.filterDto = {////nike
        dongSanPham : "c413a9fb-51de-4a92-9946-c77e0f3dcc99"
    }).then(r => {
        $scope.productNikes = r.data.content;
        // $scope.filterData = {}
    }).catch(e => console.log(e))

    $http.post("/san-pham/filter?limit=4", $scope.filterDto = {////adidas
        dongSanPham : "5c832b42-c43c-4517-bd39-23f14631f8b5"
    }).then(r => {
        $scope.productAdidas = r.data.content;
        // $scope.filterData = {}
    }).catch(e => console.log(e))

    $http.post("/san-pham/filter?limit=4", $scope.filterDto = {////vans
        dongSanPham : "874c4e1e-61a7-4144-a8e8-49fe8c61ca46"
    }).then(r => {
        $scope.productVans = r.data.content;
        // $scope.filterData = {}
    }).catch(e => console.log(e))

    ///////////////////////////

//    show giỏ hàng
    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
        return totalPrice;
    }

    $http.get("/cart/check-login")
        .then(function(response) {
            if (response.data) {
                // User is logged in, fetch the cart data from the database
                $http.get("/cart/find-all-sp")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong trang chu:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            } else {
                // User is not logged in, fetch the cart data from the session
                $http.get("/cart/find-all")
                    .then(function(r) {
                        console.log(r.data);
                        $scope.cart = r.data;
                        console.log("soLuong trangchu:", $scope.cart);
                    })
                    .catch(function(e) {
                        console.log(e);
                    });
            }
        })
        .catch(function(error) {
            console.log('Error checking login status:', error);
        });

    $scope.login = function (){
        var expires = (new Date(Date.now()+ 60*1000)).toUTCString();
        document.cookie = "url="+window.location.href+"; expires="+expires;
        location.href = "/dang-nhap";
        console.log("y",expires)
    }

})