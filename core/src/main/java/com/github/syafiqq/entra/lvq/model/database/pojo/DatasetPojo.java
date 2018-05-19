package com.github.syafiqq.entra.lvq.model.database.pojo;

import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 2:49 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetPojo
{
    public final Object2DoubleMap<String> vector;
    public Integer id;
    public int target;

    public DatasetPojo()
    {
        this.id = null;
        this.vector = new Object2DoubleLinkedOpenHashMap<>();
        this.target = 0;
    }

    public double vector(String key, double value)
    {
        return vector.put(key, value);
    }

    public double vector(String key)
    {
        return vector.getOrDefault(key, -1);
    }
}
