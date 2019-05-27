package com.avst.equipmentcontrol.outside.dealoutinterface.flushbonading.avst.dealimpl.xmljsonobject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement//根节点
public class CheckFDStateXml {

    @XmlElement
    private String version;

    @XmlElement
    private String dev_version;

    @XmlElement
    private String devmid_id;

    @XmlElement
    private String diskrec_isrec;

    @XmlElement
    private String diskrec_begintime;

    @XmlElement
    private String diskrec_continuettime;

    @XmlElement
    private String disk_loaded;

    @XmlElement
    private String disk_freespace;

    @XmlElement
    private String disk_totalspace;

    @XmlElement
    private String disk_recpath;




    @XmlElement
    private String roma_status;

    @XmlElement
    private String romb_status;

    @XmlElement
    private String roma_isburn;

    @XmlElement
    private String romb_isburn;

    @XmlElement
    private String roma_isfinishburn;

    @XmlElement
    private String romb_isfinishburn;

    @XmlElement
    private String roma_disktype;

    @XmlElement
    private String romb_disktype;

    @XmlElement
    private String roma_discCap;

    @XmlElement
    private String romb_discCap;

    @XmlElement
    private String roma_discCapUsed;

    @XmlElement
    private String romb_discCapUsed;

    @XmlElement
    private String roma_setburntime;

    @XmlElement
    private String romb_setburntime;

    @XmlElement
    private String roma_begintime;

    @XmlElement
    private String romb_begintime;

    @XmlElement
    private String roma_lefttime;

    @XmlElement
    private String romb_lefttime;





    @XmlElement
    private String burn_mode;

    @XmlElement
    private String selburntime;

    @XmlElement
    private String audioout_type;

    @XmlElement
    private String burn_syn_yw;

    @XmlElement
    private String next_burndev;

    @XmlElement
    private String prev_burndev;

    @XmlElement
    private String ypjl;

    @XmlElement
    private String subypjl;

    @XmlElement
    private String codecver;

    @XmlElement
    private String splitsize;

    @XmlElement
    private String audiosample;
    @XmlElement
    private String tcp_port;

    @XmlElement
    private String map_tcp_port;

    @XmlElement
    private String sys_tick;

    @XmlElement
    private String dvdnum;

    @XmlElement
    private String dmsg_lst;

    @XmlElement
    private String tusrs;

    @XmlElement
    private String thr_map;

    @XmlElement
    private String audmode;

    @XmlElement
    private String audspeaker;

    @XmlElement
    private String audmic;

    @XmlElement
    private String audout;

    @XmlElement
    private String aotype;

    @XmlElement
    private String audio_enable;

    @XmlElement
    private String subscreen;

    @XmlElement
    private String mainview_index;

    @XmlElement
    private String subview_index;

    @XmlElement
    private String cpu;

    @XmlElement
    private String ddr_used;

    @XmlElement
    private String ddr_total;

    @XmlElement
    private String serialnumber;

    @XmlElement
    private String factory;

    @XmlElement
    private String hw;

    @XmlElement
    private String sw;

    @XmlElement
    private String laninfo;

    @XmlElement
    private String count;

    @XmlElement
    private String lan0;

    @XmlElement
    private String ip;

    @XmlElement
    private String mac;

    @XmlElement
    private String mask;

    @XmlElement
    private String gtway;

    @XmlElement
    private String link;

    @XmlElement
    private String lan1;

    public String getLan1() {
        return lan1;
    }

    public void setLan1(String lan1) {
        this.lan1 = lan1;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGtway() {
        return gtway;
    }

    public void setGtway(String gtway) {
        this.gtway = gtway;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLan0() {
        return lan0;
    }

    public void setLan0(String lan0) {
        this.lan0 = lan0;
    }

    public String getLaninfo() {
        return laninfo;
    }

    public void setLaninfo(String laninfo) {
        this.laninfo = laninfo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSw() {
        return sw;
    }

    public void setSw(String sw) {
        this.sw = sw;
    }

    public String getHw() {
        return hw;
    }

    public void setHw(String hw) {
        this.hw = hw;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getDdr_total() {
        return ddr_total;
    }

    public void setDdr_total(String ddr_total) {
        this.ddr_total = ddr_total;
    }

    public String getDdr_used() {
        return ddr_used;
    }

    public void setDdr_used(String ddr_used) {
        this.ddr_used = ddr_used;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDevmid_id() {
        return devmid_id;
    }

    public void setDevmid_id(String devmid_id) {
        this.devmid_id = devmid_id;
    }

    public String getDiskrec_isrec() {
        return diskrec_isrec;
    }

    public void setDiskrec_isrec(String diskrec_isrec) {
        this.diskrec_isrec = diskrec_isrec;
    }

    public String getDiskrec_begintime() {
        return diskrec_begintime;
    }

    public void setDiskrec_begintime(String diskrec_begintime) {
        this.diskrec_begintime = diskrec_begintime;
    }

    public String getDiskrec_continuettime() {
        return diskrec_continuettime;
    }

    public void setDiskrec_continuettime(String diskrec_continuettime) {
        this.diskrec_continuettime = diskrec_continuettime;
    }

    public String getDisk_loaded() {
        return disk_loaded;
    }

    public void setDisk_loaded(String disk_loaded) {
        this.disk_loaded = disk_loaded;
    }

    public String getDisk_freespace() {
        return disk_freespace;
    }

    public void setDisk_freespace(String disk_freespace) {
        this.disk_freespace = disk_freespace;
    }

    public String getDisk_totalspace() {
        return disk_totalspace;
    }

    public void setDisk_totalspace(String disk_totalspace) {
        this.disk_totalspace = disk_totalspace;
    }

    public String getDisk_recpath() {
        return disk_recpath;
    }

    public void setDisk_recpath(String disk_recpath) {
        this.disk_recpath = disk_recpath;
    }

    public String getRoma_status() {
        return roma_status;
    }

    public void setRoma_status(String roma_status) {
        this.roma_status = roma_status;
    }

    public String getRomb_status() {
        return romb_status;
    }

    public void setRomb_status(String romb_status) {
        this.romb_status = romb_status;
    }

    public String getRoma_isburn() {
        return roma_isburn;
    }

    public void setRoma_isburn(String roma_isburn) {
        this.roma_isburn = roma_isburn;
    }

    public String getRomb_isburn() {
        return romb_isburn;
    }

    public void setRomb_isburn(String romb_isburn) {
        this.romb_isburn = romb_isburn;
    }

    public String getRoma_isfinishburn() {
        return roma_isfinishburn;
    }

    public void setRoma_isfinishburn(String roma_isfinishburn) {
        this.roma_isfinishburn = roma_isfinishburn;
    }

    public String getRomb_isfinishburn() {
        return romb_isfinishburn;
    }

    public void setRomb_isfinishburn(String romb_isfinishburn) {
        this.romb_isfinishburn = romb_isfinishburn;
    }

    public String getRoma_disktype() {
        return roma_disktype;
    }

    public void setRoma_disktype(String roma_disktype) {
        this.roma_disktype = roma_disktype;
    }

    public String getRomb_disktype() {
        return romb_disktype;
    }

    public void setRomb_disktype(String romb_disktype) {
        this.romb_disktype = romb_disktype;
    }

    public String getRoma_discCap() {
        return roma_discCap;
    }

    public void setRoma_discCap(String roma_discCap) {
        this.roma_discCap = roma_discCap;
    }

    public String getRomb_discCap() {
        return romb_discCap;
    }

    public void setRomb_discCap(String romb_discCap) {
        this.romb_discCap = romb_discCap;
    }

    public String getRoma_discCapUsed() {
        return roma_discCapUsed;
    }

    public void setRoma_discCapUsed(String roma_discCapUsed) {
        this.roma_discCapUsed = roma_discCapUsed;
    }

    public String getRomb_discCapUsed() {
        return romb_discCapUsed;
    }

    public void setRomb_discCapUsed(String romb_discCapUsed) {
        this.romb_discCapUsed = romb_discCapUsed;
    }

    public String getRoma_setburntime() {
        return roma_setburntime;
    }

    public void setRoma_setburntime(String roma_setburntime) {
        this.roma_setburntime = roma_setburntime;
    }

    public String getRomb_setburntime() {
        return romb_setburntime;
    }

    public void setRomb_setburntime(String romb_setburntime) {
        this.romb_setburntime = romb_setburntime;
    }

    public String getRoma_begintime() {
        return roma_begintime;
    }

    public void setRoma_begintime(String roma_begintime) {
        this.roma_begintime = roma_begintime;
    }

    public String getRomb_begintime() {
        return romb_begintime;
    }

    public void setRomb_begintime(String romb_begintime) {
        this.romb_begintime = romb_begintime;
    }

    public String getRoma_lefttime() {
        return roma_lefttime;
    }

    public void setRoma_lefttime(String roma_lefttime) {
        this.roma_lefttime = roma_lefttime;
    }

    public String getRomb_lefttime() {
        return romb_lefttime;
    }

    public void setRomb_lefttime(String romb_lefttime) {
        this.romb_lefttime = romb_lefttime;
    }

    public String getBurn_mode() {
        return burn_mode;
    }

    public void setBurn_mode(String burn_mode) {
        this.burn_mode = burn_mode;
    }

    public String getSelburntime() {
        return selburntime;
    }

    public void setSelburntime(String selburntime) {
        this.selburntime = selburntime;
    }

    public String getAudioout_type() {
        return audioout_type;
    }

    public void setAudioout_type(String audioout_type) {
        this.audioout_type = audioout_type;
    }

    public String getBurn_syn_yw() {
        return burn_syn_yw;
    }

    public void setBurn_syn_yw(String burn_syn_yw) {
        this.burn_syn_yw = burn_syn_yw;
    }

    public String getNext_burndev() {
        return next_burndev;
    }

    public void setNext_burndev(String next_burndev) {
        this.next_burndev = next_burndev;
    }

    public String getPrev_burndev() {
        return prev_burndev;
    }

    public void setPrev_burndev(String prev_burndev) {
        this.prev_burndev = prev_burndev;
    }

    public String getYpjl() {
        return ypjl;
    }

    public void setYpjl(String ypjl) {
        this.ypjl = ypjl;
    }

    public String getSubypjl() {
        return subypjl;
    }

    public void setSubypjl(String subypjl) {
        this.subypjl = subypjl;
    }

    public String getCodecver() {
        return codecver;
    }

    public void setCodecver(String codecver) {
        this.codecver = codecver;
    }

    public String getSplitsize() {
        return splitsize;
    }

    public void setSplitsize(String splitsize) {
        this.splitsize = splitsize;
    }

    public String getAudiosample() {
        return audiosample;
    }

    public void setAudiosample(String audiosample) {
        this.audiosample = audiosample;
    }

    public String getTcp_port() {
        return tcp_port;
    }

    public void setTcp_port(String tcp_port) {
        this.tcp_port = tcp_port;
    }

    public String getMap_tcp_port() {
        return map_tcp_port;
    }

    public void setMap_tcp_port(String map_tcp_port) {
        this.map_tcp_port = map_tcp_port;
    }

    public String getSys_tick() {
        return sys_tick;
    }

    public void setSys_tick(String sys_tick) {
        this.sys_tick = sys_tick;
    }

    public String getDvdnum() {
        return dvdnum;
    }

    public void setDvdnum(String dvdnum) {
        this.dvdnum = dvdnum;
    }

    public String getDmsg_lst() {
        return dmsg_lst;
    }

    public void setDmsg_lst(String dmsg_lst) {
        this.dmsg_lst = dmsg_lst;
    }

    public String getTusrs() {
        return tusrs;
    }

    public void setTusrs(String tusrs) {
        this.tusrs = tusrs;
    }

    public String getThr_map() {
        return thr_map;
    }

    public void setThr_map(String thr_map) {
        this.thr_map = thr_map;
    }

    public String getAudmode() {
        return audmode;
    }

    public void setAudmode(String audmode) {
        this.audmode = audmode;
    }

    public String getAudspeaker() {
        return audspeaker;
    }

    public void setAudspeaker(String audspeaker) {
        this.audspeaker = audspeaker;
    }

    public String getAudmic() {
        return audmic;
    }

    public void setAudmic(String audmic) {
        this.audmic = audmic;
    }

    public String getAudout() {
        return audout;
    }

    public void setAudout(String audout) {
        this.audout = audout;
    }

    public String getAotype() {
        return aotype;
    }

    public void setAotype(String aotype) {
        this.aotype = aotype;
    }

    public String getAudio_enable() {
        return audio_enable;
    }

    public void setAudio_enable(String audio_enable) {
        this.audio_enable = audio_enable;
    }

    public String getSubscreen() {
        return subscreen;
    }

    public void setSubscreen(String subscreen) {
        this.subscreen = subscreen;
    }

    public String getMainview_index() {
        return mainview_index;
    }

    public void setMainview_index(String mainview_index) {
        this.mainview_index = mainview_index;
    }

    public String getSubview_index() {
        return subview_index;
    }

    public void setSubview_index(String subview_index) {
        this.subview_index = subview_index;
    }

    public String getDev_version() {
        return dev_version;
    }

    public void setDev_version(String dev_version) {
        this.dev_version = dev_version;
    }
}
