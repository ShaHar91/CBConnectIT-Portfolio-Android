package be.christiano.portfolio.app.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import be.christiano.portfolio.app.domain.enums.LinkType

@Entity(tableName = LinkEntity.ENTITY_NAME, indices = [Index(unique = true, value = ["workId", "type"])])
data class LinkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val workId: String,
    val type: LinkType,
    val url: String
) {
    companion object {
        const val ENTITY_NAME = "link"
    }
}
