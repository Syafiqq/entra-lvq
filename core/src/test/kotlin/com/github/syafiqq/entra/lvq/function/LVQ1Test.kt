package com.github.syafiqq.entra.lvq.function

import org.junit.Assert
import org.junit.Test


/*
 * This <entra-lvq> created by : 
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 10:09 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
class LVQ1Test
{
    @Test
    fun `it_should_instantiate_object_successfully`()
    {
        val lvq = LVQ1()
        Assert.assertNotNull(lvq)
    }

    @Test
    fun `it_should_have_non_empty_dataset_after_initialize`()
    {
        val lvq = LVQ1()
        lvq.initialization()
        Assert.assertNotNull(lvq)
        Assert.assertTrue(lvq.dataset.size > 0)
        lvq.dataset.forEach(System.out::println)
    }

    @Test
    fun `it_should_have_non_empty_weight_after_initialize`()
    {
        val lvq = LVQ1()
        lvq.initialization()
        Assert.assertNotNull(lvq)
        Assert.assertTrue(lvq.weight.size > 0)
        lvq.weight.forEach(System.out::println)
    }
}
