<%--
  Created by IntelliJ IDEA.
  User: tim
  Date: 2021/12/29
  Time: 3:10 下午
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowdfundingjs/my-menu.js"></script>
<script type="text/javascript">
    $(function () {

        // 调用封装好的函数初始化树形结构
        generateTree();

        // 给添加子节点按钮绑定单击响应函数
        $("#treeDemo").on("click", ".addBtn", function () {

            // 将当前节点的id，作为新节点的pid保存到全局变量
            window.pid = this.id;

            // 打开模态框
            $("#menuAddModal").modal("show");

            return false;
        });

        // 给添加子节点的模态框中的保存按钮绑定单击响应函数
        $("#menuSaveBtn").click(function () {

            // 收集表单项中用户输入的数据
            let name = $.trim($("#menuAddModal [name=name]").val());
            let url = $.trim($("#menuAddModal [name=url]").val());

            // 单选按钮要定位到“被选中”的那一个(没有checked则会选择第一个)
            let icon = $("#menuAddModal [name=icon]:checked").val();

            // 发送Ajax请求
            $.ajax({
                "url": "menu/save.json",
                "type": "post",
                "data": {
                    "pid": window.pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    let result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
                        // 否则有可能刷新不到最新的数据，因为这里是异步的
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            // 关闭模态框
            $("#menuAddModal").modal("hide");

            // 清空表单
            // jQuery对象调用click()函数，里面不传任何参数，相当于用户点击了一下
            $("#menuResetBtn").click();
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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <!-- zTree动态生成的标签所依附的静态节点 -->
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%--<%@include file="/WEB-INF/modal-menu-edit.jsp" %>--%>
<%--<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>--%>
</body>
</html>