package com.example.internproject.domain

data class Category(
    val parentId: String?,
    val id: String,
    val title: String = "heh",
    val description: String?,
    val children: List<Category>?,
//    val filterType: FilterType,
)

data class Organization(
    val parentId: String?,
    val id: String,
    val title: String = "heh",
    val description: String?,
    val children: List<Category>?,
//    val filterType: FilterType,

)
