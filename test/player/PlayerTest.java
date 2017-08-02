package player;

import item.EquipmentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created on 2017/8/2.
 * Description:
 * @author Liao
 */
public class PlayerTest {
    private PrintStream console;
    private ByteArrayOutputStream outputStream;
    private PrintStream target;
    private String sep;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        target = new PrintStream(outputStream);
        console = System.out;
        sep = System.lineSeparator();
        System.setOut(target);
    }

    @After
    public void tearDown() {
        System.setOut(console);
        target.close();
    }

    @Test
    public void equipTest() {
        Player player = new Player("test");
        player.removeEquipment();
        String expected = "Equipment:" + sep
                + "HAND : nothing" + sep
                + "CHEST : nothing" + sep
                + "LEGS : nothing" + sep
                + "HEAD : nothing" + sep + sep
                + "You have no equipment" + sep;
        assertEquals(expected, outputStream.toString());
    }
}