package org.example.entity

import io.ebean.annotation.DbJson
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

enum class Role(val code: Int) {
    none(0),
    standard(1),
    administrator(2),
}

@Embeddable
data class CreatedInfo (
    var createdBy:Long = 0,
    @Column(length = 16)
    var createdFunc:String = "",
    var createdAt: LocalDateTime? = null,
)

@Embeddable
data class UpdatedInfo (
    var updatedBy:Long = 0,
    @Column(length = 16)
    var updatedFunc:String = "",
    var updatedAt: LocalDateTime? = null,
)

@Embeddable
data class UserData (
    var name:String = "",
    var isActive:Boolean = false,
    var role:Role = Role.none
)

@Entity(name = "mUser")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id:Long = 0,
    @Embedded
    var content: UserData,
    @Embedded
    var crated: CreatedInfo,
    @Embedded
    var updated: UpdatedInfo,
)

@Embeddable
data class UserPasswordData (
    var hash:String = "",
)

@Entity(name = "mUserPassword")
data class UserPasswordEntity (
    @Id
    var id: Long = 0,
    @Embedded
    var content: UserPasswordData,
    @Embedded
    var crated: CreatedInfo,
    @Embedded
    var updated: UpdatedInfo,
)

@Embeddable
data class UserKey(
    val userId: Long,
    val time: Int
)

@Embeddable
data class UserHistoryData (
    var id: Long = 0,
    var user: UserData? = null,
    var password: UserPasswordData? = null,
)

@Entity(name = "mUserHistory")
data class UserHistoryEntity (
    @EmbeddedId
    var id:UserKey,
    @DbJson
    var content: UserHistoryData,
    @Embedded
    var crated: CreatedInfo,
)

@Entity(name = "mUserActivity")
data class UserActivityEntity (
    @EmbeddedId
    var id:UserKey,
    @DbJson
    var content:Map<String, String>? = null,
    @Embedded
    var crated: CreatedInfo,
)
