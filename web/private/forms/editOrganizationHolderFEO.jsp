<%@page import="org.lift.massreg.util.Constants"%>
<%@page import="org.lift.massreg.util.CommonStorage"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.Option"%>
<%
    Parcel cParcel = (Parcel) request.getAttribute("currentParcel");
    boolean editable = cParcel.canEdit(CommonStorage.getCurrentUser(request));
    OrganizationHolder holder = cParcel.getOrganaizationHolder();
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-3">
            <h2 class="page-header">Edit Holder Details</h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    Parcel: Administrative UPI - ${sessionScope.upi}
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" action="#" id="editHolderFrom" name="editHolderFrom" method="action">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input class="form-control " id="organizationName" name="organizationName" placeholder="Name of the holding organization" value="<%=holder.getName()%>" />
                                </div>
                                <div class="form-group">
                                    <label>Type</label>
                                    <select class="form-control" id="organizationType" name="organizationType" value="<%=holder.getOrganizationType()%>" >
                                        <%
                                            Option[] organizationTypes = MasterRepository.getInstance().getAllOrganizationTypes();
                                            for (int i = 0; i < organizationTypes.length; i++) {
                                                out.println("<option value = '" + organizationTypes[i].getKey() + "'>" + organizationTypes[i].getValue() + "</option>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="row">
                                    <div class="col-lg-6">
                                        <button type="submit" id = "backButton" class="btn btn-default col-lg-6" style="float:left">Back</button>
                                    </div>
                                    <div class="col-lg-6">

                                        <button type='submit' id = 'cancelButton' name = 'cancelButton' class='btn btn-default col-lg-4 col-lg-offset-2' >Cancel</button>;
                                        <button type='submit' id = 'updateButton' name = 'updateButton' class='btn btn-default col-lg-5' style='float:right'>Save</button>
                                    </div>
                                    <!-- /.col-lg-6 (nested) -->
                                </div>
                                <!-- /.row (nested) -->
                            </form>
                        </div>
                    </div>
                </div>    <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
</div>
<script type="text/javascript">
    <%
        String updateurl,cancelurl;
        if (CommonStorage.getCurrentUser(request).getRole() == Constants.ROLE.FIRST_ENTRY_OPERATOR) {
            updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_FEO;
            cancelurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_FEO;
        } else {
            updateurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_UPDATE_ORGANIZATION_HOLDER_FEO;
            cancelurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_VIEW_HOLDER_SEO;
        }
    %>
        function validate() {
        var returnValue = true;
        //$("#organizationName").toggle("error=off");
        if ($("#organizationName").val().trim() === "") {
            returnValue = false;
            //$("#organizationName").toggle("error");
        }
        showMessage("Validation:");
        return returnValue;
    }
    function save() {
        $.ajax({
            url: "<%=updateurl%>",
            data: {
                "organizationName": $("#organizationName").val(),
                "organizationType": $("#organizationType").val()
            },
            error: showajaxerror,
            success: loadForward
        });
    }
    
    $(function() {
        $("#editHolderFrom select").each(function() {
            $(this).val($(this).attr("value"));
        });
        $("#cancelButton").click(function() {
            $.ajax({
                url: "<%=cancelurl%>",
                error: showajaxerror,
                success: loadInPlace
            });
            return false;
        });
        $("#backButton").click(function() {
                            bootbox.confirm("Are you sure you want to go back?", function(result) {
                                if (result) {
                                    $.ajax({
                                        url: "<%=request.getContextPath()%>/Index?action=<%=Constants.ACTION_VIEW_PARCEL_FEO%>",
                                                                error: showajaxerror,
                                                                success: loadBackward
                                                            });
                                                        }
                                                    });
                                                    return false;
                                                });
        $("#updateButton").click(function() {
            if (!validate()) {// validate
                showError("Please input appropriate values in the highlighted fields");
                return false;
            } else {
                save();// save
            }
            return false;
        });
    });
</script>