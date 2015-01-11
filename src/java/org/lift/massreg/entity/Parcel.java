package org.lift.massreg.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.CurrentUserDTO;
import org.lift.massreg.dto.ParcelDifference;
import org.lift.massreg.util.CommonStorage;
import org.lift.massreg.util.Option;

/**
 *
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 *
 */
public class Parcel implements Entity {

    private String upi;
    private byte stage;
    private String stageText;
    private long registeredBy;
    private User registeredByUser;
    private Timestamp registeredOn;
    private double area;
    private int parcelId;
    private String certificateNumber;
    private String holdingNumber;
    private byte otherEvidence;
    private String otherEvidenceText;
    private byte currentLandUse;
    private String currentLandUseText;
    private byte soilFertility;
    private String soilFertilityText;
    private byte holding;
    private String holdingText;
    private byte meansOfAcquisition;
    private String meansOfAcquisitionText;
    private int acquisitionYear;
    private String surveyDate;
    private String mapSheetNo;
    private String status;
    private byte encumbrance;
    private String encumbranceText;
    private boolean hasDispute;
    private OrganizationHolder organaizationHolder;
    private ArrayList<IndividualHolder> individualHolders;
    private ArrayList<PersonWithInterest> personsWithInterest;
    private ArrayList<Dispute> disputes;

    private synchronized void fillInTextValues() {
        Option[] allStages = MasterRepository.getInstance().getAllStages();
        for (Option allStage : allStages) {
            if (allStage.getKey().equalsIgnoreCase(stage + "")) {
                stageText = allStage.getValue();
            }
        }
        Option[] allOtherEvidences = MasterRepository.getInstance().getAllOtherEvidenceTypes();
        for (Option allOtherEvidence : allOtherEvidences) {
            if (allOtherEvidence.getKey().equalsIgnoreCase(otherEvidence + "")) {
                otherEvidenceText = allOtherEvidence.getValue();
            }
        }

        Option[] allLandUseTypes = MasterRepository.getInstance().getAllCurrentLandUseTypes();
        for (Option allLandUseType : allLandUseTypes) {
            if (allLandUseType.getKey().equalsIgnoreCase(currentLandUse + "")) {
                currentLandUseText = allLandUseType.getValue();
            }
        }

        Option[] allSoilFertilityTypes = MasterRepository.getInstance().getAllSoilFertilityTypes();
        for (Option allSoilFertilityType : allSoilFertilityTypes) {
            if (allSoilFertilityType.getKey().equalsIgnoreCase(soilFertility + "")) {
                soilFertilityText = allSoilFertilityType.getValue();
            }
        }
        Option[] allHoldingTypes = MasterRepository.getInstance().getAllHoldingTypes();
        for (Option allHoldingType : allHoldingTypes) {
            if (allHoldingType.getKey().equalsIgnoreCase(holding + "")) {
                holdingText = allHoldingType.getValue();
            }
        }
        Option[] allMeansOfAcquisitionTypes = MasterRepository.getInstance().getAllMeansOfAcquisitionTypes();
        for (Option allMeansOfAcquisitionType : allMeansOfAcquisitionTypes) {
            if (allMeansOfAcquisitionType.getKey().equalsIgnoreCase(meansOfAcquisition + "")) {
                meansOfAcquisitionText = allMeansOfAcquisitionType.getValue();
            }
        }
        Option[] allEncumbranceTypes = MasterRepository.getInstance().getAllEncumbranceTypes();
        for (Option allEncumbranceType : allEncumbranceTypes) {
            if (allEncumbranceType.getKey().equalsIgnoreCase(encumbrance + "")) {
                encumbranceText = allEncumbranceType.getValue();
            }
        }
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public String getCertificateNumber() {
        if (certificateNumber == null) {
            certificateNumber = "";
        }
        return certificateNumber.trim();
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getHoldingNumber() {
        if (holdingNumber == null) {
            holdingNumber = "";
        }
        return holdingNumber.trim();
    }

    public void setHoldingNumber(String holdingNumber) {
        this.holdingNumber = holdingNumber;
    }

    public byte getOtherEvidence() {
        return otherEvidence;
    }

    public void setOtherEvidence(byte otherEvidence) {
        this.otherEvidence = otherEvidence;
    }

    public byte getCurrentLandUse() {
        return currentLandUse;
    }

    public void setCurrentLandUse(byte currentLandUse) {
        this.currentLandUse = currentLandUse;
    }

    public byte getSoilFertility() {
        return soilFertility;
    }

    public void setSoilFertility(byte soilFertility) {
        this.soilFertility = soilFertility;
    }

    public byte getHolding() {
        return holding;
    }

    public void setHolding(byte holding) {
        this.holding = holding;
    }

    public byte getMeansOfAcquisition() {
        return meansOfAcquisition;
    }

    public void setAcquisition(byte meansOfAcquisition) {
        this.meansOfAcquisition = meansOfAcquisition;
    }

    public int getAcquisitionYear() {
        return acquisitionYear;
    }

    public void setAcquisitionYear(int acquisitionYear) {
        this.acquisitionYear = acquisitionYear;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getMapSheetNo() {
        return mapSheetNo;
    }

    public void setMapSheetNo(String mapSheetNo) {
        this.mapSheetNo = mapSheetNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte getEncumbrance() {
        return encumbrance;
    }

    public void setEncumbrance(byte encumbrance) {
        this.encumbrance = encumbrance;
    }

    public String getStageText() {
        if (stageText == null) {
            fillInTextValues();
        }
        return stageText;
    }

    public String getOtherEvidenceText() {
        if (otherEvidenceText == null) {
            fillInTextValues();
        }
        return otherEvidenceText;
    }

    public String getCurrentLandUseText() {
        if (currentLandUseText == null) {
            fillInTextValues();
        }
        return currentLandUseText;
    }

    public String getSoilFertilityText() {
        if (soilFertilityText == null) {
            fillInTextValues();
        }
        return soilFertilityText;
    }

    public String getHoldingText() {
        if (holdingText == null) {
            fillInTextValues();
        }
        return holdingText;
    }

    public String getMeansOfAcquisitionText() {
        if (meansOfAcquisitionText == null) {
            fillInTextValues();
        }
        return meansOfAcquisitionText;
    }

    public String getStatus() {
        return status;
    }

    public String getEncumbranceText() {
        if (encumbranceText == null) {
            fillInTextValues();
        }
        return encumbranceText;
    }

    public User getRegisteredByUser() {
        if (registeredByUser == null) {
            registeredByUser = MasterRepository.getInstance().getUser(this.getRegisteredBy());
        }
        return registeredByUser;
    }

    public OrganizationHolder getOrganizationHolder() {
        return organaizationHolder;
    }

    public ArrayList<IndividualHolder> getIndividualHolders() {
        if (individualHolders == null) {
            individualHolders = MasterRepository.getInstance().getAllIndividualHolders(upi, stage);
        }
        return individualHolders;
    }

    public ArrayList<PersonWithInterest> getPersonsWithInterest() {
        if (personsWithInterest == null) {
            personsWithInterest = MasterRepository.getInstance().getAllPersonsWithInterest(upi, stage);
        }
        return personsWithInterest;
    }

    public int getPersonsWithInterestCount() {
        int returnValue = 0;
        if (getPersonsWithInterest() != null) {
            returnValue = getPersonsWithInterest().size();
        }
        return returnValue;
    }

    public void addIndividualHolder(IndividualHolder individualHolder) {
        individualHolders.add(individualHolder);
    }

    public void addPersonWithInterest(PersonWithInterest personWithInterest) {
        personsWithInterest.add(personWithInterest);
    }

    public void removeIndividualHolder(IndividualHolder individualHolder) {
        individualHolders.remove(individualHolder);
    }

    public ArrayList<Dispute> getDisputes() {
        if ((disputes == null) && (hasDispute)) {
            disputes = MasterRepository.getInstance().getAllDisputes(upi, stage);
        }
        return disputes;
    }

    public void addDispute(Dispute dispute) {
        disputes.add(dispute);
    }

    public void removeDispute(Dispute dispute) {
        disputes.remove(dispute);
    }

    public boolean saveParcelOnly() {
        return MasterRepository.getInstance().save(this);
    }

    public boolean hasDispute() {
        return hasDispute;
    }

    public String hasDisputeText() {
        return hasDispute ? "Yes" : "No";
    }

    public void hasDispute(boolean hasDispute) {
        this.hasDispute = hasDispute;
    }

    public void setOrganaizationHolder(OrganizationHolder organaizationHolder) {
        this.organaizationHolder = organaizationHolder;
    }

    @Override
    public boolean validateForSave() {
        ///TODO:
        return true;
    }

    public void setIndividualHolders(ArrayList<IndividualHolder> individualHolders) {
        this.individualHolders = individualHolders;
    }

    public void setPersonsWithInterest(ArrayList<PersonWithInterest> personsWithInterest) {
        this.personsWithInterest = personsWithInterest;
    }

    public void setDisputes(ArrayList<Dispute> disputes) {
        this.disputes = disputes;
    }

    public int getDisputeCount() {
        int count = 0;
        if (this.getDisputes() != null && hasDispute) {
            count = disputes.size();
        }
        return count;
    }

    public int getHolderCount() {
        int count = 0;
        if (this.getHolding() == 1) {
            if (individualHolders != null) {
                count = individualHolders.size();
            }
        } else if (organaizationHolder != null) {
            count = 1;
        }
        return count;
    }

    public boolean canEdit(CurrentUserDTO cudto) {
        boolean returnValue = true;
        boolean firstentry = MasterRepository.getInstance().parcelExists(upi, CommonStorage.getFEStage());
        boolean secondentry = MasterRepository.getInstance().parcelExists(upi, CommonStorage.getSEStage());
        boolean supervisor = MasterRepository.getInstance().parcelExists(upi, CommonStorage.getCorrectionStage());
        boolean confirmed = MasterRepository.getInstance().parcelExists(upi, CommonStorage.getCommitedStage());
        switch (cudto.getRole()) {
            case FIRST_ENTRY_OPERATOR:
                if (secondentry || supervisor || confirmed) {
                    returnValue = false;
                }
                break;
            case SECOND_ENTRY_OPERATOR:
                if (supervisor || confirmed) {
                    returnValue = false;
                }
                break;
            case SUPERVISOR:
                if (confirmed) {
                    returnValue = false;
                }
                break;
        }
        return returnValue;
    }

    public Dispute getDispute(byte stage, Timestamp registeredOn) {
        Dispute returnValue = new Dispute();
        if (stage == this.getStage()) {
            ArrayList<Dispute> allDisputes = getDisputes();
            for (Dispute allDispute : allDisputes) {
                if (allDispute.getRegisteredOn().equals(registeredOn)) {
                    returnValue = allDispute;
                    break;
                }
            }
        }
        return returnValue;
    }

    public IndividualHolder getIndividualHolder(String holderId, byte stage, Timestamp registeredOn) {
        IndividualHolder returnValue = new IndividualHolder();
        if (stage == this.getStage()) {
            ArrayList<IndividualHolder> allIndividualHolders = getIndividualHolders();
            for (IndividualHolder allIndividualHolder : allIndividualHolders) {
                if (allIndividualHolder.getRegisteredOn().equals(registeredOn) && allIndividualHolder.getId().equalsIgnoreCase(holderId)) {
                    returnValue = allIndividualHolder;
                    break;
                }
            }
        }
        return returnValue;
    }

    public PersonWithInterest getPersonWithInterest(byte stage, Timestamp registeredOn) {
        PersonWithInterest returnValue = new PersonWithInterest();
        if (stage == this.getStage()) {
            ArrayList<PersonWithInterest> allPersonsWithInterest = getPersonsWithInterest();
            for (PersonWithInterest allPersonsWithInterest1 : allPersonsWithInterest) {
                if (allPersonsWithInterest1.getRegisteredOn().equals(registeredOn)) {
                    returnValue = allPersonsWithInterest1;
                    break;
                }
            }
        }
        return returnValue;
    }

    public boolean remove() {
        return delete(registeredOn, upi, stage);
    }

    public void complete() {
        MasterRepository.getInstance().completeParcel(getUpi(), getStage(), getRegisteredOn());
    }

    public void commit() {
        MasterRepository.getInstance().commit(this);
        if (this.getHolding() == 1) {
            for (IndividualHolder individualHolder : individualHolders) {
                individualHolder.commit();
            }
            for (PersonWithInterest personsWithInterest1 : personsWithInterest) {
                personsWithInterest1.commit();
            }
        } else if (organaizationHolder != null) {
            organaizationHolder.commit();
        }
        if (disputes != null) {
            for (Dispute dispute : disputes) {
                dispute.commit();
            }
        }
    }

    public void submitForCorrection() {
        MasterRepository.getInstance().submitForCorrection(this);
        if (this.getHolding() == 1) {
            for (IndividualHolder individualHolder : individualHolders) {
                individualHolder.submitForCorrection();
            }
            if (personsWithInterest != null) {
                for (PersonWithInterest personWithInterest : personsWithInterest) {
                    personWithInterest.submitForCorrection();
                }
            }
        } else if (organaizationHolder != null) {
            organaizationHolder.submitForCorrection();
        }
        if (disputes != null) {
            for (Dispute dispute : disputes) {
                dispute.submitForCorrection();
            }
        }
    }

    @Override
    public boolean validateForUpdate() {
        return true;
    }

    public boolean equalsParcel(Parcel obj) {
        boolean returnValue = true;
        if (this.acquisitionYear != obj.getAcquisitionYear()) {
            returnValue = false;
        }
        if (!this.getCertificateNumber().trim().equalsIgnoreCase(obj.getCertificateNumber().trim())) {
            returnValue = false;
        }
        if (!this.getHoldingNumber().trim().equalsIgnoreCase(obj.getHoldingNumber().trim())) {
            returnValue = false;
        }
        if (this.currentLandUse != obj.getCurrentLandUse()) {
            returnValue = false;
        }
        if (this.encumbrance != obj.getEncumbrance()) {
            returnValue = false;
        }
        if (this.holding != obj.getHolding()) {
            returnValue = false;
        } else if (this.holding != 1) {
            if (!this.organaizationHolder.equalsOrganizationHolder(obj.organaizationHolder)) {
                returnValue = false;
            }
        }
        if (!this.mapSheetNo.trim().equalsIgnoreCase(obj.getMapSheetNo().trim())) {
            returnValue = false;
        }
        if (this.meansOfAcquisition != obj.getMeansOfAcquisition()) {
            returnValue = false;
        }
        if (this.otherEvidence != obj.getOtherEvidence()) {
            returnValue = false;
        }
        if (this.soilFertility != obj.getSoilFertility()) {
            returnValue = false;
        }
        if (!this.surveyDate.trim().equalsIgnoreCase(obj.getSurveyDate().trim())) {
            returnValue = false;
        }
        if (!this.upi.trim().equalsIgnoreCase(obj.getUpi().trim())) {
            returnValue = false;
        }
        if (this.hasDispute != obj.hasDispute()) {
            returnValue = false;
        }
        if (this.getDisputeCount() != obj.getDisputeCount()) {
            returnValue = false;
        }
        if (this.getHolderCount() != obj.getHolderCount()) {
            returnValue = false;
        }
        if (this.getPersonsWithInterestCount() != obj.getPersonsWithInterestCount()) {
            returnValue = false;
        }
        // compare individual holders
        if (this.getHolding() == 1 || obj.getHolding() == 1) {
            if ((this.getIndividualHolders() != null) != (obj.getIndividualHolders() != null)) {
                returnValue = false;
            }
            if (this.getIndividualHolders() != null && obj.getIndividualHolders() != null) {
                for (int i = 0; i < this.getIndividualHolders().size(); i++) {
                    boolean found = false;
                    IndividualHolder firstHolder = this.getIndividualHolders().get(i);
                    if (findSimilar(firstHolder, obj.getIndividualHolders())) {
                        found = true;
                    }
                    if (!found) {
                        returnValue = false;
                        break;
                    }
                }
            }
        }
        // comapre People with interest 
        if ((this.getPersonsWithInterest() != null) != (obj.getPersonsWithInterest() != null)) {
            returnValue = false;
        }
        if (this.getPersonsWithInterest() != null && obj.getPersonsWithInterest() != null) {
            for (int i = 0; i < this.getPersonsWithInterest().size(); i++) {
                boolean found = false;
                PersonWithInterest firstPWI = this.getPersonsWithInterest().get(i);
                if (findSimilar(firstPWI, obj.getPersonsWithInterest())) {
                    found = true;
                }
                if (!found) {
                    returnValue = false;
                    break;
                }
            }
        }
        // compare disputes
        if ((this.getDisputes() != null) != (obj.getDisputes() != null)) {
            returnValue = false;
        }
        if (this.getDisputes() != null && obj.getDisputes() != null) {
            for (int i = 0; i < this.getDisputes().size(); i++) {
                boolean found = false;
                Dispute firstDispute = this.getDisputes().get(i);
                if (findSimilar(firstDispute, obj.getDisputes())) {
                    found = true;
                }
                if (!found) {
                    returnValue = false;
                    break;
                }
            }
        }
        return returnValue;
    }

    public static boolean delete(Timestamp registeredOn, String upi, byte stage) {
        return MasterRepository.getInstance().deleteParcel(registeredOn, upi, stage);
    }

    public static ParcelDifference difference(Parcel firstParcel, Parcel secondParcel) {
        ParcelDifference returnValue = new ParcelDifference();

        if (firstParcel.getAcquisitionYear() != secondParcel.getAcquisitionYear()) {
            returnValue.setAcquisitionYear(true);
        }
        if (!firstParcel.getCertificateNumber().trim().equalsIgnoreCase(secondParcel.getCertificateNumber().trim())) {
            returnValue.setCertificateNumber(true);
        }
        if (!firstParcel.getHoldingNumber().trim().equalsIgnoreCase(secondParcel.getHoldingNumber().trim())) {
            returnValue.setHoldingNumber(true);
        }
        if (firstParcel.getCurrentLandUse() != secondParcel.getCurrentLandUse()) {
            returnValue.setCurrentLandUse(true);
        }
        if (firstParcel.getDisputeCount() != secondParcel.getDisputeCount()) {
            returnValue.setDisputesCount(true);
        }
        if (firstParcel.getEncumbrance() != secondParcel.getEncumbrance()) {
            returnValue.setEncumbrance(true);
        }
        if (firstParcel.getHolderCount() != secondParcel.getHolderCount()) {
            returnValue.setHoldersCount(true);
        }
        if (firstParcel.getPersonsWithInterestCount() != secondParcel.getPersonsWithInterestCount()) {
            returnValue.setPersonsWithInterestCount(true);
        }
        if (firstParcel.getHolding() != secondParcel.getHolding()) {
            returnValue.setHolding(true);
        }
        if (!firstParcel.getMapSheetNo().trim().equalsIgnoreCase(secondParcel.getMapSheetNo().trim())) {
            returnValue.setMapSheetNo(true);
        }
        if (firstParcel.getMeansOfAcquisition() != secondParcel.getMeansOfAcquisition()) {
            returnValue.setMeansOfAcquisition(true);
        }
        if (firstParcel.getOtherEvidence() != secondParcel.getOtherEvidence()) {
            returnValue.setOtherEvidence(true);
        }
        if (firstParcel.getSoilFertility() != secondParcel.getSoilFertility()) {
            returnValue.setSoilFertility(true);
        }
        if (!firstParcel.getSurveyDate().trim().equalsIgnoreCase(secondParcel.getSurveyDate().trim())) {
            returnValue.setSurveyDate(true);
        }
        if (firstParcel.hasDispute() != secondParcel.hasDispute()) {
            returnValue.setHasDispute(true);
        }

        // compare Organization Holders
        if (firstParcel.getOrganizationHolder() != null && secondParcel.getOrganizationHolder() != null) {
            returnValue.setOrganizationHolderDifference(OrganizationHolder.difference(firstParcel.getOrganizationHolder(),
                    secondParcel.getOrganizationHolder()));
        }
        // comapre Individual Holders
        if (firstParcel.getHolding() == 1 || secondParcel.getHolding() == 1) {
            if ((firstParcel.getIndividualHolders() != null) != (secondParcel.getIndividualHolders() != null)) {
                returnValue.setHoldersCount(true);
            }
            if (firstParcel.getIndividualHolders() != null && secondParcel.getIndividualHolders() != null) {
                for (int i = 0; i < firstParcel.getIndividualHolders().size(); i++) {
                    boolean found = false;
                    IndividualHolder firstHolder = firstParcel.getIndividualHolders().get(i);
                    if (findSimilar(firstHolder, secondParcel.getIndividualHolders())) {
                        found = true;
                    }
                    if (!found) {
                        returnValue.setIndividualHolderDetails(true);
                        break;
                    }
                }
            }
        }
        // comapre People with interest 
        if ((firstParcel.getPersonsWithInterest() != null) != (secondParcel.getPersonsWithInterest() != null)) {
            returnValue.setPersonsWithInterestCount(true);
        }
        if (firstParcel.getPersonsWithInterest() != null && secondParcel.getPersonsWithInterest() != null) {
            for (int i = 0; i < firstParcel.getPersonsWithInterest().size(); i++) {
                boolean found = false;
                PersonWithInterest firstPWI = firstParcel.getPersonsWithInterest().get(i);
                if (findSimilar(firstPWI, secondParcel.getPersonsWithInterest())) {
                    found = true;
                }
                if (!found) {
                    returnValue.setPersonsWithInterestDetails(true);
                    break;
                }
            }
        }
        // compare disputes
        if ((firstParcel.getDisputes() != null) != (secondParcel.getDisputes() != null)) {
            returnValue.setDisputesCount(true);
        }
        if (firstParcel.getDisputes() != null && secondParcel.getDisputes() != null) {
            for (int i = 0; i < firstParcel.getDisputes().size(); i++) {
                boolean found = false;
                Dispute firstDispute = firstParcel.getDisputes().get(i);
                if (findSimilar(firstDispute, secondParcel.getDisputes())) {
                    found = true;
                }
                if (!found) {
                    returnValue.setDisputesDetails(true);
                    break;
                }
            }
        }

        return returnValue;
    }

    private static boolean findSimilar(IndividualHolder holder, ArrayList<IndividualHolder> holdersList) {
        boolean returnValue = false;
        if (holdersList.size() > 0) {
            for (IndividualHolder holdersList1 : holdersList) {
                if (holder.equalsIndividualHolder(holdersList1)) {
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }

    private static boolean findSimilar(PersonWithInterest pwi, ArrayList<PersonWithInterest> pwiList) {
        boolean returnValue = false;
        if (pwiList.size() > 0) {
            for (PersonWithInterest pwi1 : pwiList) {
                if (pwi.equalsPersonsWithInterest(pwi1)) {
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }

    private static boolean findSimilar(Dispute dispute, ArrayList<Dispute> disputeList) {
        boolean returnValue = false;
        if (disputeList.size() > 0) {
            for (Dispute dispute1 : disputeList) {
                if (dispute.equalsDispute(dispute1)) {
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }
}
