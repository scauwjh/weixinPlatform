package cn.edu.scau.tvprotal.dao.authorize;

import java.io.Serializable;
//import cn.edu.scau.tvprotal.dao.authorize.*;

/** 映射系统用户的实体类
 * <br />
 * 通过继承来扩展其数据域, 扩展的数据域应存储于另外的表中(通过配置文件)
 */
public class User
    implements Serializable
{
    private static final long serialVersionUID = 1L;
  // FIELDS
    private Long id;
    /** 名字
     * <br />
     * 对于管理员类来说, 同时作为登录名
     */
    private String name;

    /** 用来封号的
     */
    private Boolean enabled;
    /** 盐
     */
    private byte[] salt;
    /** 加盐散列后的密码
     */
    private byte[] pass;

    /** 用户角色
     * <br />
     * 主要影响是否可登录, 以及登录后派往的目标等
     */
    private Role role;
    /** 用户所属团体
     * <br />
     * 控制能够操作和控制的作用域
     */
    private Organization organization;

  // GETTER/SETTER
    public Long getId()
    {
        return id;
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
    public Boolean getEnabled()
    {
        return this.enabled;
    }
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }
    public byte[] getSalt()
    {
        return salt;
    }
    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }
    public byte[] getPass()
    {
        return pass;
    }
    public void setPass(byte[] pass)
    {
        this.pass = pass;
    }
    public Role getRole()
    {
        return role;
    }
    public void setRole(Role role)
    {
        this.role = role;
    }
    public Organization getOrganization()
    {
        return organization;
    }
    public void setOrganization(Organization organization)
    {
        this.organization = organization;
    }
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

  // CONSTRUCT
    public User()
    {
        this.setEnabled(Boolean.TRUE);

        return;
    }

    public User(String name, Role role, Organization organization)
    {
        this();

        this.setName(name);
        this.setRole(role);
        this.setOrganization(organization);

        return;
    }
}
