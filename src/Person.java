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
        long diff = today.getTime() - Birthday.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;
    }

    public boolean IsAlive()
    {
        return DeathDay==null;
    }

    public String toString()
    {
        return String.format("|%s\t|%s %s\t|%s\t|%s%\t|%d\t|%b\t|%s\t|%s\t|%s\t|",
            ID,Name,Gender,Birthday,GetAge(),IsAlive(),DeathDay, Child, Spouse);
    }
}