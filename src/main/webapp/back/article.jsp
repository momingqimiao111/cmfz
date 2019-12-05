<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/article/showArticle",
                datatype: "json",
                colNames: ['编号', '内容', '标题', '状态', '上传时间', '发布时间', '所属上师ID', '操作'],
                colModel: [
                    {name: 'id', align: "center", hidden: true},
                    {name: 'content', align: "center", hidden: true},
                    {name: 'title', align: "center"},
                    {name: 'status', align: "center"},
                    {name: 'publish_date', align: "center"},
                    {name: 'create_date', align: "center"},
                    {name: 'guru_id', align: "center"},
                    {
                        name: 'option', formatter: function (cellvalue, options, rowObject) {
                            var result = '';
                            result += "<a href='javascript:void(0)' onclick=\"showModel('" + rowObject.id + "')\" class='btn btn-lg' title='查看详情'> <span class='glyphicon glyphicon-th-list'></span></a>";
                            return result;
                        }
                    },
                ],
                rowNum: 5,
                rowList: [5, 10, 20],
                pager: '#articlePager',
                mtype: "post",
                viewrecords: true,
                sortorder: "desc",
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                height: "500px",
                editurl: "${pageContext.request.contextPath}/article/edit"
            });
        $("#articleTable").jqGrid('navGrid', "#articlePager", {
            add: false,
            edit: false,
            del: true,
            deltext: "删除"
        });
    })

    // 打开模态框
    function addArticle() {
        // 清除表单内数据
        $("#kindfrm")[0].reset();
        // kindeditor 提供的数据回显方法  通过"" 将内容设置为空串
        KindEditor.html("#editor_id", "");
        // 未提供查询上师信息 发送ajax请求查询
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>" +
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"insertArticle()\">添加</button>")
        $("#kind").modal("show");
    }

    $.post('${pageContext.request.contextPath}/guru/selectAll', (result) => {
        $.each(result, (i, guru) => {
            let opt = $('<option value="' + guru.id + '" >' + guru.nick_name + '</option>');
            $('#guruList').append(opt);
        })
    });

    // 编辑文章
    function showModel(id) {
        // 返回指定行的数据，返回数据类型为name:value，name为colModel中的名称，value为所在行的列的值，如果根据rowid找不到则返回空。在编辑模式下不能用此方法来获取数据，它得到的并不是编辑后的值
        let data = $("#articleTable").jqGrid("getRowData", id);
        console.log(data.title);
        $('#formid').val(data.id);
        $("#name").val(data.title);
        //$("#status").val(data.status);
        $.post('${pageContext.request.contextPath}/guru/selectAll', (result) => {
            $.each(result, (i, guru) => {
                let opt;
                if (guru.id == data.guru_id) {
                    opt = $('<option selected value="' + guru.id + '" >' + guru.nick_name + '</option>');
                } else {
                    opt = $('<option  value="' + guru.id + '" >' + guru.nick_name + '</option>');
                }

                $('#guruList').append(opt);
            })
        });
        $("#id").val(data.id);
        // KindEditor 中的赋值方法 参数1: kindeditor文本框 的id
        KindEditor.html("#editor_id", data.content);
        $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>" +
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"insertArticle()\">修改</button>")
        $("#kind").modal("show");
    }

    // 添加文章
    function insertArticle() {

        $.post('${pageContext.request.contextPath}/article/edit', {
            id: $("#formid").val(),
            title: $('#name').val(),
            content: $('#editor_id').val(),
            guru_id: $('#guruList').val()
        });
    }

    /*function updateArticle() {
        $.ajaxFileUpload({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            datatype: "json",
            type: "post",
            fileElementId: "inputfile",
            // ajaxFileUpload 不支持序列化数据上传id=111&&title="XXX"
            //                只支持 Json格式上传数据
            // 解决方案 : 1.更改 ajaxFileUpload 源码 2. 手动封装Json格式
            data: {
                id: $("#formid").val(),
                title: $("#name").val(),
                guru_id: $("#guruList").val(),
                content: $("#editor_id").val()
            },
            success: function (data) {

            }
        })
    }*/
</script>
<div class="col-sm-10">
    <div class="page-header">
        <h2><strong>文章管理</strong></h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a>文章列表</a></li>
        <li><a onclick="addArticle()">添加文章</a></li>
    </ul>
    <div class="panel">
        <table id="articleTable"></table>
        <div id="articlePager" style="width: auto;height: 50px"></div>
    </div>

</div>

