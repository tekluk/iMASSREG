package org.lift.massreg.controller;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.lift.massreg.dao.*;
import org.lift.massreg.entity.*;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CorrectionSupervisor {

    /**
     * Handlers request for getting the welcome page
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void welcomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInMajorCorrectionSupervisor();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_MCSUPERVISOR));
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the welcome form
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void welcomeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInMajorCorrectionSupervisor();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_MCSUPERVISOR));
        rd.forward(request, response);
    }

    
    /**
     * Handlers request for getting the find parcel form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void findParceForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Parcel> parcelsInCorrection = MasterRepository.getInstance().getALLParcelsInCorrection();
        request.setAttribute("parcelsInCorrection", parcelsInCorrection);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_MCSUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the fix parcel form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel correctionParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", correctionParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to edit does not exist in the database.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
                Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_PARCEL_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update parcel details 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel oldParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        Parcel newParcel = new Parcel();
        try {
            newParcel.setStatus(Constants.STATUS[0]);
            newParcel.setStage(oldParcel.getStage());
            newParcel.setCertificateNumber(request.getParameter("certificateNumber"));
            newParcel.setHoldingNumber(request.getParameter("holdingNumber"));
            newParcel.setMapSheetNo(request.getParameter("mapsheetNumber"));
            newParcel.setAcquisition(Byte.parseByte(request.getParameter("meansOfAcquisition")));
            newParcel.setAcquisitionYear(Integer.parseInt(request.getParameter("acquisitionYear")));
            newParcel.setCurrentLandUse(Byte.parseByte(request.getParameter("currentLandUse")));
            newParcel.setEncumbrance(Byte.parseByte(request.getParameter("encumbrance")));
            newParcel.setHolding(Byte.parseByte(request.getParameter("holdingType")));
            newParcel.setOtherEvidence(Byte.parseByte(request.getParameter("otherEvidence")));
            newParcel.setSoilFertility(Byte.parseByte(request.getParameter("soilFertility")));
            newParcel.setSurveyDate(request.getParameter("surveyDate"));
            if (request.getParameter("holdingLotNumber") != null && !request.getParameter("holdingLotNumber").trim().isEmpty()) {
                newParcel.setHoldingLotNumber(Integer.parseInt(request.getParameter("holdingLotNumber")));
            }
            newParcel.hasDispute(Boolean.parseBoolean(request.getParameter("hasDispute")));
            newParcel.setTeamNo(Byte.parseByte(request.getParameter("teamNo")));
            if (newParcel.validateForUpdate()) {
                //Parcel.getLogStatment(oldParcel,newParcel);
                MasterRepository.getInstance().update(oldParcel, newParcel);
                if (newParcel.getHolding() == 1 && oldParcel.getHolding() != 1) {
                    OrganizationHolder.delete(request,oldParcel.getUpi(), oldParcel.getStage());
                }
                if (newParcel.getHolding() != 1 && oldParcel.getHolding() == 1) {
                    ArrayList<IndividualHolder> holders = oldParcel.getIndividualHolders();
                    for (IndividualHolder holder : holders) {
                        holder.remove(request);
                    }
                }
                if (!newParcel.hasDispute() && oldParcel.hasDispute()) {
                    ArrayList<Dispute> disputes = oldParcel.getDisputes();
                    for (Dispute dispute : disputes) {
                        dispute.remove(request);
                    }
                }
                viewHolder(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_EDIT_PARCEL_MCSUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Parcel");
            request.getSession().setAttribute("returnAction", Constants.ACTION_EDIT_PARCEL_MCSUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the fix parcel form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel;
        if (MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getConfirmedStage())) {
            currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getConfirmedStage());
            request.setAttribute("currentParcel", currentParcel);
        } else if (MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
            request.setAttribute("currentParcel", currentParcel);
        } else {
            currentParcel = null;
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The parcel you are trying to fix does not exist in the database.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }

        // if the parcel does exit in the database 
        if (currentParcel != null) {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PARCEL_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view parcel holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        parcel = (Parcel) request.getAttribute("currentParcel");
        if (parcel != null) {
            if (parcel.getHolding() == 1) {
                individualHolderList(request, response);
            } else if (parcel.getHolderCount() < 1) {
                addOrganizationHolder(request, response);
            } else {
                viewOrganizationHolder(request, response);
            }
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view list of individual holders 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void individualHolderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());
        Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
        request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_INDIVIDUAL_HOLDERS_LIST_MCSUPERVISOR));
        rd.forward(request, response);
    }


    /**
     * Handlers request to view list of guardians holders 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void guardiansList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());
        Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
        request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_GUARDIANS_LIST_MCSUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request for getting the edit individual holder form by
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The holder you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_INDIVIDUAL_HOLDER_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view organization holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_ORGANIZATION_HOLDER_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing an individual holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_INDIVIDUAL_HOLDER_MCSUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the holder your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update individual holder information by the
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateIndividualHolder(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);

        IndividualHolder oldIndividualHolder = currentParcel.getIndividualHolder(
                request.getParameter("oldHolderId"), currentParcel.getStage(),
                Timestamp.valueOf(request.getParameter("registeredOn")));
        IndividualHolder newIndividualHolder = new IndividualHolder();
        try {
            newIndividualHolder.setDateOfBirth(request.getParameter("dateofbirth"));
            newIndividualHolder.setFamilyRole(Byte.parseByte(request.getParameter("familyrole")));
            newIndividualHolder.setFirstName(request.getParameter("firstname"));
            newIndividualHolder.setFathersName(request.getParameter("fathersname"));
            newIndividualHolder.setGrandFathersName(request.getParameter("grandfathersname"));
            newIndividualHolder.setId(request.getParameter("newHolderId"));
            newIndividualHolder.isDeceased(Boolean.parseBoolean(request.getParameter("deceased")));
            newIndividualHolder.hasPhysicalImpairment(Boolean.parseBoolean(request.getParameter("physicalImpairment")));
            newIndividualHolder.isOrphan(Boolean.parseBoolean(request.getParameter("isOrphan")));
            newIndividualHolder.setSex(request.getParameter("sex"));
            newIndividualHolder.setUpi(request.getSession().getAttribute("upi").toString());
            newIndividualHolder.setStatus(Constants.STATUS[0]);

            if (newIndividualHolder.validateForUpdate()) {
                MasterRepository.getInstance().update(oldIndividualHolder, newIndividualHolder);
                viewHolder(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_MCSUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Parcel");
            request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_PARCEL_MCSUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to save a holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void saveHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            if (parcel.getHolding() == 1) {
                saveIndividualHolder(request, response);
            } else {
                saveOrganizationHolder(request, response);
            }
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to save an individual holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void saveIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = (Parcel) request.getAttribute("currentParcel");
        if (parcel != null) {
            IndividualHolder ih = new IndividualHolder();
            try {
                ih.setRegisteredOn(Timestamp.from(Instant.now()));

                ih.setDateOfBirth(request.getParameter("dateofbirth"));
                ih.setFamilyRole(Byte.parseByte(request.getParameter("familyrole")));
                ih.setFirstName(request.getParameter("firstname"));
                ih.setFathersName(request.getParameter("fathersname"));
                ih.setGrandFathersName(request.getParameter("grandfathersname"));
                ih.setId(request.getParameter("holderId"));
                ih.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                ih.setSex(request.getParameter("sex"));
                ih.hasPhysicalImpairment(Boolean.parseBoolean(request.getParameter("physicalImpairment")));
                 ih.isDeceased(Boolean.parseBoolean(request.getParameter("deceased")));
                ih.isOrphan(Boolean.parseBoolean(request.getParameter("isOrphan")));
                ih.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
                ih.setUpi(request.getSession().getAttribute("upi").toString());
                ih.setStatus(Constants.STATUS[0]);

                if (ih.validateForSave()) {
                    ih.save();
                    viewHolder(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_MCSUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }

    }

    /**
     * Handlers request to save organization holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void saveOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = (Parcel) request.getAttribute("currentParcel");
        if (parcel != null) {

            OrganizationHolder oh = new OrganizationHolder();
            try {
                oh.setRegisteredon(Timestamp.from(Instant.now()));
                oh.setName(request.getParameter("organizationName"));
                oh.setOrganizationType(Byte.parseByte(request.getParameter("organizationType")));
                oh.setRegisteredby(CommonStorage.getCurrentUser(request).getUserId());
                oh.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
                oh.setUpi(request.getSession().getAttribute("upi").toString());
                oh.setStatus(Constants.STATUS[0]);

                if (oh.validateForSave()) {
                    oh.save();

                    if (parcel.hasDispute()) {
                        viewDisputeList(request, response);
                    } else {
                        submitToConfirmed(request, response);
                    }
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_MCSUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to Parcel");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }
    }

    /**
     * Handlers request to view list of persons with interest on the parcel by
     * the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void personsWithInterestList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());
            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
            request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_PERSONS_WITH_INTEREST_LIST_MCSUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to finish data entry 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void finishDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            submitToConfirmed(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to finish data entry 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void finishPersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            submitToConfirmed(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request send the current parcel to confirmed status
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void submitToConfirmed(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("reviewMode", false);
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        currentParcel.submitToConfirmed();
        request.getSession().setAttribute("upi", null);
        request.getSession().setAttribute("parcelNo", null);
        welcomeForm(request, response);
    }

    
    /**
     * Handlers request to save a person with interest 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void savePersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        if (parcel != null) {
            PersonWithInterest pwi = new PersonWithInterest();
            try {
                pwi.setRegisteredOn(Timestamp.from(Instant.now()));
                pwi.setDateOfBirth(request.getParameter("dateofbirth"));
                pwi.setFirstName(request.getParameter("firstname"));
                pwi.setFathersName(request.getParameter("fathersname"));
                pwi.setGrandFathersName(request.getParameter("grandfathersname"));
                pwi.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                pwi.setSex(request.getParameter("sex"));
                pwi.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
                pwi.setUpi(request.getSession().getAttribute("upi").toString());
                pwi.setStatus(Constants.STATUS[0]);

                if (pwi.validateForSave()) {
                    pwi.save();
                    personsWithInterestList(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to person with persons with interest list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCSUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go Back To Welcome Page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }

    }

    
    /**
     * Handlers request to save a person with interest 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void saveGuardian(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        if (parcel != null) {
            Guardian guardians = new Guardian();
            try {
                guardians.setRegisteredOn(Timestamp.from(Instant.now()));
                guardians.setDateOfBirth(request.getParameter("dateofbirth"));
                guardians.setFirstName(request.getParameter("firstname"));
                guardians.setFathersName(request.getParameter("fathersname"));
                guardians.setGrandFathersName(request.getParameter("grandfathersname"));
                guardians.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                guardians.setSex(request.getParameter("sex"));
                guardians.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
                guardians.setUpi(request.getSession().getAttribute("upi").toString());
                guardians.setStatus(Constants.STATUS[0]);

                if (guardians.validateForSave()) {
                    guardians.save();
                    guardiansList(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to guardians list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_GUARDIANS_LIST_MCSUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go Back To Welcome Page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        }

    }


    /**
     * Handlers request for viewing a person with interest 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewPersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_PERSON_WITH_INTEREST_MCSUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the holder your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing a guardian 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewGuardian(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_GUARDIAN_MCSUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the guardian your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }


    /**
     * Handlers request for getting the edit person with form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editPersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (currentParcel == null) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The person with interest you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_PERSON_WITH_INTEREST_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the edit guardian form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editGuardian(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (currentParcel == null) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The guardian you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_GUARDIAN_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }


    /**
     * Handlers request to update person with interest information by the
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updatePersonWithInterest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);

        PersonWithInterest oldPersonWithInterest = currentParcel.getPersonWithInterest(
                currentParcel.getStage(), Timestamp.valueOf(request.getParameter("registeredOn")));
        PersonWithInterest newPersonWithInterest = new PersonWithInterest();
        try {
            newPersonWithInterest.setDateOfBirth(request.getParameter("dateofbirth"));
            newPersonWithInterest.setFirstName(request.getParameter("firstname"));
            newPersonWithInterest.setFathersName(request.getParameter("fathersname"));
            newPersonWithInterest.setGrandFathersName(request.getParameter("grandfathersname"));
            newPersonWithInterest.setSex(request.getParameter("sex"));
            newPersonWithInterest.setUpi(request.getSession().getAttribute("upi").toString());
            newPersonWithInterest.setStatus(Constants.STATUS[0]);
            if (newPersonWithInterest.validateForUpdate()) {
                //PersonWithInterest.getLogStatment(oldPersonWithInterest,newPersonWithInterest);
                MasterRepository.getInstance().update(oldPersonWithInterest, newPersonWithInterest);
                personsWithInterestList(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to persons with interest list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCSUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go Back to Welcome Page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update guardian information 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateGuardian(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);

        Guardian oldGuardian = currentParcel.getGuardian(
                currentParcel.getStage(), Timestamp.valueOf(request.getParameter("registeredOn")));
        Guardian newGuardian = new Guardian();
        try {
            newGuardian.setDateOfBirth(request.getParameter("dateofbirth"));
            newGuardian.setFirstName(request.getParameter("firstname"));
            newGuardian.setFathersName(request.getParameter("fathersname"));
            newGuardian.setGrandFathersName(request.getParameter("grandfathersname"));
            newGuardian.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
            newGuardian.setSex(request.getParameter("sex"));
            newGuardian.setUpi(request.getSession().getAttribute("upi").toString());
            newGuardian.setStatus(Constants.STATUS[0]);
            if (newGuardian.validateForUpdate()) {
                MasterRepository.getInstance().update(oldGuardian, newGuardian);
                guardiansList(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to guardians list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_GUARDIANS_LIST_MCSUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go Back to Welcome Page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    
    /**
     * Handlers request to delete a person with interest 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deletePersonWithInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (PersonWithInterest.delete(request,Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            personsWithInterestList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleteing the person with interest");
            request.getSession().setAttribute("returnTitle", "Go back to persons with interest list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_PERSONS_WITH_INTEREST_LIST_MCSUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a guardian 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deleteGuardian(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (Guardian.delete(request,Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            guardiansList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleteing the guardian");
            request.getSession().setAttribute("returnTitle", "Go back to guardian list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_GUARDIANS_LIST_MCSUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    
    /**
     * Handlers request to delete an individual holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deleteIndividualHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (IndividualHolder.delete(request,request.getParameter("holderId"), Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
            request.setAttribute("currentParcel", parcel);
            individualHolderList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleteing the holder");
            request.getSession().setAttribute("returnTitle", "Go back to dispute list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_MCSUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to view dispute list 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewDisputeList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        boolean reviewMode = false;
        Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());
        Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
        request.setAttribute("currentParcelDifference", Parcel.difference(feoParcel, seoParcel));
        request.setAttribute("currentParcel", parcel);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DISPUTE_LIST_MCSUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request to save a dispute 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void saveDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (parcel != null) {
            Dispute dispute = new Dispute();
            try {
                dispute.setRegisteredOn(Timestamp.from(Instant.now()));
                dispute.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
                dispute.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
                dispute.setUpi(request.getSession().getAttribute("upi").toString());

                dispute.setFirstName(request.getParameter("firstname"));
                dispute.setFathersName(request.getParameter("fathersname"));
                dispute.setGrandFathersName(request.getParameter("grandfathersname"));
                dispute.setSex(request.getParameter("sex"));
                dispute.setDisputeStatus(Byte.parseByte(request.getParameter("disputeStatus")));
                dispute.setDisputeType(Byte.parseByte(request.getParameter("disputeType")));
                if (dispute.validateForSave()) {
                    dispute.save();
                    viewDisputeList(request, response);
                } else {
                    // if the parcel fails to validate show error message
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to dispute list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_DISPUTE_LIST_MCSUPERVISOR);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (NumberFormatException | ServletException | IOException ex) {
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to wlecome page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing a dispute 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_DISPUTE_MCSUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the dispute your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the edit dispute form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        if (!MasterRepository.getInstance().parcelExists(request.getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The dispute you are trying to edit does not exist in the database, use the add button to register it.");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_DISPUTE_MCSUPERVISOR));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update dispute information 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);
        // if the parcel does not exit in the database 
        Dispute oldDispute = currentParcel.getDispute(currentParcel.getStage(), Timestamp.valueOf(request.getParameter("registeredOn")));
        Dispute newDispute = new Dispute();
        try {
            newDispute.setRegisteredOn(Timestamp.from(Instant.now()));
            newDispute.setRegisteredBy(CommonStorage.getCurrentUser(request).getUserId());
            newDispute.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
            newDispute.setUpi(oldDispute.getUpi());
            newDispute.setFirstName(request.getParameter("firstname"));
            newDispute.setFathersName(request.getParameter("fathersname"));
            newDispute.setGrandFathersName(request.getParameter("grandfathersname"));
            newDispute.setDisputeStatus(Byte.parseByte(request.getParameter("disputeStatus")));
            newDispute.setSex(request.getParameter("sex"));
            newDispute.setDisputeType(Byte.parseByte(request.getParameter("disputeType")));
            if (newDispute.validateForUpdate()) {
                //Dispute.getLogStatment(oldDispute,newDispute);
                MasterRepository.getInstance().update(oldDispute, newDispute);
                viewDisputeList(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_DISPUTE_LIST_MCSUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to wlecome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a dispute 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deleteDispute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (Dispute.delete(request,Timestamp.valueOf(request.getParameter("registeredOn")), request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage())) {
            Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
            request.setAttribute("currentParcel", parcel);
            viewDisputeList(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleting the dispute");
            request.getSession().setAttribute("returnTitle", "Go back to dispute list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_DISPUTE_LIST_MCSUPERVISOR);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to add organization holder 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void addOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_ADD_ORGANIZATION_HOLDER_MCSUPERVISOR));
        rd.forward(request, response);
    }

    /**
     * Handlers request to finish data entry 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void finishOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") != null) {
            submitToConfirmed(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to edit organization holder form 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editOrganizationHolder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        if (request.getAttribute("currentParcel") != null) {
            Parcel feoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionFEStage());

            Parcel seoParcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSEStage());
            if (feoParcel.getHolding() > 1 && seoParcel.getHolding() > 1) {
                request.setAttribute("currentOrganizationHolderDifference", OrganizationHolder.difference(feoParcel.getOrganizationHolder(), seoParcel.getOrganizationHolder()));
                request.getSession().setAttribute("ignoreReviewMode",false);
            }else{
                request.getSession().setAttribute("ignoreReviewMode",true);
            }
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_ORGANIZATION_HOLDER_MCSUPERVISOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update organization holder information by the
     * supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateOrganizationHolder(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        Parcel currentParcel = MasterRepository.getInstance().getParcel(
                request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", currentParcel);

        try {
            OrganizationHolder oldOrganizationHolder = currentParcel.getOrganizationHolder();
            OrganizationHolder newOrganizationHolder = new OrganizationHolder();
            newOrganizationHolder.setName(request.getParameter("organizationName"));
            newOrganizationHolder.setOrganizationType(Byte.parseByte(request.getParameter("organizationType")));
            newOrganizationHolder.setStage(CommonStorage.getMajorCorrectionSupervisorStage());
            newOrganizationHolder.setStatus(Constants.STATUS[0]);

            if (newOrganizationHolder.validateForUpdate()) {
                //OrganizationHolder.getLogStatment(oldOrganizationHolder,newOrganizationHolder);
                MasterRepository.getInstance().update(oldOrganizationHolder, newOrganizationHolder);
                viewOrganizationHolder(request, response);
            } else {
                // if the parcel fails to validate show error message
                request.getSession().setAttribute("title", "Validation Error");
                request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                request.getSession().setAttribute("returnTitle", "Go back to holder list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_MCSUPERVISOR);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }

        } catch (NumberFormatException | ServletException | IOException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a parcel 
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deleteParcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Parcel parcel = MasterRepository.getInstance().getParcel(request.getSession().getAttribute("upi").toString(), CommonStorage.getMajorCorrectionSupervisorStage());
        request.setAttribute("currentParcel", parcel);
        // if the parcel does exist in database
        if (request.getAttribute("currentParcel") == null) {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the parcel your are trying to delete dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
            return;
        }
         // Delete all disputes of the parcel, if any
        if (parcel.hasDispute()) {
            ArrayList<Dispute> allDisputes = parcel.getDisputes();
            allDisputes.stream().forEach((allDispute) -> {
                allDispute.remove(request);
            });
        }
        // Delete all holders of the parcel, if any
        ArrayList<IndividualHolder> allHolders = parcel.getIndividualHolders();
        if (allHolders != null) {
            allHolders.stream().forEach((allHolder) -> {
                allHolder.remove(request);
            });
        }
        ArrayList<PersonWithInterest> allPWI = parcel.getPersonsWithInterest();
        if (allPWI != null) {
            allPWI.stream().forEach((allPWI1) -> {
                allPWI1.remove(request);
            });
        }
        ArrayList<Guardian> allGuardians = parcel.getGuardians();
        if (allGuardians != null) {
            allGuardians.stream().forEach((allGuardian) -> {
                allGuardian.remove(request);
            });
        }
        if (parcel.getOrganizationHolder() != null) {
            parcel.getOrganizationHolder().remove(request);
        }

        // Delete the parcel
        parcel.remove(request);

        // remove the session and request attributes
        // do not remove the kebele since the operator is probably going to 
        // work on a parcel within the same kebele
        request.getSession().setAttribute("upi", null);
        request.getSession().setAttribute("parcelNo", null);
        request.setAttribute("upi", null);
        request.setAttribute("parcelNo", null);
        request.setAttribute("currentParcel", null);

        // Forward to the welcome page
        welcomeForm(request, response);
    }

}
