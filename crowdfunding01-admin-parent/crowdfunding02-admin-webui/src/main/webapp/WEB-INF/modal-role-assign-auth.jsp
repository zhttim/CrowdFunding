<%--
  Created by IntelliJ IDEA.
  User: tim
  Date: 2022/1/1
  Time: 4:32 下午
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<div id="assignModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">系统弹窗</h4>
            </div>
            <div class="modal-body">
                <ul id="authTreeDemo" class="ztree"></ul>
            </div>
            <div class="modal-footer">
                <button id="assignBtn" type="button" class="btn btn-primary">好的，我设置好了！执行分配！</button>
            </div>
        </div>
    </div>
</div>