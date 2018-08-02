import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Family
{
    public String ID;
    private Date MarrageDate;
    public Date DivorceDate;
    public String HusbandID;
    public String WifeID;
    public ArrayList<String> Children;

    private HashMap<String,Person> people;

    public Family(HashMap<String,Person> people)
    {
        Children = new ArrayList<String>();
        this.people = people;
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
        Person husband = people.get(HusbandID);
        Person wife = people.get(WifeID);
        return String.format("|%6s|%15s|%15s|%7s|%20s|%7s|%20s|%15s|"
            , ID, MarrageDate, DivorceDate, HusbandID, husband.Name, WifeID, wife.Name, sb.toString());
    }

    public void SetMarrageDate(Date date) throws Exception
    {
        MarrageDate = date;
        Person husband = people.get(HusbandID);
        if (husband != null &&
            husband.GetBirthday()!=null &&
            husband.GetBirthday().compareTo(MarrageDate)>0)
        {
            throw new Exception("Husband can't marry before birth.");
        }
        Person wife = people.get(WifeID);
        if (wife != null &&
            wife.GetBirthday()!=null &&
            wife.GetBirthday().compareTo(MarrageDate)>0)
        {
            throw new Exception("Wife can't marry before birth.");
        }
    }
}