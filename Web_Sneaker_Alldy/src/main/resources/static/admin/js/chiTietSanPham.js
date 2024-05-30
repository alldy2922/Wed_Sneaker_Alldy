var app = angular.module('chiTietSP-app', []);
app.controller('chiTietSP-ctrl', function ($scope, $http) {

    const pathName = window.location.pathname.split('/');
    const idSP = pathName[pathName.length - 1]

    $scope.items =[];
    $scope.form ={
        sanPham : idSP
    };
    $scope.sizes = [];
    $scope.itemUpdate = {};

    $scope.getAll = function (){
        $http.get("/admin/san-pham/"+idSP+"/get-all").then(r => {
            $scope.items = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getSizes = function (){
        $http.get("/admin/san-pham/"+idSP+"/test").then(r => {
            $scope.sizes = r.data;
        }).catch(e => console.log(e))
    }
    $scope.getAll();
    $scope.getSizes();


    // $scope.delete = function (ma){
    //     $http.delete("/admin/san-pham/delete/"+ma).then(r => {
    //         var index = $scope.items.findIndex(i => i.ma == ma);
    //         console.log(index)
    //         $scope.items.splice(index,1);
    //         // $scope.getAll();
    //     }).catch(e => console.log(e));
    // }

    $scope.getChiTietSP = function (ma){
        location.href = "/admin/san-pham/"+ma;
    }

    //Thêm
    $scope.add = function (){

        let data =[];
        let sizesInForm = $scope.form.sizes
        if(sizesInForm == undefined) sizesInForm = [];

            $http.post("/admin/san-pham/"+idSP+"/add?sizes="+sizesInForm,{
                soLuong : $scope.form.soLuong
            }).then(r =>{
                $scope.removeSizeInForm(sizesInForm);
                $scope.items = $scope.items.concat(r.data);
                $scope.form.soLuong = ""
                alertify.success("Thêm thành công "+sizesInForm.length+" chi tiết sản phẩm")
            }).catch(e => {
                document.getElementById("eSize").innerText = e.data.eSize == undefined ? "" : e.data.eSize
                document.getElementById("eSoLuong").innerText =  e.data.soLuong == undefined ? "" : e.data.soLuong
                console.log(e)
                alertify.error("Thêm thất bại")
            })
    }

    //Xóa
    $scope.delete = function (item){
        console.log(item)
        alertify.confirm("Xóa chi tiết sản phẩm size "+item.size, function () {
            $http.delete("/admin/san-pham/"+idSP+"/delete/"+item.id).then(r => {
                let index = $scope.items.findIndex(i => i.id == item.id);
                $scope.items.splice(index,1);
                $scope.getSizes();
                alertify.success("Xóa thành công chi tiết sản phẩm size "+item.size);
            }).catch(e => {
                alertify.error("Xóa thất bại")
                console.log(e);
            })

        }, function () {
            alertify.error("Xóa thất bại")
        })
    }

    //Cập nhật
    $scope.viewUpdate = function (item){
        $scope.itemUpdate = angular.copy(item)
        console.log($scope.itemUpdate)
    }
    $scope.update = function (){
            alertify.confirm("Cập nhật chi tiết sản phẩm size "+$scope.itemUpdate.size+" ?", function () {
                // $scope.itemUpdate.sanPham = idSP
                $http.put("/admin/san-pham/"+idSP+"/update",$scope.itemUpdate).then(r =>{
                    let index = $scope.items.findIndex(i => i.id == $scope.itemUpdate.id)
                    $scope.items[index] = r.data
                    console.log("cl", $("#cancelModal").click())
                    alertify.success("Cập nhật thành công số lượng size "+$scope.itemUpdate.size)
                }).catch(e => {
                    document.getElementById("eSoLuongUpdate").innerText = e.data.soLuong
                    console.log(e)
                    alertify.error("Cập nhật thất bại")
                })
            }, function () {
                alertify.error("Cập nhật thất bại")
            })
    }
    $scope.updateSlInTable = function (soLuong,id){
        console.log(id)
        $scope.itemUpdate.id = id
        $scope.itemUpdate.soLuong = parseInt(soLuong);
        console.log($scope.itemUpdate)
        $http.put("/admin/san-pham/"+idSP+"/update",$scope.itemUpdate).then(r =>{
            alertify.success("Cập nhật số lượng thành công")
        }).catch(e => {
            $http.get("/admin/san-pham/"+idSP+"/getSoLuong/"+id).then(r => {
                document.getElementById(id+"").value = r.data.soLuong
            })
            alertify.error(e.data.soLuong)
        })
    }

    $scope.removeSizeInForm = function (size){
        for (let i = 0 ;i< size.length;i++){
            let index = $scope.sizes.findIndex(s => s.ma == size[i])
            $scope.sizes.splice(index,1);
        }
    }
    $scope.removeER = function (id){
        document.getElementById(id).innerText = "";
    }

    $scope.selectAllSize = function (){
        let elm = document.getElementById("sizeSL")
        if(elm.selectedIndex == 0) {
            elm.options[0].selected = false;
            console.log(elm.options[0].selected)
            for (let i = 0; i < elm.options.length; i++) {
                elm.options[i].selected = true;
            }
        }
    }
});
$( '#sizeSL' ).select2( {
    theme: "bootstrap-5",
    width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
    placeholder: $( this ).data( 'placeholder' ),
    closeOnSelect: false,
    allowClear: true,
} );





