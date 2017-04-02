package javatry.unit;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbflute.utflute.core.PlainTestCase;

/**
 * @author jflute
 */
public abstract class UnitryTestCase extends PlainTestCase {

    protected Connection getConnection() {
        final MaihamaDB provider = new MaihamaDB();
        final String driverName = provider.getJdbcDriverClassName();
        try {
            DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());
        } catch (ClassNotFoundException e) {
            handleDriverException(driverName, e);
        } catch (SQLException e) {
            handleDriverException(driverName, e);
        } catch (InstantiationException e) {
            handleDriverException(driverName, e);
        } catch (IllegalAccessException e) {
            handleDriverException(driverName, e);
        }
        final String url = provider.getUrl();
        Connection conn = null;
        try {
            log("DriverManager.getConnection(url, usr, pwd): url=" + url);
            String user = provider.getUser();
            String password = provider.getPassword();
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            String msg = "DriverManager.getConnection(url, usr, pwd) threw the exception:";
            msg = msg + " url=" + url;
            throw new RuntimeException(msg, e);
        }
        return conn;
    }

    private void handleDriverException(String driverName, Throwable e) {
        throw new RuntimeException("Class.forName(driverName) threw the exception: driverName=" + driverName, e);
    }
}
