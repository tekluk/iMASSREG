<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.entity.Parcel"%>
<%@page import="java.util.ArrayList"%>
<%
    ArrayList<Parcel> parcelsInCommitted = (ArrayList<Parcel>) request.getAttribute("parcelsInCommitted");
    ArrayList<Parcel> printedParcels = (ArrayList<Parcel>) request.getAttribute("printedParcels");
    String minorCorrectionURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_MINOR_CORRECTION_PDC;
    String majorCorrectionURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_MAJOR_CORRECTION_PDC;
    String confirmedURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_CONFIRMED_PARCEL_PDC;
    String findurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_FIND_PARCEL_PDC;
    String filterprintedurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PRINTED_PARCEL_LIST_PDC;
    String filterurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PARCEL_LIST_PDC;
    String checkListurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PRINT_CHECKLIST_PDC;
    String reprintURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_REPRINT_PARCEL_PDC;
    String exportURL = request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_PRINTED_PDC;
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-3 col-lg-offset-5 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=CommonStorage.getText("welcome")%></h2>
        </div>
    </div>
    <div class="bs-example">
        <ul class="nav nav-tabs" style="margin-bottom: 15px;">
            <li <%=request.getSession().getAttribute("action").toString().equalsIgnoreCase(Constants.ACTION_PRINTED_PARCEL_LIST_PDC) ? "" : "class='active'"%>><a href="#correctionParcels" data-toggle="tab"><%=CommonStorage.getText("committed")%></a></li>
            <li <%=request.getSession().getAttribute("action").toString().equalsIgnoreCase(Constants.ACTION_PRINTED_PARCEL_LIST_PDC) ? "class='active'" : ""%>><a href="#printedParcels" data-toggle="tab"><%=CommonStorage.getText("printed")%></a></li>
            <li><a href="#checkList" data-toggle="tab"><%=CommonStorage.getText("check_list")%></a></li>
            <!--li class="disabled"><a>Disabled</a></li-->
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade <%=request.getSession().getAttribute("action").toString().equalsIgnoreCase(Constants.ACTION_PRINTED_PARCEL_LIST_PDC) ? "" : "active in"%>" id="correctionParcels">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-lg-7"><%=CommonStorage.getText("committed_parcels")%></div>
                                    <div class="col-lg-2" style="vertical-align: middle"><label style="float: right"><%=CommonStorage.getText("kebele")%></label></div>
                                    <div class="col-lg-3" style="vertical-align: middle">
                                        <select class="form-control" id="displayKebele" name="displayKebele">
                                            <%
                                                Option[] kebeles = MasterRepository.getInstance().getAllKebeles();
                                                //out.println("<option value = 'all'>" + CommonStorage.getText("select_a_kebele") + "</option>");
                                                for (int i = 0; i < kebeles.length; i++) {
                                                    out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body" >
                                <div>
                                    <table class="table table-striped table-bordered table-hover" id="dataTables" >
                                        <thead>
                                            <tr>
                                                <th><%=CommonStorage.getText("administrative_upi")%></th>
                                                <th><%=CommonStorage.getText("certificate_number")%></th>
                                                <th><%=CommonStorage.getText("has_dispute")%></th>
                                                <th><%=CommonStorage.getText("means_of_acquisition")%></th>
                                                <th><%=CommonStorage.getText("survey_date")%></th>
                                                <th><%=CommonStorage.getText("fix")%></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (int i = 0; i < parcelsInCommitted.size(); i++) {
                                                    if (i % 2 == 0) {
                                                        out.println("<tr class='odd gradeA'>");
                                                    } else {
                                                        out.println("<tr class='even gradeA'>");
                                                    }
                                                    out.println("<td>" + parcelsInCommitted.get(i).getUpi() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).getCertificateNumber() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).hasDisputeText() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).getMeansOfAcquisition() + "</td>");
                                                    out.println("<td>" + parcelsInCommitted.get(i).getSurveyDate() + "</td>");

                                                    out.print("<td>");
                                                    out.print("<a href = '#' class='takeActionButton' "
                                                            + "data-upi='" + parcelsInCommitted.get(i).getUpi() + "'>" + CommonStorage.getText("take_action") + "</a>");
                                                    out.println("</td>");
                                                }

                                            %>
                                        </tbody>
                                    </table>
                                </div> <!-- /.table-responsive -->
                            </div> <!-- /.panel-body -->
                        </div> <!-- /.panel -->
                    </div> <!-- /.col-lg-12 -->
                </div>
            </div>
            <div class="tab-pane fade" id="findParcel">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("find_a_parcel")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="findParcelForm" name="findParcelForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <input type="hidden" name="WoredaId" id="WoredaId" value="<%= CommonStorage.getCurrentWoredaId()%>" />
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("kebele")%></label>
                                            <select class="form-control" id="kebele" name="kebele">
                                                <%
                                                    for (int i = 0; i < kebeles.length; i++) {
                                                        out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group error" id="someId">
                                            <label><%=CommonStorage.getText("parcel_id")%></label>
                                            <input class="form-control" name = "parcelNo" id = "parcelNo" type="text" size="5" maxlength="5" required value="${sessionScope.parcelNo}" autocomplete="off" />
                                        </div>
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("administrative_upi")%></label>
                                            <input class="form-control " name = "upi" id = "upi" disabled required />
                                        </div>
                                        <button type='submit' id = 'findParcelButton' name = 'nextButton' class='btn btn-default col-lg-3' style='float:right'><%=CommonStorage.getText("find")%></button>
                                    </div> <!-- /.row (nested) -->
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="checkList">
                <div class="row">
                    <div class="col-lg-4 col-lg-offset-4">
                        <div class="panel panel-default" >
                            <div class="panel-heading">
                                <%=CommonStorage.getText("check_list")%>
                            </div>
                            <div class="panel-body" id="panelBody" >
                                <form role="form" action="#" method="POST" id="printCheckListForm" name="printCheckListForm" style="padding-left: 1em;padding-right: 1em">
                                    <div class="row">
                                        <input type="hidden" name="WoredaId" id="WoredaId" value="<%= CommonStorage.getCurrentWoredaId()%>" />
                                        <div class="form-group">
                                            <label><%=CommonStorage.getText("kebele")%></label>
                                            <select class="form-control" id="checkListKebele" name="checkListKebele">
                                                <%
                                                    for (int i = 0; i < kebeles.length; i++) {
                                                        out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <button type='submit' id = 'printCheckListButton' name = 'printCheckListButton' class='btn btn-default col-lg-3' style='float:right'><%=CommonStorage.getText("print")%></button>
                                    </div> <!-- /.row (nested) -->
                                </form>
                            </div> <!-- /.panel-body -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade <%=request.getSession().getAttribute("action").toString().equalsIgnoreCase(Constants.ACTION_PRINTED_PARCEL_LIST_PDC) ? "active in" : ""%>" id="printedParcels">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-lg-7"><%=CommonStorage.getText("printed_parcels")%></div>
                                    <div class="col-lg-2" style="vertical-align: middle"><label style="float: right"><%=CommonStorage.getText("kebele")%></label></div>
                                    <div class="col-lg-3" style="vertical-align: middle">
                                        <select class="form-control" id="printedParcelsDisplayKebele" name="printedParcelsDisplayKebele">
                                            <%
                                                kebeles = MasterRepository.getInstance().getAllKebeles();
                                                //out.println("<option value = 'all'>" + CommonStorage.getText("select_a_kebele") + "</option>");
                                                for (int i = 0; i < kebeles.length; i++) {
                                                    out.println("<option value = '" + kebeles[i].getKey() + "'>" + kebeles[i].getValue() + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body" >
                                <div>
                                    <table class="table table-striped table-bordered table-hover" id="printedParcelsDataTable" >
                                        <thead>
                                            <tr>
                                                <th><%=CommonStorage.getText("administrative_upi")%></th>
                                                <th><%=CommonStorage.getText("certificate_number")%></th>
                                                <th><%=CommonStorage.getText("has_dispute")%></th>
                                                <th><%=CommonStorage.getText("means_of_acquisition")%></th>
                                                <th><%=CommonStorage.getText("survey_date")%></th>
                                                <th><%=CommonStorage.getText("fix")%></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (int i = 0; i < printedParcels.size(); i++) {
                                                    if (i % 2 == 0) {
                                                        out.println("<tr class='odd gradeA'>");
                                                    } else {
                                                        out.println("<tr class='even gradeA'>");
                                                    }
                                                    out.println("<td>" + printedParcels.get(i).getUpi() + "</td>");
                                                    out.println("<td>" + printedParcels.get(i).getCertificateNumber() + "</td>");
                                                    out.println("<td>" + printedParcels.get(i).hasDisputeText() + "</td>");
                                                    out.println("<td>" + printedParcels.get(i).getMeansOfAcquisition() + "</td>");
                                                    out.println("<td>" + printedParcels.get(i).getSurveyDate() + "</td>");

                                                    out.print("<td>");
                                                    out.print("<a href = '#' class='reprintParcelButton' "
                                                            + "data-upi='" + printedParcels.get(i).getUpi() + "'>" + CommonStorage.getText("reprint") + "</a>");
                                                    out.println("</td>");
                                                }

                                            %>
                                        </tbody>
                                    </table>
                                    <a href="<%=exportURL%>" target="_blank" class="btn btn-primary col-lg-1"><%=CommonStorage.getText("export")%></a>
                                </div> <!-- /.table-responsive -->
                            </div> <!-- /.panel-body -->
                        </div> <!-- /.panel -->
                    </div> <!-- /.col-lg-12 -->
                </div>
            </div>
        </div>
    </div>
</div>


<script>
    function sendToMinorCorrection(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=minorCorrectionURL%>",
            data: {"upi": upi, "holdingLot": $("#holdingLot").val()},
            error: showajaxerror,
            success: loadInPlace
        });
    }
    function sendToMajorCorrection(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=majorCorrectionURL%>",
            data: {"upi": upi, "holdingLot": $("#holdingLot").val()},
            error: showajaxerror,
            success: loadInPlace
        });
    }
    function sendToConfirmed(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=confirmedURL%>",
            data: {"upi": upi, "holdingLot": $("#holdingLot").val()},
            error: showajaxerror,
            success: loadInPlace
        });
    }
    function reprint(upi) {
        $.ajax({
            type: 'POST',
            url: "<%=reprintURL%>",
            data: {"upi": upi},
            error: showajaxerror,
            success: loadInPlace
        });
    }

    $("#displayKebele").val('<%=request.getSession().getAttribute("kebele").toString()%>');

    $("#printedParcelsDisplayKebele").val('<%=request.getSession().getAttribute("kebele").toString()%>');

    $("#displayKebele").change(function () {
        if ($("#displayKebele").val() === "") {
            showError("Please select a kebele");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadInPlace,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "action": "<%=Constants.ACTION_PARCEL_LIST_PDC%>",
                    "kebele": $("#displayKebele").val()},
                url: '<%=filterurl%>'
            });
        }
        return false;
    });

    $("#printedParcelsDisplayKebele").change(function () {
        if ($("#printedParcelsDisplayKebele").val() === "") {
            showError("Please select a kebele");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadInPlace,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "action": "<%=Constants.ACTION_PRINTED_PARCEL_LIST_PDC%>",
                    "kebele": $("#printedParcelsDisplayKebele").val()},
                url: '<%=filterprintedurl%>'
            });
        }

        return false;
    });
    $('#dataTables').dataTable({
        "lengthMenu": [[30, 50, 100, -1], [30, 50, 100, "All"]]
    });
    $('#printedParcelsDataTable').dataTable({
        "lengthMenu": [[30, 50, 100, -1], [30, 50, 100, "All"]]
    });

    $("#printedParcelsDataTable").on("click", ".reprintParcelButton", function () {
        var upi = $(this).attr("data-upi");
        bootbox.confirm("<%=CommonStorage.getText("are_you_sure_you_want_to_reprint_this_parcel")%>:" + upi + " ?", function (result) {
            if (result) {
                reprint(upi);
            }
        });

    });
    $("#dataTables").on("click", ".takeActionButton", function () {
        var upi = $(this).attr("data-upi");
        bootbox.dialog({
            onEscape: function () {
            },
            message: "<center>" +
                    "Holding Lot #: (optional) <input name='holdingLot' id='holdingLot'/> <br/><%=CommonStorage.getText("what_sort_action_do_you_wish_to_take_on_this_parcel") + "? <br/> (" + CommonStorage.getText("administrative_upi")%>" + upi + ")</center>",
            title: "<%=CommonStorage.getText("take_action")%>",
            buttons: {
                minor: {
                    label: "<%=CommonStorage.getText("send_for_minor_correction")%>",
                    className: "btn-default",
                    callback: function () {
                        sendToMinorCorrection(upi);
                    }
                },
                major: {
                    label: "<%=CommonStorage.getText("send_for_major_correction")%>",
                    className: "btn-default",
                    callback: function () {
                        sendToMajorCorrection(upi);
                    }
                },
                confirm: {
                    label: "<%=CommonStorage.getText("confirm_as_correct")%>",
                    className: "btn-default",
                    callback: function () {
                        sendToConfirmed(upi);
                    }
                },
                main: {
                    label: "<%=CommonStorage.getText("cancel")%>",
                    className: "btn-primary"
                }
            }
        });
        return false;
    });
    <%
        if (request.getSession().getAttribute("kebele") != null && !request.getSession().getAttribute("kebele").toString().equalsIgnoreCase("all")) {
            out.println("$('#displayKebele').val(" + request.getSession().getAttribute("kebele") + ")");
        }
    %>
    $("#findParcel #findParcelButton").click(function () {
        updateUPI();
        if ($("#kebele").val() === "") {
            showError("Please select a kebele");
        } else if ($("#findParcel #upi").val() === "" || $("#findParcel #parcelNo").val() === "") {
            showError("<%=CommonStorage.getText("parcel_number_and_administrative_upi_are_required_fields")%>");
        }
        else { // Call to the service to get the view parcel form
            $.ajax({
                success: loadForward,
                error: showajaxerror,
                type: 'POST',
                data: {
                    "upi": $("#findParcel #upi").val(),
                    "parcelNo": $("#findParcel #parcelNo").val(),
                    "kebele": $("#findParcel #kebele").val()},
                url: '<%=findurl%>'
            });
        }
        return false;
    });
    $("#checkList #printCheckListButton").click(function () {
        updateUPI();
        if ($("#kebele").val() === "") {
            showError("Please select a kebele");
        }
        else { // Call to the service to get print form
            $.ajax({
                success: function (data) {
                    var mywindow = window.open('#', 'Public Display - Check List');
                    mywindow.document.write('<html><head>');
                    mywindow.document.write('<title>Public Display - Check List</title>');
                    mywindow.document.write('</head>');
                    mywindow.document.write('<body>');
                    mywindow.document.write(data);
                    mywindow.document.write('</body></html>');
                    mywindow.document.close();
                    mywindow.focus();
                    mywindow.print();
                    mywindow.close();
                    //$("#publicDisplayDetail").html(data);
                },
                error: showajaxerror,
                type: 'POST',
                url: '<%=checkListurl%>',
                data: {
                    "kebele": $("#checkList #checkListKebele").val()},
            });
        }
        return false;
    });
    $("#findParcel #kebele").change(function () {
        updateUPI();
    });
    $("#findParcel #parcelNo").keypress(function () {
        updateUPI();
    });
    $("#findParcel #parcelNo").mouseup(function () {
        updateUPI();
    });
    $("#findParcel #parcelNo").keyup(function () {
        updateUPI();
    });
    function updateUPI() {
        // Pad the upi with leading zeros
        var parcelNo = "" + $("#findParcel #parcelNo").val();
        var pad = "00000";
        var value = pad.substring(0, pad.length - parcelNo.length) + parcelNo;
        $("#findParcel #upi").val($("#findParcel #kebele").val() + "/" + value);
    }
</script>