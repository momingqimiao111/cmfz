<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<script>

    $(function () {
        $('#tt').jqGrid({
            url: '${pageContext.request.contextPath}/banner/showBanners',
            datatype: 'json',
            styleUI: 'Bootstrap',
            mtype: "post",
            //cellEdit:true,
            colNames: ['编号', '标题', '图片', "链接", '创建时间', '描述', '状态'],
            collEdit: true,
            colModel: [
                {name: "id", align: 'center',},
                {name: 'title', align: 'center', editable: true, editrules: {required: true}},
                {
                    name: 'cover',
                    index: 'cover',
                    edittype: "file",
                    align: 'center',
                    editable: true,
                    editoptions: {enctype: "multipart/form-data"},
                    formatter: function (value, option, rows) {
                        return "<img  style='width:50%;height:10s%;' " +
                            "src='" + rows.cover + "'/>";
                    }
                },
                {name: 'href', align: 'center', editable: true, editrules: {required: true}},
                {
                    name: 'create_date',
                    align: 'center',
                    formatter: "date",
                    formatoptions: {scrformat: 'Y-m-d H:i:s', newformat: 'Y-m-d'}
                },
                {name: 'description', align: 'center', editable: true, editrules: {required: true}},
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

                //格式化参考：http://www.cnblogs.com/hxling/archive/2010/10/10/1847334.html
            ],
            pager: '#pager',
            autowidth: true,
            height: '60%',
            rowNum: 5,
            rowList: [5, 10, 15, 20],
            caption: "轮播图的详细信息",
            editurl: '${pageContext.request.contextPath}/banner/edit',//设置编辑表单提交路径
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
                    let status = response.responseJSON.status;
                    let id = response.responseJSON.id;
                    let oper = response.responseJSON.oper;

                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            fileElementId: "cover",
                            data: {id: id, oper: oper},
                            type: "post",
                            success: function () {
                                $("#tt").trigger("reloadGrid")
                            }
                        });
                        return "true";
                    }
                }
            },

            {
                jqModal: true, closeAfterAdd: true, recreateForm: true, onInitializeForm: function (formid) {
                    $(formid).attr('method', 'POST');
                    $(formid).attr('action', '');
                    $(formid).attr('enctype', 'multipart/form-data');
                },
                afterSubmit: function (response) {
                    let status = response.responseJSON.status;
                    let id = response.responseJSON.id;
                    let oper = response.responseJSON.oper;
                    if (status) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/upload",
                            fileElementId: "cover",
                            data: {id: id, oper: oper},
                            type: "post",
                            success: function () {
                                $("#tt").trigger("reloadGrid")
                            }
                        });
                        return "true";
                    }
                }
            }
        );
    })

</script>


<div class="col-sm-10">
    <table id="tt"></table>
    <div id="pager" style="height: 30px"></div>

</div>

