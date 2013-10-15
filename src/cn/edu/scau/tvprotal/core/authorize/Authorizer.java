package cn.edu.scau.tvprotal.core.authorize;

/* dao */
import cn.edu.scau.tvprotal.dao.DaoUtil;
import cn.edu.scau.tvprotal.dao.authorize.*;
/* core */
//import cn.edu.scau.tvprotal.core.authorize.*;
/* misc */
import java.util.Random;
import java.nio.ByteBuffer;
import java.io.UnsupportedEncodingException;

public class Authorizer
{
  // AUXILIARY
    public static byte[] digestPass(String plaintextPass, byte[] salt)
    {
        try
        {
            byte[] pass = plaintextPass.getBytes("UTF-8");
            // concat
            ByteBuffer buf = ByteBuffer.allocate(pass.length + salt.length);
            buf.put(pass)
                .put(salt);

            byte[] digested = CryptoUtil.MD5Digest(buf.array());

            return(digested);
        }
        catch (UnsupportedEncodingException ex)
        {
            // never occur
            ex.printStackTrace();
            return(null);
        }
    }

  // FUNCTIONALITY
    public static String setPassword(String name, String plaintextPass)
    {
        try
        {
            User u = UserMgr.forName(name);
            if (u == null)
                return("!notfound");

            Random r = new Random();

            byte[] salt = ByteBuffer.allocate(8).putLong(r.nextLong()).array();
            u.setSalt(salt);

            u.setPass(digestPass(plaintextPass, salt));

            DaoUtil.update(u);

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }

    /** 清空密码
     * <br />
     * 是指清空pass域使之完全无法登陆.
     */
    public static String clearPassword(String name)
    {
        try
        {
            User u = UserMgr.forName(name);
            if (u == null)
                return("!notfound");

            u.setPass(null);

            DaoUtil.update(u);

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }

    public static String verifyPassword(String name, byte[] rawPass)
    {
        try
        {
            User u = UserMgr.forName(name);
            if (u == null)
                return("!notfound");
            if (u.getPass() == null)
                return("!forbid");

            byte[] salt = u.getSalt();
            ByteBuffer buf = ByteBuffer.allocate(rawPass.length + salt.length);
            buf.put(rawPass)
                .put(salt);
            byte[] digested = CryptoUtil.MD5Digest(buf.array());
            byte[] saved = u.getPass();

            if (digested.length != saved.length)
                return("!incorrect");
            for (int i=0; i<saved.length; i++)
                if (digested[i] != saved[i])
                    return("!incorrect");

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }

    public static String verifyPassword(String name, String plaintextPass)
    {
        try
        {
            User u = UserMgr.forName(name);
            if (u == null)
                return("!notfound");
            if (u.getPass() == null)
                return("!forbid");

            byte[] digested = digestPass(name, u.getSalt());
            byte[] saved = u.getPass();

            if (digested.length != saved.length)
                return("!incorrect");
            for (int i=0; i<saved.length; i++)
                if (digested[i] != saved[i])
                    return("!incorrect");

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }

    public static String initRoot()
    {
        try
        {
            Role r = RoleMgr.forName("root");
            if (r == null)
            {
                r = new Role("root", "root user group of system");
                DaoUtil.save(r);
            }

            User u = UserMgr.forName("root");
            if (u == null)
            {
                u = new User("root", r, null);
                DaoUtil.save(u);
            }

            setPassword("root", "root");

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }
}
