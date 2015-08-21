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

/**
 * 页面切换动画类别
 * User:lizhangqu(513163535@qq.com)
 * Date:2015-07-22
 * Time: 09:42
 */
public enum CoreAnim {
    none, /* 没有动画 */
    present, /*由下到上动画 */
    slide,/* 从左到右动画 */
    fade,/*渐变 */
    zoom;/*放大 */
}
