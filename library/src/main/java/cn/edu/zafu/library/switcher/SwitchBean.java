package cn.edu.zafu.library.switcher;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

import cn.edu.zafu.library.R;
import cn.edu.zafu.library.anim.Anim;

/**
 * 页面跳转控制参数
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public class SwitchBean implements Parcelable {
    public static final Parcelable.Creator<SwitchBean> CREATOR = new Parcelable.Creator<SwitchBean>() {
        @Override
        public SwitchBean createFromParcel(Parcel in) {
            return new SwitchBean(in);
        }

        @Override
        public SwitchBean[] newArray(int size) {
            return new SwitchBean[size];
        }
    };
    private String mPageName;
    //页面名
    private Bundle mBundle;
    //相关数据
    private int[] mAnim = null;
    //动画类型
    private boolean mAddToBackStack = true;
    //是否添加到栈中
    private boolean mNewActivity = false;
    //是否起新的Activity
    private int requestCode = -1;

    //fragment跳转
    public SwitchBean(String pageName) {
        this.mPageName = pageName;
    }

    public SwitchBean(String pageName, Bundle bundle) {
        this.mPageName = pageName;
        this.mBundle = bundle;
    }

    public SwitchBean(String pageName, Bundle bundle, Anim anim) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.setAnim(anim);
    }

    public void setAnim(Anim anim) {
        mAnim = convertAnimations(anim);
    }

    public static int[] convertAnimations(Anim anim) {
        if (anim == Anim.present) {
            int[] animations = {R.anim.push_in_down, R.anim.push_no_ani, R.anim.push_no_ani, R.anim.push_out_down};
            return animations;
        } else if (anim == Anim.fade) {
            int[] animations = {R.anim.alpha_in, R.anim.alpha_out, R.anim.alpha_in, R.anim.alpha_out};
            return animations;
        } else if (anim == Anim.slide) {
            int[] animations = {R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right};
            return animations;
        }
        return null;
    }

    public SwitchBean(String pageName, Bundle bundle, int[] anim) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.mAnim = anim;
    }

    public SwitchBean(String pageName, Bundle bundle, Anim anim, boolean addToBackStack) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.setAnim(anim);
        this.mAddToBackStack = addToBackStack;
    }

    public SwitchBean(String pageName, Bundle bundle, int[] anim, boolean addToBackStack) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.mAnim = anim;
        this.mAddToBackStack = addToBackStack;
    }

    public SwitchBean(String pageName, Bundle bundle, Anim anim, boolean addToBackStack, boolean newActivity) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.setAnim(anim);
        this.mAddToBackStack = addToBackStack;
        this.mNewActivity = newActivity;
    }

    public SwitchBean(String pageName, Bundle bundle, int[] anim, boolean addToBackStack, boolean newActivity) {
        this.mPageName = pageName;
        this.mBundle = bundle;
        this.mAnim = anim;
        this.mAddToBackStack = addToBackStack;
        this.mNewActivity = newActivity;
    }

    protected SwitchBean(Parcel in) {
        mPageName = in.readString();
        mBundle = in.readBundle();
        int[] a = {in.readInt(), in.readInt(), in.readInt(), in.readInt()};
        mAnim = a;
        mAddToBackStack = in.readInt() == 1 ? true : false;
        mNewActivity = in.readInt() == 1 ? true : false;
        requestCode = in.readInt();
    }

    public String getPageName() {
        return mPageName;
    }

    public void setPageName(String pageName) {
        mPageName = pageName;
    }

    public boolean isNewActivity() {
        return mNewActivity;
    }

    public void setNewActivity(boolean newActivity) {
        mNewActivity = newActivity;
    }

    public boolean isAddToBackStack() {
        return mAddToBackStack;
    }

    public void setAddToBackStack(boolean addToBackStack) {
        mAddToBackStack = addToBackStack;
    }

    public int[] getAnim() {
        return mAnim;
    }

    public void setAnim(int[] anim) {
        mAnim = anim;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    @Override
    public String toString() {
        return "SwitchBean{" +
                "mPageName='" + mPageName + '\'' +
                ", mBundle=" + mBundle +
                ", mAnim=" + Arrays.toString(mAnim) +
                ", mAddToBackStack=" + mAddToBackStack +
                ", mNewActivity=" + mNewActivity +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        if (mPageName == null) {
            mPageName = "";
        }
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        if (mAnim == null) {
            int[] a = {-1, -1, -1, -1};
            mAnim = a;
        }
        out.writeString(mPageName);
        mBundle.writeToParcel(out, flags);
        if (mAnim != null && mAnim.length == 4) {
            out.writeInt(mAnim[0]);
            out.writeInt(mAnim[1]);
            out.writeInt(mAnim[2]);
            out.writeInt(mAnim[3]);
        } else {
            out.writeInt(-1);
            out.writeInt(-1);
            out.writeInt(-1);
            out.writeInt(-1);
        }
        out.writeInt(mAddToBackStack ? 1 : 0);
        out.writeInt(mNewActivity ? 1 : 0);
        out.writeInt(requestCode);
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
