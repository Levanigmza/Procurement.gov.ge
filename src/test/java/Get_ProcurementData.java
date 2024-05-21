import Steps.ProcurementGov_Steps;
import Utils.Setup;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static Page_Objects.ProcurementGov_Data.Spa_Url;

public class Get_ProcurementData {

    private static final Setup setup = new Setup();
    private static final  ProcurementGov_Steps steps = new ProcurementGov_Steps();

    @BeforeClass
    public void setUp() {
        setup.setup();
    }

    @AfterClass
    public void tearDown() {
        setup.teardown();
    }

    @Test
    public void exampleTest() {
        setup.Navigate_ToUrl(Spa_Url);
        steps.GetCurrentDate_Data();
        steps.Open_Record();

    }
}
