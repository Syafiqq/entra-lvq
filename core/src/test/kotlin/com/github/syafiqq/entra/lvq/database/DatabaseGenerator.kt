package com.github.syafiqq.entra.lvq.database

import com.github.syafiqq.entra.lvq.database.seeder.DatasetSeeder
import com.github.syafiqq.entra.lvq.database.seeder.WeightSeeder


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
        private val dispatcher: Array<DatabaseDispatcher> = arrayOf(CreateDatasetTable(), CreateWeightTable())
    }

    override fun up()
    {
        dispatcher.forEach(DatabaseDispatcher::up)
    }

    override fun down()
    {
        dispatcher.forEach(DatabaseDispatcher::down)
    }

    fun refresh()
    {
        this.down()
        this.up()
    }

    fun refreshAndSeed()
    {
        this.refresh()
        WeightSeeder().run(WeightSeeder::addInitial)
        DatasetSeeder().run(DatasetSeeder::addInitial)
    }
}

fun main(args: Array<String>)
{
    DatabaseGenerator().run(DatabaseGenerator::refreshAndSeed)
}
