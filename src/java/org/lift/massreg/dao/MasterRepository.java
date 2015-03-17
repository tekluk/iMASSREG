package org.lift.massreg.dao;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dto.*;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class MasterRepository {

    private static final MasterRepository instance = new MasterRepository();

    private MasterRepository() {
    }

    public static MasterRepository getInstance() {
        return instance;
    }

    public boolean save(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute, teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, parcel.getStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, parcel.getRegisteredOn());
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, parcel.getRegisteredBy());
            stmnt.setString(3, "Parcel");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(IndividualHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, holder.getStage());
            stmnt.setLong(3, holder.getRegisteredBy());
            stmnt.setTimestamp(4, holder.getRegisteredOn());
            stmnt.setString(5, holder.getFirstName());
            stmnt.setString(6, holder.getFathersName());
            stmnt.setString(7, holder.getGrandFathersName());
            stmnt.setString(8, holder.getSex());
            stmnt.setString(9, holder.getDateOfBirth());
            stmnt.setByte(10, holder.getFamilyRole());
            stmnt.setString(11, holder.getId());
            stmnt.setBoolean(12, holder.hasPhysicalImpairment());
            stmnt.setBoolean(13, holder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, holder.getRegisteredBy());
            stmnt.setString(3, "IndividualHolder");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, personWithInterest.getStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, personWithInterest.getRegisteredOn());
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, personWithInterest.getRegisteredBy());
            stmnt.setString(3, "PersonWithInterest");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, guardian.getStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, guardian.getRegisteredOn());
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, guardian.getRegisteredBy());
            stmnt.setString(3, "Guardian");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, dispute.getStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, dispute.getRegisteredOn());
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, dispute.getRegisteredBy());
            stmnt.setString(3, "Dispute");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(HttpServletRequest request, User user) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO users(username, password, firstname,"
                + " fathersname, grandfathersname, phoneno, role) VALUES "
                + "( ?, (SELECT MD5(?)), ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            stmnt.setString(2, user.getPassword());
            stmnt.setString(3, user.getFirstName());
            stmnt.setString(4, user.getFathersName());
            stmnt.setString(5, user.getGrandFathersName());
            stmnt.setString(6, user.getPhoneNumber());
            stmnt.setString(7, user.getRoleText());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            query = "INSERT INTO groups(username, groupid) VALUES (?, 'user')";
            stmnt = connection.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "User");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean save(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, holder.getStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, holder.getRegisteredon());
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, holder.getRegisteredby());
            stmnt.setString(3, "OrganizationHolder");
            stmnt.setString(4, "NEW");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public Parcel getParcel(String upi, byte stage) {
        Parcel returnValue = null;

        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = new Parcel();
                returnValue.setAcquisition(rs.getByte("acquisitiontype"));
                returnValue.setAcquisitionYear(rs.getInt("acquisitionyear"));
                returnValue.setCertificateNumber(rs.getString("certificateno"));
                returnValue.setCurrentLandUse(rs.getByte("landusetype"));
                returnValue.setEncumbrance(rs.getByte("encumbrancetype"));
                returnValue.setHolding(rs.getByte("holdingtype"));
                returnValue.setHoldingNumber(rs.getString("holdingno"));
                returnValue.setMapSheetNo(rs.getString("mapsheetno"));
                returnValue.setOtherEvidence(rs.getByte("otherevidence"));
                returnValue.setParcelId(rs.getInt("parcelid"));
                returnValue.setRegisteredBy(rs.getLong("registeredby"));
                returnValue.setSoilFertility(rs.getByte("soilfertilitytype"));
                returnValue.setStage(rs.getByte("stage"));
                returnValue.setStatus(rs.getString("status"));
                returnValue.setSurveyDate(rs.getString("surveydate"));
                returnValue.setUpi(rs.getString("upi"));
                returnValue.setRegisteredOn(rs.getTimestamp("registeredon"));
                returnValue.hasDispute(rs.getBoolean("hasdispute"));
                returnValue.setTeamNo(rs.getByte("teamNo"));
                if (returnValue.hasDispute()) {
                    returnValue.setDisputes(getAllDisputes(upi, stage));
                }
                if (returnValue.getHolding() == 1) {
                    returnValue.setIndividualHolders(getAllIndividualHolders(upi, stage));
                    returnValue.setGuardians(getAllGuardians(upi, stage));
                    returnValue.setPersonsWithInterest(getAllPersonsWithInterest(upi, stage));
                } else {
                    returnValue.setOrganaizationHolder(getOrganaizationHolder(upi, stage));
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }

        return returnValue;
    }

    /**
     * Retrieves details of the user specified with the username
     *
     * @return an entity object representing a user
     * @param userName - username of the user to return
     */
    public CurrentUserDTO getCurrentUserDTO(String userName) {
        CurrentUserDTO returnValue = new CurrentUserDTO();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE username= ?");
            stmnt.setString(1, userName);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue.setUserId(rs.getLong("userId"));
                returnValue.setUsername(rs.getString("username"));
                returnValue.setFirstName(rs.getString("firstname"));
                returnValue.setFathersName(rs.getString("fathersname"));
                returnValue.setGrandFathersName(rs.getString("grandfathersname"));
                returnValue.setPhoneNumber(rs.getString("phoneNo"));
                returnValue.setStatus(rs.getString("status"));
                String role = rs.getString("role");
                switch (role) {
                    case "FEO":
                        returnValue.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                        break;
                    case "SEO":
                        returnValue.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                        break;
                    case "SUPERVISOR":
                        returnValue.setRole(Constants.ROLE.SUPERVISOR);
                        break;
                    case "ADMINISTRATOR":
                        returnValue.setRole(Constants.ROLE.ADMINISTRATOR);
                        break;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean parcelExists(String upi, byte stage) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean isParcelComplete(String upi, byte stage) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE upi=? and stage=? and status='active' and completed='true'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    /**
     * Retrieves details of the user specified with the id
     *
     * @return an entity object representing a user
     * @param userId id of the user to return
     */
    public User getUser(long userId) {
        User returnValue = new User();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE userid= ?");
            stmnt.setLong(1, userId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue.setUserId(rs.getLong("userId"));
                returnValue.setUsername(rs.getString("username"));
                returnValue.setFirstName(rs.getString("firstname"));
                returnValue.setFathersName(rs.getString("fathersname"));
                returnValue.setGrandFathersName(rs.getString("grandfathersname"));
                returnValue.setPhoneNumber(rs.getString("phoneNo"));
                returnValue.setStatus(rs.getString("status"));
                String role = rs.getString("role");
                switch (role) {
                    case "FEO":
                        returnValue.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                        break;
                    case "SEO":
                        returnValue.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                        break;
                    case "SUPERVISOR":
                        returnValue.setRole(Constants.ROLE.SUPERVISOR);
                        break;
                    case "ADMINISTRATOR":
                        returnValue.setRole(Constants.ROLE.ADMINISTRATOR);
                        break;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    private OrganizationHolder getOrganaizationHolder(String upi, byte stage) {
        OrganizationHolder returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM organizationholder WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = new OrganizationHolder();
                returnValue.setName(rs.getString("organizationname"));
                returnValue.setUpi(rs.getString("upi"));
                returnValue.setStage(rs.getByte("stage"));
                returnValue.setRegisteredon(rs.getTimestamp("registeredon"));
                returnValue.setRegisteredby(rs.getLong("registeredby"));
                returnValue.setOrganizationType(rs.getByte("organizationtype"));
                returnValue.setStatus(rs.getString("status"));

            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public ArrayList<IndividualHolder> getAllIndividualHolders(String upi, byte stage) {
        ArrayList<IndividualHolder> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM individualholder WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                IndividualHolder ih = new IndividualHolder();
                ih.setDateOfBirth(rs.getString("dateofbirth"));
                ih.setFamilyRole(rs.getByte("familyrole"));
                ih.setFirstName(rs.getString("firstname"));
                ih.setFathersName(rs.getString("fathersname"));
                ih.setGrandFathersName(rs.getString("grandfathersname"));
                ih.setId(rs.getString("holderId"));
                ih.setRegisteredBy(rs.getLong("registeredby"));
                ih.setRegisteredOn(rs.getTimestamp("registeredon"));
                ih.setSex(rs.getString("sex"));
                ih.hasPhysicalImpairment(rs.getBoolean("physicalimpairment"));
                ih.isOrphan(rs.getBoolean("isOrphan"));
                ih.setStage(rs.getByte("stage"));
                ih.setUpi(rs.getString("upi"));
                returnValue.add(ih);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }

        return returnValue;
    }

    public ArrayList<Dispute> getAllDisputes(String upi, byte stage) {
        ArrayList<Dispute> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM dispute WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Dispute dispute = new Dispute();
                dispute.setFirstName(rs.getString("firstname"));
                dispute.setFathersName(rs.getString("fathersname"));
                dispute.setGrandFathersName(rs.getString("grandfathersname"));
                dispute.setRegisteredBy(rs.getLong("registeredby"));
                dispute.setRegisteredOn(rs.getTimestamp("registeredon"));
                dispute.setSex(rs.getString("sex"));
                dispute.setStage(rs.getByte("stage"));
                dispute.setUpi(rs.getString("upi"));
                dispute.setDisputeStatus(rs.getByte("disputestatus"));
                dispute.setDisputeType(rs.getByte("disputetype"));
                returnValue.add(dispute);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteDispute(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE dispute SET status='deleted' WHERE upi=? and stage=? and registeredOn=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "Dispute");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean deleteIndividualHolder(HttpServletRequest request, String holderId, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE IndividualHolder SET status='deleted' WHERE upi=? and stage=? and registeredOn=? and holderId=?");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.setString(4, holderId);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "IndividualHolder");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        return returnValue;
    }

    public boolean update(Dispute oldDispute, Dispute newDispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE dispute SET firstname = ?,  fathersname = ?,"
                + "grandfathersname = ?, sex=?, disputetype=?, disputestatus=?,"
                + "status=? WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newDispute.getFirstName());
            stmnt.setString(2, newDispute.getFathersName());
            stmnt.setString(3, newDispute.getGrandFathersName());
            stmnt.setString(4, newDispute.getSex());
            stmnt.setByte(5, newDispute.getDisputeType());
            stmnt.setByte(6, newDispute.getDisputeStatus());
            stmnt.setString(7, "active");
            stmnt.setString(8, oldDispute.getUpi());
            stmnt.setByte(9, oldDispute.getStage());
            stmnt.setTimestamp(10, oldDispute.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldDispute.getDifferenceForChangeLog(newDispute);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldDispute.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newDispute.getRegisteredBy());
                stmnt.setString(4, "Dispute");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(Parcel oldParcel, Parcel newParcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE parcel SET certificateno=?, holdingno=?,"
                + "otherevidence=?, landusetype=?, soilfertilitytype=?, "
                + "holdingtype=?, acquisitiontype=?, acquisitionyear=?, "
                + "surveydate=?, mapsheetno=?, encumbrancetype=?, hasdispute=?, teamNo=?"
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newParcel.getCertificateNumber());
            stmnt.setString(2, newParcel.getHoldingNumber());
            stmnt.setByte(3, newParcel.getOtherEvidence());
            stmnt.setByte(4, newParcel.getCurrentLandUse());
            stmnt.setByte(5, newParcel.getSoilFertility());
            stmnt.setByte(6, newParcel.getHolding());
            stmnt.setByte(7, newParcel.getMeansOfAcquisition());
            stmnt.setInt(8, newParcel.getAcquisitionYear());
            stmnt.setString(9, newParcel.getSurveyDate());
            stmnt.setString(10, newParcel.getMapSheetNo());
            stmnt.setByte(11, newParcel.getEncumbrance());
            stmnt.setBoolean(12, newParcel.hasDispute());
            stmnt.setByte(13, newParcel.getTeamNo());
            stmnt.setString(14, oldParcel.getUpi());
            stmnt.setByte(15, oldParcel.getStage());
            stmnt.setTimestamp(16, oldParcel.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldParcel.getDifferenceForChangeLog(newParcel);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldParcel.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newParcel.getRegisteredBy());
                stmnt.setString(4, "Parcel");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(IndividualHolder oldIndividualHolder,
            IndividualHolder newIndividualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE individualholder SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=?, "
                + "familyrole=?, holderId=?, physicalimpairment=?, isOrphan=? WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newIndividualHolder.getFirstName());
            stmnt.setString(2, newIndividualHolder.getFathersName());
            stmnt.setString(3, newIndividualHolder.getGrandFathersName());
            stmnt.setString(4, newIndividualHolder.getSex());
            stmnt.setString(5, newIndividualHolder.getDateOfBirth());
            stmnt.setByte(6, newIndividualHolder.getFamilyRole());
            stmnt.setString(7, newIndividualHolder.getId());
            stmnt.setBoolean(8, newIndividualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(9, newIndividualHolder.isOrphan());
            stmnt.setString(10, oldIndividualHolder.getUpi());
            stmnt.setByte(11, oldIndividualHolder.getStage());
            stmnt.setTimestamp(12, oldIndividualHolder.getRegisteredOn());

            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldIndividualHolder.getDifferenceForChangeLog(newIndividualHolder);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldIndividualHolder.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newIndividualHolder.getRegisteredBy());
                stmnt.setString(4, "IndividualHolder");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(PersonWithInterest oldPersonWithInterest,
            PersonWithInterest newPersonWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE personwithinterest SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=? "
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newPersonWithInterest.getFirstName());
            stmnt.setString(2, newPersonWithInterest.getFathersName());
            stmnt.setString(3, newPersonWithInterest.getGrandFathersName());
            stmnt.setString(4, newPersonWithInterest.getSex());
            stmnt.setString(5, newPersonWithInterest.getDateOfBirth());
            stmnt.setString(6, oldPersonWithInterest.getUpi());
            stmnt.setByte(7, oldPersonWithInterest.getStage());
            stmnt.setTimestamp(8, oldPersonWithInterest.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldPersonWithInterest.getDifferenceForChangeLog(newPersonWithInterest);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldPersonWithInterest.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newPersonWithInterest.getRegisteredBy());
                stmnt.setString(4, "PersonWithInterest");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(Guardian oldGuardian,
            Guardian newGuardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE Guardian SET firstname=?, "
                + "fathersname=?, grandfathersname=?, sex=?, dateofbirth=? "
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newGuardian.getFirstName());
            stmnt.setString(2, newGuardian.getFathersName());
            stmnt.setString(3, newGuardian.getGrandFathersName());
            stmnt.setString(4, newGuardian.getSex());
            stmnt.setString(5, newGuardian.getDateOfBirth());
            stmnt.setString(6, oldGuardian.getUpi());
            stmnt.setByte(7, oldGuardian.getStage());
            stmnt.setTimestamp(8, oldGuardian.getRegisteredOn());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldGuardian.getDifferenceForChangeLog(newGuardian);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldGuardian.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newGuardian.getRegisteredBy());
                stmnt.setString(4, "Guardian");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(OrganizationHolder oldOrganizationHolder,
            OrganizationHolder newOrganizationHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE organizationholder SET organizationname=?, "
                + "organizationtype=? WHERE upi=? and stage = ? and "
                + "registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newOrganizationHolder.getName());
            stmnt.setByte(2, newOrganizationHolder.getOrganizationType());
            stmnt.setString(3, oldOrganizationHolder.getUpi());
            stmnt.setByte(4, oldOrganizationHolder.getStage());
            stmnt.setTimestamp(5, oldOrganizationHolder.getRegisteredon());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            ArrayList<Change> changes = oldOrganizationHolder.getDifferenceForChangeLog(newOrganizationHolder);
            for (Change change : changes) {
                query = "INSERT INTO changelog(upi, activitydate, userid, "
                        + "tablename, fieldname, fieldoldvalue, fieldnewvalue, "
                        + "actiontype) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                stmnt = connection.prepareStatement(query);
                stmnt.setString(1, oldOrganizationHolder.getUpi());
                stmnt.setTimestamp(2, Timestamp.from(Instant.now()));
                stmnt.setLong(3, newOrganizationHolder.getRegisteredby());
                stmnt.setString(4, "OrganizationHolder");
                stmnt.setString(5, change.getField());
                stmnt.setString(6, change.getOldValue());
                stmnt.setString(7, change.getNewValue());
                stmnt.setString(8, "UPDATE");
                result = stmnt.executeUpdate();
                if (result < 1) {
                    returnValue = false;
                }
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean update(User oldUser, User newUser) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE users SET username =?, firstName=?, "
                + "fathersName=?, grandfathersName=?, phoneno=?, role=? "
                + "WHERE userid=? ";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newUser.getUsername());
            stmnt.setString(2, newUser.getFirstName());
            stmnt.setString(3, newUser.getFathersName());
            stmnt.setString(4, newUser.getGrandFathersName());
            stmnt.setString(5, newUser.getPhoneNumber());
            stmnt.setString(6, newUser.getRoleText());
            stmnt.setLong(7, oldUser.getUserId());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean updatePassword(User oldUser, String newPassword) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE users SET password=MD5(?) WHERE userid=? ";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, newPassword);
            stmnt.setLong(2, oldUser.getUserId());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean deleteOrganizationHolder(HttpServletRequest request, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE OrganizationHolder SET status='deleted' WHERE upi=? and stage=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.executeUpdate();
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "OrganizationHolder");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }

            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public boolean deleteParcel(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE Parcel SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            stmnt.executeUpdate();
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "Parcel");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public void completeParcel(String upi, byte stage, Timestamp registeredOn) {
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE parcel SET completed=? "
                + "WHERE upi=? and stage = ? and registeredon = ?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setBoolean(1, true);
            stmnt.setString(2, upi);
            stmnt.setByte(3, stage);
            stmnt.setTimestamp(4, registeredOn);
            int result = stmnt.executeUpdate();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
    }

    public ArrayList<PersonWithInterest> getAllPersonsWithInterest(String upi, byte stage) {
        ArrayList<PersonWithInterest> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM PersonWithInterest WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                PersonWithInterest personWithInterest = new PersonWithInterest();
                personWithInterest.setDateOfBirth(rs.getString("dateofbirth"));
                personWithInterest.setFirstName(rs.getString("firstname"));
                personWithInterest.setFathersName(rs.getString("fathersname"));
                personWithInterest.setGrandFathersName(rs.getString("grandfathersname"));
                personWithInterest.setRegisteredBy(rs.getLong("registeredby"));
                personWithInterest.setRegisteredOn(rs.getTimestamp("registeredon"));
                personWithInterest.setSex(rs.getString("sex"));
                personWithInterest.setStage(rs.getByte("stage"));
                personWithInterest.setUpi(rs.getString("upi"));
                returnValue.add(personWithInterest);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public ArrayList<Guardian> getAllGuardians(String upi, byte stage) {
        ArrayList<Guardian> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Guardian WHERE upi=? and stage=? and status='active'");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Guardian guardian = new Guardian();
                guardian.setDateOfBirth(rs.getString("dateofbirth"));
                guardian.setFirstName(rs.getString("firstname"));
                guardian.setFathersName(rs.getString("fathersname"));
                guardian.setGrandFathersName(rs.getString("grandfathersname"));
                guardian.setRegisteredBy(rs.getLong("registeredby"));
                guardian.setRegisteredOn(rs.getTimestamp("registeredon"));
                guardian.setSex(rs.getString("sex"));
                guardian.setStage(rs.getByte("stage"));
                guardian.setUpi(rs.getString("upi"));
                returnValue.add(guardian);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public boolean deletePersonWithInterest(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE personwithinterest SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "PersonWithInterest");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }

            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public boolean deleteGuardian(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE Guardian SET status='deleted' WHERE upi=? and stage=? and registeredOn=? ");
            stmnt.setString(1, upi);
            stmnt.setByte(2, stage);
            stmnt.setTimestamp(3, registeredOn);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "Guardian");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }

            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public boolean deleteUser(HttpServletRequest request, long userId) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("UPDATE users SET username=? , status='deleted' WHERE userId=? ");
            stmnt.setString(1, Instant.now().getEpochSecond() + getUser(userId).getUsername());
            stmnt.setLong(2, userId);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            stmnt = connection.prepareStatement("INSERT INTO changelog(activitydate, userid, tablename,actiontype) VALUES (?, ?, ?, ?)");
            stmnt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmnt.setLong(2, CommonStorage.getCurrentUser(request).getUserId());
            stmnt.setString(3, "User");
            stmnt.setString(4, "DELETE");
            result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            returnValue = false;
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return an array of all the possible option from the table specified
     * @param table name of the table to query from
     */
    private Option[] getAllOptions(String table) {
        return getAllOptions(table, "code", CommonStorage.getCurrentLanguage());
    }

    /**
     * @return an array of all the possible option from the table specified
     * @param table name of the table to query from
     * @param key column to be used as key in the option objects
     * @param value name of column to be used as value in the option objects
     */
    private Option[] getAllOptions(String table, String key, String value) {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM " + table + " ORDER BY " + key);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt(key) + "", rs.getString(value)));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return an array of option objects representing the possible Other
     * Evidence Types
     */
    public Option[] getAllOtherEvidenceTypes() {
        return getAllOptions("otherevidencetypes");
    }

    /**
     * @return an array of option objects representing the possible Current Land
     * Use Types
     */
    public Option[] getAllCurrentLandUseTypes() {
        return getAllOptions("landusetypes");
    }

    /**
     * @return an array of option objects representing the possible Soil
     * Fertility Types
     */
    public Option[] getAllSoilFertilityTypes() {
        return getAllOptions("soilfertilitytypes");
    }

    /**
     * @return an array of option objects representing the possible holding
     * types
     */
    public Option[] getAllHoldingTypes() {
        return getAllOptions("holdingtypes");
    }

    /**
     * @return an array of option objects representing the possible means of
     * acquisition types
     */
    public Option[] getAllMeansOfAcquisitionTypes() {
        return getAllOptions("acquisitiontypes");
    }

    /**
     * @return an array of option objects representing the possible encumbrance
     * types
     */
    public Option[] getAllEncumbranceTypes() {
        return getAllOptions("encumbrancetypes");
    }

    /**
     * @return an array of option objects representing the possible family role
     * types
     */
    public Option[] getAllFamilyRoles() {
        return getAllOptions("familyroletypes");
    }

    /**
     * @return an array of option objects representing the possible organization
     * types
     */
    public Option[] getAllOrganizationTypes() {
        return getAllOptions("organizationtypes");
    }

    /**
     * @return an array of option objects representing the possible encumbrance
     * types
     */
    public Option[] getAllStages() {
        return getAllOptions("stages");
    }

    /**
     * @return an array of option objects representing the possible dispute
     * status types
     */
    public Option[] getAllDisputeStatusTypes() {
        return getAllOptions("disputestatustypes");
    }

    /**
     * @return an array of option objects representing the possible dispute
     * types
     */
    public Option[] getAllDisputeTypes() {
        return getAllOptions("disputetypes");
    }

    /**
     * @return an array of option objects representing all possible family role
     * types types
     */
    public Option[] getAllfamilyRoleTypes() {
        return getAllOptions("familyroletypes");
    }

    /**
     * @return a hash table of all the kebele values
     */
    public Option[] getAllKebeles() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM kebele WHERE woredacode=? ORDER BY code");
            stmnt.setLong(1, CommonStorage.getCurrentWoredaId());
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return a hash table of all the woredas
     */
    public Option[] getAllWoredas() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Woreda ORDER BY code");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @return a hash table of all the regions
     */
    public Option[] getAllRegions() {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Region ORDER BY code");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue.toArray(new Option[0]);
    }

    /**
     * @param region id of the region
     * @return a hash table of all the woredas in a region
     */
    public Option[] getAllWoredas(int region) {
        ArrayList<Option> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT Woreda.* FROM Woreda, Zone WHERE Zone.regioncode = ? and Woreda.zonecode = Zone.code ORDER BY Woreda.english");
            stmnt.setInt(1, region);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                returnValue.add(new Option(rs.getInt("code") + "", rs.getString(CommonStorage.getCurrentLanguage())));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue.toArray(new Option[0]);
    }

    public boolean commit(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute, teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean commit(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getCommitedStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(Parcel parcel) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Parcel (upi,stage,registeredBy,registeredOn,"
                + "parcelId,certificateNo,holdingNo, otherEvidence,landUseType,"
                + "soilFertilityType,holdingType,acquisitionType,acquisitionYear,"
                + "surveyDate,mapSheetNo,status,encumbranceType,hasDispute,teamNo) VALUES (?,?,?,"
                + "?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, parcel.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, parcel.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setInt(5, parcel.getParcelId());
            stmnt.setString(6, parcel.getCertificateNumber());
            stmnt.setString(7, parcel.getHoldingNumber());
            stmnt.setByte(8, parcel.getOtherEvidence());
            stmnt.setByte(9, parcel.getCurrentLandUse());
            stmnt.setByte(10, parcel.getSoilFertility());
            stmnt.setByte(11, parcel.getHolding());
            stmnt.setByte(12, parcel.getMeansOfAcquisition());
            stmnt.setInt(13, parcel.getAcquisitionYear());
            stmnt.setString(14, parcel.getSurveyDate());
            stmnt.setString(15, parcel.getMapSheetNo());
            stmnt.setString(16, parcel.getStatus());
            stmnt.setByte(17, parcel.getEncumbrance());
            stmnt.setBoolean(18, parcel.hasDispute());
            stmnt.setByte(19, parcel.getTeamNo());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(OrganizationHolder holder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO organizationholder(upi, stage, organizationname, "
                + "registeredby, registeredon, organizationtype) VALUES "
                + "( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, holder.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setString(3, holder.getName());
            stmnt.setLong(4, holder.getRegisteredby());
            stmnt.setTimestamp(5, Timestamp.from(Instant.now()));
            stmnt.setByte(6, holder.getOrganizationType());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(IndividualHolder individualHolder) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO individualholder( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth, familyrole, holderId, physicalImpairment, isOrphan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, individualHolder.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, individualHolder.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, individualHolder.getFirstName());
            stmnt.setString(6, individualHolder.getFathersName());
            stmnt.setString(7, individualHolder.getGrandFathersName());
            stmnt.setString(8, individualHolder.getSex());
            stmnt.setString(9, individualHolder.getDateOfBirth());
            stmnt.setByte(10, individualHolder.getFamilyRole());
            stmnt.setString(11, individualHolder.getId());
            stmnt.setBoolean(12, individualHolder.hasPhysicalImpairment());
            stmnt.setBoolean(13, individualHolder.isOrphan());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(PersonWithInterest personWithInterest) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO PersonWithInterest( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, personWithInterest.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, personWithInterest.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, personWithInterest.getFirstName());
            stmnt.setString(6, personWithInterest.getFathersName());
            stmnt.setString(7, personWithInterest.getGrandFathersName());
            stmnt.setString(8, personWithInterest.getSex());
            stmnt.setString(9, personWithInterest.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(Guardian guardian) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO Guardian( upi, stage, registeredby, "
                + "registeredon, firstname, fathersname, grandfathersname, sex, "
                + "dateofbirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, guardian.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, guardian.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, guardian.getFirstName());
            stmnt.setString(6, guardian.getFathersName());
            stmnt.setString(7, guardian.getGrandFathersName());
            stmnt.setString(8, guardian.getSex());
            stmnt.setString(9, guardian.getDateOfBirth());
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean submitForCorrection(Dispute dispute) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "INSERT INTO dispute(upi, stage, registeredby, registeredon,"
                + "firstname, fathersname, grandfathersname, sex, disputetype, "
                + "disputestatus,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, dispute.getUpi());
            stmnt.setByte(2, CommonStorage.getCorrectionStage());
            stmnt.setLong(3, dispute.getRegisteredBy());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            stmnt.setString(5, dispute.getFirstName());
            stmnt.setString(6, dispute.getFathersName());
            stmnt.setString(7, dispute.getGrandFathersName());
            stmnt.setString(8, dispute.getSex());
            stmnt.setByte(9, dispute.getDisputeType());
            stmnt.setByte(10, dispute.getDisputeStatus());
            stmnt.setString(11, "active");
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public ArrayList<Parcel> getALLParcelsInCorrection() {
        ArrayList<Parcel> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Parcel WHERE stage = 3 and status='active' and upi not in (SELECT upi FROM Parcel WHERE stage = 4 and status='active')");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Parcel parcel = new Parcel();
                parcel.setAcquisition(rs.getByte("acquisitiontype"));
                parcel.setAcquisitionYear(rs.getInt("acquisitionyear"));
                parcel.setCertificateNumber(rs.getString("certificateno"));
                parcel.setCurrentLandUse(rs.getByte("landusetype"));
                parcel.setEncumbrance(rs.getByte("encumbrancetype"));
                parcel.setHolding(rs.getByte("holdingtype"));
                parcel.setHoldingNumber(rs.getString("holdingno"));
                parcel.setMapSheetNo(rs.getString("mapsheetno"));
                parcel.setOtherEvidence(rs.getByte("otherevidence"));
                parcel.setParcelId(rs.getInt("parcelid"));
                parcel.setRegisteredBy(rs.getLong("registeredby"));
                parcel.setSoilFertility(rs.getByte("soilfertilitytype"));
                parcel.setStage(rs.getByte("stage"));
                parcel.setStatus(rs.getString("status"));
                parcel.setSurveyDate(rs.getString("surveydate"));
                parcel.setUpi(rs.getString("upi"));
                parcel.setRegisteredOn(rs.getTimestamp("registeredon"));
                parcel.hasDispute(rs.getBoolean("hasdispute"));
                parcel.setTeamNo(rs.getByte("teamNo"));
                if (parcel.hasDispute()) {
                    parcel.setDisputes(getAllDisputes(parcel.getUpi(), parcel.getStage()));
                }
                if (parcel.getHolding() == 1) {
                    parcel.setIndividualHolders(getAllIndividualHolders(parcel.getUpi(), parcel.getStage()));
                    parcel.setPersonsWithInterest(getAllPersonsWithInterest(parcel.getUpi(), parcel.getStage()));
                } else {
                    parcel.setOrganaizationHolder(getOrganaizationHolder(parcel.getUpi(), parcel.getStage()));
                }
                returnValue.add(parcel);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> returnValue = new ArrayList<>();
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE status='active'  and username in (SELECT username FROM Groups)");
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setFathersName(rs.getString("fathersName"));
                user.setFirstName(rs.getString("firstName"));
                user.setGrandFathersName(rs.getString("grandFathersName"));
                user.setPhoneNumber(rs.getString("phoneno"));
                user.setUserId(rs.getLong("userId"));
                user.setUsername(rs.getString("username"));
                String role = rs.getString("role");
                switch (role) {
                    case "FEO":
                        user.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                        break;
                    case "SEO":
                        user.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                        break;
                    case "SUPERVISOR":
                        user.setRole(Constants.ROLE.SUPERVISOR);
                        break;
                    case "ADMINISTRATOR":
                        user.setRole(Constants.ROLE.ADMINISTRATOR);
                        break;
                }
                returnValue.add(user);
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public boolean changePassword(long userId, String password) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "UPDATE users SET password=(SELECT MD5(?)) WHERE userId=?";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, password);
            stmnt.setLong(2, userId);
            int result = stmnt.executeUpdate();
            if (result < 1) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean checkPassword(CurrentUserDTO user, String password) {
        boolean returnValue = true;
        Connection connection = CommonStorage.getConnection();
        String query = "SELECT * FROM users WHERE password = (SELECT MD5(?)) and userId=?  and username in (SELECT username FROM Groups)";
        try {
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, password);
            stmnt.setLong(2, user.getUserId());
            ResultSet result = stmnt.executeQuery();
            if (!result.next()) {
                returnValue = false;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());            returnValue = false;
        }
        return returnValue;
    }

    public boolean userExists(String username) {
        boolean returnValue = false;
        Connection connection = CommonStorage.getConnection();
        try {
            PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Users WHERE username=? and status='active' and username in (SELECT username FROM Groups)");
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = true;
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public String getWoredaName(long woredaId) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT " + CommonStorage.getCurrentLanguage() + " as woredaName FROM WOREDA WHERE woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("woredaName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public String getKebeleName(long kebeleId, String language) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT " + language + " as kebeleName FROM kebele WHERE kebele.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, kebeleId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("kebeleName");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public String getWoredaIdForUPI(long woredaId) {
        String returnValue = "";
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT woreda.code as woredacode, woreda.textValue FROM WOREDA WHERE woreda.code = ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setLong(1, woredaId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getString("woredacode");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

//    /// Reports at the woreda level
//    /// Periodical reports
//    /**
//     * @return Total number of parcels entered into iMASSREG for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfCommittedParcels(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(upi) as c FROM parcel WHERE stage=4 and status = 'active' and registeredon between ? and ? and UPI LIKE '" + kebele + "%'";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of parcels under dispute for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfParcelsWithDispute(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(upi) as c FROM parcel WHERE stage=4 and status = 'active' and registeredon between ? and ? and UPI LIKE '" + kebele + "%' and hasdispute=true";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of unique holders for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfUniqueHolders(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(ih.*) as c FROM parcel p, individualholder ih WHERE p.status = 'active' and ih.status = 'active' and  ih.registeredon between ? and ? and p.UPI = ih.UPI and p.stage = ih.stage and p.UPI LIKE '" + kebele + "%' and p.stage=4;";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of Female holders for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfFemaleHolders(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(distinct ih.holderId) as c FROM parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and  ih.registeredon between ? and ? and p.UPI = ih.UPI and p.stage = ih.stage and p.UPI LIKE '" + kebele + "%' and p.stage=4 and ih.sex='f'";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of male holders for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfMaleHolders(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(distinct ih.holderId) as c FROM parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and  ih.registeredon between ? and ? and p.UPI = ih.UPI and p.stage = ih.stage and p.UPI LIKE '" + kebele + "%' and p.stage=4 and ih.sex='m'";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of FHH for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountFemaleInSharedOwnerships(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(holderid) as c FROM IndividualHolder WHERE stage=4 and sex='f' and status = 'active' and UPI in ( SELECT ih.upi FROM parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and  ih.registeredon between ? and ?  and p.UPI = ih.UPI and p.stage = ih.stage and p.UPI LIKE '" + kebele + "%'  and p.stage=4 group by ih.UPI having count(ih.holderId) > 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of MHH for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountMaleInSharedOwnerships(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(holderid) as c FROM IndividualHolder WHERE stage=4 and sex='m' and status = 'active' and UPI in ( SELECT ih.upi FROM parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and  ih.registeredon between ? and ?  and p.UPI = ih.UPI and p.stage = ih.stage and p.UPI LIKE '" + kebele + "%'  and p.stage=4 group by ih.UPI having count(ih.holderId) > 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of parcels with of shared ownership for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfSharedOwnership(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(c) as c FROM ( SELECT ih.upi, count(ih.holderid) as c   FROM parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and  ih.registeredon between ? and ? and p.UPI = ih.UPI and p.stage = ih.stage and p.UPI LIKE '" + kebele + "%'  and p.stage=4 group by ih.UPI having count(ih.holderId) > 1) a";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of of female involved in a dispute for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfFemaleInDispute(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(*) as c FROM parcel p, dispute d WHERE p.status = 'active' and  d.status = 'active' and d.registeredon between ? and ? and p.UPI = d.UPI and p.stage = d.stage and p.UPI LIKE '" + kebele + "%'  and p.stage=4 and d.sex='f'";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of of male involved in a dispute for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfMaleInDispute(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(*) as c FROM parcel p, dispute d WHERE p.status = 'active' and  d.status = 'active' and d.registeredon between ? and ? and p.UPI = d.UPI and p.stage = d.stage and p.UPI LIKE '" + kebele + "%'  and p.stage=4 and d.sex='m'";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of single female holders for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountParcelsUnderSingleFemaleHolders(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(upi) as c FROM IndividualHolder  WHERE stage=4 and sex='f' and status = 'active' and UPI in (SELECT ih.UPI FROM Parcel p,  IndividualHolder ih  WHERE  p.status = 'active' and  ih.status = 'active' and ih.registeredon between  ? and ? and p.UPI = ih.UPI and p.stage = ih.stage and p.stage=4 and p.UPI LIKE '" + kebele + "%' GROUP BY ih.UPI HAVING count(ih.holderId) = 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of single male holders for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfSingleMaleHolders(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(upi) as c FROM IndividualHolder  WHERE stage=4 and sex='m' and status = 'active' and UPI in (SELECT ih.UPI FROM Parcel p,  IndividualHolder ih  WHERE  p.status = 'active' and  ih.status = 'active' and ih.registeredon between  ? and ? and p.UPI = ih.UPI and p.stage = ih.stage and p.stage=4 and p.UPI LIKE '" + kebele + "%' GROUP BY ih.UPI HAVING count(ih.holderId) = 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Total number of non-natural persons for a kebele
//     * @param from : starting date for report
//     * @param to : ending date for report
//     * @param kebele : kebele to report on
//     */
//    public int getCountOfNonNaturalPersons(Date from, Date to, String kebele) {
//        int returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT count(distinct oh.organizationname) as c FROM parcel p, organizationholder oh WHERE p.status = 'active' and  oh.status = 'active' and oh.registeredon between ? and ? and p.UPI = oh.UPI and p.stage = oh.stage and p.UPI LIKE '" + kebele + "%'  and p.stage=4";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            stmnt.setDate(1, from);
//            stmnt.setDate(2, to);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getInt("c");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }

    private String getKebeleTable(long kebele) {
        return getKebeleName(kebele, "english").toLowerCase().replace(" ", "_")
                + "_" + kebele;
    }

    private String getDBLinkString() {
        return "'dbname=" + CommonStorage.getGISDBName() + " user="
                + CommonStorage.getGISDBUserName() + " password="
                + CommonStorage.getGISDBPassword() + "'";
    }

    public void updateParcelArea(long kebele) {
        try {
            String query = "UPDATE Parcel SET area = P2.area FROM dblink("
                    + getDBLinkString() + " ,'SELECT parcel_id, "
                    + "ST_Area (the_geom) *POWER(0.3048,2) as area FROM " + getKebeleTable(kebele)
                    + "') as P2(parcel_id integer, area double precision) WHERE Parcel.parcelid = P2.parcel_id";
            Connection connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.executeUpdate();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
    }

//    /// Reports generated once the kebele digitization is complete 
//    /**
//     * @return The area size of a kebele
//     * @param kebele : kebele to report on
//     */
//    public double getSizeOfKebele(long kebele) {
//        double returnValue = 0.0;
//        try {
//            String query = "SELECT sum(P2.area) as totalArea FROM dblink(" + getDBLinkString()
//                    + ", 'SELECT  ST_Area (the_geom) *POWER(0.3048,2) as area FROM " + getKebeleTable(kebele) + "') as P2(area double precision)";
//
//            Connection connection = CommonStorage.getConnection();
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getDouble("totalArea");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return The average area of a parcel in a kebele
//     * @param kebele : kebele to report on
//     */
//    public double getAverageParcelSize(long kebele) {
//        double returnValue = 0.0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT avg(area) as avgArea FROM parcel WHERE stage=4 and status = 'active' and UPI LIKE '" + kebele + "%'";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getDouble("avgArea");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Average parcel size for single male holders in a kebele
//     * @param kebele : kebele to report on
//     */
//    public double getAverageParcelSizeForSingleMale(long kebele) {
//        double returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT avg(area) avgArea FROM Parcel WHERE status = 'active' and stage=4 and UPI in ( SELECT ih.upi FROM Parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and p.stage = ih.stage and p.stage=4 and p.UPI = ih.UPI and p.UPI LIKE '" + kebele + "%' and ih.sex='m' GROUP BY ih.upi HAVING count(ih.holderId) = 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getDouble("avgArea");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Average parcel size for single female holders in a kebele
//     * @param kebele : kebele to report on
//     */
//    public double getAverageParcelSizeForSingleFemale(long kebele) {
//        double returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT avg(area) avgArea FROM Parcel WHERE status = 'active' and stage=4 and UPI in ( SELECT ih.upi FROM Parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and p.stage = ih.stage and p.stage=4 and p.UPI = ih.UPI and p.UPI LIKE '" + kebele + "%' and ih.sex='f' GROUP BY ih.upi HAVING count(ih.holderId) = 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getDouble("avgArea");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Average parcel size for parcels with shared ownership in a kebele
//     * @param kebele : kebele to report on
//     */
//    public double getAverageParcelSizeForSharedOwnership(long kebele) {
//        double returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT avg(area) avgArea FROM Parcel WHERE status = 'active' and stage=4 and UPI in ( SELECT ih.upi FROM Parcel p, individualholder ih WHERE p.status = 'active' and  ih.status = 'active' and p.stage = ih.stage and p.stage=4 and p.UPI = ih.UPI and p.UPI LIKE '" + kebele + "%' GROUP BY ih.upi HAVING count(ih.holderId) > 1)";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getDouble("avgArea");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }
//
//    /**
//     * @return Average parcel size for parcels with non natural persons
//     * ownership in a kebele
//     * @param kebele : kebele to report on
//     */
//    public double getAverageParcelSizeForNonNatrualPersons(long kebele) {
//        double returnValue = 0;
//        Connection connection = CommonStorage.getConnection();
//        try {
//            String query = "SELECT avg(area) avgArea FROM Parcel WHERE status = 'active' and stage=4 and holdingType <> 1 and UPI LIKE '" + kebele + "%' ";
//            PreparedStatement stmnt = connection.prepareStatement(query);
//            ResultSet rs = stmnt.executeQuery();
//            if (rs.next()) {
//                returnValue = rs.getDouble("avgArea");
//            }
//            connection.close();
//        } catch (Exception ex) {
//            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());//        }
//        return returnValue;
//    }

    /////////////////////
    // New Reports
    // Timebound Report
    /**
     * @return Total number of parcels with married couple holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderMarriedCoupleHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single female holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleFemaleHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE sex='m' and stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) = 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single male holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleMaleHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE sex='f' and stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) = 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under orphans holders for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderOrphanHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under holding of non-natural persons for
     * a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderNonNaturalHolders(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and holdingtype!=1 and registeredon between ? and ? and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under holding of not married persons for
     * a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderOtherHoldingTypes(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under dispute for a kebele
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderDispute(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels entered into iMASSREG for a kebele
     * @param kebele : kebele to report on
     */
    public long getCountOfAllCommittedParcels(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE stage=4 and status='active' and registeredon between ? and ? and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels entered into iMASSREG for a kebele but
     * not committed
     * @param from : starting date for report
     * @param to : ending date for report
     * @param kebele : kebele to report on
     */
    public long getCountOfAllNonCommittedParcels(Date from, Date to, String kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM parcel WHERE status = 'active' and  registeredon between ? and ? and UPI LIKE '" + kebele + "%' and UPI not in ( SELECT upi FROM Parcel WHERE stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setDate(1, from);
            stmnt.setDate(2, to);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /// Kebele Report
    public long getCountOfMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM MarriedCouple WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleFemaleHolder WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleMaleHolder WHERE UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder, Parcel WHERE IndividualHolder.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.hasDispute=false AND IndividualHolder.stage=Parcel.stage AND IndividualHolder.UPI=Parcel.UPI AND IndividualHolder.stage=4 AND IndividualHolder.status='active' AND IndividualHolder.isOrphan=true";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfNonNaturalHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct organizationname) as c FROM OrganizationHolder,Parcel WHERE OrganizationHolder.UPI LIKE '" + kebele + "/%'  AND Parcel.stage=4 AND Parcel.status='active' AND Parcel.hasDispute=false AND OrganizationHolder.stage=4 AND OrganizationHolder.status='active'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.hasDispute=true AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * Gets the total number of demarcated parcels from the GIS database
     *
     * @return the total number of demarcated parcels
     * @param kebele : kebele to report on
     */
    public long getCountOfDemarcatedParcels(long kebele) {
        long returnValue = 0;
        try {
            String query = "SELECT P2.c as c FROM dblink(" + getDBLinkString()
                    + ",'SELECT count (gid) as c FROM " + getKebeleTable(kebele)
                    + "') as P2(c bigint)";
            Connection connection = CommonStorage.getConnection();
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllCommittedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsNoData(long kebele) {
        long returnValue = 0;
        returnValue = getCountOfDemarcatedParcels(kebele) - getCountOfAllCommittedParcels(kebele);
        return returnValue;
    }

    public double getAverageCountOfParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfParcelsPerMarriedCoupleHolder(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM ( SELECT count(Parcel.upi) as c, holderId FROM MarriedCouple,Parcel WHERE 	Parcel.UPI LIKE '" + kebele + "/%'  and Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and Parcel.UPI = MarriedCouple.UPI GROUP BY holderId ) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfParcelsPerSingleFemaleHolder(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM ( SELECT count(Parcel.upi) as c, holderId FROM SingleFemaleHolder,Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and Parcel.UPI = SingleFemaleHolder.UPI GROUP BY holderId ) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfParcelsPerSingleMaleHolder(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM ( SELECT count(Parcel.upi) as c, holderId FROM SingleMaleHolder,Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and Parcel.UPI = SingleMaleHolder.UPI GROUP BY holderId ) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfParcelsPerOrphanHolder(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = " SELECT avg(c) as a FROM (SELECT holderId ,count(distinct Parcel.upi) as c FROM IndividualHolder,Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  AND Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and Parcel.UPI = IndividualHolder.UPI and isOrphan=true GROUP BY holderId) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfParcelsPerNonNaturalHolder(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT organizationname ,count(distinct Parcel.upi) as c FROM OrganizationHolder,Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  AND Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and Parcel.UPI = OrganizationHolder.UPI GROUP BY organizationname) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels entered into iMASSREG for a kebele but
     * not committed
     * @param kebele : kebele to report on
     */
    public long getCountOfAllNonCommittedParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM parcel WHERE status = 'active' and  UPI LIKE '" + kebele + "%' and UPI not in ( SELECT upi FROM Parcel WHERE stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single female holders for a kebele
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM IndividualHolder WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE sex='m' and stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) = 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /**
     * @return Total number of parcels under single male holders for a kebele
     * @param kebele : kebele to report on
     */
    public long getCountOfParcelsUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM IndividualHolder WHERE hasDispute='false' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE sex='f' and stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) = 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) a FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and  Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) a FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and  Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) a FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%'  and  Parcel.hasDispute='false' and Parcel.status='active' and Parcel.stage = 4 and UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderNonNaturalHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and holdingtype!=1";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsUnderDispute(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfParcelsWithNoData(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT parcel_id, ST_Area (the_geom) * POWER(0.3048,2) as area FROM "
                    + getKebeleTable(kebele) + "') as P2(parcel_id integer, area double precision) WHERE parcel_id NOT IN (SELECT parcelId FROM Parcel WHERE status='active' and stage=4)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getTotalAreaOfAllParcels(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT ST_Area (the_geom) * POWER(0.3048,2)  as area FROM "
                    + getKebeleTable(kebele) + "') as P2(area double precision)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' stage=4 and status='active' and hasDispute='false' and UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' stage=4 and status='active' and hasDispute='false' and UPI in (SELECT UPI FROM SingleFemaleHolder )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' stage=4 and hasDispute='false' and status='active' and UPI in (SELECT UPI FROM SingleMaleHolder )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderNonNaturalHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and holdingtype!=1";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE UPI LIKE '" + kebele + "/%' and hasDispute='false' and stage=4 and status='active' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsUnderDispute(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT sum(area) as a FROM Parcel WHERE hasDispute='true' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfParcelsWithNoData(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT parcel_id, ST_Area (the_geom) * POWER(0.3048,2) as area FROM "
                    + getKebeleTable(kebele) + "') as P2(parcel_id integer, area double precision) WHERE parcel_id NOT IN (SELECT parcelId FROM Parcel WHERE status='active' and stage=4)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageAreaOfAllParcels(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(area) as a FROM dblink(" + getDBLinkString()
                    + ",'SELECT ST_Area (the_geom) * POWER(0.3048,2)  as area FROM "
                    + getKebeleTable(kebele) + "') as P2(area double precision)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    // By Other ownership evidence
    
    public long getCountOfParcelsUnderMarriedCoupleHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND otherevidence=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND otherevidence=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND otherevidence=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND otherevidence=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND otherevidence=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByOwnershipEvidence(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND otherevidence=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByOwnershipEvidence(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND otherevidence=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsByOwnershipEvidence(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND otherevidence=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    // By Land Use type
    
    public long getCountOfParcelsUnderMarriedCoupleHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND landusetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND landusetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND landusetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND landusetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND landusetype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByLandUseType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND landusetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByLandUseType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND landusetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsByLandUseType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND landusetype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    // By Soil Fertility Status
    
    public long getCountOfParcelsUnderMarriedCoupleHoldersBySoilFertility (long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND soilfertilitytype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND soilfertilitytype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesBySoilFertility(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND soilfertilitytype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeBySoilFertility(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND soilfertilitytype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsBySoilFertility(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND soilfertilitytype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    // By Soil Fertility Status
    
    public long getCountOfParcelsUnderMarriedCoupleHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND holdingtype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND holdingtype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND holdingtype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND holdingtype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND holdingtype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByHoldingType(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND holdingtype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByHoldingType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND holdingtype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsByHoldingType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND holdingtype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    // By Organisation Type

    public long getCountOfParcelsUnderNonNaturalHoldersByOrganisationType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct Parcel.UPI) as c FROM Parcel, OrganizationHolder WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.hasDispute='false' AND Parcel.status='active' AND Parcel.stage=4 AND OrganizationHolder.UPI = Parcel.UPI AND OrganizationHolder.stage=Parcel.stage AND OrganizationHolder.status='active' AND OrganizationHolder.organizationtype= ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByOrganisationType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct Parcel.UPI) as c FROM Parcel, OrganizationHolder WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.hasDispute='true' AND Parcel.status='active' AND Parcel.stage=4 AND OrganizationHolder.UPI = Parcel.UPI AND OrganizationHolder.stage=Parcel.stage AND OrganizationHolder.status='active' AND OrganizationHolder.organizationtype= ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsByOrganisationType(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct Parcel.UPI) as c FROM Parcel, OrganizationHolder WHERE Parcel.UPI LIKE '" + kebele + "/%' AND Parcel.status='active' AND Parcel.stage=4 AND OrganizationHolder.UPI = Parcel.UPI AND OrganizationHolder.stage=Parcel.stage AND OrganizationHolder.status='active' AND OrganizationHolder.organizationtype= ?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    // By means of acquisition
    
    public long getCountOfParcelsUnderMarriedCoupleHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND acquisitiontype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND acquisitiontype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByMeansOfAcquisition(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND acquisitiontype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByMeansOfAcquisition(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND acquisitiontype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsByMeansOfAcquisition(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND acquisitiontype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
 
    // By encumbrance
    
    public long getCountOfParcelsUnderMarriedCoupleHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM MarriedCouple )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleFemaleHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderSingleMaleHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM SingleMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOrphanHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND encumbrancetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM IndividualHolder WHERE isOrphan='true' and stage=4 and status = 'active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderNonNaturalHoldersByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) c FROM Parcel WHERE hasDispute='false' AND encumbrancetype=? AND stage=4 and status='active' and holdingtype!=1 and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderOtherHoldingTypesByEncumbrances(long kebele, byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='false' AND encumbrancetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsUnderDisputeByEncumbrances(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel WHERE hasDispute='true' AND encumbrancetype=? AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%'";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsByEncumbrances(long kebele,byte type) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE Parcel.UPI LIKE '" + kebele + "/%' AND encumbrancetype=? AND Parcel.status='active' AND Parcel.stage=4";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setByte(1, type);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
 
    // Physical Impairment
    
    public long getCountOfMarriedCoupleHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND	(familyrole=1 OR familyrole=2 ) AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfMarriedCoupleHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND (familyrole=1 OR familyrole=2 ) AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfSingleFemaleHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleFemaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfSingleFemaleHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleFemaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfSingleMaleHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleMaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfSingleMaleHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM SingleMaleHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfOrphanHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND isOrphan='true' AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfOrphanHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND isOrphan='true' AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfOtherHoldingTypesWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE physicalimpairment='true' AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfOtherHoldingTypesWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE physicalimpairment='false' AND stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllHoldersWithPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='true' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllHoldersWithoutPhysicalImpairment(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct holderId) as c FROM IndividualHolder WHERE UPI LIKE '" + kebele + "/%' AND status='active' AND stage=4 AND physicalimpairment='false' ";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM SingelFemaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfParcelsWithPersonsOfInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest  WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM SingelMaleHolder)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfParcelsWithPersonsOfInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' ) AND UPI in (SELECT UPI FROM IndividualHolder WHERE AND stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfParcelsWithPersonsOfInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='false' AND UPI not in (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) AND UPI IN (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfParcelsWithPersonsOfInterestUnderDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND hasDispute='true' AND UPI IN (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfAllParcelsWithPersonsOfInterest(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct UPI) as c FROM Parcel WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM PersonWithInterest WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingelFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingelMaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderOphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfPersonsWithInterestForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingelFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE  sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingelMaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE  sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderOphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfMalePersonsWithInterestForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='m' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfFemalePersonsWithInterestUnderMarriedCoupleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderSingleFemaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingelFemaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderSingleMaleHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM SingelMaleHolder WHERE stage=4 AND status='active')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderOrphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderOphanHolders(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 AND status='active' AND isOrphan='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestUnderOtherHoldingTypes(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and UPI IN (SELECT UPI FROM IndividualHolder WHERE stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public long getCountOfFemalePersonsWithInterestForParcelsWithDispute(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' and hasDispute='true')";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }
    
    public long getCountOfFemalePersonsWithInterestForAllParcels(long kebele) {
        long returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex='f' AND UPI LIKE '" + kebele + "/%' AND stage=4 AND status='active' AND UPI IN (SELECT UPI FROM Parcel WHERE stage=4 and status = 'active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getLong("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderMarriedCoupleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderSingleFemaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM SingleFemaleHolder WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderSingleMaleHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM SingleMaleHolder WHERE stage=4 and status='active' ) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderOrphanHolders(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and Parcel.UPI in (SELECT UPI FROM IndividualHolder WHERE stage=4 and status='active' and isOrphan='true') group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcelsUnderOtherHoldingTypes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and  Parcel.UPI NOT IN (SELECT UPI FROM IndividualHolder WHERE (familyrole=1 or familyrole=2) and stage=4 and status = 'active') and Parcel.UPI IN (SELECT UPI FROM IndividualHolder WHERE sex='m' and stage=4 and status = 'active' GROUP BY UPI HAVING count(holderId) > 1) group by Parcel.upi) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    //.......
    public double getAverageNumberOfPIPerParcelsUnderDisputes(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' and  Parcel.hasDispute='true') t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public double getAverageNumberOfPIPerParcels(long kebele) {
        double returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT avg(c) as a FROM (SELECT Parcel.upi,count(PersonWithInterest.*) c FROM PersonWithInterest, Parcel WHERE Parcel.stage = 4 and PersonWithInterest.UPI = Parcel.UPI and PersonWithInterest.stage=Parcel.stage and  PersonWithInterest.stage=4 and PersonWithInterest.status='active' and PersonWithInterest.UPI LIKE '" + kebele + "/%' ) t";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDouble("a");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /////////////////////

    public int getCountOfParcelsWithPersonsOfInterestForMarriedHolders(String kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(distinct upi) as c FROM Parcel  WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM PersonWithInterest WHERE stage=4 and status='active') and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public int getCountOfPersonWithInterestForParcelsUnderMarriedHolders(String kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }


    public int getCountOfMalePersonWithInterestForParcelsUnderMarriedHolders(String kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex = 'm' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public int getCountOfFemalePersonWithInterestForParcelsUnderMarriedHolders(String kebele) {
        int returnValue = 0;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT count(*) as c FROM PersonWithInterest WHERE sex = 'f' and stage=4 and status='active' and UPI LIKE '" + kebele + "/%' and UPI in (SELECT UPI FROM MarriedCouple WHERE stage=4 and status='active' )";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getInt("c");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public Date getFirstDataEntryDateInKebeleForMarriedHolders(String kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT min(registeredon) as d  FROM Parcel WHERE UPI LIKE '70106006/%' and  UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    public Date getLastDataEntryDateInKebeleForMarriedHolders(String kebele) {
        Date returnValue = null;
        Connection connection = CommonStorage.getConnection();
        try {
            String query = "SELECT max(registeredon) as d  FROM Parcel WHERE UPI LIKE '70106006/%' and  UPI in (SELECT UPI FROM MarriedCouple)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                returnValue = rs.getDate("d");
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());        }
        return returnValue;
    }

    /// Disputes Report

}
