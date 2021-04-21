package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalQuantityDocument;

public class QuantityDocumentController extends DalController<DalQuantityDocument> {

    private final String tableName = "QuantityDocuments";

    public QuantityDocumentController(){super("QuantityDocuments");}
}
