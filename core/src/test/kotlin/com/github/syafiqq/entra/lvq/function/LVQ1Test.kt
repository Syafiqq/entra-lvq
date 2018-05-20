package com.github.syafiqq.entra.lvq.function

import com.github.syafiqq.entra.lvq.function.model.ProcessedDatasetPojo
import com.github.syafiqq.entra.lvq.model.database.dao.DatasetDao
import com.github.syafiqq.entra.lvq.util.Settings
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
        val lvq = LVQ1(0.05, 0.1, 1E-11, 5)
        Assert.assertNotNull(lvq)
    }

    @Test
    fun `it_should_have_non_empty_dataset_after_initialize`()
    {
        val lvq = LVQ1(0.05, 0.1, 1E-11, 5)
        lvq.initialization()
        Assert.assertNotNull(lvq)
        Assert.assertTrue(lvq.dataset.size > 0)
        lvq.dataset.forEach(System.out::println)
    }

    @Test
    fun `it_should_have_non_empty_weight_after_initialize`()
    {
        val lvq = LVQ1(0.05, 0.1, 1E-11, 5)
        lvq.initialization()
        Assert.assertNotNull(lvq)
        Assert.assertTrue(lvq.weight.size > 0)
        lvq.weight.forEach(System.out::println)
    }

    @Test
    fun `it_should_run_lvq_smoothly`()
    {
        val lvq = LVQ1(0.05, 0.1, 1E-11, 5)
        lvq.training()
        lvq.weight.forEach(System.out::println)
    }

    @Test
    fun `it_should_run_lvq_smoothly_with_test`()
    {
        val testing = mutableListOf(DatasetDao.getAll(Settings.DB).map(::ProcessedDatasetPojo)[0])
        val lvq = LVQ1(0.05, 0.1, 1E-11, 5)
        lvq.training()
        lvq.testing(testing)
        println(lvq.calculateAccuracy(testing))
        println(testing[0].actualTarget)
    }
}
