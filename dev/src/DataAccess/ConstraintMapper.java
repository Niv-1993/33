package DataAccess;

import Business.ShiftPKG.ConstConstraint;
import Business.ShiftPKG.Constraint;
import Business.ShiftPKG.TempConstraint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintMapper extends Mapper{
    private static ConstraintMapper conMapper = null;
    private final HashMap<Integer, Constraint> constraints;

    public static ConstraintMapper getInstance() {
        if (conMapper == null)
            conMapper = new ConstraintMapper();
        return conMapper;
    }

    private ConstraintMapper(){
        super();
        constraints = new HashMap<>();
    }


    public int getNextCID() {
        int nextCID = 1;
        ResultSet res;
        String query = "SELECT max(CID)+1 as maxCID FROM (SELECT CID from ConstConstraints union all SELECT CID FROM TempConstraints";
        try (Connection con = connect(); PreparedStatement pre = con.prepareStatement(query)) {
            res = pre.executeQuery();
            if (res.first())
                nextCID = res.getInt("nextCID");
        } catch (Exception e) {
        }
        return nextCID;

    }

    public void insertConstConstraint(ConstConstraint c) {
        boolean res =false;
        String query = "INSERT INTO ConstConstraint (CID,EID,ShiftType,DayOfWeek,Reason) VALUES (?,?,?,?,?)";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, c.getCID());
            pre.setInt(2,c.getEID());
            pre.setString(3, c.getShiftType().name());
            pre.setString(4, c.getDay().name());
            pre.setString(5, c.getReason());
            res = pre.executeUpdate()>0;
        } catch (Exception e) {
        }finally {
            if (res)
                constraints.put(c.getCID(), c);
        }
    }


    public void insertTempConstraint(TempConstraint c) {
        boolean res =false;
        String query = "INSERT INTO TempConstraint (CID,EID,ShiftType,Date,Reason) VALUES (?,?,?,?,?)";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, c.getCID());
            pre.setInt(2,c.getEID());
            pre.setString(3, c.getShiftType().name());
            pre.setString(4, c.getDate().toString());
            pre.setString(5, c.getReason());
            res = pre.executeUpdate()>0;
        } catch (Exception e) {
        }finally {
            if (res)
                constraints.put(c.getCID(), c);
        }
    }


    public void deleteConstraint(int cid) {
        String query = "DELETE FROM ConstConstraint WHERE CID= ?;\n" +"DELETE FROM TempConstraint WHERE CID= ?";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1,cid);
            pre.executeUpdate();
        } catch (Exception e) {
        }
    }

    public Map<Integer, Constraint> selectAllConstraints() {
        String query1 = "SELECT * FROM ConstConstraint";
        String query2 = "SELECT * FROM TempConstraint";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query1);PreparedStatement pre2 = con.prepareStatement(query2)) {
            ResultSet res = pre.executeQuery();
            ResultSet res2 = pre2.executeQuery();
            while (res.next()) {
                addConstConstraintToMap(res);
            }
            while (res2.next()) {
                addTempConstraintToMap(res2);
            }
        } catch (Exception e) {
        }
        return constraints;
    }

    private Constraint addConstConstraintToMap(ResultSet res) throws SQLException {
        int CID = res.getInt("CID");
        if (constraints.containsKey(CID))
            return constraints.get(CID);
        int EID = res.getInt("EID");
        String shiftType = res.getString("ShiftType");
        DayOfWeek day = DayOfWeek.valueOf(res.getString("Day"));
        String reason = res.getString("Reason");
        ConstConstraint constCon = new ConstConstraint(CID, EID, day, shiftType, reason);
        constraints.put(constCon.getCID(), constCon);
        return constCon;
    }

    private Constraint addTempConstraintToMap(ResultSet res) throws SQLException {
        int CID = res.getInt("CID");
        if (constraints.containsKey(CID))
            return constraints.get(CID);
        int EID = res.getInt("EID");
        String shiftType = res.getString("ShiftType");
        LocalDate date = LocalDate.parse(res.getString("Date"));
        String reason = res.getString("Reason");
        TempConstraint tempCon = new TempConstraint(CID,EID,date,shiftType,reason);
        constraints.put(tempCon.getCID(), tempCon);
        return tempCon;
    }

    public List<Constraint> getConstraint(int eid, LocalDate date) {
        List<Constraint> listCon = new ArrayList<>();
        String query1 = "SELECT * FROM TempConstraint WHERE EID=? AND Date=?";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query1)) {
            pre.setInt(1,eid);
            pre.setString(2,date.toString());
            ResultSet res = pre.executeQuery();
            while (res.next()) {

                    addTempConstraintToMap(res);
                listCon.add(constraints.get(res.getInt("CID")));
            }
        } catch (Exception e) {
        }
        return listCon;
    }

    public Constraint getConstraint(int cid) {
        if (constraints.containsKey(cid))
            return constraints.get(cid);
        else {
            String query1 = "SELECT * FROM TempConstraint WHERE CID=?";
            String query2 = "SELECT * FROM ConstConstraint WHERE CID=?";
            try (Connection con = connect();
                 PreparedStatement pre = con.prepareStatement(query1); PreparedStatement pre2 = con.prepareStatement(query2)) {
                pre.setInt(1, cid);
                ResultSet res = pre.executeQuery();
                if (res.next()) {
                    return addTempConstraintToMap(res);
                } else {
                    pre2.setInt(1, cid);
                    ResultSet res2 = pre2.executeQuery();
                    if (res2.next()) {
                        return addConstConstraintToMap(res);
                    }
                }
            } catch (Exception e) {
            }
            return null;
        }
    }

    public List<Constraint> getConstraintsOfEID(int eid) {
        ArrayList<Constraint> consList = new ArrayList<>();
        String query1 ="SELECT * FROM ConstConstraint WHERE EID=?";
        String query2 = "SELECT * FROM TempConstraint WHERE EID=?";
        try (Connection con = connect();
             PreparedStatement pre = con.prepareStatement(query1);PreparedStatement pre2 = con.prepareStatement(query2)) {
            pre.setInt(1,eid);
            pre2.setInt(1,eid);
            ResultSet res = pre.executeQuery();
            ResultSet res2 = pre2.executeQuery();
            while (res.next()) {
                Constraint c =  addConstConstraintToMap(res);
                consList.add(c);
            }
            while (res2.next()) {
                Constraint c = addTempConstraintToMap(res2);
                consList.add(c);
            }
        } catch (Exception e) {
        }
        return consList;
    }

    public void updateReason(int cid, String newReason) {
        updateIntString(cid,newReason,"Reason","CID","ConstConstraint");
        updateIntString(cid,newReason,"Reason","CID","TempConstraint");
    }

    public void updateShiftType(int cid, String newType) {
        updateIntString(cid,newType,"ShiftType","CID","ConstConstraint");
        updateIntString(cid,newType,"ShiftType","CID","TempConstraint");
    }
}
