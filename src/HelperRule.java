import java.util.ArrayList;
import java.util.Random;

public class HelperRule{
    private ArrayList<String> rules;
    private int ruleCount;
    private Random random;
    
    public HelperRule(){
        rules = new ArrayList<String>();
        ruleCount = 0;
        random = new Random();
    }
    
    public void addRule(String rule){
        rules.add(rule);
        ++ruleCount;
    }

    public String getRule(){
        int choice = random.nextInt(ruleCount);
        return rules.get(choice);
    }
}