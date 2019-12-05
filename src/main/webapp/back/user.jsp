<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
    <script>
        $(function () {
            $("#tt").jqGrid(
                {
                    url: "${pageContext.request.contextPath}/user/showUser",
                    datatype: "json",
                    colNames: ['编号', '手机号', '密码', '状态', '头像', '姓名', '法号', '性别','标签','地址','注册时间','最后登录时间','操作'],
                    colModel: [
                        {name: 'id', align: "center", hidden: true},
                        {name: 'phone', align: "center", hidden: true},
                        {name: 'password', align: "center"},
                        {name: 'status', align: "center"},
                        {name: 'photo', align: "center"},
                        {name: 'name', align: "center"},
                        {name: 'nick_name', align: "center"},
                        {name: 'sex', align: "center"},
                        {name: 'sign', align: "center"},
                        {name: 'location', align: "center"},
                        {name: 'rigest_date', align: "center"},
                        {name: 'last_login', align: "center"},
                        {
                            name: 'option', formatter: function (cellvalue, options, rowObject) {
                                var result = '';
                                result += "<a href='javascript:void(0)' onclick=\"changeStatus('" + rowObject.id + "')\" class='btn btn-lg' title='查看详情'> <span class='glyphicon glyphicon-th-list'></span></a>";
                                return result;
                            }
                        },
                    ],
                    rowNum: 5,
                    rowList: [5, 10, 20],
                    pager: '#pager',
                    mtype: "post",
                    viewrecords: true,
                    sortorder: "desc",
                    styleUI: "Bootstrap",
                    autowidth: true,
                    multiselect: true,
                    height: "500px",
                    editurl: "${pageContext.request.contextPath}/article/edit"
                });
            $("#tt").jqGrid('navGrid', "#pager", {
                add: false,
                edit: false,
                del: true,
                deltext: "删除"
            });
        });

        function changeStatus(id) {
            let data = $("#tt").jqGrid("getRowData", id);
            $.post('${path}/user/edit', {id: data.id,status:data.status},(result)=>{
                $("#tt").trigger("reloadGrid");
            });

        }


    </script>
    <div class="col-sm-10">
        <table id="tt"></table>
        <div id="pager" style="height: 30px"></div>
    </div>

