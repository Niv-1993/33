package Presentation;

import Business.ApplicationFacade.*;
import Business.ShiftPKG.ShiftController;

public class Controllers {
    private final iRegularRoleController rc;
    private final iManagerRoleController mc;

    public Controllers() {
        rc = new RegularRoleController();
        mc = new ManagerRoleController(rc.getUtils());
    }

    public iManagerRoleController getMc() {
        return mc;
    }

    public iRegularRoleController getRc() {
        return rc;
    }
}
