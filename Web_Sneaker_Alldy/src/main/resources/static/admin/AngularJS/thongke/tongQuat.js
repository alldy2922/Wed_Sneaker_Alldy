//Thống kê
var app = angular.module("thongke-tongquat", [])
app.controller("ctrl", function ($scope, $http) {
    $scope.tongQuat = {}
    $scope.sanPhamBanChay = [];
    $scope.sanPhamTon = [];
    $scope.chiTietBanChay = []

    $scope.getTongQuat = function (firstDay, lastDay) {
        $http.get("/admin/thong-ke/tong-quat?firstDate=" + firstDay + "&lastDate=" + lastDay).then(r => {
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
    $scope.getTongQuat(new Date().toJSON().slice(0, 10), new Date().toJSON().slice(0, 10))

    $scope.getSanPhamBanChay = function () {
        $http.get("/admin/thong-ke/san-pham-ban-chay").then(r => {
            $scope.sanPhamBanChay = r.data
        }).catch(e => console.log(e))

    }
    $scope.getSanPhamBanChay()

    $scope.getChiTietSpBanChay = function (maSP) {
        $http.get("/admin/thong-ke/san-pham-ban-chay/" + maSP).then(r => {
            $scope.chiTietBanChay = r.data
            $('#chiTietBanChay').modal('show')
        }).catch(e => console.log(e))
    }

    $scope.getSanPhamTon = function () {
        $http.get("/admin/thong-ke/san-pham-ton").then(r => {
            $scope.sanPhamTon = r.data
        }).catch(e => console.log(e))

    }
    $scope.getSanPhamTon()


    $scope.getChartMonth = function () {

        // var donHang = [], sanPham = [], doanhThu = [];
        // var months = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12']

        var donHang = new Array(12).fill(0);
        var sanPham = new Array(12).fill(0);
        var doanhThu = new Array(12).fill(0);
        var months = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'];

        $http.get("/admin/thong-ke/tong-quat-nam").then(r => {
            // sanPham = r.data.totalProducts || new Array(12).fill(0);
            // donHang = r.data.quantityOrders || new Array(12).fill(0);
            // doanhThu = r.data.revenue || new Array(12).fill(0);
            //
            // // Nếu dữ liệu phản hồi không có đủ 12 tháng, điền thêm giá trị 0
            // while (sanPham.length < 12) sanPham.push(0);
            // while (donHang.length < 12) donHang.push(0);
            // while (doanhThu.length < 12) doanhThu.push(0);

            let dataMap = {};
            r.data.months.forEach((month, index) => {
                dataMap[month] = {
                    sanPham: r.data.totalProducts[index] || 0,
                    donHang: r.data.quantityOrders[index] || 0,
                    doanhThu: r.data.revenue[index] || 0
                };
            });

            // Fill the data arrays according to the standard months array
            months.forEach((month, index) => {
                if (dataMap[month]) {
                    sanPham[index] = dataMap[month].sanPham;
                    donHang[index] = dataMap[month].donHang;
                    doanhThu[index] = dataMap[month].doanhThu;
                }
            });

            months = r.data.months
            console.log("asdad ", r.data)


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

    $(function () {

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
        }).on('apply.daterangepicker', function (ev, picker) {

            let chosenLabel = picker.chosenLabel
            var labelConnect = "Đến", start = "", end = "";

            if (chosenLabel == "Hôm nay") {
                labelConnect = "Hôm nay"
            } else if (chosenLabel == "Hôm qua") {
                labelConnect = "Hôm qua"
            } else {
                start = picker.startDate.format('DD-MM-YYYY');
                end = picker.endDate.format('DD-MM-YYYY');
            }

            $('#connect').html(labelConnect);
            $('#start').html(start);
            $('#end').html(end);

            $scope.getTongQuat(picker.startDate.format('YYYY-MM-DD'), picker.endDate.format('YYYY-MM-DD'))
        });


        cb(start, end);

    });

    $scope.setDropDown = function (id) {
        var content = document.getElementById(id);
        if (content.style.display == 'none') {
            content.style.display = 'flow-root'
        } else {
            content.style.display = 'none'
        }
    }

})