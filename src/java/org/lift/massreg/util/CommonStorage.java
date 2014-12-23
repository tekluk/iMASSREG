package org.lift.massreg.util;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import javax.servlet.http.HttpServletRequest;
import org.lift.massreg.dao.MasterRepository;
import org.lift.massreg.dto.*;

/**
 * @author Yoseph Berhanu <yoseph@bayeth.com>
 * @version 2.0
 * @since 2.0
 */
public class CommonStorage {

    private static Connection connection = null;
    private static Logger logger = new Logger();

    public static String getCurrentWoredaId(){
        String returnValue = "1/2/3/4";
        ///TODO: set woreda id from glassfish
        return returnValue;
    }

    public static String getCurrentWoredaName(){
        String returnValue = "Meskan";
        ///TODO: set woreda Name from glassfish
        return returnValue;
    }
    public static CurrentUserDTO getCurrentUser(HttpServletRequest request) {
        CurrentUserDTO returnValue = MasterRepository.getInstance().getCurrentUserDTO(request.getRemoteUser());
        return returnValue;
    }

    public static byte getFEStage(){
        return 1;
    }
    public static Logger getLogger() {
        return logger;
    }

    static {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("jdbc/newmassreg");
            connection = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            getLogger().logError(ex.toString());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}