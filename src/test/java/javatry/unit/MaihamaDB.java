package javatry.unit;

import java.io.File;
import java.io.IOException;

import org.dbflute.util.DfResourceUtil;

/**
 * @author jflute
 */
public class MaihamaDB {

    public String getJdbcDriverClassName() {
        return "org.h2.Driver";
    }

    public String getUrl() {
        final File dir = DfResourceUtil.getBuildDir(MaihamaDB.class);
        final String canonicalPath;
        try {
            canonicalPath = dir.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException("dir.getCanonicalPath() threw the exception: dir=" + dir, e);
        }
        final String url = "jdbc:h2:file:" + canonicalPath.replace('\\', '/') + "/maihamadb/maihamadb";
        return url;
    }

    public String getUser() {
        return "sa";
    }

    public String getPassword() {
        return "";
    }
}
