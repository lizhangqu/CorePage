package cn.edu.zafu.corepage.sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.zafu.corepage.base.BaseFragment;
import cn.edu.zafu.corepage.core.CoreAnim;


/**
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-20
 * Time: 16:29
 */
public class TestFragment1 extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test1,container,false);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //openPage("test2",null, Anim.slide,true);
                openPageForResult("test2", null, CoreAnim.slide, 101);
            }
        });

        return view;
    }
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Intent data) {
        Log.e("Test onResult fragment", requestCode + " " + resultCode + " ");
        super.onFragmentResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        String param1 = arguments.getString("param1");
        String param2 = arguments.getString("param2");
        String param3 = arguments.getString("test");
        Log.e("tag", param1 + "  " + param2 + "  " + param3);
    }
}
