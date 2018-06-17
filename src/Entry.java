public class Entry
{
    public int Level;
    public String Tag;
    public String Args;
    public int LineNumber;

    public Entry(String line, int lineNumber)
    {
        LineNumber = lineNumber;
        String[] lineParts = line.split(" ");
        if (lineParts.length > 0)
        {
            Level = Integer.parseInt(lineParts[0]);
            if (lineParts.length > 2 && (lineParts[2].equalsIgnoreCase("FAM")||lineParts[2].equalsIgnoreCase("INDI"))) {
                Tag = lineParts[2];
                Args = lineParts[1];
            }
            else
            {
                if (lineParts.length > 1) {
                    Tag = lineParts[1];
                    Args = line.substring(line.indexOf(Tag)+Tag.length());
                    if (Args.length() > 0) {
                        Args = Args.substring(1);
                    }
                }
            }
        }
    }
}