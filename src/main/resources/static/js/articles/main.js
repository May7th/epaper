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
    function getArticlesByName(pageIndex, pageSize) {
         $.ajax({ 
             url: "/pages/"+pageId,
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
        getArticlesByName(0, _articleSize);
    });

    // 获取添加用户的界面
    $("#addarticle").click(function() {
        $.ajax({ 
             url: "/articles/add/"+pageId,
             success: function(data){
                 $("#articleFormContainer").html(data);
             },
             error : function(data) {
                 toastr.error("error!");
             }
         });
    });

    $(".article-edit-article").click(function() {
        $.ajax({
            url: "/articles/edit/" + $(this).attr("articleId"),
            success: function(data){
                $("#articleFormContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // // 获取编辑用户的界面
    // $("#rightContainer").on("click","", function () {
    //     $.ajax({
    //          url: "/articles/edit/" + $(this).attr("articleId"),
    //          success: function(data){
    //              $("#articleFormContainer").html(data);
    //          },
    //          error : function() {
    //              toastr.error("error!");
    //          }
    //      });
    // });

    // 提交变更后，清空表单
    $("#submitEdit").click(function() {
        $.ajax({ 
             url: "/articles",
             type: 'POST',
             data:$('#articleForm').serialize(),
             success: function(data){
                 $('#articleForm')[0].reset();

                 if (data.success) {
                     // 从新刷新主界面
                     getarticlesByName(0, _articleSize);
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
    $(".article-delete-article").click(function() {
    // $("#rightContainer").on("click",".article-delete-article", function () {
        var articleId = $(this).attr("articleId");
        swal({
            title: "确定删除文章么?",
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
                    url: "/articles/" + articleId ,
                    type: 'DELETE',
                    beforeSend: function(request) {
                        request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
                    },
                    success: function(data){
                        if (data.success) {
                            // 从新刷新主界面
                            getArticlesByName(0, _pageSize);
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