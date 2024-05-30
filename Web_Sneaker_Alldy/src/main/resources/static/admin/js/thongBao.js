// var app = angular.module('app', []);
// app.controller('ctrl', function ($scope, $http) {
//
//     $scope.thongBao = [];
//
//     $http.get("/admin/thong-bao/get-all/afc0b6cc-4c66-11ee-b10b-d69e940a783b").then(r => {
//         $scope.thongBao = r.data
//         console.log("tb",r.data)
//     })
//         .catch(e => console.log(e));
//
//     //////////////////
//     var socket = new SockJS('/connectNotification');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function(frame) {
//         // console.log('Connected: ' + frame);
//         stompClient.subscribe('/afc0b6cc-4c66-11ee-b10b-d69e940a783b', function(r) {
//             // $scope.thongBao.unshift()
//             console.log(r)
//         });
//     });
//     /////////////////
// })