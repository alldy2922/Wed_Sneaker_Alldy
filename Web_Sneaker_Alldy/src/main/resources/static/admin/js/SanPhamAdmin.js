

var app = angular.module('product-admin', []);
app.controller('ctrl', function ($scope, $http) {


    $scope.items =[];
    $scope.form ={};
    $scope.filterDto = {};
    $scope.totalPage = 0;
    $scope.pageNumbers = [];
    $scope.pageNumber = 0;
    var isfilter = false;



    $http.get("/admin/san-pham/get-all").then(r => {
        $scope.items = r.data.content;
        $scope.totalPage = r.data.totalPages;
        $scope.getPageNumbers(r.data.totalPages)
        $scope.filterData = {}
    }).catch(e => console.log(e))

    $scope.getAll = function (pageNumber){
        $scope.pageNumber = pageNumber;
        //
        // let pagination = document.getElementsByClassName("pagination")[0]
        // let pageItem = pagination.getElementsByClassName("pageNumber")

        // pageItem.forEach(p => {
        //     p.getElementsByTagName("a")[0].className = "page-link"
        // })
        //
        // document.getElementById(pageNumber+"").getElementsByTagName("a")[0].className = "page-link active"

        if(!isfilter){
            $http.get("/admin/san-pham/get-all?pageNumber="+pageNumber).then(r => {
                $scope.items = r.data.content;
                // $scope.filterData = {}
            }).catch(e => console.log(e))
        }else{
            $http.post("/admin/san-pham/filter?pageNumber="+pageNumber,$scope.filterDto).then(r => {
                $scope.items = r.data.content;
            }).catch(e => console.log(e))
        }
    }

    $scope.getPageNumbers = function (totalPages){
        $scope.pageNumbers = []
        for (let i = 0; i< totalPages;i++){
            $scope.pageNumbers.push(i);
        }
    }

    $scope.delete = function (ma){


        alertify.confirm("Xóa sản phẩm?", function () {
            $http.delete("/admin/san-pham/delete/"+ma).then(r => {
                alertify.success("Xóa thành công")
                if ($scope.pageNumber == $scope.totalPage - 1) {
                    if ($scope.items.length == 1 && $scope.pageNumber > 0) {
                        $scope.pageNumber -=1;
                        $scope.totalPage -=1;
                    }
                }

                $scope.getPageNumbers($scope.totalPage)
                $scope.getAll($scope.pageNumber)
            }).catch(e => {
                alertify.error("Xóa thất bại")
                console.log(e)
            });
        }, function () {
            alertify.error("Xóa thất bại")
        })
    }

    $scope.getChiTietSP = function (ma){
        location.href = "/admin/san-pham/"+ma;
    }

    $scope.updateTrangThaiHienThi = function (switchId,maSP){
        let trangThai = document.getElementById(switchId).checked
        $http.put("/admin/san-pham/update-TrangThai-HienThi/"+maSP,trangThai).then(r => {
            alertify.success("Cập nhật thành công")
        }).catch(e => {
            alertify.error("Cập nhật thất bại")
            document.getElementById(switchId).checked = trangThai == true ? false : true
        });
    }

    $scope.getPropertiesInFilter = function (){
        $http.get("/admin/mau-sac/find-all").then(r =>{
            $scope.mauSac = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/chat-lieu/find-all").then(r =>{
            $scope.chatLieu = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/thuong-hieu/find-all").then(r =>{
            $scope.thuongHieu = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/xuat-xu/find-all").then(r =>{
            $scope.xuatXu = r.data;
        }).catch( e => console.log(e))

        $http.get("/admin/kieu-dang/find-all").then(r =>{
            $scope.kieuDang = r.data;
        }).catch( e => console.log(e))
    }
    $scope.getPropertiesInFilter();

    $scope.filter = function (filterData){
        for (const [key, value] of Object.entries(filterData)) {
            if(value.length==0) delete filterData[key]
        }
        console.log(filterData)
        console.log(isNaN(filterData.giaBan))
        if(filterData.giaBan != undefined){
            if(isNaN(filterData.giaBan)){
                alertify.error("Giá min phải là số!!")
                return
            }else{
                if(filterData.giaBan < 10000){
                    alertify.error("Giá min phải > 10.000đ!!")
                    return
                }
            }
        }
        if(filterData.giaMax != undefined){
            if(isNaN(filterData.giaMax)){
                alertify.error("Giá max phải là số!!")
                return
            }else{
                if(filterData.giaMax > 100000000){
                    alertify.error("Giá max phải < 100.000.000đ !!")
                    return
                }else{
                    if(filterData.giaBan != undefined){
                        if(parseFloat(filterData.giaBan) > parseFloat(filterData.giaMax)){
                            console.log("max",parseFloat(filterData.giaMax))
                            console.log("min",parseFloat(filterData.giaBan))
                            alertify.error("Giá max phải > giá min!!")
                            return
                        }
                    }
                }
            }
        }

        $scope.pageNumber = 0
        $scope.filterDto = filterData
        $scope.pageNumbers = []
        $http.post("/admin/san-pham/filter",$scope.filterDto).then(r => {
            if(Object.keys( $scope.filterDto ).length>0){
                document.getElementById('lengthFilter').innerText = Object.keys( $scope.filterDto ).length
            }else{
                document.getElementById('lengthFilter').innerText = ""
            }
            $scope.items = r.data.content;
            $scope.totalPage = r.data.totalPages;
            $scope.getPageNumbers(r.data.totalPages)
            isfilter = true;
        }).catch(e => console.log(e))
    }
    $scope.clearFilter = function (){

        $scope.pageNumber = 0
        $scope.pageNumbers = []
        $http.get("/admin/san-pham/get-all").then(r => {
            document.getElementById('lengthFilter').innerText = ""
            $scope.items = r.data.content;
            $scope.getPageNumbers(r.data.totalPages)
            $scope.filterData = {}
            $scope.filterDto = {}
            isfilter = false;
        }).catch(e => console.log(e))
    }

    $scope.sortName = function (){
       let button = document.getElementById("sortName")
        if(button.className == "bx bx-sort-up"){
            $scope.resetIconButton()
            button.className = "bx bx-sort-down"
            $scope.filterDto.sort = 3
        }else if(button.className == "bx bx-sort"){
            $scope.resetIconButton()
            button.className = "bx bx-sort-up"
            $scope.filterDto.sort =  4
        }else{
            button.className = "bx bx-sort"
            // $scope.clearFilter()
            delete $scope.filterDto.sort
        }

        $scope.filter($scope.filterDto)

        // $scope.pageNumber = 0
        // $scope.pageNumbers = []
        // $http.post("/admin/san-pham/filter",$scope.filterDto).then(r => {
        //     $scope.items = r.data.content;
        //     $scope.getPageNumbers(r.data.totalPages)
        //     isfilter = true;
        // }).catch(e => console.log(e))
    }
    $scope.sortColor = function (){
        let button = document.getElementById("sortColor")
        if(button.className == "bx bx-sort-up"){
            $scope.resetIconButton()
            $scope.filterDto.sort = 7
            button.className = "bx bx-sort-down"
        }else if(button.className == "bx bx-sort"){
            $scope.resetIconButton()
            $scope.filterDto.sort = 8
            button.className = "bx bx-sort-up"
        }else{
            button.className = "bx bx-sort"
            delete $scope.filterDto.sort
        }
        $scope.filter($scope.filterDto)
    }
    $scope.sortBrand = function (){
        let button = document.getElementById("sortBrand")
        if(button.className == "bx bx-sort-up"){
            $scope.resetIconButton()
            $scope.filterDto.sort = 9
            button.className = "bx bx-sort-down"
        }else if(button.className == "bx bx-sort"){
            $scope.resetIconButton()
            $scope.filterDto.sort = 10
            button.className = "bx bx-sort-up"
        }else{
            button.className = "bx bx-sort"
            delete $scope.filterDto.sort
        }
        $scope.filter($scope.filterDto)
    }
    $scope.sortAcount = function (){
        let button = document.getElementById("sortAcount")
        if(button.className == "bx bx-sort-up"){
            $scope.resetIconButton()
            button.className = "bx bx-sort-down"
            $scope.filterDto.sort = 5
        }else if(button.className == "bx bx-sort"){
            $scope.resetIconButton()
            button.className = "bx bx-sort-up"
            $scope.filterDto.sort = 6
        }else{
            button.className = "bx bx-sort"
            delete $scope.filterDto.sort
        }
        $scope.filter($scope.filterDto)
    }
    $scope.sortPrice = function (){
        let button = document.getElementById("sortPrice")
        if(button.className == "bx bx-sort-up"){
            $scope.resetIconButton()
            button.className = "bx bx-sort-down"
            $scope.filterDto.sort = 1
        }else if(button.className == "bx bx-sort"){
            $scope.resetIconButton()
            button.className = "bx bx-sort-up"
            $scope.filterDto.sort = 2
        }else{
            button.className = "bx bx-sort"
            delete $scope.filterDto.sort
        }
        $scope.filter($scope.filterDto)
    }
    $scope.resetIconButton = function (){
        document.getElementById("sortName").className = "bx bx-sort";
        document.getElementById("sortColor").className = "bx bx-sort";
        document.getElementById("sortBrand").className = "bx bx-sort";
        document.getElementById("sortPrice").className = "bx bx-sort";
        document.getElementById("sortAcount").className = "bx bx-sort";
    }


    $scope.toastSuccess = function (text) {

        $.toast({
            heading: 'Thành công',
            text: text,
            position: 'top-right',
            icon: 'success',
            stack: false
        })
    }
    // $scope.toastSuccess("Thành công")
    $scope.toastError = function (text) {

        $.toast({
            heading: 'Thành công',
            text: text,
            position: 'top-right',
            icon: 'error',
            stack: false
        })
    }

});





