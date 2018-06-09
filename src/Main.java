
import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the path for the config file:");
        String configPath = scan.nextLine();
        System.out.println("Enter the path for the GED file:");
        String gedPath = scan.nextLine();

        BufferedReader configFile = new BufferedReader(new FileReader(configPath));
        BufferedReader gedFile = new BufferedReader(new FileReader(gedPath));

        String[][] validTags = new String[3][];
        validTags[0] = configFile.readLine().split(",");
        validTags[1] = configFile.readLine().split(",");
        validTags[2] = configFile.readLine().split(",");

        String line = gedFile.readLine();
        while(line!=null)
        {
            System.out.println("--> "+line);
            String[] lineParts = line.split(" ");
            int level = Integer.parseInt(lineParts[0]);
            String tag;
            boolean fail=false;//Hack to handle the special <ID> type tags
            if (lineParts.length > 2 && (lineParts[2].equalsIgnoreCase("FAM")||lineParts[2].equalsIgnoreCase("INDI"))) {
                tag = lineParts[2];
            }
            else
            {
                tag = lineParts[1];
                if(tag.equalsIgnoreCase("FAM")||tag.equalsIgnoreCase("INDI"))
                {
                    fail=true;
                }
            }
            boolean validTag = fail || level>=validTags.length?false:IsValid(tag, validTags[level]);
            String lineArgs = line.substring(line.indexOf(tag)+tag.length());
            System.out.printf("<-- %d|%s|%s|%s\n", level, tag, validTag?"Y":"N", lineArgs);

            line = gedFile.readLine();
        }

        scan.close();
        configFile.close();
        gedFile.close();
    }

    private static boolean IsValid(String tag, String[] tagList)
    {
        for (String validTag : tagList) {
            if (validTag.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }
}
