/**
 * Copyright 2015 ZhangQu Li
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.zafu.corepage.core;

import java.io.Serializable;

/**
 * assets/page.json 页面属性类
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:34
 */
public class CorePage implements Serializable {
    private static final long serialVersionUID = 3736359137726536495L;

    private String mName;
    //页面名
    private String mClazz;
    //页面class
    private String mParams;
    //传入参数，json object结构

    public CorePage(String name, String clazz, String params) {
        mName = name;
        mClazz = clazz;
        mParams = params;
    }

    public String getClazz() {
        return mClazz;
    }

    public void setClazz(String clazz) {
        mClazz = clazz;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getParams() {
        return mParams;
    }

    public void setParams(String params) {
        mParams = params;
    }

    @Override
    public String toString() {
        return "Page{" +
                "mName='" + mName + '\'' +
                ", mClazz='" + mClazz + '\'' +
                ", mParams='" + mParams + '\'' +
                '}';
    }
}
