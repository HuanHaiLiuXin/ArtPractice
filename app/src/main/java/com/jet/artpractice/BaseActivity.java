package com.jet.artpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者:幻海流心
 * GitHub:https://github.com/HuanHaiLiuXin
 * 邮箱:wall0920@163.com
 * 2018/6/15 10:32
 */
public class BaseActivity extends AppCompatActivity {
    public void jump(Class clazz){
        startActivity(new Intent(this,clazz));
    }
}