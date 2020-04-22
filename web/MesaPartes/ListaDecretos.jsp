<%-- 
    Document   : ListaDecretos
    Created on : 03/04/2020, 01:00:02 AM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var periodo = $("#cbo_Periodo").val();
    var tipo = 'E';
    var mes = $("#cbo_Mes").val();
    var codigo = null;
    var archivo = null;
    var decreto = null;
    var mode = null;
    var msg = '';
    var lista = new Array();
    <c:forEach var="d" items="${objDecretos}">
    var result = {numero: '${d.numero}', decreto: '${d.decreto}', numeroDocumento: '${d.numeroDocumento}', asunto: '${d.asunto}',
        institucion: '${d.institucion}', prioridad: '${d.prioridad}', fecha: '${d.fecha}', comentario: '${d.postFirma}',
        referencia: "${d.referencia}", responsable: '${d.usuarioResponsable}',
        archivo: '${d.archivo}'};
    lista.push(result);
    </c:forEach>
    $(document).ready(function () {
        //PARA CARGAR LOS ELEMENTOS DEL DETALLE
        var sourceDetalle = {
            datatype: "array",
            datafields:
                    [
                        {name: "detalle", type: "number"},
                        {name: "decretador", type: "string"},
                        {name: "usuario", type: "string"},
                        {name: "prioridad", type: "string"},
                        {name: "comentario", type: "string"},
                        {name: "fechaDecreto", type: "date", format: 'dd/MM/yyyy'},
                        {name: "fechaRecepcion", type: "date", format: 'dd/MM/yyyy'},
                        {name: "estado", type: "string"}
                    ],
            addrow: function (rowid, rowdata, position, commit) {
                commit(true);
            }
        };
        var dataDetalle = new $.jqx.dataAdapter(sourceDetalle);
        //PARA CARGAR LOS ELEMENTOS DE LA GRILLA
        var source = {
            localdata: lista,
            datatype: "array",
            datafields:
                    [
                        {name: 'numero', type: "string"},
                        {name: 'decreto', type: "string"},
                        {name: 'numeroDocumento', type: "string"},
                        {name: 'asunto', type: "string"},
                        {name: 'institucion', type: "string"},
                        {name: 'prioridad', type: "string"},
                        {name: 'fecha', type: "date", format: 'dd/MM/yyyy'},
                        {name: 'comentario', type: "string"},
                        {name: 'referencia', type: "string"},
                        {name: 'responsable', type: "string"},
                        {name: 'archivo', type: "string"}
                    ],
            root: "Decretos",
            record: "Decretos",
            id: 'numero'
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        //ESTILOS A LAS CELDAS DE LA GRILLA 
        var cellclass = function (row, datafield, value, rowdata) {
            if (datafield === "numero") {
                return "RowBold";
            }
            if (datafield === "institucion") {
                return "RowPurple";
            }
            if (datafield === "responsable") {
                return "RowBlue";
            }
        };
        var cellclassDet = function (row, datafield, value, rowdata) {
            if (rowdata['estado'] === "DOC") {
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
                    $("#div_GrillaPrincipal").jqxGrid('exportdata', 'xls', 'DecretoDocumentos');
                });
            },
            columns: [
                {text: 'CÓDIGO', dataField: 'numero', width: '5%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'PRIORIDAD', dataField: 'prioridad', filtertype: 'checkedlist', width: '8%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'INSTITUCIÓN', dataField: 'institucion', width: '10%', align: 'center', cellsAlign: 'center', cellclassname: cellclass},
                {text: 'DOCUMENTO', dataField: 'numeroDocumento', width: '9%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'FEC. DOC', dataField: 'fecha', columntype: 'datetimeinput', filtertype: 'date', width: '7%', align: 'center', cellsAlign: 'center', cellsFormat: 'd', cellclassname: cellclass},
                {text: 'ASUNTO', dataField: 'asunto', width: '25%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'DECRETO', dataField: 'comentario', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'RESPONSABLE', dataField: 'responsable', filtertype: 'checkedlist', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'REFERENCIA', dataField: 'referencia', width: '15%', align: 'center', cellsAlign: 'center', cellclassname: cellclass}
            ]
        });
        //DEFINIMOS CAMPOS DE LA GRILLA DE CONSULTA
        $("#div_GrillaDetalle").jqxGrid({
            width: '100%',
            height: 350,
            source: dataDetalle,
            pageable: true,
            columnsresize: true,
            altrows: false,
            editable: false,
            statusbarheight: 25,
            autoheight: false,
            autorowheight: false,
            sortable: true,
            columns: [
                {text: ' ', sortable: false, filterable: false, editable: false, groupable: false, draggable: false, resizable: false,
                    datafield: '', columntype: 'number', width: '3%', pinned: true, cellsrenderer: function (row, column, value) {
                        return "<div style='margin:4px; text-align: center;'>" + (value + 1) + "</div>";
                    }
                },
                {text: 'DECRETADOR', datafield: 'decretador', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclassDet},
                {text: 'PRIORIDAD', datafield: 'prioridad', width: '13%', align: 'center', cellsAlign: 'center', cellclassname: cellclassDet},
                {text: 'COMENTARIO', datafield: 'comentario', width: '30%', align: 'center', cellsAlign: 'left', cellclassname: cellclassDet},
                {text: 'RESPONSABLE', datafield: 'usuario', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclassDet},
                {text: 'FECHA', datafield: 'fechaDecreto', columntype: 'datetimeinput', filtertype: 'date', width: '12%', align: 'center', cellsAlign: 'center', cellsFormat: 'd', cellclassname: cellclassDet},
                {text: 'RECEPCIÓN', datafield: 'fechaRecepcion', columntype: 'datetimeinput', filtertype: 'date', width: '12%', align: 'center', cellsAlign: 'center', cellsFormat: 'd', cellclassname: cellclassDet},
                {text: 'ESTADO', datafield: 'estado', width: '10%', align: 'center', cellsAlign: 'center', cellclassname: cellclassDet}
            ]
        });
        // DEFINIMOS EL MENU CONTEXTUAL
        var alto = 115;
        var contextMenu = $("#div_ContextMenu").jqxMenu({width: 200, height: alto, autoOpenPopup: false, mode: 'popup'});
        $("#div_GrillaPrincipal").on('contextmenu', function () {
            return false;
        });
        // HABILITAMOS LA OPCION DE CLICK DEL MENU CONTEXTUAL.
        $('#div_GrillaPrincipal').on('rowclick', function (event) {
            if (event.args.rightclick) {
                $("#div_GrillaPrincipal").jqxGrid('selectrow', event.args.rowindex);
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                if (parseInt(event.args.originalEvent.clientY) > 600) {
                    scrollTop = scrollTop - alto;
                }
                contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);
                return false;
            }
        });
        //DEFINIMOS LOS EVENTOS SEGUN LA OPCION DEL MENU CONTEXTUAL      
        $("#div_ContextMenu").on('itemclick', function (event) {
            var opcion = event.args;
            if ($.trim($(opcion).text()) === "Editar") {
                mode = 'U';
                fn_EditarRegistro();
            } else if ($.trim($(opcion).text()) === "Eliminar") {
                $.confirm({
                    title: 'AVISO DEL SISTEMA',
                    content: 'Desea Eliminar este Decreto!',
                    theme: 'material',
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'red',
                    typeAnimated: true,
                    buttons: {
                        aceptar: {
                            text: 'Aceptar',
                            btnClass: 'btn-primary',
                            keys: ['enter', 'shift'],
                            action: function () {
                                mode = 'D';
                                fn_GrabarDatos();
                            }
                        },
                        cancelar: function () {
                        }
                    }
                });
            } else if ($.trim($(opcion).text()) === "Seguimiento Decreto") {
                fn_SeguimientoDecreto();
            } else if ($.trim($(opcion).text()) === "Ver Documento") {
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
            } else {
                $.alert({
                    theme: 'material',
                    title: 'AVISO DEL SISTEMA',
                    content: "No hay Opción a mostrar",
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'orange',
                    typeAnimated: true
                });
            }
        });
        //SELECCIONAMOS UN REGISTRO DE LA GRILLA
        $("#div_GrillaPrincipal").on('rowselect', function (event) {
            var args = event.args;
            var row = $("#div_GrillaPrincipal").jqxGrid('getrowdata', args.rowindex);
            codigo = row['numero'];
            decreto = row['decreto'];
            archivo = row['archivo'];
        });
        //CREA LOS ELEMENTOS DE LAS VENTANAS
        var customButtonsDemo = (function () {
            function _createElements() {
                //INICIA LOS VALORES DE LA VENTANA
                var posicionX, posicionY;
                var ancho = 550;
                var alto = 275;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_VentanaPrincipal').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_Cancelar'),
                    initContent: function () {
                        $("#cbo_UsuarioEmision").jqxDropDownList({animationType: 'fade', width: 350, height: 20});
                        $("#cbo_AreaLaboral").jqxDropDownList({animationType: 'fade', width: 200, height: 20, dropDownWidth: 250});
                        $('#cbo_AreaLaboral').on('change', function () {
                            var codigo = $("#cbo_AreaLaboral").val();
                            $("#cbo_Usuario").jqxDropDownList('clear');
                            fn_cargarComboAjax("#cbo_Usuario", {mode: 'usuarioAreaLaboral', codigo: codigo});
                        });
                        $("#cbo_Prioridad").jqxDropDownList({animationType: 'fade', width: 200, height: 20, dropDownWidth: 250});
                        $("#cbo_Usuario").jqxDropDownList({animationType: 'fade', width: 350, height: 20, dropDownWidth: 450});
                        $("#cbo_TipoDecretos").jqxDropDownList({animationType: 'fade', checkboxes: true, width: 440, height: 20, dropDownWidth: 500});
                        $("#txt_Comentario").jqxInput({placeHolder: "Ingrese un Comentario", height: 80, width: 440, minLength: 1});
                        $('#btn_Cancelar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').on('click', function () {
                            var msg = "";
                            msg += fn_validaCombos('#cbo_UsuarioEmision', "Seleccione el Decretador.");
                            msg += fn_validaCombos('#cbo_Prioridad', "Seleccione la Prioridad.");
                            msg += fn_validaCombos('#cbo_AreaLaboral', "Seleccione el Area Laboral.");
                            msg += fn_validaCombos('#cbo_Usuario', "Seleccione el Usuario Responsable.");
                            msg += fn_validaCombos('#cbo_TipoDecretos', "Seleccione los Tipos de Decretos.");
                            if (msg === "") {
                                $('#frm_Decreto').jqxValidator('validate');
                            } else {
                                $.alert({
                                    theme: 'material',
                                    title: 'Aviso del Sistema',
                                    content: msg,
                                    type: 'blue',
                                    animation: 'zoom',
                                    closeAnimation: 'zoom',
                                    typeAnimated: true,
                                    boxWidth: '50%'
                                });
                            }
                        });
                        $('#frm_Decreto').jqxValidator({
                            rules: [
                                {input: '#txt_Comentario', message: 'Ingrese un comentatio', action: 'keyup, blur', rule: 'required'}
                            ]
                        });
                        $('#frm_Decreto').jqxValidator({
                            onSuccess: function () {
                                fn_GrabarDatos();
                            }
                        });
                    }
                });
                //INICIALIZAMOS LOS VALORES DE LA VENTANA DE DETALLE
                var ancho = 750;
                var alto = 430;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_VentanaDetalle').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_Salir'),
                    initContent: function () {
                        $('#btn_Salir').jqxButton({width: '65px', height: 25});
                    }
                });
            }
            return {init: function () {
                    _createElements();
                }
            };
        }());
        $(document).ready(function () {
            customButtonsDemo.init();
        });
        //FUNCION PARA VER EL SEGUIMIENTO DEL DECRETO
        function fn_SeguimientoDecreto() {
            $('#div_GrillaDetalle').jqxGrid('clear');
            $.ajax({
                type: "GET",
                url: "../Decretos",
                data: {mode: 'S', periodo: periodo, tipo: tipo, numero: codigo},
                success: function (data) {
                    data = data.replace("[", "");
                    var fila = data.split("[");
                    var rows = new Array();
                    for (i = 1; i < fila.length; i++) {
                        var columna = fila[i];
                        var datos = columna.split("+++");
                        var index = 7;
                        while (datos[index].indexOf(']') > 0) {
                            datos[index] = datos[index].replace("]", "");
                        }
                        while (datos[index].indexOf(',') > 0) {
                            datos[index] = datos[index].replace(",", "");
                        }
                        var row = {detalle: datos[0], decretador: datos[1], usuario: datos[2], 
                            prioridad: datos[3], comentario: datos[4],
                            fechaDecreto: datos[5], fechaRecepcion: datos[6], estado: datos[7]};
                        rows.push(row);
                    }
                    if (rows.length > 0) {
                        $("#div_GrillaDetalle").jqxGrid('addrow', null, rows);
                    }
                }
            });
            $('#div_VentanaDetalle').jqxWindow({isModal: true});
            $('#div_VentanaDetalle').jqxWindow('open');
        }
        //FUNCION PARA DECRETAR DOCUMENTACION
        function fn_EditarRegistro() {
            $.ajax({
                type: "GET",
                url: "../Decretos",
                data: {mode: mode, periodo: periodo, tipo: tipo, numero: codigo, codigo: decreto},
                success: function (data) {
                    var dato = data.split("+++");
                    if (dato.length === 5) {
                        $("#cbo_AreaLaboral").jqxDropDownList('selectItem', dato[2]);
                        $("#cbo_UsuarioEmision").jqxDropDownList('selectItem', dato[0]);
                        $("#cbo_Prioridad").jqxDropDownList('selectItem', dato[1]);
                        $("#txt_Comentario").val(dato[4]);
                        $("#cbo_TipoDecretos").jqxDropDownList('uncheckAll');
                        $.ajax({
                            type: "GET",
                            url: "../Decretos",
                            data: {mode: 'T', periodo: periodo, tipo: tipo, numero: codigo, codigo: decreto},
                            success: function (data) {
                                data = data.replace("[", "");
                                data = data.replace("]", "");
                                var fila = data.split(",");
                                for (i = 0; i < fila.length; i++) {
                                    $("#cbo_TipoDecretos").jqxDropDownList('checkIndex', fila[i]);
                                }
                                $("#cbo_Usuario").jqxDropDownList('selectItem', dato[3]);
                            }
                        });
                        $('#div_VentanaPrincipal').jqxWindow({isModal: true});
                        $('#div_VentanaPrincipal').jqxWindow('open');
                    }
                }
            });
        }
        //FUNCION PARA ACTUALIZAR DATOS DE LA GRILLA
        function fn_Refrescar() {
            $("#div_GrillaPrincipal").remove();
            $("#div_VentanaPrincipal").remove();
            $("#div_VentanaDetalle").remove();
            $("#div_ContextMenu").remove();
            var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
            $.ajax({
                type: "POST",
                url: "../Decretos",
                data: {mode: 'A', periodo: periodo, mes: mes, tipo: tipo},
                success: function (data) {
                    $contenidoAjax.html(data);
                }
            });
        }
        //FUNCION PARA GRABAR LOS DATOS DE LA VENTANA PRINCIPAL
        function fn_GrabarDatos() {
            var usuarioEmision = $("#cbo_UsuarioEmision").val();
            var prioridad = $("#cbo_Prioridad").val();
            var area = $("#cbo_AreaLaboral").val();
            var usuario = $("#cbo_Usuario").val();
            var comentario = $("#txt_Comentario").val();
            var tipoDecretos = $("#cbo_TipoDecretos").jqxDropDownList('getCheckedItems');
            var result = "";
            var lista = new Array();
            $.each(tipoDecretos, function (index) {
                result = this.value;
                lista.push(result);
            });
            $.ajax({
                type: "POST",
                url: "../IduDecretos",
                data: {mode: mode, periodo: periodo, tipo: tipo, numero: codigo, decreto: decreto,
                    usuarioEmision: usuarioEmision, prioridad: prioridad, area: area,
                    usuario: usuario, comentario: comentario, lista: JSON.stringify(lista)},
                success: function (data) {
                    msg = data;
                    if (msg === "GUARDO") {
                        $.confirm({
                            title: 'AVISO DEL SISTEMA',
                            content: 'Datos procesados correctamente',
                            type: 'green',
                            typeAnimated: true,
                            autoClose: 'cerrarAction|1000',
                            buttons: {
                                cerrarAction: {
                                    text: 'Cerrar',
                                    action: function () {
                                        $('#div_VentanaPrincipal').jqxWindow('close');
                                        fn_Refrescar();
                                    }
                                }
                            }
                        });
                    } else {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: msg,
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    }
                }
            });
        }
    });
</script>
<div id="div_GrillaPrincipal"></div>
<div id="div_VentanaPrincipal" style="display: none">
    <div>
        <span style="float: left">DECRETAR DOCUMENTACIÓN</span>
    </div>
    <div style="overflow: hidden">
        <form id="frm_Decreto" name="frm_Decreto" method="post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="inputlabel">Decretado por : </td>
                    <td>
                        <select id="cbo_UsuarioEmision" name="cbo_UsuarioEmision">
                            <c:forEach var="d" items="${objUsuarioJefatura}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Prioridad : </td>
                    <td>
                        <select id="cbo_Prioridad" name="cbo_Prioridad">
                            <c:forEach var="d" items="${objPrioridades}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Area : </td>
                    <td>
                        <select id="cbo_AreaLaboral" name="cbo_AreaLaboral">
                            <c:forEach var="d" items="${objAreaLaboral}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Usuario : </td>
                    <td>
                        <select id="cbo_Usuario" name="cbo_Usuario">
                            <option value="0">Seleccione</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Decretos : </td>
                    <td>
                        <select id="cbo_TipoDecretos" name="cbo_TipoDecretos">
                            <c:forEach var="d" items="${objTipoDecretos}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Comentario : </td>
                    <td><textarea id="txt_Comentario" name="txt_Comentario" style="text-transform: uppercase;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="Summit" colspan="4">
                        <div>
                            <input type="button" id="btn_Guardar"  value="Guardar" style="margin-right: 20px"/>
                            <input type="button" id="btn_Cancelar" value="Cancelar" style="margin-right: 20px"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="div_VentanaDetalle" style="display: none">
    <div>
        <span style="float: left">SEGUIMIENTO DE LA DOCUMENTACIÓN</span>
    </div>
    <div style="overflow: hidden">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td colspan="4"><div id="div_GrillaDetalle"> </div></div></td>  
            </tr>
            <tr>
                <td class="Summit" colspan="4">
                    <div>
                        <input type="button" id="btn_Salir" value="Salir" style="margin-right: 20px"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id='div_ContextMenu' style='display:none;'>
    <ul>
        <li>Editar</li>
        <li style="color: red">Eliminar</li>
        <li type='separator'></li>
        <li style="color: blue">Seguimiento Decreto</li>
        <li type='separator'></li>
        <li>Ver Documento</li>
    </ul>
</div>
