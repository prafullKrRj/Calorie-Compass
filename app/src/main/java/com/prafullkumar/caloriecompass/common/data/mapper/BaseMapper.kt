package com.prafullkumar.caloriecompass.common.data.mapper

interface BaseMapper<ENTITY, DOMAIN> {
    fun mapToEntity(domain: DOMAIN): ENTITY
    fun mapToDomain(entity: ENTITY): DOMAIN
}