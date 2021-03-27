package Meeting4_Soap;

    import java.io.File;
    import io.restassured.RestAssured;
    import org.testng.annotations.Test;
    import static org.testng.AssertJUnit.assertEquals;

public class FileDownloading {
        public static long getFileSize() {
            File baseFile = new File("C:\\Users\\atkabladze\\Desktop\\files\\minion.zip");
            return baseFile.length();
        }
        @Test
        void test() {
            byte[] dowloadedFile = RestAssured.given().when()
                    .get("https://chercher.tech/files/minion.zip")
                    .then().extract().asByteArray();

            System.out.println("Actual File Size : "+getFileSize());
            System.out.println("Download File Size : "+dowloadedFile.length);
            assertEquals(getFileSize(), dowloadedFile.length);
        }

    }

