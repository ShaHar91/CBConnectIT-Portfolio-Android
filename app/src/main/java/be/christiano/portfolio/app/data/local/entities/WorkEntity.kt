package be.christiano.portfolio.app.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(WorkEntity.ENTITY_NAME)
data class WorkEntity(
    @PrimaryKey
    val id: String,
    val image: String,
    val title: String,
    val description: String,
    val link: String,
) {
    companion object {
        const val ENTITY_NAME = "work"
    }
}

//TODO: M-M relation!!

@Entity(TagEntity.ENTITY_NAME)
data class TagEntity(
    @PrimaryKey
    val id: String,
    val name: String
) {
    companion object {
        const val ENTITY_NAME = "tag"
    }
}

data class WorkWithTags(
    @Embedded
    val work: WorkEntity,
    @Relation(
        parentColumn = "id",
        entity = TagEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = WorkTagCrossRefEntity::class,
            parentColumn = WorkTagCrossRefEntity.COLUMN_ID_WORK,
            entityColumn = WorkTagCrossRefEntity.COLUMN_ID_TAG
        )
    )
    val tags: List<TagEntity>
)