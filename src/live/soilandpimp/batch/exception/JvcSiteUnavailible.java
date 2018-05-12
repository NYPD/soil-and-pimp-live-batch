package live.soilandpimp.batch.exception;

/**
 * Exception to be thrown whenever an attempt to fetch data from the JVC Soil and Pimp site results
 * in a non 200 staus code.
 * 
 * @author NYPD
 *
 */
public class JvcSiteUnavailible extends Exception {

    private static final long serialVersionUID = -848354569620943134L;
    private int webResponseCode;

    public JvcSiteUnavailible(int responsecode) {
        super();
        webResponseCode = responsecode;
    }

    public int getWebResponseCode() {
        return webResponseCode;
    }

}
