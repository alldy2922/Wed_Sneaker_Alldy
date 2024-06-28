var app = angular.module("ctsp-app", [])
app.controller("ctsp-ctrl", function ($scope, $http) {
    $scope.product = {};
    $scope.productDetails = [];
    $scope.images = [];
    $scope.idCTSP = ""
    $scope.soLuongAdd = 1
    $scope.soLuong = ""
    $scope.size = null
    $scope.lengthFoot = 26
    $scope.cart =[];
    // const idSP = pathName[pathName.length - 1]
    // $scope.form ={
    //     sanPham : idSP
    // };
    // $scope.sizes = [];

    var heartButton = document.getElementById("heart")
    const sizeZone = $("#sizes-zone")
    const pathName = location.pathname;
    const maSP = pathName.substring(pathName.lastIndexOf("/"))

    $scope.login = function (){
        var expires = (new Date(Date.now()+ 60*1000)).toUTCString();
        document.cookie = "url="+window.location.href+"; expires="+expires;
        location.href = "/dang-nhap";
    }

    $http.get("/san-pham" + maSP).then(r => {
        $scope.product = r.data
        console.log($scope.product.thuongHieu.length)
        if (r.data.anh.length > 0) {
            for (let i = 1; i < r.data.anh.length; i++) {
                $scope.images = $scope.images.concat(r.data.anh[i]);
            }
        }
    }).catch(e => {
        console.log(e)
        alert("Lỗi!")
    })

    $http.get("/chi-tiet-san-pham" + maSP + "/get-all").then(r => {
        $scope.productDetails = r.data;
        $scope.productDetails.forEach(s => {
            if (s.soLuong <= 0) sizeZone.append('<input type="radio" class="btn-check" name="ctsp" id="' + s.size + '" autocomplete="off" disabled>\n' +
                '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;">' + s.size + '</label>')
            else sizeZone.append('<input type="radio" ng-model="idCTSP" value="' + s.id + '" class="btn-check" name="ctsp" id="' + s.size + '" autocomplete="off" ng-click="getSoLuong(' + s.id + ')">\n' +
                '<label class="btn btn-outline-secondary" for="' + s.size + '" style="width: 60px;" onclick="angular.element(this).scope().getSoLuong(\'' + s.id + '\')">' + s.size + '</label>')
        })
    }).catch(e => console.log(e))

    $http.get("/san-pham/san-pham-tuong-tu" + maSP).then(r => {
        $scope.productsTuongTu = r.data
    }).catch(e => {
        console.log(e)
        alert("Lỗi!")
    })


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

                alertify.success("Đã xóa sản phẩm ra khỏi yêu thích!")
            }).catch(e => {
                heartButton.className = "fas fa-heart"//lỗi thì ko đổi giữ nguyên icon

                if (e.status == "401") {//Bắt lỗi chưa đăng nhập
                    $('#dangNhap').modal('show') // hiển thị modal
                }
            })
        }

    }
    // $scope.getMaSanPhamInDSTY()

    $scope.addDSYT1 = function (id) {
        console.log(id)

        let data = {
            "sanPham": id
        }
        $http.post("/danh-sach-yeu-thich/add", data).then(r => {
            alert("Đã thêm vào danh sách yêu thích!")
        })
    }

    $scope.checkSanPhamInDSYT = function (maSP) {
        let reult = false;
        $http.get("/danh-sach-yeu-thich/check/" + maSP).then(r => {
            reult = r.data
        }).catch(e => console.log(e))
        console.log(reult)
        return reult
    }

    $scope.getMaSanPhamInDSTY = function () {
        $http.get("/danh-sach-yeu-thich/get-ma-san-pham-in-dsyt").then(r => {
            $scope.maSpInDSYT = r.data
            // console.log($scope.maSpInDSYT.length)
            // document.getElementById("buttonHeart").setAttribute("data-notify", ""+$scope.maSpInDSYT.length)
        }).catch(e => console.log(e))
    }
    $scope.getMaSanPhamInDSTY()
    $scope.showImg = function (nameImg) {
        document.getElementById("show-Img").src = "/image/loadImage/product/" + nameImg
    }
    //add to cart
    $scope.addToCart = function () {
        let idCtsp = form.elements["ctsp"].value
        // if (idCtsp == null) {
        //     return;
        // }
        let sl = parseInt(document.getElementById("soLuong").value)
        console.log("sốluong: " + sl)
        alertify.confirm("Thêm sản phẩm vào giỏ hàng?", function () {
            $http.post("/cart/add-to-cart?idCTSP=" + idCtsp + "&sl=" + sl).then(function (response) {
                console.log(response.data)


                if (response.data == null || response.data.length == 0) {
                    alertify.error("Phân loại của sản phẩm không đủ số lượng!!!")
                } else {
                    alertify.success("Thêm thành công vào giỏ hàng")
                    $scope.cartShow()
                }
            }).catch(e => {
                document.getElementById("eSize").innerText = e.data.eSize == undefined ? "" : e.data.eSize
                alertify.error("Thêm sản phẩm vào giỏ hàng thất bại!!!")
                console.log(e)
            })
        },function (){})
    }
    //Mua Ngay
    $scope.muaNgay = function () {
        let idCtsp = form.elements["ctsp"].value
        // if (idCtsp == null) {
        //     return;
        // }
        let sl = parseInt(document.getElementById("soLuong").value)
        console.log("sốluong: " + sl)
        // let alertify;
        alertify.confirm("Bạn Có Muốn Mua Ngay Không ?", function () {
            $http.post("/cart/mua-ngay?idCTSP=" + idCtsp + "&sl=" + sl).then(function (response) {
                console.log(response.data)
                if (response.data == null || response.data.length == 0) {
                    alertify.error("Phân loại của sản phẩm không đủ số lượng!!!")
                } else {
                    alertify.success("Thêm thành công vào giỏ hàng")
                    $scope.cartShow()
                    location.href = "/thanh-toan";
                }

            }).catch(e => {
                document.getElementById("eSize").innerText = e.data.eSize == undefined ? "" : e.data.eSize
                alertify.error("Mua Ngay thất bại!!!")
                console.log(e)
            })


        },function (){})
    }

    $scope.getSoLuong = function (idCTSP) {
        $http.get("/chi-tiet-san-pham/1/" + idCTSP).then(r => {
            $scope.soLuong = "Còn lại " + r.data + " sản phẩm"
        }).catch(e => console.log(e))
    }
    $scope.getSizePhuHop = function () {
        let sizes = []
        $http.get("/size/get-by-chieu-dai?chieuDai=" + $scope.lengthFoot).then(r => {
            sizes = r.data
            if (sizes.length == 0) {
                $scope.size = "Không có kích thước phù hợp"
            } else {
                for (let i = 0; i < sizes.length; i++) {
                    if (i == 0) {
                        $scope.size = sizes[i].ma + "";
                    } else {
                        $scope.size += ", " + sizes[i].ma;
                    }
                }
            }
        }).catch(e => console.log(e))
    }
    $scope.getSizePhuHop()


//    cart show
    $scope.cartShow = function () {
        $http.get("/cart/check-login")
            .then(function(response) {
                if (response.data) {
                    // User is logged in, fetch the cart data from the database
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
                    // User is not logged in, fetch the cart data from the session
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
    }
$scope.cartShow()

        $scope.getTotal = function () {
            var totalPrice = 0;
            for (let i = 0; i < $scope.cart.length; i++) {
                totalPrice += $scope.cart[i].soLuong * $scope.cart[i].donGiaSauGiam
            }
            return totalPrice;
        }

    $scope.login = function () {
        var expires = (new Date(Date.now() + 60 * 1000)).toUTCString();
        document.cookie = "url=" + window.location.href + "; expires=" + expires;
        location.href = "/dang-nhap";
    }

    $scope.nhanXet = {
        pageNumber : 0,
        totalElement : 0,
        pageNumbers : [],
        contents : [],
        totalPages : 0,
        avg : 0,
        rating : [1,2,3,4,5],
        rates : {},
        rate : "",
        haveContent : false,
        init(){
            this.rate = "";
            this.pageNumber = 0
            $http.get("/nhan-xet?maSP="+maSP.substring(1)).then(r => {
                this.contents = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPages = r.data.totalPages;
                this.setPageNumbers()
                if(this.totalElement > 0) this.haveContent = true

                this.setdefaultButtons('all')
            })


        },getAvgRate(){
            $http.get("/nhan-xet/avg-by-sanpham?maSP="+maSP.substring(1)).then(r => {
                // try {
                    if(!Number.isInteger(r.data)) {
                        this.avg = r.data.toFixed(1)
                    }else this.avg = r.data

                // }
                // catch(err) {
                //     this.avg = r.data
                // }
                // if(this.avg.length == 0) this.avg=0
                raterJs({
                    rating: Number.parseFloat(this.avg+""),
                    starSize: 22, step: .1, element: document.querySelector("#rater-step"), rateCallback: function (e, t) {
                        this.setRating(e), t()
                    }
                });
                
            })
            $http.get("/nhan-xet/avg-rates-by-sanpham?maSP="+maSP.substring(1)).then(r => {
                this.rates = r.data
            })
        },setPageNumbers() {
            let numbers = [];
            for (let i = 0; i < this.totalPages; i++) {
                numbers.push(i)
            }
            this.pageNumbers = numbers;
        },get(page){
            this.pageNumber = page
            $http.get("/nhan-xet?maSP="+maSP.substring(1)+"&page="+page+"&rate="+this.rate).then(r => {
                this.contents = r.data.content;
            })
        },filterByRate(rate){
            this.pageNumber = 0
            this.rate = rate;
            $http.get("/nhan-xet?maSP="+maSP.substring(1)+"&rate="+rate).then(r => {
                this.contents = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPages = r.data.totalPages;
                this.setPageNumbers()
                this.setdefaultButtons('rate'+rate)
            })

        },setdefaultButtons(id){
            var button = document.getElementsByName("filterNhanXet")

            button.forEach(b => {
                if(b.id == id){
                    b.style.backgroundColor = "lightgray"
                    b.style.color = "white"
                }else {
                    b.style.backgroundColor = "white"
                    b.style.color = "black"
                }
            })
        }
    }
    $scope.nhanXet.init()
    $scope.nhanXet.getAvgRate()
})

