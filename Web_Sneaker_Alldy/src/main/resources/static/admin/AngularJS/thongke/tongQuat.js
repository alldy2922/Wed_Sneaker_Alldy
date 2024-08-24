var app = angular.module("thongke-tongquat", [])
app.controller("ctrl", function ($scope, $http){
    $scope.tongQuat = {}
    $scope.sanPhamBanChay = [];
    $scope.sanPhamTon = [];
    $scope.chiTietBanChay = []
    $scope.sanPhamDaTra = [];

    $scope.getTongQuat = function (firstDay,lastDay){
        $http.get("/admin/thong-ke/tong-quat?firstDate="+firstDay+"&lastDate="+lastDay).then(r => {
            $scope.tongQuat = r.data
            // const tooltip = new bootstrap.Tooltip('#doanhThu', {
            //     // boundary: document.getElementById('doanhThu'),
            //     animation : true,
            //     html : true,
            //     title : "<b>Tổng doanh thu: " +$scope.tongQuat.doanhThuDetail.tongTien+"</b>" +
            //                 "<br>" +
            //                 "<b>Tiền giảm: " +$scope.tongQuat.doanhThuDetail.tienGiam+"</b>"
            // })
            console.log(r.data)
        }).catch(e => console.log(e))
    }
    $scope.getTongQuat(new Date().toJSON().slice(0, 10),new Date().toJSON().slice(0, 10))

    $scope.getSanPhamBanChay = function (){
        $http.get("/admin/thong-ke/san-pham-ban-chay").then(r => {
            $scope.sanPhamBanChay = r.data
        }).catch(e => console.log(e))

    }
    $scope.getSanPhamBanChay()
    //fun gọi api sản phẩm trả nhiều nhất
    $scope.getSanPhamTra = function (){
        $http.get("/admin/thong-ke/san-pham-tra").then(r => {
            $scope.sanPhamDaTra = r.data
            console.log("data",$scope.sanPhamDaTra)
        }).catch(e => console.log(e))

    }
    $scope.getSanPhamTra()

    $scope.getChiTietSpBanChay = function (maSP){
        $http.get("/admin/thong-ke/san-pham-ban-chay/"+maSP).then(r => {
            $scope.chiTietBanChay = r.data
            $('#chiTietBanChay').modal('show')
        }).catch(e => console.log(e))
    }

    $scope.getSanPhamTon = function (){
        $http.get("/admin/thong-ke/san-pham-ton").then(r => {
            $scope.sanPhamTon = r.data
        }).catch(e => console.log(e))

    }
    $scope.getSanPhamTon()

    // --BIEU DO sp ban chay
    $scope.chartStart = null;
    $scope.chartEnd = null;
    $scope.chartNum = 0;
    // Hàm truy vấn thống kê
    $scope.getChart = function () {
        console.log("ok")
        if ($scope.chartStart && $scope.chartEnd && $scope.chartStart <= $scope.chartEnd && $scope.chartNum > 0)
            $http({
                method: 'POST',
                url: '/admin/thong-ke/san-pham-ban-chay',
                params: { start: $scope.chartStart, end: $scope.chartEnd, num: $scope.chartNum },
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
            }).then(
                function successCallback(response) {
                    const productNames = response.data.map(item => item.TenSP);
                    const productQuantities = response.data.map(item => item.SoLuongSP);
                    const productImages = response.data.map(item => item.TenAnh);

                    var options = {
                        series: [{
                            name: 'Số lượng sản phẩm',
                            data: productQuantities
                        }],
                        chart: {
                            type: 'bar',
                            height: 400
                        },
                        plotOptions: {
                            bar: {
                                horizontal: false,
                            }
                        },
                        dataLabels: {
                            enabled: true
                        },
                        xaxis: {
                            categories: productNames,
                            labels: {
                                show: true
                            }
                        },
                        yaxis: {
                            labels: {
                                style: {
                                    fontSize: '12px'
                                }
                            },
                            title: {
                                text: 'Số lượng',
                                style: {
                                    fontSize: '12px'
                                }
                            }
                        },
                        title: {
                            text: 'Top sản phẩm bán chạy nhất',
                            align: 'center',
                        },
                        tooltip: {
                            enabled: true,
                            custom: function({ series, seriesIndex, dataPointIndex, w }) {
                                var imageUrl = productImages[dataPointIndex];
                                var productName = productNames[dataPointIndex];
                                var value = series[seriesIndex][dataPointIndex];
                                return `
                                    <div style="width: 280px; height: 245px; display: flex; flex-direction: column; padding: 10px">
                                        <div style="word-wrap: break-word">${productName}</div>
                                      
                                        <div style="padding: 5px 0">
                                            ${value} sản phẩm
                                        </div>
                                        <div>
                                            <img src="/image/loadImage/product/${imageUrl}" alt="Image" style="width: 100%; height: 50% ;" />
                                        </div>
                                    </div>
                                `;
                            }
                        }
                    };

                    var chart = new ApexCharts(document.getElementById("spbanchay"), options);
                    chart.render();
                },
                function errorCallback(response) {
                    console.log(response)
                }
            )
    }

    var delay = 100;
    setTimeout(function() {
        $scope.chartNum = 10;
        $scope.chartStart = new Date(2024, 0, 1);
        $scope.chartEnd = new Date();
        $scope.getChart();
    }, delay);






    $scope.getChartMonth = function () {
        // var months = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6',
        //     'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];
        var donHang = [], sanPham = [], doanhThu = [], months = []
        $http.get("/admin/thong-ke/tong-quat-nam").then(r => {
            // var donHang = new Array(12).fill(0);
            // var sanPham = new Array(12).fill(0);
            // var doanhThu = new Array(12).fill(0);
            //
            // r.data.totalProducts.forEach((value, index) => {
            //     if (index < 12) sanPham[index] = value;
            // });
            // r.data.quantityOrders.forEach((value, index) => {
            //     if (index < 12) donHang[index] = value;
            // });
            // r.data.revenue.forEach((value, index) => {
            //     if (index < 12) doanhThu[index] = value;
            // });

            var data = r.data;

            // Khởi tạo 12 tháng với giá trị mặc định là 0
            for (var i = 0; i < 12; i++) {
                donHang.push(0);
                sanPham.push(0);
                doanhThu.push(0);
                months.push(`Tháng ${i + 1}`);
            }

            // Cập nhật giá trị từ dữ liệu nhận được
            data.months.forEach((month, index) => {
                var monthIndex = parseInt(month.split(" ")[1]) - 1;
                donHang[monthIndex] = data.quantityOrders[index] || 0;
                sanPham[monthIndex] = data.totalProducts[index] || 0;
                doanhThu[monthIndex] = data.revenue[index] || 0;
            });

            // months = r.data.months
            // console.log("asdad ", r.data)

            var options = {
                series: [{
                    name: 'Đơn hàng',
                    type: 'column',
                    data: donHang
                }, {
                    name: 'Sản phẩm',
                    type: 'column',
                    data: sanPham
                }, {
                    name: 'Doanh thu',
                    type: 'line',
                    data: doanhThu
                }],
                chart: {
                    height: 350,
                    type: 'line',
                    stacked: false
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    width: [1, 1, 4]
                },
                title: {
                    text: 'Sơ đồ hàng tháng năm 2024',
                    align: 'center',
                    // offsetX: 110
                },
                xaxis: {
                    categories: months,
                    labels: {
                        show: true,
                        rotate: -45, // Giúp hiển thị các nhãn dễ đọc hơn
                        rotateAlways: true,
                        minHeight: 80
                    },
                    tickPlacement: 'between' // Đặt nhãn giữa các cột
                },
                yaxis: [
                    {
                        axisTicks: {
                            show: true,
                        },
                        axisBorder: {
                            show: true,
                            color: '#008FFB'
                        },
                        labels: {
                            style: {
                                colors: '#008FFB',
                            }
                        },
                        title: {
                            text: "Đơn hàng",
                            style: {
                                color: '#008FFB',
                            }
                        },
                        tooltip: {
                            enabled: true
                        }
                    },
                    {
                        seriesName: 'Đơn hàng',
                        opposite: true,
                        axisTicks: {
                            show: true,
                        },
                        axisBorder: {
                            show: true,
                            color: '#00E396'
                        },
                        labels: {
                            style: {
                                colors: '#00E396',
                            }
                        },
                        title: {
                            text: "Sản phẩm",
                            style: {
                                color: '#00E396',
                            }
                        },
                    },
                    {
                        seriesName: 'Doanh thu',
                        opposite: true,
                        axisTicks: {
                            show: true,
                        },
                        axisBorder: {
                            show: true,
                            color: '#FEB019'
                        },
                        labels: {
                            style: {
                                colors: '#FEB019',
                            },
                            formatter: function (value) {
                                const VND = new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND',
                                });
                                return VND.format(value);
                                // return value + "$";
                            }
                        },
                        title: {
                            text: "Doanh thu",
                            style: {
                                color: '#FEB019',
                            }
                        }
                    },
                ],
                tooltip: {
                    fixed: {
                        enabled: true,
                        position: 'topLeft', // topRight, topLeft, bottomRight, bottomLeft
                        offsetY: 30,
                        offsetX: 60
                    },
                },
                legend: {
                    horizontalAlign: 'left',
                    offsetX: 40
                }
            };

            var chart = new ApexCharts(document.getElementById("month"), options);
            chart.render();

        }).catch(e => console.log(e))

    }
    $scope.getChartMonth()

    $(function() {

        var start = moment();
        var end = moment();

        $('#reportrange').daterangepicker({
            startDate: start,
            endDate: end,
            ranges: {
                'Hôm nay': [moment(), moment()],
                'Hôm qua': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                '7 ngày trước': [moment().subtract(6, 'days'), moment()],
                '30 ngày trước': [moment().subtract(29, 'days'), moment()],
                'Tháng này': [moment().startOf('month'), moment().endOf('month')],
                'Tháng trước': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
            },
            locale: {
                "format": "DD/MM/YYYY",
                "separator": " - ",
                "applyLabel": "Xác nhận",
                "cancelLabel": "Trở về",
                "fromLabel": "Từ",
                "toLabel": "Đến",
                "customRangeLabel": "Lựa chọn",
                "daysOfWeek": ["T2", "T3", "T4", "T5", "T6", "T7", "CN"],
                "monthNames": ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
                // "firstDay": 1
            }
        }).on('apply.daterangepicker',function (ev, picker){

            let chosenLabel= picker.chosenLabel
            var labelConnect = "Đến", start = "",end = "";

            if(chosenLabel == "Hôm nay"){
                labelConnect = "Hôm nay"
            }else if(chosenLabel == "Hôm qua"){
                labelConnect = "Hôm qua"
            }else{
                start = picker.startDate.format('DD-MM-YYYY');
                end = picker.endDate.format('DD-MM-YYYY');
            }

            $('#connect').html(labelConnect);
            $('#start').html(start);
            $('#end').html(end);

            $scope.getTongQuat(picker.startDate.format('YYYY-MM-DD'),picker.endDate.format('YYYY-MM-DD'))
        });


        cb(start, end);

    });

    $scope.setDropDown = function(id){
        var content = document.getElementById(id);
        if(content.style.display=='none'){
            content.style.display='flow-root'
        }else{
            content.style.display='none'
        }
    }

})