package cn.edu.scau.tvprotal.servlet;

@SuppressWarnings("serial")
public class MissingParameterException
    extends NullPointerException
{
    public MissingParameterException()
    {
        super();
    }

    public MissingParameterException(String s)
    {
        super(s);
    }
}
