<%--
  Created by IntelliJ IDEA.
  User: tim
  Date: 2021/12/27
  Time: 12:45 下午
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowdfundingjs/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        // 为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 调用分页函数，实现分页效果
        generatePage();

        //  查询操作
        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();
            generatePage();
        });
        // 点击新增打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });
        // 模态框数据保存
        $("#saveRoleBtn").click(function () {
            // 获取用户在模态框中输入的角色名，trim去前后空格
            let roleName = $.trim($("#addModal [name=roleName]").val());

            // 发送ajax请求
            $.ajax({
                url: "role/save.json",
                type: "post",
                data: {
                    name: roleName
                },
                dataType: "json",
                success: function (resp) {
                    let result = resp.result;
                    if (result === "SUCCESS") {
                        layer.msg("已保存");

                        // 重新加载分页
                        window.pageNum = 99999999;
                        generatePage();
                    }
                    if (result === "FAILED") {
                        layer.msg("操作失败！" + resp.message);
                    }
                },
                error: function (resp) {
                    layer.msg(resp.status + "" + resp.statusText);
                }
            });

            // 关闭模态框
            $("#addModal").modal("hide");

            // 清理模态框内容
            $("#addModal [name=roleName]").val("");

        });
    })
</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" id="showAddModalBtn" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <!--这里显示分页-->
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-role-add.jsp" %>
</body>
</html>
