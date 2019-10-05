package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CMDTest {


    public static void main(String[] args) {
        String keyWord = "chrome.exe";

        Runtime runtime = Runtime.getRuntime();

        try {
            Process process = runtime.exec("cmd /c Tasklist");

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String s;
            while ((s = in.readLine()) != null) {
                s = s.toLowerCase();
                System.out.println(s+"-----s");
                if (s.startsWith(keyWord)) {
                    System.out.println("==========>"+keyWord);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
