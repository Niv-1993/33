package BusinessLayer;
import BusinessLayer.Type.Category;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class iStoreControllerTest {
    iStoreController sc;

    public iStoreControllerTest(){
        sc=null;
    }
    @BeforeEach
    void setUp() {
        Category c=mock(Category.class);
        when(c.get_categories()).thenReturn(new ArrayList<>());


    }

    @Test
    void getID() {
        int check=sc.getID();
        if (check<0)
            Assert.fail();
        for (int i=0; i<20; i++)
            if (check!=sc.getID())
                Assert.fail();
    }

    @Test
    void counterCategory() {
        int counter=sc.counterCategory();
        for (int i=0; i<20; i++)
        {
            sc.addCategory("test "+i);
            if (counter+i!=sc.counterCategory())
                Assert.fail("the SC does not added Category #"+i);
        }
    }
    @Test
    void getCategory() {
        ArrayList<Category> l=new ArrayList();
        for (int i=0; i<20; i++)
        {
            sc.addCategory("test "+i);
//            if (counter+i!=sc.counterCategory())
//                Assert.fail("the SC does not added Category #"+i);
        }
    }

}