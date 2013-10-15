package cn.edu.scau.tvprotal.servlet.authorize;

/* io */
import java.io.IOException;
import java.io.PrintWriter;

//import java.io.OutputStream;
/* net&servlet */
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

/* galin-FX */
import cn.edu.scau.tvprotal.debug.Dump;
import cn.edu.scau.tvprotal.dao.DaoUtil;
import cn.edu.scau.tvprotal.servlet.HttpUtil;
import cn.edu.scau.tvprotal.servlet.MissingParameterException;

/* json */
import com.alibaba.fastjson.*;

/* log */
import org.apache.log4j.Logger;

/* tvprotal */
import cn.edu.scau.tvprotal.dao.authorize.*;
import cn.edu.scau.tvprotal.core.authorize.UserMgr;
import cn.edu.scau.tvprotal.core.authorize.Authorizer;
import cn.edu.scau.tvprotal.core.authorize.CryptoUtil;

/* crypto */
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

/**
 * 管理员后台登入接口
 * <br />
 * 该接口接受用户名和 RSA 加密的密码(密钥来自<code>session.getAttrbute("authorize.key")</code>)
 * 并验证登录行为, 如成功, 将用户发往角色相应地址. 同时将用户信息载入到服务器session,
 * 通过session.getAttribute("authorize.user.*")提供</code>
 * <pre style="font-size:12px">

   <strong>请求</strong>
   GET/POST /cgi/authorize/adminLogin

   <strong>参数</strong>
   name:String, 表示用户名
   pass:HexString, RSA加密的密码

   <strong>响应</strong>
   application/json 对象:
   flag:String, 成功时返回success

   <strong>例外</strong>
   参数不全时返回 Bad Request(400): {}
   无对应用户名时返回 Bad Request(400): {"flag":"!notfound"}
   用户不可登录返回 Forbidden(403): ("flag":"!forbid")
   密码不正确/不能正确解密返回 Bad Request(400): {"flag":"!incorrect"}
   业务逻辑错误返回 Internal Server Error(500): {"flag":"!error"}

   <strong>样例</strong>暂无
 * </pre>
 *
 */
@SuppressWarnings("serial")
@WebServlet(
    name="AdminLogin",
    urlPatterns={"/cgi/authorize/adminLogin"}
)
public class AdminLogin extends HttpServlet
{
    private static String NAME = "name";
    private static String PASS = "pass";
    private static String FLAG = "flag";
    private static String HOME = "home";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        this.doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        // Encoding Configuration
        // encode of req effects post method only
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        // Dequote if pend to use session
        HttpSession session = req.getSession();

        // Dequote if pend to write binary
        //resp.setContentType("???MIME");
        //OutputStream out = resp.getOutputStream();

        // Dequote if pend to write chars
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        // output buffer prepare
        JSONObject json = new JSONObject();

        try
        {
            // Fetch parameters need no DB-access
            // throw NullPointerException if required pararmeter is missing.
            String name = HttpUtil.getParam(req, NAME);
            if (name == null)
                throw(new MissingParameterException("Missing requeired parameter:" + NAME));

            String pass = HttpUtil.getParam(req, PASS);
            if (pass == null)
                throw(new MissingParameterException("Missing requested parameter:" + PASS));
            byte[] encrypted = CryptoUtil.hexToBytes(pass);
            System.err.println("encrypted");
            Dump.dump(encrypted);

            KeyPair kp = (KeyPair)session.getAttribute("authorize.key");
            RSAPrivateKey prikey = (RSAPrivateKey)kp.getPrivate();
            byte[] decrypted = CryptoUtil.RSADecrypt(encrypted, prikey);
            //System.err.println("decrypted");
            //Dump.dump(decrypted);

            DaoUtil.begin();

            // Fetch variables do need DB-access

            // Business logic
            String flag = Authorizer.verifyPassword(name, decrypted);
            User u = null;
            if (flag.equals("success"))
                u = UserMgr.forName(name);

            DaoUtil.commit();

            // Compose output
            json.put(FLAG, flag);
            if (flag != "success")
            {
                if (flag.equals("!notfound"))
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                if (flag.equals("!forbid"))
                    resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                if (flag.equals("!incorrect"))
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                if (flag.equals("!error"))
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            else
            {
                session.setAttribute("authorize.user.name", u.getName());
                session.setAttribute("authorize.user.roleid", u.getRole().getId());
                Organization o = u.getOrganization();
                if (o != null)
                    session.setAttribute("authorize.user.organization", o.getName());
                Role r = u.getRole();
                if (r != null)
                {
                    session.setAttribute("authorize.user.role", r.getName());
                    if (r.getHome() != null)
                    {
                        String home = req.getContextPath() + r.getHome();
                        session.setAttribute("authorize.user.home", home);
                        json.put(HOME, home);
                    }
                }
            }

        }
        catch (MissingParameterException ex)
        {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Logger.getLogger("tvprotal.authorize")
                .error("/authorize/adminLogin failed:", ex);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        finally
        {
            out.println(json.toJSONString());
            DaoUtil.close();
        }

        return;
    }
}
