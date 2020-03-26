/*
 * Optimized permission settings for proxy users
 *
 * Author:yangpei16@qq.com
 * Date:2020-03-24
 */
package azkaban.jobExecutor;

import azkaban.database.DataSourceUtils;
import azkaban.utils.Props;
import azkaban.Constants;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.List;

/**
 * A job that runs a simple unix command
 */
public class JobExtend  {

    private static final Logger logger = Logger.getLogger(ProcessJob.class);

    /**
     * Loads the Azkaban property file from the AZKABAN_HOME conf directory
     *
     * @return Props instance
     */

    public Props loadConfigurationFromAzkabanHome() {

        final File confPath = new File("./", Constants.DEFAULT_CONF_PATH);
        if (!confPath.exists() || !confPath.isDirectory() || !confPath.canRead()) {
            logger.error( "does not contain a readable conf directory.");
            return null;
        }

        final File azkabanPrivatePropsFile = new File(confPath, Constants.AZKABAN_PRIVATE_PROPERTIES_FILE);
        final File azkabanPropsFile = new File(confPath, Constants.AZKABAN_PROPERTIES_FILE);

        Props props = null;
        try {
            // This is purely optional
            if (azkabanPrivatePropsFile.exists() && azkabanPrivatePropsFile.isFile()) {
                logger.info("Loading azkaban private properties file");
                props = new Props(null, azkabanPrivatePropsFile);
            }

            if (azkabanPropsFile.exists() && azkabanPropsFile.isFile()) {
                logger.info("Loading azkaban properties file");
                props = new Props(props, azkabanPropsFile);
            }
        } catch (final FileNotFoundException e) {
            logger.error("File not found. Could not load azkaban config file", e);
        } catch (final IOException e) {
            logger.error("File found, but error reading. Could not load azkaban config file", e);
        }
        return props;
    }

    /**
     * vaild proxy user from mysql
     * @param azkProps
     * @param submitUserName
     * @param proxyUserName
     * @return
     */
    public boolean validProxyUserFromDB(Props azkProps,String submitUserName,String proxyUserName)  {

        final DataSource datasource = DataSourceUtils.getDataSource(azkProps);
        final QueryRunner runner = new QueryRunner(datasource);
        final String sql = "select proxy_user from proxy_users where submit_user = '" +submitUserName+"';";
        List<String> proxyUsers = null;

        try {
            proxyUsers  = (List<String>)runner.query(sql, new ColumnListHandler("proxy_user"));
        } catch (final SQLException e) {
            logger.error(submitUserName + ": valid proxy users from db error", e);
        }

	if ( proxyUsers.size() == 0 ){
            return false;
        }else {
            return proxyUsers.contains(proxyUserName);
        }
    }

}
