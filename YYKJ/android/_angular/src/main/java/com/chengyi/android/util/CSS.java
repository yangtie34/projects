package com.chengyi.android.util;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chengyi.android.angular.R;

/**通用的样式类
 * Created by administrator on 2016-12-19.
 */

public class CSS {
    /**
     * 样式
     */
    public static class LayoutParams {
        public static RelativeLayout.LayoutParams wrapAll(){
            return new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        public static RelativeLayout.LayoutParams matchAll(){
            return new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        public static RelativeLayout.LayoutParams wrapWidth(){
            return new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        public static RelativeLayout.LayoutParams wrapHeight(){
            return new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 效果
     */
    public static class effect{
        public final static int duration=200;//持续的时间
        /**
         * 进入退出出动画
         */
        public static class Animation{
            public static int byAlpha(){
                return R.style.animByAlpha;
            }
            public static int byScale(){
                return R.style.animByScale;
            }
            public static int byBottom(){
                return R.style.animByBottom;
            }
            public static int byTop(){
                return R.style.animByTop;
            }
            public static int byLeft(){
                return R.style.animByLeft;
            }
            public static int byRight(){
                return R.style.animByRight;
            }
            public static int getAnimByGravity(int gravity){
                int style=0;
                switch (gravity){
                    case Gravity.LEFT:
                        style= byLeft();
                     break;
                    case Gravity.RIGHT:
                        style= byRight();
                    break;
                    case Gravity.BOTTOM:
                        style= byBottom();
                    break;
                    case Gravity.TOP:
                        style= byTop();
                    break;
                    default:
                        style=gravity;
                        break;
                }
                return style;
            }
        }
        /**
         * half进入退出动画
         */
        public static class AnimationHalf{
            public static int byBottom(){
                return R.style.animByBottomHalf;
            }
            public static int byTop(){
                return R.style.animByTopHalf;
            }
            public static int byLeft(){
                return R.style.animByLeftHalf;
            }
            public static int byRight(){
                return R.style.animByRightHalf;
            }
            public static int getAnimByGravity(int gravity){
                int style=0;
                switch (gravity){
                    case Gravity.LEFT:
                        style= byLeft();
                        break;
                    case Gravity.RIGHT:
                        style= byRight();
                        break;
                    case Gravity.BOTTOM:
                        style= byBottom();
                        break;
                    case Gravity.TOP:
                        style= byTop();
                        break;
                    default:
                        style=gravity;
                        break;
                }
                return style;
            }
        }

    }

}
