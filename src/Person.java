import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;;

public class Person
{
    public String ID;
    public String Name;
    public String Gender;
    public Date Birthday;
    public Date DeathDay;
    public String Child;
    public String Spouse;

    public Person()
    {
        Child = "N/A";
        Spouse = "N/A";
    }

    public long GetAge()
    {
        if (Birthday==null) {
            return 0;
        }
        
        Date today = new Date();
        if (DeathDay != null && DeathDay.compareTo(today)<0) {
            today = DeathDay;
        }
        long diff = today.getTime() - Birthday.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;
    }

    public boolean IsAlive()
    {
        return DeathDay==null;
    }

    public String toString()
    {
        String birthString = Birthday==null?null:new SimpleDateFormat("dd-MMM-yyyy").format(Birthday);
        String deathString = DeathDay==null?null:new SimpleDateFormat("dd-MMM-yyyy").format(DeathDay);
        return String.format("|%6s|%20s|%5s|%15s|%6d|%6b|%15s|%8s|%8s|",
            ID,Name,Gender,birthString,GetAge(),IsAlive(),deathString, Child, Spouse);
    }
}