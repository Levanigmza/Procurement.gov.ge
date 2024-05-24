package Steps;

import Utils.CsvListener;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Page_Objects.ProcurementGov_Data.*;

public class ProcurementGov_Steps {
    public static Page page;
    private String dayOfMonth;
    private int PageOfCount;
    private LocalDate currentDate;
    private CsvListener csvListener;

    public ProcurementGov_Steps() {
        csvListener = new CsvListener();
        csvListener.onStart();
    }

    public void GetCurrentDate_Data() {
        currentDate = LocalDate.now();
        int day = currentDate.getDayOfMonth();
        dayOfMonth = String.valueOf(day);
        page.locator("#app_date_from").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(dayOfMonth)).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("ძებნა")).click();

        page.waitForTimeout(500);

    }

    public static String getElementTextByXPath(String xPath) {
        return page.locator("xpath=" + xPath).textContent();
    }


    public void Open_Record() {
        String  PageCount = getElementTextByXPath("/html/body/div[2]/div[3]/div[2]/div/span/button[3]/span[2]");
        String regex = "/(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(PageCount);

        if (matcher.find()) {
            String numberAfterSlash = matcher.group(1);
            PageOfCount = Integer.parseInt(numberAfterSlash);
        } else {
            System.out.println("No match found for the regex pattern.");
            return;
        }

        try {
            for (int j = 0; j < PageOfCount - 1; j++) {
                for (int i = 1; i <= 4; i++) {
                    Locator element = page.locator("xpath=/html/body/div[2]/div[3]/div[2]/table/tbody/tr[" + i + "]");
                    if (element.isVisible()) {
                        element.click();
                        page.waitForTimeout(500);
                        Parse_RecordData();

                        csvListener.writeResultToExcel(Purchase_Type, Application_Id, Buyer, Purchase_Cost, Purchase_Category, Additional_Info );
                        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("უკან")).click();
                    } else {
                        System.out.println("Element at index " + i + " is not visible.");
                    }
                }
                page.locator("#btn_next").click();
            }
            csvListener.onFinish();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }


    public void Parse_RecordData() {
        Purchase_Type = null;
        Application_Id = null;
        Buyer = null;
        Purchase_Cost = null;
        Purchase_Category = null;
        Additional_Info = null;


        String xPath1 = "/html/body/div[2]/div[3]/div[3]/div[2]/div[1]/div/div[1]/table/tbody/tr[1]/td[2]";
        String xPath2 = "/html/body/div[2]/div[3]/div[3]/div[2]/div[1]/div/div[1]/table/tbody/tr[2]/td[2]";
        String xPath3 = "/html/body/div[2]/div[3]/div[3]/div[2]/div[1]/div/div[1]/table/tbody/tr[4]/td[2]";
        String xPath4 = "/html/body/div[2]/div[3]/div[3]/div[2]/div[1]/div/div[1]/table/tbody/tr[8]/td[2]";
        String xPath5 = "/html/body/div[2]/div[3]/div[3]/div[2]/div[1]/div/div[1]/table/tbody/tr[10]/td[2]";
        String xPath6 = "/html/body/div[2]/div[3]/div[3]/div[2]/div[1]/div/div[1]/table/tbody/tr[15]/td[1]";


        Purchase_Type = getElementTextByXPath(xPath1);
        Application_Id = getElementTextByXPath(xPath2);
        Buyer = getElementTextByXPath(xPath3);
        Purchase_Cost = getElementTextByXPath(xPath4);
        Purchase_Category = getElementTextByXPath(xPath5);
        Additional_Info = getElementTextByXPath(xPath6);


    }


}
