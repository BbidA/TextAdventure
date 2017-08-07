package io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created on 2017/8/7.
 * Description:
 * @author Liao
 */
public class MessageHelperTest {

    private Scanner scanner;
    private ByteArrayInputStream byteArrayInputStream;

    @Before
    public void setUp() {
        String tmp = "e";
        byteArrayInputStream = new ByteArrayInputStream(tmp.getBytes());
        System.setIn(byteArrayInputStream);
    }


    @Test
    public void getNumberInputTest() throws Exception {
        assertEquals(-1, MessageHelper.getNumberInput());
    }
}