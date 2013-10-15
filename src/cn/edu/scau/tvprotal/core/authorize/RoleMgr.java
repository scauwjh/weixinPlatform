package cn.edu.scau.tvprotal.core.authorize;

/* dao */
import cn.edu.scau.tvprotal.dao.DaoUtil;
import cn.edu.scau.tvprotal.dao.authorize.*;
/* hibernate */
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.exception.ConstraintViolationException;

/** 用于操纵 角色 的数据类
 * <br />
 */
public class RoleMgr
{
  // API
    /** 按名查找
     */
    public static Role forName(String name)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Role.class)
            .add(Restrictions.eq("name", name));

        return(
            (Role)DaoUtil.get(dc)
        );
    }

    /**
     * 创建角色
     * <br />
     * 名字重复的话会抛出异常
     */
    public static Role create(String name)
    {
        try
        {
            if (name == null)
                throw(new IllegalArgumentException("name must have value."));

            Role r = new Role(name);

            DaoUtil.save(r);

            return(r);
        }
        catch (ConstraintViolationException ex)
        {
            throw(new IllegalArgumentException("name have been used:" + name));
        }
    }

    /** 删除角色
     * <br />
     * 同时会对持有该角色的用户进行级联的set null
     */
    public static String delete(String name)
    {
        try
        {
            Role r = forName(name);
            if (r == null)
                return("!notfound");

            DaoUtil.createQuery("update User set role = null where role.id = :rid")
                .setLong("rid", r.getId())
                .executeUpdate();

            DaoUtil.delete(r);

            return("success");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return("!error");
        }
    }
}
