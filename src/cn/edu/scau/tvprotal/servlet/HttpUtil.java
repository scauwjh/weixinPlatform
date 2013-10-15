package cn.edu.scau.tvprotal.servlet;

/* util */
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
/* http */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
/* log */
import org.apache.log4j.Logger;

public class HttpUtil
{
  // PARAM NAMES & VALUES
    /**
     * 以下常数用于统一Http请求的参数名字
     */
	// Common
    public static final String FLAG = "flag";
    public static final String FORMAT = "format";
    public static final String DECIMAL = "decimal";
    public static final String BYTES = "bytes";
    public static final String HEX = "hex";
    // User
    public static final String UID = "uid";
    public static final String MAIL = "mail";
    public static final String UNAME = "uname";
    public static final String DIGESTED_PASS = "dig_pass";
    public static final String ENCRYPTED_PASS = "enc_pass";
    public static final String SID = "sid";
    public static final String M = "m";
    public static final String E = "e";
	public static final String ENABLE = "enable";
	public static final String FORBID = "forbid";
    // Book
    public static final String BID = "bid";
    public static final String ISBN = "isbn";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String BORROW = "borrow";
    public static final String SALE = "sale";
    public static final String RENT = "rent";
    // Search
    public static final String DISTANCE = "dist";
    public static final String START = "start";
    public static final String LIMIT = "limit";
    public static final String COUNT = "count";
    // Message
    public static final String MSID = "msid";
    public static final String LATEST = "latest";
    public static final String PEERS = "peers";
    public static final String T = "t";
    //public static final String M = "m";
    public static final String S = "s";
    public static final String BEFORE = "before";
    public static final String AFTER = "after";
    // Notify
    //public static final String T = "t";
    public static final String V = "v";
    public static final String AT = "at";

    /**
     * 从 HTTP 请求中检出相应参数值的简便封装
     *
     * 没有该命名的参数时返回null
     * (!) 不能用于在 Session 中检出 Object, 会返回它们的 toString()
     * 优先顺序为 Http请求参数 > Session > Cookie
     * @param req Http请求
     * @param name 参数的名字
     * @return String 参数的值
     */
    public static String getParam(HttpServletRequest req, String name)
    {
        // Server-side Attribute
        Object ra = req.getAttribute(name);
        if (ra != null)
            return(ra.toString());

        // Http Parameter
        String value = null;
        if ((value = req.getParameter(name)) != null)
            return(value);

        // Session
        HttpSession s = req.getSession();
        Object sa = s.getAttribute(name);
        if (sa != null)
            return(sa.toString());

        // Cookies
        Cookie[] carr = req.getCookies();
        if (carr != null)
            for (int i=0; i<carr.length; i++)
                if (carr[i].getName().equals(name))
                    return(carr[i].getValue());

        return(null);
    }

  // DIRECT-CALL
    /**
     * 同 getParam() 但是转换为 Integer 返回
     *
     * 对于无法转换的值返回null, 没有对应的值返回null
     * 同理因为有一次转换所以效率略低
     * @param req Http请求
     * @param name 参数的名字
     * @return Integer 参数的值
     */
    public static Integer getIntParam(HttpServletRequest req, String name)
    {
        try
        {
            String v = getParam(req, name);
            if (v != null)
                return(Integer.valueOf(v));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger("librarica.servlet")
                .error(ex.toString());
        }
        return(null);
    }

    /**
     * 同 getParam() 但是转换为 Double 返回
     *
     * 对于无法转换的值返回null, 没有对应的值返回null
     * 同理因为有一次转换所以效率略低
     * @param req Http请求
     * @param name 参数的名字
     * @return Double 参数的值
     */
    public static Double getDoubleParam(HttpServletRequest req, String name)
    {
        try
        {
            String v = getParam(req, name);
            if (v != null)
                return(Double.valueOf(v));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger("librarica.servlet")
                .error(ex.toString());
        }
        return(null);
    }

    /**
     * 同 getParam() 但是转换为 Byte 返回
     *
     * 对于无法转换的值返回null, 没有对应的值返回null
     * 同理因为有一次转换所以效率略低
     * @param req Http请求
     * @param name 参数的名字
     * @return Byte 参数的值
     */
    public static Byte getByteParam(HttpServletRequest req, String name)
    {
        try
        {
            String v = getParam(req, name);
            if (v != null)
                return(Byte.valueOf(v));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger("librarica.servlet")
                .error(ex.toString());
        }
        return(null);
    }

    public static Long getLongParam(HttpServletRequest req, String name)
    {
        try
        {
            String v = getParam(req, name);
            if (v != null)
                return(Long.valueOf(v));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger("librarica.servlet")
                .error(ex.toString());
        }
        return(null);
    }

    public static List<Long> getLongListParam(HttpServletRequest req, String name)
    {
        try
        {
            String v = getParam(req, name);
            if (v == null)
                return(null);

            StringTokenizer st = new StringTokenizer(v, ",");
            List<Long> l = new ArrayList<Long>(st.countTokens());

            while (st.hasMoreTokens())
                l.add(Long.valueOf(st.nextToken()));

            return(l);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger("librarica.servlet")
                .error(ex.toString());
        }
        return(null);
    }
}
