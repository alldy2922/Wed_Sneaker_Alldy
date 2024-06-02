var app = angular.module("nhanxet-admin",[])
app.controller("ctrl", function($scope, $http){

    const pathName = location.pathname;
    const maSP = pathName.substring(pathName.lastIndexOf("/"))

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
        pheDuyet : "",
        haveContent : false,
        init(){
            this.rate = "";
            this.pageNumber = 0
            $http.get("/admin/nhan-xet?maSP="+maSP.substring(1)+"&pheDuyet="+this.pheDuyet).then(r => {
                this.contents = r.data.content;
                this.totalElement = r.data.totalElements;
                this.totalPages = r.data.totalPages;
                this.setPageNumbers()
                if(this.totalElement > 0) this.haveContent = true

                this.setdefaultButtons('all')
            })


        },getAvgRate(){
            $http.get("/admin/nhan-xet/avg-by-sanpham?maSP="+maSP.substring(1)+"&pheDuyet="+this.pheDuyet).then(r => {
                console.log(r.data)
                if(r.data.length == 0) this.avg=0
                else {
                    if (!Number.isInteger(r.data)) {
                        this.avg = r.data.toFixed(1)
                    } else this.avg = r.data
                }

                raterJs({
                    rating: Number.parseFloat(this.avg+""),
                    starSize: 22, step: .1, element: document.querySelector("#rater-step"), rateCallback: function (e, t) {
                        this.setRating(e), t()
                    }
                });
            })
            $http.get("/admin/nhan-xet/avg-rates-by-sanpham?maSP="+maSP.substring(1)+"&pheDuyet="+this.pheDuyet).then(r => {
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
            $http.get("/admin/nhan-xet?maSP="+maSP.substring(1)+"&page="+page+"&rate="+this.rate+"&pheDuyet="+this.pheDuyet).then(r => {
                this.contents = r.data.content;
            })
        },filterByRate(rate){
            this.pageNumber = 0
            this.rate = rate;
            $http.get("/admin/nhan-xet?maSP="+maSP.substring(1)+"&rate="+rate+"&pheDuyet="+this.pheDuyet).then(r => {
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
        },changePheDuyet(pheDuyet){
            this.pheDuyet = pheDuyet
            this.init()
            this.getAvgRate()
        },pheDuyetNhanXet(id,pheDuyet){
            let mess = pheDuyet ? 'Phê duyệt' : 'Bỏ phê duyệt'
            $http.put("/admin/nhan-xet/phe-duyet/"+id,pheDuyet).then(r => {
                var index = this.contents.findIndex(n => n.id == id)
                this.contents[index].pheDuyet = pheDuyet
                alertify.success(mess + " thành công")
            }).catch(e => {
                alertify.error(mess + " thất bại")
                console.log(e)
            });
        }
    }
    $scope.nhanXet.init()
    $scope.nhanXet.getAvgRate()

    $scope.donHang= {}
    $scope.chiTietDonHang = []
    $scope.getDetailDonHang =  function (ma) {
        $http.get("/admin/don-hang/" + ma).then(r => {
            $scope.donHang = r.data;
            $('#donHangDetail').modal('show')
        }).catch(e => console.log(e))

        $http.get("/admin/chi-tiet-don-hang/" + ma).then(r => {
            $scope.chiTietDonHang = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getTotalPrice = function () {
        let total = 0;
        $scope.chiTietDonHang.forEach(c => total += (c.donGiaSauGiam * c.soLuong))
        return total
    }
})