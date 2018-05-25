package com.github.syafiqq.entra.lvq.model.database.pojo;

import it.unimi.dsi.fastutil.objects.Object2DoubleLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;

/*
 * This <entra-lvq> created by :
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 2:42 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class WeightPojo
{
    public final Object2DoubleMap<String> vector;
    public Integer id;
    public int target;

    public WeightPojo()
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

    @Override public String toString()
    {
        return String.format("%d, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %g, %d",
                this.id,
                this.vector.getOrDefault("g1", 0.0),
                this.vector.getOrDefault("g2", 0.0),
                this.vector.getOrDefault("g3", 0.0),
                this.vector.getOrDefault("g4", 0.0),
                this.vector.getOrDefault("g5", 0.0),
                this.vector.getOrDefault("g6", 0.0),
                this.vector.getOrDefault("g7", 0.0),
                this.vector.getOrDefault("g8", 0.0),
                this.vector.getOrDefault("g9", 0.0),
                this.vector.getOrDefault("g10", 0.0),
                this.vector.getOrDefault("g11", 0.0),
                this.vector.getOrDefault("g12", 0.0),
                this.vector.getOrDefault("g13", 0.0),
                this.vector.getOrDefault("g14", 0.0),
                this.vector.getOrDefault("g15", 0.0),
                this.vector.getOrDefault("g16", 0.0),
                this.vector.getOrDefault("g17", 0.0),
                this.vector.getOrDefault("g18", 0.0),
                this.vector.getOrDefault("g19", 0.0),
                this.vector.getOrDefault("g20", 0.0),
                this.vector.getOrDefault("g21", 0.0),
                this.target);
    }

    public int getTarget()
    {
        return this.target;
    }
}
