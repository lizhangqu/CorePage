package cn.edu.zafu.corepage.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import cn.edu.zafu.corepage.base.BaseFragment;
import cn.edu.zafu.corepage.core.CoreAnim;


/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:29
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1= (Button) view.findViewById(R.id.btn1);
        btn2= (Button) view.findViewById(R.id.btn2);
        btn3= (Button) view.findViewById(R.id.btn3);
        btn4= (Button) view.findViewById(R.id.btn4);
        btn5= (Button) view.findViewById(R.id.btn5);
        btn6= (Button) view.findViewById(R.id.btn6);
        btn7= (Button) view.findViewById(R.id.btn7);
        btn8= (Button) view.findViewById(R.id.btn8);
        btn9= (Button) view.findViewById(R.id.btn9);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //int anim[]= {R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right};
                //openPage("test1",null, anim);
                openPage("test1",null, CoreAnim.slide);
                break;
            case R.id.btn2:
                openPage("test1",null,CoreAnim.none);
                break;
            case R.id.btn3:
                openPage("test1",null,CoreAnim.fade,true);
                break;
            case R.id.btn4:
                openPage("test1",null,CoreAnim.fade,false);
                break;
            case R.id.btn5:
                openPage("test1",null,CoreAnim.fade,true,true);
                break;
            case R.id.btn6:
                int requestCode=1;
                Bundle bundle=new Bundle();
                openPageForResult("test2",bundle,CoreAnim.fade,requestCode);
                break;
            case R.id.btn7:
                Bundle params=new Bundle();
                params.putString("test","hello i'm from main");
                openPage("test3", params, CoreAnim.slide);
                break;
            case R.id.btn8:
                popToBack();
                break;
            case R.id.btn9:
                openPage("test4",null, CoreAnim.present);
                break;

        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if(data!=null) {
            Bundle extras = data.getExtras();
            Toast.makeText(getActivity(), "requestCode:" + requestCode + " result:" + resultCode+" data:"+extras.getString("data"), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
