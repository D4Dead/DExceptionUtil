package fr.d4delta.dexceptionutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author D4Death
 */
public class ExceptionManager implements ExceptionHandler {
    
    String applicationName;
    
    public ExceptionManager(String applicationName) {
        this.applicationName = applicationName;
    }
    
    
    @Override
    public void handle(Exception e) {
        handleWithMsg(e, "Something wrong hapenned ! " + System.lineSeparator() + "Here's the full stacktrace:");
    }
    
    public void handleWithMsg(Exception e, String msg) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        sw.toString();
        String logPath;
        JOptionPane.showMessageDialog(null, msg + System.lineSeparator() + sw + System.lineSeparator() + writeLog(sw.toString()));
    }
    
    //Only for some very, very old project 
    @Deprecated
    public void showAndSave(Throwable e) {
        handle((Exception) e);
    }
    
    private String writeLog(String text) {
        
        BufferedWriter writer = null;
        File logFile = null;
        
        try {
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            logFile = new File(System.getProperty("user.dir") + "/" + applicationName + "-crashlog-" + timeLog + ".txt");
            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "Error while saving crash log. You're not lucky !" + System.lineSeparator() + 
            "Here's the (other) stacktrace :" + System.lineSeparator() +
            sw.toString(); 
        } finally {
            try {
                writer.close();
            } catch (Exception e) {}
        }
        
        return "The crash log has been saved to " + logFile.getAbsolutePath();
        
    }

}
