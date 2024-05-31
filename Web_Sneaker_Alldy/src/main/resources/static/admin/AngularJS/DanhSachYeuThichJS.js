var app = angular.module("danh-sach-yeu-thich",[])
app.controller("danh-sach-yeu-thich-ctrl", function($scope, $http){
    $scope.form = {}
    $scope.items = []
    $scope.productDetails = [];

    $scope.findAll = function () {
        $http.get("/danh-sach-yeu-thich/find-all").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                $http.get("/chi-tiet-san-pham/" + item.sanPhamm.ma + "/get-all").then(r => {
                    item.chietTietSanPham = r.data;
                }).catch(e => console.log(e))
            })
        }).catch(error =>{
            console.log(error)
        })
    }
    $scope.findAll();

    $scope.delete = function (ma){
        alertify.confirm("Bạn có muốn xóa sản phẩm khỏi danh sách yêu thích?", function () {
            var url = "/danh-sach-yeu-thich/delete" +"/"+ ma;
            $http.delete(url).then(function (r){
                alertify.success("Đã xóa sản phẩm ra khỏi danh sách yêu thích")
                let index = $scope.items.findIndex(i => i.sanPhamm.ma == ma)
                $scope.items.splice(index,1)
            })
        },function (){})
    }

    $scope.getDetail =function (maSP){
        var productDetails = []
        $http.get("/chi-tiet-san-pham/" + maSP + "/get-all").then(r => {
            productDetails = r.data;
        }).catch(e => console.log(e))
        return productDetails
    }
    $scope.getSoLuong = function (idCTSP,idSpan) {
        $http.get("/chi-tiet-san-pham/1/" + idCTSP).then(r => {
            document.getElementById('soLuong'+idSpan).innerText = "Còn lại " + r.data
        }).catch(e => console.log(e))
    }

    $scope.addToCart = function (idSlCTSP, idInputSoLuong){
        var soLuong = document.getElementById(idInputSoLuong).value
        if(soLuong.length == 0){
            alertify.error("Vui lòng nhập số lượng")
            return
        }
        if(parseInt(soLuong) < 1){
            alertify.error("Số lượng phải > 0")
            return
        }
        var ctsp = document.getElementById(idSlCTSP).value
        console.log(soLuong,ctsp)
        alertify.confirm("Bạn có muốn thêm sản phẩm vào giỏ hàng ?", function () {
            $http.post("/cart/add-to-cart?idCTSP=" + ctsp + "&sl=" + soLuong).then(function (response) {
                console.log(response.data)
                if (response.data == null || response.data.length == 0) {
                    alertify.error("Phân loại của sản phẩm không đủ số lượng!!!")
                } else {
                    alertify.success("Thêm thành công vào giỏ hàng")
                    $scope.cartShow()
                }
            }).catch(e => {
                if(e.data.eSize != undefined){
                    alertify.error(e.data.eSize)
                }
                alertify.error("Thêm sản phẩm vào giỏ hàng thất bại!!!")
                console.log(e)
            })
        },function (){})
    }


})