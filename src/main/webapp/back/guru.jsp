<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script>
    $(function () {
        $('#tt').jqGrid({
            url: '${pageContext.request.contextPath}/guru/showGuru',
            datatype: 'json',
            styleUI: 'Bootstrap',
            mtype: "post",
            //cellEdit:true,
            colNames: ['Id', '姓名', '图片', "状态", '法名'],
            collEdit: true,
            colModel: [
                {name: "id", align: 'center',},
                {name: 'name', align: 'center', editable: true, editrules: {required: true}},
                {
                    name: 'photo',
                    edittype: "file",
                    align: 'center',
                    editable: true,
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (data) {
                        return "<img  style='width:50%;height:10s%;' " +
                            "src='" + data + "'/>";
                    }
                },
                {
                    name: 'status', align: 'center', editable: true, edittype: "select",
                    editoptions: {value: "1:正常;2:冻结"},
                    formatter: function (value, option, rows) {
                        if (rows.status == 1) {
                            return "激活";
                        } else {
                            return "冻结";
                        }
                    }
                },
                {name: 'nick_name', align: 'center', editable: true, editrules: {required: true}},

                //格式化参考：http://www.cnblogs.com/hxling/archive/2010/10/10/1847334.html
            ],
            pager: '#pager',
            autowidth: true,
            height: '60%',
            rowNum: 5,
            rowList: [5, 10, 15, 20],
            caption: "上师信息",
            editurl: '${pageContext.request.contextPath}/guru/edit',//设置编辑表单提交路径
            viewrecords: true,
            //recreateForm: true确保每添加或编辑表单是重新创建。
        }).navGrid('#pager', {edit: true, add: true, del: true, search: false},

            {
                jqModal: true, closeAfterEdit: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    let id = response.responseJSON.id;

                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/guru/upload",
                        fileElementId: "photo",
                        data: {id: id,},
                        type: "post",
                        success: function () {
                            $("#tt").trigger("reloadGrid")
                        }
                    });
                    return "true";

                }
            },

            {
                closeAfterAdd: true,
                afterSubmit: function (response, postData) {
                    let id = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/guru/upload",
                        datatype: "json",
                        type: "post",
                        data: {id: id},
                        // 指定的上传input框的id
                        fileElementId: "photo",
                        success: function (data) {
                            // 输出上传成功
                            // jqgrid重新载入
                            $("#tt").trigger("reloadGrid")
                        }
                    });
                    return postData;

                }
            }
        );
    })

</script>


<div class="col-sm-10">
    <table id="tt"></table>
    <div id="pager" style="height: 30px"></div>

</div>

