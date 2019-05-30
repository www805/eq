package com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo;

import com.avst.equipmentcontrol.outside.dealoutinterface.polygraph.cmcross.v1.vo.param.Face_rectParam;

public class XBOX_GetResultVO {

    private Face_rectParam face_rect;

    private Integer age;

    private Integer br;

    private Integer emotion;

    private Integer fps;

    private Integer gender;

    private Integer have_face;

    private Integer hr;

    private Integer hr_snr;

    private Integer hrv;

    private Integer relax;

    private Integer spo2;

    private Integer status;

    private Integer stress;

    private Integer stress_snr;

    public Face_rectParam getFace_rect() {
        return face_rect;
    }

    public void setFace_rect(Face_rectParam face_rect) {
        this.face_rect = face_rect;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getBr() {
        return br;
    }

    public void setBr(Integer br) {
        this.br = br;
    }

    public Integer getEmotion() {
        return emotion;
    }

    public void setEmotion(Integer emotion) {
        this.emotion = emotion;
    }

    public Integer getFps() {
        return fps;
    }

    public void setFps(Integer fps) {
        this.fps = fps;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getHave_face() {
        return have_face;
    }

    public void setHave_face(Integer have_face) {
        this.have_face = have_face;
    }

    public Integer getHr() {
        return hr;
    }

    public void setHr(Integer hr) {
        this.hr = hr;
    }

    public Integer getHr_snr() {
        return hr_snr;
    }

    public void setHr_snr(Integer hr_snr) {
        this.hr_snr = hr_snr;
    }

    public Integer getHrv() {
        return hrv;
    }

    public void setHrv(Integer hrv) {
        this.hrv = hrv;
    }

    public Integer getRelax() {
        return relax;
    }

    public void setRelax(Integer relax) {
        this.relax = relax;
    }

    public Integer getSpo2() {
        return spo2;
    }

    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStress() {
        return stress;
    }

    public void setStress(Integer stress) {
        this.stress = stress;
    }

    public Integer getStress_snr() {
        return stress_snr;
    }

    public void setStress_snr(Integer stress_snr) {
        this.stress_snr = stress_snr;
    }
}
