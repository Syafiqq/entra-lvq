package com.github.syafiqq.entra.lvq.function.model;

import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;
import com.github.syafiqq.entra.lvq.model.database.pojo.WeightPojo;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 8:57 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public abstract class ProcessedWeightPojo<T>
{
    protected WeightPojo weight;
    protected T distance;

    public abstract void calculateDistance(DatasetPojo pojo);

    public double vector(String key, double value)
    {
        return weight.vector(key, value);
    }

    public double vector(String key)
    {
        return weight.vector(key);
    }
}
