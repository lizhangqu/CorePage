package com.kltz88.alldemo.page;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:37
 */
public class PageSwitchBean implements Parcelable{
    private String mPageName;
    private Bundle mBundle;
    private boolean addToBackStack=true;
    private boolean newActivity=false;
    public PageSwitchBean(String pageName){
        this.mPageName=pageName;
    }
    public PageSwitchBean(String pageName,Bundle bundle){
        this.mPageName=pageName;
        this.mBundle = bundle;
    }
    public PageSwitchBean(String pageName,Bundle bundle,boolean newActivity) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.newActivity=newActivity;
    }
    public PageSwitchBean(String pageName,Bundle bundle,boolean newActivity,boolean addToBackStack){
        this.mPageName=pageName;
        this.mBundle=bundle;
        this.newActivity=newActivity;
        this.addToBackStack=addToBackStack;
    }

    public String getPageName() {
        return mPageName;
    }

    public void setPageName(String pageName) {
        mPageName = pageName;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }

    public void setAddToBackStack(boolean addToBackStack) {
        this.addToBackStack = addToBackStack;
    }

    public boolean isNewActivity() {
        return newActivity;
    }

    public void setNewActivity(boolean newActivity) {
        this.newActivity = newActivity;
    }

    @Override
    public String toString() {
        return "PageSwitchBean{" +
                "mPageName='" + mPageName + '\'' +
                ", mBundle=" + mBundle +
                ", addToBackStack=" + addToBackStack +
                ", newActivity=" + newActivity +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPageName);
        dest.writeBundle(mBundle);
        dest.writeByte(addToBackStack ? (byte) 1 : (byte) 0);
        dest.writeByte(newActivity ? (byte) 1 : (byte) 0);
    }

    protected PageSwitchBean(Parcel in) {
        this.mPageName = in.readString();
        mBundle = in.readBundle();
        this.addToBackStack = in.readByte() != 0;
        this.newActivity = in.readByte() != 0;
    }

    public static final Creator<PageSwitchBean> CREATOR = new Creator<PageSwitchBean>() {
        public PageSwitchBean createFromParcel(Parcel source) {
            return new PageSwitchBean(source);
        }

        public PageSwitchBean[] newArray(int size) {
            return new PageSwitchBean[size];
        }
    };
}
