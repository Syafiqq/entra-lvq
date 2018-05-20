package com.github.syafiqq.entra.lvq.function.model;

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

    public ProcessedWeightPojo(WeightPojo pojo)
    {
        this.weight = pojo;
    }

    public abstract void calculateDistance(ProcessedDatasetPojo pojo);

    public abstract boolean isSameSignature(ProcessedDatasetPojo data);

    public abstract void moveToward(ProcessedDatasetPojo data);

    public abstract void moveAway(ProcessedDatasetPojo data);

    public double vector(String key, double value)
    {
        return weight.vector(key, value);
    }

    public double vector(String key)
    {
        return weight.vector(key);
    }

    public T getDistance()
    {
        return this.distance;
    }
}
