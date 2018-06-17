public class EntryError extends GEDError
{
    private int lineNumber;
    private String tag;
    private int level;

    public EntryError(int line, String tag, int level)
    {
        this.lineNumber = line;
        this.level = level;
    }

    public String toString()
    {
        return String.format("Error at line %d: Tag \"%s\" is not valid at level %d", lineNumber, tag, level);
    }
}