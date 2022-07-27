package com.example.project.api.main.response

class ComplaintResult {
    var status: String? = null
    var message: String? = null
}


class Requests {
    var parentId: Long? = null
    lateinit var id: String
    var title: String? = null
    var description: String? = null
    var children: List<Category>? = null
}


class Category() {
    var parentId: Long? = null
    var id: Long = 0
    var title: String? = null
    var description: String? = null
    var children: List<Category>? = null
}

class OrganizationalUnits {

    var parentId: Long? = null
    var id: Long = 0
    var title: String? = null
    var description: String? = null
    var children: List<OrganizationalUnits>? = null

}


class Organization {
    var id: Long = 0
    var title: String? = null
    var description: String? = null
}