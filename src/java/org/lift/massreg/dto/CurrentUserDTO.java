package org.lift.massreg.dto;

import java.sql.Timestamp;
import org.lift.massreg.entity.User;
import org.lift.massreg.util.Constants;

/**
 * A DTO Class to hold information about the currently logged in user
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CurrentUserDTO extends User {

    private String username;
    private Timestamp loginTime;
    private String clientInfo;

    private Constants.ROLE role;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    @Override
    public Constants.ROLE getRole() {
        return role;
    }

    @Override
    public void setRole(Constants.ROLE role) {
        this.role = role;
    }

    private String getRoleShortText() {
        String returnValue = "(";
        switch (role) {
            case ADMINISTRATOR:
                returnValue += "Admin";
                break;
            case FIRST_ENTRY_OPERATOR:
                returnValue += "FEO";
                break;
            case SECOND_ENTRY_OPERATOR:
                returnValue += "SEO";
                break;
            case SUPERVISOR:
                returnValue += "Supervisor";
                break;
            case POSTPDCOORDINATOR:
                returnValue += "PDC";
                break;
            case WOREDA_COORDINATOR:
                returnValue += "WC";
                break;
            case MINOR_CORRECTION_OFFICER:
                returnValue += "MCO";
                break;
            case CORRECTION_FIRST_ENTRY_OPERATOR:
                returnValue += "CFEO";
                break;
            case CORRECTION_SECOND_ENTRY_OPERATOR:
                returnValue += "CSEO";
                break;
            case CORRECTION_SUPERVISOR:
                returnValue += "CSupervisor";
                break;
        }
        returnValue += ")";
        return returnValue;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getFathersName() + " " + getGrandFathersName();
    }

    public String getNameWithRole() {
        return this.getFirstName() + " " + this.getFathersName() + getRoleShortText();
    }
}
