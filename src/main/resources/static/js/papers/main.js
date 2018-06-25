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

    // 根据用户名、页面索引、页面大小获取用户列表
    function getPapersByName(pageIndex, pageSize) {
         $.ajax({ 
             url: "/papers",
             contentType : 'application/json',
             data:{
                 "async":true, 
                 "pageIndex":pageIndex,
                 "pageSize":pageSize,
                 "name":$("#searchName").val()
             },
             success: function(data){
                 $("#mainContainer").html(data);
             },
             error : function() {
                 toastr.error("error!");
             }
         });
    }

    // 分页
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getPapersByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // 搜索
    $("#searchNameBtn").click(function() {
        getPapersByName(0, _pageSize);
    });

    // 获取添加用户的界面
    $("#addPaper").click(function() {
        $.ajax({ 
             url: "/papers/add",
             success: function(data){
                 $("#paperFormContainer").html(data);
             },
             error : function(data) {
                 toastr.error("error!");
             }
         });
    });

    // 获取编辑用户的界面
    $("#rightContainer").on("click",".paper-edit-paper", function () {
        $.ajax({ 
             url: "/papers/edit/" + $(this).attr("paperId"),
             success: function(data){
                 $("#paperFormContainer").html(data);
             },
             error : function() {
                 toastr.error("error!");
             }
         });
    });

    // 提交变更后，清空表单
    $("#submitEdit").click(function() {
        $.ajax({ 
             url: "/papers",
             type: 'POST',
             data:$('#paperForm').serialize(),
             success: function(data){
                 $('#paperForm')[0].reset();

                 if (data.success) {
                     // 从新刷新主界面
                     getPapersByName(0, _pageSize);
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
    $("#rightContainer").on("click",".paper-delete-paper", function () {
        var paperId = $(this).attr("paperId");
        swal({
            title: "确定删除报刊及其版面么?",
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
                    url: "/papers/" + paperId ,
                    type: 'DELETE',
                    beforeSend: function(request) {
                        request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
                    },
                    success: function(data){
                        if (data.success) {
                            // 从新刷新主界面
                            getUsersByName(0, _pageSize);
                        } else {
                            toastr.error(data.message);
                        }
                    },
                    error : function() {
                        toastr.error("error!");
                    }
                });
                swal("已经成功删除报刊", {
                    icon: "success",
                });
            } else {

    }
    });
    });
});