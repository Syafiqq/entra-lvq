package com.github.syafiqq.entra.lvq.observable.java.util;

import java.util.Collection;
import java.util.List;
import java.util.Observable;
import org.jetbrains.annotations.NotNull;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 22 May 2018, 8:29 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class OList<T> extends Observable
{
    public final List<T> lists;

    public OList(List<T> lists)
    {
        this.lists = lists;
    }

    public void clear()
    {
        lists.clear();
    }

    public void oClear()
    {
        this.clear();
        this.commit(null);
    }

    public boolean refresh(List<T> lists)
    {
        this.clear();
        return this.addAll(lists);
    }

    public boolean addAll(@NotNull Collection<? extends T> c)
    {
        boolean result = lists.addAll(c);
        this.commit(null);
        return result;
    }

    private void commit(Object data)
    {
        this.setChanged();
        this.notifyObservers(data == null ? this.lists : data);
    }
}
