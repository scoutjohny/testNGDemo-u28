import org.testng.annotations.*;

public class Anotations {

    @BeforeClass //Pokreće kod jednom pre SVIH testova!!!
    public void beforeClass(){
        System.out.println("Before Class");
    }

    @BeforeMethod //Pokreće kod jednom pre SVAKOG testa!!!
    public void beforeMethod(){
        System.out.println("Before Method");
    }

    @AfterMethod //Pokreće kod jednom POSLE svakog testa
    public void afterMethod(){
        System.out.println("After Method");
    }

    @AfterClass //Pokreće kod jednom POSLE svih testova
    public void afterClass(){
        System.out.println("After Class");
    }

    @Test
    public void test1(){
        System.out.println("Test1");
    }

    @Test
    public void test2(){
        System.out.println("Test2");
    }
}
