package DataAccess;

import Business.ShiftPKG.Constraint;
import java.util.HashMap;

public class ConstraintMapper extends Mapper{
    private static ConstraintMapper conMapper = null;
    private HashMap<Integer, Constraint> constraints;

    public static ConstraintMapper getInstance() {
        if (conMapper == null)
            conMapper = new ConstraintMapper();
        return conMapper;
    }

    private ConstraintMapper(){
        super();
        constraints = new HashMap<>();
    }
    public HashMap<Integer, Constraint> getConstraints() {
        return constraints;
    }


}
