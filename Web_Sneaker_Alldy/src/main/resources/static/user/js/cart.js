var app = angular.module("cart-app", [])
app.controller("cart-ctrl", function ($scope, $http) {
    $scope.cart = [];
    $scope.vouchers = [];
    $scope.user = [];
    $scope.cartUser = [];
    $scope.productDetails ={};
    // $http.get("/khach-hang/getUser").then(r => {
    //     let data = r.data;
    //     data.ngaySinh = new Date(data.ngaySinh); // Chuyển đổi ngaySinh sang đối tượng Date
    //     $scope.user = data;
    //     $scope.getThongTinCartUser($scope.user.username)
    //     console.log("datauser", $scope.user);
    // }).catch(e => {
    //     console.error("Error", e);
    // });

    // $scope.getThongTinCartUser = function (username){
    //     $http.get("/cart/find-all-user?kh=" + username ).then(r => {
    //         $scope.productDetails  = r.data;
    //         console.log("soLuong1:", $scope.productDetails);
    //         $scope.getProductDetails($scope.productDetails[0].chiTietSanPham)
    //
    //     }).catch(e => console.log(e));
    //
    // }
    // $scope.getSoLuong = function (idCTSP) {
    //
    // }
    $http.get("/cart/find-all").then(r => {
        console.log(r.data)
        $scope.cart = r.data;
        console.log("soLuong:")
    }).catch(e => console.log(e))
    //
    // $scope.getProductDetails = function(idCTSP) {
    //         $http.get("/chi-tiet-san-pham/1/get-all-id?id=" + idCTSP).then(r => {
    //             console.log("ctsp",r.data)
    //             $scope.cartUser = r.data;
    //
    //     }).catch(e => console.log(e))
    // };
    // $scope.getProductDetails();

    $scope.updateSl = function (id, soLuong) {
        if (soLuong <= 0) {
            alertify.error("Số lượng phải là số nguyên > 0!!!")
            return
        }
        if (!parseInt(soLuong)) {
            alertify.error("Số lượng phải là số nguyên > 0!!")
            return
        }
        $http.put("/cart/update-sl/" + id + "/" + soLuong).then(function (r){
            console.log(r.data)
            $scope.cart = r.data;
        }).catch(e=>{
            $scope.cart.forEach(c =>{
                if(c.id == id){
                    document.getElementById(c.id).value =  c.soLuong
                }
            })
            alertify.error(e.data.sl)
            console.log(e)
        })
    }
    $scope.removeProductIncart = function (idCTSP) {
        alertify.confirm("Xóa sản phẩm khỏi giỏ hàng? ", function () {
            $http.delete("/cart/remove/" + idCTSP).then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                $scope.getTotal();
                // let index = $scope.cart.findIndex(c => c.id == idCTSP);
                // $scope.cart.slice(index,1)
            })
        }, function () {})
    }
    $scope.removeAllProductIncart = function () {
        alertify.confirm("Xóa hết giỏ hàng? ", function () {
            $http.delete("/cart/removeAll").then(function (response) {
                // alert("Success")
                $scope.cart = response.data;
                console.log(response.data())
            })
        }, function () {})
    }
    $scope.getTotal = function () {
        var totalPrice = 0;
        for (let i = 0; i < $scope.cart.length; i++) {
            totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
        }
        return totalPrice;
    }

//    show voucher
    $http.get("/check-out/voucher").then(resp => {
        console.log(resp.data)
        $scope.vouchers = resp.data;
    }).catch(error => {
        console.log(error)
    })

    $scope.showDetails = function (index) {
        $scope.selectedVoucher = $scope.vouchers[index];
    };

    $scope.hideDetails = function () {
        $scope.selectedVoucher = null;
    };

})



