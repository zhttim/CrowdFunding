function myAddDiyDom(treeId, treeNode) {
    /*    zTree 生成 id 的规则
        例子： treeDemo_7_ico
        解析： ul 标签的 id_当前节点的序号_功能
        提示： “ul 标签的 id_当前节点的序号” 部分可以通过访问 treeNode 的 tId 属性得到
        根据 id 的生成规则拼接出来 span 标签的 id
    */
    let spanId = treeNode.tId + "_ico";

    // 根据控制图标的span标签的id找到这个span标签
    // 删除旧的class
    // 添加新的class
    $("#" + spanId).removeClass().addClass(treeNode.icon);
}

// 鼠标移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {

    // 按钮组的标签结构： <span><a><i></i></a><a><i></i></a></span>
    // 按钮组出现的位置： 节点中 treeDemo_n_a 超链接的后面

    // 为了在需要移除按钮组的时候能够精确定位到按钮组所在 span， 需要给 span 设置有规律的 id
    let btnGroupId = treeNode.tId + "_btnGrp";
    // 判断一下以前是否已经添加了按钮组
    if ($("#" + btnGroupId).length > 0) {
        return;
    }
    let addBtn = "<a id='" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    let removeBtn = "<a id='" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    let editBtn = "<a id='" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fafa-fw fa-edit rbg '></i></a>";

    // 获取当前节点的级别数据
    let level = treeNode.level;
    // 声明变量存储拼装好的按钮代码
    let btnHTML = "";
    // 判断当前节点的级别
    if (level === 0) {
        // 级别为 0 时是根节点， 只能添加子节点
        btnHTML = addBtn;
    }
    if (level === 1) {
        // 级别为 1 时是分支节点， 可以添加子节点、 修改
        btnHTML = addBtn + " " + editBtn;
        // 获取当前节点的子节点数量
        let length = treeNode.children.length;
        // 如果没有子节点， 可以删除
        if (length === 0) {
            btnHTML = btnHTML + " " + removeBtn;
        }
    }
    if (level === 2) {
        // 级别为 2 时是叶子节点， 可以修改、 删除
        btnHTML = editBtn + " " + removeBtn;
    }
    // 找到附着按钮组的超链接
    let anchorId = treeNode.tId + "_a";
    // 执行在超链接后面附加 span 元素的操作
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");
}

// 鼠标离开节点范围时删除按钮组
function myRemoveHoverDom(treeId, treeNode) {

    // 拼接按钮组的 id
    let btnGroupId = treeNode.tId + "_btnGrp";
    // 移除对应的元素
    $("#" + btnGroupId).remove();
}

function generateTree() {
    // 准备生成树形结构的JSON数据，数据来源是发送Ajax请求得到
    $.ajax({
        url: "menu/get/whole/tree.json",
        type: "post",
        dataType: "json",
        success: function (resp) {
            let result = resp.result;
            if (result === "SUCCESS") {

                // 创建json对象用于存储对zTree所做的设置
                let setting = {
                    view: {
                        addDiyDom: myAddDiyDom,
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom
                    },
                    data: {
                        key: {
                            // url值改为不存在的属性名，则点击页面响应菜单时不会跳转
                            url: "nothing",
                        }
                    }
                };

                // 从响应体中获取用来生成树形结构的JSON数据
                let zNodes = resp.data;

                // 初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result === "FAILED") {
                layer.msg(resp.message);
            }
        },
    });

}