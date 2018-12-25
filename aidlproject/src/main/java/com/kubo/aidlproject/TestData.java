package com.kubo.aidlproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * 测试数据序列化
 * @author hfcai
 */
public class TestData implements Parcelable {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "TestData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tips='" + tips + '\'' +
                ", can=" + can +
                '}';
    }

    private String tips;
    private boolean can;

    public TestData(){}

    public TestData(int id, String name, String tips, boolean can) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.can = can;
    }

    protected TestData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        tips = in.readString();
        can = in.readByte()!=0;

    }


    public void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        tips = in.readString();
        can = in.readByte()!=0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(tips);
        dest.writeByte((byte) (can?1:0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestData> CREATOR = new Creator<TestData>() {
        @Override
        public TestData createFromParcel(Parcel in) {
            return new TestData(in);
        }

        @Override
        public TestData[] newArray(int size) {
            return new TestData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCan() {
        return can;
    }

    public void setCan(boolean can) {
        this.can = can;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }


}
