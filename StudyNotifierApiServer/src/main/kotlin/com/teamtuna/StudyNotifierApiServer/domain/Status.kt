package com.teamtuna.StudyNotifierApiServer.domain

import javax.persistence.*

@Entity
@Table(name = "status")
data class Status(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
        @Column(name = "total_study_time") var totalStudyTime: Long,
        @Column(name = "last_updated_time")var lastUpdatedTime: Long,
        var grade: String = "",

        // lazy loading 하여 Status Entity 를 미리 가져오지 않고 사용하는 시점에서 데이터베이서에서 가져옴
        @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id") var member: Member
)