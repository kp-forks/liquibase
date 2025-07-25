package liquibase.database;

import liquibase.exception.DatabaseException;
import liquibase.servicelocator.PrioritizedService;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 * A liquibase abstraction over the normal Connection that is available in
 * java.sql. This interface allows wrappers and aspects over the basic 
 * connection.
 * 
 */
public interface DatabaseConnection extends PrioritizedService, AutoCloseable {

    void open(String url, Driver driverObject, Properties driverProperties)
            throws DatabaseException;

    /**
     * Default implementation for compatibility with a URL.
     * Method is used when a Connection is opened based on an identified driverObject from url.
     * Can be overridden in DatabaseConnection implementations with a higher priority to check against a given url.
     *
     * @param url the url connection string
     * @return true if URL is supported
     */
    default boolean supports(String url) {
        return true;
    }

    @Override
    void close() throws DatabaseException;

    void commit() throws DatabaseException;

    boolean getAutoCommit() throws DatabaseException;

    String getCatalog() throws DatabaseException;

    String nativeSQL(String sql) throws DatabaseException;

    void rollback() throws DatabaseException;

    void setAutoCommit(boolean autoCommit) throws DatabaseException;

    String getDatabaseProductName() throws DatabaseException;

    String getDatabaseProductVersion() throws DatabaseException;

    int getDatabaseMajorVersion() throws DatabaseException;

    int getDatabaseMinorVersion() throws DatabaseException;

    String getURL();

    /**
     *
     * Default implementation for the URL that is displayed
     *
     * @return  String
     */
    default String getVisibleUrl() {
        return getURL();
    }

    String getConnectionUserName();

    boolean isClosed() throws DatabaseException;

    void attached(Database database);

    default Connection getUnderlyingConnection() {
        return null;
    }
}
