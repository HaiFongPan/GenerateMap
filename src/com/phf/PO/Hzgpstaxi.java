package com.phf.PO;

import java.sql.Timestamp;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-4-22
 * Time: 上午11:09
 * Project: GenerateRN
 */
public class Hzgpstaxi  implements Comparator<Hzgpstaxi>{
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    private int vehicleId;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    private String vehicleNum;

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    private double longi;

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    private double lati;

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    private long px;

    public long getPx() {
        return px;
    }

    public void setPx(long px) {
        this.px = px;
    }

    private long py;

    public long getPy() {
        return py;
    }

    public void setPy(long py) {
        this.py = py;
    }

    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private Timestamp speedTime;

    public Timestamp getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(Timestamp speedTime) {
        this.speedTime = speedTime;
    }

    private String note;
    private int carstate;
    private Timestamp dbtime;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getCarstate() {
		return carstate;
	}

	public void setCarstate(int carstate) {
		this.carstate = carstate;
	}

	public Timestamp getDbtime() {
		return dbtime;
	}

	public void setDbtime(Timestamp dbtime) {
		this.dbtime = dbtime;
	}
	public void showHzgpstaxi(){
		System.out.println(this.messageId+","+this.longi+","+this.lati+","+this.px+","+this.py+","+this.speedTime);
	}

	@Override
	public int compare(Hzgpstaxi o1, Hzgpstaxi o2) {

		if(o1.getVehicleId() < o2.getVehicleId())
			return 1;
		else
			return o1.getSpeedTime().compareTo(o2.getSpeedTime());
	}
}
