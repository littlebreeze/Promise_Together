package com.myfirstapp.logintest2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class TODO implements Parcelable {

    private static final String TAG = TODO.class.getSimpleName();

    private String documentId;
    private boolean isChecked;
    private String title, content;
    private int requiredTime;
    private Date endTime;

    public TODO() { }

    public TODO(boolean isChecked, String title, String content, int requiredTime, Date endTime) {
        this.title = title;
        this.isChecked = isChecked;
        this.content = content;
        this.requiredTime = requiredTime;
        this.endTime = endTime;
    }

    public TODO(Parcel parcel) {
        documentId = parcel.readString();
        title = parcel.readString();
        isChecked = parcel.readByte() != 0; //신기하군
        content = parcel.readString();
        requiredTime = parcel.readInt();
        endTime = new Date(parcel.readLong()); //여얼~
    }

    public static final Creator<TODO> CREATOR = new Creator<TODO>() {
        @Override
        public TODO createFromParcel(Parcel in) {
            return new TODO(in);
        }

        @Override
        public TODO[] newArray(int size) {
            return new TODO[size];
        }
    };

    public boolean isChecked() {
        return isChecked;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getDocumentId() {
        return documentId;
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(documentId);
        parcel.writeString(title);
        parcel.writeByte((byte) (isChecked ? 1 : 0)); //신기하군2
        parcel.writeString(content);
        parcel.writeInt(requiredTime);
        parcel.writeLong(endTime.getTime()); //오올
    }
}