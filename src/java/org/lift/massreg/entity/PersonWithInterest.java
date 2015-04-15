package org.lift.massreg.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.Change;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class PersonWithInterest implements Entity {

    private String upi;
    private byte stage;
    private long registeredBy;
    private Timestamp registeredOn;
    private String firstName;
    private String fathersName;
    private String grandFathersName;
    private String sex;
    private String dateOfBirth;
    private String status;
   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }

    public byte getStage() {
        return stage;
    }

    public void setStage(byte stage) {
        this.stage = stage;
    }

    public long getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(long registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Timestamp getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Timestamp registeredOn) {
        this.registeredOn = registeredOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getGrandFathersName() {
        return grandFathersName;
    }

    public void setGrandFathersName(String grandFathersName) {
        this.grandFathersName = grandFathersName;
    }

    public String getSex() {
        return sex.toLowerCase();
    }

    public String getSexText() {
        String returnValue = "";
        if (sex.equalsIgnoreCase("m")) {
            returnValue = "Male";
        } else {
            returnValue = "Female";
        }
        return returnValue;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
   
    public String getFullName() {
        return getFirstName() + " " + getFathersName() + " " + getGrandFathersName();
    }

    public boolean save() {
        return MasterRepository.getInstance().save(this);
    }

    @Override
    public boolean validateForSave() {
        return true;
    }

    public boolean remove(HttpServletRequest request) {
        return delete(request,registeredOn, upi, stage);
    }

    public static boolean delete(HttpServletRequest request, Timestamp registeredOn, String upi, byte stage) {
        return MasterRepository.getInstance().deletePersonWithInterest(request,registeredOn, upi, stage);
    }

    public boolean equalsPersonsWithInterest(PersonWithInterest obj) {
        boolean returnValue = true;
        if (!this.getDateOfBirth().trim().equalsIgnoreCase(obj.getDateOfBirth().trim())) {
            returnValue = false;
        }
        if (!this.getFathersName().trim().equalsIgnoreCase(obj.getFathersName().trim())) {
            returnValue = false;
        }
        if (!this.getFirstName().trim().equalsIgnoreCase(obj.getFirstName().trim())) {
            returnValue = false;
        }
        if (!this.getGrandFathersName().trim().equalsIgnoreCase(obj.getGrandFathersName().trim())) {
            returnValue = false;
        }
        if (!this.getSex().trim().equalsIgnoreCase(obj.getSex().trim())) {
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }

    public void commit() {
        MasterRepository.getInstance().commit(this);
    }

    public boolean submitForCorrection() {
        return MasterRepository.getInstance().submitForCorrection(this);
    }

    public ArrayList<Change> getDifferenceForChangeLog(PersonWithInterest newPersonWithInterest) {
        ArrayList<Change> returnValue = new ArrayList<>();

        if (!this.getFirstName().equalsIgnoreCase(newPersonWithInterest.getFirstName())) {
            returnValue.add(new Change("firstname", this.getFirstName() + "", newPersonWithInterest.getFirstName() + ""));
        }
        if (!this.getFathersName().equalsIgnoreCase(newPersonWithInterest.getFathersName())) {
            returnValue.add(new Change("fathersname", this.getFirstName() + "", newPersonWithInterest.getFathersName() + ""));
        }
        if (!this.getGrandFathersName().equalsIgnoreCase(newPersonWithInterest.getGrandFathersName())) {
            returnValue.add(new Change("grandfathersname", this.getGrandFathersName() + "", newPersonWithInterest.getGrandFathersName() + ""));
        }
        if (!this.getSex().equalsIgnoreCase(newPersonWithInterest.getSex())) {
            returnValue.add(new Change("sex", this.getSex() + "", newPersonWithInterest.getSex() + ""));
        }
        if (!this.getDateOfBirth().equalsIgnoreCase(newPersonWithInterest.getDateOfBirth())) {
            returnValue.add(new Change("dateofbirth", this.getDateOfBirth() + "", newPersonWithInterest.getDateOfBirth() + ""));
        }
        return returnValue;
    }

    public boolean submitForMinorCorrection() {
        return MasterRepository.getInstance().submitForMinorCorrection(this);
    }
    public boolean submitForMajorCorrection() {
        return MasterRepository.getInstance().submitForMajorCorrection(this);
    }

    public boolean submitToConfirmed() {
        return MasterRepository.getInstance().submitToConfirmed(this);
    }
}
