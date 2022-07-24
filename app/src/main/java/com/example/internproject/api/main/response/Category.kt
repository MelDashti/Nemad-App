package com.example.internproject.api.main.response

class Category() {
    var parentId: Long? = null
    var id: Long = 0
    var title: String? = null
    var description: String? = null
    var children: List<Category>? = null
}


class Organization {
    var id: Long = 0
    var title: String? = null
    var description: String? = null
}