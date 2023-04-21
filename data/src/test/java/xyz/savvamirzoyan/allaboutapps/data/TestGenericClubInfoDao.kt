package xyz.savvamirzoyan.allaboutapps.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.savvamirzoyan.allaboutapps.storage.dao.GenericClubInfoDao
import xyz.savvamirzoyan.allaboutapps.storage.model.GenericClubInfoLocal

internal abstract class TestGenericClubInfoDao : GenericClubInfoDao {

    override suspend fun insert(vararg items: GenericClubInfoLocal) {
        TODO("Not yet implemented")
    }

    override suspend fun selectAll(): List<GenericClubInfoLocal> {
        TODO("Not yet implemented")
    }

    override suspend fun select(name: String): GenericClubInfoLocal? {
        TODO("Not yet implemented")
    }

    override fun selectAllFlow(): Flow<List<GenericClubInfoLocal>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: GenericClubInfoLocal) {
        TODO("Not yet implemented")
    }

    override suspend fun nukeTable() {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: GenericClubInfoLocal) {
        TODO("Not yet implemented")
    }
}