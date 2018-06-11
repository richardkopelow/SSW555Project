public class Entry
{
    public int Level;
    public String Tag;
    public String Args;

    public Entry(String line)
    {
        String[] lineParts = line.split(" ");
        Level = Integer.parseInt(lineParts[0]);
        if (lineParts.length > 2 && (lineParts[2].equalsIgnoreCase("FAM")||lineParts[2].equalsIgnoreCase("INDI"))) {
            Tag = lineParts[2];
            Args = lineParts[1];
        }
        else
        {
            Tag = lineParts[1];
            Args = line.substring(line.indexOf(Tag)+Tag.length());
            if (Args.length()>0) {
                Args = Args.substring(1);
            }
        }
    }
}