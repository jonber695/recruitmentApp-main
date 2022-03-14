package iv1201.group7.recruitmentApp.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) // Default scope, this annotation is not required.
public class DbUtil {
    private static final Pattern delimP = Pattern.compile("^\\s*(--)?\\s*delimiter\\s*=?\\s*([^\\s]+)+\\s*.*$",
            Pattern.CASE_INSENSITIVE);
    private static final String DEFAULT_DELIMITER = ";";
    private static DataSource db;
    private boolean autoCommit;
    private String delimiter = DEFAULT_DELIMITER;
    private boolean fullLineDelimiter = false;
    @Autowired
    private Environment env;

    @PostConstruct
    public void createPool() {
        autoCommit = false;
        if (db == null) {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            config.setJdbcUrl(env.getProperty("spring.datasource.url"));
            config.setUsername(env.getProperty("spring.datasource.username"));
            config.setPassword(env.getProperty("spring.datasource.password"));
            db = new HikariDataSource(config);
        }
    }

    /**
     * Drops all tables and creates a new, empty, bank database, by executing the
     * script specified in the property <code>se.kth.iv1201.db.emptydb</code>.
     * Database driver, url, username and password are read from the following
     * properties: <code>spring.datasource.driver-class-name</code>,
     * <code>spring.datasource.url</code>, <code>spring.datasource.username</code>,
     * <code>spring.datasource.password</code>
     */
    public void emptyDb() throws IOException {
        runScript(new BufferedReader(new FileReader(env.getProperty("iv1201.db.emptydb"))));
    }

    private void setDelimiter(String delimiter, boolean fullLineDelimiter) {
        this.delimiter = delimiter;
        this.fullLineDelimiter = fullLineDelimiter;
    }

    private void runScript(Reader reader) {
        try (Connection connection = db.getConnection()) {
            boolean originalAutoCommit = connection.getAutoCommit();
            try {
                if (originalAutoCommit != this.autoCommit) {
                    connection.setAutoCommit(this.autoCommit);
                }
                runScript(connection, reader);
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runScript(Connection conn, Reader reader) throws SQLException {
        StringBuffer command = null;
        try {
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuffer();
                }
                String trimmedLine = line.trim();
                final Matcher delimMatch = delimP.matcher(trimmedLine);
                if (trimmedLine.length() < 1 || trimmedLine.startsWith("//")) {
                    // Do nothing
                } else if (delimMatch.matches()) {
                    setDelimiter(delimMatch.group(2), false);
                } else if (trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (trimmedLine.length() < 1 || trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (!fullLineDelimiter && trimmedLine.endsWith(getDelimiter())
                        || fullLineDelimiter && trimmedLine.equals(getDelimiter())) {
                    command.append(line.substring(0, line.lastIndexOf(getDelimiter())));
                    command.append(" ");
                    this.execCommand(conn, command, lineReader);
                    command = null;
                } else {
                    command.append(line);
                    command.append("\n");
                }
            }
            if (command != null) {
                this.execCommand(conn, command, lineReader);
            }
            if (!autoCommit) {
                conn.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.rollback();
        }
    }

    private void execCommand(Connection conn, StringBuffer command, LineNumberReader lineReader) throws SQLException {
        Statement statement = conn.createStatement();

        // boolean hasResults = false;
        try {
            // hasResults = statement.execute(command.toString());
            statement.execute(command.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (autoCommit && !conn.getAutoCommit()) {
            conn.commit();
        }

        // ResultSet rs = statement.getResultSet();
        // if (hasResults && rs != null) {
        // ResultSetMetaData md = rs.getMetaData();
        // int cols = md.getColumnCount();
        // for (int i = 1; i <= cols; i++) {
        // String name = md.getColumnLabel(i);
        // }
        // while (rs.next()) {
        // for (int i = 1; i <= cols; i++) {
        // String value = rs.getString(i);
        // }
        // }
        // }

        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDelimiter() {
        return delimiter;
    }
}