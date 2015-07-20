package org.lift.massreg.controller;

import java.io.*;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.HoldingHolderDTO;
import org.lift.massreg.dto.HoldingParcelPublicDisplayDTO;
import org.lift.massreg.dto.HoldingPublicDisplayDTO;
import org.lift.massreg.entity.User;
import org.lift.massreg.util.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class Administrator {

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
        // setup request attributes
        ArrayList<User> users = MasterRepository.getInstance().getAllUsers();
        request.setAttribute("users", users);
        request.setAttribute("page", IOC.getPage(Constants.INDEX_WELCOME_ADMINISTRATOR));
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
        ArrayList<User> users = MasterRepository.getInstance().getAllUsers();
        request.setAttribute("users", users);
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_WELCOME_ADMINISTRATOR));
        rd.forward(request, response);
    }

    /**
     * Sends a reload order for the browser
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void reload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(IOC.getPage(Constants.INDEX_RELOAD));
        rd.forward(request, response);
    }

    /**
     * Handlers request to save a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!MasterRepository.getInstance().userExists(request.getParameter("username"))) {
            User user = new User();
            try {
                user.setFathersName(request.getParameter("fathersName"));
                user.setFirstName(request.getParameter("firstName"));
                user.setGrandFathersName(request.getParameter("grandfathersName"));
                user.setPhoneNumber(request.getParameter("phoneno"));
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
                String role = request.getParameter("role");
                switch (role.toUpperCase()) {
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
                    case "PDC":
                        user.setRole(Constants.ROLE.POSTPDCOORDINATOR);
                        break;
                    case "WC":
                        user.setRole(Constants.ROLE.WOREDA_COORDINATOR);
                        break;
                    case "MCO":
                        user.setRole(Constants.ROLE.MINOR_CORRECTION_OFFICER);
                        break;
                    case "CFEO":
                        user.setRole(Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR);
                        break;
                    case "CSEO":
                        user.setRole(Constants.ROLE.CORRECTION_SECOND_ENTRY_OPERATOR);
                        break;
                    case "CSUPER":
                        user.setRole(Constants.ROLE.CORRECTION_SUPERVISOR);
                        break;
                }

                if (user.validateForSave()) {
                    user.save(request);
                    welcomeForm(request, response);
                } else {
                    request.getSession().setAttribute("title", "Validation Error");
                    request.getSession().setAttribute("message", "Sorry, there was a validation error ");
                    request.getSession().setAttribute("returnTitle", "Go back to holder list");
                    request.getSession().setAttribute("returnAction", Constants.ACTION_VIEW_HOLDER_FEO);
                    RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                    rd.forward(request, response);
                }

            } catch (ServletException | IOException ex) {
                ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
                request.getSession().setAttribute("title", "Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend");
                request.getSession().setAttribute("returnTitle", "Go back to user list");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
                RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
                rd.forward(request, response);
            }
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "The username already existes");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for viewing a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void viewUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = MasterRepository.getInstance().getUser(Long.parseLong(request.getParameter("userId")));
        request.setAttribute("currentUser", user);
        // if the parcel does exist in database
        if (request.getAttribute("currentUser") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_VIEW_USER_ADMINISTRATOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the user your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request for getting the edit user form by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = MasterRepository.getInstance().getUser(Long.parseLong(request.getParameter("userId")));
        request.setAttribute("currentUser", user);
        if (request.getAttribute("currentUser") != null) {
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_EDIT_USER_ADMINISTRATOR));
            rd.forward(request, response);
        } else {
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the user your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to update user information by the supervisor
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateUser(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        User oldUser = MasterRepository.getInstance().getUser(Long.parseLong(request.getParameter("userId")));
        try {
            User newUser = new User();
            newUser.setFathersName(request.getParameter("fathersName"));
            newUser.setFirstName(request.getParameter("firstName"));
            newUser.setGrandFathersName(request.getParameter("grandfathersName"));
            newUser.setUsername(request.getParameter("username"));
            newUser.setPhoneNumber(request.getParameter("phoneno"));
            newUser.setFathersName(request.getParameter("fathersName"));
            String role = request.getParameter("role");
            switch (role.toUpperCase()) {
                case "FEO":
                    newUser.setRole(Constants.ROLE.FIRST_ENTRY_OPERATOR);
                    break;
                case "SEO":
                    newUser.setRole(Constants.ROLE.SECOND_ENTRY_OPERATOR);
                    break;
                case "SUPERVISOR":
                    newUser.setRole(Constants.ROLE.SUPERVISOR);
                    break;
                case "ADMINISTRATOR":
                    newUser.setRole(Constants.ROLE.ADMINISTRATOR);
                    break;
                case "PDC":
                    newUser.setRole(Constants.ROLE.POSTPDCOORDINATOR);
                    break;
                case "WC":
                    newUser.setRole(Constants.ROLE.WOREDA_COORDINATOR);
                    break;
                case "MCO":
                    newUser.setRole(Constants.ROLE.MINOR_CORRECTION_OFFICER);
                    break;
                case "CFEO":
                    newUser.setRole(Constants.ROLE.CORRECTION_FIRST_ENTRY_OPERATOR);
                    break;
                case "CSEO":
                    newUser.setRole(Constants.ROLE.CORRECTION_SECOND_ENTRY_OPERATOR);
                    break;
                case "CSUPER":
                    newUser.setRole(Constants.ROLE.CORRECTION_SUPERVISOR);
                    break;
            }
            if (MasterRepository.getInstance().update(oldUser, newUser)) {
                welcomeForm(request, response);
            } else {
                request.getSession().setAttribute("title", "Inrernal Error");
                request.getSession().setAttribute("message", "Sorry, some internal error has happend/");
                request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
                request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
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
     * Handlers request to update settings by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updateSettings(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String language = request.getParameter("language");
        long woreda = Long.parseLong(request.getParameter("woreda"));
        CommonStorage.setLanguage(language);
        CommonStorage.setWoreda(woreda, MasterRepository.getInstance().getWoredaName(woreda), MasterRepository.getInstance().getWoredaIdForUPI(woreda));
        reload(request, response);
    }

    /**
     * Handlers request to update password by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void updatePassword(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        long userId = Long.parseLong(request.getParameter("userId"));
        if (MasterRepository.getInstance().userExists(MasterRepository.getInstance().getUser(userId).getUsername())) {
            MasterRepository.getInstance().changePassword(userId, newPassword);
            welcomeForm(request, response);
        } else { // User Does not exist
            request.getSession().setAttribute("title", "Error");
            request.getSession().setAttribute("message", "Sorry, the user your are looking for dose not exist in the database");
            request.getSession().setAttribute("returnTitle", "Go back to the welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to delete a user by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected static void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (User.delete(request, Long.parseLong(request.getParameter("userId")))) {
            welcomeForm(request, response);
        } else {
            // if the parcel fails to validate show error message
            request.getSession().setAttribute("title", "Delete Error");
            request.getSession().setAttribute("message", "Sorry, there was a problem deleting the user");
            request.getSession().setAttribute("returnTitle", "Go back to user list");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    /**
     * Handlers request to generate report by the administrator
     *
     * @param request request object passed from the main controller
     * @param response response object passed from the main controller
     */
    public static void exportReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String file = request.getParameter("file");
        File f = new File(file);
        try {
            ReportUtil.sign(file);
            FileInputStream in = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            in.read(data);
            in.close();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + file);
            response.setHeader("Cache-Control", "no cache");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }
    }

    protected static void timeBoundReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date fromDate = Date.valueOf(request.getParameter("fromDate"));
        Date toDate = Date.valueOf(request.getParameter("toDate"));
        //String fileName = CommonStorage.getCurrentWoredaName() + " Timebound report " + (new Date(Instant.now().toEpochMilli()).toString()) + ".xlsx";
        String fileName = Instant.now().toEpochMilli() + ".xlsx";

        if (fromDate == null || toDate == null) {
            response.getOutputStream().println("Please specfiey valide report dates");
        } else {
            ReportUtil.generateTimeBoundReport(fromDate, toDate, fileName);
            request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + fileName);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DOWNLOAD_REPORT));
            rd.forward(request, response);
        }
    }

    public static void kebeleReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String kebele = request.getParameter("kebele");
        if (kebele.isEmpty()) {
            response.getOutputStream().println("Please specfiey kebele for the report ");
        } else {
            long kebeleId = Long.parseLong(request.getParameter("kebele"));
            String kebeleName = MasterRepository.getInstance().getKebeleName(kebeleId, "english");
            String fileName = "Kebele Report " + kebeleName + " " + (new Date(Instant.now().toEpochMilli()).toString()) + ".xlsx";
            ReportUtil.generateKebeleReport(kebeleId, kebeleName, fileName);
            request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + fileName);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DOWNLOAD_REPORT));
            rd.forward(request, response);
        }
    }

    public static void publicDisplay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println("1.");
        try {
            String kebele = request.getParameter("kebele");
            if (kebele.isEmpty()) {
                response.getOutputStream().println("Please specfiey kebele for the report ");
                return;
            }
            long kebeleId = Long.parseLong(request.getParameter("kebele"));
            MasterRepository.getInstance().updateParcelArea(kebeleId);
            String page;
            if (Boolean.parseBoolean(request.getParameter("holdingBased"))) {
                String fileName = kebeleId + ".xlsx";
                ArrayList<HoldingPublicDisplayDTO> a = MasterRepository.getInstance().getPublicDisplayReportI2(kebeleId);
                System.gc();
                ArrayList<HoldingPublicDisplayDTO> b = MasterRepository.getInstance().getPublicDisplayReportO2(kebeleId);
                pd(fileName, a, b);
                request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + fileName);
                page = IOC.getPage(Constants.INDEX_DOWNLOAD_REPORT);
            } else {
                MasterRepository.getInstance().updateParcelArea(kebeleId);
                request.setAttribute("holdersListI", MasterRepository.getInstance().getPublicDisplayReportI(kebeleId));
                request.setAttribute("holdersListO", MasterRepository.getInstance().getPublicDisplayReportO(kebeleId));
                page = IOC.getPage(Constants.INDEX_PUBLIC_DISPLAY_HOLDER);
            }
            System.err.println("9....");
            request.setAttribute("missingParcels", MasterRepository.getInstance().getParcelsWithoutTextualData(kebeleId));
            request.setAttribute("regionName", MasterRepository.getInstance().getRegionName(CommonStorage.getCurrentWoredaId(), CommonStorage.getCurrentLanguage()));
            request.setAttribute("zoneName", MasterRepository.getInstance().getZoneName(CommonStorage.getCurrentWoredaId(), CommonStorage.getCurrentLanguage()));
            request.setAttribute("woredaName", MasterRepository.getInstance().getWoredaName(CommonStorage.getCurrentWoredaId(), CommonStorage.getCurrentLanguage()));
            request.setAttribute("kebeleName", MasterRepository.getInstance().getKebeleName(kebeleId, CommonStorage.getCurrentLanguage()));

            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(page);
            rd.forward(request, response);
        } catch (IOException | NumberFormatException | ServletException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }

    }

    public static void pd(String fileName, ArrayList<HoldingPublicDisplayDTO> indvididual, ArrayList<HoldingPublicDisplayDTO> organizational) throws IOException {
        Workbook wb = new XSSFWorkbook();
        try {
            int holdingNoCol = 0;
            int namesCol = 1;
            int sexCol = 2;
            int parcelNoCol = 3;
            int areaCol = 4;
            int disputeCol = 5;
            int guardianCol = 6;
            int incompleteCol = 7;

            //CreationHelper createHelper = wb.getCreationHelper();
            Font hSSFFont = wb.createFont();
            hSSFFont.setFontName("Abyssinica SIL");
            hSSFFont.setFontHeightInPoints((short) 11);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setWrapText(true);
            cellStyle.setFont(hSSFFont);

            CellStyle boarderStyle = wb.createCellStyle();
            boarderStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);

            CellStyle boldCellStyle = wb.createCellStyle();
            boldCellStyle.cloneStyleFrom(cellStyle);
            hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            hSSFFont.setBold(true);
            cellStyle.setFont(hSSFFont);
            FileOutputStream fileOut = new FileOutputStream(fileName);

            Sheet individualSheet = wb.createSheet("Individual Holdings");
            Row headerRow = individualSheet.createRow(0);
            headerRow.createCell(holdingNoCol).setCellValue("Holding Number");
            headerRow.createCell(namesCol).setCellValue("Name");
            headerRow.createCell(sexCol).setCellValue("Sex");
            headerRow.createCell(parcelNoCol).setCellValue("Parcel No");
            headerRow.createCell(areaCol).setCellValue("Area");
            headerRow.createCell(guardianCol).setCellValue("Guardian");
            headerRow.createCell(incompleteCol).setCellValue("Incomplete");

            int rowCount = 1;
            for (HoldingPublicDisplayDTO item : indvididual) {
                int x = 0;
                Row[] rows = new Row[Math.max(item.getHolders().size(), item.getParcels().size())];
                for (int j = 0; j < rows.length; j++) {
                    rows[j] = individualSheet.createRow(rowCount + x++);
                }
                rows[0].createCell(holdingNoCol).setCellValue(item.getHoldingNumber());
                CellStyle cs = wb.createCellStyle();
                cs.setBorderRight(HSSFCellStyle.BORDER_THICK);

                int i = 0;
                for (; i < item.getHolders().size(); i++) {
                    HoldingHolderDTO holder = item.getHolders().get(i);
                    rows[i].createCell(namesCol).setCellValue(holder.getName());
                    rows[i].createCell(sexCol).setCellValue(holder.getSexText());
                }
                for (; i < item.getHolders().size() - item.getParcels().size(); i++) {
                    rows[i].createCell(parcelNoCol).setCellStyle(cs);
                }
                int j = 0;

                for (; j < item.getParcels().size(); j++) {
                    HoldingParcelPublicDisplayDTO parcel = item.getParcels().get(j);
                    rows[j].createCell(parcelNoCol).setCellValue(String.format("%05d", parcel.getParcelId()));
                    rows[j].createCell(disputeCol).setCellValue(parcel.hasDisputeText());
                    rows[j].createCell(areaCol).setCellValue(parcel.getArea());
                    rows[j].createCell(guardianCol).setCellValue(parcel.getGuardiansText());
                    Cell c = rows[j].createCell(incompleteCol);
                    c.setCellValue(parcel.hasMissingValueText());
                    c.setCellStyle(cs);
                }
                for (; j < item.getParcels().size() - item.getHolders().size(); j++) {
                    rows[j].createCell(parcelNoCol).setCellStyle(cs);
                }

                for (Iterator<Cell> iterator = rows[0].cellIterator(); iterator.hasNext();) {
                    CellStyle topBoarder = wb.createCellStyle();
                    topBoarder.setBorderTop(HSSFCellStyle.BORDER_THICK);
                    iterator.next().setCellStyle(topBoarder);
                }
                ///TODO: Merge those rows under the same holding
                individualSheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + x - 1, 0, 0));
                rowCount += x;

            }
            /*
             Sheet organizationSheet = wb.createSheet("Organization Holdings");
             rowCount = 0;
             for (HoldingPublicDisplayDTO item : organizational) {
             int x = 0;
             Row[] rows = new Row[Math.max(item.getHolders().size(), item.getParcels().size())];
             for (int j = 0; j < rows.length; j++) {
             rows[j] = organizationSheet.createRow(rowCount + x++);
             }
             rows[0].createCell(holdingNoCol).setCellValue(item.getHoldingNumber());
             CellStyle cs = wb.createCellStyle();
             cs.setBorderRight(HSSFCellStyle.BORDER_THICK);
                
             int i = 0;
             for (; i < item.getHolders().size(); i++) {
             HoldingHolderDTO holder = item.getHolders().get(i);
             rows[i].createCell(namesCol).setCellValue(holder.getName());
             //rows[i].createCell(sexCol).setCellValue(holder.getSexText());
             }
             for (; i < item.getHolders().size()-item.getParcels().size(); i++) {
             rows[i].createCell(parcelNoCol).setCellStyle(cs);
             }
             int j = 0;
                
             for (; j < item.getParcels().size(); j++) {
             HoldingParcelPublicDisplayDTO parcel = item.getParcels().get(j);
             rows[j].createCell(parcelNoCol).setCellValue(parcel.getParcelId());
             rows[j].createCell(disputeCol).setCellValue(parcel.hasDisputeText());
             rows[j].createCell(areaCol).setCellValue(parcel.getArea());
             rows[j].createCell(guardianCol).setCellValue(parcel.getGuardiansText());
             Cell c =  rows[j].createCell(incompleteCol);
             c.setCellValue(parcel.hasMissingValueText());
             c.setCellStyle(cs);
             }
             for (; j < item.getParcels().size()-item.getHolders().size(); j++) {
             rows[j].createCell(parcelNoCol).setCellStyle(cs);
             }

             for (Iterator<Cell> iterator = rows[0].cellIterator(); iterator.hasNext();) {
             CellStyle topBoarder = wb.createCellStyle();
             topBoarder.setBorderTop(HSSFCellStyle.BORDER_THICK);
             iterator.next().setCellStyle(topBoarder);
             }
             ///TODO: Merge those rows under the same holding
             //tempSheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+x-1, 0, 0));
             rowCount += x;
                
             }
             */
            wb.write(fileOut);
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
    }

    public static void dailyReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Date date = Date.valueOf(request.getParameter("reportDate"));
        if (date == null) {
            response.getOutputStream().println("Please specfiey a date for the report ");
            response.getOutputStream().close();
        } else {
            request.setAttribute("report", ReportUtil.generateDailyReport(date));
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DAILY_REPORT_ADMINISTRATOR));
            rd.forward(request, response);
        }
    }

    public static void publicDisplay2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String kebele = request.getParameter("kebele");
            if (kebele.isEmpty()) {
                response.getOutputStream().println("Please specfiey kebele for the report ");
                return;
            }
            long kebeleId = Long.parseLong(request.getParameter("kebele"));
            MasterRepository.getInstance().updateParcelArea(kebeleId);
            if (Boolean.parseBoolean(request.getParameter("holdingBased"))) {
                holdingBasedPublicDisplay(request, response, kebeleId);
            } else {
                holderBasedPublicDisplay(request, kebeleId);
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
            request.getSession().setAttribute("title", "Inrernal Error");
            request.getSession().setAttribute("message", "Sorry, some internal error has happend");
            request.getSession().setAttribute("returnTitle", "Go back to Welcome page");
            request.getSession().setAttribute("returnAction", Constants.ACTION_WELCOME_PART);
            RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_MESSAGE));
            rd.forward(request, response);
        }

    }

    public static void holdingBasedPublicDisplay(HttpServletRequest request, HttpServletResponse response, long kebele) throws ServletException, IOException {
        String fileName = kebele + ".xlsx";

        ////////////////////////////////////////////////
        Workbook wb = new XSSFWorkbook();

        int holdingNoCol = 0;
        int namesCol = 1;
        int sexCol = 2;
        int parcelNoCol = 3;
        int areaCol = 4;
        int disputeCol = 5;
        int guardianCol = 6;
        int incompleteCol = 7;

        Font hSSFFont = wb.createFont();
        hSSFFont.setFontName("Abyssinica SIL");
        hSSFFont.setFontHeightInPoints((short) 11);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setFont(hSSFFont);

        CellStyle boarderStyle = wb.createCellStyle();
        boarderStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);

        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        hSSFFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        hSSFFont.setBold(true);
        cellStyle.setFont(hSSFFont);

        CellStyle rightBorderStyle = wb.createCellStyle();
        rightBorderStyle.setBorderRight(HSSFCellStyle.BORDER_THICK);

        CellStyle rowBoarder = wb.createCellStyle();
        rowBoarder.setBorderTop(HSSFCellStyle.BORDER_THICK);
        try {

            FileOutputStream fileOut = new FileOutputStream(fileName);
            Sheet individualSheet = wb.createSheet("Individual Holdings");
            int totalRows = 1;
            // Writeout Header for the excel
            Row headerRow = individualSheet.createRow(0);
            headerRow.createCell(holdingNoCol).setCellValue("Holding Number");
            headerRow.createCell(namesCol).setCellValue("Name");
            headerRow.createCell(sexCol).setCellValue("Sex");
            headerRow.createCell(parcelNoCol).setCellValue("Parcel No");
            headerRow.createCell(areaCol).setCellValue("Area");
            headerRow.createCell(disputeCol).setCellValue("Has Dispute");
            headerRow.createCell(guardianCol).setCellValue("Guardian");
            headerRow.createCell(incompleteCol).setCellValue("Incomplete");

            for (Iterator<Cell> iterator = headerRow.cellIterator(); iterator.hasNext();) {
                iterator.next().setCellStyle(headerStyle);
            }
            ArrayList<String> holdingNumbers = MasterRepository.getInstance().getHoldingNumbers(kebele, (byte) 1);
            for (String holdingNo : holdingNumbers) {
                // Individual Holders
                HoldingPublicDisplayDTO item = MasterRepository.getInstance().getPublicDisplayInfoByHoldingNumberIH(holdingNo);
                if (item == null) {
                    continue;  // Unlikely to happen
                }
                Row[] rows = new Row[Math.max(item.getHolders().size(), item.getParcels().size())];
                for (int j = 0; j < rows.length; j++) {
                    rows[j] = individualSheet.createRow(totalRows + j);
                }

                // Writeout the holding number on the first row, first cell
                rows[0].createCell(holdingNoCol).setCellValue(item.getHoldingNumber());

                // Writeout holder details for this holding
                int i = 0;
                for (; i < item.getHolders().size(); i++) {
                    HoldingHolderDTO holder = item.getHolders().get(i);
                    rows[i].createCell(namesCol).setCellValue(holder.getName());
                    Cell sexCell = rows[i].createCell(sexCol);
                    sexCell.setCellValue(holder.getSexText());
                }

                // If the number of holders is greater than the number of parcels 
                // continue to add cells for styling purpose
//                for (; i < item.getHolders().size() - item.getParcels().size(); i++) {
//                    rows[i].createCell(sexCol).setCellStyle(rightBorderStyle);
//                }
                // Writeout holder details for this holding
                int j = 0;
                for (; j < item.getParcels().size(); j++) {
                    HoldingParcelPublicDisplayDTO parcel = item.getParcels().get(j);
                    rows[j].createCell(parcelNoCol).setCellValue(String.format("%05d", parcel.getParcelId()));
                    rows[j].createCell(disputeCol).setCellValue(parcel.hasDisputeText());
                    rows[j].createCell(areaCol).setCellValue(parcel.getArea());
                    rows[j].createCell(guardianCol).setCellValue(parcel.getGuardiansText());
                    Cell incompleteCell = rows[j].createCell(incompleteCol);
                    incompleteCell.setCellValue(parcel.hasMissingValueText());
                    //incompleteCell.setCellStyle(rightBorderStyle);
                }

                // If the number of parcels is greater than the number of holders 
                // continue to add cells for styling purpose
//                for (; i < item.getParcels().size() - item.getHolders().size(); i++) {
//                    rows[i].createCell(incompleteCol).setCellStyle(rightBorderStyle);
//                }
                // Add border to the Bottom each holding row by iterating over the first row 
                // with in the holding
                for (Iterator<Cell> iterator = rows[0].cellIterator(); iterator.hasNext();) {
                    iterator.next().setCellStyle(rowBoarder);
                }

                ///TODO: Merge those rows under the same holding
                totalRows += rows.length - 1;
            }

            /// TODO: Organizational Holdings Sheet

            /// TODO: Missing parcels Sheet
            /*
             Sheet organizationSheet = wb.createSheet("Organization Holdings");
             rowCount = 0;
             for (HoldingPublicDisplayDTO item : organizational) {
             int x = 0;
             Row[] rows = new Row[Math.max(item.getHolders().size(), item.getParcels().size())];
             for (int j = 0; j < rows.length; j++) {
             rows[j] = organizationSheet.createRow(rowCount + x++);
             }
             rows[0].createCell(holdingNoCol).setCellValue(item.getHoldingNumber());
             CellStyle cs = wb.createCellStyle();
             cs.setBorderRight(HSSFCellStyle.BORDER_THICK);
                
             int i = 0;
             for (; i < item.getHolders().size(); i++) {
             HoldingHolderDTO holder = item.getHolders().get(i);
             rows[i].createCell(namesCol).setCellValue(holder.getName());
             //rows[i].createCell(sexCol).setCellValue(holder.getSexText());
             }
             for (; i < item.getHolders().size()-item.getParcels().size(); i++) {
             rows[i].createCell(parcelNoCol).setCellStyle(cs);
             }
             int j = 0;
                
             for (; j < item.getParcels().size(); j++) {
             HoldingParcelPublicDisplayDTO parcel = item.getParcels().get(j);
             rows[j].createCell(parcelNoCol).setCellValue(parcel.getParcelId());
             rows[j].createCell(disputeCol).setCellValue(parcel.hasDisputeText());
             rows[j].createCell(areaCol).setCellValue(parcel.getArea());
             rows[j].createCell(guardianCol).setCellValue(parcel.getGuardiansText());
             Cell c =  rows[j].createCell(incompleteCol);
             c.setCellValue(parcel.hasMissingValueText());
             c.setCellStyle(cs);
             }
             for (; j < item.getParcels().size()-item.getHolders().size(); j++) {
             rows[j].createCell(parcelNoCol).setCellStyle(cs);
             }

             for (Iterator<Cell> iterator = rows[0].cellIterator(); iterator.hasNext();) {
             CellStyle topBoarder = wb.createCellStyle();
             topBoarder.setBorderTop(HSSFCellStyle.BORDER_THICK);
             iterator.next().setCellStyle(topBoarder);
             }
             ///TODO: Merge those rows under the same holding
             //tempSheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount+x-1, 0, 0));
             rowCount += x;
                
             }
             */
            wb.write(fileOut);
        } catch (Exception ex) {
            ex.printStackTrace(CommonStorage.getLogger().getErrorStream());
        }
        ////////////////////////////////////////////////

        request.setAttribute("reportURL", request.getContextPath() + "/Index?action=" + Constants.ACTION_EXPORT_REPORT_ADMINISTRATOR + "&file=" + fileName);
        RequestDispatcher rd = request.getServletContext().getRequestDispatcher(IOC.getPage(Constants.INDEX_DOWNLOAD_REPORT));
        rd.forward(request, response);

    }

    public static void holderBasedPublicDisplay(HttpServletRequest request, long kebele) {
        // Three Sheets - 
        // Individual holders
        // Organizational holders
        // Missing Parcels - those with no textual data

    }

}
