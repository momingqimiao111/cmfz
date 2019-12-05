<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

    <script>
        $(function () {
            pageInit();
        });

        //表格
        function pageInit() {
            jQuery("#tt").jqGrid(
                {
                    url: '${path}/album/showAlbum',
                    datatype: "json",
                    styleUI: 'Bootstrap',
                    autowidth: true,
                    height: 500,
                    colNames: ['ID', '标题', '星级', '作者', '播音', '章节数', '简介', "状态", "创建时间", "图片"],
                    colModel: [
                        {name: 'id', index: 'id',},
                        {name: 'title', editable: true, editrules: {required: true}},
                        {name: 'score', editable: true, editrules: {required: true}},
                        {name: 'author', editable: true, editrules: {required: true}},
                        {name: 'broadcast', editable: true, editrules: {required: true}},
                        {name: 'count',},
                        {name: 'description', editable: true, editrules: {required: true}},
                        {
                            name: 'status', editable: true, edittype: "select",
                            editoptions: {value: "1:正常;2:冻结"},
                            formatter: function (value, option, rows) {
                                if (rows.status == 1) {
                                    return "正常";
                                } else {
                                    return "冻结";
                                }
                            }
                        },
                        {
                            name: 'create_date',
                        },
                        {
                            name: 'cover', edittype: "file",
                            editable: true, editoptions: {enctype: "multipart/form-data"},
                            formatter: function (data) {
                                return "<img  style='width:50%;height:10s%;' " +
                                    "src='" + data + "'/>";
                            }
                        }
                    ],
                    rowNum: 8,
                    rowList: [8, 10, 20, 30],
                    pager: '#pager',
                    sortname: 'id',
                    viewrecords: true,
                    sortorder: "asc",
                    multiselect: false,
                    subGrid: true,
                    editurl: '${pageContext.request.contextPath}/album/edit',//设置编辑表单提交路径
                    caption: "专辑表",
                    subGridRowExpanded: function (subgrid_id, row_id) {
                        let subgridTable = subgrid_id + "table";
                        let subgridPage = subgrid_id + "page";
                        $("#" + subgrid_id).html(
                            "<table id='" + subgridTable
                            + "' class='scroll'></table><div id='"
                            + subgridPage + "' class='scroll'></div>");
                        jQuery("#" + subgridTable).jqGrid(
                            {
                                url: '${path}/chapter/showChapter?album_id=' + row_id,
                                datatype: "json",
                                height: 300,
                                styleUI: 'Bootstrap',
                                autowidth: true,
                                colNames: ['Id', '标题', '音乐', '大小', '时长', '创建时间', "文章id"],
                                colModel: [
                                    {name: "id", key: true},
                                    {name: "title", editable: true, align: "center", editrules: {required: true}},
                                    {
                                        name: "url", edittype: "file", width: 250,
                                        editable: true, editoptions: {enctype: "multipart/form-data"},
                                        formatter: function (data) {
                                            return "<audio controls='controls'  " +
                                                "src='" + data + "'/>";
                                        }
                                    },
                                    {
                                        name: "size", align: "center",
                                        formatter: function (data) {

                                            let size = data / 1024 / 1024;
                                            return size.toFixed(2) + "Mb";
                                        }
                                    },
                                    {
                                        name: "time", align: "center",
                                        formatter: function (data) {
                                            let m = 0;
                                            if (data%60<10){
                                                m = "0"+data%60;
                                            }else {
                                                m = data%60;
                                            }

                                            return (data/60).toFixed(0)+":"+m;
                                        }
                                    },
                                    {name: "create_time",},
                                    {name: "album_id",},
                                ],
                                rowNum: 20,
                                editurl: '${pageContext.request.contextPath}/chapter/edit?album_id=' + row_id,//设置编辑表单提交路径
                                pager: "#" + subgridPage,
                                sortname: 'num',
                                sortorder: "asc",
                                height: '100%'
                            });
                        jQuery("#" + subgridTable).jqGrid('navGrid',
                            "#" + subgridPage, {
                                edit: true,
                                add: true,
                                del: false
                            }, {
                                closeAfterEdit: true,
                                // frm ---> 表单对象
                                beforeShowForm: function (frm) {
                                    frm.find("#url").attr("disabled", true);
                                },
                            },
                            {
                                closeAfterAdd: true,
                                afterSubmit: function (response, postData) {
                                    let id = response.responseJSON.id;
                                    $.ajaxFileUpload({
                                        url: "${pageContext.request.contextPath}/chapter/upload",
                                        datatype: "json",
                                        type: "post",
                                        data: {id: id},
                                        // 指定的上传input框的id
                                        fileElementId: "url",
                                        success: function (data) {
                                            // 输出上传成功
                                            // jqgrid重新载入
                                            $("#" + subgridTable).trigger("reloadGrid")
                                        }
                                    });
                                    return postData;

                                }
                            }
                        );
                    },
                    subGridRowColapsed: function (subgrid_id, row_id) {
                        // this function is called before removing the data
                        //var subgrid_table_id;
                        //subgrid_table_id = subgrid_id+"_t";
                        //jQuery("#"+subgrid_table_id).remove();
                        let subgridTable = subgrid_id + "table";
                        $('#subgridTable').remove();
                    }
                });
            jQuery("#tt").jqGrid('navGrid', '#pager', {
                    add: true,
                    edit: true,
                    del: false
                },
                {
                    closeAfterEdit: true,
                    // frm ---> 表单对象
                    beforeShowForm: function (frm) {
                        frm.find("#cover").attr("disabled", true);
                    },
                },
                {
                    closeAfterAdd: true,
                    afterSubmit: function (response, postData) {
                        let id = response.responseJSON.id;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/upload",
                            datatype: "json",
                            type: "post",
                            data: {id: id},
                            // 指定的上传input框的id
                            fileElementId: "cover",
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
        }


    </script>


            <div class="col-sm-10">
                <table id="tt"></table>
                <div id="pager" style="height: 30px"></div>


            </div>