<%@page import="org.lift.massreg.dto.ParcelDifference"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.util.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.entity.*"%>
<%
    Parcel currentParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = currentParcel.canEdit(CommonStorage.getCurrentUser(request));
    ArrayList<Guardian> guardians = currentParcel.getGuardians();
    ParcelDifference parcelDifference = new ParcelDifference();
    boolean reviewMode = false;
    if (request.getSession().getAttribute("reviewMode") != null) {
        reviewMode = Boolean.parseBoolean(request.getSession().getAttribute("reviewMode").toString());
    }
    if (reviewMode) {
        parcelDifference = (ParcelDifference) request.getAttribute("currentParcelDifference");
    }

    String saveurl, viewurl, editurl, deleteurl, backurl, nexturl, finshurl;
    if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_GUARDIAN_FEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_GUARDIAN_FEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_GUARDIAN_FEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_GUARDIAN_FEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_FEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_FEO;
    } else {
        saveurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_SAVE_GUARDIAN_SEO;
        viewurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_GUARDIAN_SEO;
        editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_GUARDIAN_SEO;
        deleteurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_DELETE_GUARDIAN_SEO;
        backurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_SEO;
        nexturl = request.getContextPath() + "/Index?action=" + Constants.ACTION_PERSONS_WITH_INTEREST_LIST_SEO;
    }
%>
<div class="col-lg-12">
    <div class="row">
        <div class="col-lg-4 col-lg-offset-4 ">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Guardians List</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    Parcel: Administrative UPI - ${sessionScope.upi}
                    <%
                        if(parcelDifference==null){
                            System.err.println("Parcel Diff Null");
                        }
                        if (reviewMode && parcelDifference.isGuardiansDetails()) {
                            out.println("<span style='margin-left: 3em' class='discrepancy-field'>There is a discrepancy in guardian details</span>");
                        }
                        
                    %>
                    <span style='float:right' class='<%= reviewMode
                                                && parcelDifference.isGuardiansCount()
                                                        ? "discrepancy-field" : ""%>'>Guardian Count:<%=currentParcel.getGuardiansCount()%></span>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Sex</th>
                                    <th>Date of Birth</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < guardians.size(); i++) {
                                        if (i % 2 == 0) {
                                            out.println("<tr class='odd gradeA'>");
                                        } else {
                                            out.println("<tr class='even gradeA'>");
                                        }
                                        out.println("<td>" + guardians.get(i).getFullName() + "</td>");
                                        out.println("<td>" + guardians.get(i).getSexText() + "</td>");
                                        out.println("<td>" + guardians.get(i).getDateOfBirth() + "</td>");
                                        out.println("<td>");
                                        out.println("<a href = '#' class = 'viewButton' "
                                                + "data-registeredOn = '" + guardians.get(i).getRegisteredOn() + "' >View</a>");

                                        if (editable) {
                                            out.println("|");
                                            out.println("<a href = '#' class='editButton' data-registeredOn='"
                                                    + guardians.get(i).getRegisteredOn() + "'>Edit</a>");
                                            out.println("|");
                                            out.println("<a href = '#' class='deleteButton' data-registeredOn='"
                                                    + guardians.get(i).getRegisteredOn() + "'>Delete</a>");

                                        }
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }
                                %>
                            </tbody>
                        </table>
                    </div> <!-- /.table-responsive -->
                </div> <!-- /.panel-body -->
                <div class="row">
                    <div class="col-lg-6">
                        <button type="submit" id = "backButton" class="btn btn-default col-lg-2 col-lg-offset-1">Back</button>
                    </div>
                    <div class="col-lg-6">
                        <div class="row">
                            <%
                                if (editable) {
                                    out.println("<button type='submit' id = 'addGuardianButton' name = 'addGuardianButton' class='btn btn-default col-lg-2 col-lg-offset-6' data-toggle='modal' data-target='#addModal' >Add</button>");
                                } else {
                                    out.println("<span class='col-lg-2 col-lg-offset-6'></span>");
                                }
                                out.println("<button type='submit' id = 'nextButton' name = 'nextButton' class='btn btn-default col-lg-2' style='margin-left: 1em'>Next</button>");
                            %>
                        </div>                        
                    </div>
                </div>
                <br/>
            </div> <!-- /.panel -->
        </div> <!-- /.col-lg-12 -->
    </div> <!-- /.row -->
</div>
<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabe" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Register a Guardian</h4>
            </div>            <!-- /modal-header -->
            <div class="modal-body">
                <form role="form" action="#" id="addGuardianForm">
                    <div class="panel-body">
                        <div class="row">
                            <div class="form-group">
                                <label>First Name</label>
                                <input class="form-control " id="firstName" name="firstName" />
                            </div>
                            <div class="form-group">
                                <label>Father's Name</label>
                                <input class="form-control " id="fathersName" name="fathersName" />
                            </div> 
                            <div class="form-group">
                                <label>Grandfather's Name</label>
                                <input class="form-control " id="grandFathersName" name="grandFathersName" />
                            </div>
                            <div class="form-group">
                                <label>Sex</label>
                                <select class="form-control" id="sex" name="sex">
                                    <option value = 'm'>Male</option>
                                    <option value = 'f'>Female</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Date of Birth</label>
                                <input class="form-control " id="dateOfBirth" name="dateOfBirth"  type='text' readonly style="background: #FFF !important"/>
                            </div>
                        </div> <!-- /.row (nested) -->
                    </div> <!-- /.panel-body -->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <input type="submit" id="saveGuardianButton" class="btn btn-primary" value = "Add" />
            </div> 
        </div>
    </div>
</div>
<div class="modal fade" id="viewModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="viewModalLable" aria-hidden="true"></div>
<script type="text/javascript">
    var calendarAdd = $.calendars.instance("ethiopian", "am");
    $("#addGuardianForm #dateOfBirth").calendarsPicker({calendar: calendarAdd});
    function closeModals() {
        $("#editModal").html("");
        $("#editModal").hide();
        $("#viewModal").hide();
        $("#viewModal").html("");
    }
    function validate(formId) {
        var returnValue = true;
        $("#" + formId + " #firstName").toggleClass("error-field", false);
        $("#" + formId + " #fathersName").toggleClass("error-field", false);
        $("#" + formId + " #grandFathersName").toggleClass("error-field", false);
        $("#" + formId + " #sex").toggleClass("error-field", false);
        if ($("#" + formId + " #firstName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #firstName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #fathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #fathersName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #grandFathersName").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #grandFathersName").toggleClass("error-field", true);
        }
        if ($("#" + formId + " #sex").val().trim() === "") {
            returnValue = false;
            $("#" + formId + " #sex").toggleClass("error-field", true);
        }
        return returnValue;
    }
    function loadViewGuardian(result) {
        $("#editModal").html("").hide();
        $("#viewModal").html(result).modal();
    }
    function loadEditGuardian(result) {
        $("#viewModal").html("").hide();
        $("#editModal").html(result);
    }
    function deleteGuardian(regOn) {
        bootbox.confirm("Are you sure you want delete this guardian ?", function(result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=deleteurl%>",
                    data: {"registeredOn": regOn},
                    error: showajaxerror,
                    success: loadInPlace
                });
            }
        });
    }
    function editGuardian(regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            data: {"registeredOn": regOn},
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function viewGuardian(regOn) {
        $.ajax({
            type: 'POST',
            url: "<%=viewurl%>",
            data: {"registeredOn": regOn},
            error: showajaxerror,
            success: loadViewHolder
        });
    }
    function validateGuardiansList() {
        //showError("validateGuardianList");
        return true;
    }
    function save() {
        $.ajax({
            type: 'POST',
            url: "<%=saveurl%>",
            data: {
                "dateofbirth": $("#addGuardianForm #dateOfBirth").val(),
                "firstname": $("#addGuardianForm #firstName").val(),
                "fathersname": $("#addGuardianForm #fathersName").val(),
                "grandfathersname": $("#addGuardianForm #grandFathersName").val(),
                "sex": $("#addGuardianForm #sex").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    $('#dataTables-example').dataTable({"bPaginate": false});
    $('.editButton').click(function() {
        editGuardian($(this).attr("data-registeredOn"));
        return false;
    });
    $('.viewButton').click(function() {
        viewGuardian($(this).attr("data-registeredOn"));
        return false;
    });
    $('.deleteButton').click(function() {
        deleteGuardian($(this).attr("data-registeredOn"));
        return false;
    });
    $("#saveGuardianButton").click(function() {
        if (!validate("addGuardianForm")) {// validate
            showError("Please input appropriate values in the highlighted fields");
        }
        else {
            save();// save
            $("#addModal").hide();// close modale
        }
        return false;
    });
    $("#nextButton").click(function() {
        if (validateGuardiansList()) {
            $.ajax({
                type: 'POST',
                url: "<%=nexturl%>",
                error: showajaxerror,
                success: loadForward
            });
        }
        return false;
    });
    $("#backButton").click(function() {
        bootbox.confirm("Are you sure you want to go back?", function(result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: "<%=backurl%>",
                    error: showajaxerror,
                    success: loadBackward
                });
            }
        });
        return false;
    });
</script>