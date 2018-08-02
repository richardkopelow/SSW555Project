
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException, ParseException
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

        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<GEDError> gedErrors =  new ArrayList<GEDError>();

        int count = 1;
        //Parse out the ged file
        String line = gedFile.readLine();
        while(line!=null)
        {
            Entry entry = new Entry(line, count);
            try
            {
                if (IsValid(entry.Tag, validTags[entry.Level])) {
                    entries.add(entry);
                }
                else
                {
                    gedErrors.add(new EntryError(count, entry.Tag, entry.Level));
                }
            }
            catch (Exception e)
            {
                gedErrors.add(new EntryError(count, entry.Tag, entry.Level));
            }

            line = gedFile.readLine();
            count++;
        }
        scan.close();
        configFile.close();
        gedFile.close();

        //Develop Family map
        HashMap<String, Family> families = new HashMap<String, Family>();
        HashMap<String, Person> people = new HashMap<String, Person>();
        Family workingFamily = new Family(people);
        Person workingPerson = new Person();
        String stage2Type = "";

        for (int i = 0; i < entries.size(); i++) {
            Entry ent = entries.get(i);
            try
            {
                switch (ent.Tag.toLowerCase()) {
                    case "indi":
                        workingPerson = new Person();
                        workingPerson.ID = ent.Args;
                        people.put(ent.Args, workingPerson);
                        break;
                    case "fam":
                        workingFamily = new Family(people);
                        workingFamily.ID = ent.Args;
                        families.put(ent.Args, workingFamily);
                        break;
                    case "name":
                        workingPerson.Name = ent.Args;
                        break;
                    case "sex":
                        workingPerson.Gender = ent.Args;
                        break;
                    case "birt":
                    case "deat":
                    case "marr":
                    case "div":
                        stage2Type = ent.Tag;
                        break;
                    case "famc":
                        workingPerson.Child = ent.Args;
                        break;
                    case "fams":
                        workingPerson.Spouse = ent.Args;
                        break;
                    case "wife":
                        workingFamily.WifeID = ent.Args;
                        break;
                    case "husb":
                        workingFamily.HusbandID = ent.Args;
                        break;
                    case "chil":
                        workingFamily.Children.add(ent.Args);
                        break;
                    case "date":
                        DateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
                        Date date = fmt.parse(ent.Args);
                        switch (stage2Type.toLowerCase()) {
                            case "birt":
                                workingPerson.SetBirthday(date);
                                break;
                            case "deat":
                                workingPerson.SetDeathday(date);
                                break;
                            case "marr":
                                workingFamily.SetMarrageDate(date);
                                break;
                            case "div":
                                workingFamily.DivorceDate = date;
                                break;
                        
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (Exception e)
            {
                gedErrors.add(new GEDError("Error with line " + ent.LineNumber + ": " + e.getMessage()));
            }
        }
        System.out.println(String.format("|%6s|%20s|%5s|%15s|%6s|%6s|%15s|%8s|%8s|",
        "ID","Name","Gender","Birth","Age","Alive","Death", "Child", "Spouse"));
        for (Person person : people.values()) {
            System.out.println(person.toString());
        }

        System.out.println(String.format("|%6s|%15s|%15s|%7s|%20s|%7s|%20s|%15s|"
        , "ID", "Marrage", "Divorce", "Husband", "Husband Name", "Wife", "Wife Name", "Children"));
        for (Family family : families.values()) {
            System.out.println(family.Print(people));
        }

        System.out.println();
        System.out.println("Errors:");
        for (GEDError error : gedErrors) {
            System.out.println(error);
        }
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
