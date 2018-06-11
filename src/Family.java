import java.util.Date;
import java.text.Format;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class Family
{
    public String ID;
    public Date MarrageDate;
    public Date DivorceDate;
    public String HusbandID;
    public String WifeID;
    public ArrayList<String> Children;

    public Family()
    {
        Children = new ArrayList<String>();
    }

    public String Print(HashMap<String, Person> people)
    {
        StringBuilder sb = new StringBuilder();
        if (Children.size()>0) {
            sb.append("{'");
            sb.append(Children.get(0));
            sb.append('\'');
            for (int i = 1; i < Children.size(); i++) {
                sb.append(",'");
                sb.append('\'');
            }
            sb.append('}');
        }
        else
        {
            sb.append("N/A");
        }
        Person husband =people.get(HusbandID);
        Person wife = people.get(WifeID);
        return String.format("|%6s|%15s|%15s|%7s|%20s|%7s|%20s|%15s|"
            , ID, MarrageDate, DivorceDate, HusbandID, husband.Name, WifeID, wife.Name, sb.toString());
    }
}