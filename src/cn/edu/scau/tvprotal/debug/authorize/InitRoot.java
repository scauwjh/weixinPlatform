package cn.edu.scau.tvprotal.debug.authorize;

import cn.edu.scau.tvprotal.dao.DaoUtil;
import cn.edu.scau.tvprotal.core.authorize.Authorizer;

public class InitRoot
{
    public static void main(String[] args)
    {
        try
        {
            DaoUtil.begin();

            String flag = Authorizer.initRoot();
            System.out.println("*** InitRoot ***\n" + flag);

            DaoUtil.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return;
        }
        finally
        {
            DaoUtil.close();
        }
    }
}
