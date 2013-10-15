package cn.edu.scau.tvprotal.dao.authorize;

import java.io.Serializable;

/** 映射所属组织的实体类
 */
public class Organization
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
        return this.description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

  // CONSTRUCT
    public Organization()
    {
        return;
    }

    public Organization(String name)
    {
        this();
        this.setName(name);
        return;
    }

    public Organization(String name, String description)
    {
        this(name);
        this.setDescription(description);
        return;
    }
}
