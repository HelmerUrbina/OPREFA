<%-- 
    Document   : ListaMenu
    Created on : 18/03/2020, 11:42:06 PM
    Author     : H-URBINA-M
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    var autorizacion = '${autorizacion}';
    var codigo = null;
    var mode = null;
    var msg = "";
    var lista = new Array();
    <c:forEach var="c" items="${objMenus}">
    var result = {codigo: '${c.codigo}', modulo: '${c.modulo}', nombre: '${c.nombre}',
        servlet: '${c.servlet}', modo: '${c.modo}', estado: "${c.estado}"};
    lista.push(result);
    </c:forEach>
    $(document).ready(function () {
        //PARA CARGAR LOS ELEMENTOS DE LA GRILLA
        var source = {
            localdata: lista,
            datatype: "array",
            datafields:
                    [
                        {name: 'codigo', type: "string"},
                        {name: 'modulo', type: "string"},
                        {name: 'nombre', type: "string"},
                        {name: 'servlet', type: "string"},
                        {name: 'modo', type: "string"},
                        {name: 'estado', type: "string"}
                    ],
            root: "Menu",
            record: "Menu",
            id: 'codigo'
        };
        var dataAdapter = new $.jqx.dataAdapter(source);
        //ESTILOS A LAS CELDAS DE LA GRILLA 
        var cellclass = function (row, datafield, value, rowdata) {
            if (datafield === "codigo") {
                return "RowBold";
            }
        };
        //DEFINIMOS LOS CAMPOS Y DATOS DE LA GRILLA
        $("#div_GrillaPrincipal").jqxGrid({
            width: '99.8%',
            height: ($(window).height() - 32),
            source: dataAdapter,
            autoheight: false,
            autorowheight: false,
            altrows: true,
            sortable: true,
            pageable: true,
            filterable: true,
            autoshowfiltericon: true,
            columnsresize: true,
            showfilterrow: true,
            showtoolbar: true,
            editable: false,
            rendertoolbar: function (toolbar) {
                // ADICIONAMOS BOTONES A LA BARRA DE ESTADOS
                var container = $("<div style='overflow: hidden; position: relative; margin: 1px;'></div>");
                var ButtonNuevo = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/nuevo42.gif' width=18 height=18 /><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonExportar = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/pauf42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonRecargar = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/refresh42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                var ButtonSalir = $("<div style='float: left; margin-left: 5px;'><img style='position: relative; margin-top: 2px;' src='../Imagenes/Botones/exit42.gif' width=18 height=18/><span style='margin-left: 4px; position: relative; top: -3px;'> </span></div>");
                container.append(ButtonNuevo);
                container.append(ButtonExportar);
                container.append(ButtonRecargar);
                container.append(ButtonSalir);
                toolbar.append(container);
                ButtonNuevo.jqxButton({width: 30, height: 22});
                ButtonNuevo.jqxTooltip({position: 'bottom', content: "Nuevo Registro"});
                ButtonExportar.jqxButton({width: 30, height: 22});
                ButtonExportar.jqxTooltip({position: 'bottom', content: "Exportar Datos"});
                ButtonRecargar.jqxButton({width: 30, height: 22});
                ButtonRecargar.jqxTooltip({position: 'bottom', content: "Recargar"});
                ButtonSalir.jqxButton({width: 30, height: 22});
                ButtonSalir.jqxTooltip({position: 'bottom', content: "Salir de la Pantalla"});
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON NUEVO
                ButtonNuevo.click(function (event) {
                    if (autorizacion !== 'true') {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: 'Usuario no Autorizado para realizar este Tipo de Operaci�n',
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    } else {
                        mode = 'I';
                        codigo = '00';
                        $("#cbo_Modulo").jqxDropDownList('selectItem', '0');
                        $("#cbo_Modulo").jqxDropDownList({disabled: false});
                        $("#txt_Nombre").val('');
                        $("#txt_Servlet").val('');
                        $("#txt_Modo").val('');
                        $("#cbo_Estado").jqxDropDownList('selectItem', 'AC');
                        $('#div_VentanaPrincipal').jqxWindow({isModal: true, modalOpacity: 0.8});
                        $('#div_VentanaPrincipal').jqxWindow('open');
                    }
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON EXPORTAR
                ButtonExportar.click(function (event) {
                    $("#div_GrillaPrincipal").jqxGrid('exportdata', 'xls', 'Listado de Menus');
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON RECARGAR
                ButtonRecargar.click(function (event) {
                    fn_Refrescar();
                });
                //ASIGNAMOS LAS FUNCIONES PARA EL BOTON SALIR
                ButtonSalir.click(function (event) {
                    window.location.reload();
                });
            },
            columns: [
                {text: ' ', sortable: false, filterable: false, editable: false, groupable: false, draggable: false, resizable: false,
                    datafield: '', columntype: 'number', width: '2%', pinned: true, cellsrenderer: function (row, column, value) {
                        return "<div style='margin:4px; text-align: center;'>" + (value + 1) + "</div>";
                    }
                },
                {text: 'M�DULO', dataField: 'modulo', filtertype: 'list', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'NOMBRE', dataField: 'nombre', width: '23%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'SERVLET', dataField: 'servlet', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'MODO', dataField: 'modo', width: '20%', align: 'center', cellsAlign: 'left', cellclassname: cellclass},
                {text: 'ESTADO', dataField: 'estado', filtertype: 'list', width: '15%', align: 'center', cellsAlign: 'center', cellclassname: cellclass}
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
            if (codigo === null || codigo === '') {
                $.alert({
                    theme: 'material',
                    title: 'AVISO DEL SISTEMA',
                    content: 'Debe Seleccionar un Registro',
                    animation: 'zoom',
                    closeAnimation: 'zoom',
                    type: 'orange',
                    typeAnimated: true
                });
            } else {
                if (autorizacion !== 'true') {
                    $.alert({
                        theme: 'material',
                        title: 'AVISO DEL SISTEMA',
                        content: 'Usuario no Autorizado para realizar este Tipo de Operaci�n',
                        animation: 'zoom',
                        closeAnimation: 'zoom',
                        type: 'red',
                        typeAnimated: true
                    });
                } else {
                    if ($.trim($(opcion).text()) === "Editar") {
                        mode = 'U';
                        fn_EditarRegistro();
                    } else {
                        $.alert({
                            theme: 'material',
                            title: 'AVISO DEL SISTEMA',
                            content: 'Opci�n NO VALIDA!!',
                            animation: 'zoom',
                            closeAnimation: 'zoom',
                            type: 'red',
                            typeAnimated: true
                        });
                    }
                }
            }
        });
        //SELECCIONAMOS UN REGISTRO DE LA GRILLA
        $("#div_GrillaPrincipal").on('rowselect', function (event) {
            var args = event.args;
            var row = $("#div_GrillaPrincipal").jqxGrid('getrowdata', args.rowindex);
            codigo = row['codigo'];
        });
        //CREA LOS ELEMENTOS DE LAS VENTANAS
        var customButtonsDemo = (function () {
            function _createElements() {
                //INICIA LOS VALORES DE LA VENTANA
                var posicionX, posicionY;
                var ancho = 500;
                var alto = 190;
                posicionX = ($(window).width() / 2) - (ancho / 2);
                posicionY = ($(window).height() / 2) - (alto / 2);
                $('#div_VentanaPrincipal').jqxWindow({
                    position: {x: posicionX, y: posicionY},
                    width: ancho, height: alto, resizable: false,
                    cancelButton: $('#btn_Cancelar'),
                    initContent: function () {
                        $("#cbo_Modulo").jqxDropDownList({width: 200, height: 20, promptText: "Seleccione"});
                        $("#txt_Nombre").jqxInput({placeHolder: "Nombre", width: 390, height: 20, minLength: 2, maxLength: 50});
                        $("#txt_Servlet").jqxInput({placeHolder: "Servlet", width: 390, height: 20, minLength: 2, maxLength: 100});
                        $("#txt_Modo").jqxInput({placeHolder: "Modo", width: 390, height: 20, minLength: 2, maxLength: 50});
                        $("#cbo_Estado").jqxDropDownList({width: 200, height: 20, promptText: "Seleccione"});
                        $('#btn_Cancelar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').jqxButton({width: '65px', height: 25});
                        $('#btn_Guardar').on('click', function () {
                            msg = "";
                            msg += fn_validaCombos('#cbo_Modulo', "Seleccione el Modulo.");
                            if (msg === "") {
                                $('#frm_Menu').jqxValidator('validate');
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
                        $('#frm_Menu').jqxValidator({
                            rules: [
                                {input: '#txt_Nombre', message: 'Ingrese la Descripci�n de la Menu!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Servlet', message: 'Ingrese el Nombre del Servlet!', action: 'keyup, blur', rule: 'required'},
                                {input: '#txt_Modo', message: 'Ingrese el tipo de modo!', action: 'keyup, blur', rule: 'required'}
                            ]
                        });
                        $('#frm_Menu').jqxValidator({
                            onSuccess: function () {
                                fn_GrabarDatos();
                            }
                        });
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
        //FUNCION PARA ACTUALIZAR DATOS DE LA GRILLA
        function fn_Refrescar() {
            $("#div_GrillaPrincipal").remove();
            $("#div_VentanaPrincipal").remove();
            $("#div_ContextMenu").remove();
            var $contenidoAjax = $('#div_Detalle').html('<img src="../Imagenes/Fondos/cargando.gif">');
            $.ajax({
                type: "GET",
                url: "../Menu",
                data: {mode: 'G'},
                success: function (data) {
                    $contenidoAjax.html(data);
                }
            });
        }
        //FUNCION PARA CARGAR VENTANA PARA EDITAR REGISTRO
        function fn_EditarRegistro() {
            $.ajax({
                type: "GET",
                url: "../Menu",
                data: {mode: mode, codigo: codigo},
                success: function (data) {
                    var dato = data.split("+++");
                    if (dato.length === 6) {
                        $("#cbo_Modulo").jqxDropDownList('selectItem', dato[0]);
                        codigo = dato[1];
                        $("#txt_Nombre").val(dato[2]);
                        $("#txt_Servlet").val(dato[3]);
                        $("#txt_Modo").val(dato[4]);
                        $("#cbo_Estado").jqxDropDownList('selectItem', dato[5]);
                        $("#cbo_Modulo").jqxDropDownList({disabled: true});
                    }
                }
            });
            $('#div_VentanaPrincipal').jqxWindow({isModal: true});
            $('#div_VentanaPrincipal').jqxWindow('open');
        }
        //FUNCION PARA GRABAR LOS DATOS DE LA VENTANA PRINCIPAL
        function fn_GrabarDatos() {
            var modulo = $("#cbo_Modulo").val();
            var nombre = $("#txt_Nombre").val();
            var servlet = $("#txt_Servlet").val();
            var modo = $("#txt_Modo").val();
            var estado = $("#cbo_Estado").val();

            $.ajax({
                type: "POST",
                url: "../IduMenu",
                data: {mode: mode, codigo: codigo, modulo: modulo, nombre: nombre,
                    servlet: servlet, modo: modo, estado: estado},
                success: function (data) {
                    msg = data;
                    if (msg === "GUARDO") {
                        $.confirm({
                            title: 'AVISO DEL SISTEMA',
                            content: 'Datos procesados correctamente!!',
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
        <span style="float: left">DATOS DEL MEN� DEL SISTEMA</span>
    </div>
    <div style="overflow: hidden">
        <form id="frm_Menu" name="frm_Menu" method="post" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="inputlabel">M�dulo : </td>
                    <td>
                        <select id="cbo_Modulo" name="cbo_Modulo">
                            <option value="0">Seleccione</option> 
                            <c:forEach var="d" items="${objModulos}">
                                <option value="${d.codigo}">${d.descripcion}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="inputlabel">Nombre : </td>
                    <td><input id="txt_Nombre" name="txt_Nombre"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Servlet : </td>
                    <td><input id="txt_Servlet" name="txt_Servlet"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Modo : </td>
                    <td><input id="txt_Modo" name="txt_Modo"/></td>
                </tr>
                <tr>
                    <td class="inputlabel">Estado : </td>
                    <td>
                        <select id="cbo_Estado" name="cbo_Estado">
                            <option value="AC" selected>ACTIVO</option>
                            <option value="IN">INACTIVO</option>
                        </select>
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
<div id='div_ContextMenu' style='display: none;'>
    <ul>
        <li style="font-weight: bold;">Editar</li>
    </ul>
</div>