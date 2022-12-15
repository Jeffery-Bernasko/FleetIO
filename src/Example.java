import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class Example{

    /* Get the class name to be printed on */
    static Logger log = Logger.getLogger(Example.class);

    public static void main(String[] args)throws IOException,SQLException{

        //Configure the log4j to its right properties
        //BasicConfigurator.configure();
        log.debug("Hello this is a debug message");
        log.info("Hello this is an info message");
    }
}