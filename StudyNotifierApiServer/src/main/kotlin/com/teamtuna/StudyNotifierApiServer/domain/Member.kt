package com.teamtuna.StudyNotifierApiServer.domain

import javax.persistence.*

@Entity
@Table(name = "member")
data class Member(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
        var email: String,
        var pw: String,
        var name: String,
        var deviceToken: String = "",
        @Column(name = "profile_url") var profileUrl: String = "",
        @OneToMany(mappedBy = "member", fetch = FetchType.EAGER) var study: MutableList<Study> = mutableListOf()
) {
    fun addStudy(study: Study) {
        this.study.add(study)
    }
}