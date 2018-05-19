package com.github.syafiqq.entra.lvq.database

import org.junit.BeforeClass
import org.junit.Test


/*
 * This <entra-lvq> created by : 
 * Name         : syafiq
 * Date / Time  : 19 May 2018, 3:51 PM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
class DatabaseGenerator: DatabaseDispatcher
{
    companion object
    {
        private lateinit var dispatcher: Array<DatabaseDispatcher>

        @BeforeClass
        @JvmStatic
        fun initialize()
        {
            this.dispatcher = arrayOf(CreateDatasetTable(), CreateWeightTable())
        }
    }


    @Test override fun up()
    {
        dispatcher.forEach(DatabaseDispatcher::up)
    }

    @Test override fun down()
    {
        dispatcher.forEach(DatabaseDispatcher::down)
    }
}
