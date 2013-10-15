package cn.edu.scau.tvprotal.core.authorize;

import cn.edu.scau.tvprotal.dao.DaoUtil;
import cn.edu.scau.tvprotal.dao.authorize.*;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class UserMgr
{
    public static User forName(String name)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(User.class)
            .add(Restrictions.eq("name", name));

        return(
            (User)DaoUtil.get(dc)
        );
    }

    /** 创建用户
     * <br />
     * 使用此方法创建的用户因为密码域为空所以自动地无法在Web登陆, 需要
     */
    public static User create(String name, String role, String organ)
    {
        try
        {
            if (name == null)
                throw(new IllegalArgumentException("name must have value."));

            Role r = null;
            if (role != null)
            {
                r = RoleMgr.forName(role);
                if (r == null)
                    throw(new IllegalArgumentException("No such Role:" + role));
            }

            Organization o = null;
            if (organ != null)
            {
                o = OrgMgr.forName(organ);
                if (o == null)
                    throw(new IllegalArgumentException("No such Organization:" + organ));
            }

            User u = new User(name, r, o);

            DaoUtil.save(u);

            return(u);
        }
        catch (ConstraintViolationException ex)
        {
            throw(new IllegalArgumentException("name have been used:" + name));
        }
    }

    public static String delete(String name)
    {
        try
        {
            User u = forName(name);
            if (u == null)
                return("!notfound");
            DaoUtil.delete(u);

            return("success");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }
}
