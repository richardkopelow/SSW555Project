public class GEDError
{
    private String message;

    public GEDError(String message)
    {
        this.message = message;
    }

    public GEDError()
    {
        this.message = "";
    }

    public String toString()
    {
        return message;
    }
}