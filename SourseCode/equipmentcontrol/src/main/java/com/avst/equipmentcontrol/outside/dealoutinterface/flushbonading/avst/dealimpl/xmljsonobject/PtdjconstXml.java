package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.vo.GetptdjconstVO;

import java.util.ArrayList;
import java.util.List;

public class PtdjconstXml {

    private String version;

    private String line0;

    private String line1;

    private String line2;

    private String line3;

    private String line4;

    private String line5;

    private String line6;

    private String line7;

    private String line8;

    private String line9;

    private String line10;

    private String line11;

    private String line12;

    private String line13;

    private String line14;

    private String line15;

    public String getLine0() {
        return line0;
    }

    public void setLine0(String line0) {
        this.line0 = line0;
    }

    public String getLine15() {
        return line15;
    }

    public void setLine15(String line15) {
        this.line15 = line15;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(String line4) {
        this.line4 = line4;
    }

    public String getLine5() {
        return line5;
    }

    public void setLine5(String line5) {
        this.line5 = line5;
    }

    public String getLine6() {
        return line6;
    }

    public void setLine6(String line6) {
        this.line6 = line6;
    }

    public String getLine7() {
        return line7;
    }

    public void setLine7(String line7) {
        this.line7 = line7;
    }

    public String getLine8() {
        return line8;
    }

    public void setLine8(String line8) {
        this.line8 = line8;
    }

    public String getLine9() {
        return line9;
    }

    public void setLine9(String line9) {
        this.line9 = line9;
    }

    public String getLine10() {
        return line10;
    }

    public void setLine10(String line10) {
        this.line10 = line10;
    }

    public String getLine11() {
        return line11;
    }

    public void setLine11(String line11) {
        this.line11 = line11;
    }

    public String getLine12() {
        return line12;
    }

    public void setLine12(String line12) {
        this.line12 = line12;
    }

    public String getLine13() {
        return line13;
    }

    public void setLine13(String line13) {
        this.line13 = line13;
    }

    public String getLine14() {
        return line14;
    }

    public void setLine14(String line14) {
        this.line14 = line14;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> toList() {

        List<String> list=new ArrayList<String>();
        if(null!=line0){
            list.add(line0.replaceAll(" ",""));
        }
        if(null!=line1){
            list.add(line1.replaceAll(" ",""));
        }
        if(null!=line2){
            list.add(line2.replaceAll(" ",""));
        }
        if(null!=line3){
            list.add(line3.replaceAll(" ",""));
        }
        if(null!=line4){
            list.add(line4.replaceAll(" ",""));
        }
        if(null!=line5){
            list.add(line5.replaceAll(" ",""));
        }
        if(null!=line6){
            list.add(line6.replaceAll(" ",""));
        }
        if(null!=line7){
            list.add(line7.replaceAll(" ",""));
        }
        if(null!=line8){
            list.add(line8.replaceAll(" ",""));
        }
        if(null!=line9){
            list.add(line9.replaceAll(" ",""));
        }
        if(null!=line10){
            list.add(line10.replaceAll(" ",""));
        }
        if(null!=line11){
            list.add(line11.replaceAll(" ",""));
        }
        if(null!=line12){
            list.add(line12.replaceAll(" ",""));
        }
        if(null!=line13){
            list.add(line13.replaceAll(" ",""));
        }
        if(null!=line14){
            list.add(line14.replaceAll(" ",""));
        }
        if(null!=line15){
            list.add(line15.replaceAll(" ",""));
        }
         return list;

    }
}
