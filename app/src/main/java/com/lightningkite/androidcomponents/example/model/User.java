package com.lightningkite.androidcomponents.example.model;

import com.lightningkite.androidcomponents.form.AutoFormPosition;

import co.uk.rushorm.core.RushObject;

/**
 * Created by jivie on 6/10/15.
 */
public class User extends RushObject {
    public String name;
    public String password;
    public String email;
    @AutoFormPosition(0)
    public int id;
}
