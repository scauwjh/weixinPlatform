package cn.edu.scau.tvprotal.core.authorize;

/* dao */
import cn.edu.scau.tvprotal.dao.DaoUtil;
import cn.edu.scau.tvprotal.dao.authorize.*;
/* hibernate */
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;

/** 用于操纵 组织 的数据类
 * <br />
 */
public class OrgMgr
{
  // API
    /** 按名查找
     */
    public static Organization forName(String name)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Organization.class)
            .add(Restrictions.eq("name", name));

        return(
            (Organization)DaoUtil.get(dc)
        );
    }

    /**
     * 创建组织
     * <br />
     * 名字重复的话会抛出异常
     */
    public static Organization create(String name)
    {
        try
        {
            if (name == null)
                throw(new IllegalArgumentException("name must have value."));

            Organization o = new Organization(name);

            DaoUtil.save(o);

            return(o);
        }
        catch (ConstraintViolationException ex)
        {
            throw(new IllegalArgumentException("name has been used:" + name));
        }
    }

    /** 删除组织
     * <br />
     * 同时会对该组织的用户进行级联的set null
     */
    public static String delete(String name)
    {
        try
        {
            Organization o = forName(name);
            if (o == null)
                return("!notfound");

            DaoUtil.createQuery("update User set organization = null where organization.id = :oid")
                .setLong("oid", o.getId())
                .executeUpdate();

            DaoUtil.delete(o);

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }

    public static String deleteCascade(String name)
    {
        try
        {
            Organization o = forName(name);
            if (o == null)
                return("!notfound");

            DaoUtil.createQuery("delete User where organization.id = :oid")
                .setLong("oid", o.getId())
                .executeUpdate();

            DaoUtil.delete(o);

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }
}
