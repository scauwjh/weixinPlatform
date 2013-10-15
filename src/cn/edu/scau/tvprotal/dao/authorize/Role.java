package cn.edu.scau.tvprotal.dao.authorize;

import java.io.Serializable;

/** 映射用户角色的实体类
 */
public class Role
    implements Serializable
{
    private static final long serialVersionUID = 1L;
  // FIELDS
    private Long id;
    /** 名字
     * <br />
     * 虽然允许使用中文但考虑到编码一致性请尽量使用alphanumeric
     */
    private String name;
    private String description;
    ///** 主页
     //* <br />
     //* 可以通过该属性指派登录后被发往的页面
     //*/
    private String home;

  // GETTER/SETTER
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getHome()
    {
        return(this.home);
    }
    public void setHome(String home)
    {
        this.home = home;
        return;
    }
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

  // CONSTRUCT
    public Role()
    {
        return;
    }

    public Role(String name)
    {
        this();
        this.setName(name);
        return;
    }

    public Role(String name, String description)
    {
        this(name);
        this.setDescription(description);
        return;
    }
}
