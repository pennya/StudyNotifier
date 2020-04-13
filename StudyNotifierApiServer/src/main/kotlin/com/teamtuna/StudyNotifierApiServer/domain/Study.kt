package com.teamtuna.StudyNotifierApiServer.domain

import javax.persistence.*

@Entity
@Table(name = "study")
data class Study(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
        @Column(name = "start_time") var startTime: Long,
        @Column(name = "end_time") var endTime: Long,
        var date: Long,
        var title: String,
        var description: String = "",
        @ManyToOne @JoinColumn(name = "member_id") var member: Member? = null
)