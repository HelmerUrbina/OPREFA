<%-- 
    Document   : ListaConsultaPlanilla
    Created on : 12/07/2020, 11:56:41 PM
    Author     : H-URBIN-M
--%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var lista = new Array();
    <c:forEach var="d" items="${objConsultaPlanillas}">
    var result = {periodo: '${d.periodo}', mes: '${d.mes}', tipoPlanilla: '${d.tipoPlanilla}',
        clasePlanilla: '${d.clasePlanilla}', correlativo: '${d.correlativo}', tipoDocumento: '${d.tipoDocumento}',
        numeroDocumento: '${d.numeroDocumento}', nombres: '${d.nombres}',
        monto: '${d.monto}', banco: '${d.banco}', cuenta: '${d.cuentaBancaria}', formaPago: '${d.formaPago}'};
    lista.push(result);
    </c:forEach>
    $(document).ready(function () {
        //PARA CARGAR LOS ELEMENTOS DE LA GRILLA
        var source = {
            localdata: lista,
            datatype: "array",
            datafields:
                    [
                        {name: 'periodo', type: "string"},
                        {name: 'mes', type: "string"},
                        {name: 'tipoPlanilla', type: "string"},
                        {name: 'clasePlanilla', type: "string"},
                        {name: 'correlativo', type: "string"},
                        {name: 'tipoDocumento', type: "string"},
                        {name: 'numeroDocumento', type: "string"},
                        {name: 'nombres', type: "string"},
                        {name: 'monto', type: "number"},
                        {name: 'banco', type: "string"},
                        {name: 'cuenta', type: "string"},
                        {name: 'formaPago', type: "string"}
                    ],
            root: "ConsultaPlanillas",
            record: "ConsultaPlanillas",
            id: 'correlativo'
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        //ESTILOS A LAS CELDAS DE LA GRILLA 
        var cellclass = function (row, datafield, value, rowdata) {
            if (datafield === "monto") {
                return "RowBold";
            }
            if (datafield === "nombres") {
                return "RowBlue";
            }
            if (datafield === "numeroDocumento") {
                return "RowBrown";
            }
        };
        //DEFINIMOS LOS CAMPOS Y DATOS DE LA GRILLA
        $("#div_GrillaPrincipal").jqxGrid({
            width: '99.8%',
            height: ($(window).height() - 60),
            source: dataAdapter,
            autoheight: false,
            autorowheight: false,
            altrows: true,
            showtoolbar: true,
            sortable: true,
            pageable: true,
            filterable: true,
            autoshowfiltericon: true,
            columnsresize: true,
            showfilterrow: true,
            editable: false,
            rendertoolbar: function (toolbar) {
                var container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
                var ButtonExportar = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/pauf42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                container.append(ButtonExportar);
                toolbar.append(container);
                ButtonExportar.jqxButton({width: 30, height: 22});
                ButtonExportar.jqxTooltip({position: 'bottom', content: "Exportar Datos"});
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON EXPORTAR
                ButtonExportar.click(function (event) {
                    $("#div_GrillaPrincipal").jqxGrid('exportdata', 'xls', 'ConsultaPlanillas');
                });
            },
            columns: [
                {text: 'PERIODO', dataField: 'periodo', filtertype: 'checkedlist', width: '4%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'MES', dataField: 'mes', filtertype: 'checkedlist', width: '4%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'TIPO PLANILLA', dataField: 'tipoPlanilla', filtertype: 'checkedlist',width: '9%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'CLASE PLANILLA', dataField: 'clasePlanilla', filtertype: 'checkedlist', width: '10%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'CORRELATIVO', dataField: 'correlativo',  width: '6%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'T/DOC', dataField: 'tipoDocumento', filtertype: 'checkedlist', width: '5%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'NRO. DOC.', dataField: 'numeroDocumento',  width: '7%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'APELLIDOS Y NOMBRES', dataField: 'nombres', width: '25%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'MONTO ', dataField: 'monto', width: "10%", align: 'center', cellsAlign: 'right', cellsFormat: 'f2', cellclassname: cellclass},
                {text: 'BANCO', dataField: 'banco', filtertype: 'checkedlist', width: '8%', align: 'center', cellsAlign: 'center', cellsFormat: 'f', cellclassname: cellclass},
                {text: 'CUENTA', dataField: 'cuenta', width: '15%', align: 'center', cellsAlign: 'center', cellsFormat: 'f', cellclassname: cellclass},
                {text: 'F. PAGO', dataField: 'formaPago', filtertype: 'checkedlist', width: '12%', align: 'center', cellsAlign: 'center', cellclassname: cellclass}
            ]
        });
        // DEFINIMOS EL MENU CONTEXTUAL 
        var contextMenu = $("#div_ContextMenu").jqxMenu({width: 200, height: 30, autoOpenPopup: false, mode: 'popup'});
        $("#div_GrillaPrincipal").on('contextmenu', function () {
            return false;
        });
        // HABILITAMOS LA OPCION DE CLICK DEL MENU CONTEXTUAL.
        $("#div_GrillaPrincipal").on('rowclick', function (event) {
            if (event.args.rightclick) {
                $("#div_GrillaPrincipal").jqxGrid('selectrow', event.args.rowindex);
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
                return false;
            }
        });
        //DEFINIMOS LOS EVENTOS SEGUN LA OPCION DEL MENU CONTEXTUAL
        $("#div_ContextMenu").on('itemclick', function (event) {
            var opcion = event.args;
            if ($.trim($(opcion).text()) === "Ver Documento") {
                if (archivo !== null && archivo !== '') {
                    document.location.target = "_blank";
                    document.location.href = "../Descarga?opcion=MesaPartes&periodo=" + periodo + "&codigo=" + tipo + "-" + codigo + "&documento=" + archivo;
                } else {
                    $.alert({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: 'No existe Archivo a Vizualizar!!!',
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'red',
                        typeAnimated: true
                    });
                }
            }
        });
        //SELECCIONAMOS UN REGISTRO DE LA GRILLA
        $("#div_GrillaPrincipal").on('rowselect', function (event) {
            var args = event.args;
            var row = $("#div_GrillaPrincipal").jqxGrid('getrowdata', args.rowindex);
            codigo = row['numero'];
            archivo = row['archivo'];
        });
    });
</script>
<div id="div_GrillaPrincipal"></div>
<div id='div_ContextMenu' style='display:none;'>
    <ul>   
        <li style="font-weight: bold">Ver Documento</li>
    </ul>
</div>