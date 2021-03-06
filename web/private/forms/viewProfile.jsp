<%@page import="org.lift.massreg.entity.*"%>
<%@page import="org.lift.massreg.dao.MasterRepository"%>
<%@page import="org.lift.massreg.util.*"%>
<%
    User currentUser = CommonStorage.getCurrentUser(request);
    String editurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_EDIT_PROFILE;
    String homeurl = request.getContextPath() + "/Index?action=" + Constants.ACTION_WELCOME_PART;
%>
<div class="col-lg-5 col-lg-offset-4">
    <div class="row">
        <div class="col-lg-7 col-lg-offset-3">
            <h2 class="page-header">&nbsp;&nbsp;&nbsp;&nbsp; <%=CommonStorage.getText("profile_details")%></h2>
        </div>
    </div> <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <%= currentUser.getFirstName()+" " +currentUser.getFathersName()%>' <%=CommonStorage.getText("profile")%>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form role="form" id="viewProfileFrom" name="viewProfileFrom" >
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("name")%></label>
                                    <input class="form-control " id="name" name="name" value="<%=currentUser.getFullName()%>" disabled/>
                                </div>
                                <div class="form-group">
                                    <label><%=CommonStorage.getText("phone_number")%></label>
                                    <input class="form-control " id="phoneNumber" name="phoneNumber" value="<%=currentUser.getPhoneNumber()%>" disabled/>
                                </div>
                                <div class="row">
                                    <button type='submit' id = 'editProfileButton' name = 'editProfileButton' class='btn btn-default col-lg-4' style='float: right;margin-right: 1em'><%=CommonStorage.getText("change_password")%></button>
                                    <button type='submit' id = 'goToHomeButton' name = 'goToHomeButton' class='btn btn-default col-lg-2' style='float: right;margin-right: 1em'><%=CommonStorage.getText("home")%></button>
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
    $("#viewProfileFrom select").each(function() {
        $(this).val($(this).attr("value"));
    });
    $("#goToHomeButton").click(function() {
        $.ajax({
            type: 'POST',
            url: "<%=homeurl%>",
            error: showajaxerror,
            success: loadBackward
        });
        return false;
    });
    $("#editProfileButton").click(function() {
        $.ajax({
            type: 'POST',
            url: "<%=editurl%>",
            error: showajaxerror,
            success: loadInPlace
        });
        return false;
    });
</script>