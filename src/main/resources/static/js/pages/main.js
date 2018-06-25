/*!
 * Bolg main JS.
 * 
 * @since: 1.0.0 2017/3/9
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=main.js
 
// DOM 加载完再执行
//DOM 加载完再执行
$(function() {

    var _pageSize; // 存储用于搜索

    $("#uploadImage").click(function() {
        $.ajax({
            url: 'http://localhost:8081/upload',
            type: 'POST',
            data: new FormData($('#uploadformid')[0]),
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function(data){
                alert("11111");
            }
        }).done(function(res) {
            $('#file').val('');
        }).fail(function(res) {});
    })
    // 根据用户名、页面索引、页面大小获取用户列表
    function getPagesByName(pageIndex, pageSize) {
         $.ajax({ 
             url: "/papers/"+paperId,
             contentType : 'application/json',
             data:{
                 "async":true, 
                 "pageIndex":pageIndex,
                 "pageSize":pageSize,
                 "name":$("#searchName").val()
             },
             success: function(data){
                 $("body").html(data);
             },
             error : function() {
                 toastr.error("error!");
             }
         });
    }

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getPagesByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function() {
        getPagesByName(0, _pageSize);
    });

    // 获取添加用户的界面
    $("#addPage").click(function() {
        $.ajax({ 
             url: "/pages/add/"+paperId,
             success: function(data){
                 $("#pageFormContainer").html(data);
             },
             error : function(data) {
                 toastr.error("error!");
             }
         });
    });

    $(".page-edit-page").click(function() {
        $.ajax({
            url: "/pages/edit/" + $(this).attr("pageId"),
            success: function(data){
                $("#pageFormContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // // 获取编辑用户的界面
    // $("#rightContainer").on("click","", function () {
    //     $.ajax({
    //          url: "/pages/edit/" + $(this).attr("pageId"),
    //          success: function(data){
    //              $("#pageFormContainer").html(data);
    //          },
    //          error : function() {
    //              toastr.error("error!");
    //          }
    //      });
    // });

    // 提交变更后，清空表单
    $("#submitEdit").click(function() {
        $.ajax({ 
             url: "/pages",
             type: 'POST',
             data:$('#pageForm').serialize(),
             success: function(data){
                 $('#pageForm')[0].reset();

                 if (data.success) {
                     // 从新刷新主界面
                     getPagesByName(0, _pageSize);
                 } else {
                     toastr.error(data.message);
                 }

             },
             error : function() {
                 toastr.error("error!");
             }
         });
    });

    // 删除用户
    $(".page-delete-page").click(function() {
    // $("#rightContainer").on("click",".page-delete-page", function () {
        var pageId = $(this).attr("pageId");
        swal({
            title: "确定删除版面么?",
            text: "删除后无法恢复",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then(function(willDelete){
            if (willDelete) {
                var csrfToken = $("meta[name='_csrf']").attr("content");
                var csrfHeader = $("meta[name='_csrf_header']").attr("content");

                $.ajax({
                    url: "/pages/" + pageId ,
                    type: 'DELETE',
                    beforeSend: function(request) {
                        request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
                    },
                    success: function(data){
                        if (data.success) {
                            // 从新刷新主界面
                            getPagesByName(0, _pageSize);
                        } else {
                            toastr.error(data.message);
                        }
                    },
                    error : function() {
                        toastr.error("error!");
                    }
                });
                swal("已经成功删除版面", {
                    icon: "success",
                });
            } else {

    }
    });
    });
});