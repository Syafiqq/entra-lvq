package com.github.syafiqq.entra.lvq.observable.java.lang;

import java.util.Observable;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 26 May 2018, 7:15 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class OStringBuilder extends Observable
{
    public final StringBuilder builder;

    public OStringBuilder(StringBuilder builder)
    {
        this.builder = builder;
    }

    public StringBuilder append(String str)
    {
        StringBuilder result = builder.append(str);
        this.setChanged();
        this.notifyObservers(result);
        return result;
    }

    public void setLength(int newLength)
    {
        builder.setLength(newLength);
        this.setChanged();
        this.notifyObservers(this.builder);
    }
}
