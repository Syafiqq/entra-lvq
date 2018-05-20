package com.github.syafiqq.entra.lvq.function.model;

import com.github.syafiqq.entra.lvq.model.database.pojo.DatasetPojo;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 20 May 2018, 12:03 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class ProcessedDatasetPojo
{
    public DatasetPojo dataset;
    public int actualTarget;

    public ProcessedDatasetPojo(DatasetPojo pojo)
    {
        this.dataset = pojo;
    }

    public boolean isSameClass()
    {
        return this.dataset.target == this.actualTarget;
    }

    public double vector(String key, double value)
    {
        return dataset.vector(key, value);
    }

    public double vector(String key)
    {
        return dataset.vector(key);
    }

    @Override public String toString()
    {
        return this.dataset.toString();
    }
}
